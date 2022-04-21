package io.github.richardstartin.galena.wiring;

import io.github.richardstartin.galena.Dictionary;
import io.github.richardstartin.galena.DimensionBinding;

import java.util.function.ToLongFunction;

public class LongDimensionBinding<T> implements DimensionBinding<T> {
  private final Dictionary dictionary;
  private final ToLongFunction<T> extractor;

  public LongDimensionBinding(Dictionary dictionary, ToLongFunction<T> extractor) {
    this.dictionary = dictionary;
    this.extractor = extractor;
  }

  @Override
  public int applyAsInt(T message) {
    return dictionary.encode(extractor.applyAsLong(message));
  }
}
