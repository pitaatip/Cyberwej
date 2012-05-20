/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.common;

/**
 * @author Pita
 *
 */
public class CryptoUtil {
    
    public static String encrypt(String pass) throws Exception{
        byte[] computeHash = CryptoUtil.computeHash(pass);
        return CryptoUtil.byteArrayToHexString(computeHash);
    }

 /*   public static void main(String arg[]) {
       try {
         // quick way to do input from the keyboard, now deprecated...
         java.io.StreamTokenizer Input=new java.io.StreamTokenizer(System.in);
         //
         System.out.print("Input your secret password : ");
         Input.nextToken();
         String hash = byteArrayToHexString(CryptoUtils.computeHash(Input.sval));
         System.out.println("the computed hash (hex string) : " + hash);
         boolean ok = true;
         String inputHash = "";
         while (ok) {
             System.out.print("Now try to enter a password : " );
             Input.nextToken();
             inputHash = byteArrayToHexString(CryptoUtils.computeHash(Input.sval));
             if (hash.equals(inputHash)){
                System.out.println("You got it!");
                ok = false;
                }
             else
                System.out.println("Wrong, try again...!");
         }
       }
       catch (Exception e){
         e.printStackTrace();
       }
     }*/

    
    
     public static byte[] computeHash(String x)   
     throws Exception  
     {
        java.security.MessageDigest d =null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return  d.digest();
     }
     
     public static String byteArrayToHexString(byte[] b){
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++){
          int v = b[i] & 0xff;
          if (v < 16) {
            sb.append('0');
          }
          sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
     }
}
