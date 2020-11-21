package com.sylvan.hecate.interview.stack;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

public abstract class AbstractStack<T, C, O> implements IStack<T> {
    private Stack<T> stack;
    private List<C> inputList;
    private O result;

    public AbstractStack(List<C> inputList, O initData) {
        this.stack = new Stack<>();
        this.inputList = inputList;
        this.result = initData;
    }

    @Override
    public Optional<T> pop() {
        if (stack.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(stack.pop());
    }

    @Override
    public void push(T item) {
        this.stack.add(item);
    }

    @Override
    public void run() {
        inputList.forEach(
                input -> {
                    Optional<T> output = operation(pop(), input);
                    output.ifPresent(this::push);
                });
    }

    public abstract Optional<T> operation(Optional<T> item, C input);
}
