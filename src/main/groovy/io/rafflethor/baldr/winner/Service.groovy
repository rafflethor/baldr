package io.rafflethor.baldr.winner

import io.reactivex.Observable

interface Service {
  /**
   * Emits a maximun number of {@link Winner} of a given raffle
   *
   * @param raffle id of the raffle to take the winners from
   * @param noWinners number of winners to take
   * @return an {@link Observable} of {@link Winner} instances
   * @since 0.1.0
   */
  Observable<Winner> findAllWinners(UUID raffle, Integer noWinners)
}
