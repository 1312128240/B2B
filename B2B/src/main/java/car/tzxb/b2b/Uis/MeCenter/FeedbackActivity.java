package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class FeedbackActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.et_feedback)
    EditText et_feedback;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.tv_feedback_length)
    TextView tv_length;
    private    int length;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("意见反馈");
        tv_length.setText(Html.fromHtml("<font color='#FA3314'>" + length+ "</font>"+"/140"));
        bindWatch();
    }

    private void bindWatch() {
        et_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                length=s.length();
                tv_length.setText(Html.fromHtml("<font color='#FA3314'>" + length+ "</font>"+"/140"));
            }
        });
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
}
