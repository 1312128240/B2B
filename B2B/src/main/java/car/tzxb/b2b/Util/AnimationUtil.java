package car.tzxb.b2b.Util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import car.tzxb.b2b.R;

/**
 * Created by Administrator on 2018/5/23 0023.
 */

public class AnimationUtil {
    /**
     * by moos on 2017.8.21
     * edittext控件抖动动画
     */
    public static void Sharke(Context context,View view){
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
    }

}
