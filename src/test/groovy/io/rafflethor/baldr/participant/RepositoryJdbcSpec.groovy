package io.rafflethor.baldr.participant

import io.reactivex.Observable
import org.junit.ClassRule

import spock.lang.Shared
import spock.lang.AutoCleanup
import spock.lang.Specification
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.testcontainers.containers.PostgreSQLContainer

/**
 * @since 0.1.0
 */
class RepositoryJdbcSpec extends Specification {

  @ClassRule
  static PostgreSQLContainer postgres = new PostgreSQLContainer()

  @Shared
  @AutoCleanup
  EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

  void 'find all possible participants'() {
    given: 'a populated database'
    ApplicationContext ctx = embeddedServer.applicationContext
    Repository repository = ctx.getBean(Repository)

    when: 'asking for a given raffle winners'
    UUID id = UUID.fromString('cc00c00e-6a42-11e8-adc0-fa7ae01bbebc')
    Observable<Participant> participants = repository
      .findAllParticipants(id)

    then: 'we should get 4 winners'
    participants
      .test()
      .assertNoErrors()
      .assertValueCount(2)
  }

  void 'find all possible winners'() {
    given: 'a populated database'
    ApplicationContext ctx = embeddedServer.applicationContext
    Repository repository = ctx.getBean(Repository)

    when: 'asking for a given raffle winners'
    UUID id = UUID.fromString('cc00c00e-6a42-11e8-adc0-fa7ae01bbebc')
    Observable<Participant> participants = repository
      .findAllPossibleWinners(id, 1)

    then: 'we should get the expected number of results'
    participants
      .test()
      .assertNoErrors()
      .assertValueCount(1)
  }
}
