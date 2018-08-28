package io.rafflethor.baldr.security

import io.micronaut.http.annotation.Post
import io.micronaut.http.client.Client as MicronautClient
import io.reactivex.Observable

/**
 * @since 0.1.0
 */
@MicronautClient('${security.url}')
interface Client {

  /**
   * @return
   * @since 0.1.0
   */
  @Post('/api/auth')
  Observable<User> authenticate()
}
