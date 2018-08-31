package io.rafflethor.baldr.security

import groovy.transform.Immutable

/**
 * Information about the authenticated user
 *
 * @since 0.1.0
 */
@Immutable
class User {

  /**
   * User id
   *
   * @since 0.1.0
   */
  UUID id

  /**
   * User's username
   *
   * @since 0.1.0
   */
  String username

  /**
   * Current user token
   *
   * @since 0.1.0
   */
  String token
}
