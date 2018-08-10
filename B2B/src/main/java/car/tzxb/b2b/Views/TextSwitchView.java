package car.tzxb.b2b.Views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import car.tzxb.b2b.Bean.BaseDataListBean;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class TextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private int index= -1;
    private List<BaseDataListBean.DataBean> resources;
    private Context context;
    private Handler mHandler = new Handler(){
    public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    index = next(); //取得下标值
                    updateText();  //更新TextSwitcherd显示内容;
                    break;
            }
        };
    };

    private Timer timer;
    public TextSwitchView(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public TextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    private void init() {
        if(timer==null){
            timer = new Timer();
        }

        this.setFactory(this);
    }
    public void setResources(List<BaseDataListBean.DataBean> res){
        this.resources = res;
    }
    public void setTextStillTime(long time){
        if(timer==null){
            timer = new Timer();
        }else{
            timer.scheduleAtFixedRate(new MyTask(), 1, time);//每3秒更新
        }
    }
    private class MyTask extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    }
    private int next(){
        int flag = index+1;
        if(flag>resources.size()-1){
            flag=flag-resources.size();
        }
        return flag;
    }
    private void updateText(){
        this.setText(resources.get(index).getTitle());
    }
    @Override
    public View makeView() {
        TextView tv =new TextView(context);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 BaseDataListBean.DataBean bean=resources.get(index);

                 listener.click(bean.getTitle(),bean.getContent());
            }
        });
        return tv;
    }
    clickListener listener;
    public void setListener(clickListener clickListener){
        this.listener=clickListener;
    }
   public interface  clickListener{
       void click(String title,String content);
   }

}

