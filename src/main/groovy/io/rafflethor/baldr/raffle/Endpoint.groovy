package io.rafflethor.baldr.raffle

import javax.inject.Inject

import io.reactivex.Observable
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Produces

import io.rafflethor.baldr.db.Pagination

/**
 * Rest API endpoint to serve Baldr functionality
 *
 * @since 0.1.0
 */
@Controller('/api')
@Consumes([MediaType.APPLICATION_JSON])
@Produces([MediaType.APPLICATION_JSON])
class Endpoint {

  @Inject
  Service service

  /**
   * @param pagination
   * @return
   * @since 0.1.0
   */
  @Get('/raffles')
  Observable<Raffle> list(Pagination pagination) {
    return service.listAllByUser(pagination, null)
  }

  /**
   * @param id
   * @return
   * @since 0.1.0
   */
  @Get('/raffles/{id}')
  Observable<Raffle> findById(UUID id) {
    return service.findById(id, null)
  }
}
