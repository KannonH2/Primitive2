/*     */ package io.github.andyalvarezdev.primitive.collections.abstracts;
/*     */ 
/*     */ import io.github.andyalvarezdev.primitive.collections.DoubleCollection;
/*     */ import io.github.andyalvarezdev.primitive.iterators.DoubleIterator;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDoubleCollection
/*     */   implements DoubleCollection
/*     */ {
/*     */   public abstract DoubleIterator iterator();
/*     */   
/*     */   public abstract int size();
/*     */   
/*     */   public boolean isEmpty() {
/*  70 */     return (size() == 0);
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
/*     */   public boolean contains(double o) {
/*  84 */     DoubleIterator e = iterator();
/*  85 */     while (e.hasNext()) {
/*     */       
/*  87 */       if (o == e.next())
/*     */       {
/*  89 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  93 */     return false;
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
/*     */   public double[] toArray() {
/* 120 */     double[] r = new double[size()];
/* 121 */     DoubleIterator it = iterator();
/* 122 */     for (int i = 0; i < r.length; i++) {
/*     */       
/* 124 */       if (!it.hasNext())
/*     */       {
/* 126 */         return Arrays.copyOf(r, i);
/*     */       }
/* 128 */       r[i] = it.next();
/*     */     } 
/* 130 */     return it.hasNext() ? finishToArray(r, it) : r;
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
/*     */   public double[] toArray(double[] a) {
/* 163 */     int size = size();
/* 164 */     double[] r = (a.length >= size) ? a : new double[size];
/* 165 */     DoubleIterator it = iterator();
/*     */     
/* 167 */     for (int i = 0; i < r.length; i++) {
/*     */       
/* 169 */       if (!it.hasNext()) {
/*     */         
/* 171 */         if (a != r)
/*     */         {
/* 173 */           return Arrays.copyOf(r, i);
/*     */         }
/* 175 */         r[i] = 0.0D;
/* 176 */         return r;
/*     */       } 
/* 178 */       r[i] = it.next();
/*     */     } 
/* 180 */     return it.hasNext() ? finishToArray(r, it) : r;
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
/*     */   private static double[] finishToArray(double[] r, DoubleIterator it) {
/* 195 */     int i = r.length;
/* 196 */     while (it.hasNext()) {
/*     */       
/* 198 */       int cap = r.length;
/* 199 */       if (i == cap) {
/*     */         
/* 201 */         int newCap = (cap / 2 + 1) * 3;
/* 202 */         if (newCap <= cap) {
/*     */           
/* 204 */           if (cap == Integer.MAX_VALUE)
/*     */           {
/* 206 */             throw new OutOfMemoryError("Required array size too large");
/*     */           }
/* 208 */           newCap = Integer.MAX_VALUE;
/*     */         } 
/* 210 */         r = Arrays.copyOf(r, newCap);
/*     */       } 
/* 212 */       r[i++] = it.next();
/*     */     } 
/*     */     
/* 215 */     return (i == r.length) ? r : Arrays.copyOf(r, i);
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
/*     */   public boolean add(double e) {
/* 234 */     throw new UnsupportedOperationException();
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
/*     */   public boolean remove(double o) {
/* 255 */     DoubleIterator e = iterator();
/* 256 */     while (e.hasNext()) {
/*     */       
/* 258 */       if (o == e.next()) {
/*     */         
/* 260 */         e.remove();
/* 261 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     return false;
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
/*     */   public boolean containsAll(DoubleCollection c) {
/* 285 */     DoubleIterator e = c.iterator();
/* 286 */     while (e.hasNext()) {
/*     */       
/* 288 */       if (!contains(e.next()))
/*     */       {
/* 290 */         return false;
/*     */       }
/*     */     } 
/* 293 */     return true;
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
/*     */   public boolean addAll(DoubleCollection c) {
/* 315 */     boolean modified = false;
/* 316 */     DoubleIterator e = c.iterator();
/* 317 */     while (e.hasNext()) {
/*     */       
/* 319 */       if (add(e.next()))
/*     */       {
/* 321 */         modified = true;
/*     */       }
/*     */     } 
/* 324 */     return modified;
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
/*     */   public boolean removeAll(DoubleCollection c) {
/* 349 */     boolean modified = false;
/* 350 */     DoubleIterator e = iterator();
/* 351 */     while (e.hasNext()) {
/*     */       
/* 353 */       if (c.contains(e.next())) {
/*     */         
/* 355 */         e.remove();
/* 356 */         modified = true;
/*     */       } 
/*     */     } 
/* 359 */     return modified;
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
/*     */   public boolean retainAll(DoubleCollection c) {
/* 384 */     boolean modified = false;
/* 385 */     DoubleIterator e = iterator();
/* 386 */     while (e.hasNext()) {
/*     */       
/* 388 */       if (!c.contains(e.next())) {
/*     */         
/* 390 */         e.remove();
/* 391 */         modified = true;
/*     */       } 
/*     */     } 
/* 394 */     return modified;
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
/*     */   public void clear() {
/* 414 */     DoubleIterator e = iterator();
/* 415 */     while (e.hasNext()) {
/*     */       
/* 417 */       e.next();
/* 418 */       e.remove();
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
/*     */   public String toString() {
/* 438 */     DoubleIterator i = iterator();
/* 439 */     if (!i.hasNext()) {
/* 440 */       return "[]";
/*     */     }
/* 442 */     StringBuilder sb = new StringBuilder();
/* 443 */     sb.append('[');
/*     */     
/*     */     while (true) {
/* 446 */       double e = i.next();
/* 447 */       sb.append(e);
/* 448 */       if (!i.hasNext())
/*     */       {
/* 450 */         return sb.append(']').toString();
/*     */       }
/* 452 */       sb.append(", ");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\collections\abstracts\AbstractDoubleCollection.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */