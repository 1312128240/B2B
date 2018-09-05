package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.Explode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.MyGoldBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class MyGoldActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_click_sign)
    TextView tv_clcik;
    @BindView(R.id.recy_sign_history)
    RecyclerView recyclerView;
    @BindView(R.id.tv_my_gold)
    TextView tv_my_gold;
    @BindView(R.id.tv_my_gold_sign)
    TextView tv_sign_date;
    private String isSign;

    private List<MyGoldBean.DataBean.LogBean> beanList = new ArrayList<>();
    private CommonAdapter<MyGoldBean.DataBean.LogBean> adapter;


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_gold;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("我的金币");
        initRecy();
    }

    private void initRecy() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //日期
        adapter = new CommonAdapter<MyGoldBean.DataBean.LogBean>(MyApp.getContext(), R.layout.my_gold_sign_item, beanList) {
            @Override
            protected void convert(ViewHolder holder, MyGoldBean.DataBean.LogBean logBean, int position) {
                //日期
              /*  String t1=logBean.getAdd_time();
                String t2=t1.substring(0,10);*/
                holder.setText(R.id.tv_sign_date, logBean.getAdd_time());
                //金币数量
                holder.setText(R.id.tv_sign_gold_num, "+" + logBean.getNumber());
                //签到成功
                holder.setText(R.id.tv_sign_succeed, "签到成功");
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的金币", Constant.baseUrl + "item/index.php?c=Home&m=UserSignInData&user_id=" + userId);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UserSignInData")
                .tag(this)
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<MyGoldBean>(new JsonGenericsSerializator()) {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(MyGoldBean response, int id) {
                        if (response.getStatus() == 1) {
                            MyGoldBean.DataBean.UserBean bean = response.getData().getUser();
                            tv_my_gold.setText(Html.fromHtml("<big>" + bean.getGold() + "</big>" + "个"));
                            tv_sign_date.setText(Html.fromHtml("<big>" + bean.getSign_in() + "</big>" + "天"));
                            beanList = response.getData().getLog();
                            adapter.add(beanList, true);
                            isSign = bean.getIs_sign_in();
                            if ("1".equals(bean.getIs_sign_in())) {
                                tv_clcik.setText("今日" + "\n已签到");
                            } else {
                                tv_clcik.setText("点击签到");
                            }

                        }
                    }
                });
    }

    @OnClick(R.id.tv_click_sign)
    public void sign(){
        if(isFastClick()){
            if("0".equals(isSign)){
               getData();
            }
        }
    }
}
