package io.rafflethor.baldr.raffle

import io.rafflethor.baldr.db.Pagination

/**
 * Repository to get raffles
 *
 * @since 0.1.0
 */
interface Repository {

  /**
   * Lists all raffles
   *
   * @param pagination
   * @param user
   * @return
   * @since 0.1.0
   */
  List<Raffle> listAll(Pagination pagination, UUID user)

  /**
   * Finds a given {@link Raffle} by its id
   *
   * @param id the identifier of the raffle
   * @param user who created the raffle
   * @since 0.1.0
   */
  Raffle findById(UUID id, UUID user)

  /**
   * Finds a given {@link Raffle} by the spot assigned to it
   *
   * @param spotId the spot used by the {@link Raffle} we would like to get
   * @return the {@link Raffle} using the spot id passed as argument
   * @since 0.1.0
   */
  Raffle findBySpot(String spotId)

  /**
   * Saves or updates a given {@link Raffle}
   *
   * @param raffle the raffle we would like to save
   * @param user user saving the raffle
   * @return the saved {@link Raffle}
   * @since 0.1.0
   */
  Raffle upsert(Raffle raffle, UUID user)

  /**
   * Marks a raffle as ready to start (WAITING). Only the user who
   * created the raffle can mark the raffle as WAITING.
   *
   * @param id the id of the {@link Raffle}
   * @param user the user who created the raffle
   * @return the updated {@link Raffle}
   * @since 0.1.0
   */
  Raffle markWaiting(UUID id, UUID user)

  /**
   * Marks a raffle as LIVE
   *
   * @param id the id of the {@link Raffle}
   * @return the updated {@link Raffle}
   * @since 0.1.0
   */
  Raffle markLive(UUID id)

  /**
   * Marks a raffle as FINISHED
   *
   * @param id the id of the {@link Raffle}
   * @return the updated {@link Raffle}
   * @since 0.1.0
   */
  Raffle markFinished(UUID id)

  /**
   * Retrieves the first {@link Raffle} with the WAITING status
   *
   * @return an instance of {@link Raffle}
   * @since 0.1.0
   */
  Raffle findWaiting()

  /**
   * Deletes the {@link Raffle} with the id passed as argument
   *
   * @param id the id of the {@link Raffle} we would like to delete
   * @param user the user who created the raffle
   * @return true if the {@link Raffle} has been removed, false otherwise
   * @since 0.1.0
   */
  Boolean delete(UUID id, UUID user)
}
