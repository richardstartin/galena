package io.github.richardstartin.galena;

public interface AggregateRow {
  void add(int column, int value);
  void add(int column, long value);
  void add(int column, double value);
}
