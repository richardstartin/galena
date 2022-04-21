package io.github.richardstartin.galena.wiring;

import io.github.richardstartin.galena.Dictionary;
import io.github.richardstartin.galena.DimensionBinding;

import java.util.function.Function;

public class StringDimensionBinding<T> implements DimensionBinding<T> {
  private final Dictionary dictionary;
  private final Function<T, String> extractor;

  public StringDimensionBinding(Dictionary dictionary, Function<T, String> extractor) {
    this.dictionary = dictionary;
    this.extractor = extractor;
  }

  @Override
  public int applyAsInt(T message) {
    return dictionary.encode(extractor.apply(message));
  }
}
