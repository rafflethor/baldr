package io.rafflethor.baldr.winner

import io.reactivex.Observable
import org.junit.Rule

import spock.lang.Shared
import spock.lang.AutoCleanup
import spock.lang.Specification
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.testcontainers.containers.PostgreSQLContainer

/**
 * @since 0.1.0
 */
class RepositoryRxSpec extends Specification {

  @Rule
  PostgreSQLContainer postgres = new PostgreSQLContainer()

  @Shared
  @AutoCleanup
  EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

  void 'find all possible winners'() {
    given: 'a populated database'
    ApplicationContext ctx = embeddedServer.applicationContext
    Repository repository = ctx.getBean(Repository)

    when: 'asking for a given raffle winners'
    UUID id = UUID.fromString('cc00c00e-6a42-11e8-adc0-fa7ae01bbebc')
    Winner winner = repository
      .findAllWinners(id)
      .blockingFirst()

    then: 'we should get 4 winners'
    winner
  }
}
