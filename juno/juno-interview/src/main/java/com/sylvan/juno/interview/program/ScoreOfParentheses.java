package com.sylvan.juno.interview.program;

import com.sylvan.juno.common.stack.AbstractStack;
import java.util.List;
import java.util.Optional;

public class ScoreOfParentheses extends AbstractStack<Character, Character, Integer> {
  public ScoreOfParentheses(List<Character> inputList, Integer initData) {
    super(inputList, initData);
    run();
  }

  @Override
  public Optional<Character> operation(Optional<Character> item, Character input) {
    return Optional.empty();
  }
}
