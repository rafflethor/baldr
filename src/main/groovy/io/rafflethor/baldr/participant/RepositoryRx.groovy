package io.rafflethor.baldr.participant

import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.Observable
import io.reactiverse.reactivex.pgclient.Row
import io.reactiverse.reactivex.pgclient.PgPool
import io.reactiverse.reactivex.pgclient.Tuple

import io.rafflethor.baldr.db.RxUtils

/**
 * Database access implementation for participants using Groovy jdbc
 * api
 *
 * @since 0.1.0
 */
@Singleton
class RepositoryRx implements Repository {

  /**
   * Reactive database connection
   *
   * @since 0.1.0
   */
  @Inject
  PgPool client

  @Override
  Observable<Participant> findAllParticipants(UUID raffle) {
    String query = 'SELECT * FROM participants WHERE raffleId = $1'

    return client
      .rxPreparedQuery(query, Tuple.of(raffle))
      .flatMapObservable(RxUtils.&toObservable)
      .map(this.&toParticipant)
  }

  private Participant toParticipant(Row row) {
    UUID id = row.delegate.getUUID("id")
    UUID raffleId = row.delegate.getUUID("raffleid")

    return new Participant(
      id: id,
      raffleId: raffleId,
      social: row.getString("social"),
      nick: row.getString("nick"),
      hash: row.getString("hash")
    )
  }
}
