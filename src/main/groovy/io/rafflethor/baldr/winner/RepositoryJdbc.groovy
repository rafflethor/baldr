package io.rafflethor.baldr.winner

import javax.inject.Inject
import javax.inject.Singleton
import java.util.stream.Collectors

import io.reactivex.Single
import io.reactivex.Observable
import io.reactivex.Completable

import groovy.sql.Sql
import groovy.sql.GroovyRowResult

import io.rafflethor.baldr.db.Utils

/**
 * Database access repository implementation using plain Groovy jdbc
 *
 * @since 0.1.0
 */
@Singleton
class RepositoryJdbc implements Repository {

  static final List<String> FIELDS = [
    'id',
    'ordering',
    'social',
    'hash',
    'nick',
    'raffleId',
    'createdAt',
    'isValid',
  ]

  @Inject
  Sql sql

  @Override
  Observable<Winner> saveWinners(UUID raffle, List<WinnerInput> winners) {
    List<Winner> result = Utils.executeTx(sql, { Sql sql ->
      //saveAll(winners)
    }) as List<Winner>

    return Completable
      .fromObservable(Observable.fromIterable(result))
      .andThen(findAllWinners(raffle))
  }

  //  Single<PgRowSet> saveAll(PgTransaction tx, List<WinnerInput> winners) {
  //    String query = 'INSERT INTO winners (id, participantid, raffleid)
  // VALUES ($1, $2, $3) ON CONFLICT (id) DO NOTHING;'
  //
  //    return winners
  //      .stream()
  //      .map({ WinnerInput p ->
  //        UUID uuid = UUID.randomUUID()
  //        tx.rxPreparedQuery(
  //          query,
  //          Tuple.of(uuid, p.participantId, p.raffleId))
  //      }).reduce(this.&getLastElement)
  //        .orElse(Single.error(new NoSuchElementException()))
  //  }

  //  private Single<PgRowSet> getLastElement(Single<PgRowSet> first, Single<PgRowSet> second) {
  //    return first.flatMap({ PgRowSet l -> second })
  //  }

  @Override
  Observable<Winner> findAllWinners(UUID raffle) {
    List<Winner> winners = sql
      .rows(findAllQuery, [raffle] as List<Object>)
      .collect(this.&toWinner)

    return Observable.fromIterable(winners)
  }

  @Override
  Observable<Winner> markWinnersAsNonValid (List<UUID> winnersIds, UUID raffle) {
    int noRows = Utils.executeTx(sql, { Sql sql ->
      sql.executeUpdate(getUpdateQuery(winnersIds), winnersIds as List<Object>)
    })

    return Completable
      .fromObservable(Observable.just(noRows))
      .andThen(findAllWinners(raffle))
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
      WHERE w.raffleid = ?
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

    List<Winner> winners = sql
      .rows(query, [id] as List<Object>)
      .collect(this.&toWinner)

    return Single.just(getResult(id)(winners))
  }

  private Closure<Result> getResult(UUID id) {
    return { List<Winner> winners ->
      Boolean didIWin = winners.any(this.&isSameHash)

      return new Result(
        winners: winners,
        didIWin: didIWin,
        raffleId: id,
      )
    }
  }
//
//  private Boolean isSameHash(String hash) {
//    return { Winner w ->
//      return w.hash.contains(hash)
//    }
//  }
//
//  private List<Winner> toWinnerList(PgRowSet rowSet) {
//    return RxUtils
//      .toStream(rowSet)
//      .map(this.&toWinner)
//      .collect(Collectors.toList())
//  }

  @groovy.transform.CompileDynamic
  private Winner toWinner(GroovyRowResult row) {
    return new Winner(row.subMap(FIELDS))
  }
}
