package io.rafflethor.baldr

import javax.inject.Inject

import io.reactivex.Observable
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Produces

import io.rafflethor.baldr.model.Winner
import io.rafflethor.baldr.model.Participant

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

  @Get('/participants/{id}')
  public Observable<Participant> findAllParticipants(UUID id) {
    return service.findAllParticipants(id)
  }

  @Get('/winners/{id}{?noWinners:0}')
  public Observable<Winner> findAllWinners(UUID id, Integer noWinners) {
    return service.findAllWinners(id, noWinners)
  }
}
