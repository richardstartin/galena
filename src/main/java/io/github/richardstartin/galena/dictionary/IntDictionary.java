package io.github.richardstartin.galena.dictionary;

import io.github.richardstartin.galena.Dictionary;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class IntDictionary implements Dictionary {
  private final Int2IntMap map = new Int2IntOpenHashMap();

  public IntDictionary() {
    map.defaultReturnValue(-1);
  }

  @Override
  public int encode(int value) {
    int code = map.putIfAbsent(value, map.size());
    return code == -1 ? map.size() - 1 : code;
  }

  @Override
  public int encode(long value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int encode(String value) {
    throw new UnsupportedOperationException();
  }
}
