package io.rafflethor.baldr.db

import java.util.stream.Stream
import java.util.stream.StreamSupport
import io.reactivex.Single
import io.reactivex.Observable
import io.reactiverse.reactivex.pgclient.Row
import io.reactiverse.reactivex.pgclient.PgPool
import io.reactiverse.reactivex.pgclient.PgRowSet

/**
 * @since 0.1.0
 */
class RxUtils {

  /**
   * @since 0.1.0
   */
  static class RowIterable implements Iterable<Row> {

    /**
     * @since 0.1.0
     */
    PgRowSet rowSet

    /**
     * @param rowSet
     * @since 0.1.0
     */
    RowIterable(PgRowSet rowSet) {
      this.rowSet = rowSet
    }

    @Override
    public Iterator<Row> iterator() {
      return rowSet.iterator() as Iterator<Row>
      }
  }

  /**
   * @param rowSet
   * @return
   * @since 0.1.0
   */
  static Observable<Row> toObservable(PgRowSet rowSet) {
    return Observable.fromIterable(toIterable(rowSet))
  }

  /**
   * @param rowSet
   * @return
   * @since 0.1.0
   */
  static Iterable<Row> toIterable(PgRowSet rowSet) {
    return new RowIterable(rowSet)
  }

  /**
   * @param rowSet
   * @return
   * @since 0.1.0
   */
  static Stream<Row> toStream(PgRowSet rowSet) {
    return StreamSupport.stream(toIterable(rowSet).spliterator(), false)
  }

  /**
   * @param client
   * @param fn
   * @return
   * @since 0.1.0
   */
  static Single<PgRowSet> executeTx(PgPool client, Closure<Single<PgRowSet>>... fn) {
    return null
  }
}
