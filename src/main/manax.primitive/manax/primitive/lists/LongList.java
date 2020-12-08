package io.github.andyalvarezdev.primitive.lists;

import io.github.andyalvarezdev.primitive.LongCollection;
import io.github.andyalvarezdev.primitive.iterators.LongListIterator;
import java.util.PrimitiveIterator;

public interface LongList extends LongCollection {
  int size();
  
  boolean isEmpty();
  
  boolean contains(long paramLong);
  
  PrimitiveIterator.OfLong iterator();
  
  long[] toArray();
  
  long[] toArray(long[] paramArrayOflong);
  
  boolean add(long paramLong);
  
  boolean remove(long paramLong);
  
  boolean containsAll(LongCollection paramLongCollection);
  
  boolean addAll(LongCollection paramLongCollection);
  
  boolean addAll(int paramInt, LongCollection paramLongCollection);
  
  boolean removeAll(LongCollection paramLongCollection);
  
  boolean retainAll(LongCollection paramLongCollection);
  
  void clear();
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  long get(int paramInt);
  
  long set(int paramInt, long paramLong);
  
  void add(int paramInt, long paramLong);
  
  long removeByIndex(int paramInt);
  
  int indexOf(long paramLong);
  
  int lastIndexOf(long paramLong);
  
  LongListIterator listIterator();
  
  LongListIterator listIterator(int paramInt);
  
  LongList subList(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\lists\LongList.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */