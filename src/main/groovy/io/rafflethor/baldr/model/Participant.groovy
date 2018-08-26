package io.rafflethor.baldr.model

import groovy.transform.Immutable

/**
 * A given raffle participants. Winners will be chosen from the list
 * of participants
 *
 * @since 0.1.0
 */
@Immutable
class Participant {
  UUID id
  UUID raffleId
  String social
  String nick
  String hash
}
