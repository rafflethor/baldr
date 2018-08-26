package io.rafflethor.baldr.db

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

import javax.sql.DataSource
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

/**
 * @since 0.1.0
 */
@Singleton
class DataSourceProvider implements Provider<DataSource> {

  @Inject
  DataSourceConfig config

  @Override
  DataSource get() {
    HikariConfig hikariConfig = new HikariConfig()

    hikariConfig.driverClassName = config.driverClassName
    hikariConfig.jdbcUrl = config.url
    hikariConfig.username = config.username
    hikariConfig.password = config.password

    return new HikariDataSource(hikariConfig)
  }
}
