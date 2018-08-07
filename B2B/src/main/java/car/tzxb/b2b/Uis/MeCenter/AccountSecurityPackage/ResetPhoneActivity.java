package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class ResetPhoneActivity extends MyBaseAcitivity {
     @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.btn_new_phone)
    Button btn;
    @BindView(R.id.et_new_phone)
    EditText et;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reset_phone;
    }

    @Override
    public void doBusiness(Context mContext) {
          tv_title.setText("换绑手机");
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0){
                    btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg1));
                }else {
                    btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg10));
                }
            }
        });
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }
}
