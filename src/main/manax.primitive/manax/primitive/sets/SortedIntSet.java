package io.github.andyalvarezdev.primitive.sets;

import io.github.andyalvarezdev.primitive.IntSet;
import io.github.andyalvarezdev.primitive.comparators.IntComparator;

public interface SortedIntSet extends IntSet {
  IntComparator comparator();
  
  SortedIntSet subSet(int paramInt1, int paramInt2);
  
  SortedIntSet headSet(int paramInt);
  
  SortedIntSet tailSet(int paramInt);
  
  int first();
  
  int last();
}


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\sets\SortedIntSet.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */