package io.github.andyalvarezdev.primitive.iterators;

public interface LongListIterator extends LongIterator {
  boolean hasNext();
  
  long nextLong();
  
  boolean hasPrevious();
  
  long previous();
  
  int nextIndex();
  
  int previousIndex();
  
  void remove();
  
  void set(long paramLong);
  
  void add(long paramLong);
}


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\iterators\LongListIterator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */