package car.tzxb.b2b.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class StringUtil {

    /**
     * md5签名加密
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 转大写
     * @param str
     * @return
     */
    public static StringBuilder UpperLowerCase(String str){
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {

            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                sb1.append(c);
            } else if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') { //如果是字母就换成大写
                if (Character.isUpperCase(c)) {
                    sb1.append(Character.toLowerCase(c));
                } else if (Character.isLowerCase(c)) {
                    sb1.append(Character.toUpperCase(c));
                }
            }
        }
        return sb1;
    }
}
