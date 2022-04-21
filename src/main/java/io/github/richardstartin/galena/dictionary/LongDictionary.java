package io.github.richardstartin.galena.dictionary;

import io.github.richardstartin.galena.Dictionary;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;

public class LongDictionary implements Dictionary {
  private final Long2IntMap map = new Long2IntOpenHashMap();

  public LongDictionary() {
    map.defaultReturnValue(-1);
  }

  @Override
  public int encode(int value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int encode(long value) {
    int code = map.putIfAbsent(value, map.size());
    return code == -1 ? map.size() - 1 : code;
  }

  @Override
  public int encode(String value) {
    throw new UnsupportedOperationException();
  }
}
