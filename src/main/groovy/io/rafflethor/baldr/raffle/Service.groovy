package io.rafflethor.baldr.raffle

import io.reactivex.Observable
import io.rafflethor.baldr.db.Pagination

/**
 * Manages operations over {@link Raffle} instances
 *
 * @since 0.1.0
 */
interface Service {

  /**
   * Lists all available raffles
   *
   * @return a {@link CompletableFuture} carrying a list of {@link Raffle}
   * @since 0.1.0
   */
  Observable<Raffle> listAllByUser(Pagination pagination, UUID user)

  /**
   * Saves a new {@link Raffle} with the content found in the
   * incoming {@link DataFetchingEnvironment}
   *
   * @return the saved {@link Raffle} wrapped in a {@link Observable}
   * @since 0.1.0
   */
  Observable<Raffle> save(Raffle raffle, UUID user)

  /**
   * Finds a {@link Raffle} by its id
   *
   * @return
   * @since 0.1.0
   */
  Observable<Raffle> findById(UUID raffle, UUID user)

  /**
   * Marks a {@link Raffle} as it's been started
   *
   * @return the {@link Raffle} initiated
   * @since 0.1.0
   */
  Observable<Raffle> start(UUID raffle, UUID user)

  /**
   * Deletes a specific {@link Raffle} by its id
   *
   * @return a map containing information about the deletion process
   * @since 0.1.0
   */
  Observable<Boolean> delete(UUID id, UUID user)
}
