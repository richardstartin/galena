package io.github.richardstartin.galena.wiring;

import io.github.richardstartin.galena.AggregateColumn;
import io.github.richardstartin.galena.AggregateRow;
import io.github.richardstartin.galena.MeasureBinding;
import io.github.richardstartin.galena.aggregate.IntAggregateColumn;

import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;

public class IntMeasureBinding<T> implements MeasureBinding<T> {

  private final ToIntFunction<T> extractor;
  private final IntBinaryOperator aggregationFunction;

  public IntMeasureBinding(ToIntFunction<T> extractor, IntBinaryOperator aggregationFunction) {
    this.extractor = extractor;
    this.aggregationFunction = aggregationFunction;
  }

  @Override
  public void accept(T message, int column, AggregateRow row) {
    row.add(column, extractor.applyAsInt(message));
  }

  @Override
  public AggregateColumn toColumn() {
    return new IntAggregateColumn(aggregationFunction);
  }
}
