package io.github.richardstartin.galena;

public interface MeasureBinding<T> {
  void accept(T message, int column, AggregateRow row);

  AggregateColumn toColumn();
}
