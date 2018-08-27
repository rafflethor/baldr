package io.rafflethor.baldr.db

import groovy.sql.Sql
import java.sql.Connection

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
}
