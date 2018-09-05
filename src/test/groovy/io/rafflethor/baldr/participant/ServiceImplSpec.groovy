package io.rafflethor.baldr.participant

import io.reactivex.Observable
import spock.lang.Specification

/**
 * Tests {@link ServiceImpl}
 *
 * @since 0.1.0
 */
class ServiceImplSpec extends Specification {

  void 'find all participants with result' () {
    given: 'the mocked repository'
    Participant sample = Fixtures.createSample()
    Repository repository = Mock(Repository) {
      findAllParticipants(_ as UUID) >> {
        return Observable.fromIterable(sample.collect())
      }
    }

    and: 'an instance of the service'
    Service service = new ServiceImpl(repository: repository)

    when: 'getting all participants'
    UUID id = UUID.randomUUID()
    Observable<Participant> participants = service.findAllParticipants(id)

    then: 'we should get an expected result'
    participants
      .test()
      .assertNoErrors()
      .assertValue(sample)
      .assertValueCount(1)
  }

  void 'find all participants with no result' () {
    given: 'the mocked repository'
    Repository repository = Mock(Repository) {
      findAllParticipants(_ as UUID) >> {
        return Observable.fromIterable([])
      }
    }

    and: 'an instance of the service'
    Service service = new ServiceImpl(repository: repository)

    when: 'getting all participants'
    UUID id = UUID.randomUUID()
    Observable<Participant> participants = service.findAllParticipants(id)

    then: 'we should get an expected result'
    participants
      .test()
      .assertNoErrors()
      .assertValueCount(0)
  }
}
