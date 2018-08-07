package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.Custkeyboard.KeyboardAdapter;
import car.myview.Custkeyboard.KeyboardView;
import car.myview.Custkeyboard.PasswordInputView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;

public class ResetPayPasswordActivity extends MyBaseAcitivity implements KeyboardAdapter.OnKeyboardClickListener,PasswordInputView.OnFinishListener{
      @BindView(R.id.password_view)
    PasswordInputView  etInput;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;
    private List<String> datas;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reset_pay_password;
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
           Toast.makeText(this, "密码是" + etInput.getOriginText(), Toast.LENGTH_LONG).show();
           Intent intent=new Intent(this,ResetPayPassword2Activity.class);
           intent.putExtra("pass",etInput.getOriginText());
           startActivity(intent);
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
