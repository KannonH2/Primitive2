/*      */ package io.github.andyalvarezdev.primitive.maps.impl;
/*      */ 
/*      */ import io.github.andyalvarezdev.primitive.AbstractLongCollection;
/*      */ import io.github.andyalvarezdev.primitive.Constants;
/*      */ import io.github.andyalvarezdev.primitive.HashUtils;
/*      */ import io.github.andyalvarezdev.primitive.IntSet;
/*      */ import io.github.andyalvarezdev.primitive.LongCollection;
/*      */ import io.github.andyalvarezdev.primitive.iterators.LongIterator;
/*      */ import io.github.andyalvarezdev.primitive.maps.IntLongMap;
/*      */ import io.github.andyalvarezdev.primitive.maps.abstracts.AbstractIntLongMap;
/*      */ import io.github.andyalvarezdev.primitive.pair.IntLong;
/*      */ import io.github.andyalvarezdev.primitive.pair.impl.IntLongImpl;
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
/*      */ public class HashIntLongMap
/*      */   extends AbstractIntLongMap
/*      */   implements IntLongMap, Cloneable, Serializable
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
/*      */   public HashIntLongMap(int initialCapacity, float loadFactor) {
/*  187 */     if (initialCapacity < 0)
/*      */     {
/*  189 */       throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
/*      */     }
/*  191 */     if (initialCapacity > 1073741824)
/*      */     {
/*  193 */       initialCapacity = 1073741824;
/*      */     }
/*  195 */     if (loadFactor <= 0.0F || Float.isNaN(loadFactor))
/*      */     {
/*  197 */       throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
/*      */     }
/*      */ 
/*      */     
/*  201 */     int capacity = 1;
/*  202 */     while (capacity < initialCapacity)
/*      */     {
/*  204 */       capacity <<= 1;
/*      */     }
/*      */     
/*  207 */     this.loadFactor = loadFactor;
/*  208 */     this.threshold = (int)(capacity * loadFactor);
/*  209 */     this.table = new Entry[capacity];
/*  210 */     init();
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
/*      */   public HashIntLongMap(int initialCapacity) {
/*  222 */     this(initialCapacity, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashIntLongMap() {
/*  231 */     this.loadFactor = 0.75F;
/*  232 */     this.threshold = 12;
/*  233 */     this.table = new Entry[16];
/*  234 */     init();
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
/*      */   public HashIntLongMap(IntLongMap m) {
/*  248 */     this(Math.max((int)(m.size() / 0.75F) + 1, 16), 0.75F);
/*  249 */     putAllForCreate(m);
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
/*  274 */     int h = HashUtils.hashCode(value);
/*      */ 
/*      */ 
/*      */     
/*  278 */     h ^= h >>> 20 ^ h >>> 12;
/*  279 */     return h ^ h >>> 7 ^ h >>> 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int indexFor(int h, int length) {
/*  287 */     return h & length - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  297 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  307 */     return (this.size == 0);
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
/*      */   public long get(int key) {
/*  330 */     int hash = hash(key);
/*  331 */     for (Entry e = this.table[indexFor(hash, this.table.length)]; e != null; e = e.next) {
/*  332 */       if (e.hash == hash && e.getKey() == key)
/*  333 */         return e.getValue(); 
/*      */     } 
/*  335 */     return Constants.DEFAULT_LONG_VALUE;
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
/*  348 */     return (getEntry(key) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry getEntry(int key) {
/*  358 */     int hash = hash(key);
/*  359 */     for (Entry e = this.table[indexFor(hash, this.table.length)]; e != null; e = e.next) {
/*      */       
/*  361 */       if (e.hash == hash && e.getKey() == key)
/*  362 */         return e; 
/*      */     } 
/*  364 */     return null;
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
/*      */   public long put(int key, long value) {
/*  382 */     int hash = hash(key);
/*  383 */     int i = indexFor(hash, this.table.length);
/*  384 */     for (Entry e = this.table[i]; e != null; e = e.next) {
/*      */       
/*  386 */       if (e.hash == hash && e.getKey() == key) {
/*      */         
/*  388 */         long oldValue = e.setValue(value);
/*  389 */         e.recordAccess(this);
/*  390 */         return oldValue;
/*      */       } 
/*      */     } 
/*      */     
/*  394 */     this.modCount++;
/*  395 */     addEntry(hash, key, value, i);
/*  396 */     return Constants.DEFAULT_LONG_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void putForCreate(int key, long value) {
/*  407 */     int hash = hash(key);
/*  408 */     int i = indexFor(hash, this.table.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  415 */     for (Entry e = this.table[i]; e != null; e = e.next) {
/*      */       
/*  417 */       if (e.hash == hash && e.getKey() == key) {
/*      */         
/*  419 */         e.setValue(value);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  424 */     createEntry(hash, key, value, i);
/*      */   }
/*      */ 
/*      */   
/*      */   private void putAllForCreate(IntLongMap m) {
/*  429 */     for (Iterator<IntLong> i = m.entrySet().iterator(); i.hasNext(); ) {
/*      */       
/*  431 */       IntLong e = i.next();
/*  432 */       putForCreate(e.getKey(), e.getValue());
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
/*  452 */     Entry[] oldTable = this.table;
/*  453 */     int oldCapacity = oldTable.length;
/*  454 */     if (oldCapacity == 1073741824) {
/*      */       
/*  456 */       this.threshold = Integer.MAX_VALUE;
/*      */       
/*      */       return;
/*      */     } 
/*  460 */     Entry[] newTable = new Entry[newCapacity];
/*  461 */     transfer(newTable);
/*  462 */     this.table = newTable;
/*  463 */     this.threshold = (int)(newCapacity * this.loadFactor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void transfer(Entry[] newTable) {
/*  471 */     Entry[] src = this.table;
/*  472 */     int newCapacity = newTable.length;
/*  473 */     for (int j = 0; j < src.length; j++) {
/*      */       
/*  475 */       Entry e = src[j];
/*  476 */       if (e != null) {
/*      */         
/*  478 */         src[j] = null;
/*      */         
/*      */         do {
/*  481 */           Entry next = e.next;
/*  482 */           int i = indexFor(e.hash, newCapacity);
/*  483 */           e.next = newTable[i];
/*  484 */           newTable[i] = e;
/*  485 */           e = next;
/*      */         }
/*  487 */         while (e != null);
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
/*  502 */     int numKeysToBeAdded = m.size();
/*  503 */     if (numKeysToBeAdded == 0) {
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
/*  517 */     if (numKeysToBeAdded > this.threshold) {
/*      */       
/*  519 */       int targetCapacity = (int)(numKeysToBeAdded / this.loadFactor + 1.0F);
/*  520 */       if (targetCapacity > 1073741824)
/*      */       {
/*  522 */         targetCapacity = 1073741824;
/*      */       }
/*  524 */       int newCapacity = this.table.length;
/*  525 */       while (newCapacity < targetCapacity)
/*      */       {
/*  527 */         newCapacity <<= 1;
/*      */       }
/*  529 */       if (newCapacity > this.table.length)
/*      */       {
/*  531 */         resize(newCapacity);
/*      */       }
/*      */     } 
/*      */     
/*  535 */     for (Iterator<IntLong> i = m.entrySet().iterator(); i.hasNext(); ) {
/*      */       
/*  537 */       IntLong e = i.next();
/*  538 */       put(e.getKey(), e.getValue());
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
/*      */   public long remove(int key) {
/*  554 */     Entry e = removeEntryForKey(key);
/*  555 */     return (e == null) ? Constants.DEFAULT_LONG_VALUE : e.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry removeEntryForKey(int key) {
/*  565 */     int hash = hash(key);
/*  566 */     int i = indexFor(hash, this.table.length);
/*  567 */     Entry prev = this.table[i];
/*  568 */     Entry e = prev;
/*      */     
/*  570 */     while (e != null) {
/*      */       
/*  572 */       Entry next = e.next;
/*  573 */       if (e.hash == hash && e.getKey() == key) {
/*      */         
/*  575 */         this.modCount++;
/*  576 */         this.size--;
/*  577 */         if (prev == e) {
/*      */           
/*  579 */           this.table[i] = next;
/*      */         }
/*      */         else {
/*      */           
/*  583 */           prev.next = next;
/*      */         } 
/*  585 */         e.recordRemoval(this);
/*  586 */         return e;
/*      */       } 
/*  588 */       prev = e;
/*  589 */       e = next;
/*      */     } 
/*      */     
/*  592 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry removeMapping(Object o) {
/*  600 */     if (!(o instanceof IntLong))
/*      */     {
/*  602 */       return null;
/*      */     }
/*      */     
/*  605 */     IntLong entry = (IntLong)o;
/*  606 */     Object key = Integer.valueOf(entry.getKey());
/*  607 */     int hash = (key == null) ? 0 : hash(key.hashCode());
/*  608 */     int i = indexFor(hash, this.table.length);
/*  609 */     Entry prev = this.table[i];
/*  610 */     Entry e = prev;
/*      */     
/*  612 */     while (e != null) {
/*      */       
/*  614 */       Entry next = e.next;
/*  615 */       if (e.hash == hash && e.equals(entry)) {
/*      */         
/*  617 */         this.modCount++;
/*  618 */         this.size--;
/*  619 */         if (prev == e) {
/*      */           
/*  621 */           this.table[i] = next;
/*      */         }
/*      */         else {
/*      */           
/*  625 */           prev.next = next;
/*      */         } 
/*  627 */         e.recordRemoval(this);
/*  628 */         return e;
/*      */       } 
/*  630 */       prev = e;
/*  631 */       e = next;
/*      */     } 
/*      */     
/*  634 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  643 */     this.modCount++;
/*  644 */     Entry[] tab = this.table;
/*  645 */     for (int i = 0; i < tab.length; i++)
/*      */     {
/*  647 */       tab[i] = null;
/*      */     }
/*  649 */     this.size = 0;
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
/*  662 */     Entry[] tab = this.table;
/*  663 */     for (int i = 0; i < tab.length; i++) {
/*      */       
/*  665 */       for (Entry e = tab[i]; e != null; e = e.next) {
/*      */         
/*  667 */         if (value == e.getValue())
/*      */         {
/*  669 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  673 */     return false;
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
/*  684 */     HashIntLongMap result = null;
/*      */     
/*      */     try {
/*  687 */       result = (HashIntLongMap)super.clone();
/*      */     }
/*  689 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */     
/*  693 */     result.table = new Entry[this.table.length];
/*  694 */     result.entrySet = null;
/*  695 */     result.modCount = 0;
/*  696 */     result.size = 0;
/*  697 */     result.init();
/*  698 */     result.putAllForCreate(this);
/*      */     
/*  700 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   static class Entry
/*      */     extends IntLongImpl
/*      */   {
/*      */     Entry next;
/*      */     
/*      */     final int hash;
/*      */ 
/*      */     
/*      */     Entry(int h, int k, long v, Entry n) {
/*  713 */       super(k, v);
/*  714 */       this.next = n;
/*  715 */       this.hash = h;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void recordAccess(HashIntLongMap m) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void recordRemoval(HashIntLongMap m) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addEntry(int hash, int key, long value, int bucketIndex) {
/*  740 */     Entry e = this.table[bucketIndex];
/*  741 */     this.table[bucketIndex] = new Entry(hash, key, value, e);
/*  742 */     if (this.size++ >= this.threshold)
/*      */     {
/*  744 */       resize(2 * this.table.length);
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
/*      */   void createEntry(int hash, int key, long value, int bucketIndex) {
/*  758 */     Entry e = this.table[bucketIndex];
/*  759 */     this.table[bucketIndex] = new Entry(hash, key, value, e);
/*  760 */     this.size++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class HashLongIterator
/*      */     implements LongIterator
/*      */   {
/*      */     HashIntLongMap.Entry next;
/*      */ 
/*      */     
/*  772 */     int expectedModCount = HashIntLongMap.this.modCount; HashLongIterator() {
/*  773 */       if (HashIntLongMap.this.size > 0) {
/*      */         
/*  775 */         HashIntLongMap.Entry[] t = HashIntLongMap.this.table;
/*  776 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int index;
/*      */     HashIntLongMap.Entry current;
/*      */     
/*      */     public final boolean hasNext() {
/*  785 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     final HashIntLongMap.Entry nextEntry() {
/*  790 */       if (HashIntLongMap.this.modCount != this.expectedModCount)
/*      */       {
/*  792 */         throw new ConcurrentModificationException();
/*      */       }
/*  794 */       HashIntLongMap.Entry e = this.next;
/*  795 */       if (e == null)
/*      */       {
/*  797 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*  800 */       if ((this.next = e.next) == null) {
/*      */         
/*  802 */         HashIntLongMap.Entry[] t = HashIntLongMap.this.table;
/*  803 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  808 */       this.current = e;
/*  809 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  814 */       if (this.current == null)
/*      */       {
/*  816 */         throw new IllegalStateException();
/*      */       }
/*  818 */       if (HashIntLongMap.this.modCount != this.expectedModCount)
/*      */       {
/*  820 */         throw new ConcurrentModificationException();
/*      */       }
/*  822 */       int k = this.current.getKey();
/*  823 */       this.current = null;
/*  824 */       HashIntLongMap.this.removeEntryForKey(k);
/*  825 */       this.expectedModCount = HashIntLongMap.this.modCount;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class HashIntIterator
/*      */     implements PrimitiveIterator.OfInt
/*      */   {
/*      */     HashIntLongMap.Entry next;
/*      */ 
/*      */     
/*  838 */     int expectedModCount = HashIntLongMap.this.modCount; HashIntIterator() {
/*  839 */       if (HashIntLongMap.this.size > 0) {
/*      */         
/*  841 */         HashIntLongMap.Entry[] t = HashIntLongMap.this.table;
/*  842 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int index;
/*      */     HashIntLongMap.Entry current;
/*      */     
/*      */     public final boolean hasNext() {
/*  851 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     final HashIntLongMap.Entry nextEntry() {
/*  856 */       if (HashIntLongMap.this.modCount != this.expectedModCount)
/*      */       {
/*  858 */         throw new ConcurrentModificationException();
/*      */       }
/*  860 */       HashIntLongMap.Entry e = this.next;
/*  861 */       if (e == null)
/*      */       {
/*  863 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*  866 */       if ((this.next = e.next) == null) {
/*      */         
/*  868 */         HashIntLongMap.Entry[] t = HashIntLongMap.this.table;
/*  869 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  874 */       this.current = e;
/*  875 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  880 */       if (this.current == null)
/*      */       {
/*  882 */         throw new IllegalStateException();
/*      */       }
/*  884 */       if (HashIntLongMap.this.modCount != this.expectedModCount)
/*      */       {
/*  886 */         throw new ConcurrentModificationException();
/*      */       }
/*  888 */       int k = this.current.getKey();
/*  889 */       this.current = null;
/*  890 */       HashIntLongMap.this.removeEntryForKey(k);
/*  891 */       this.expectedModCount = HashIntLongMap.this.modCount;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class HashIterator<E>
/*      */     implements Iterator<E>
/*      */   {
/*      */     HashIntLongMap.Entry next;
/*      */ 
/*      */     
/*  904 */     int expectedModCount = HashIntLongMap.this.modCount; HashIterator() {
/*  905 */       if (HashIntLongMap.this.size > 0) {
/*      */         
/*  907 */         HashIntLongMap.Entry[] t = HashIntLongMap.this.table;
/*  908 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int index;
/*      */     HashIntLongMap.Entry current;
/*      */     
/*      */     public final boolean hasNext() {
/*  917 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     final HashIntLongMap.Entry nextEntry() {
/*  922 */       if (HashIntLongMap.this.modCount != this.expectedModCount)
/*      */       {
/*  924 */         throw new ConcurrentModificationException();
/*      */       }
/*  926 */       HashIntLongMap.Entry e = this.next;
/*  927 */       if (e == null)
/*      */       {
/*  929 */         throw new NoSuchElementException();
/*      */       }
/*      */       
/*  932 */       if ((this.next = e.next) == null) {
/*      */         
/*  934 */         HashIntLongMap.Entry[] t = HashIntLongMap.this.table;
/*  935 */         while (this.index < t.length && (this.next = t[this.index++]) == null);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  940 */       this.current = e;
/*  941 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  946 */       if (this.current == null)
/*      */       {
/*  948 */         throw new IllegalStateException();
/*      */       }
/*  950 */       if (HashIntLongMap.this.modCount != this.expectedModCount)
/*      */       {
/*  952 */         throw new ConcurrentModificationException();
/*      */       }
/*  954 */       int k = this.current.getKey();
/*  955 */       this.current = null;
/*  956 */       HashIntLongMap.this.removeEntryForKey(k);
/*  957 */       this.expectedModCount = HashIntLongMap.this.modCount;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueIterator
/*      */     extends HashLongIterator
/*      */   {
/*      */     public long nextLong() {
/*  965 */       return nextEntry().getValue();
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeyIterator
/*      */     extends HashIntIterator
/*      */   {
/*      */     public int nextInt() {
/*  973 */       return nextEntry().getKey();
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntryIterator
/*      */     extends HashIterator<IntLong>
/*      */   {
/*      */     public IntLong next() {
/*  981 */       return (IntLong)nextEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   PrimitiveIterator.OfInt newKeyIterator() {
/*  988 */     return new KeyIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   LongIterator newValueIterator() {
/*  993 */     return new ValueIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   Iterator<IntLong> newEntryIterator() {
/*  998 */     return new EntryIterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1004 */   private transient Set<IntLong> entrySet = null;
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
/* 1021 */     IntSet ks = this.keySet;
/* 1022 */     return (ks != null) ? ks : (this.keySet = (IntSet)new KeySet());
/*      */   }
/*      */   
/*      */   private final class KeySet
/*      */     extends AbstractIntSet
/*      */   {
/*      */     public PrimitiveIterator.OfInt iterator() {
/* 1029 */       return HashIntLongMap.this.newKeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1034 */       return HashIntLongMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int o) {
/* 1039 */       return HashIntLongMap.this.containsKey(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(int o) {
/* 1044 */       return (HashIntLongMap.this.removeEntryForKey(o) != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1049 */       HashIntLongMap.this.clear();
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
/*      */   public LongCollection values() {
/* 1068 */     LongCollection vs = this.values;
/* 1069 */     return (vs != null) ? vs : (this.values = (LongCollection)new Values());
/*      */   }
/*      */   
/*      */   private final class Values
/*      */     extends AbstractLongCollection
/*      */   {
/*      */     public LongIterator iterator() {
/* 1076 */       return HashIntLongMap.this.newValueIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1081 */       return HashIntLongMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long o) {
/* 1086 */       return HashIntLongMap.this.containsValue(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1091 */       HashIntLongMap.this.clear();
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
/*      */   public Set<IntLong> entrySet() {
/* 1113 */     return entrySet0();
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<IntLong> entrySet0() {
/* 1118 */     Set<IntLong> es = this.entrySet;
/* 1119 */     return (es != null) ? es : (this.entrySet = new EntrySet());
/*      */   }
/*      */   
/*      */   private final class EntrySet
/*      */     extends AbstractSet<IntLong>
/*      */   {
/*      */     public Iterator<IntLong> iterator() {
/* 1126 */       return HashIntLongMap.this.newEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1131 */       if (!(o instanceof IntLong))
/*      */       {
/* 1133 */         return false;
/*      */       }
/* 1135 */       IntLong e = (IntLong)o;
/* 1136 */       HashIntLongMap.Entry candidate = HashIntLongMap.this.getEntry(e.getKey());
/* 1137 */       return (candidate != null && candidate.equals(e));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1142 */       return (HashIntLongMap.this.removeMapping(o) != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1147 */       return HashIntLongMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1152 */       HashIntLongMap.this.clear();
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
/* 1172 */     Iterator<IntLong> i = (this.size > 0) ? entrySet0().iterator() : null;
/*      */ 
/*      */     
/* 1175 */     s.defaultWriteObject();
/*      */ 
/*      */     
/* 1178 */     s.writeInt(this.table.length);
/*      */ 
/*      */     
/* 1181 */     s.writeInt(this.size);
/*      */ 
/*      */     
/* 1184 */     if (i != null)
/*      */     {
/* 1186 */       while (i.hasNext()) {
/*      */         
/* 1188 */         IntLong e = i.next();
/* 1189 */         s.writeInt(e.getKey());
/* 1190 */         s.writeObject(Long.valueOf(e.getValue()));
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
/* 1208 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1211 */     int numBuckets = s.readInt();
/* 1212 */     this.table = new Entry[numBuckets];
/*      */     
/* 1214 */     init();
/*      */ 
/*      */     
/* 1217 */     int size = s.readInt();
/*      */ 
/*      */     
/* 1220 */     for (int i = 0; i < size; i++) {
/*      */       
/* 1222 */       int key = s.readInt();
/* 1223 */       long value = s.readLong();
/* 1224 */       putForCreate(key, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int capacity() {
/* 1231 */     return this.table.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public float loadFactor() {
/* 1236 */     return this.loadFactor;
/*      */   }
/*      */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\maps\impl\HashIntLongMap.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */