package io.rafflethor.baldr.db

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import javax.sql.DataSource

import groovy.sql.Sql

@Singleton
class SqlProvider implements Provider<Sql> {

  @Inject
  DataSource dataSource

  @Override
  Sql get() {
    return new Sql(dataSource)
  }
}
