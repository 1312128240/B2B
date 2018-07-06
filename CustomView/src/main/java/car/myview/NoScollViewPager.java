package car.myview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class NoScollViewPager extends ViewPager {

    private boolean noScroll = false;

    public NoScollViewPager(Context context) {
        super(context);
    }

    public NoScollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 覆盖ViewPager的onInterceptTouchEvent(MotionEvent arg0)方法和onTouchEvent(MotionEvent arg0)方法，
     * 这两个方法的返回值都是boolean类型的，只需要将返回值改为false，那么ViewPager就不会消耗掉手指滑动的事件了
     * @param arg0
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    /**
     * 向外暴露方法
     */
    public void isCanScoll(boolean b){
        this.noScroll=b;
    }
}
