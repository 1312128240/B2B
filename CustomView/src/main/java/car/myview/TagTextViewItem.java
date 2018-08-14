package car.myview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.Checkable;

/**
 * Created by Administrator on 2018/2/12 0012.
 */

public class TagTextViewItem extends AppCompatTextView implements Checkable {
    private boolean mChecked;
    public int CheckTextColor= Color.parseColor("#ff4444");
    public int unCheckTextColor=Color.parseColor("#333333");
    public Drawable CheckBackgroundDrawable;
    public Drawable unCheckBackgroundDrawable;
    public TagTextViewItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        setTextColor(checked?  CheckTextColor: unCheckTextColor);
        setBackgroundDrawable(checked? CheckBackgroundDrawable:unCheckBackgroundDrawable);
    }
         //选中字的颜色
      public void setCheckTextColor(int color){

            this.CheckTextColor=color;
      }
      //未选中字的颜色
    public void setUncheckTextColor(int color){
        this.unCheckTextColor=color;
    }

    //选中时的背景
    public void setCheckBackgroundDrawable(Drawable drawable){
        this.CheckBackgroundDrawable=drawable;
    }
    //未选中时的背景
    public void  setUncheckbackgroundDrawable(Drawable drawable){
        this.unCheckBackgroundDrawable=drawable;
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
