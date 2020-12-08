/*     */ package io.github.andyalvarezdev.primitive.lists.impl;
/*     */ 
/*     */ import io.github.andyalvarezdev.primitive.LongCollection;
/*     */ import io.github.andyalvarezdev.primitive.lists.LongList;
/*     */ import io.github.andyalvarezdev.primitive.lists.abstracts.AbstractLongList;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.RandomAccess;
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
/*     */ public class ArrayLongList
/*     */   extends AbstractLongList
/*     */   implements LongList, RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private transient long[] elementData;
/*     */   private int size;
/*     */   
/*     */   public ArrayLongList(int initialCapacity) {
/* 135 */     if (initialCapacity < 0)
/*     */     {
/* 137 */       throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
/*     */     }
/* 139 */     this.elementData = new long[initialCapacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayLongList() {
/* 147 */     this(10);
/*     */   }
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
/*     */   public ArrayLongList(LongCollection c) {
/* 160 */     this.elementData = c.toArray();
/* 161 */     this.size = this.elementData.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trimToSize() {
/* 171 */     this.modCount++;
/* 172 */     int oldCapacity = this.elementData.length;
/* 173 */     if (this.size < oldCapacity)
/*     */     {
/* 175 */       this.elementData = Arrays.copyOf(this.elementData, this.size);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int minCapacity) {
/* 188 */     this.modCount++;
/* 189 */     int oldCapacity = this.elementData.length;
/* 190 */     if (minCapacity > oldCapacity) {
/*     */       
/* 192 */       long[] oldData = this.elementData;
/* 193 */       int newCapacity = oldCapacity * 3 / 2 + 1;
/* 194 */       if (newCapacity < minCapacity)
/*     */       {
/* 196 */         newCapacity = minCapacity;
/*     */       }
/*     */       
/* 199 */       this.elementData = Arrays.copyOf(this.elementData, newCapacity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 211 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 222 */     return (this.size == 0);
/*     */   }
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
/*     */   public boolean contains(long o) {
/* 237 */     return (indexOf(o) >= 0);
/*     */   }
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
/*     */   public int indexOf(long o) {
/* 250 */     for (int i = 0; i < this.size; i++) {
/*     */       
/* 252 */       if (o == this.elementData[i])
/*     */       {
/* 254 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 258 */     return -1;
/*     */   }
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
/*     */   public int lastIndexOf(long o) {
/* 271 */     for (int i = this.size - 1; i >= 0; i--) {
/*     */       
/* 273 */       if (o == this.elementData[i])
/*     */       {
/* 275 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 279 */     return -1;
/*     */   }
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
/*     */   public Object clone() {
/*     */     try {
/* 293 */       ArrayLongList v = (ArrayLongList)super.clone();
/* 294 */       v.elementData = Arrays.copyOf(this.elementData, this.size);
/* 295 */       v.modCount = 0;
/* 296 */       return v;
/*     */     }
/* 298 */     catch (CloneNotSupportedException e) {
/*     */ 
/*     */       
/* 301 */       throw new InternalError();
/*     */     } 
/*     */   }
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
/*     */   
/*     */   public long[] toArray() {
/* 323 */     return Arrays.copyOf(this.elementData, this.size);
/*     */   }
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
/*     */   public long[] toArray(long[] a) {
/* 354 */     if (a.length < this.size)
/*     */     {
/* 356 */       return Arrays.copyOf(this.elementData, this.size);
/*     */     }
/* 358 */     System.arraycopy(this.elementData, 0, a, 0, this.size);
/* 359 */     if (a.length > this.size)
/*     */     {
/* 361 */       a[this.size] = 0L;
/*     */     }
/* 363 */     return a;
/*     */   }
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
/*     */   public long get(int index) {
/* 377 */     RangeCheck(index);
/*     */     
/* 379 */     return this.elementData[index];
/*     */   }
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
/*     */   public long set(int index, long element) {
/* 393 */     RangeCheck(index);
/*     */     
/* 395 */     long oldValue = this.elementData[index];
/* 396 */     this.elementData[index] = element;
/* 397 */     return oldValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(long e) {
/* 409 */     ensureCapacity(this.size + 1);
/* 410 */     this.elementData[this.size++] = e;
/* 411 */     return true;
/*     */   }
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
/*     */   public void add(int index, long element) {
/* 426 */     if (index > this.size || index < 0)
/*     */     {
/* 428 */       throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
/*     */     }
/*     */     
/* 431 */     ensureCapacity(this.size + 1);
/* 432 */     System.arraycopy(this.elementData, index, this.elementData, index + 1, this.size - index);
/* 433 */     this.elementData[index] = element;
/* 434 */     this.size++;
/*     */   }
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
/*     */   public long removeByIndex(int index) {
/* 448 */     RangeCheck(index);
/*     */     
/* 450 */     this.modCount++;
/* 451 */     long oldValue = this.elementData[index];
/*     */     
/* 453 */     int numMoved = this.size - index - 1;
/* 454 */     if (numMoved > 0)
/*     */     {
/* 456 */       System.arraycopy(this.elementData, index + 1, this.elementData, index, numMoved);
/*     */     }
/* 458 */     this.elementData[--this.size] = 0L;
/*     */     
/* 460 */     return oldValue;
/*     */   }
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
/*     */   public boolean remove(long o) {
/* 479 */     for (int index = 0; index < this.size; index++) {
/*     */       
/* 481 */       if (o == this.elementData[index]) {
/*     */         
/* 483 */         fastRemove(index);
/* 484 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 488 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fastRemove(int index) {
/* 497 */     this.modCount++;
/* 498 */     int numMoved = this.size - index - 1;
/* 499 */     if (numMoved > 0)
/*     */     {
/* 501 */       System.arraycopy(this.elementData, index + 1, this.elementData, index, numMoved);
/*     */     }
/* 503 */     this.elementData[--this.size] = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 512 */     this.modCount++;
/*     */ 
/*     */     
/* 515 */     for (int i = 0; i < this.size; i++)
/*     */     {
/* 517 */       this.elementData[i] = 0L;
/*     */     }
/*     */     
/* 520 */     this.size = 0;
/*     */   }
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
/*     */   public boolean addAll(LongCollection c) {
/* 539 */     long[] a = c.toArray();
/* 540 */     int numNew = a.length;
/* 541 */     ensureCapacity(this.size + numNew);
/* 542 */     System.arraycopy(a, 0, this.elementData, this.size, numNew);
/* 543 */     this.size += numNew;
/* 544 */     return (numNew != 0);
/*     */   }
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
/*     */   public boolean addAll(int index, LongCollection c) {
/* 563 */     if (index > this.size || index < 0)
/*     */     {
/* 565 */       throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
/*     */     }
/*     */     
/* 568 */     long[] a = c.toArray();
/* 569 */     int numNew = a.length;
/* 570 */     ensureCapacity(this.size + numNew);
/*     */     
/* 572 */     int numMoved = this.size - index;
/* 573 */     if (numMoved > 0)
/*     */     {
/* 575 */       System.arraycopy(this.elementData, index, this.elementData, index + numNew, numMoved);
/*     */     }
/*     */     
/* 578 */     System.arraycopy(a, 0, this.elementData, index, numNew);
/* 579 */     this.size += numNew;
/* 580 */     return (numNew != 0);
/*     */   }
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
/*     */   protected void removeRange(int fromIndex, int toIndex) {
/* 599 */     this.modCount++;
/* 600 */     int numMoved = this.size - toIndex;
/* 601 */     System.arraycopy(this.elementData, toIndex, this.elementData, fromIndex, numMoved);
/*     */ 
/*     */     
/* 604 */     int newSize = this.size - toIndex - fromIndex;
/* 605 */     while (this.size != newSize)
/*     */     {
/* 607 */       this.elementData[--this.size] = 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void RangeCheck(int index) {
/* 619 */     if (index >= this.size)
/*     */     {
/* 621 */       throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
/*     */     }
/*     */   }
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
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 640 */     int expectedModCount = this.modCount;
/* 641 */     s.defaultWriteObject();
/*     */ 
/*     */     
/* 644 */     s.writeInt(this.elementData.length);
/*     */ 
/*     */     
/* 647 */     for (int i = 0; i < this.size; i++)
/*     */     {
/* 649 */       s.writeLong(this.elementData[i]);
/*     */     }
/*     */     
/* 652 */     if (this.modCount != expectedModCount)
/*     */     {
/* 654 */       throw new ConcurrentModificationException();
/*     */     }
/*     */   }
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
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 669 */     s.defaultReadObject();
/*     */ 
/*     */     
/* 672 */     int arrayLength = s.readInt();
/* 673 */     long[] a = this.elementData = new long[arrayLength];
/*     */ 
/*     */     
/* 676 */     for (int i = 0; i < this.size; i++)
/*     */     {
/* 678 */       a[i] = s.readLong();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\lists\impl\ArrayLongList.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */