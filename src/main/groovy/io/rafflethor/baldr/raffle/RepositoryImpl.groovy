package io.rafflethor.baldr.raffle

import static io.rafflethor.baldr.db.Utils.toTimestamp

import javax.inject.Inject

import groovy.sql.Sql
import groovy.sql.GroovyRowResult

import io.rafflethor.baldr.db.Utils
import io.rafflethor.baldr.db.Pagination

/**
 * Repository to get raffles of twitter nature
 *
 * @since 0.1.0
 */
class RepositoryImpl implements Repository {

  /**
   * @since 0.1.0
   */
  @Inject
  Sql sql

  @Override
  List<Raffle> listAll(Pagination pagination, UUID user) {
    String query = '''
          SELECT * FROM
            raffles
          WHERE
            createdBy = ?
          ORDER BY
            createdAt DESC
        '''

    return sql
      .rows(query, [user] as List<Object>, pagination.offset, pagination.max)
      .collect(Raffles.&toRaffle)
  }

  @Override
  Raffle findById(UUID id, UUID user) {
    Raffle raffle = sql
      .rows("select * from raffles where id = :id and createdBy = :createdBy", id: id, createdBy: user)
      .collect(Raffles.&toRaffle)
      .find()

    return raffle
  }

  // TODO see if this has to be deleted
  // it doesnt use any user
  // it only should be accessed by app processes
  private Raffle findByIdUnsecured(UUID id) {
    Raffle raffle = sql
      .rows("select * from raffles where id = :id", id: id)
      .collect(Raffles.&toRaffle)
      .find()

    return raffle
  }

  @Override
  Raffle upsert(Raffle raffle, UUID user) {
    raffle.id = raffle.id ?: Utils.generateUUID()

    List<GroovyRowResult> rows = sql.executeInsert('''
          INSERT INTO raffles
            (id,
             name,
             type,
             noWinners,
             preventPreviousWinners,
             organizationId,
             since,
             until,
             payload,
             createdBy)
          VALUES
            (:id,
             :name,
             :type,
             :noWinners,
             :preventPreviousWinners,
             :organizationId,
             :since,
             :until,
             :payload::JSON,
             :createdBy)
          ON CONFLICT (id) DO UPDATE SET
             name = :name,
             type = :type,
             noWinners = :noWinners,
             preventPreviousWinners = :preventPreviousWinners,
             since = :since,
             until = :until,
             payload = :payload::JSON
          WHERE
             raffles.createdBy = :createdBy
          RETURNING id, name, type, noWinners, preventPreviousWinners, organizationId, since, until, payload, status
        ''', [
        id: raffle.id,
        name: raffle.name,
        type: raffle.type,
        noWinners: raffle.noWinners,
        preventPreviousWinners: raffle.preventPreviousWinners,
        organizationId: raffle.organizationId,
        since: toTimestamp(raffle.since),
        until: toTimestamp(raffle.until),
        payload: raffle.payload,
        createdBy: user,
      ]) as List<GroovyRowResult>

    return rows
      .grep()
      .collect(Raffles.&toRaffle)
      .find()
  }

  @Override
  Raffle findRaffleFromSpot(String spotId) {
    UUID uuid = sql
      .firstRow("SELECT raffleId  as id FROM raffle_spot WHERE id = ?", spotId)
      .id as UUID

    return Optional
      .ofNullable(uuid)
      .map(this.&findById)
      .orElse(null) as Raffle
  }

  @Override
  Raffle markRaffleWaiting(UUID id, UUID user) {
    sql.executeUpdate("UPDATE raffles SET status = 'WAITING' WHERE id = ? and createdBy = ?", id, user)

    return findById(id, user)
  }

  @Override
  Raffle markRaffleLive(UUID id) {
    sql.executeUpdate("UPDATE raffles SET status = 'LIVE' WHERE id = ?", id)

    return findByIdUnsecured(id)
  }

  @Override
  Raffle markRaffleFinished(UUID id) {
    sql.executeUpdate("UPDATE raffles SET status = 'FINISHED' WHERE id = ?", id)

    return findByIdUnsecured(id)
  }

  @Override
  Raffle findWaitingRaffle() {
    return Raffles.toRaffle(sql.firstRow("SELECT * FROM raffles WHERE status = 'WAITING'"))
  }

  @Override
  Boolean delete(UUID id, UUID user) {
    Integer deletedRows = 0

    sql.withTransaction {
      deletedRows = sql.executeUpdate("DELETE FROM raffles WHERE id = ? AND createdBy = ?", id, user)
    }

    return deletedRows == 1
  }
}
