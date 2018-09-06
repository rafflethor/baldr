package io.rafflethor.baldr.winner

import io.reactivex.Observable
import org.junit.Rule

import spock.lang.Specification
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.context.ApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

/**
 * @since 0.1.0
 */
class RepositoryRxSpec extends Specification {

  static final String DB = "rafflethor-docker-rafflethor.bintray.io/db:1.0.0-alpine"

  @Rule
  PostgreSQLContainer postgres = new PostgreSQLContainer(DB)

  void 'find all possible winners'() {
    given: 'an instance of the server'
    EmbeddedServer server = ApplicationContext.run(EmbeddedServer)
    Repository repository = server
      .applicationContext
      .getBean(Repository)

    when: 'asking for a given raffle winners'
    UUID id = UUID.randomUUID()
    Observable<Winner> winners = repository.findAllWinners(id)

    then: 'we should get 4 winners'
    winners
      .test()
      .assertNoErrors()
      .assertValueCount(4)
  }
}
