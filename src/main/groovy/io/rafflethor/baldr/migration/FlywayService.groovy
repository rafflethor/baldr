package io.rafflethor.baldr.migration

import javax.inject.Singleton
import javax.inject.Inject
import javax.sql.DataSource

import groovy.util.logging.Slf4j
import io.micronaut.runtime.event.ApplicationStartupEvent
import io.micronaut.context.event.ApplicationEventListener
import org.flywaydb.core.Flyway

@Slf4j
@Singleton
class FlywayService implements ApplicationEventListener<ApplicationStartupEvent> {

  @Inject
  DataSource dataSource

  @Inject
  FlywayConfig config

  @Override
  void onApplicationEvent(ApplicationStartupEvent event) {
    log.info('Flyway: starts')

    Flyway flyway = new Flyway()

    flyway.dataSource = dataSource
    flyway.locations = config.migrations
    flyway.schemas = config.schemas

    flyway.repair()
    flyway.migrate()

    log.info('Flyway: ends')
  }
}
