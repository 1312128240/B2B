package car.tzxb.b2b.Util;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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

    public static void FABAnimation(View view,int type) {

        if(type==1){      //显示
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
            PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
            ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(300).start();
        }else {          //隐藏
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f);
            PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f);
            ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(300).start();
        }
    }

}
