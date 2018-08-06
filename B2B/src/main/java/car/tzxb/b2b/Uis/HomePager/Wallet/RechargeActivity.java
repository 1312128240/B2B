package car.tzxb.b2b.Uis.HomePager.Wallet;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class RechargeActivity extends MyBaseAcitivity{

    @BindView(R.id.et_input_number)
    EditText et;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @Override
    public void initParms(Bundle parms) {


    }

    @Override
    public int bindLayout() {
        return R.layout.activity_recharge;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("余额充值");
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               et.setInputType(InputType.TYPE_NULL);
            }
        });

    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
}
