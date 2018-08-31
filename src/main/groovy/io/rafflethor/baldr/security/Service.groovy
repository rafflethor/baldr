package io.rafflethor.baldr.security

import io.reactivex.Single

/**
 * Security service. The service acts as a proxy to the real security
 * service.
 *
 * @since 0.1.0
 */
interface Service {

  /**
   * Authenticates the provided token
   *
   * @param token the provided token
   * @return the user information
   * @since 0.1.0
   */
  Single<User> authenticateToken(String token)
}
