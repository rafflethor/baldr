package io.rafflethor.baldr.participant

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
  List<Participant> findAllParticipants(UUID raffle)
}
