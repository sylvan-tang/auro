package com.sylvan.juno.common.concurrency;

import com.google.common.base.Preconditions;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 有界的 BlockingQueue
 *
 * @author sylvan
 */
public class BoundedBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E> {

  private final BlockingQueue<E> delegate;
  private final int capacity;
  private final AtomicInteger size = new AtomicInteger();

  public BoundedBlockingQueue(BlockingQueue<E> delegate, int capacity) {
    Preconditions.checkArgument(capacity >= 0);
    this.delegate = delegate;
    this.capacity = capacity;
  }

  @Override
  public int size() {
    return size.get();
  }

  public int capacity() {
    return this.capacity;
  }

  @Override
  public Iterator<E> iterator() {
    final Iterator<E> it = delegate.iterator();
    return new Iterator<E>() {
      E current;

      @Override
      public boolean hasNext() {
        return it.hasNext();
      }

      @Override
      public E next() {
        current = it.next();
        return current;
      }

      @Override
      public void remove() {
        if (delegate.remove(current)) {
          size.decrementAndGet();
        }
      }
    };
  }

  @Override
  public E peek() {
    return delegate.peek();
  }

  @Override
  public E poll() {
    E e = delegate.poll();
    if (e != null) {
      size.decrementAndGet();
    }
    return e;
  }

  @Override
  public E poll(long timeout, TimeUnit unit) throws InterruptedException {
    E e = delegate.poll(timeout, unit);
    if (e != null) {
      size.decrementAndGet();
    }
    return e;
  }

  @Override
  public boolean remove(Object o) {
    boolean v = delegate.remove(o);
    if (v) {
      size.decrementAndGet();
    }
    return v;
  }

  @Override
  public boolean offer(E e) {
    while (true) {
      final int current = size.get();
      if (current >= capacity()) {
        return false;
      }
      if (size.compareAndSet(current, 1 + current)) {
        break;
      }
    }
    boolean offered = delegate.offer(e);
    if (!offered) {
      size.decrementAndGet();
    }
    return offered;
  }

  @Override
  public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
    throw new IllegalStateException("offer with timeout not allowed on size queue");
  }

  @Override
  public void put(E e) throws InterruptedException {
    throw new IllegalStateException("put not allowed on size queue");
  }

  @Override
  public E take() throws InterruptedException {
    E e;
    e = delegate.take();
    size.decrementAndGet();
    return e;
  }

  @Override
  public int remainingCapacity() {
    return capacity() - size.get();
  }

  @Override
  public int drainTo(Collection<? super E> c) {
    int v = delegate.drainTo(c);
    size.addAndGet(-v);
    return v;
  }

  @Override
  public int drainTo(Collection<? super E> c, int maxElements) {
    int v = delegate.drainTo(c, maxElements);
    size.addAndGet(-v);
    return v;
  }

  @Override
  public Object[] toArray() {
    return delegate.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return delegate.toArray(a);
  }

  @Override
  public boolean contains(Object o) {
    return delegate.contains(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return delegate.containsAll(c);
  }
}
