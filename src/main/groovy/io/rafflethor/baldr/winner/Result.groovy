package io.rafflethor.baldr.winner

import groovy.transform.Immutable

/**
 * Holds the winner information about a finished raffle
 *
 * @since 0.1.0
 */
@Immutable
class Result {
  /**
   * The winners of the raffle
   *
   * @since 0.1.0
   */
  List<Winner> winners

  /**
   * Tells whether I'm among the winners or not
   *
   * @since 0.1.0
   */
  Boolean didIWin

  /**
   * The id of the raffle
   *
   * @since 0.1.0
   */
  UUID raffleId
}
