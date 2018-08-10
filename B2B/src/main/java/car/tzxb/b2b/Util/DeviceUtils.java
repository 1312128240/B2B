package car.tzxb.b2b.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.lang.reflect.Method;

import car.tzxb.b2b.MyApp;

/**
 * Created by Administrator on 2018/5/23 0023.
 */

public class DeviceUtils {
    /**
     * 获取状态栏高度
     * @param mContext
     * @return
     */
    public static int getNavigationBarHeight(Context mContext) {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
    /**
     * 获取是否存在NavigationBar
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }


    /**
     * dp转px
     *
     */
    public static int dip2px(Context ctx,float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * px转dp
     * @param ctx
     * @param pxValue
     * @return
     */

     public static int px2dip(Context ctx,float pxValue) {
     final float scale = ctx.getResources().getDisplayMetrics().density;
     return (int) (pxValue / scale + 0.5f);
     }

    /**
     * 版本名检测
     */
    public static String getVersionName(Context context) {
        //获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }
    /**
     * 设置不调用系统键盘
     */
    public static void hideSystemSoftKeyBoard(Activity activity ,EditText et){
        if (Build.VERSION.SDK_INT <= 10) {
            et.setInputType(InputType.TYPE_NULL);
        } else {
         activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(et, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示弹窗的方法
     */
    public static void showPopWindow(View parent , PopupWindow popupWindow){
        boolean b= DeviceUtils.checkDeviceHasNavigationBar(MyApp.getContext());
        if (b == true) {
            int navigationBarHeight= DeviceUtils.getNavigationBarHeight(MyApp.getContext());
            popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, navigationBarHeight);
        } else {
            popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }
    }
}
