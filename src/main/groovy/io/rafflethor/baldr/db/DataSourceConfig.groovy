package io.rafflethor.baldr.db

import io.micronaut.context.annotation.ConfigurationProperties

/**
 * Configuration to get properties needed to build a data source
 *
 * @since 0.1.0
 */
@ConfigurationProperties('db')
class DataSourceConfig {
  /**
   * @since 0.1.0
   */
  String url

  /**
   * @since 0.1.0
   */
  String username

  /**
   * @since 0.1.0
   */
  String password

  /**
   * @since 0.1.0
   */
  String driverClassName
}
