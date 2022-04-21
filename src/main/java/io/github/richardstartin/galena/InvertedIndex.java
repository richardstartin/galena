package io.github.richardstartin.galena;

import org.roaringbitmap.RoaringBitmap;

public interface InvertedIndex {
  void add(int attribute, int rid);

  RoaringBitmap get(int attribute);

  void forEach(BitmapConsumer consumer);
}
