package io.rafflethor.baldr.security

import groovy.transform.Immutable

/**
 * @since 0.1.0
 */
@Immutable
class User {

  /**
   * @since 0.1.0
   */
  String username

  /**
   * @since 0.1.0
   */
  String token

  /**
   * @since 0.1.0
   */
  String jwtToken
}
