package io.rafflethor.baldr.participant

import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.Observable

@Singleton
class ServiceImpl implements Service {

  /**
   * @since 0.1.0
   */
  @Inject
  Repository repository

  @Override
  Observable<Participant> findAllParticipants(UUID raffle) {
    return Observable.fromIterable(repository.findAllParticipants(raffle))
  }
}
