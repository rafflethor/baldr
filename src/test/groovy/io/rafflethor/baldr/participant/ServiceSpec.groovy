package io.rafflethor.baldr.participant

import java.util.stream.StreamSupport
import io.reactivex.Observable
import spock.lang.Specification

class ServiceSpec extends Specification {

  void 'find all participants' () {
    given: 'the mocked repository'
    Repository repository = Mock(Repository) {
      findAllParticipants(_ as UUID) >> Fixtures.createSample().collect()
    }

    and: 'an instance of the service'
    Service service = new ServiceImpl(repository: repository)

    when: 'getting all participants'
    UUID id = UUID.randomUUID()
    Observable<Participant> participants = service.findAllParticipants(id)
    Spliterator iterator = participants.blockingIterable().spliterator()

    then: 'we should get an expected result'
    StreamSupport.stream(iterator, false).count() == 1
  }
}
