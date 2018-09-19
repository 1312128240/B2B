package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class NickNameActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.et_nick)
    EditText et_nick;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_nick_name;
    }

    @Override
    public void doBusiness(Context mContext) {
       tv_title.setText("个人资料");
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.tv_actionbar_back,R.id.btn_cancle_nick})
    public void back(){
        onBackPressed();
    }
    @OnClick(R.id.btn_set_nick)
    public void nick(){
       // String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        String nickName=et_nick.getText().toString();
        if(TextUtils.isEmpty(nickName)){
            AnimationUtil.Sharke(MyApp.getContext(),et_nick);
            MyToast.makeTextAnim(MyApp.getContext(),"昵称不能为空",0, Gravity.CENTER,0,0).show();
            return;
        }
        Intent intent=new Intent();
        intent.putExtra("nick",nickName);
        setResult(RESULT_OK, intent);
        finish();
    }
}
