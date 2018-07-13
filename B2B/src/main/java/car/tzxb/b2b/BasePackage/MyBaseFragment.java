package car.tzxb.b2b.BasePackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.mylibrary.HttpClient.OkHttpUtils;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/3/26 0026.
 */

public abstract class MyBaseFragment extends Fragment {
    private BasePresenter presenter = null;
    public Context mContext;
    protected final String TAG = this.getClass().getSimpleName();
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private  final int MIN_CLICK_DELAY_TIME = 1000;
    private  long lastClickTime;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = View.inflate(mContext, getLayoutResId(), null);
        presenter = bindPresenter();
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    /**
     * 返回资源的布局
     *
     * @return
     */
    public abstract int getLayoutResId();


    /**
     * 页面初始化页面数据，在initView之后调用
     */
    public abstract void initData();

    /**
     * 绑定presenter，主要用于销毁工作
     *
     * @return
     */
    protected abstract BasePresenter bindPresenter();

    /**
     * 如果重写了此方法，一定要调用super.onDestroy();
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
      /*  if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
            System.gc();
        }*/

        OkHttpUtils.getInstance().cancelTag(TAG);
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
        return flag;
    }
}
