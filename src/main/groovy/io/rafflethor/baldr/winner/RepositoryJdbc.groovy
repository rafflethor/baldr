package io.rafflethor.baldr.winner

import javax.inject.Inject
import javax.inject.Singleton

import groovy.sql.Sql
import groovy.sql.GroovyRowResult
import groovy.transform.CompileDynamic

import io.rafflethor.baldr.db.Utils

/**
 * Database access repository implementation using plain Groovy jdbc
 *
 * @since 0.1.0
 */
@Singleton
class RepositoryJdbc implements Repository {

  @Inject
  Sql sql

  @Override
  List<Winner> saveWinners(UUID raffle, List<WinnerInput> winners) {
    String query = '''
      INSERT INTO winners (id, participantId, raffleId) VALUES (?, ?, ?)
    '''

    sql.withTransaction {
      winners.each { p ->
        UUID uuid = Utils.generateUUID()

        sql.executeInsert(
          query,
          uuid,
          p.participantId,
          p.raffleId)
      }
    }

    return findAllWinners(raffle)
  }

  @Override
  List<Winner> findAllWinners(UUID raffle) {
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

    return sql
      .rows(query, raffle)
      .collect(this.&toWinner)
  }

  @Override
  List<Winner> markWinnersAsNonValid (List<UUID> winnersIds, UUID raffle) {
    String placeHolders = (1..winnersIds.size())
      .collect { "?" }
      .join(",")

    String updateQuery = """
          UPDATE winners SET
            isValid = false
          WHERE
            id in ($placeHolders)
        """

    return Utils.executeTx(sql) {
      sql.executeUpdate(updateQuery, winnersIds as List<Object>)
      findAllWinners(raffle)
    }
  }

  @Override
  Result checkRaffleResult(UUID id, String userHash) {
    List<GroovyRowResult> winners = sql.rows('''
          SELECT
            p.hash,
            p.email
          FROM winners w JOIN participants p ON
            w.participantId = p.id
          WHERE w.raffleId = ?
        ''', id)

    Boolean didIWin = userHash in winners*.hash

    return new Result(
      winners: winners,
      didIWin: didIWin,
      raffle: id,
    )
  }

  @CompileDynamic
  @SuppressWarnings('DuplicateStringLiteral')
  private Winner toWinner(GroovyRowResult row) {
    List<String> fields = [
      'id',
      'ordering',
      'social',
      'nick',
      'raffleId',
      'createdAt',
      'isValid',
    ]

    return new Winner(row.subMap(fields))
  }
}
