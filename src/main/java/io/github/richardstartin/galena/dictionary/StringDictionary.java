package io.github.richardstartin.galena.dictionary;

import io.github.richardstartin.galena.Dictionary;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class StringDictionary implements Dictionary {

  private final Object2IntMap<String> map = new Object2IntOpenHashMap<>();

  public StringDictionary() {
    map.defaultReturnValue(-1);
  }

  @Override
  public int encode(int value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int encode(long value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int encode(String value) {
    int code = map.putIfAbsent(value, map.size());
    return code == -1 ? map.size() - 1 : code;
  }
}
