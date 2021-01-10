package com.sylvan.hecate.common.stack;

import java.util.Optional;

public interface IStack<T> {
  Optional<T> pop();

  void push(T item);

  void run();
}
