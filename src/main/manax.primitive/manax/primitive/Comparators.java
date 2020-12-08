/*    */ package io.github.andyalvarezdev.primitive;
/*    */ 
/*    */ import io.github.andyalvarezdev.primitive.comparators.IntComparator;
/*    */ import io.github.andyalvarezdev.primitive.comparators.LongComparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Comparators
/*    */ {
/* 26 */   public static final IntComparator DEFAULT_INT_COMPARATOR = new IntComparator()
/*    */     {
/*    */       
/*    */       public int compare(int x, int y)
/*    */       {
/* 31 */         return (x < y) ? -1 : ((x == y) ? 0 : 1);
/*    */       }
/*    */     };
/*    */   
/* 35 */   public static final LongComparator DEFAULT_LONG_COMPARATOR = new LongComparator()
/*    */     {
/*    */       
/*    */       public int compare(long x, long y)
/*    */       {
/* 40 */         return (x < y) ? -1 : ((x == y) ? 0 : 1);
/*    */       }
/*    */     };
/*    */   
/* 44 */   public static final IntComparator REVERSE_INT_COMPARATOR = reverseOrder(DEFAULT_INT_COMPARATOR);
/* 45 */   public static final LongComparator REVERSE_LONG_COMPARATOR = reverseOrder(DEFAULT_LONG_COMPARATOR);
/*    */ 
/*    */   
/*    */   public static IntComparator reverseOrder(final IntComparator comparator) {
/* 49 */     if (comparator == null) {
/* 50 */       return REVERSE_INT_COMPARATOR;
/*    */     }
/* 52 */     return new IntComparator()
/*    */       {
/*    */         
/*    */         public int compare(int o1, int o2)
/*    */         {
/* 57 */           return comparator.compare(o2, o1);
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static LongComparator reverseOrder(final LongComparator comparator) {
/* 64 */     if (comparator == null) {
/* 65 */       return REVERSE_LONG_COMPARATOR;
/*    */     }
/* 67 */     return new LongComparator()
/*    */       {
/*    */         
/*    */         public int compare(long o1, long o2)
/*    */         {
/* 72 */           return comparator.compare(o2, o1);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\Comparators.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */