package io.github.richardstartin.galena;

import java.util.function.ToIntFunction;

public interface DimensionBinding<T> extends ToIntFunction<T> {
}
