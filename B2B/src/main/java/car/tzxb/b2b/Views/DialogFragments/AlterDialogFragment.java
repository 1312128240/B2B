package car.tzxb.b2b.Views.DialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import car.tzxb.b2b.R;


/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class AlterDialogFragment extends DialogFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.alter_dialog_fragement,null);
        TextView tv_cancle= view.findViewById(R.id.tv_alter_dialog_cancle);
        TextView tv_sure=  view.findViewById(R.id.tv_alter_dialog_sure);
        TextView tv_title=  view.findViewById(R.id.tv_alter_dialog_title);
        // 接收关联Activity传来的数据 -----
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title=bundle.getString("title");
            tv_title.setText(title);
        }
        tv_cancle.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_alter_dialog_cancle:
                custAlterDialgoInterface.cancle();
                break;
            case R.id.tv_alter_dialog_sure:
                custAlterDialgoInterface.sure();
                break;
        }
    }
    CustAlterDialgoInterface custAlterDialgoInterface;

    public void setOnClick(CustAlterDialgoInterface click){
        this.custAlterDialgoInterface=click;
    }

    public interface CustAlterDialgoInterface{
        void cancle();
        void sure();
    }
}
