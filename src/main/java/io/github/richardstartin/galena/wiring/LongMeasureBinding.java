package io.github.richardstartin.galena.wiring;

import io.github.richardstartin.galena.AggregateColumn;
import io.github.richardstartin.galena.AggregateRow;
import io.github.richardstartin.galena.MeasureBinding;
import io.github.richardstartin.galena.aggregate.LongAggregateColumn;

import java.util.function.LongBinaryOperator;
import java.util.function.ToLongFunction;

public class LongMeasureBinding<T> implements MeasureBinding<T> {

  private final ToLongFunction<T> extractor;
  private final LongBinaryOperator aggregationFunction;

  public LongMeasureBinding(ToLongFunction<T> extractor, LongBinaryOperator aggregationFunction) {
    this.extractor = extractor;
    this.aggregationFunction = aggregationFunction;
  }

  @Override
  public void accept(T message, int column, AggregateRow row) {
    row.add(column, extractor.applyAsLong(message));
  }

  @Override
  public AggregateColumn toColumn() {
    return new LongAggregateColumn(aggregationFunction);
  }
}
