/*     */ package io.github.andyalvarezdev.primitive.maps.abstracts;
/*     */ 
/*     */ import io.github.andyalvarezdev.primitive.AbstractLongCollection;
/*     */ import io.github.andyalvarezdev.primitive.Constants;
/*     */ import io.github.andyalvarezdev.primitive.IntSet;
/*     */ import io.github.andyalvarezdev.primitive.LongCollection;
/*     */ import io.github.andyalvarezdev.primitive.maps.IntLongMap;
/*     */ import io.github.andyalvarezdev.primitive.pair.IntLong;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractIntLongMap
/*     */   implements IntLongMap
/*     */ {
/*     */   public int size() {
/*  85 */     return entrySet().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  94 */     return (size() == 0);
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
/*     */   public boolean containsValue(long value) {
/* 110 */     Iterator<IntLong> i = entrySet().iterator();
/* 111 */     while (i.hasNext()) {
/*     */       
/* 113 */       IntLong e = i.next();
/* 114 */       if (value == e.getValue())
/*     */       {
/* 116 */         return true;
/*     */       }
/*     */     } 
/* 119 */     return false;
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
/* 136 */     Iterator<IntLong> i = entrySet().iterator();
/* 137 */     while (i.hasNext()) {
/*     */       
/* 139 */       IntLong e = i.next();
/* 140 */       if (key == e.getKey())
/* 141 */         return true; 
/*     */     } 
/* 143 */     return false;
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
/*     */   public long get(int key) {
/* 160 */     for (IntLong e : entrySet()) {
/* 161 */       if (key == e.getKey())
/* 162 */         return e.getValue(); 
/*     */     } 
/* 164 */     return Constants.DEFAULT_LONG_VALUE;
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
/*     */   public long put(int key, long value) {
/* 182 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long putIfAbsent(int key, long value) {
/* 189 */     return containsKey(key) ? get(key) : put(key, value);
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
/*     */   public long remove(int key) {
/* 214 */     Iterator<IntLong> i = entrySet().iterator();
/* 215 */     IntLong correctEntry = null;
/*     */     
/* 217 */     while (correctEntry == null && i.hasNext()) {
/*     */       
/* 219 */       IntLong e = i.next();
/* 220 */       if (key == e.getKey()) {
/* 221 */         correctEntry = e;
/*     */       }
/*     */     } 
/*     */     
/* 225 */     long oldValue = Constants.DEFAULT_LONG_VALUE;
/* 226 */     if (correctEntry != null) {
/*     */       
/* 228 */       oldValue = correctEntry.getValue();
/* 229 */       i.remove();
/*     */     } 
/* 231 */     return oldValue;
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
/*     */   public void putAll(IntLongMap m) {
/* 254 */     for (IntLong e : m.entrySet()) {
/* 255 */       put(e.getKey(), e.getValue());
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
/* 270 */     entrySet().clear();
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
/* 281 */   protected volatile transient IntSet keySet = null;
/* 282 */   protected volatile transient LongCollection values = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 300 */     if (this.keySet == null)
/*     */     {
/* 302 */       this.keySet = (IntSet)new AbstractIntSet()
/*     */         {
/*     */           public PrimitiveIterator.OfInt iterator()
/*     */           {
/* 306 */             return new PrimitiveIterator.OfInt()
/*     */               {
/* 308 */                 private Iterator<IntLong> i = AbstractIntLongMap.this.entrySet().iterator();
/*     */ 
/*     */                 
/*     */                 public boolean hasNext() {
/* 312 */                   return this.i.hasNext();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public int nextInt() {
/* 317 */                   return ((IntLong)this.i.next()).getKey();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public void remove() {
/* 322 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */           
/*     */           public int size() {
/* 329 */             return AbstractIntLongMap.this.size();
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean contains(int k) {
/* 334 */             return AbstractIntLongMap.this.containsKey(k);
/*     */           }
/*     */         };
/*     */     }
/* 338 */     return this.keySet;
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
/*     */   public LongCollection values() {
/* 357 */     if (this.values == null)
/*     */     {
/* 359 */       this.values = (LongCollection)new AbstractLongCollection()
/*     */         {
/*     */           public PrimitiveIterator.OfLong iterator()
/*     */           {
/* 363 */             return new PrimitiveIterator.OfLong()
/*     */               {
/* 365 */                 private Iterator<IntLong> i = AbstractIntLongMap.this.entrySet().iterator();
/*     */ 
/*     */                 
/*     */                 public boolean hasNext() {
/* 369 */                   return this.i.hasNext();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public long nextLong() {
/* 374 */                   return ((IntLong)this.i.next()).getValue();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public void remove() {
/* 379 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */           
/*     */           public int size() {
/* 386 */             return AbstractIntLongMap.this.size();
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean contains(long v) {
/* 391 */             return AbstractIntLongMap.this.containsValue(v);
/*     */           }
/*     */         };
/*     */     }
/* 395 */     return this.values;
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
/*     */   public abstract Set<IntLong> entrySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 427 */     if (o == this)
/*     */     {
/* 429 */       return true;
/*     */     }
/*     */     
/* 432 */     if (!(o instanceof IntLongMap))
/*     */     {
/* 434 */       return false;
/*     */     }
/* 436 */     IntLongMap m = (IntLongMap)o;
/* 437 */     if (m.size() != size())
/*     */     {
/* 439 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 444 */       Iterator<IntLong> i = entrySet().iterator();
/* 445 */       while (i.hasNext())
/*     */       {
/* 447 */         IntLong e = i.next();
/* 448 */         int key = e.getKey();
/* 449 */         long value = e.getValue();
/* 450 */         if (value != m.get(key))
/*     */         {
/* 452 */           return false;
/*     */         }
/*     */       }
/*     */     
/* 456 */     } catch (ClassCastException|NullPointerException unused) {
/*     */       
/* 458 */       return false;
/*     */     } 
/*     */     
/* 461 */     return true;
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
/* 484 */     int h = 0;
/* 485 */     for (IntLong intLongPair : entrySet())
/* 486 */       h += intLongPair.hashCode(); 
/* 487 */     return h;
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
/* 504 */     Iterator<IntLong> i = entrySet().iterator();
/* 505 */     if (!i.hasNext())
/*     */     {
/* 507 */       return "{}";
/*     */     }
/*     */     
/* 510 */     StringBuilder sb = new StringBuilder();
/* 511 */     sb.append('{');
/*     */     
/*     */     while (true) {
/* 514 */       IntLong e = i.next();
/* 515 */       int key = e.getKey();
/* 516 */       long value = e.getValue();
/* 517 */       sb.append(key);
/* 518 */       sb.append('=');
/* 519 */       sb.append(value);
/* 520 */       if (!i.hasNext())
/*     */       {
/* 522 */         return sb.append('}').toString();
/*     */       }
/* 524 */       sb.append(", ");
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
/* 536 */     AbstractIntLongMap result = (AbstractIntLongMap)super.clone();
/* 537 */     result.keySet = null;
/* 538 */     result.values = null;
/* 539 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\maps\abstracts\AbstractIntLongMap.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */