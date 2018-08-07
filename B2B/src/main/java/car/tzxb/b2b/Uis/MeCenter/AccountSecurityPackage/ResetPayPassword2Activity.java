package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myview.Custkeyboard.KeyboardAdapter;
import car.myview.Custkeyboard.KeyboardView;
import car.myview.Custkeyboard.PasswordInputView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

public class ResetPayPassword2Activity extends MyBaseAcitivity implements KeyboardAdapter.OnKeyboardClickListener,PasswordInputView.OnFinishListener{
    @BindView(R.id.password_view2)
    PasswordInputView etInput;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.keyboard_view2)
    KeyboardView keyboardView;
    private List<String> datas;
    private String pass;

    @Override
    public void initParms(Bundle parms) {
        pass = getIntent().getStringExtra("pass");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reset_pay_password2;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("支付密码");
        initCustKey();
    }

    private void initCustKey() {
        DeviceUtils.hideSystemSoftKeyBoard(this, etInput);
        datas = keyboardView.getDatas();
        etInput.setOnFinishListener(this);
        keyboardView.setOnKeyBoardClickListener(this);
        etInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!keyboardView.isVisible()) {
                    keyboardView.show();
                }
            }
        });

    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    public void onKeyClick(View view, RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 9: // 按下小数点
                String num = etInput.getText().toString().trim();
                if (!num.contains(datas.get(position))) {
                    num += datas.get(position);
                    etInput.setText(num);
                    etInput.setSelection(etInput.getText().length());
                }
                break;
            default: // 按下数字键
                if ("0".equals(etInput.getText().toString().trim())) { // 第一个数字按下0的话，第二个数字只能按小数点
                    break;
                }
                etInput.setText(etInput.getText().toString().trim() + datas.get(position));
                etInput.setSelection(etInput.getText().length());
                break;
        }
    }

    @Override
    public void onDeleteClick(View view, RecyclerView.ViewHolder holder, int position) {
        // 点击删除按钮
        String num = etInput.getText().toString().trim();
        if (num.length() > 0) {
            etInput.setText(num.substring(0, num.length() - 1));
            etInput.setSelection(etInput.getText().length());
        }
    }

    @Override
    public void setOnPasswordFinished() {
        if (etInput.getOriginText().length() == etInput.getMaxPasswordLength()) {
            String endPass=etInput.getOriginText();
            if(endPass.equals(pass)){
                MyToast.makeTextAnim(MyApp.getContext(),"设置密码成功",0, Gravity.CENTER,0,0).show();
            }else {
                MyToast.makeTextAnim(MyApp.getContext(),"密码错误",0, Gravity.CENTER,0,0).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (keyboardView.isVisible()) {
            keyboardView.dismiss();
            return;
        }
        super.onBackPressed();
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }


}
