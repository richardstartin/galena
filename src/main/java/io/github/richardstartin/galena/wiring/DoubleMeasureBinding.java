package io.github.richardstartin.galena.wiring;

import io.github.richardstartin.galena.AggregateColumn;
import io.github.richardstartin.galena.AggregateRow;
import io.github.richardstartin.galena.MeasureBinding;
import io.github.richardstartin.galena.aggregate.DoubleAggregateColumn;

import java.util.function.DoubleBinaryOperator;
import java.util.function.ToDoubleFunction;

public class DoubleMeasureBinding<T> implements MeasureBinding<T> {

  private final ToDoubleFunction<T> extractor;
  private final DoubleBinaryOperator aggregationFunction;

  public DoubleMeasureBinding(ToDoubleFunction<T> extractor, DoubleBinaryOperator aggregationFunction) {
    this.extractor = extractor;
    this.aggregationFunction = aggregationFunction;
  }

  @Override
  public void accept(T message, int column, AggregateRow row) {
    row.add(column, extractor.applyAsDouble(message));
  }

  @Override
  public AggregateColumn toColumn() {
    return new DoubleAggregateColumn(aggregationFunction);
  }
}
