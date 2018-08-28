package io.rafflethor.baldr.security

import io.reactivex.Observable

/**
 * Security service. The service acts as a proxy to the real security
 * service.
 *
 * @since 0.1.0
 */
interface Service {

  /**
   * @param username
   * @param password
   * @return
   * @since 0.1.0
   */
  Observable<User> authentication(String username, String password)

  /**
   * @param jwtToken
   * @return
   * @since 0.1.0
   */
  Observable<User> authentication(String jwtToken)
}
