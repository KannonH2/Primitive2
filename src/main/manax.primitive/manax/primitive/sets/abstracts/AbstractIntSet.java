/*     */ package io.github.andyalvarezdev.primitive.sets.abstracts;
/*     */ 
/*     */ import io.github.andyalvarezdev.primitive.AbstractIntCollection;
/*     */ import io.github.andyalvarezdev.primitive.IntCollection;
/*     */ import io.github.andyalvarezdev.primitive.IntSet;
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
/*     */ public abstract class AbstractIntSet
/*     */   extends AbstractIntCollection
/*     */   implements IntSet
/*     */ {
/*     */   public boolean equals(Object o) {
/*  90 */     if (o == this)
/*     */     {
/*  92 */       return true;
/*     */     }
/*     */     
/*  95 */     if (!(o instanceof IntSet))
/*     */     {
/*  97 */       return false;
/*     */     }
/*  99 */     IntCollection c = (IntCollection)o;
/* 100 */     if (c.size() != size())
/*     */     {
/* 102 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 106 */       return containsAll(c);
/*     */     }
/* 108 */     catch (ClassCastException|NullPointerException unused) {
/*     */       
/* 110 */       return false;
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
/*     */   public int hashCode() {
/* 134 */     int h = 0;
/* 135 */     PrimitiveIterator.OfInt i = iterator();
/* 136 */     while (i.hasNext()) {
/*     */       
/* 138 */       int obj = i.nextInt();
/* 139 */       h += obj;
/*     */     } 
/* 141 */     return h;
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
/*     */   public boolean removeAll(IntCollection c) {
/* 182 */     boolean modified = false;
/*     */     
/* 184 */     if (size() > c.size()) {
/*     */       
/* 186 */       for (PrimitiveIterator.OfInt i = c.iterator(); i.hasNext();)
/*     */       {
/* 188 */         modified |= remove(i.nextInt());
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 193 */       for (PrimitiveIterator.OfInt i = iterator(); i.hasNext();) {
/*     */         
/* 195 */         if (c.contains(i.nextInt())) {
/*     */           
/* 197 */           i.remove();
/* 198 */           modified = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 202 */     return modified;
/*     */   }
/*     */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\sets\abstracts\AbstractIntSet.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */