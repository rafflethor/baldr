package io.rafflethor.baldr.raffle

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Observable
import io.rafflethor.baldr.db.Pagination

/**
 * Implementation to manage operations over {@link Raffle} instances
 *
 * @since 0.1.0
 */
@Singleton
class ServiceImpl implements Service {

  /**
   * Database access repository
   *
   * @since 0.1.0
   */
  @Inject
  Repository repository

  @Override
  Observable<Raffle> listAllByUser(Pagination pagination, UUID user) {
    return Observable.fromIterable(repository.listAll(pagination, user))
  }

  @Override
  Observable<Raffle> save(Raffle raffle, UUID user) {
    return Observable.just(repository.upsert(raffle, user))
  }

  @Override
  Observable<Raffle> findById(UUID raffle, UUID user) {
    return Observable.just(repository.findById(raffle, user))
  }

  @Override
  Observable<Raffle> start(UUID raffle, UUID user) {
    return Observable.just(repository.markWaiting(raffle, user))
  }

  @Override
  Observable<Boolean> delete(UUID id, UUID user) {
    return Observable.just(repository.delete(id, user))
  }
}
