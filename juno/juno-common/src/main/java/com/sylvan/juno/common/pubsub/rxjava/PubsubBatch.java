package com.sylvan.juno.common.pubsub.rxjava;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author sylvan
 * @date 2020/7/15
 */
public abstract class PubsubBatch<T, U extends Collection<? super T>> {
  private final PublishSubject<T> pipeline;

  protected PubsubBatch(
      Scheduler observer,
      Scheduler subscriber,
      Duration duration,
      int count,
      Supplier<U> bufferSupplier) {
    this.pipeline = PublishSubject.create();
    this.pipeline
        .observeOn(observer)
        .subscribeOn(subscriber)
        .buffer(duration.toMillis(), TimeUnit.MILLISECONDS, observer, count, bufferSupplier, false)
        .subscribe(
            this::batch,
            throwable -> {
              System.out.println(
                  String.format("Failed to run %s.batch: ", this.getClass().getSimpleName()));
              throwable.printStackTrace();
            });
  }

  protected abstract void batch(U objects);

  public void onNext(T object) {
    pipeline.onNext(object);
  }

  public void onComplete() {
    pipeline.onComplete();
  }
}
