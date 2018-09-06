package io.rafflethor.baldr.winner

import io.reactivex.Observable
import spock.lang.Specification

import io.rafflethor.baldr.participant.Participant
import io.rafflethor.baldr.participant.Repository as ParticipantRepository

/**
 * Tests {@link ServiceImpl}
 *
 * @since 0.1.0
 */
class ServiceImplSpec extends Specification {

  void 'find all winners with result' () {
    given: 'a couple of sample data'
    Participant participant = Fixtures.PARTICIPANTS.createSample()
    Winner winner = Fixtures.WINNERS.createSample()

    and: 'the mocked repository'
    Repository repository = Mock(Repository) {
      saveWinners(_ as UUID, _ as List<WinnerInput>) >> {
        return Observable.fromIterable(winner.collect())
      }
    }

    ParticipantRepository participantRepository = Mock(ParticipantRepository) {
      findAllPossibleWinners(_ as UUID, _ as Integer) >> {
        return Observable.fromIterable(participant.collect())
      }
    }

    and: 'an instance of the service'
    Service service = new ServiceImpl(
      repository: repository,
      participantRepository: participantRepository
    )

    when: 'asking for all winners'
    UUID id = UUID.randomUUID()
    Observable<Winner> winners = service.findAllWinners(id, 1)

    then: 'we should'
    winners
      .test()
      .assertNoErrors()
      .assertValue(winner)
      .assertValueCount(1)
  }
}
