package io.rafflethor.baldr.security

import javax.inject.Inject

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpParameters
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter as MicronautFilter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.reactivex.BackpressureStrategy
import org.reactivestreams.Publisher

/**
 * @since 0.1.0
 */
@MicronautFilter("*")
class Filter implements HttpServerFilter {

  /**
   * @since 0.1.0
   */
  @Inject
  Service service

  @Override
  Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
    HttpParameters params = request.parameters

    String username = params.get('username', String)
    String password = params.get('password', String)

    return service
      .authentication(username, password)
      .switchMap({ Boolean foo -> chain.proceed(request) })
      .toFlowable(BackpressureStrategy.LATEST)
  }
}
