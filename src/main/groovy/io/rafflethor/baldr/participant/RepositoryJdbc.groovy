package io.rafflethor.baldr.participant

import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton
import groovy.transform.CompileDynamic
import groovy.sql.Sql
import groovy.sql.GroovyRowResult

/**
 * Database access implementation for participants using Groovy jdbc
 * api
 *
 * @since 0.1.0
 */
@Singleton
class RepositoryJdbc implements Repository {

  static final List<String> FIELDS = [
    'id',
    'raffleId',
    'social',
    'nick',
    'hash',
  ]

  /**
   *
   * @since 0.1.0
   */
  @Inject
  Sql sql

  @Override
  Observable<Participant> findAllParticipants(UUID raffle) {
    String query = 'SELECT * FROM participants WHERE raffleId = ?'
    List<Participant> participants = sql
      .rows(query, [raffle] as List<Object>)
      .collect(this.&toParticipant)

    return Observable.fromIterable(participants)
  }

  @Override
  Observable<Participant> findAllPossibleWinners(UUID raffle, Long noWinners) {
    String query = '''
      SELECT
        p.*
      FROM participants p JOIN winners w ON
        p.id != w.participantId
      WHERE
        p.raffleId = ?
      ORDER BY random() LIMIT ?
    '''

    List<Participant> participants = sql
      .rows(query, [raffle, noWinners] as List<Object>)
      .collect(this.&toParticipant)

    return Observable.fromIterable(participants)
  }

  @CompileDynamic
  private Participant toParticipant(GroovyRowResult row) {
    return new Participant(row.subMap(FIELDS))
  }
}
