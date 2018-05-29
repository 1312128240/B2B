package car.tzxb.b2b;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2018/5/22 0022.
 */

public class MyApp extends Application {

    private static Context context;
    public static boolean isDebug=true;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

    }

    public static Context getContext(){

        return context;
    }

}
