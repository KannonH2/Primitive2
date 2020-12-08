/*      */ package io.github.andyalvarezdev.primitive.maps.impl;
/*      */ 
/*      */ import io.github.andyalvarezdev.primitive.Constants;
/*      */ import io.github.andyalvarezdev.primitive.HashUtils;
/*      */ import io.github.andyalvarezdev.primitive.IntSet;
/*      */ import io.github.andyalvarezdev.primitive.collections.DoubleCollection;
/*      */ import io.github.andyalvarezdev.primitive.collections.abstracts.AbstractDoubleCollection;
/*      */ import io.github.andyalvarezdev.primitive.iterators.DoubleIterator;
/*      */ import io.github.andyalvarezdev.primitive.maps.IntDoubleMap;
/*      */ import io.github.andyalvarezdev.primitive.maps.IntLongMap;
/*      */ import io.github.andyalvarezdev.primitive.maps.abstracts.AbstractIntDoubleMap;
/*      */ import io.github.andyalvarezdev.primitive.pair.IntDouble;
/*      */ import io.github.andyalvarezdev.primitive.pair.IntLong;
/*      */ import io.github.andyalvarezdev.primitive.pair.impl.IntDoubleImpl;
/*      */ import io.github.andyalvarezdev.primitive.sets.abstracts.AbstractIntSet;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.PrimitiveIterator;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HashIntDoubleMap
/*      */   extends AbstractIntDoubleMap
/*      */   implements IntDoubleMap, Cloneable, Serializable
/*      */ {
/*      */   static final int DEFAULT_INITIAL_CAPACITY = 16;
/*      */   static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*      */   transient Entry[] table;
/*      */   transient int size;
/*      */   int threshold;
/*      */   final float loadFactor;
/*      */   volatile transient int modCount;
/*      */   
/*      */   public HashIntDoubleMap(int initialCapacity, float loadFactor) {
/*  170 */     if (initialCapacity < 0)
/*      */     {
/*  172 */       throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
/*      */     }
/*  174 */     if (initialCapacity > 1073741824)
/*      */     {
/*  176 */       initialCapacity = 1073741824;
/*      */     }
/*  178 */     if (loadFactor <= 0.0F || Float.isNaN(loadFactor))
/*      */     {
/*  180 */       throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
/*      */     }
/*      */ 
/*      */     
/*  184 */     int capacity = 1;
/*  185 */     while (capacity < initialCapacity)
/*      */     {
/*  187 */       capacity <<= 1;
/*      */     }
/*      */     
/*  190 */     this.loadFactor = loadFactor;
/*  191 */     this.threshold = (int)(capacity * loadFactor);
/*  192 */     this.table = new Entry[capacity];
/*  193 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashIntDoubleMap(int initialCapacity) {
/*  205 */     this(initialCapacity, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashIntDoubleMap() {
/*  214 */     this.loadFactor = 0.75F;
/*  215 */     this.threshold = 12;
/*  216 */     this.table = new Entry[16];
/*  217 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashIntDoubleMap(IntDoubleMap m) {
/*  231 */     this(Math.max((int)(m.size() / 0.75F) + 1, 16), 0.75F);
/*  232 */     putAllForCreate(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void init() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int hash(int value) {
/*  257 */     int h = HashUtils.hashCode(value);
/*      */ 
/*      */ 
/*      */     
/*  261 */     h ^= h >>> 20 ^ h >>> 12;
/*  262 */     return h ^ h >>> 7 ^ h >>> 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int indexFor(int h, int length) {
/*  270 */     return h & length - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  280 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  290 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double get(int key) {
/*  313 */     int hash = hash(key);
/*  314 */     for (Entry e = this.table[indexFor(hash, this.table.length)]; e != null; e = e.next) {
/*  315 */       if (e.hash == hash && e.getKey() == key)
/*  316 */         return e.getValue(); 
/*      */     } 
/*  318 */     return Constants.DEFAULT_LONG_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKey(int key) {
/*  331 */     return (getEntry(key) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry getEntry(int key) {
/*  341 */     int hash = hash(key);
/*  342 */     for (Entry e = this.table[indexFor(hash, this.table.length)]; e != null; e = e.next) {
/*      */       
/*  344 */       if (e.hash == hash && e.getKey() == key)
/*  345 */         return e; 
/*      */     } 
/*  347 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double put(int key, double value) {
/*  365 */     int hash = hash(key);
/*  366 */     int i = indexFor(hash, this.table.length);
/*  367 */     for (Entry e = this.table[i]; e != null; e = e.next) {
/*      */       
/*  369 */       if (e.hash == hash && e.getKey() == key) {
/*      */         
/*  371 */         double oldValue = e.setValue(value);
/*  372 */         e.recordAccess(this);
/*  373 */         return oldValue;
/*      */       } 
/*      */     } 
/*      */     
/*  377 */     this.modCount++;
/*  378 */     addEntry(hash, key, value, i);
/*  379 */     return Constants.DEFAULT_LONG_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void putForCreate(int key, double value) {
/*  390 */     int hash = hash(key);
/*  391 */     int i = indexFor(hash, this.table.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  398 */     for (Entry e = this.table[i]; e != null; e = e.next) {
/*      */       
/*  400 */       if (e.hash == hash && e.getKey() == key) {
/*      */         
/*  402 */         e.setValue(value);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  407 */     createEntry(hash, key, value, i);
/*      */   }
/*      */ 
/*      */   
/*      */   private void putAllForCreate(IntDoubleMap m) {
/*  412 */     for (Iterator<IntDouble> i = m.entrySet().iterator(); i.hasNext(); ) {
/*      */       
/*  414 */       IntDouble e = i.next();
/*  415 */       putForCreate(e.getKey(), e.getValue());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void resize(int newCapacity) {
/*  435 */     Entry[] oldTable = this.table;
/*  436 */     int oldCapacity = oldTable.length;
/*  437 */     if (oldCapacity == 1073741824) {
/*      */       
/*  439 */       this.threshold = Integer.MAX_VALUE;
/*      */       
/*      */       return;
/*      */     } 
/*  443 */     Entry[] newTable = new Entry[newCapacity];
/*  444 */     transfer(newTable);
/*  445 */     this.table = newTable;
/*  446 */     this.threshold = (int)(newCapacity * this.loadFactor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void transfer(Entry[] newTable) {
/*  454 */     Entry[] src = this.table;
/*  455 */     int newCapacity = newTable.length;
/*  456 */     for (int j = 0; j < src.length; j++) {
/*      */       
/*  458 */       Entry e = src[j];
/*  459 */       if (e != null) {
/*      */         
/*  461 */         src[j] = null;
/*      */         
/*      */         do {
/*  464 */           Entry next = e.next;
/*  465 */           int i = indexFor(e.hash, newCapacity);
/*  466 */           e.next = newTable[i];
/*  467 */           newTable[i] = e;
/*  468 */           e = next;
/*      */         }
/*  470 */         while (e != null);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putAll(IntLongMap m) {
/*  485 */     int numKeysToBeAdded = m.size();
/*  486 */     if (numKeysToBeAdded == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  500 */     if (numKeysToBeAdded > this.threshold) {
/*      */       
/*  502 */       int targetCapacity = (int)(numKeysToBeAdded / this.loadFactor + 1.0F);
/*  503 */       if (targetCapacity > 1073741824)
/*      */       {
/*  505 */         targetCapacity = 1073741824;
/*      */       }
/*  507 */       int newCapacity = this.table.length;
/*  508 */       while (newCapacity < targetCapacity)
/*      */       {
/*  510 */         newCapacity <<= 1;
/*      */       }
/*  512 */       if (newCapacity > this.table.length)
/*      */       {
/*  514 */         resize(newCapacity);
/*      */       }
/*      */     } 
/*      */     
/*  518 */     for (Iterator<IntLong> i = m.entrySet().iterator(); i.hasNext(); ) {
/*      */       
/*  520 */       IntLong e = i.next();
/*  521 */       put(e.getKey(), e.getValue());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double remove(int key) {
/*  537 */     Entry e = removeEntryForKey(key);
/*  538 */     return (e == null) ? Constants.DEFAULT_LONG_VALUE : e.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry removeEntryForKey(int key) {
/*  548 */     int hash = hash(key);
/*  549 */     int i = indexFor(hash, this.table.length);
/*  550 */     Entry prev = this.table[i];
/*  551 */     Entry e = prev;
/*      */     
/*  553 */     while (e != null) {
/*      */       
/*  555 */       Entry next = e.next;
/*  556 */       if (e.hash == hash && e.getKey() == key) {
/*      */         
/*  558 */         this.modCount++;
/*  559 */         this.size--;
/*  560 */         if (prev == e) {
/*      */           
/*  562 */           this.table[i] = next;
/*      */         }
/*      */         else {
/*      */           
/*  566 */           prev.next = next;
/*      */         } 
/*  568 */         e.recordRemoval(this);
/*  569 */         return e;
/*      */       } 
/*  571 */       prev = e;
/*  572 */       e = next;
/*      */     } 
/*      */     
/*  575 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry removeMapping(Object o) {
/*  583 */     if (!(o instanceof IntLong))
/*      */     {
/*  585 */       return null;
/*      */     }
/*      */     
/*  588 */     IntLong entry = (IntLong)o;
/*  589 */     Object key = Integer.valueOf(entry.getKey());
/*  590 */     int hash = (key == null) ? 0 : hash(key.hashCode());
/*  591 */     int i = indexFor(hash, this.table.length);
/*  592 */     Entry prev = this.table[i];
/*  593 */     Entry e = prev;
/*      */     
/*  595 */     while (e != null) {
/*      */       
/*  597 */       Entry next = e.next;
/*  598 */       if (e.hash == hash && e.equals(entry)) {
/*      */         
/*  600 */         this.modCount++;
/*  601 */         this.size--;
/*  602 */         if (prev == e) {
/*      */           
/*  604 */           this.table[i] = next;
/*      */         }
/*      */         else {
/*      */           
/*  608 */           prev.next = next;
/*      */         } 
/*  610 */         e.recordRemoval(this);
/*  611 */         return e;
/*      */       } 
/*  613 */       prev = e;
/*  614 */       e = next;
/*      */     } 
/*      */     
/*  617 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  626 */     this.modCount++;
/*  627 */     Entry[] tab = this.table;
/*  628 */     for (int i = 0; i < tab.length; i++)
/*      */     {
/*  630 */       tab[i] = null;
/*      */     }
/*  632 */     this.size = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsValue(long value) {
/*  645 */     Entry[] tab = this.table;
/*  646 */     for (int i = 0; i < tab.length; i++) {
/*      */       
/*  648 */       for (Entry e = tab[i]; e != null; e = e.next) {
/*      */         
/*  650 */         if (value == e.getValue())
/*      */         {
/*  652 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  656 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*  667 */     HashIntDoubleMap result = null;
/*      */     
/*      */     try {
/*  670 */       result = (HashIntDoubleMap)super.clone();
/*      */     }
/*  672 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */     
/*  676 */     result.table = new Entry[this.table.length];
/*  677 */     result.entrySet = null;
/*  678 */     result.modCount = 0;
/*  679 */     result.size = 0;
/*  680 */     result.init();
/*  681 */     result.putAllForCreate(this);
/*      */     
/*  683 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   static class Entry
/*      */     extends IntDoubleImpl
/*      */   {
/*      */     Entry next;
/*      */     
/*      */     final int hash;
/*      */ 
/*      */     
/*      */     Entry(int h, int k, double v, Entry n) {
/*  696 */       super(k, v);
/*  697 */       this.next = n;
/*  698 */       this.hash = h;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void recordAccess(HashIntDoubleMap m) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void recordRemoval(HashIntDoubleMap m) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addEntry(int hash, int key, double value, int bucketIndex) {
/*  723 */     Entry e = this.table[bucketIndex];
/*  724 */     this.table[bucketIndex] = new Entry(hash, key, value, e);
/*  725 */     if (this.size++ >= this.threshold)
/*      */     {
/*  727 */       resize(2 * this.table.length);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void createEntry(int hash, int key, double value, int bucketIndex) {
/*  741 */     Entry e = this.table[bucketIndex];
/*  742 */     this.table[bucketIndex] = new Entry(hash, key, value, e);
/*  743 */     this.size++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class HashDoubleIterator
/*      */     implements DoubleIterator
/*      */   {
/*      */     HashIntDoubleMap.Entry next;
/*      */ 
/*      */     
/*  755 */     int expectedModCount = HashIntDoubleMap.this.modCount; HashDoubleIterator() {
/*  756 */       if (HashIntDoubleMap.this.size > 0) {
/*      */         
/*  758 */         HashIntDoubleMap.Entry[] t = HashIntDoubleMap.this.table;
/*  759 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int index;
/*      */     HashIntDoubleMap.Entry current;
/*      */     
/*      */     public final boolean hasNext() {
/*  768 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     final HashIntDoubleMap.Entry nextEntry() {
/*  773 */       if (HashIntDoubleMap.this.modCount != this.expectedModCount)
/*      */       {
/*  775 */         throw new ConcurrentModificationException();
/*      */       }
/*  777 */       HashIntDoubleMap.Entry e = this.next;
/*  778 */       if (e == null)
/*      */       {
/*  780 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*  783 */       if ((this.next = e.next) == null) {
/*      */         
/*  785 */         HashIntDoubleMap.Entry[] t = HashIntDoubleMap.this.table;
/*  786 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  791 */       this.current = e;
/*  792 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  797 */       if (this.current == null)
/*      */       {
/*  799 */         throw new IllegalStateException();
/*      */       }
/*  801 */       if (HashIntDoubleMap.this.modCount != this.expectedModCount)
/*      */       {
/*  803 */         throw new ConcurrentModificationException();
/*      */       }
/*  805 */       int k = this.current.getKey();
/*  806 */       this.current = null;
/*  807 */       HashIntDoubleMap.this.removeEntryForKey(k);
/*  808 */       this.expectedModCount = HashIntDoubleMap.this.modCount;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class HashIntIterator
/*      */     implements PrimitiveIterator.OfInt
/*      */   {
/*      */     HashIntDoubleMap.Entry next;
/*      */ 
/*      */     
/*  821 */     int expectedModCount = HashIntDoubleMap.this.modCount; HashIntIterator() {
/*  822 */       if (HashIntDoubleMap.this.size > 0) {
/*      */         
/*  824 */         HashIntDoubleMap.Entry[] t = HashIntDoubleMap.this.table;
/*  825 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int index;
/*      */     HashIntDoubleMap.Entry current;
/*      */     
/*      */     public final boolean hasNext() {
/*  834 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     final HashIntDoubleMap.Entry nextEntry() {
/*  839 */       if (HashIntDoubleMap.this.modCount != this.expectedModCount)
/*      */       {
/*  841 */         throw new ConcurrentModificationException();
/*      */       }
/*  843 */       HashIntDoubleMap.Entry e = this.next;
/*  844 */       if (e == null)
/*      */       {
/*  846 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*  849 */       if ((this.next = e.next) == null) {
/*      */         
/*  851 */         HashIntDoubleMap.Entry[] t = HashIntDoubleMap.this.table;
/*  852 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  857 */       this.current = e;
/*  858 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  863 */       if (this.current == null)
/*      */       {
/*  865 */         throw new IllegalStateException();
/*      */       }
/*  867 */       if (HashIntDoubleMap.this.modCount != this.expectedModCount)
/*      */       {
/*  869 */         throw new ConcurrentModificationException();
/*      */       }
/*  871 */       int k = this.current.getKey();
/*  872 */       this.current = null;
/*  873 */       HashIntDoubleMap.this.removeEntryForKey(k);
/*  874 */       this.expectedModCount = HashIntDoubleMap.this.modCount;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class HashIterator<E>
/*      */     implements Iterator<E>
/*      */   {
/*      */     HashIntDoubleMap.Entry next;
/*      */ 
/*      */     
/*  887 */     int expectedModCount = HashIntDoubleMap.this.modCount; HashIterator() {
/*  888 */       if (HashIntDoubleMap.this.size > 0) {
/*      */         
/*  890 */         HashIntDoubleMap.Entry[] t = HashIntDoubleMap.this.table;
/*  891 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int index;
/*      */     HashIntDoubleMap.Entry current;
/*      */     
/*      */     public final boolean hasNext() {
/*  900 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     final HashIntDoubleMap.Entry nextEntry() {
/*  905 */       if (HashIntDoubleMap.this.modCount != this.expectedModCount)
/*      */       {
/*  907 */         throw new ConcurrentModificationException();
/*      */       }
/*  909 */       HashIntDoubleMap.Entry e = this.next;
/*  910 */       if (e == null)
/*      */       {
/*  912 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*  915 */       if ((this.next = e.next) == null) {
/*      */         
/*  917 */         HashIntDoubleMap.Entry[] t = HashIntDoubleMap.this.table;
/*  918 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  923 */       this.current = e;
/*  924 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  929 */       if (this.current == null)
/*      */       {
/*  931 */         throw new IllegalStateException();
/*      */       }
/*  933 */       if (HashIntDoubleMap.this.modCount != this.expectedModCount)
/*      */       {
/*  935 */         throw new ConcurrentModificationException();
/*      */       }
/*  937 */       int k = this.current.getKey();
/*  938 */       this.current = null;
/*  939 */       HashIntDoubleMap.this.removeEntryForKey(k);
/*  940 */       this.expectedModCount = HashIntDoubleMap.this.modCount;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueIterator
/*      */     extends HashDoubleIterator
/*      */   {
/*      */     public double next() {
/*  948 */       return nextEntry().getValue();
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeyIterator
/*      */     extends HashIntIterator
/*      */   {
/*      */     public int nextInt() {
/*  956 */       return nextEntry().getKey();
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntryIterator
/*      */     extends HashIterator<IntDouble>
/*      */   {
/*      */     public IntDouble next() {
/*  964 */       return (IntDouble)nextEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   PrimitiveIterator.OfInt newKeyIterator() {
/*  971 */     return new KeyIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   DoubleIterator newValueIterator() {
/*  976 */     return new ValueIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   Iterator<IntDouble> newEntryIterator() {
/*  981 */     return new EntryIterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  987 */   private transient Set<IntDouble> entrySet = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 362498820763181265L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
/* 1004 */     IntSet ks = this.keySet;
/* 1005 */     return (ks != null) ? ks : (this.keySet = (IntSet)new KeySet());
/*      */   }
/*      */   
/*      */   private final class KeySet
/*      */     extends AbstractIntSet
/*      */   {
/*      */     public PrimitiveIterator.OfInt iterator() {
/* 1012 */       return HashIntDoubleMap.this.newKeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1017 */       return HashIntDoubleMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int o) {
/* 1022 */       return HashIntDoubleMap.this.containsKey(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(int o) {
/* 1027 */       return (HashIntDoubleMap.this.removeEntryForKey(o) != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1032 */       HashIntDoubleMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleCollection values() {
/* 1051 */     DoubleCollection vs = this.values;
/* 1052 */     return (vs != null) ? vs : (this.values = (DoubleCollection)new Values());
/*      */   }
/*      */   
/*      */   private final class Values
/*      */     extends AbstractDoubleCollection
/*      */   {
/*      */     public DoubleIterator iterator() {
/* 1059 */       return HashIntDoubleMap.this.newValueIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1064 */       return HashIntDoubleMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long o) {
/* 1069 */       return HashIntDoubleMap.this.containsValue(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1074 */       HashIntDoubleMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<IntDouble> entrySet() {
/* 1096 */     return entrySet0();
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<IntDouble> entrySet0() {
/* 1101 */     Set<IntDouble> es = this.entrySet;
/* 1102 */     return (es != null) ? es : (this.entrySet = new EntrySet());
/*      */   }
/*      */   
/*      */   private final class EntrySet
/*      */     extends AbstractSet<IntDouble>
/*      */   {
/*      */     public Iterator<IntDouble> iterator() {
/* 1109 */       return HashIntDoubleMap.this.newEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1114 */       if (!(o instanceof IntLong))
/*      */       {
/* 1116 */         return false;
/*      */       }
/* 1118 */       IntLong e = (IntLong)o;
/* 1119 */       HashIntDoubleMap.Entry candidate = HashIntDoubleMap.this.getEntry(e.getKey());
/* 1120 */       return (candidate != null && candidate.equals(e));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1125 */       return (HashIntDoubleMap.this.removeMapping(o) != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1130 */       return HashIntDoubleMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1135 */       HashIntDoubleMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1155 */     Iterator<IntDouble> i = (this.size > 0) ? entrySet0().iterator() : null;
/*      */ 
/*      */     
/* 1158 */     s.defaultWriteObject();
/*      */ 
/*      */     
/* 1161 */     s.writeInt(this.table.length);
/*      */ 
/*      */     
/* 1164 */     s.writeInt(this.size);
/*      */ 
/*      */     
/* 1167 */     if (i != null)
/*      */     {
/* 1169 */       while (i.hasNext()) {
/*      */         
/* 1171 */         IntDouble e = i.next();
/* 1172 */         s.writeInt(e.getKey());
/* 1173 */         s.writeObject(Double.valueOf(e.getValue()));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1191 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1194 */     int numBuckets = s.readInt();
/* 1195 */     this.table = new Entry[numBuckets];
/*      */     
/* 1197 */     init();
/*      */ 
/*      */     
/* 1200 */     int size = s.readInt();
/*      */ 
/*      */     
/* 1203 */     for (int i = 0; i < size; i++) {
/*      */       
/* 1205 */       int key = s.readInt();
/* 1206 */       long value = s.readLong();
/* 1207 */       putForCreate(key, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int capacity() {
/* 1214 */     return this.table.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public float loadFactor() {
/* 1219 */     return this.loadFactor;
/*      */   }
/*      */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\maps\impl\HashIntDoubleMap.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */