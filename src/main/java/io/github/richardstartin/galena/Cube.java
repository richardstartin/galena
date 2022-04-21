package io.github.richardstartin.galena;

import io.github.richardstartin.galena.aggregate.AggregateTableImpl;
import io.github.richardstartin.galena.dictionary.IntDictionary;
import io.github.richardstartin.galena.dictionary.LongDictionary;
import io.github.richardstartin.galena.dictionary.StringDictionary;
import io.github.richardstartin.galena.wiring.*;
import org.roaringbitmap.RoaringBitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class Cube<T> implements Consumer<T> {

  public static <T> Builder<T> builder(Class<T> witness) {
    return new Builder<>();
  }

  public static class Builder<T> {

    private final List<DimensionBinding<T>> dimensions = new ArrayList<>();
    private final List<MeasureBinding<T>> measures = new ArrayList<>();

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

  private final List<DimensionBinding<T>> encoders;
  private final List<MeasureBinding<T>> measureBindings;
  private final int[] dimensions;
  private final AggregateTable table;

  public Cube(List<DimensionBinding<T>> encoders, List<MeasureBinding<T>> measureBindings) {
    this.encoders = encoders;
    this.measureBindings = measureBindings;
    this.dimensions = new int[encoders.size()];
    this.table = new AggregateTableImpl(encoders.size(), measureBindings.stream().map(MeasureBinding::toColumn)
            .toArray(AggregateColumn[]::new));
  }

  public int getAsInt(int column, int... coordinates) {
    return table.getIntValue(column, coordinates);
  }

  public long getAsLong(int column, int... coordinates) {
    return table.getLongValue(column, coordinates);
  }

  public double getAsDouble(int column, int... coordinates) {
    return table.getDoubleValue(column, coordinates);
  }
  
  @Override
  public void accept(T message) {
    for (int i = 0; i < encoders.size(); i++) {
      dimensions[i] = encoders.get(i).applyAsInt(message);
    }
    aggregate(message, table.get(dimensions));
  }
  
  private void aggregate(T message, AggregateRow row) {
    for (int i = 0; i < measureBindings.size(); i++) {
      measureBindings.get(i).accept(message, i, row);
    }
  }
}
