package io.github.richardstartin.galena;

import org.roaringbitmap.RoaringBitmap;

@FunctionalInterface
public interface BitmapConsumer {
  void accept(int attribute, RoaringBitmap bitmap);
}
