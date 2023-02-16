package com.sylvan.auro.common.concurrency;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/** A simple tool to run map reduce job */
@Slf4j
public class ParallelProcessor {

  private static final Object VOID_RESULT_HOLDER = new Object();
  private final Scheduler scheduler;

  private ParallelProcessor(ExecutorService executorService) {
    this.scheduler = Schedulers.from(executorService);
  }

  public static ParallelProcessor runOn(ExecutorService executorService) {
    return new ParallelProcessor(executorService);
  }

  /** Executes {@code mapFunc} in parallel, and keeps results' order as same as the input */
  public <T, R> CompletableFuture<List<R>> processListResult(
      List<T> input, int partitionSize, Function<List<T>, List<R>> mapFunc) {
    if (input.isEmpty()) {
      return CompletableFuture.completedFuture(Collections.emptyList());
    }
    List<Indexwise<T>> indexwises =
        IntStream.range(0, input.size())
            .mapToObj(idx -> new Indexwise<>(idx, input.get(idx)))
            .collect(Collectors.toList());

    Function<Collection<Indexwise<T>>, List<Indexwise<R>>> innerMapFunc =
        partitioned -> {
          List<Indexwise<T>> subList = Lists.newArrayList(partitioned);
          List<T> sectionElements =
              subList.stream().map(Indexwise::getElement).collect(Collectors.toList());

          List<R> sectionValues = mapFunc.apply(sectionElements);

          Preconditions.checkState(
              sectionElements.size() == sectionValues.size(),
              "input and output in mapFunc should have same size");

          List<Indexwise<R>> results = new ArrayList<>(partitioned.size());
          for (int idx = 0; idx < partitioned.size(); idx++) {
            results.add(new Indexwise<>(subList.get(idx).index, sectionValues.get(idx)));
          }

          return results;
        };
    // These variables help inferring arguments for `doProcess`
    List<Indexwise<R>> returnOnErrorItem = Collections.emptyList();
    BiConsumer<List<Indexwise<R>>, List<Indexwise<R>>> reduceFunc = List::addAll;
    return RxJavaUtils.toCompletableFuture(
        doProcess(
                indexwises,
                partitionSize,
                returnOnErrorItem,
                innerMapFunc,
                new ArrayList<>(indexwises.size()),
                reduceFunc)
            .map(
                ow ->
                    ow.stream().sorted().map(Indexwise::getElement).collect(Collectors.toList())));
  }

  /**
   * Executes {@code mapFunc} in parallel
   *
   * <p>This is a tricky way to implement concurrent subscribe atop Flowable
   */
  public <T> CompletableFuture<Object> process(
      Collection<T> input, int partitionSize, Consumer<Collection<T>> consumerFunc) {
    if (input.isEmpty()) {
      return CompletableFuture.completedFuture(VOID_RESULT_HOLDER);
    }

    Function<Collection<T>, Object> mapFunc =
        sub -> {
          consumerFunc.accept(sub);
          return VOID_RESULT_HOLDER;
        };
    return RxJavaUtils.toCompletableFuture(
        doProcess(
            input,
            partitionSize,
            VOID_RESULT_HOLDER,
            mapFunc,
            VOID_RESULT_HOLDER,
            (v1, v2) -> {} // DO NOTHING
            ));
  }

  /** Executes {@code mapFunc} in parallel and returns Map as result */
  public <T, K, V> CompletableFuture<Map<K, V>> processMapResult(
      Collection<T> input, int partitionSize, Function<Collection<T>, Map<K, V>> mapFunc) {
    if (input.isEmpty()) {
      return CompletableFuture.completedFuture(Collections.emptyMap());
    }
    return RxJavaUtils.toCompletableFuture(
        doProcess(
            input,
            partitionSize,
            Collections.emptyMap(),
            mapFunc,
            new HashMap<>(input.size()),
            Map::putAll));
  }

  private <T, R> Single<R> doProcess(
      Collection<T> input,
      int partitionSize,
      R returnOnErrorItem,
      Function<Collection<T>, R> mapFunc,
      R consumeSeed,
      BiConsumer<R, R> consumeFunc) {
    if (input.size() <= partitionSize) {
      return Single.fromCallable(() -> mapFunc.apply(input))
          .subscribeOn(scheduler)
          .onErrorReturn(
              ex -> {
                log.error("Process failed, item ids: {}", input, ex);
                return returnOnErrorItem;
              });
    }
    return Flowable.fromIterable(input)
        .buffer(partitionSize)
        .flatMap(
            sub ->
                Flowable.fromCallable(() -> mapFunc.apply(sub))
                    .subscribeOn(scheduler)
                    .onErrorReturn(
                        ex -> {
                          log.error("Process failed, item ids: {}", sub, ex);
                          return returnOnErrorItem;
                        }))
        .collect(() -> consumeSeed, consumeFunc::accept);
  }

  @AllArgsConstructor
  @Getter
  private static class Indexwise<T> implements Comparable<Indexwise<T>> {

    int index;
    T element;

    @Override
    public int compareTo(Indexwise<T> o) {
      return this.index - o.index;
    }
  }
}
