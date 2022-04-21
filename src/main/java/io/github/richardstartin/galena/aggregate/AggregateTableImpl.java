package io.github.richardstartin.galena.aggregate;

import io.github.richardstartin.galena.AggregateColumn;
import io.github.richardstartin.galena.AggregateRow;
import io.github.richardstartin.galena.AggregateTable;
import io.github.richardstartin.galena.InvertedIndex;
import io.github.richardstartin.galena.index.RoaringBitmapIndex;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.roaringbitmap.FastAggregation;
import org.roaringbitmap.RoaringBitmap;

public class AggregateTableImpl implements AggregateTable {

  private final AggregateColumn[] columns;
  private final AggregateRowFlyweight rowFlyweight;
  private final Object2IntMap<IntArray> rowIndex;
  private final IntArray dimensionsFlyweight;
  private final InvertedIndex[] indexes;

  public AggregateTableImpl(int numDimensions, AggregateColumn[] columns) {
    this.columns = columns;
    this.rowFlyweight = new AggregateRowFlyweight(columns);
    this.dimensionsFlyweight = new IntArray(new int[numDimensions]);
    this.rowIndex = new Object2IntOpenHashMap<>();
    this.rowIndex.defaultReturnValue(-1);
    this.indexes = createIndexes(numDimensions);
  }

  @Override
  public int getIntValue(int column, int... coordinates) {
    return columns[column].aggregateInt(intersect(coordinates));
  }

  @Override
  public long getLongValue(int column, int... coordinates) {
    return columns[column].aggregateLong(intersect(coordinates));
  }

  @Override
  public double getDoubleValue(int column, int... coordinates) {
    return columns[column].aggregateDouble(intersect(coordinates));
  }

  private RoaringBitmap intersect(int... coordinates) {
    RoaringBitmap[] bitmaps = new RoaringBitmap[coordinates.length / 2];
    for (int i = 0, j = 0; i < coordinates.length; i += 2, j++) {
      int dimension = coordinates[i];
      int value = coordinates[i+1];
      bitmaps[j] = indexes[dimension].get(value);
    }
    return FastAggregation.and(bitmaps);
  }

  @Override
  public AggregateRow get(int[] dimensions) {
    dimensionsFlyweight.wrap(dimensions);
    int row = rowIndex.getInt(dimensionsFlyweight);
    if (row == -1) {
      row = rowIndex.size();
      rowIndex.put(dimensionsFlyweight.clone(), row);
      index(dimensions, row);
    }
    rowFlyweight.setRow(row);
    return rowFlyweight;
  }

  private void index(int[] dimensions, int row) {
    for (int i = 0; i < dimensions.length; i++) {
      indexes[i].add(dimensions[i], row);
    }
  }

  private static InvertedIndex[] createIndexes(int numDimensions) {
    var indexes = new InvertedIndex[numDimensions];
    for (int i = 0; i < indexes.length; i++) {
      indexes[i] = new RoaringBitmapIndex();
    }
    return indexes;
  }
}
