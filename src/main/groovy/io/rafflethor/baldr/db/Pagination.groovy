package io.rafflethor.baldr.db

import groovy.transform.Immutable

@Immutable
class Pagination {
  Integer max = 20
  Integer offset = 1
}
