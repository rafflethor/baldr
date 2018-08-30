package io.rafflethor.baldr.participant

import io.reactivex.Observable

/**
 * Service contract responsible for managing participants
 *
 * @since 0.1.0
 */
interface Service {

  /**
   * @param raffle id of the raffle we want the participants from
   * @return an {@link Observable}
   * @since 0.1.0
   */
  Observable<Participant> findAllParticipants(UUID raffle)
}
