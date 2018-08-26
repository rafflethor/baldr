package io.rafflethor.baldr

import io.rafflethor.baldr.model.Winner
import io.rafflethor.baldr.model.WinnerInput
import io.rafflethor.baldr.model.Participant

/**
 * Database access to manage participants and winners
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
   * Retrieves a map containing information about the winners of a
   * given raffle including whether the user with the hash passed as
   * argument has won or not
   *
   * @param raffle the id of the raffle
   * @param userHash hash of a user
   * @return information about the raffle winners
   * @since 0.1.0
   */
  Map checkRaffleResult(UUID raffle, String userHash)

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
  List<Winner> markWinnersAsNonValid (List<UUID> winners, UUID raffle)
}
