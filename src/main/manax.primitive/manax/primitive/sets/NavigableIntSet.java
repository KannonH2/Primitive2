package io.github.andyalvarezdev.primitive.sets;

import java.util.PrimitiveIterator;

public interface NavigableIntSet extends SortedIntSet {
  int lower(int paramInt);
  
  int floor(int paramInt);
  
  int ceiling(int paramInt);
  
  int higher(int paramInt);
  
  int pollFirst();
  
  int pollLast();
  
  PrimitiveIterator.OfInt iterator();
  
  NavigableIntSet descendingSet();
  
  PrimitiveIterator.OfInt descendingIterator();
  
  NavigableIntSet subSet(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2);
  
  NavigableIntSet headSet(int paramInt, boolean paramBoolean);
  
  NavigableIntSet tailSet(int paramInt, boolean paramBoolean);
  
  SortedIntSet subSet(int paramInt1, int paramInt2);
  
  SortedIntSet headSet(int paramInt);
  
  SortedIntSet tailSet(int paramInt);
}


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\sets\NavigableIntSet.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */