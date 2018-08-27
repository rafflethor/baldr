package io.rafflethor.baldr.participant

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
