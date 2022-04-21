package io.github.richardstartin.galena.wiring;

import io.github.richardstartin.galena.Dictionary;
import io.github.richardstartin.galena.DimensionBinding;

import java.util.function.ToIntFunction;

public class IntDimensionBinding<T> implements DimensionBinding<T> {

  private final Dictionary dictionary;
  private final ToIntFunction<T> extractor;

  public IntDimensionBinding(Dictionary dictionary, ToIntFunction<T> extractor) {
    this.dictionary = dictionary;
    this.extractor = extractor;
  }

  @Override
  public int applyAsInt(T message) {
    return dictionary.encode(extractor.applyAsInt(message));
  }
}
