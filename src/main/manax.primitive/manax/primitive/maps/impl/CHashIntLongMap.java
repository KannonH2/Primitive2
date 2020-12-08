/*      */ package io.github.andyalvarezdev.primitive.maps.impl;
/*      */ 
/*      */ import io.github.andyalvarezdev.primitive.AbstractLongCollection;
/*      */ import io.github.andyalvarezdev.primitive.Constants;
/*      */ import io.github.andyalvarezdev.primitive.IntSet;
/*      */ import io.github.andyalvarezdev.primitive.LongCollection;
/*      */ import io.github.andyalvarezdev.primitive.iterators.LongIterator;
/*      */ import io.github.andyalvarezdev.primitive.maps.CIntLongMap;
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
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.PrimitiveIterator;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CHashIntLongMap
/*      */   extends AbstractIntLongMap
/*      */   implements CIntLongMap, Serializable
/*      */ {
/*      */   public static final long serialVersionUID = -6788505480067876064L;
/*      */   static final int DEFAULT_INITIAL_CAPACITY = 16;
/*      */   static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*      */   static final int DEFAULT_CONCURRENCY_LEVEL = 16;
/*      */   static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   static final int MAX_SEGMENTS = 65536;
/*      */   static final int RETRIES_BEFORE_LOCK = 2;
/*      */   final int segmentMask;
/*      */   final int segmentShift;
/*      */   final Segment[] segments;
/*      */   transient IntSet keySet;
/*      */   transient Set<IntLong> entrySet;
/*      */   transient LongCollection values;
/*      */   
/*      */   private static int hash(int h) {
/*  179 */     h += h << 15 ^ 0xFFFFCD7D;
/*  180 */     h ^= h >>> 10;
/*  181 */     h += h << 3;
/*  182 */     h ^= h >>> 6;
/*  183 */     h += (h << 2) + (h << 14);
/*  184 */     return h ^ h >>> 16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Segment segmentFor(int hash) {
/*  195 */     return this.segments[hash >>> this.segmentShift & this.segmentMask];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class HashEntry
/*      */   {
/*      */     final int key;
/*      */ 
/*      */ 
/*      */     
/*      */     final int hash;
/*      */ 
/*      */ 
/*      */     
/*      */     volatile long value;
/*      */ 
/*      */ 
/*      */     
/*      */     final HashEntry next;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     HashEntry(int key, HashEntry next, long value) {
/*  221 */       this.key = key;
/*  222 */       this.hash = CHashIntLongMap.hash(key);
/*  223 */       this.next = next;
/*  224 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     static HashEntry[] newArray(int i) {
/*  229 */       return new HashEntry[i];
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
/*      */   static final class Segment
/*      */     extends ReentrantLock
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile transient int count;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     transient int modCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     transient int threshold;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile transient CHashIntLongMap.HashEntry[] table;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final float loadFactor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Segment(int initialCapacity, float lf) {
/*  317 */       this.loadFactor = lf;
/*  318 */       setTable(CHashIntLongMap.HashEntry.newArray(initialCapacity));
/*      */     }
/*      */ 
/*      */     
/*      */     static Segment[] newArray(int i) {
/*  323 */       return new Segment[i];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setTable(CHashIntLongMap.HashEntry[] newTable) {
/*  332 */       this.threshold = (int)(newTable.length * this.loadFactor);
/*  333 */       this.table = newTable;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CHashIntLongMap.HashEntry getFirst(int hash) {
/*  341 */       CHashIntLongMap.HashEntry[] tab = this.table;
/*  342 */       return tab[hash & tab.length - 1];
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
/*      */     long readValueUnderLock(CHashIntLongMap.HashEntry e) {
/*  354 */       lock();
/*      */       
/*      */       try {
/*  357 */         return e.value;
/*      */       }
/*      */       finally {
/*      */         
/*  361 */         unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     long get(int key, int hash) {
/*  369 */       if (this.count != 0) {
/*      */         
/*  371 */         CHashIntLongMap.HashEntry e = getFirst(hash);
/*  372 */         while (e != null) {
/*      */           
/*  374 */           if (e.key == key)
/*      */           {
/*  376 */             return e.value;
/*      */           }
/*  378 */           e = e.next;
/*      */         } 
/*      */       } 
/*  381 */       return Constants.DEFAULT_LONG_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean containsKey(int key, int hash) {
/*  386 */       if (this.count != 0) {
/*      */         
/*  388 */         CHashIntLongMap.HashEntry e = getFirst(hash);
/*  389 */         while (e != null) {
/*      */           
/*  391 */           if (e.key == key)
/*  392 */             return true; 
/*  393 */           e = e.next;
/*      */         } 
/*      */       } 
/*  396 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean containsValue(long value) {
/*  401 */       if (this.count != 0) {
/*      */         
/*  403 */         CHashIntLongMap.HashEntry[] tab = this.table;
/*  404 */         int len = tab.length;
/*  405 */         for (int i = 0; i < len; i++) {
/*      */           
/*  407 */           for (CHashIntLongMap.HashEntry e = tab[i]; e != null; e = e.next) {
/*      */             
/*  409 */             long v = e.value;
/*  410 */             if (value == v)
/*      */             {
/*  412 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*  417 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean replace(int key, int hash, long oldValue, long newValue) {
/*  422 */       lock();
/*      */       
/*      */       try {
/*  425 */         CHashIntLongMap.HashEntry e = getFirst(hash);
/*  426 */         while (e != null && key != e.key) {
/*  427 */           e = e.next;
/*      */         }
/*  429 */         boolean replaced = false;
/*  430 */         if (e != null && oldValue == e.value) {
/*      */           
/*  432 */           replaced = true;
/*  433 */           e.value = newValue;
/*      */         } 
/*  435 */         return replaced;
/*      */       }
/*      */       finally {
/*      */         
/*  439 */         unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     long replace(int key, int hash, long newValue) {
/*  445 */       lock();
/*      */       
/*      */       try {
/*  448 */         CHashIntLongMap.HashEntry e = getFirst(hash);
/*  449 */         while (e != null && key != e.key)
/*      */         {
/*  451 */           e = e.next;
/*      */         }
/*      */         
/*  454 */         long oldValue = Constants.DEFAULT_LONG_VALUE;
/*  455 */         if (e != null) {
/*      */           
/*  457 */           oldValue = e.value;
/*  458 */           e.value = newValue;
/*      */         } 
/*  460 */         return oldValue;
/*      */       }
/*      */       finally {
/*      */         
/*  464 */         unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     long put(int key, int hash, long value, boolean onlyIfAbsent) {
/*  471 */       lock();
/*      */       try {
/*      */         long oldValue;
/*  474 */         int c = this.count;
/*  475 */         if (c++ > this.threshold)
/*      */         {
/*  477 */           rehash();
/*      */         }
/*  479 */         CHashIntLongMap.HashEntry[] tab = this.table;
/*  480 */         int index = hash & tab.length - 1;
/*  481 */         CHashIntLongMap.HashEntry first = tab[index];
/*  482 */         CHashIntLongMap.HashEntry e = first;
/*  483 */         while (e != null && key != e.key)
/*      */         {
/*  485 */           e = e.next;
/*      */         }
/*      */ 
/*      */         
/*  489 */         if (e != null) {
/*      */           
/*  491 */           oldValue = e.value;
/*  492 */           if (!onlyIfAbsent)
/*      */           {
/*  494 */             e.value = value;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  499 */           oldValue = Constants.DEFAULT_LONG_VALUE;
/*  500 */           this.modCount++;
/*  501 */           tab[index] = new CHashIntLongMap.HashEntry(key, first, value);
/*  502 */           this.count = c;
/*      */         } 
/*  504 */         return oldValue;
/*      */       }
/*      */       finally {
/*      */         
/*  508 */         unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void rehash() {
/*  514 */       CHashIntLongMap.HashEntry[] oldTable = this.table;
/*  515 */       int oldCapacity = oldTable.length;
/*  516 */       if (oldCapacity >= 1073741824) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  535 */       CHashIntLongMap.HashEntry[] newTable = CHashIntLongMap.HashEntry.newArray(oldCapacity << 1);
/*  536 */       this.threshold = (int)(newTable.length * this.loadFactor);
/*  537 */       int sizeMask = newTable.length - 1;
/*  538 */       for (int i = 0; i < oldCapacity; i++) {
/*      */ 
/*      */ 
/*      */         
/*  542 */         CHashIntLongMap.HashEntry e = oldTable[i];
/*      */         
/*  544 */         if (e != null) {
/*      */           
/*  546 */           CHashIntLongMap.HashEntry next = e.next;
/*  547 */           int idx = e.hash & sizeMask;
/*      */ 
/*      */           
/*  550 */           if (next == null) {
/*      */             
/*  552 */             newTable[idx] = e;
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/*  558 */             CHashIntLongMap.HashEntry lastRun = e;
/*  559 */             int lastIdx = idx;
/*  560 */             for (CHashIntLongMap.HashEntry last = next; last != null; last = last.next) {
/*      */               
/*  562 */               int k = last.hash & sizeMask;
/*  563 */               if (k != lastIdx) {
/*      */                 
/*  565 */                 lastIdx = k;
/*  566 */                 lastRun = last;
/*      */               } 
/*      */             } 
/*  569 */             newTable[lastIdx] = lastRun;
/*      */ 
/*      */             
/*  572 */             for (CHashIntLongMap.HashEntry p = e; p != lastRun; p = p.next) {
/*      */               
/*  574 */               int k = p.hash & sizeMask;
/*  575 */               CHashIntLongMap.HashEntry n = newTable[k];
/*  576 */               newTable[k] = new CHashIntLongMap.HashEntry(p.key, n, p.value);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  581 */       this.table = newTable;
/*      */     }
/*      */ 
/*      */     
/*      */     long remove(int key, int hash) {
/*  586 */       lock();
/*      */       
/*      */       try {
/*  589 */         int c = this.count - 1;
/*  590 */         CHashIntLongMap.HashEntry[] tab = this.table;
/*  591 */         int index = hash & tab.length - 1;
/*  592 */         CHashIntLongMap.HashEntry first = tab[index];
/*  593 */         CHashIntLongMap.HashEntry e = first;
/*  594 */         while (e != null && key != e.key) {
/*  595 */           e = e.next;
/*      */         }
/*  597 */         long oldValue = Constants.DEFAULT_LONG_VALUE;
/*  598 */         if (e != null) {
/*      */           
/*  600 */           oldValue = e.value;
/*      */ 
/*      */ 
/*      */           
/*  604 */           this.modCount++;
/*  605 */           CHashIntLongMap.HashEntry newFirst = e.next;
/*  606 */           for (CHashIntLongMap.HashEntry p = first; p != e; p = p.next)
/*  607 */             newFirst = new CHashIntLongMap.HashEntry(p.key, newFirst, p.value); 
/*  608 */           tab[index] = newFirst;
/*  609 */           this.count = c;
/*      */         } 
/*  611 */         return oldValue;
/*      */       }
/*      */       finally {
/*      */         
/*  615 */         unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     boolean removeTrueIfRemoved(int key, int hash) {
/*  621 */       lock();
/*      */       
/*      */       try {
/*  624 */         int c = this.count - 1;
/*  625 */         CHashIntLongMap.HashEntry[] tab = this.table;
/*  626 */         int index = hash & tab.length - 1;
/*  627 */         CHashIntLongMap.HashEntry first = tab[index];
/*  628 */         CHashIntLongMap.HashEntry e = first;
/*  629 */         while (e != null && key != e.key) {
/*  630 */           e = e.next;
/*      */         }
/*  632 */         if (e != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  637 */           this.modCount++;
/*  638 */           CHashIntLongMap.HashEntry newFirst = e.next;
/*  639 */           for (CHashIntLongMap.HashEntry p = first; p != e; p = p.next)
/*  640 */             newFirst = new CHashIntLongMap.HashEntry(p.key, newFirst, p.value); 
/*  641 */           tab[index] = newFirst;
/*  642 */           this.count = c;
/*      */         } 
/*  644 */         return (e != null);
/*      */       }
/*      */       finally {
/*      */         
/*  648 */         unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     boolean removeWithValue(int key, int hash, long value) {
/*  654 */       lock();
/*      */       
/*      */       try {
/*  657 */         int c = this.count - 1;
/*  658 */         CHashIntLongMap.HashEntry[] tab = this.table;
/*  659 */         int index = hash & tab.length - 1;
/*  660 */         CHashIntLongMap.HashEntry first = tab[index];
/*  661 */         CHashIntLongMap.HashEntry e = first;
/*  662 */         while (e != null && key != e.key) {
/*  663 */           e = e.next;
/*      */         }
/*  665 */         if (e != null) {
/*      */           
/*  667 */           long v = e.value;
/*  668 */           if (value == v) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  673 */             this.modCount++;
/*  674 */             CHashIntLongMap.HashEntry newFirst = e.next;
/*  675 */             for (CHashIntLongMap.HashEntry p = first; p != e; p = p.next)
/*  676 */               newFirst = new CHashIntLongMap.HashEntry(p.key, newFirst, p.value); 
/*  677 */             tab[index] = newFirst;
/*  678 */             this.count = c;
/*      */           } 
/*      */         } 
/*  681 */         return (e != null);
/*      */       }
/*      */       finally {
/*      */         
/*  685 */         unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void clear() {
/*  691 */       if (this.count != 0) {
/*      */         
/*  693 */         lock();
/*      */         
/*      */         try {
/*  696 */           CHashIntLongMap.HashEntry[] tab = this.table;
/*  697 */           for (int i = 0; i < tab.length; i++)
/*      */           {
/*  699 */             tab[i] = null;
/*      */           }
/*  701 */           this.modCount++;
/*  702 */           this.count = 0;
/*      */         }
/*      */         finally {
/*      */           
/*  706 */           unlock();
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CHashIntLongMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
/*  733 */     if (loadFactor <= 0.0F || initialCapacity < 0 || concurrencyLevel <= 0)
/*      */     {
/*  735 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  738 */     if (concurrencyLevel > 65536)
/*      */     {
/*  740 */       concurrencyLevel = 65536;
/*      */     }
/*      */ 
/*      */     
/*  744 */     int sshift = 0;
/*  745 */     int ssize = 1;
/*  746 */     while (ssize < concurrencyLevel) {
/*      */       
/*  748 */       sshift++;
/*  749 */       ssize <<= 1;
/*      */     } 
/*  751 */     this.segmentShift = 32 - sshift;
/*  752 */     this.segmentMask = ssize - 1;
/*  753 */     this.segments = Segment.newArray(ssize);
/*      */     
/*  755 */     if (initialCapacity > 1073741824)
/*      */     {
/*  757 */       initialCapacity = 1073741824;
/*      */     }
/*  759 */     int c = initialCapacity / ssize;
/*  760 */     if (c * ssize < initialCapacity)
/*      */     {
/*  762 */       c++;
/*      */     }
/*  764 */     int cap = 1;
/*  765 */     while (cap < c)
/*      */     {
/*  767 */       cap <<= 1;
/*      */     }
/*      */     
/*  770 */     for (int i = 0; i < this.segments.length; i++)
/*      */     {
/*  772 */       this.segments[i] = new Segment(cap, loadFactor);
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
/*      */   public CHashIntLongMap(int initialCapacity, float loadFactor) {
/*  791 */     this(initialCapacity, loadFactor, 16);
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
/*      */   public CHashIntLongMap(int initialCapacity) {
/*  805 */     this(initialCapacity, 0.75F, 16);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CHashIntLongMap() {
/*  814 */     this(16, 0.75F, 16);
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
/*      */   public CHashIntLongMap(IntLongMap m) {
/*  827 */     this(Math.max((int)(m.size() / 0.75F) + 1, 16), 0.75F, 16);
/*  828 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  838 */     Segment[] segments = this.segments;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  848 */     int[] mc = new int[segments.length];
/*  849 */     int mcsum = 0; int i;
/*  850 */     for (i = 0; i < segments.length; i++) {
/*      */       
/*  852 */       if ((segments[i]).count != 0) {
/*  853 */         return false;
/*      */       }
/*  855 */       mc[i] = (segments[i]).modCount; mcsum += (segments[i]).modCount;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  860 */     if (mcsum != 0)
/*      */     {
/*  862 */       for (i = 0; i < segments.length; i++) {
/*      */         
/*  864 */         if ((segments[i]).count != 0 || mc[i] != (segments[i]).modCount)
/*      */         {
/*  866 */           return false;
/*      */         }
/*      */       } 
/*      */     }
/*  870 */     return true;
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
/*      */   public int size() {
/*  882 */     Segment[] segments = this.segments;
/*  883 */     long sum = 0L;
/*  884 */     long check = 0L;
/*  885 */     int[] mc = new int[segments.length];
/*      */ 
/*      */     
/*  888 */     for (int k = 0; k < 2; k++) {
/*      */       
/*  890 */       check = 0L;
/*  891 */       sum = 0L;
/*  892 */       int mcsum = 0; int i;
/*  893 */       for (i = 0; i < segments.length; i++) {
/*      */         
/*  895 */         sum += (segments[i]).count;
/*  896 */         mc[i] = (segments[i]).modCount; mcsum += (segments[i]).modCount;
/*      */       } 
/*  898 */       if (mcsum != 0)
/*      */       {
/*  900 */         for (i = 0; i < segments.length; i++) {
/*      */           
/*  902 */           check += (segments[i]).count;
/*  903 */           if (mc[i] != (segments[i]).modCount) {
/*      */             
/*  905 */             check = -1L;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*  910 */       if (check == sum) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/*  915 */     if (check != sum) {
/*      */       
/*  917 */       sum = 0L; int i;
/*  918 */       for (i = 0; i < segments.length; i++)
/*      */       {
/*  920 */         segments[i].lock();
/*      */       }
/*  922 */       for (i = 0; i < segments.length; i++)
/*      */       {
/*  924 */         sum += (segments[i]).count;
/*      */       }
/*  926 */       for (i = 0; i < segments.length; i++)
/*      */       {
/*  928 */         segments[i].unlock();
/*      */       }
/*      */     } 
/*  931 */     if (sum > 2147483647L)
/*      */     {
/*  933 */       return Integer.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*  937 */     return (int)sum;
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
/*      */   public long get(int key) {
/*  955 */     int hash = hash(key);
/*  956 */     return segmentFor(hash).get(key, hash);
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
/*      */   public boolean containsKey(int key) {
/*  970 */     int hash = hash(key);
/*  971 */     return segmentFor(hash).containsKey(key, hash);
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
/*      */   public boolean containsValue(long value) {
/*  989 */     Segment[] segments = this.segments;
/*  990 */     int[] mc = new int[segments.length];
/*      */ 
/*      */     
/*  993 */     for (int k = 0; k < 2; k++) {
/*      */       
/*  995 */       int sum = 0;
/*  996 */       int mcsum = 0;
/*  997 */       for (int j = 0; j < segments.length; j++) {
/*      */         
/*  999 */         int c = (segments[j]).count;
/* 1000 */         mc[j] = (segments[j]).modCount; mcsum += (segments[j]).modCount;
/* 1001 */         if (segments[j].containsValue(value))
/*      */         {
/* 1003 */           return true;
/*      */         }
/*      */       } 
/* 1006 */       boolean cleanSweep = true;
/* 1007 */       if (mcsum != 0)
/*      */       {
/* 1009 */         for (int m = 0; m < segments.length; m++) {
/*      */           
/* 1011 */           int c = (segments[m]).count;
/* 1012 */           if (mc[m] != (segments[m]).modCount) {
/*      */             
/* 1014 */             cleanSweep = false;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 1019 */       if (cleanSweep)
/*      */       {
/* 1021 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1025 */     for (int i = 0; i < segments.length; i++)
/*      */     {
/* 1027 */       segments[i].lock();
/*      */     }
/* 1029 */     boolean found = false;
/*      */     try {
/*      */       int j;
/* 1032 */       for (j = 0; j < segments.length; j++) {
/*      */         
/* 1034 */         if (segments[j].containsValue(value)) {
/*      */           
/* 1036 */           found = true;
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 1043 */       for (int j = 0; j < segments.length; j++)
/*      */       {
/* 1045 */         segments[j].unlock();
/*      */       }
/*      */     } 
/* 1048 */     return found;
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
/*      */   public long put(int key, long value) {
/* 1067 */     int hash = hash(key);
/* 1068 */     return segmentFor(hash).put(key, hash, value, false);
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
/*      */   public long putIfAbsent(int key, long value) {
/* 1080 */     int hash = hash(key);
/* 1081 */     return segmentFor(hash).put(key, hash, value, true);
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
/*      */   public void putAll(IntLongMap m) {
/* 1093 */     for (IntLong e : m.entrySet())
/*      */     {
/* 1095 */       put(e.getKey(), e.getValue());
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
/*      */   public long remove(int key) {
/* 1110 */     int hash = hash(key);
/* 1111 */     return segmentFor(hash).remove(key, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int key, long value) {
/* 1121 */     int hash = hash(key);
/* 1122 */     return segmentFor(hash).removeWithValue(key, hash, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(int key, long oldValue, long newValue) {
/* 1132 */     int hash = hash(key);
/* 1133 */     return segmentFor(hash).replace(key, hash, oldValue, newValue);
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
/*      */   public long replace(int key, long value) {
/* 1145 */     int hash = hash(key);
/* 1146 */     return segmentFor(hash).replace(key, hash, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/* 1155 */     for (int i = 0; i < this.segments.length; i++) {
/* 1156 */       this.segments[i].clear();
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
/*      */   public IntSet keySet() {
/* 1178 */     IntSet ks = this.keySet;
/* 1179 */     return (ks != null) ? ks : (this.keySet = (IntSet)new KeySet());
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
/*      */   public LongCollection values() {
/* 1201 */     LongCollection vs = this.values;
/* 1202 */     return (vs != null) ? vs : (this.values = (LongCollection)new Values());
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
/*      */   public Set<IntLong> entrySet() {
/* 1224 */     Set<IntLong> es = this.entrySet;
/* 1225 */     return (es != null) ? es : (this.entrySet = new EntrySet());
/*      */   }
/*      */ 
/*      */   
/*      */   abstract class HashIterator
/*      */   {
/*      */     int nextSegmentIndex;
/*      */     
/*      */     int nextTableIndex;
/*      */     
/*      */     CHashIntLongMap.HashEntry[] currentTable;
/*      */     
/*      */     CHashIntLongMap.HashEntry nextEntry;
/*      */     CHashIntLongMap.HashEntry lastReturned;
/*      */     
/*      */     HashIterator() {
/* 1241 */       this.nextSegmentIndex = CHashIntLongMap.this.segments.length - 1;
/* 1242 */       this.nextTableIndex = -1;
/* 1243 */       advance();
/*      */     }
/*      */ 
/*      */     
/*      */     final void advance() {
/* 1248 */       if (this.nextEntry != null && (this.nextEntry = this.nextEntry.next) != null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1253 */       while (this.nextTableIndex >= 0) {
/*      */         
/* 1255 */         if ((this.nextEntry = this.currentTable[this.nextTableIndex--]) != null) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1261 */       while (this.nextSegmentIndex >= 0) {
/*      */         
/* 1263 */         CHashIntLongMap.Segment seg = CHashIntLongMap.this.segments[this.nextSegmentIndex--];
/* 1264 */         if (seg.count != 0) {
/*      */           
/* 1266 */           this.currentTable = seg.table;
/* 1267 */           for (int j = this.currentTable.length - 1; j >= 0; j--) {
/*      */             
/* 1269 */             if ((this.nextEntry = this.currentTable[j]) != null) {
/*      */               
/* 1271 */               this.nextTableIndex = j - 1;
/*      */               return;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1281 */       return (this.nextEntry != null);
/*      */     }
/*      */ 
/*      */     
/*      */     CHashIntLongMap.HashEntry nextEntry() {
/* 1286 */       if (this.nextEntry == null)
/*      */       {
/* 1288 */         throw new NoSuchElementException();
/*      */       }
/* 1290 */       this.lastReturned = this.nextEntry;
/* 1291 */       advance();
/* 1292 */       return this.lastReturned;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1297 */       if (this.lastReturned == null)
/*      */       {
/* 1299 */         throw new IllegalStateException();
/*      */       }
/* 1301 */       CHashIntLongMap.this.remove(this.lastReturned.key);
/* 1302 */       this.lastReturned = null;
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeyIterator
/*      */     extends HashIterator
/*      */     implements PrimitiveIterator.OfInt {
/*      */     public int nextInt() {
/* 1310 */       return (nextEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   final class ValueIterator
/*      */     extends HashIterator
/*      */     implements LongIterator {
/*      */     public long nextLong() {
/* 1318 */       return (nextEntry()).value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final class WriteThroughEntry
/*      */     extends IntLongImpl
/*      */   {
/*      */     WriteThroughEntry(int k, long v) {
/* 1330 */       super(k, v);
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
/*      */     public long setValue(long value) {
/* 1344 */       long v = super.setValue(value);
/* 1345 */       CHashIntLongMap.this.put(getKey(), value);
/* 1346 */       return v;
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntryIterator
/*      */     extends HashIterator
/*      */     implements Iterator<IntLong> {
/*      */     public IntLong next() {
/* 1354 */       CHashIntLongMap.HashEntry e = nextEntry();
/* 1355 */       return (IntLong)new CHashIntLongMap.WriteThroughEntry(e.key, e.value);
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeySet
/*      */     extends AbstractIntSet
/*      */   {
/*      */     public PrimitiveIterator.OfInt iterator() {
/* 1363 */       return new CHashIntLongMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1368 */       return CHashIntLongMap.this.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int o) {
/* 1373 */       return CHashIntLongMap.this.containsKey(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(int o) {
/* 1378 */       int hash = CHashIntLongMap.hash(o);
/* 1379 */       return CHashIntLongMap.this.segmentFor(hash).removeTrueIfRemoved(o, hash);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1384 */       CHashIntLongMap.this.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   final class Values
/*      */     extends AbstractLongCollection
/*      */   {
/*      */     public LongIterator iterator() {
/* 1392 */       return new CHashIntLongMap.ValueIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1397 */       return CHashIntLongMap.this.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long o) {
/* 1402 */       return CHashIntLongMap.this.containsValue(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1407 */       CHashIntLongMap.this.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntrySet
/*      */     extends AbstractSet<IntLong>
/*      */   {
/*      */     public Iterator<IntLong> iterator() {
/* 1415 */       return new CHashIntLongMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1420 */       if (!(o instanceof IntLong))
/*      */       {
/* 1422 */         return false;
/*      */       }
/* 1424 */       IntLong e = (IntLong)o;
/* 1425 */       long v = CHashIntLongMap.this.get(e.getKey());
/* 1426 */       return (v == e.getValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1431 */       if (!(o instanceof IntLong))
/*      */       {
/* 1433 */         return false;
/*      */       }
/* 1435 */       IntLong e = (IntLong)o;
/* 1436 */       return CHashIntLongMap.this.remove(e.getKey(), e.getValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1441 */       return CHashIntLongMap.this.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1446 */       CHashIntLongMap.this.clear();
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
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1465 */     s.defaultWriteObject();
/*      */     
/* 1467 */     for (int k = 0; k < this.segments.length; k++) {
/*      */       
/* 1469 */       Segment seg = this.segments[k];
/* 1470 */       seg.lock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1488 */     s.writeObject(null);
/* 1489 */     s.writeObject(null);
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
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1502 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1505 */     for (int i = 0; i < this.segments.length; i++)
/*      */     {
/* 1507 */       this.segments[i].setTable(new HashEntry[1]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*      */       while (true) {
/* 1515 */         int key = s.readInt();
/* 1516 */         long value = s.readLong();
/* 1517 */         put(key, value);
/*      */       } 
/* 1519 */     } catch (IOException e) {
/*      */       return;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\maps\impl\CHashIntLongMap.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */