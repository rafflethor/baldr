package io.rafflethor.baldr.participant

/**
 * Provides sample data for participant related tests
 *
 * @since 0.1.0
 */
class Fixtures {

  /**
   * Creates a random participant sample
   *
   * @return an instance of {@link Participant}
   * @since 0.1.0
   */
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
