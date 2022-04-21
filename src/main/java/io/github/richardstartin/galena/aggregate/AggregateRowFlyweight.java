package io.github.richardstartin.galena.aggregate;

import io.github.richardstartin.galena.AggregateColumn;
import io.github.richardstartin.galena.AggregateRow;

public class AggregateRowFlyweight implements AggregateRow {

  private final AggregateColumn[] columns;
  private int row;

  public AggregateRowFlyweight(AggregateColumn[] columns) {
    this.columns = columns;
  }

  public void setRow(int row) {
    this.row = row;
  }

  @Override
  public void add(int column, int value) {
    columns[column].add(row, value);
  }

  @Override
  public void add(int column, long value) {
    columns[column].add(row, value);
  }

  @Override
  public void add(int column, double value) {
    columns[column].add(row, value);
  }
}
