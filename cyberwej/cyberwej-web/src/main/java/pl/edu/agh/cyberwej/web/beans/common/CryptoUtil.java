/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.common;

/**
 * @author Pita
 */
public class CryptoUtil {

    public static String encrypt(String pass) throws Exception {
        byte[] computeHash = CryptoUtil.computeHash(pass);
        return CryptoUtil.byteArrayToHexString(computeHash);
    }

    public static byte[] computeHash(String x) throws Exception {
        java.security.MessageDigest d = null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }
}
