package car.tzxb.b2b.Uis.HomePager.Wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.Custkeyboard.KeyboardAdapter;
import car.myview.Custkeyboard.KeyboardView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.wxapi.WXPayEntryActivity;

public class RechargeActivity extends MyBaseAcitivity implements KeyboardAdapter.OnKeyboardClickListener {

    @BindView(R.id.et_input_number)
    EditText etInput;
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
        return R.layout.activity_recharge;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("余额充值");
        initCustKey();

    }

    private void initCustKey() {
        DeviceUtils.hideSystemSoftKeyBoard(this, etInput);
        etInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!keyboardView.isVisible()) {
                    keyboardView.show();
                }
            }
        });

        datas = keyboardView.getDatas();

        keyboardView.setOnKeyBoardClickListener(this);
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
                if(".".equals(String.valueOf(num.charAt(0)))){
                    etInput.setText("");
                }
                break;
            default: // 按下数字键
                String num2=etInput.getText().toString().trim();
                if("0".equals(num2)){
                    break;
                }
                //取出前2位
                if(num2.length()>=2){
                    if("0.".equals(num2.substring(0,2))){
                        if(num2.length()>3){
                            String str = etInput.getText().toString();
                            String body = str.substring(0, str.length() - 1);
                            etInput.setText(body);
                            etInput.setSelection(body.length());
                        }
                    }
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

    @OnClick(R.id.btn_recharge_next)
    public void next() {
        String price = etInput.getText().toString();
        if (TextUtils.isEmpty(price)||"0".equals(price)||"0.".equals(price)) {
            MyToast.makeTextAnim(MyApp.getContext(), "请输入要充值的金额", 0, Gravity.CENTER, 0, 0).show();
            return;
        }
        Intent intent = new Intent(this, WXPayEntryActivity.class);
        intent.putExtra("total", price);
        intent.putExtra("from", "Recharge");
        startActivity(intent);
    }

}
