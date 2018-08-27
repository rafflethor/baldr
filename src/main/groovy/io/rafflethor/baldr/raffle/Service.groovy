package io.rafflethor.baldr.raffle

import io.reactivex.Observable
import io.rafflethor.baldr.db.Pagination

/**
 * Service linked to GraphQL queries and mutations regarding raffles
 *
 * @since 0.1.0
 */
interface Service {

  /**
   * Lists all available raffles
   *
   * @param env data execution environment
   * @return a {@link CompletableFuture} carrying a list of {@link Raffle}
   * @since 0.1.0
   */
  Observable<Raffle> listAllRafflesByUser(Pagination pagination, UUID user)

  /**
   * Saves a new {@link Raffle} with the content found in the
   * incoming {@link DataFetchingEnvironment}
   *
   * @param env data execution environment
   * @return the saved {@link Raffle} wrapped in a {@link Observable}
   * @since 0.1.0
   */
  Observable<Raffle> save(Raffle raffle)

  /**
   * Finds a {@link Raffle} by its id
   *
   * @param env data execution environment
   * @return
   * @since 0.1.0
   */
  Observable<Raffle> findById(UUID raffle, UUID user)

  /**
   * Marks a {@link Raffle} as it's been started
   *
   * @param env data execution environment
   * @return the {@link Raffle} initiated
   * @since 0.1.0
   */
  Observable<Raffle> startRaffle(UUID raffle, UUID user)

  /**
   * Deletes a specific {@link Raffle} by its id
   *
   * @param env data execution environment
   * @return a map containing information about the deletion process
   * @since 0.1.0
   */
  Observable<Map> delete(UUID id, UUID user)

  /**
   * Updates a specific {@link Raffle}
   *
   * @param env data execution environment
   * @return the updated {@link Raffle}
   * @since 0.1.0
   */
  Observable<Raffle> update(Raffle raffle, UUID user)
}
