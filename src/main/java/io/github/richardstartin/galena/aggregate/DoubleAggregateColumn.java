package io.github.richardstartin.galena.aggregate;

import io.github.richardstartin.galena.AggregateColumn;
import org.roaringbitmap.IntConsumer;
import org.roaringbitmap.RoaringBitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class DoubleAggregateColumn implements AggregateColumn {

  private static final int SIZE = 1024;
  private final List<double[]> pages = new ArrayList<>();
  private final DoubleBinaryOperator aggregationFunction;

  public DoubleAggregateColumn(DoubleBinaryOperator aggregationFunction) {
    this.aggregationFunction = aggregationFunction;
  }

  @Override
  public void add(int row, int value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int row, long value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int row, double value) {
    int pageNumber = row / SIZE;
    while (pageNumber >= pages.size()) {
      pages.add(new double[SIZE]);
    }
    double[] page = pages.get(pageNumber);
    page[row & (SIZE - 1)] = aggregationFunction.applyAsDouble(page[row & (SIZE - 1)], value);
  }

  @Override
  public int aggregateInt(RoaringBitmap bitmap) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long aggregateLong(RoaringBitmap bitmap) {
    throw new UnsupportedOperationException();
  }

  @Override
  public double aggregateDouble(RoaringBitmap bitmap) {
    double[] aggregate = new double[1];
    bitmap.forEach((IntConsumer)  i -> aggregate[0] = aggregationFunction.applyAsDouble(aggregate[0], pages.get(i / SIZE)[i & (SIZE - 1)]));
    return aggregate[0];
  }
}
