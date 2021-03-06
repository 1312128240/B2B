package car.tzxb.b2b.Util;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.crypto.Cipher;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class StringUtil {

    /**
     * md5签名加密
     *
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
     *
     * @param str
     * @return
     */
    public static StringBuilder UpperLowerCase(String str) {
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


    // 通过正则表达式来判断。EditText允许显示字母、数字和汉字。
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    //通过正则表达式来判断。EditText只能是数字
    public static String numberFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    //小数后面只显示2位
    public static String doubleConvert(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }
}
