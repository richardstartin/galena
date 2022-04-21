package io.github.richardstartin.galena;

import org.roaringbitmap.RoaringBitmap;

public interface AggregateColumn {
  void add(int row, int value);
  void add(int row, long value);
  void add(int row, double value);
  int aggregateInt(RoaringBitmap bitmap);
  long aggregateLong(RoaringBitmap bitmap);
  double aggregateDouble(RoaringBitmap bitmap);
}
