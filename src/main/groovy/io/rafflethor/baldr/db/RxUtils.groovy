package io.rafflethor.baldr.db

import io.reactivex.Observable
import io.reactiverse.reactivex.pgclient.Row
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
    return Observable.fromIterable(new RowIterable(rowSet))
  }
}
