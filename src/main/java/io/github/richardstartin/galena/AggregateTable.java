package io.github.richardstartin.galena;

public interface AggregateTable {
  AggregateRow get(int[] dimensions);

  int getIntValue(int column, int... coordinates);

  long getLongValue(int column, int... coordinates);

  double getDoubleValue(int column, int... coordinates);
}
