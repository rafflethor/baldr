package io.rafflethor.baldr.security

import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.Single

/**
 * Security service implementation. The service acts as a proxy to the
 * real security service.
 *
 * @since 0.1.0
 */
@Singleton
class ServiceImpl implements Service {

  /**
   * Client used to proxy all security calls to the security module
   *
   * @since 0.1.0
   */
  @Inject
  Client client

  @Override
  Single<User> authenticateToken(String token) {
    return client.authenticateToken(token)
  }
}
