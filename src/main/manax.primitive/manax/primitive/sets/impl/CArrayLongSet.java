/*     */ package io.github.andyalvarezdev.primitive.sets.impl;
/*     */ 
/*     */ import io.github.andyalvarezdev.primitive.AbstractLongSet;
/*     */ import io.github.andyalvarezdev.primitive.IntSet;
/*     */ import io.github.andyalvarezdev.primitive.LongCollection;
/*     */ import io.github.andyalvarezdev.primitive.iterators.LongIterator;
/*     */ import io.github.andyalvarezdev.primitive.lists.impl.CArrayLongList;
/*     */ import java.io.Serializable;
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
/*     */ public class CArrayLongSet
/*     */   extends AbstractLongSet
/*     */   implements Serializable
/*     */ {
/*     */   public static final long serialVersionUID = 2894695535825095335L;
/*  99 */   private final CArrayLongList al = new CArrayLongList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CArrayLongSet(LongCollection c) {
/* 111 */     this();
/* 112 */     this.al.addAllAbsent(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 122 */     return this.al.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 132 */     return this.al.isEmpty();
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
/*     */   public boolean contains(int o) {
/* 146 */     return this.al.contains(o);
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
/*     */   public long[] toArray() {
/* 168 */     return this.al.toArray();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] toArray(long[] a) {
/* 216 */     return this.al.toArray(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 225 */     this.al.clear();
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
/*     */   public boolean remove(long o) {
/* 242 */     return this.al.remove(o);
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
/*     */   public boolean add(long e) {
/* 259 */     return this.al.addIfAbsent(e);
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
/*     */   public boolean containsAll(LongCollection c) {
/* 275 */     return this.al.containsAll(c);
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
/*     */   public boolean addAll(LongCollection c) {
/* 293 */     return (this.al.addAllAbsent(c) > 0);
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
/*     */   public boolean removeAll(LongCollection c) {
/* 313 */     return this.al.removeAll(c);
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
/*     */   public boolean retainAll(LongCollection c) {
/* 335 */     return this.al.retainAll(c);
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
/*     */   public LongIterator iterator() {
/* 352 */     return this.al.iterator();
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
/*     */   public boolean equals(Object o) {
/* 373 */     if (o == this)
/*     */     {
/* 375 */       return true;
/*     */     }
/* 377 */     if (!(o instanceof IntSet))
/*     */     {
/* 379 */       return false;
/*     */     }
/* 381 */     IntSet set = (IntSet)o;
/* 382 */     PrimitiveIterator.OfInt it = set.iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     long[] elements = this.al.toArray();
/* 389 */     int len = elements.length;
/*     */     
/* 391 */     boolean[] matched = new boolean[len];
/* 392 */     int k = 0;
/*     */     
/* 394 */     label24: while (it.hasNext()) {
/*     */       
/* 396 */       if (++k > len)
/*     */       {
/* 398 */         return false;
/*     */       }
/* 400 */       int x = it.nextInt();
/* 401 */       for (int i = 0; i < len; i++) {
/*     */         
/* 403 */         if (!matched[i] && x == elements[i]) {
/*     */           
/* 405 */           matched[i] = true;
/*     */           continue label24;
/*     */         } 
/*     */       } 
/* 409 */       return false;
/*     */     } 
/* 411 */     return (k == len);
/*     */   }
/*     */   
/*     */   public CArrayLongSet() {}
/*     */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\sets\impl\CArrayLongSet.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */