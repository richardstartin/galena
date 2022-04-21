package io.github.richardstartin.galena.schema;

import io.github.richardstartin.galena.Cube;
import io.github.richardstartin.galena.DimensionBinding;
import io.github.richardstartin.galena.MeasureBinding;
import io.github.richardstartin.galena.dictionary.IntDictionary;
import io.github.richardstartin.galena.dictionary.LongDictionary;
import io.github.richardstartin.galena.dictionary.StringDictionary;
import io.github.richardstartin.galena.wiring.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class Builder<T> {

  private final List<DimensionBinding<T>> dimensions = new ArrayList<>();
  private final List<MeasureBinding<T>> measures = new ArrayList<>();

  public static <T> Builder<T> of() {
    return new Builder<>();
  }

  public Builder<T> withDimension(Function<T, String> extractor) {
    dimensions.add(new StringDimensionBinding<>(new StringDictionary(), extractor));
    return this;
  }

  public Builder<T> withDimension(ToIntFunction<T> extractor) {
    dimensions.add(new IntDimensionBinding<>(new IntDictionary(), extractor));
    return this;
  }

  public Builder<T> withDimension(ToLongFunction<T> extractor) {
    dimensions.add(new LongDimensionBinding<>(new LongDictionary(), extractor));
    return this;
  }

  public Builder<T> withMeasure(ToIntFunction<T> extractor, IntBinaryOperator aggregationFunction) {
    measures.add(new IntMeasureBinding<>(extractor, aggregationFunction));
    return this;
  }

  public Builder<T> withMeasure(ToLongFunction<T> extractor, LongBinaryOperator aggregationFunction) {
    measures.add(new LongMeasureBinding<>(extractor, aggregationFunction));
    return this;
  }

  public Builder<T> withMeasure(ToDoubleFunction<T> extractor, DoubleBinaryOperator aggregationFunction) {
    measures.add(new DoubleMeasureBinding<>(extractor, aggregationFunction));
    return this;
  }

  public Cube<T> build() {
    return new Cube<>(dimensions, measures);
  }
}
