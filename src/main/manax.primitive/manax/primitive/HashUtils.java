/*    */ package io.github.andyalvarezdev.primitive;
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
/*    */ 
/*    */ 
/*    */ public class HashUtils
/*    */ {
/*    */   public static int hashCode(int val) {
/* 26 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int hashCode(long val) {
/* 31 */     return (int)(val ^ val >>> 32L);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int hashCode(Object val) {
/* 36 */     return (val == null) ? 0 : val.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\Manax\.gradle\caches\modules-2\files-2.1\io.github.andyalvarezdev\primitive\2.0.0-SNAPSHOT\4e4aa0ef5b5777a32b87dc244c52e046907e1af2\primitive-2.0.0-SNAPSHOT.jar!\io\github\andyalvarezdev\primitive\HashUtils.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */