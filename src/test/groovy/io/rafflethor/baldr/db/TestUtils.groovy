package io.rafflethor.baldr.db

import io.reactiverse.reactivex.pgclient.PgPool
import io.reactiverse.reactivex.pgclient.PgClient
import io.reactiverse.pgclient.PgPoolOptions
import org.testcontainers.containers.PostgreSQLContainer
import org.flywaydb.core.Flyway
import groovy.sql.Sql
import javax.sql.DataSource
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class TestUtils {

  static PgPool createPgPool(PostgreSQLContainer db) {
    PgPoolOptions options = new PgPoolOptions()
      .setDatabase(db.getDatabaseName())
      .setUser(db.getUsername())
      .setPassword(db.getPassword())
      .setConnectTimeout(10000)
      .setLocalAddress("0.0.0.0")
      .setMaxSize(5)

    return PgClient.pool(options)
  }

  static DataSource createDataSource(PostgreSQLContainer db) {
    HikariConfig hikariConfig = new HikariConfig()

    hikariConfig.driverClassName = db.driverClassName
    hikariConfig.jdbcUrl = db.jdbcUrl
    hikariConfig.username = db.username
    hikariConfig.password = db.password

    return new HikariDataSource(hikariConfig)
  }

  static Sql createSql(PostgreSQLContainer db) {
    return Sql.newInstance(db.jdbcUrl, db.username, db.password)
  }

  static Flyway createFlyway(PostgreSQLContainer db) {
    Flyway flyway = new Flyway()

    flyway.dataSource = createDataSource(db)
    flyway.locations = ["/migrations"]
    flyway.schemas = ["public"]

    return flyway
  }
}
