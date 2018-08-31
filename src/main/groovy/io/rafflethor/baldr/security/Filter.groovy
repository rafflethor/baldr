package io.rafflethor.baldr.security

import javax.inject.Inject

import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.HttpResponseFactory
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Filter as MicronautFilter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import org.reactivestreams.Publisher

/**
 * Micronaut filter to check that the current http request is properly
 * authenticated
 *
 * @since 0.1.0
 */
@MicronautFilter("*")
class Filter implements HttpServerFilter {

  /**
   * Empty string
   *
   * @since 0.1.0
   */
  static final String EMPTY = ''

  /**
   * Default authorization header value prefix
   *
   * @since 0.1.0
   */
  static final String HEADER_VALUE_PREFIX = 'Bearer '

  /**
   * Service used to authenticate the current request
   *
   * @since 0.1.0
   */
  @Inject
  Service service

  @Override
  Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
    String token = request
      .headers
      .findFirst('Authorization')
      .map({ String authorization -> authorization - HEADER_VALUE_PREFIX })
      .orElse(EMPTY)

    return service
      .authenticateToken(token)
      .flatMap({ User user -> chain.proceed(request) })
      .onErrorReturnItem(HttpResponseFactory.INSTANCE.status(HttpStatus.UNAUTHORIZED))
      .toFlowable()
  }
}
