package io.rafflethor.baldr.winner

import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.Observable

import io.rafflethor.baldr.participant.Participant
import io.rafflethor.baldr.participant.Repository as ParticipantRepository

@Singleton
class ServiceImpl implements Service {

  @Inject
  Repository repository

  @Inject
  ParticipantRepository participantRepository

  @Override
  Observable<Winner> findAllWinners(UUID raffle, Integer noWinners) {
    Integer validWinners = repository
      .findAllWinners(raffle)
      .size()

    List<Participant> participants = participantRepository
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
