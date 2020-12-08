/*     */ package io.github.andyalvarezdev.primitive;
/*     */ 
/*     */ import io.github.andyalvarezdev.primitive.function.IntBiFunction;
/*     */ import io.github.andyalvarezdev.primitive.iterators.LongIterator;
/*     */ import io.github.andyalvarezdev.primitive.lists.LongList;
/*     */ import io.github.andyalvarezdev.primitive.lists.abstracts.AbstractLongList;
/*     */ import io.github.andyalvarezdev.primitive.maps.IntLongMap;
/*     */ import io.github.andyalvarezdev.primitive.maps.LongObjectMap;
/*     */ import io.github.andyalvarezdev.primitive.maps.abstracts.AbstractIntLongMap;
/*     */ import io.github.andyalvarezdev.primitive.pair.IntLong;
/*     */ import io.github.andyalvarezdev.primitive.sets.abstracts.AbstractIntSet;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Containers
/*     */ {
/*  42 */   public static final PrimitiveIterator.OfInt EMPTY_INT_ITERATOR = new EmptyIntIterator();
/*  43 */   public static final LongIterator EMPTY_LONG_ITERATOR = new EmptyLongIterator();
/*     */   
/*  45 */   public static final Container EMPTY_CONTAINER = new EmptyContainer();
/*     */   
/*  47 */   public static final IntList EMPTY_INT_LIST = new EmptyIntList();
/*  48 */   public static final LongList EMPTY_LONG_LIST = (LongList)new EmptyLongList();
/*     */   
/*  50 */   private static final IntSet EMPTY_INT_SET = (IntSet)new EmptyIntSet();
/*  51 */   public static final LongSet EMPTY_LONG_SET = new EmptyLongSet();
/*     */ 
/*     */   
/*  54 */   private static final IntMap EMPTY_INT_MAP = new EmptyIntMap();
/*  55 */   public static final IntLongMap EMPTY_INT_LONG_MAP = (IntLongMap)new EmptyIntLongMap();
/*     */ 
/*     */   
/*  58 */   private static final LongMap EMPTY_LONG_OBJECT_MAP = new EmptyLongMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> IntMap<V> emptyIntMap() {
/*  69 */     return EMPTY_INT_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> LongMap<T> emptyLongMap() {
/*  74 */     return EMPTY_LONG_OBJECT_MAP;
/*     */   }
/*     */   
/*     */   public static IntSet emptyIntSet() {
/*  78 */     return EMPTY_INT_SET;
/*     */   }
/*     */   public static IntList emptyList() {
/*  81 */     return EMPTY_INT_LIST;
/*     */   }
/*     */   
/*     */   public static IntList singletonIntList(int t) {
/*  85 */     return new SingletonIntList(t);
/*     */   }
/*     */ 
/*     */   
/*     */   public static LongList singletonLongList(long t) {
/*  90 */     return (LongList)new SingletonLongList(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrimitiveIterator.OfInt singletonIntIterator(int e) {
/* 101 */     return new SingletonIntIterator(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongIterator singletonLongIterator(long e) {
/* 112 */     return new SingletonLongIterator(e);
/*     */   }
/*     */   
/*     */   private static class SingletonIntIterator
/*     */     implements PrimitiveIterator.OfInt
/*     */   {
/*     */     private boolean _hasNext = true;
/*     */     private final int _value;
/*     */     
/*     */     public SingletonIntIterator(int value) {
/* 122 */       this._value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 128 */       return this._hasNext;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int nextInt() {
/* 134 */       if (this._hasNext) {
/*     */         
/* 136 */         this._hasNext = false;
/* 137 */         return this._value;
/*     */       } 
/* 139 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 145 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SingletonLongIterator
/*     */     implements LongIterator
/*     */   {
/*     */     private boolean _hasNext = true;
/*     */     private final long _value;
/*     */     
/*     */     public SingletonLongIterator(long value) {
/* 156 */       this._value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 162 */       return this._hasNext;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 168 */       if (this._hasNext) {
/*     */         
/* 170 */         this._hasNext = false;
/* 171 */         return this._value;
/*     */       } 
/* 173 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 179 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SingletonIntList
/*     */     extends AbstractIntList
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = -6795777618381165134L;
/*     */     private final int element;
/*     */     
/*     */     SingletonIntList(int obj) {
/* 191 */       this.element = obj;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public PrimitiveIterator.OfInt iterator() {
/* 197 */       return Containers.singletonIntIterator(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 203 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(int obj) {
/* 209 */       return (this.element == obj);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int get(int index) {
/* 215 */       if (index != 0)
/*     */       {
/* 217 */         throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
/*     */       }
/* 219 */       return this.element;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SingletonLongList
/*     */     extends AbstractLongList
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = 4281415105123492902L;
/*     */     private final long element;
/*     */     
/*     */     SingletonLongList(long obj) {
/* 231 */       this.element = obj;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongIterator iterator() {
/* 237 */       return Containers.singletonLongIterator(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 243 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(long obj) {
/* 249 */       return (this.element == obj);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long get(int index) {
/* 255 */       if (index != 0)
/*     */       {
/* 257 */         throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
/*     */       }
/* 259 */       return this.element;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EmptyIntIterator
/*     */     implements PrimitiveIterator.OfInt
/*     */   {
/*     */     public boolean hasNext() {
/* 268 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int nextInt() {
/* 274 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 280 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EmptyLongIterator
/*     */     implements LongIterator
/*     */   {
/*     */     public boolean hasNext() {
/* 289 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 295 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 301 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class AbstractImmutableIntCollection extends AbstractIntCollection {
/* 306 */     public boolean add(int e) { throw new UnsupportedOperationException(); }
/* 307 */     public boolean addAll(IntCollection c) { throw new UnsupportedOperationException(); }
/* 308 */     public void clear() { throw new UnsupportedOperationException(); }
/* 309 */     public boolean remove(int o) { throw new UnsupportedOperationException(); }
/* 310 */     public boolean removeAll(IntCollection c) { throw new UnsupportedOperationException(); }
/* 311 */     public boolean removeIf(IntPredicate filter) { throw new UnsupportedOperationException(); } public boolean retainAll(IntCollection c) {
/* 312 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class AbstractImmutableIntSet
/*     */     extends AbstractImmutableIntCollection
/*     */     implements IntSet {
/*     */     public boolean equals(Object o) {
/* 320 */       if (o == this)
/* 321 */         return true; 
/* 322 */       if (!(o instanceof IntSet)) {
/* 323 */         return false;
/*     */       }
/*     */       
/* 326 */       IntCollection c = (IntCollection)o;
/* 327 */       if (c.size() != size()) {
/* 328 */         return false;
/*     */       }
/* 330 */       for (PrimitiveIterator.OfInt it = iterator(); it.hasNext();) {
/* 331 */         if (!contains(it.nextInt())) {
/* 332 */           return false;
/*     */         }
/*     */       } 
/* 335 */       return true;
/*     */     }
/*     */     
/*     */     public abstract int hashCode();
/*     */   }
/*     */   
/*     */   public static final class IntSet1
/*     */     extends AbstractImmutableIntSet
/*     */     implements Serializable
/*     */   {
/*     */     final int e0;
/*     */     
/*     */     public IntSet1(int e0) {
/* 348 */       this.e0 = e0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 353 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(int o) {
/* 358 */       return (this.e0 == o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 363 */       return this.e0;
/*     */     }
/*     */     
/*     */     public PrimitiveIterator.OfInt iterator()
/*     */     {
/* 368 */       return new PrimitiveIterator.OfInt() {
/* 369 */           private int idx = Containers.IntSet1.this.size();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 373 */             return (this.idx > 0);
/*     */           }
/*     */           
/*     */           public int nextInt()
/*     */           {
/* 378 */             if (this.idx++ == 1) {
/* 379 */               return Containers.IntSet1.this.e0;
/*     */             }
/* 381 */             throw new NoSuchElementException(); } }; } } class null implements PrimitiveIterator.OfInt { public int nextInt() { if (this.idx++ == 1) return Containers.IntSet1.this.e0;  throw new NoSuchElementException(); }
/*     */      private int idx; null() {
/*     */       this.idx = Containers.IntSet1.this.size();
/*     */     }
/*     */     public boolean hasNext() {
/*     */       return (this.idx > 0);
/*     */     } }
/*     */   public static abstract class ImmutabbleIntMap<V> extends AbstractIntMap<V> implements Serializable { public void clear() {
/* 389 */       throw new UnsupportedOperationException(); }
/* 390 */     public V compute(int key, IntBiFunction<? super V, ? extends V> rf) { throw new UnsupportedOperationException(); }
/* 391 */     public V computeIfAbsent(int key, IntFunction<? extends V> mf) { throw new UnsupportedOperationException(); }
/* 392 */     public V computeIfPresent(int key, IntBiFunction<? super V, ? extends V> rf) { throw new UnsupportedOperationException(); }
/* 393 */     public V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> rf) { throw new UnsupportedOperationException(); }
/* 394 */     public V put(int key, V value) { throw new UnsupportedOperationException(); }
/* 395 */     public void putAll(IntMap<? extends V> m) { throw new UnsupportedOperationException(); }
/* 396 */     public V putIfAbsent(int key, V value) { throw new UnsupportedOperationException(); }
/* 397 */     public V remove(int key) { throw new UnsupportedOperationException(); }
/* 398 */     public boolean remove(int key, Object value) { throw new UnsupportedOperationException(); }
/* 399 */     public V replace(int key, V value) { throw new UnsupportedOperationException(); }
/* 400 */     public boolean replace(int key, V oldValue, V newValue) { throw new UnsupportedOperationException(); } public void replaceAll(IntBiFunction<? super V, ? extends V> f) {
/* 401 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   public static class IntMap1<V> extends ImmutabbleIntMap<V> {
/*     */     private final int k0;
/*     */     private final V v0;
/*     */     
/*     */     IntMap1(int k0, V v0) {
/* 409 */       this.k0 = ((Integer)Objects.<Integer>requireNonNull(Integer.valueOf(k0))).intValue();
/* 410 */       this.v0 = Objects.requireNonNull(v0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<IntMap.Entry<V>> entrySet() {
/* 415 */       return Set.of(IntMap.entry(this.k0, this.v0));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(int o) {
/* 420 */       return (o == this.k0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object o) {
/* 425 */       return o.equals(this.v0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 430 */       return this.k0 ^ this.v0.hashCode();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EmptyIntMap<V>
/*     */     extends ImmutabbleIntMap<V> {
/*     */     public static final long serialVersionUID = -5514480375351672864L;
/*     */     
/*     */     public int size() {
/* 439 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 444 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(int key) {
/* 449 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object value) {
/* 454 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(int key) {
/* 459 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 464 */       return Containers.EMPTY_INT_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<V> values() {
/* 469 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<IntMap.Entry<V>> entrySet() {
/* 475 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 481 */       return (o instanceof IntMap && ((IntMap)o).size() == 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 487 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 493 */       return Containers.EMPTY_INT_MAP;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EmptyLongMap<T>
/*     */     extends AbstractLongMap<T>
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7578467527233523710L;
/*     */     
/*     */     public int size() {
/* 504 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 510 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsKey(long key) {
/* 516 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object value) {
/* 522 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public T get(long key) {
/* 528 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongSet keySet() {
/* 534 */       return Containers.EMPTY_LONG_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Collection<T> values() {
/* 540 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<LongMap.Entry<T>> entrySet() {
/* 546 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 552 */       return (o instanceof LongObjectMap && ((LongObjectMap)o).size() == 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 558 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 564 */       return Containers.EMPTY_LONG_OBJECT_MAP;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EmptyIntLongMap
/*     */     extends AbstractIntLongMap
/*     */     implements Serializable
/*     */   {
/*     */     public static final long serialVersionUID = 2323155007002525853L;
/*     */     
/*     */     public int size() {
/* 576 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 582 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsKey(int key) {
/* 588 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(long value) {
/* 594 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long get(int key) {
/* 600 */       return Constants.DEFAULT_LONG_VALUE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 606 */       return Containers.EMPTY_INT_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 612 */       return (LongCollection)Containers.EMPTY_LONG_LIST;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<IntLong> entrySet() {
/* 618 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 624 */       return (o instanceof IntLongMap && ((IntLongMap)o).size() == 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 630 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 636 */       return Containers.EMPTY_INT_MAP;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EmptyContainer
/*     */     implements Container
/*     */   {
/*     */     public int size() {
/* 645 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 651 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 657 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EmptyIntSet
/*     */     extends AbstractIntSet
/*     */     implements Serializable
/*     */   {
/*     */     public static final long serialVersionUID = 8067917265294829951L;
/*     */     
/*     */     public PrimitiveIterator.OfInt iterator() {
/* 668 */       return Containers.EMPTY_INT_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 674 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(int obj) {
/* 680 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 686 */       return Containers.EMPTY_INT_SET;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EmptyLongSet
/*     */     extends AbstractLongSet
/*     */     implements Serializable
/*     */   {
/*     */     public static final long serialVersionUID = 8067917265294829951L;
/*     */     
/*     */     public LongIterator iterator() {
/* 697 */       return Containers.EMPTY_LONG_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 703 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(long obj) {
/* 709 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 715 */       return Containers.EMPTY_LONG_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EmptyIntList
/*     */     extends AbstractIntList
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = 7062789284942705902L;
/*     */     
/*     */     public int size() {
/* 727 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(int obj) {
/* 733 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int get(int index) {
/* 739 */       throw new IndexOutOfBoundsException("Index: " + index);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 745 */       return Containers.EMPTY_INT_LIST;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EmptyLongList
/*     */     extends AbstractLongList
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = 8054308266104274168L;
/*     */     
/*     */     public int size() {
/* 756 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(long obj) {
/* 762 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long get(int index) {
/* 768 */       throw new IndexOutOfBoundsException("Index: " + index);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 774 */       return Containers.EMPTY_INT_LIST;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\Containers.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */