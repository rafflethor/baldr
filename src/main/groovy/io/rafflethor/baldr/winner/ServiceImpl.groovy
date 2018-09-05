package io.rafflethor.baldr.winner

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Single
import io.reactivex.Observable

import io.rafflethor.baldr.participant.Participant
import io.rafflethor.baldr.participant.Repository as ParticipantRepository

/**
 * Service implementation responsible for managing winners
 *
 * @since 0.1.0
 */
@Singleton
class ServiceImpl implements Service {

  @Inject
  Repository repository

  @Inject
  ParticipantRepository participantRepository

  @Override
  Observable<Winner> findAllWinners(UUID raffle, Integer noWinners) {
    return participantRepository
      .findAllPossibleWinners(raffle, noWinners)
      .map(this.&toWinnerInput)
      .buffer(noWinners)
      .flatMap({ List<WinnerInput> inputs -> repository.saveWinners(raffle, inputs) })
  }

  private WinnerInput toWinnerInput(Participant participant) {
    return new WinnerInput(
      participantId: participant.id,
      raffleId: participant.raffleId
    )
  }

  @Override
  Observable<Winner> markWinnersAsNonValid(List<UUID> winners, UUID raffle) {
    return repository.markWinnersAsNonValid(winners, raffle)
  }

  @Override
  Single<Result> checkRaffleResult(UUID raffle, String userHash) {
    return repository.checkRaffleResult(raffle, userHash)
  }
}
