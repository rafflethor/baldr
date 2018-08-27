package io.rafflethor.baldr.participant

import javax.inject.Inject
import javax.inject.Singleton

import groovy.sql.Sql
import groovy.sql.GroovyRowResult
import groovy.transform.CompileDynamic

@Singleton
class RepositoryJdbc implements Repository {

  @Inject
  Sql sql

  @Override
  List<Participant> findAllParticipants(UUID raffle) {
    String query = '''
      SELECT * FROM participants WHERE raffleId = ?
    '''

    return sql
      .rows(query, raffle)
      .collect(this.&toParticipant)
  }

  @CompileDynamic
  @SuppressWarnings('DuplicateStringLiteral')
  private Participant toParticipant(GroovyRowResult row) {
    List<String> fields = [
      'id',
      'raffleId',
      'social',
      'nick',
      'hash',
    ]

    return new Participant(row.subMap(fields))
  }
}
