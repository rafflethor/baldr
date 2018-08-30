package io.rafflethor.baldr.participant

import groovy.transform.Immutable

/**
 * A given raffle participants. Winners will be chosen from the list
 * of participants
 *
 * @since 0.1.0
 */
@Immutable
class Participant {

  /**
   * Database id
   *
   * @since 0.1.0
   */
  UUID id

  /**
   * Raffle id the participant belongs to
   *
   * @since 0.1.0
   */
  UUID raffleId

  /**
   * Social identifier of the participant
   *
   * @since 0.1.0
   */
  String social

  /**
   * Custom name used by the user
   *
   * @since 0.1.0
   */
  String nick

  /**
   * Hash created from some of the data from the participant
   *
   * @since 0.1.0
   */
  String hash
}
