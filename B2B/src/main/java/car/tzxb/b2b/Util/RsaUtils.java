package car.tzxb.b2b.Util;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Administrator on 2018/9/10 0010.
 */

public class RsaUtils {

    public static String PUblicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxKQcslm2RwUCXh4SXNhtt5JhgsnI6YMhk7y7D/Nw8un1BwBinoV8YCnmGFCQueV+ue6pAuVqRniiKgHXMjZqgGfl0ZXvoa8zAW47DAQHU5abSlSpTuP/bceUpkk6sljxnYMiWa3RcV1hZDF2KKiKwXEG1KVq/ryohl0MEZ0VHoiXWWG1jZAfpp6/p2O8UK1VwIgyhSLfvEgc3LKMoGjonouLMu5QflanGjmeKD8bty1hJjWEGUZrBpgI++2VeDH/+JEJJ9VqPxYfEg9ATA/dl8ePEdKtt38NZTkZUCv3CqiPojd8czNxmUbJrnYTJ7Tsq+uRQ5bl+kBPtf7kWepPqQIDAQAB";

    //密钥获取：
    public static PublicKey getPublicKey(String publicKeyString) throws Exception{
        byte[] keyBytes = Base64.decode(publicKeyString.getBytes(), Base64.NO_WRAP);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey.getBytes(), Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }



   // 加密和解密：
    public static byte[] encrypted(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

   //利用公钥加密，并进行base64编码：
    public static String base64Encrypted(String data,String publicKeyString)  {
        byte[] encryptedBytes = {};
        try {
            PublicKey publicKey=getPublicKey(publicKeyString);
            encryptedBytes = encrypted(data.getBytes(), publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP);
    }


}
