package com.sylvan.juno.common.concurrency;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public final class RxJavaUtils {

  public static <T> Flowable<T> flatten(CompletableFuture<? extends Collection<T>> future) {
    return Flowable.fromFuture(future).flatMap(Flowable::fromIterable);
  }

  public static <T> CompletableFuture<T> toCompletableFuture(Single<T> single) {
    CompletableFuture<T> f = new CompletableFuture<>();
    single.subscribe(
        (r, ex) -> {
          if (ex != null) {
            f.completeExceptionally(ex);
          } else {
            f.complete(r);
          }
        });
    return f;
  }
}
