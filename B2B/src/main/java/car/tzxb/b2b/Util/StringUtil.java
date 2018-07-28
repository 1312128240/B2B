package car.tzxb.b2b.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

    // 通过正则表达式来判断。下面的例子只允许显示字母、数字和汉字。

    public static String stringFilter(String str)throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String   regEx  =  "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }
    //通过正则表达式来判断。下面只能是数字
    public static String numberFilter(String str)throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String   regEx  =  "[^0-9]";
        Pattern   p   =   Pattern.compile(regEx);
        Matcher   m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }
}
