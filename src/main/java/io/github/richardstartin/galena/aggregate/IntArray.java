package io.github.richardstartin.galena.aggregate;

import java.util.Arrays;

public class IntArray {

  private int[] array;

  public IntArray(int[] array) {
    this.array = array;
  }

  public void wrap(int[] array) {
    this.array = array;
  }

  public IntArray clone() {
    return new IntArray(Arrays.copyOf(array, array.length));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IntArray intArray = (IntArray) o;
    return Arrays.equals(array, intArray.array);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(array);
  }
}
