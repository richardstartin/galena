package io.github.richardstartin.galena.index;

import io.github.richardstartin.galena.BitmapConsumer;
import io.github.richardstartin.galena.InvertedIndex;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import org.roaringbitmap.RoaringBitmap;
import org.roaringbitmap.RoaringBitmapWriter;

public class RoaringBitmapIndex implements InvertedIndex {

  private final Int2ObjectSortedMap<RoaringBitmapWriter<RoaringBitmap>> appenders = new Int2ObjectAVLTreeMap<>();

  public void add(int attribute, int rid) {
    var appender = appenders.get(attribute);
    if (appender == null) {
      appender = RoaringBitmapWriter.writer().get();
      appenders.put(attribute, appender);
    }
    appender.add(rid);
  }

  public RoaringBitmap get(int attribute) {
    var appender = appenders.get(attribute);
    return appender == null ? null : appender.get();
  }

  public void forEach(BitmapConsumer consumer) {
    for (Int2ObjectMap.Entry<RoaringBitmapWriter<RoaringBitmap>> entry : appenders.int2ObjectEntrySet()) {
      consumer.accept(entry.getIntKey(), entry.getValue().get());
    }
  }
}
