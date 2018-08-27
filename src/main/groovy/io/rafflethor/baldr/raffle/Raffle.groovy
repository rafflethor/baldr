package io.rafflethor.baldr.raffle

import groovy.transform.ToString
import java.time.LocalDateTime

/**
 * Base class for all raffles
 *
 * @since 0.1.0
 */
@ToString
class Raffle {

  /**
   * Raffle identifier
   *
   * @since 0.1.0
   */
  UUID id

  /**
   * Organization the raffle belongs to
   *
   * @since 0.1.0
   */
  UUID organizationId

  /**
   * The name of the raffle
   *
   * @since 0.1.0
   */
  String name

  /**
   * How many winners will this raffle have
   *
   * @since 0.1.0
   */
  Integer noWinners

  /**
   * Whether to prevent or not previous winners to participate in
   * this raffle
   *
   * @since 0.1.0
   */
  Boolean preventPreviousWinners

  /**
   * Type of the {@link Raffle}
   *
   * @since 0.1.0
   */
  String type

  /**
   * Whether the current raffle is still going or has finished
   *
   * @since 0.1.0
   */
  String status

  /**
   * {@link Raffle} related information in JSON
   *
   * @since 0.1.0
   */
  String payload

  /**
   *@ since 0.1.0
   */
  LocalDateTime since

  /**
   *@ since 0.1.0
   */
  LocalDateTime until
}
