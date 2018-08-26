package io.rafflethor.baldr

import io.reactivex.Observable
import io.rafflethor.baldr.model.Winner
import io.rafflethor.baldr.model.Participant

/**
 * Service to handle participants and winners
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

  /**
   * Emits a maximun number of {@link Winner} of a given raffle
   *
   * @param raffle id of the raffle to take the winners from
   * @param noWinners number of winners to take
   * @return an {@link Observable} of {@link Winner} instances
   * @since 0.1.0
   */
  Observable<Winner> findAllWinners(UUID raffle, Integer noWinners)
}
