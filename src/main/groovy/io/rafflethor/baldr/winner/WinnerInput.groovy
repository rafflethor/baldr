package io.rafflethor.baldr.winner

import groovy.transform.Immutable

/**
 * Tuple to create a winner from a given participant and a given
 * raffle
 *
 * @since 0.1.0
 */
@Immutable
class WinnerInput {
  /**
   * Id of the Participant becoming a winner
   *
   * @since 0.1.0
   */
  UUID participantId

  /**
   * The id of the raffle
   *
   * @since 0.1.0
   */
  UUID raffleId
}
