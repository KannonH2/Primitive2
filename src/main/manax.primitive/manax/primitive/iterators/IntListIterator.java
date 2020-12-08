package io.github.andyalvarezdev.primitive.iterators;

import java.util.PrimitiveIterator;

public interface IntListIterator extends PrimitiveIterator.OfInt {
  boolean hasNext();
  
  int nextInt();
  
  boolean hasPrevious();
  
  int previous();
  
  int nextIndex();
  
  int previousIndex();
  
  void remove();
  
  void set(int paramInt);
  
  void add(int paramInt);
}


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\iterators\IntListIterator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */