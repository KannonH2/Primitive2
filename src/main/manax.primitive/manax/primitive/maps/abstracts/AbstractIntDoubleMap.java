/*     */ package io.github.andyalvarezdev.primitive.maps.abstracts;
/*     */ 
/*     */ import io.github.andyalvarezdev.primitive.Constants;
/*     */ import io.github.andyalvarezdev.primitive.IntSet;
/*     */ import io.github.andyalvarezdev.primitive.collections.DoubleCollection;
/*     */ import io.github.andyalvarezdev.primitive.collections.abstracts.AbstractDoubleCollection;
/*     */ import io.github.andyalvarezdev.primitive.iterators.DoubleIterator;
/*     */ import io.github.andyalvarezdev.primitive.maps.IntDoubleMap;
/*     */ import io.github.andyalvarezdev.primitive.maps.IntLongMap;
/*     */ import io.github.andyalvarezdev.primitive.pair.IntDouble;
/*     */ import io.github.andyalvarezdev.primitive.sets.abstracts.AbstractIntSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractIntDoubleMap
/*     */   implements IntDoubleMap
/*     */ {
/*     */   public int size() {
/*  67 */     return entrySet().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  76 */     return (size() == 0);
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
/*     */   public boolean containsValue(double value) {
/*  92 */     for (IntDouble e : entrySet()) {
/*  93 */       if (value == e.getValue()) {
/*  94 */         return true;
/*     */       }
/*     */     } 
/*  97 */     return false;
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
/*     */   public boolean containsKey(int key) {
/* 114 */     Iterator<IntDouble> i = entrySet().iterator();
/* 115 */     while (i.hasNext()) {
/*     */       
/* 117 */       IntDouble e = i.next();
/* 118 */       if (key == e.getKey())
/* 119 */         return true; 
/*     */     } 
/* 121 */     return false;
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
/*     */   public double get(int key) {
/* 138 */     for (IntDouble e : entrySet()) {
/* 139 */       if (key == e.getKey())
/* 140 */         return e.getValue(); 
/*     */     } 
/* 142 */     return Constants.DEFAULT_LONG_VALUE;
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
/*     */   public double put(int key, double value) {
/* 160 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double putIfAbsent(int key, double value) {
/* 168 */     return containsKey(key) ? get(key) : put(key, value);
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
/*     */   public double remove(int key) {
/* 193 */     Iterator<IntDouble> i = entrySet().iterator();
/* 194 */     IntDouble correctEntry = null;
/*     */     
/* 196 */     while (correctEntry == null && i.hasNext()) {
/*     */       
/* 198 */       IntDouble e = i.next();
/* 199 */       if (key == e.getKey()) {
/* 200 */         correctEntry = e;
/*     */       }
/*     */     } 
/*     */     
/* 204 */     double oldValue = Constants.DEFAULT_LONG_VALUE;
/* 205 */     if (correctEntry != null) {
/*     */       
/* 207 */       oldValue = correctEntry.getValue();
/* 208 */       i.remove();
/*     */     } 
/* 210 */     return oldValue;
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
/*     */   public void putAll(IntDoubleMap m) {
/* 233 */     for (IntDouble e : m.entrySet()) {
/* 234 */       put(e.getKey(), e.getValue());
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
/*     */   public void clear() {
/* 249 */     entrySet().clear();
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
/* 260 */   protected volatile transient IntSet keySet = null;
/* 261 */   protected volatile transient DoubleCollection values = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntSet keySet() {
/* 279 */     if (this.keySet == null)
/*     */     {
/* 281 */       this.keySet = (IntSet)new AbstractIntSet()
/*     */         {
/*     */           public PrimitiveIterator.OfInt iterator()
/*     */           {
/* 285 */             return new PrimitiveIterator.OfInt()
/*     */               {
/* 287 */                 private Iterator<IntDouble> i = AbstractIntDoubleMap.this.entrySet().iterator();
/*     */ 
/*     */                 
/*     */                 public boolean hasNext() {
/* 291 */                   return this.i.hasNext();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public int nextInt() {
/* 296 */                   return ((IntDouble)this.i.next()).getKey();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public void remove() {
/* 301 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */           
/*     */           public int size() {
/* 308 */             return AbstractIntDoubleMap.this.size();
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean contains(int k) {
/* 313 */             return AbstractIntDoubleMap.this.containsKey(k);
/*     */           }
/*     */         };
/*     */     }
/* 317 */     return this.keySet;
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
/*     */   public DoubleCollection values() {
/* 336 */     if (this.values == null)
/*     */     {
/* 338 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*     */         {
/*     */           public DoubleIterator iterator()
/*     */           {
/* 342 */             return new DoubleIterator()
/*     */               {
/* 344 */                 private Iterator<IntDouble> i = AbstractIntDoubleMap.this.entrySet().iterator();
/*     */ 
/*     */                 
/*     */                 public boolean hasNext() {
/* 348 */                   return this.i.hasNext();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public double next() {
/* 353 */                   return ((IntDouble)this.i.next()).getValue();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public void remove() {
/* 358 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */           
/*     */           public int size() {
/* 365 */             return AbstractIntDoubleMap.this.size();
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean contains(long v) {
/* 370 */             return AbstractIntDoubleMap.this.containsValue(v);
/*     */           }
/*     */         };
/*     */     }
/* 374 */     return this.values;
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
/*     */   public abstract Set<IntDouble> entrySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 406 */     if (o == this)
/*     */     {
/* 408 */       return true;
/*     */     }
/*     */     
/* 411 */     if (!(o instanceof IntLongMap))
/*     */     {
/* 413 */       return false;
/*     */     }
/* 415 */     IntLongMap m = (IntLongMap)o;
/* 416 */     if (m.size() != size())
/*     */     {
/* 418 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 423 */       Iterator<IntDouble> i = entrySet().iterator();
/* 424 */       while (i.hasNext())
/*     */       {
/* 426 */         IntDouble e = i.next();
/* 427 */         int key = e.getKey();
/* 428 */         double value = e.getValue();
/* 429 */         if (value != m.get(key))
/*     */         {
/* 431 */           return false;
/*     */         }
/*     */       }
/*     */     
/* 435 */     } catch (ClassCastException|NullPointerException unused) {
/*     */       
/* 437 */       return false;
/*     */     } 
/*     */     
/* 440 */     return true;
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
/*     */   public int hashCode() {
/* 463 */     int h = 0;
/* 464 */     for (IntDouble intDoublePair : entrySet())
/* 465 */       h += intDoublePair.hashCode(); 
/* 466 */     return h;
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
/*     */   public String toString() {
/* 483 */     Iterator<IntDouble> i = entrySet().iterator();
/* 484 */     if (!i.hasNext())
/*     */     {
/* 486 */       return "{}";
/*     */     }
/*     */     
/* 489 */     StringBuilder sb = new StringBuilder();
/* 490 */     sb.append('{');
/*     */     
/*     */     while (true) {
/* 493 */       IntDouble e = i.next();
/* 494 */       int key = e.getKey();
/* 495 */       double value = e.getValue();
/* 496 */       sb.append(key);
/* 497 */       sb.append('=');
/* 498 */       sb.append(value);
/* 499 */       if (!i.hasNext())
/*     */       {
/* 501 */         return sb.append('}').toString();
/*     */       }
/* 503 */       sb.append(", ");
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
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 515 */     AbstractIntDoubleMap result = (AbstractIntDoubleMap)super.clone();
/* 516 */     result.keySet = null;
/* 517 */     result.values = null;
/* 518 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\maps\abstracts\AbstractIntDoubleMap.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */