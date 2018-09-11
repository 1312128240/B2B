package car.tzxb.b2b.Uis.MeCenter.AccountSecurityPackage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.umeng.analytics.MobclickAgent;

import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myview.Custkeyboard.KeyboardAdapter;
import car.myview.Custkeyboard.KeyboardView;
import car.myview.Custkeyboard.PasswordInputView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.MeCenter.SettingsActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

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
            if(!endPass.equals(pass)){
                MyToast.makeTextAnim(MyApp.getContext(),"密码错误",0, Gravity.CENTER,0,0).show();
            }else {
                setPayPass(endPass);
            }

        }
    }


    private void setPayPass(String endPass) {
        String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        //支付密码加密
        String  stringA="pay_pwd="+endPass+"&user_id="+userId;
        String  stringB = stringA + "&key=H1ereN3nfdF6wZcoeBdYUfQv7tq1Pq5t";
        String  stringMd5= StringUtil.stringToMD5(stringB);
        String  stringUpper=StringUtil.UpperLowerCase(stringMd5).toString();
        //生成支付密码签名
        String m = "UserSetPayPassWord";
        String signA ="c=Home"+"&m=" + m + "&paypassword=" +stringUpper + "&user_id=" +userId + "&key=6ljH6wpC4vDPy%Ruqlr4JJmG0kLo%^yN";
        final String signMd5 = StringUtil.stringToMD5(signA);
        String signUpper = StringUtil.UpperLowerCase(signMd5).toString();

        Log.i("设置支付密码",Constant.baseUrl+"item/index.php?c=Home&m=UserSetPayPassWord"+"&user_id="+userId+"&paypassword="+stringUpper+"&sign="+signUpper);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?")
                .addParams("c","Home")
                .addParams("m",m)
                .addParams("user_id",userId)
                .addParams("paypassword",stringUpper)
                .addParams("sign", signUpper)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                         if(response.getStatus()==1){
                             deleActivity();
                         }else {
                            MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0,Gravity.CENTER,0,0).show();
                         }
                    }
                });

    }

    private void deleActivity() {
        AccountSecurityHomePageActivity a2= (AccountSecurityHomePageActivity) AccountSecurityHomePageActivity.sInstance;
        if(a2!=null){
            a2.finish();
        }
        AccountSecurityYzmActivity a3= (AccountSecurityYzmActivity) AccountSecurityYzmActivity.sInstance;
        ResetPayPasswordActivity a4= (ResetPayPasswordActivity) ResetPayPasswordActivity.sInstance;
        a3.finish();
        a4.finish();
        this.finish();
        onBackPressed();
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
