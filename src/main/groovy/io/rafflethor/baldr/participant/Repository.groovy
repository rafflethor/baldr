package io.rafflethor.baldr.participant

import io.reactivex.Observable

/**
 * Database access for participants
 *
 * @since 0.1.0
 */
interface Repository {

  /**
   * Finds a random list of participants from a given raffle
   *
   * @param raffle the id of the raffle
   * @return a map containing the information of the winners of a
   * given raffle
   * @since 0.1.0
   */
  Observable<Participant> findAllParticipants(UUID raffle)

  /**
   * Returns a random list of participants that are not winners yet
   *
   * @param raffle raffle the participants belong to
   * @param noWinners number of samples
   * @return a random number of {@link Participant} instances
   * @since 0.1.0
   */
  Observable<Participant> findAllPossibleWinners(UUID raffle, Long noWinners)
}
