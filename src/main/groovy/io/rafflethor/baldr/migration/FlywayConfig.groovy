package io.rafflethor.baldr.migration

import io.micronaut.context.annotation.ConfigurationProperties

/**
 *
 * @since 0.1.0
 */
@ConfigurationProperties('flyway')
class FlywayConfig {
  String[] migrations
  String[] schemas
}
