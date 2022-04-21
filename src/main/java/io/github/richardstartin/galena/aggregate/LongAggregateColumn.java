package io.github.richardstartin.galena.aggregate;

import io.github.richardstartin.galena.AggregateColumn;
import org.roaringbitmap.IntConsumer;
import org.roaringbitmap.RoaringBitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongBinaryOperator;

public class LongAggregateColumn implements AggregateColumn {
  private static final int SIZE = 1024;
  private final List<long[]> pages = new ArrayList<>();
  private final LongBinaryOperator aggregationFunction;

  public LongAggregateColumn(LongBinaryOperator aggregationFunction) {
    this.aggregationFunction = aggregationFunction;
  }

  @Override
  public void add(int row, int value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int row, long value) {
    int pageNumber = row / SIZE;
    while (pageNumber >= pages.size()) {
      pages.add(new long[SIZE]);
    }
    long[] page = pages.get(pageNumber);
    page[row & (SIZE - 1)] = aggregationFunction.applyAsLong(page[row & (SIZE - 1)], value);
  }

  @Override
  public void add(int row, double value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int aggregateInt(RoaringBitmap bitmap) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long aggregateLong(RoaringBitmap bitmap) {
    long[] aggregate = new long[1];
    bitmap.forEach((IntConsumer) i -> aggregate[0] = aggregationFunction.applyAsLong(aggregate[0], pages.get(i / SIZE)[i & (SIZE - 1)]));
    return aggregate[0];
  }

  @Override
  public double aggregateDouble(RoaringBitmap bitmap) {
    throw new UnsupportedOperationException();
  }

}
