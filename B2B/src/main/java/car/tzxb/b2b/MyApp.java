package car.tzxb.b2b;

import android.app.Application;
import android.content.Context;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * Created by Administrator on 2018/5/22 0022.
 */

public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        initUm();
    }

     //初始化友盟统计
    private void initUm() {
        UMConfigure.init(this, 0, null);
        MobclickAgent.enableEncrypt(true);               //设置是否对日志信息进行加密, 默认false(不加密).
        MobclickAgent.openActivityDurationTrack(false);  // 禁止默认的页面统计方式，这样将不会再自动统计Activity。
        MobclickAgent.setDebugMode(true);               //开启调试模式（如果不开启debug运行不会上传umeng统计）
    }

    public static Context getContext(){

        return context;
    }

}
