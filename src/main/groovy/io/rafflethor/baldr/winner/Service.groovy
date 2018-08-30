package io.rafflethor.baldr.winner

import io.reactivex.Observable

/**
 * Service contract responsible for managing winners
 *
 * @since 0.1.0
 */
interface Service {
  /**
   * Emits a maximun number of {@link Winner} of a given raffle
   *
   * @param raffle id of the raffle to take the winners from
   * @param noWinners number of winners to take
   * @return an {@link Observable} of {@link Winner} instances
   * @since 0.1.0
   */
  Observable<Winner> findAllWinners(UUID raffle, Integer noWinners)

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
  Observable<Winner> markWinnersAsNonValid(List<UUID> winners, UUID raffle)

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
  Observable<Result> checkRaffleResult(UUID raffle, String userHash)
}
