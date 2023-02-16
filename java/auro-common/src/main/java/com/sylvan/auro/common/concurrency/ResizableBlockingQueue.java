package com.sylvan.auro.common.concurrency;

import com.google.common.base.Preconditions;
import java.util.concurrent.BlockingQueue;

/** @author sylvan */
public final class ResizableBlockingQueue<E> extends BoundedBlockingQueue<E> {

  private volatile int capacity;
  private final int minCapacity;
  private final int maxCapacity;
  private final int adjustAmount;

  public ResizableBlockingQueue(
      BlockingQueue<E> queue,
      int initialCapacity,
      int minCapacity,
      int maxCapacity,
      int adjustAmount) {
    super(queue, initialCapacity);

    Preconditions.checkArgument(adjustAmount > 0, "adjust amount should be a positive value");
    Preconditions.checkArgument(minCapacity >= 0, "cannot have min capacity smaller than 0");
    Preconditions.checkArgument(
        maxCapacity >= minCapacity, "cannot have max capacity smaller than min capacity");

    this.capacity = initialCapacity;
    this.minCapacity = minCapacity;
    this.maxCapacity = maxCapacity;
    this.adjustAmount = adjustAmount;
  }

  @Override
  public int capacity() {
    return this.capacity;
  }

  @Override
  public int remainingCapacity() {
    return Math.max(0, this.capacity());
  }

  public synchronized int adjustCapacity(int desiredCapacity) {
    Preconditions.checkArgument(desiredCapacity >= 0, "desired capacity cannot be negative");

    if (desiredCapacity > capacity + adjustAmount) {
      int newCapacity = Math.min(maxCapacity, capacity + adjustAmount);
      this.capacity = newCapacity;
      return newCapacity;
    } else if (desiredCapacity < capacity - adjustAmount) {
      int newCapacity = Math.max(minCapacity, capacity - adjustAmount);
      this.capacity = newCapacity;
      return newCapacity;
    } else {
      this.capacity = desiredCapacity;
      return this.capacity;
    }
  }
}
