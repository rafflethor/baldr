package io.rafflethor.baldr.security

import io.reactivex.Single
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.Client as MicronautClient

/**
 * Client used to authenticate user against the security module
 *
 * @since 0.1.0
 */
@MicronautClient('${security.url}')
interface Client {

  /**
   * Checks whether the provided token is a valid user or not
   *
   * @return authenticated user information
   * @param token the provided token
   * @since 0.1.0
   */
  @Post('/api/security/auth/token')
  Single<User> authenticateToken(String token)
}
