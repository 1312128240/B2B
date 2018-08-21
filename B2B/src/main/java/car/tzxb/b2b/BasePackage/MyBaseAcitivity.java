package car.tzxb.b2b.BasePackage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.umeng.analytics.MobclickAgent;
import butterknife.ButterKnife;
import car.tzxb.b2b.Util.ActivityManager;

import static car.tzxb.b2b.config.Constant.isDebug;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public abstract class MyBaseAcitivity extends AppCompatActivity {
    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = true;
    /** 是否允许全屏 **/
    private boolean mAllowFullScreen = true;
    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = false;
    /** 当前Activity渲染的视图View **/
    private View mContextView = null;
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private  final int MIN_CLICK_DELAY_TIME = 1000;
    private  long lastClickTime;
    private boolean mStateEnable;

    private BasePresenter presenter = null;
    protected final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (isSetStatusBar) {
            steepStatusBar();
        }
          setContentView(mContextView);
          presenter = bindPresenter();
          ButterKnife.bind(this);

        ActivityManager.getInstance().addActivity(this);

        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        doBusiness(this);
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
       if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    /**
     * [初始化参数]
     *
     * @param parms
     */
    public abstract void initParms(Bundle parms);


    /**
     * [绑定布局]
     * @return
     */
    public abstract int bindLayout();

    /**
     * [绑定控件]
     * @param resId
     * @return
     */
    protected    <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * [业务操作]
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);

    public void startActivity(Class<?> clz) {
        startActivity(new Intent(MyBaseAcitivity.this,clz));
    }

    /**
     * [携带数据的页面跳转]
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * [简化Toast]
     * @param msg
     */
    protected void showToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 调试信息
     */

    public void tLog(String msg){
        if(isDebug){
            Log.i(String.valueOf(MyBaseAcitivity.this),msg);
        }
    }



    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){

            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }
    /**
     * 防止快速点击
     */

    public  boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        Log.i("点击超时",flag+"");
        return flag;
    }

    /**
     * [是否允许全屏]
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * [是否允许屏幕旋转]
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }
    /**
     * 绑定presenter，主要用于销毁工作
     *
     * @return
     */
    protected abstract BasePresenter bindPresenter();


    @Override
    protected void onResume() {
        mStateEnable = true;
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());     //统计页面跳转的
        // 必须调用 MobclickAgent.onResume() 和MobclickAgent.onPause()方法，
        // 才能够保证获取正确的新增用户、活跃用户、启动次数、使用时长等基本数据。
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());      //统计页面跳转的

        // 必须调用 MobclickAgent.onResume() 和MobclickAgent.onPause()方法，
        // 才能够保证获取正确的新增用户、活跃用户、启动次数、使用时长等基本数据。
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStateEnable = true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mStateEnable = false;
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onStop() {
        // super.onStop();中将mStateSaved置为true
        mStateEnable = false;
        super.onStop();
    }
    /**
     *
     * activity状态是否处于可修改周期内，避免状态丢失的错误
     * @return
     */
    public boolean isStateEnable() {
        return mStateEnable;
    }

    /**
     * 如果重写了此方法，一定要调用super.onDestroy();
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
     /*   if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
            System.gc();
        }*/
        OkHttpUtils.getInstance().cancelTag(TAG);
        ActivityManager.getInstance().deleteActivity(this);
    }
}
