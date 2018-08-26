package io.rafflethor.baldr

import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.Observable

import io.rafflethor.baldr.model.Winner
import io.rafflethor.baldr.model.WinnerInput
import io.rafflethor.baldr.model.Participant

/**
 * @since 0.1.0
 */
@Singleton
class ServiceImpl implements Service {

  /**
   * @since 0.1.0
   */
  @Inject
  Repository repository

  @Override
  Observable<Participant> findAllParticipants(UUID raffle) {
    return Observable.fromIterable(repository.findAllParticipants(raffle))
  }

  @Override
  Observable<Winner> findAllWinners(UUID raffle, Integer noWinners) {
    Integer validWinners = repository
      .findAllWinners(raffle)
      .size()

    List<Participant> participants = repository
      .findAllParticipants(raffle)

    Collections.shuffle(participants)

    Integer howMany = noWinners - validWinners
    List<WinnerInput> winners = participants
      .take(howMany)
      .collect(this.&toWinnerInput)

    repository.saveWinners(raffle, winners)
    return Observable.fromIterable(repository.findAllWinners(raffle))
  }

  private WinnerInput toWinnerInput(Participant participant) {
    return new WinnerInput(
      participantId: participant.id,
      raffleId: participant.raffleId
    )
  }
}
