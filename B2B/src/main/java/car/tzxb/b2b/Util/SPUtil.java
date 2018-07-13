package car.tzxb.b2b.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/5/22 0022.
 */

public class SPUtil {
    public static final String FILE_NAME="share_data";
    private volatile static SPUtil instance;
    private final SharedPreferences.Editor editor;
    private final SharedPreferences sp;

    public SPUtil(Context context) {
        sp = context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SPUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SPUtil.class) {
                if (instance == null) {
                    // 使用双重同步锁
                    instance = new SPUtil(context);
                }
            }
        }
        return instance;
    }
    //保存用户id
    public void putUserId(String key, String value) {
        editor.putString(key, value).commit();
    }

    /**
     * @param key  UserId
     * @param defValue
     * @return
     */
    public String getUserId(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    /**删除
     * @param key
     *
     */
    public String dele(String key){

       editor.remove(key).commit();

        return key;
    }

    /**
     * Mobile
     * @param key
     * @param value
     */
    public void putMobile(String key,String value){
        editor.putString(key,value).commit();
    }
    //获取用户手机号
    public String getMobile(String key,String defValue){
        return sp.getString(key,defValue);
    }
}
