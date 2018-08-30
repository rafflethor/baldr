package io.rafflethor.baldr.winner

import javax.inject.Inject

import io.reactivex.Observable
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Produces

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

  @Get('/raffle/{raffleId}/winners/{?noWinners:0}')
  Observable<Winner> findAllWinners(UUID raffleId, Integer noWinners) {
    return service.findAllWinners(raffleId, noWinners)
  }

  @Put('/raffle/{raffleId}/winners/')
  Observable<Winner> markWinnersAsNonValid(List<UUID> winners, UUID raffleId) {
    return service.markWinnersAsNonValid(winners, raffleId)
  }

  @Get('/raffle/{raffleId}/result/{userHash}')
  Observable<Result> checkRaffleResult(UUID raffleId, String userHash) {
    return service.checkRaffleResult(raffleId, userHash)
  }
}
