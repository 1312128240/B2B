package car.myview.CustomToast;

import android.content.Context;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

import car.myview.R;


/**
 * Created by Administrator on 2018/5/23 0023.
 */

public class MyToast extends Toast {
    public MyToast(Context context) {
        super(context);
    }

    /**
     * 调用有动画的Toast
     */
    public static Toast makeTextAnim(Context context, CharSequence text, int duration,int Gravity,int x,int y) {
        Toast toast = makeText(context, text, duration);
        toast.setText(text);
        toast.setGravity(Gravity,x,y);
        toast.setDuration(duration);
        int styleId=  R.style.Lite_Animation_Toast;

        try {
            Object mTN ;
            mTN = getField(toast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.windowAnimations = styleId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toast;
    }

    /**
     * 反射字段
     * @param object 要反射的对象
     * @param fieldName 要反射的字段名称
     */
    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }
}
