package io.github.richardstartin.galena.aggregate;

import io.github.richardstartin.galena.AggregateColumn;
import org.roaringbitmap.IntConsumer;
import org.roaringbitmap.RoaringBitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class IntAggregateColumn implements AggregateColumn {
  private static final int SIZE = 1024;
  private final List<int[]> pages = new ArrayList<>();
  private final IntBinaryOperator aggregationFunction;

  public IntAggregateColumn(IntBinaryOperator aggregationFunction) {
    this.aggregationFunction = aggregationFunction;
  }

  @Override
  public void add(int row, int value) {
    int pageNumber = row / SIZE;
    while (pageNumber >= pages.size()) {
      pages.add(new int[SIZE]);
    }
    int[] page = pages.get(pageNumber);
    page[row & (SIZE - 1)] = aggregationFunction.applyAsInt(page[row & (SIZE - 1)], value);
  }

  @Override
  public void add(int row, long value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int row, double value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int aggregateInt(RoaringBitmap bitmap) {
    int[] aggregate = new int[1];
    bitmap.forEach((IntConsumer) i -> aggregate[0] = aggregationFunction.applyAsInt(aggregate[0], pages.get(i / SIZE)[i & (SIZE - 1)]));
    return aggregate[0];
  }

  @Override
  public long aggregateLong(RoaringBitmap bitmap) {
    throw new UnsupportedOperationException();
  }

  @Override
  public double aggregateDouble(RoaringBitmap bitmap) {
    throw new UnsupportedOperationException();
  }
}
