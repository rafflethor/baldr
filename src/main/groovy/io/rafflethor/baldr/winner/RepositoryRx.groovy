package io.rafflethor.baldr.winner

import javax.inject.Inject
import javax.inject.Singleton
import java.util.stream.Collectors

import io.reactivex.Single
import io.reactivex.Observable
import io.reactivex.Completable
import io.reactiverse.reactivex.pgclient.Row
import io.reactiverse.reactivex.pgclient.PgPool
import io.reactiverse.reactivex.pgclient.PgTransaction
import io.reactiverse.reactivex.pgclient.Tuple
import io.reactiverse.reactivex.pgclient.PgRowSet
import io.reactiverse.reactivex.pgclient.PgConnection

import io.rafflethor.baldr.db.RxUtils

/**
 * Database access repository implementation using plain Groovy jdbc
 *
 * @since 0.1.0
 */
@Singleton
class RepositoryRx implements Repository {

  @Inject
  PgPool client

  @Override
  Observable<Winner> saveWinners(UUID raffle, List<WinnerInput> winners) {
    Completable completable = client
      .rxBegin()
      .flatMapCompletable({ PgTransaction tx ->
         saveAll(tx, winners) // FALTA tx.rollback()
          .flatMapCompletable({ PgRowSet rowSet -> tx.rxCommit() })
      })

    return completable.andThen(findAllWinners(raffle))
  }

  Single<PgRowSet> saveAll(PgTransaction tx, List<WinnerInput> winners) {
    String query = '''
        INSERT INTO winners
          (id, participantId, raffleId)
        VALUES
          ($1, $2, $3)
      '''

    return winners
      .stream()
      .map({ WinnerInput p ->
        UUID uuid = UUID.randomUUID()
        tx.rxPreparedQuery(
          query,
          Tuple.of(uuid, p.participantId, p.raffleId)
        )
    }).reduce({ Single<PgRowSet> left, Single<PgRowSet> right -> left.flatMap({ PgRowSet l -> right }) })
      .orElse(Single.error(new Exception('ss')))
  }

  @Override
  Observable<Winner> findAllWinners(UUID raffle) {
    Closure<Single<PgRowSet>> getAll = this.listWinners(raffle)

    return getAll()
      .flatMapObservable(RxUtils.&toObservable)
      .map(this.&toWinner)
  }

  @Override
  Observable<Winner> markWinnersAsNonValid (List<UUID> winnersIds, UUID raffle) {
    Single<PgRowSet> rows = RxUtils.executeTx(
      client,
      this.updateWinners(winnersIds),
      this.listWinners(raffle)
    )

    return rows
      .flatMapObservable(RxUtils.&toObservable)
      .map(this.&toWinner)
  }

  Closure<Single<PgRowSet>> updateWinners(List<UUID> winnersIds) {
    return { PgConnection conn ->
      String placeHolders = (1..winnersIds.size())
        .collect({ Integer i -> "\$$i" })
        .join(",")

      String updateQuery = """
      UPDATE winners SET
        isValid = false
      WHERE
        id in ($placeHolders)
      """

      return conn
        .rxPreparedQuery(updateQuery, Tuple.of(winnersIds))
    } as Closure<Single<PgRowSet>>
  }

  Closure<Single<PgRowSet>> listWinners(UUID raffle) {
    return { PgConnection conn ->
      String query = '''
          SELECT
            w.raffleId,
            w.ordering,
            w.id,
            p.social,
            p.nick,
            w.isValid,
            w.createdAt
          FROM winners w JOIN participants p ON
            w.participantId = p.id
          WHERE w.raffleId = ?
        '''

      return conn
        .rxPreparedQuery(query, Tuple.of(raffle))
    } as Closure<Single<PgRowSet>>
  }

  @Override
  Single<Result> checkRaffleResult(UUID id, String userHash) {
    String query = '''
      SELECT
        p.hash,
        p.social
      FROM winners w JOIN participants p ON
        w.participantId = p.id
      WHERE w.raffleId = ?
    '''

    return client
      .rxPreparedQuery(query, Tuple.of(id))
      .map(this.&toWinnerList)
      .map({ List<Winner> winners ->
      Boolean didIWin = winners.any(this.&isSameHash)
      return new Result(
        winners: winners.collect(this.&toWinner),
        didIWin: didIWin,
        raffleId: id,
      )
    })
  }

  private Boolean isSameHash(String hash) {
    return { Winner w ->
      return w.hash.contains(hash)
    }
  }

  private List<Winner> toWinnerList(PgRowSet rowSet) {
    return RxUtils
      .toStream(rowSet)
      .map(this.&toWinner)
      .collect(Collectors.toList())
  }

  private Winner toWinner(Row row) {
    return new Winner(
      id: row.delegate.getUUID('id'),
      ordering: row.getInteger('ordering'),
      social: row.getString('social'),
      hash: row.getString('hash'),
      nick: row.getString('nick'),
      raffleId: row.delegate.getUUID('raffleid'),
      createdAt: row.delegate.getLocalDateTime('createdAt'),
      isValid:row.getBoolean('isvalid')
    )
  }
}
