package car.myview;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/8/2 0002.
 */

public class MyNestScollview extends NestedScrollView {

    private OnScrollviewListener listener;
    public void setOnScrolInterface(OnScrollviewListener listener) {
        this.listener = listener;
    }
    public MyNestScollview(Context context) {
        super(context);
    }

    public MyNestScollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestScollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollviewListener{
        void onScroll(int scrollY);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(listener!=null){
            listener.onScroll(getScrollY());

        }
    }
}
