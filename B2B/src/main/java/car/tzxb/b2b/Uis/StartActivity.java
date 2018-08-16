package car.tzxb.b2b.Uis;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MainActivity;
import car.tzxb.b2b.R;

public class StartActivity extends MyBaseAcitivity {

    @BindView(R.id.iv_start)
    ImageView iv_start;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_start;
    }

    @Override
    public void doBusiness(Context mContext) {
        startAnmation(mContext);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void startAnmation(Context mContext) {

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1500);
        iv_start.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(MainActivity.class);
                overridePendingTransition(R.anim.tran, R.anim.alpha);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


}
