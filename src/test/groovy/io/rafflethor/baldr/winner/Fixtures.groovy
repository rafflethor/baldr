package io.rafflethor.baldr.winner

import io.rafflethor.baldr.participant.Participant

/**
 * Provides sample data for {@link Winner} related tests
 *
 * @since 0.1.0
 */
class Fixtures {

  static final Participants PARTICIPANTS = new Participants()
  static final Winners WINNERS = new Winners()

  static class Participants {
    static Participant createSample() {
      return new Participant(
        id: UUID.randomUUID(),
        raffleId: UUID.randomUUID(),
        social: 'admin@rafflethor.com',
        nick: 'Admin',
        hash: 'dasdfasdfffhashadminasdfa'
      )
    }
  }

  static class Winners {
    /**
     * Creates a random winner sample
     *
     * @return an instance of {@link Winner}
     * @since 0.1.0
     */
    static Winner createSample() {
      return new Winner(
        id: UUID.randomUUID(),
        raffleId: UUID.randomUUID(),
        social: 'admin@rafflethor.com',
        nick: 'Admin'
      )
    }
  }
}
