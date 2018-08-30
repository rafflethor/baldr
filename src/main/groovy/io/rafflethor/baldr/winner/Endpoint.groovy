package io.rafflethor.baldr.winner

import javax.inject.Inject

import io.reactivex.Single
import io.reactivex.Observable
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.QueryValue

/**
 * Rest API endpoint to serve Baldr functionality
 *
 * @since 0.1.0
 */
@Controller('/api/raffles')
@Consumes([MediaType.APPLICATION_JSON])
@Produces([MediaType.APPLICATION_JSON])
class Endpoint {

  @Inject
  Service service

  @Get('/{raffleId}/winners{?noWinners}')
  Observable<Winner> findAllWinners(UUID raffleId, @QueryValue Integer noWinners) {
    return service.findAllWinners(raffleId, noWinners)
  }

  @Put('/{raffleId}/winners')
  Observable<Winner> markWinnersAsNonValid(@Body List<UUID> winners, UUID raffleId) {
    return service.markWinnersAsNonValid(winners, raffleId)
  }

  @Get('/{raffleId}/result/{userHash}')
  Single<Result> checkRaffleResult(UUID raffleId, String userHash) {
    return service.checkRaffleResult(raffleId, userHash)
  }
}
