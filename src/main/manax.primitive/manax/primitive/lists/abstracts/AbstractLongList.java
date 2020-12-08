/*     */ package io.github.andyalvarezdev.primitive.lists.abstracts;
/*     */ 
/*     */ import io.github.andyalvarezdev.primitive.AbstractLongCollection;
/*     */ import io.github.andyalvarezdev.primitive.LongCollection;
/*     */ import io.github.andyalvarezdev.primitive.iterators.LongIterator;
/*     */ import io.github.andyalvarezdev.primitive.iterators.LongListIterator;
/*     */ import io.github.andyalvarezdev.primitive.lists.LongList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.PrimitiveIterator;
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
/*     */ public abstract class AbstractLongList
/*     */   extends AbstractLongCollection
/*     */   implements LongList
/*     */ {
/*     */   public boolean add(long e) {
/* 115 */     add(size(), e);
/* 116 */     return true;
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
/*     */   public abstract long get(int paramInt);
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
/* 141 */     throw new UnsupportedOperationException();
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
/*     */   public void add(int index, long element) {
/* 158 */     throw new UnsupportedOperationException();
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
/* 172 */     throw new UnsupportedOperationException();
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
/*     */   public int indexOf(long o) {
/* 190 */     LongListIterator e = listIterator();
/* 191 */     while (e.hasNext()) {
/*     */       
/* 193 */       if (o == e.nextLong())
/*     */       {
/* 195 */         return e.previousIndex();
/*     */       }
/*     */     } 
/* 198 */     return -1;
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
/*     */   public int lastIndexOf(long o) {
/* 214 */     LongListIterator e = listIterator(size());
/* 215 */     while (e.hasPrevious()) {
/*     */       
/* 217 */       if (o == e.previous())
/*     */       {
/* 219 */         return e.nextIndex();
/*     */       }
/*     */     } 
/* 222 */     return -1;
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
/*     */   public void clear() {
/* 246 */     removeRange(0, size());
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
/*     */   public boolean addAll(int index, LongCollection c) {
/* 270 */     boolean modified = false;
/* 271 */     PrimitiveIterator.OfLong e = c.iterator();
/* 272 */     while (e.hasNext()) {
/*     */       
/* 274 */       add(index++, e.nextLong());
/* 275 */       modified = true;
/*     */     } 
/* 277 */     return modified;
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
/*     */   public PrimitiveIterator.OfLong iterator() {
/* 306 */     return (PrimitiveIterator.OfLong)new Itr();
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
/*     */   public LongListIterator listIterator() {
/* 318 */     return listIterator(0);
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
/*     */   public LongListIterator listIterator(int index) {
/* 346 */     if (index < 0 || index > size())
/*     */     {
/* 348 */       throw new IndexOutOfBoundsException("Index: " + index);
/*     */     }
/*     */     
/* 351 */     return new ListItr(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Itr
/*     */     implements LongIterator
/*     */   {
/* 359 */     int cursor = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     int lastRet = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 373 */     int expectedModCount = AbstractLongList.this.modCount;
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 377 */       return (this.cursor != AbstractLongList.this.size());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long nextLong() {
/* 383 */       checkForComodification();
/*     */       
/*     */       try {
/* 386 */         long next = AbstractLongList.this.get(this.cursor);
/* 387 */         this.lastRet = this.cursor++;
/* 388 */         return next;
/*     */       }
/* 390 */       catch (IndexOutOfBoundsException e) {
/*     */         
/* 392 */         checkForComodification();
/* 393 */         throw new NoSuchElementException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 399 */       if (this.lastRet == -1)
/*     */       {
/* 401 */         throw new IllegalStateException();
/*     */       }
/* 403 */       checkForComodification();
/*     */ 
/*     */       
/*     */       try {
/* 407 */         AbstractLongList.this.remove(this.lastRet);
/* 408 */         if (this.lastRet < this.cursor)
/*     */         {
/* 410 */           this.cursor--;
/*     */         }
/* 412 */         this.lastRet = -1;
/* 413 */         this.expectedModCount = AbstractLongList.this.modCount;
/*     */       }
/* 415 */       catch (IndexOutOfBoundsException e) {
/*     */         
/* 417 */         throw new ConcurrentModificationException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     final void checkForComodification() {
/* 423 */       if (AbstractLongList.this.modCount != this.expectedModCount)
/*     */       {
/* 425 */         throw new ConcurrentModificationException();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private class ListItr
/*     */     extends Itr
/*     */     implements LongListIterator {
/*     */     ListItr(int index) {
/* 434 */       this.cursor = index;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 440 */       return (this.cursor != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long previous() {
/* 446 */       checkForComodification();
/*     */       
/*     */       try {
/* 449 */         int i = this.cursor - 1;
/* 450 */         long previous = AbstractLongList.this.get(i);
/* 451 */         this.lastRet = this.cursor = i;
/* 452 */         return previous;
/*     */       }
/* 454 */       catch (IndexOutOfBoundsException e) {
/*     */         
/* 456 */         checkForComodification();
/* 457 */         throw new NoSuchElementException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int nextIndex() {
/* 464 */       return this.cursor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int previousIndex() {
/* 470 */       return this.cursor - 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void set(long e) {
/* 476 */       if (this.lastRet == -1)
/*     */       {
/* 478 */         throw new IllegalStateException();
/*     */       }
/* 480 */       checkForComodification();
/*     */ 
/*     */       
/*     */       try {
/* 484 */         AbstractLongList.this.set(this.lastRet, e);
/* 485 */         this.expectedModCount = AbstractLongList.this.modCount;
/*     */       }
/* 487 */       catch (IndexOutOfBoundsException ex) {
/*     */         
/* 489 */         throw new ConcurrentModificationException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(long e) {
/* 496 */       checkForComodification();
/*     */ 
/*     */       
/*     */       try {
/* 500 */         AbstractLongList.this.add(this.cursor++, e);
/* 501 */         this.lastRet = -1;
/* 502 */         this.expectedModCount = AbstractLongList.this.modCount;
/*     */       }
/* 504 */       catch (IndexOutOfBoundsException ex) {
/*     */         
/* 506 */         throw new ConcurrentModificationException();
/*     */       } 
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
/*     */   public LongList subList(int fromIndex, int toIndex) {
/* 548 */     return (this instanceof java.util.RandomAccess) ? new RandomAccessSubLongList(this, fromIndex, toIndex) : new SubLongList(this, fromIndex, toIndex);
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
/*     */   public boolean equals(Object o) {
/* 578 */     if (o == this)
/*     */     {
/* 580 */       return true;
/*     */     }
/* 582 */     if (!(o instanceof LongList))
/*     */     {
/* 584 */       return false;
/*     */     }
/*     */     
/* 587 */     LongListIterator e1 = listIterator();
/* 588 */     LongListIterator e2 = ((LongList)o).listIterator();
/* 589 */     while (e1.hasNext() && e2.hasNext()) {
/*     */       
/* 591 */       long o1 = e1.nextLong();
/* 592 */       long o2 = e2.nextLong();
/* 593 */       if (o1 != o2)
/*     */       {
/* 595 */         return false;
/*     */       }
/*     */     } 
/* 598 */     return (!e1.hasNext() && !e2.hasNext());
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
/*     */   public int hashCode() {
/* 613 */     long hashCode = 1L;
/* 614 */     PrimitiveIterator.OfLong i = iterator();
/* 615 */     while (i.hasNext()) {
/*     */       
/* 617 */       long obj = i.nextLong();
/* 618 */       hashCode = 31L * hashCode + obj;
/*     */     } 
/* 620 */     return (int)hashCode;
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
/*     */   protected void removeRange(int fromIndex, int toIndex) {
/* 649 */     LongListIterator it = listIterator(fromIndex);
/* 650 */     for (int i = 0, n = toIndex - fromIndex; i < n; i++) {
/*     */       
/* 652 */       it.nextLong();
/* 653 */       it.remove();
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
/* 684 */   protected transient int modCount = 0;
/*     */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\lists\abstracts\AbstractLongList.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */