package io.rafflethor.baldr.winner

/**
 * Database access repository contract
 *
 * @since 0.1.0
 */
interface Repository {
  /**
   * Finds all chosen winners for a given raffle
   *
   * @param raffle the raffle we want to get the winners from
   * @return a list of {@link Winner}
   * @since 0.1.0
   */
  List<Winner> findAllWinners(UUID raffle)

  /**
   * @param raffle
   * @param participants
   * @return
   * @since 0.1.0
   */
  List<Winner> saveWinners(UUID raffle, List<WinnerInput> participants)

  /**
   * Marks a list of winners as non valid using their ids. And
   * return the information about all the winners once the update
   * has been done
   *
   * @param winners ids of the non valid winners
   * @param raffle id of the raffle
   * @return a list of the non valid winners
   * @since 0.1.0
   */
  List<Winner> markWinnersAsNonValid(List<UUID> winners, UUID raffle)

  /**
   * Retrieves a map containing information about the winners of a
   * given raffle including whether the user with the hash passed as
   * argument has won or not
   *
   * @param raffle the id of the raffle
   * @param userHash hash of a user
   * @return information about the raffle winners
   * @since 0.1.0
   */
  Result checkRaffleResult(UUID raffle, String userHash)
}
