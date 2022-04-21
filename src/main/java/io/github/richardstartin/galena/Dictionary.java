package io.github.richardstartin.galena;

public interface Dictionary {

  int encode(int value);

  int encode(long value);

  int encode(String value);
}
