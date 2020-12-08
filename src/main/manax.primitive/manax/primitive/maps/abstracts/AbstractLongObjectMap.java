/*     */ package io.github.andyalvarezdev.primitive.maps.abstracts;
/*     */ 
/*     */ import io.github.andyalvarezdev.primitive.AbstractLongSet;
/*     */ import io.github.andyalvarezdev.primitive.LongSet;
/*     */ import io.github.andyalvarezdev.primitive.maps.LongObjectMap;
/*     */ import io.github.andyalvarezdev.primitive.pair.LongObject;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractLongObjectMap<V>
/*     */   implements LongObjectMap<V>
/*     */ {
/*     */   public int size() {
/*  87 */     return entrySet().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  96 */     return (size() == 0);
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
/*     */   public boolean containsValue(Object value) {
/* 113 */     Iterator<LongObject<V>> i = entrySet().iterator();
/* 114 */     if (value == null) {
/*     */       
/* 116 */       while (i.hasNext())
/*     */       {
/* 118 */         LongObject<V> e = i.next();
/* 119 */         if (e.getValue() == null)
/*     */         {
/* 121 */           return true;
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 127 */       while (i.hasNext()) {
/*     */         
/* 129 */         LongObject<V> e = i.next();
/* 130 */         if (value.equals(e.getValue()))
/*     */         {
/* 132 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 136 */     return false;
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
/*     */   public boolean containsKey(long key) {
/* 154 */     Iterator<LongObject<V>> i = entrySet().iterator();
/* 155 */     while (i.hasNext()) {
/*     */       
/* 157 */       LongObject<V> e = i.next();
/* 158 */       if (key == e.getKey())
/*     */       {
/* 160 */         return true;
/*     */       }
/*     */     } 
/* 163 */     return false;
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
/*     */   public V get(long key) {
/* 181 */     Iterator<LongObject<V>> i = entrySet().iterator();
/* 182 */     while (i.hasNext()) {
/*     */       
/* 184 */       LongObject<V> e = i.next();
/* 185 */       if (key == e.getKey())
/*     */       {
/* 187 */         return (V)e.getValue();
/*     */       }
/*     */     } 
/*     */     
/* 191 */     return null;
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
/*     */   public V put(long key, V value) {
/* 209 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V putIfAbsent(long key, V value) {
/* 216 */     return containsKey(key) ? get(key) : put(key, value);
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
/*     */   public V remove(long key) {
/* 242 */     Iterator<LongObject<V>> i = entrySet().iterator();
/* 243 */     LongObject<V> correctEntry = null;
/*     */     
/* 245 */     while (correctEntry == null && i.hasNext()) {
/*     */       
/* 247 */       LongObject<V> e = i.next();
/* 248 */       if (key == e.getKey())
/*     */       {
/* 250 */         correctEntry = e;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 255 */     V oldValue = null;
/* 256 */     if (correctEntry != null) {
/*     */       
/* 258 */       oldValue = (V)correctEntry.getValue();
/* 259 */       i.remove();
/*     */     } 
/* 261 */     return oldValue;
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
/*     */   public void putAll(LongObjectMap<? extends V> m) {
/* 285 */     for (LongObject<? extends V> e : (Iterable<LongObject<? extends V>>)m.entrySet())
/*     */     {
/* 287 */       put(e.getKey(), (V)e.getValue());
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
/*     */   public void clear() {
/* 304 */     entrySet().clear();
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
/* 315 */   protected volatile transient LongSet keySet = null;
/* 316 */   protected volatile transient Collection<V> values = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongSet keySet() {
/* 334 */     if (this.keySet == null)
/*     */     {
/* 336 */       this.keySet = (LongSet)new AbstractLongSet()
/*     */         {
/*     */           
/*     */           public PrimitiveIterator.OfLong iterator()
/*     */           {
/* 341 */             return new PrimitiveIterator.OfLong()
/*     */               {
/* 343 */                 private Iterator<LongObject<V>> i = AbstractLongObjectMap.this.entrySet().iterator();
/*     */ 
/*     */ 
/*     */                 
/*     */                 public boolean hasNext() {
/* 348 */                   return this.i.hasNext();
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public long nextLong() {
/* 354 */                   return ((LongObject)this.i.next()).getKey();
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void remove() {
/* 360 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public int size() {
/* 368 */             return AbstractLongObjectMap.this.size();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public boolean contains(long k) {
/* 374 */             return AbstractLongObjectMap.this.containsKey(k);
/*     */           }
/*     */         };
/*     */     }
/* 378 */     return this.keySet;
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
/*     */   public Collection<V> values() {
/* 398 */     if (this.values == null)
/*     */     {
/* 400 */       this.values = new AbstractCollection<V>()
/*     */         {
/*     */           
/*     */           public Iterator<V> iterator()
/*     */           {
/* 405 */             return new Iterator<V>()
/*     */               {
/* 407 */                 private Iterator<LongObject<V>> i = AbstractLongObjectMap.this.entrySet().iterator();
/*     */ 
/*     */ 
/*     */                 
/*     */                 public boolean hasNext() {
/* 412 */                   return this.i.hasNext();
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public V next() {
/* 418 */                   return (V)((LongObject)this.i.next()).getValue();
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void remove() {
/* 424 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public int size() {
/* 432 */             return AbstractLongObjectMap.this.size();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public boolean contains(Object v) {
/* 438 */             return AbstractLongObjectMap.this.containsValue(v);
/*     */           }
/*     */         };
/*     */     }
/* 442 */     return this.values;
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
/*     */   public abstract Set<LongObject<V>> entrySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 474 */     if (o == this)
/*     */     {
/* 476 */       return true;
/*     */     }
/*     */     
/* 479 */     if (!(o instanceof LongObjectMap))
/*     */     {
/* 481 */       return false;
/*     */     }
/*     */     
/* 484 */     LongObjectMap<V> m = (LongObjectMap<V>)o;
/* 485 */     if (m.size() != size())
/*     */     {
/* 487 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 492 */       Iterator<LongObject<V>> i = entrySet().iterator();
/* 493 */       while (i.hasNext())
/*     */       {
/* 495 */         LongObject<V> e = i.next();
/* 496 */         long key = e.getKey();
/* 497 */         V value = (V)e.getValue();
/* 498 */         if (value == null) {
/*     */           
/* 500 */           if (m.get(key) != null || !m.containsKey(key))
/*     */           {
/* 502 */             return false;
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/* 507 */         if (!value.equals(m.get(key)))
/*     */         {
/* 509 */           return false;
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 514 */     catch (ClassCastException|NullPointerException unused) {
/*     */       
/* 516 */       return false;
/*     */     } 
/*     */     
/* 519 */     return true;
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
/* 542 */     int h = 0;
/* 543 */     Iterator<LongObject<V>> i = entrySet().iterator();
/* 544 */     while (i.hasNext())
/*     */     {
/* 546 */       h += ((LongObject)i.next()).hashCode();
/*     */     }
/* 548 */     return h;
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
/* 565 */     Iterator<LongObject<V>> i = entrySet().iterator();
/* 566 */     if (!i.hasNext())
/*     */     {
/* 568 */       return "{}";
/*     */     }
/*     */     
/* 571 */     StringBuilder sb = new StringBuilder();
/* 572 */     sb.append('{');
/*     */     
/*     */     while (true) {
/* 575 */       LongObject<V> e = i.next();
/* 576 */       long key = e.getKey();
/* 577 */       V value = (V)e.getValue();
/* 578 */       sb.append(key);
/* 579 */       sb.append('=');
/* 580 */       sb.append((value == this) ? "(this Map)" : value);
/* 581 */       if (!i.hasNext())
/*     */       {
/* 583 */         return sb.append('}').toString();
/*     */       }
/* 585 */       sb.append(", ");
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
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 598 */     AbstractLongObjectMap<V> result = (AbstractLongObjectMap<V>)super.clone();
/* 599 */     result.keySet = null;
/* 600 */     result.values = null;
/* 601 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\maps\abstracts\AbstractLongObjectMap.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */