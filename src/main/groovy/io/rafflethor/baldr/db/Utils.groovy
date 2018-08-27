package io.rafflethor.baldr.db

import java.sql.Connection
import java.sql.Timestamp
import java.time.LocalDateTime

import groovy.sql.Sql

/**
 * Different utility functions
 *
 * @since 0.1.0
 */
class Utils {

  /**
   * Executes a transaction and returns the last expression in the
   * transaction
   *
   * @param sql underlying sql connection
   * @param expression the expression executed within the transaction
   * @return the last expression of the transaction once the
   * transaction has finished
   * @since 0.1.0
   */
  static <T> T executeTx(Sql sql, Closure<T> expression) {
    T result = null

    sql.withTransaction { Connection connection ->
      result = expression(new Sql(connection))
    }

    return result
  }

  /**
   * Returns a random UUID
   *
   * @return a random UUID
   * @since 0.1.0
   */
  static UUID generateUUID() {
    return UUID.randomUUID()
  }

  static Timestamp toTimestamp(LocalDateTime dateTime) {
    return Optional
      .ofNullable(dateTime)
      .map(Timestamp.&valueOf)
      .orElse(null) as Timestamp
  }
}
