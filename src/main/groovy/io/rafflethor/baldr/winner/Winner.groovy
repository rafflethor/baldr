package io.rafflethor.baldr.winner

import java.time.LocalDateTime

/**
 * Represents a potential winner for a given raffle
 *
 * @since 0.1.0
 */
class Winner {
  /**
   * The raffle the winner has been taken from
   *
   * @since 0.1.0
   */
  UUID raffleId

  /**
   * Unique identifier of the winner
   *
   * @since 0.1.0
   */
  UUID id

  String hash

  /**
   * Which position holds when winning
   *
   * @since 0.1.0
   */
  Integer ordering

  /**
   * Human readable name
   *
   * @since 0.1.0
   */
  String nick

  /**
   * Social account used to connect to the raffle
   *
   * @since 0.1.0
   */
  String social

  /**
   * When the winner has been chosen
   *
   * @since 0.1.0
   */
  LocalDateTime createdAt

  /**
   * A given winner could be marked as non valid because several
   * reasons, e.g.:
   *
   * <ul>
   *  <li>Not to be present phisically in the raffle</li>
   *  <li>Not to be able to identify her/him</li>
   *  <li>...</li>
   * </ul>
   * @since 0.1.0
   */
  Boolean isValid;

  void setCreatedAt(java.sql.Timestamp timestamp) {
    this.createdAt = timestamp.toLocalDateTime()
  }
}
