/*      */ package io.github.andyalvarezdev.primitive.lists.impl;
/*      */ 
/*      */ import io.github.andyalvarezdev.primitive.LongCollection;
/*      */ import io.github.andyalvarezdev.primitive.iterators.LongIterator;
/*      */ import io.github.andyalvarezdev.primitive.iterators.LongListIterator;
/*      */ import io.github.andyalvarezdev.primitive.lists.LongList;
/*      */ import io.github.andyalvarezdev.primitive.lists.abstracts.AbstractLongList;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.PrimitiveIterator;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CArrayLongList
/*      */   implements LongList, RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   public static final long serialVersionUID = 6922017381919959104L;
/*   82 */   final transient ReentrantLock lock = new ReentrantLock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile transient long[] array;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final long[] getArray() {
/*   95 */     return this.array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setArray(long[] a) {
/*  103 */     this.array = a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CArrayLongList() {
/*  111 */     setArray(new long[0]);
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
/*      */   public CArrayLongList(LongCollection c) {
/*  124 */     long[] elements = c.toArray();
/*  125 */     setArray(elements);
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
/*      */   public CArrayLongList(long[] toCopyIn) {
/*  137 */     setArray(Arrays.copyOf(toCopyIn, toCopyIn.length));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  147 */     return (getArray()).length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  157 */     return (size() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean eq(long o1, long o2) {
/*  165 */     return (o1 == o2);
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
/*      */   private static int indexOf(long o, long[] elements, int index, int fence) {
/*  180 */     for (int i = index; i < fence; i++) {
/*      */       
/*  182 */       if (o == elements[i])
/*      */       {
/*  184 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  188 */     return -1;
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
/*      */   private static int lastIndexOf(long o, long[] elements, int index) {
/*  201 */     for (int i = index; i >= 0; i--) {
/*      */       
/*  203 */       if (o == elements[i])
/*      */       {
/*  205 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  209 */     return -1;
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
/*      */   public boolean contains(long o) {
/*  224 */     long[] elements = getArray();
/*  225 */     return (indexOf(o, elements, 0, elements.length) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(long o) {
/*  234 */     long[] elements = getArray();
/*  235 */     return indexOf(o, elements, 0, elements.length);
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
/*      */   public int indexOf(long e, int index) {
/*  255 */     long[] elements = getArray();
/*  256 */     return indexOf(e, elements, index, elements.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(long o) {
/*  265 */     long[] elements = getArray();
/*  266 */     return lastIndexOf(o, elements, elements.length - 1);
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
/*      */   public int lastIndexOf(long e, int index) {
/*  287 */     long[] elements = getArray();
/*  288 */     return lastIndexOf(e, elements, index);
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
/*      */   public Object clone() {
/*      */     try {
/*  301 */       CArrayLongList c = (CArrayLongList)super.clone();
/*  302 */       c.resetLock();
/*  303 */       return c;
/*      */     }
/*  305 */     catch (CloneNotSupportedException e) {
/*      */ 
/*      */       
/*  308 */       throw new InternalError();
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
/*      */   public long[] toArray() {
/*  328 */     long[] elements = getArray();
/*  329 */     return Arrays.copyOf(elements, elements.length);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long[] toArray(long[] a) {
/*  374 */     long[] elements = getArray();
/*  375 */     int len = elements.length;
/*  376 */     if (a.length < len)
/*      */     {
/*  378 */       return Arrays.copyOf(elements, len);
/*      */     }
/*      */ 
/*      */     
/*  382 */     System.arraycopy(elements, 0, a, 0, len);
/*  383 */     if (a.length > len)
/*      */     {
/*  385 */       a[len] = 0L;
/*      */     }
/*  387 */     return a;
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
/*      */   public long get(int index) {
/*  401 */     return getArray()[index];
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
/*      */   public long set(int index, long element) {
/*  413 */     ReentrantLock lock = this.lock;
/*  414 */     lock.lock();
/*      */     
/*      */     try {
/*  417 */       long[] elements = getArray();
/*  418 */       long oldValue = elements[index];
/*      */       
/*  420 */       if (oldValue != element) {
/*      */         
/*  422 */         int len = elements.length;
/*  423 */         long[] newElements = Arrays.copyOf(elements, len);
/*  424 */         newElements[index] = element;
/*  425 */         setArray(newElements);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  430 */         setArray(elements);
/*      */       } 
/*  432 */       return oldValue;
/*      */     }
/*      */     finally {
/*      */       
/*  436 */       lock.unlock();
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
/*      */   public boolean add(long e) {
/*  449 */     ReentrantLock lock = this.lock;
/*  450 */     lock.lock();
/*      */     
/*      */     try {
/*  453 */       long[] elements = getArray();
/*  454 */       int len = elements.length;
/*  455 */       long[] newElements = Arrays.copyOf(elements, len + 1);
/*  456 */       newElements[len] = e;
/*  457 */       setArray(newElements);
/*  458 */       return true;
/*      */     }
/*      */     finally {
/*      */       
/*  462 */       lock.unlock();
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
/*      */   public void add(int index, long element) {
/*  476 */     ReentrantLock lock = this.lock;
/*  477 */     lock.lock();
/*      */     
/*      */     try {
/*  480 */       long[] newElements, elements = getArray();
/*  481 */       int len = elements.length;
/*  482 */       if (index > len || index < 0)
/*      */       {
/*  484 */         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + len);
/*      */       }
/*      */       
/*  487 */       int numMoved = len - index;
/*  488 */       if (numMoved == 0) {
/*      */         
/*  490 */         newElements = Arrays.copyOf(elements, len + 1);
/*      */       }
/*      */       else {
/*      */         
/*  494 */         newElements = new long[len + 1];
/*  495 */         System.arraycopy(elements, 0, newElements, 0, index);
/*  496 */         System.arraycopy(elements, index, newElements, index + 1, numMoved);
/*      */       } 
/*  498 */       newElements[index] = element;
/*  499 */       setArray(newElements);
/*      */     }
/*      */     finally {
/*      */       
/*  503 */       lock.unlock();
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
/*      */   public long removeByIndex(int index) {
/*  517 */     ReentrantLock lock = this.lock;
/*  518 */     lock.lock();
/*      */     
/*      */     try {
/*  521 */       long[] elements = getArray();
/*  522 */       int len = elements.length;
/*  523 */       long oldValue = elements[index];
/*  524 */       int numMoved = len - index - 1;
/*  525 */       if (numMoved == 0) {
/*      */         
/*  527 */         setArray(Arrays.copyOf(elements, len - 1));
/*      */       }
/*      */       else {
/*      */         
/*  531 */         long[] newElements = new long[len - 1];
/*  532 */         System.arraycopy(elements, 0, newElements, 0, index);
/*  533 */         System.arraycopy(elements, index + 1, newElements, index, numMoved);
/*  534 */         setArray(newElements);
/*      */       } 
/*  536 */       return oldValue;
/*      */     }
/*      */     finally {
/*      */       
/*  540 */       lock.unlock();
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
/*      */   public boolean remove(long o) {
/*  560 */     ReentrantLock lock = this.lock;
/*  561 */     lock.lock();
/*      */     
/*      */     try {
/*  564 */       long[] elements = getArray();
/*  565 */       int len = elements.length;
/*  566 */       if (len != 0) {
/*      */ 
/*      */ 
/*      */         
/*  570 */         int newlen = len - 1;
/*  571 */         long[] newElements = new long[newlen];
/*      */         int i;
/*  573 */         for (i = 0; i < newlen; i++) {
/*      */           
/*  575 */           if (eq(o, elements[i])) {
/*      */             int k;
/*      */             
/*  578 */             for (k = i + 1; k < len; k++)
/*      */             {
/*  580 */               newElements[k - 1] = elements[k];
/*      */             }
/*  582 */             setArray(newElements);
/*  583 */             k = 1; return k;
/*      */           } 
/*      */ 
/*      */           
/*  587 */           newElements[i] = elements[i];
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  592 */         if (eq(o, elements[newlen])) {
/*      */           
/*  594 */           setArray(newElements);
/*  595 */           i = 1; return i;
/*      */         } 
/*      */       } 
/*  598 */       return false;
/*      */     }
/*      */     finally {
/*      */       
/*  602 */       lock.unlock();
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
/*      */   private void removeRange(int fromIndex, int toIndex) {
/*  621 */     ReentrantLock lock = this.lock;
/*  622 */     lock.lock();
/*      */     
/*      */     try {
/*  625 */       long[] elements = getArray();
/*  626 */       int len = elements.length;
/*      */       
/*  628 */       if (fromIndex < 0 || fromIndex >= len || toIndex > len || toIndex < fromIndex)
/*      */       {
/*  630 */         throw new IndexOutOfBoundsException();
/*      */       }
/*  632 */       int newlen = len - toIndex - fromIndex;
/*  633 */       int numMoved = len - toIndex;
/*  634 */       if (numMoved == 0)
/*      */       {
/*  636 */         setArray(Arrays.copyOf(elements, newlen));
/*      */       }
/*      */       else
/*      */       {
/*  640 */         long[] newElements = new long[newlen];
/*  641 */         System.arraycopy(elements, 0, newElements, 0, fromIndex);
/*  642 */         System.arraycopy(elements, toIndex, newElements, fromIndex, numMoved);
/*  643 */         setArray(newElements);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  648 */       lock.unlock();
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
/*      */   public boolean addIfAbsent(long e) {
/*  660 */     ReentrantLock lock = this.lock;
/*  661 */     lock.lock();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  666 */       long[] elements = getArray();
/*  667 */       int len = elements.length;
/*  668 */       long[] newElements = new long[len + 1]; int i;
/*  669 */       for (i = 0; i < len; i++) {
/*      */         
/*  671 */         if (eq(e, elements[i]))
/*      */         {
/*  673 */           return false;
/*      */         }
/*      */ 
/*      */         
/*  677 */         newElements[i] = elements[i];
/*      */       } 
/*      */       
/*  680 */       newElements[len] = e;
/*  681 */       setArray(newElements);
/*  682 */       i = 1; return i;
/*      */     }
/*      */     finally {
/*      */       
/*  686 */       lock.unlock();
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
/*      */   public boolean containsAll(LongCollection c) {
/*  703 */     long[] elements = getArray();
/*  704 */     int len = elements.length;
/*  705 */     for (long e : c.toArray()) {
/*      */       
/*  707 */       if (indexOf(e, elements, 0, len) < 0)
/*      */       {
/*  709 */         return false;
/*      */       }
/*      */     } 
/*  712 */     return true;
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
/*      */   public boolean removeAll(LongCollection c) {
/*  731 */     ReentrantLock lock = this.lock;
/*  732 */     lock.lock();
/*      */     
/*      */     try {
/*  735 */       long[] elements = getArray();
/*  736 */       int len = elements.length;
/*  737 */       if (len != 0) {
/*      */ 
/*      */         
/*  740 */         int newlen = 0;
/*  741 */         long[] temp = new long[len]; int i;
/*  742 */         for (i = 0; i < len; i++) {
/*      */           
/*  744 */           long element = elements[i];
/*  745 */           if (!c.contains(element))
/*      */           {
/*  747 */             temp[newlen++] = element;
/*      */           }
/*      */         } 
/*  750 */         if (newlen != len) {
/*      */           
/*  752 */           setArray(Arrays.copyOf(temp, newlen));
/*  753 */           i = 1; return i;
/*      */         } 
/*      */       } 
/*  756 */       return false;
/*      */     }
/*      */     finally {
/*      */       
/*  760 */       lock.unlock();
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
/*      */   public boolean retainAll(LongCollection c) {
/*  780 */     ReentrantLock lock = this.lock;
/*  781 */     lock.lock();
/*      */     
/*      */     try {
/*  784 */       long[] elements = getArray();
/*  785 */       int len = elements.length;
/*  786 */       if (len != 0) {
/*      */ 
/*      */         
/*  789 */         int newlen = 0;
/*  790 */         long[] temp = new long[len]; int i;
/*  791 */         for (i = 0; i < len; i++) {
/*      */           
/*  793 */           long element = elements[i];
/*  794 */           if (c.contains(element))
/*      */           {
/*  796 */             temp[newlen++] = element;
/*      */           }
/*      */         } 
/*  799 */         if (newlen != len) {
/*      */           
/*  801 */           setArray(Arrays.copyOf(temp, newlen));
/*  802 */           i = 1; return i;
/*      */         } 
/*      */       } 
/*  805 */       return false;
/*      */     }
/*      */     finally {
/*      */       
/*  809 */       lock.unlock();
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
/*      */   public int addAllAbsent(LongCollection c) {
/*  826 */     long[] cs = c.toArray();
/*  827 */     if (cs.length == 0)
/*      */     {
/*  829 */       return 0;
/*      */     }
/*  831 */     long[] uniq = new long[cs.length];
/*  832 */     ReentrantLock lock = this.lock;
/*  833 */     lock.lock();
/*      */     
/*      */     try {
/*  836 */       long[] elements = getArray();
/*  837 */       int len = elements.length;
/*  838 */       int added = 0; int i;
/*  839 */       for (i = 0; i < cs.length; i++) {
/*      */         
/*  841 */         long e = cs[i];
/*  842 */         if (indexOf(e, elements, 0, len) < 0 && indexOf(e, uniq, 0, added) < 0)
/*      */         {
/*  844 */           uniq[added++] = e;
/*      */         }
/*      */       } 
/*  847 */       if (added > 0) {
/*      */         
/*  849 */         long[] newElements = Arrays.copyOf(elements, len + added);
/*  850 */         System.arraycopy(uniq, 0, newElements, len, added);
/*  851 */         setArray(newElements);
/*      */       } 
/*  853 */       i = added; return i;
/*      */     }
/*      */     finally {
/*      */       
/*  857 */       lock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  868 */     ReentrantLock lock = this.lock;
/*  869 */     lock.lock();
/*      */     
/*      */     try {
/*  872 */       setArray(new long[0]);
/*      */     }
/*      */     finally {
/*      */       
/*  876 */       lock.unlock();
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
/*      */   public boolean addAll(LongCollection c) {
/*  893 */     long[] cs = c.toArray();
/*  894 */     if (cs.length == 0)
/*      */     {
/*  896 */       return false;
/*      */     }
/*  898 */     ReentrantLock lock = this.lock;
/*  899 */     lock.lock();
/*      */     
/*      */     try {
/*  902 */       long[] elements = getArray();
/*  903 */       int len = elements.length;
/*  904 */       long[] newElements = Arrays.copyOf(elements, len + cs.length);
/*  905 */       System.arraycopy(cs, 0, newElements, len, cs.length);
/*  906 */       setArray(newElements);
/*  907 */       return true;
/*      */     }
/*      */     finally {
/*      */       
/*  911 */       lock.unlock();
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
/*      */   public boolean addAll(int index, LongCollection c) {
/*  934 */     long[] cs = c.toArray();
/*  935 */     ReentrantLock lock = this.lock;
/*  936 */     lock.lock();
/*      */     
/*      */     try {
/*  939 */       long[] newElements, elements = getArray();
/*  940 */       int len = elements.length;
/*  941 */       if (index > len || index < 0)
/*      */       {
/*  943 */         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + len);
/*      */       }
/*  945 */       if (cs.length == 0)
/*      */       {
/*  947 */         return false;
/*      */       }
/*  949 */       int numMoved = len - index;
/*      */       
/*  951 */       if (numMoved == 0) {
/*      */         
/*  953 */         newElements = Arrays.copyOf(elements, len + cs.length);
/*      */       }
/*      */       else {
/*      */         
/*  957 */         newElements = new long[len + cs.length];
/*  958 */         System.arraycopy(elements, 0, newElements, 0, index);
/*  959 */         System.arraycopy(elements, index, newElements, index + cs.length, numMoved);
/*      */       } 
/*  961 */       System.arraycopy(cs, 0, newElements, index, cs.length);
/*  962 */       setArray(newElements);
/*  963 */       return true;
/*      */     }
/*      */     finally {
/*      */       
/*  967 */       lock.unlock();
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
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/*  985 */     s.defaultWriteObject();
/*      */     
/*  987 */     long[] elements = getArray();
/*  988 */     int len = elements.length;
/*      */     
/*  990 */     s.writeInt(len);
/*      */ 
/*      */     
/*  993 */     for (int i = 0; i < len; i++)
/*      */     {
/*  995 */       s.writeLong(elements[i]);
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
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1011 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1014 */     resetLock();
/*      */ 
/*      */     
/* 1017 */     int len = s.readInt();
/* 1018 */     long[] elements = new long[len];
/*      */ 
/*      */     
/* 1021 */     for (int i = 0; i < len; i++)
/*      */     {
/* 1023 */       elements[i] = s.readLong();
/*      */     }
/* 1025 */     setArray(elements);
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
/*      */   public String toString() {
/* 1040 */     return Arrays.toString(getArray());
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
/*      */   public boolean equals(Object o) {
/* 1060 */     if (o == this)
/*      */     {
/* 1062 */       return true;
/*      */     }
/* 1064 */     if (!(o instanceof LongList))
/*      */     {
/* 1066 */       return false;
/*      */     }
/*      */     
/* 1069 */     LongList list = (LongList)o;
/* 1070 */     PrimitiveIterator.OfLong it = list.iterator();
/* 1071 */     long[] elements = getArray();
/* 1072 */     int len = elements.length;
/* 1073 */     for (int i = 0; i < len; i++) {
/*      */       
/* 1075 */       if (!it.hasNext() || !eq(elements[i], it.nextLong()))
/*      */       {
/* 1077 */         return false;
/*      */       }
/*      */     } 
/* 1080 */     if (it.hasNext())
/*      */     {
/* 1082 */       return false;
/*      */     }
/* 1084 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1095 */     int hashCode = 1;
/* 1096 */     long[] elements = getArray();
/* 1097 */     int len = elements.length;
/* 1098 */     for (int i = 0; i < len; i++) {
/*      */       
/* 1100 */       Object obj = Long.valueOf(elements[i]);
/* 1101 */       hashCode = 31 * hashCode + ((obj == null) ? 0 : obj.hashCode());
/*      */     } 
/* 1103 */     return hashCode;
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
/*      */   public LongIterator iterator() {
/* 1117 */     return (LongIterator)new COWIterator(getArray(), 0);
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
/*      */   public LongListIterator listIterator() {
/* 1129 */     return new COWIterator(getArray(), 0);
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
/*      */   public LongListIterator listIterator(int index) {
/* 1143 */     long[] elements = getArray();
/* 1144 */     int len = elements.length;
/* 1145 */     if (index < 0 || index > len)
/*      */     {
/* 1147 */       throw new IndexOutOfBoundsException("Index: " + index);
/*      */     }
/*      */     
/* 1150 */     return new COWIterator(elements, index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class COWIterator
/*      */     implements LongListIterator
/*      */   {
/*      */     private final long[] snapshot;
/*      */ 
/*      */     
/*      */     private int cursor;
/*      */ 
/*      */ 
/*      */     
/*      */     private COWIterator(long[] elements, int initialCursor) {
/* 1166 */       this.cursor = initialCursor;
/* 1167 */       this.snapshot = elements;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1173 */       return (this.cursor < this.snapshot.length);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1179 */       return (this.cursor > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1185 */       if (!hasNext())
/*      */       {
/* 1187 */         throw new NoSuchElementException();
/*      */       }
/* 1189 */       return this.snapshot[this.cursor++];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long previous() {
/* 1195 */       if (!hasPrevious())
/*      */       {
/* 1197 */         throw new NoSuchElementException();
/*      */       }
/* 1199 */       return this.snapshot[--this.cursor];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1205 */       return this.cursor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1211 */       return this.cursor - 1;
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
/*      */     public void remove() {
/* 1223 */       throw new UnsupportedOperationException();
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
/*      */     public void set(long e) {
/* 1235 */       throw new UnsupportedOperationException();
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
/*      */     public void add(long e) {
/* 1247 */       throw new UnsupportedOperationException();
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
/*      */ 
/*      */   
/*      */   public LongList subList(int fromIndex, int toIndex) {
/* 1274 */     ReentrantLock lock = this.lock;
/* 1275 */     lock.lock();
/*      */     
/*      */     try {
/* 1278 */       long[] elements = getArray();
/* 1279 */       int len = elements.length;
/* 1280 */       if (fromIndex < 0 || toIndex > len || fromIndex > toIndex)
/*      */       {
/* 1282 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 1284 */       return (LongList)new COWSubList(this, fromIndex, toIndex);
/*      */     }
/*      */     finally {
/*      */       
/* 1288 */       lock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class COWSubList
/*      */     extends AbstractLongList
/*      */   {
/*      */     private final CArrayLongList l;
/*      */ 
/*      */ 
/*      */     
/*      */     private final int offset;
/*      */ 
/*      */ 
/*      */     
/*      */     private int size;
/*      */ 
/*      */ 
/*      */     
/*      */     private long[] expectedArray;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private COWSubList(CArrayLongList list, int fromIndex, int toIndex) {
/* 1317 */       this.l = list;
/* 1318 */       this.expectedArray = this.l.getArray();
/* 1319 */       this.offset = fromIndex;
/* 1320 */       this.size = toIndex - fromIndex;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void checkForComodification() {
/* 1326 */       if (this.l.getArray() != this.expectedArray)
/*      */       {
/* 1328 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void rangeCheck(int index) {
/* 1335 */       if (index < 0 || index >= this.size)
/*      */       {
/* 1337 */         throw new IndexOutOfBoundsException("Index: " + index + ",Size: " + this.size);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long set(int index, long element) {
/* 1344 */       ReentrantLock lock = this.l.lock;
/* 1345 */       lock.lock();
/*      */       
/*      */       try {
/* 1348 */         rangeCheck(index);
/* 1349 */         checkForComodification();
/* 1350 */         long x = this.l.set(index + this.offset, element);
/* 1351 */         this.expectedArray = this.l.getArray();
/* 1352 */         return x;
/*      */       }
/*      */       finally {
/*      */         
/* 1356 */         lock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long get(int index) {
/* 1363 */       ReentrantLock lock = this.l.lock;
/* 1364 */       lock.lock();
/*      */       
/*      */       try {
/* 1367 */         rangeCheck(index);
/* 1368 */         checkForComodification();
/* 1369 */         return this.l.get(index + this.offset);
/*      */       }
/*      */       finally {
/*      */         
/* 1373 */         lock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1380 */       ReentrantLock lock = this.l.lock;
/* 1381 */       lock.lock();
/*      */       
/*      */       try {
/* 1384 */         checkForComodification();
/* 1385 */         return this.size;
/*      */       }
/*      */       finally {
/*      */         
/* 1389 */         lock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void add(int index, long element) {
/* 1396 */       ReentrantLock lock = this.l.lock;
/* 1397 */       lock.lock();
/*      */       
/*      */       try {
/* 1400 */         checkForComodification();
/* 1401 */         if (index < 0 || index > this.size)
/*      */         {
/* 1403 */           throw new IndexOutOfBoundsException();
/*      */         }
/* 1405 */         this.l.add(index + this.offset, element);
/* 1406 */         this.expectedArray = this.l.getArray();
/* 1407 */         this.size++;
/*      */       }
/*      */       finally {
/*      */         
/* 1411 */         lock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1418 */       ReentrantLock lock = this.l.lock;
/* 1419 */       lock.lock();
/*      */       
/*      */       try {
/* 1422 */         checkForComodification();
/* 1423 */         this.l.removeRange(this.offset, this.offset + this.size);
/* 1424 */         this.expectedArray = this.l.getArray();
/* 1425 */         this.size = 0;
/*      */       }
/*      */       finally {
/*      */         
/* 1429 */         lock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long removeByIndex(int index) {
/* 1436 */       ReentrantLock lock = this.l.lock;
/* 1437 */       lock.lock();
/*      */       
/*      */       try {
/* 1440 */         rangeCheck(index);
/* 1441 */         checkForComodification();
/* 1442 */         long result = this.l.removeByIndex(index + this.offset);
/* 1443 */         this.expectedArray = this.l.getArray();
/* 1444 */         this.size--;
/* 1445 */         return result;
/*      */       }
/*      */       finally {
/*      */         
/* 1449 */         lock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LongIterator iterator() {
/* 1456 */       ReentrantLock lock = this.l.lock;
/* 1457 */       lock.lock();
/*      */       
/*      */       try {
/* 1460 */         checkForComodification();
/* 1461 */         return (LongIterator)new CArrayLongList.COWSubListIterator(this.l, 0, this.offset, this.size);
/*      */       }
/*      */       finally {
/*      */         
/* 1465 */         lock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator listIterator(int index) {
/* 1471 */       ReentrantLock lock = this.l.lock;
/* 1472 */       lock.lock();
/*      */       
/*      */       try {
/* 1475 */         checkForComodification();
/* 1476 */         if (index < 0 || index > this.size)
/*      */         {
/* 1478 */           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
/*      */         }
/* 1480 */         return new CArrayLongList.COWSubListIterator(this.l, index, this.offset, this.size);
/*      */       }
/*      */       finally {
/*      */         
/* 1484 */         lock.unlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LongList subList(int fromIndex, int toIndex) {
/* 1491 */       ReentrantLock lock = this.l.lock;
/* 1492 */       lock.lock();
/*      */       
/*      */       try {
/* 1495 */         checkForComodification();
/* 1496 */         if (fromIndex < 0 || toIndex > this.size)
/*      */         {
/* 1498 */           throw new IndexOutOfBoundsException();
/*      */         }
/* 1500 */         return (LongList)new COWSubList(this.l, fromIndex + this.offset, toIndex + this.offset);
/*      */       }
/*      */       finally {
/*      */         
/* 1504 */         lock.unlock();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class COWSubListIterator
/*      */     implements LongListIterator
/*      */   {
/*      */     private final LongListIterator i;
/*      */     
/*      */     private final int index;
/*      */     private final int offset;
/*      */     private final int size;
/*      */     
/*      */     private COWSubListIterator(LongList l, int index, int offset, int size) {
/* 1520 */       this.index = index;
/* 1521 */       this.offset = offset;
/* 1522 */       this.size = size;
/* 1523 */       this.i = l.listIterator(index + offset);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1529 */       return (nextIndex() < this.size);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1535 */       if (hasNext())
/*      */       {
/* 1537 */         return this.i.nextLong();
/*      */       }
/*      */ 
/*      */       
/* 1541 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1548 */       return (previousIndex() >= 0);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long previous() {
/* 1554 */       if (hasPrevious())
/*      */       {
/* 1556 */         return this.i.previous();
/*      */       }
/*      */ 
/*      */       
/* 1560 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1567 */       return this.i.nextIndex() - this.offset;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1573 */       return this.i.previousIndex() - this.offset;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1579 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void set(long e) {
/* 1585 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void add(long e) {
/* 1591 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 1596 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*      */   
/*      */   private static final long lockOffset;
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 1603 */       lockOffset = unsafe.objectFieldOffset(CArrayLongList.class.getDeclaredField("lock"));
/*      */     }
/* 1605 */     catch (Exception ex) {
/*      */       
/* 1607 */       throw new Error(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void resetLock() {
/* 1613 */     unsafe.putObjectVolatile(this, lockOffset, new ReentrantLock());
/*      */   }
/*      */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\lists\impl\CArrayLongList.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */