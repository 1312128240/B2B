package car.tzxb.b2b.Uis.MeCenter.IntegralShop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class IntegralXqActivity extends MyBaseAcitivity{

    @BindView(R.id.btn_dh)
    Button btn_dh;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_jf_goods_name)
    TextView tv_jf_name;
    @BindView(R.id.tv_jf_gold)
    TextView tv_gold;
    @BindView(R.id.tv_jf_original_prices)
    TextView tv_original_prices;
    @BindView(R.id.tv_jf_content)
    TextView tv_content;
    @BindView(R.id.iv_jf_xq)
    ImageView iv_jf;
    private String product_id;

    @Override
    public void initParms(Bundle parms) {
        product_id = getIntent().getStringExtra("product_id");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_integral_xq;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("积分商城");

        GradientDrawable gd=new GradientDrawable();
        gd.setCornerRadius(50);
        gd.setColor(Color.parseColor("#FA3314"));
        btn_dh.setBackground(gd);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back(){
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Log.i("积分商品详情",Constant.baseUrl+"goods/integralgoods.php?m=integralshop_list"+"&product_id="+product_id);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"goods/integralgoods.php?m=integralshop_list")
                .addParams("product_id",product_id)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                          if(response.getData().size()!=0){
                              initUi(response);
                          }

                    }
                });
    }

    private void initUi(BaseDataListBean response) {
         BaseDataListBean.DataBean bean=response.getData().get(0);
         Glide.with(MyApp.getContext()).load(bean.getPic()).into(iv_jf);

         tv_jf_name.setText(bean.getTitle());
         tv_gold.setText(bean.getCost_point()+"积分");

         tv_original_prices.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
         tv_original_prices.setText("原价: "+bean.getMarket_price());

         tv_content.setText(bean.getContents());
    }

}
