package io.rafflethor.baldr.security

import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.Observable

/**
 * Security service implementation. The service acts as a proxy to the
 * real security service.
 *
 * @since 0.1.0
 */
@Singleton
class ServiceImpl implements Service {

  @Inject
  Client client

  @Override
  Observable<User> authentication(String username, String password) {
    return null
  }

  @Override
  Observable<User> authentication(String jwtToken) {
    return null
  }
}
