package io.github.andyalvarezdev.primitive.maps;

public interface CIntLongMap extends IntLongMap {
  long putIfAbsent(int paramInt, long paramLong);
  
  boolean remove(int paramInt, long paramLong);
  
  boolean replace(int paramInt, long paramLong1, long paramLong2);
  
  long replace(int paramInt, long paramLong);
}


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\maps\CIntLongMap.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */