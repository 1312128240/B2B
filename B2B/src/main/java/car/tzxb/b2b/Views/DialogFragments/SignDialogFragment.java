package car.tzxb.b2b.Views.DialogFragments;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import car.tzxb.b2b.R;


/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class SignDialogFragment extends DialogFragment implements View.OnClickListener{

    Clicklistener clicklistener;
   public void setListener(Clicklistener listener){
       this.clicklistener=listener;
   }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.mystyle);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //添加动画
        Window window = this.getDialog().getWindow();
        window.setWindowAnimations(R.style.my_dialog_style2);   //设置dialog的显示动画
        View  view = inflater.inflate(R.layout.sign, container);
        ImageView iv_close=view.findViewById(R.id.iv_close_sign);
        TextView tv_gold=view.findViewById(R.id.tv_sign_gold);
        Button btn_go_gold=view.findViewById(R.id.btn_check_gold);
        iv_close.setOnClickListener(this);
        tv_gold.setOnClickListener(this);
        btn_go_gold.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_sign:
                dismiss();
                break;
            case R.id.btn_check_gold:
                clicklistener.check();
                break;
        }
    }



    public interface Clicklistener{
        void check();
    }
}
