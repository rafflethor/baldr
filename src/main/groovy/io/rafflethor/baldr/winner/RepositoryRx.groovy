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
        saveAll(tx, winners).flatMapCompletable({ PgRowSet rowSet ->
          tx.rxCommit()
        })
      })

    return completable.andThen(findAllWinners(raffle))
  }

  Single<PgRowSet> saveAll(PgTransaction tx, List<WinnerInput> winners) {
    String query = 'INSERT INTO winners (id, participantid, raffleid) VALUES ($1, $2, $3) ON CONFLICT (id) DO NOTHING;'

    return winners
      .stream()
      .map({ WinnerInput p ->
        UUID uuid = UUID.randomUUID()
        tx.rxPreparedQuery(
          query,
          Tuple.of(uuid, p.participantId, p.raffleId))
      }).reduce(this.&getLastElement)
        .orElse(Single.error(new NoSuchElementException()))
  }

  private Single<PgRowSet> getLastElement(Single<PgRowSet> first, Single<PgRowSet> second) {
    return first.flatMap({ PgRowSet l -> second })
  }

  @Override
  Observable<Winner> findAllWinners(UUID raffle) {
    return client
      .rxPreparedQuery(findAllQuery, Tuple.of(raffle))
      .flatMapObservable(RxUtils.&toObservable)
      .map(this.&toWinner)
  }

  @Override
  Observable<Winner> markWinnersAsNonValid (List<UUID> winnersIds, UUID raffle) {
    String updateQuery = getUpdateQuery(winnersIds)
    Completable transaction = client
      .rxBegin()
      .flatMapCompletable({ PgTransaction tx ->
      tx
        .rxPreparedQuery(updateQuery, Tuple.of(winnersIds))
        .flatMapCompletable({ result -> tx.commit() })
      })

    return transaction.andThen(findAllWinners(raffle))
  }

  String getFindAllQuery() {
    return '''
      SELECT
        w.raffleid,
        w.ordering,
        w.id,
        p.social,
        p.nick,
        w.isvalid,
        w.createdat
      FROM winners w JOIN participants p ON
        w.participantid = p.id
      WHERE w.raffleid = $1
    '''
  }

  String getUpdateQuery(List<UUID> winnersIds) {
    String placeHolders = (1..winnersIds.size())
      .collect({ Integer i -> "\$$i" })
      .join(",")

    String updateQuery = """
      UPDATE winners SET
        isValid = false
      WHERE
        id in ($placeHolders)
    """

    return updateQuery
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
      .map(this.getResult(id))
  }

  private Closure<Result> getResult(UUID id) {
    return { List<Winner> winners ->
      Boolean didIWin = winners.any(this.&isSameHash)

      return new Result(
        winners: winners.collect(this.&toWinner),
        didIWin: didIWin,
        raffleId: id,
      )
    }
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
