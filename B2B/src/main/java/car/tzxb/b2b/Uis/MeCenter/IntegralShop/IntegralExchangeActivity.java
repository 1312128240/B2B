package car.tzxb.b2b.Uis.MeCenter.IntegralShop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class IntegralExchangeActivity extends MyBaseAcitivity{
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_history)
    RecyclerView recyclerview;
    private CommonAdapter<BaseDataListBean.DataBean> adapter;
    private List<BaseDataListBean.DataBean> beanList=new ArrayList<>();
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_integral_exchange;
    }

    @Override
    public void doBusiness(Context mContext) {
         tv_title.setText("兑换记录");

         initRecy();
    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }


    private void initRecy() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.commn_item,beanList) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                //图片
                ImageView iv=holder.getView(R.id.iv_category);
                Glide.with(MyApp.getContext()).load(bean.getImg_url()).error(R.drawable.bucket_no_picture).into(iv);
                //标题
                holder.setText(R.id.tv_catagroy_name,bean.getProduct_title());
                //兑换时间
                holder.setText(R.id.tv_maker_price,bean.getAdd_time());
                //状态
                holder.setText(R.id.tv_goods_count,bean.getOrderstatus());
                //隐藏控件
                holder.getView(R.id.tv_goods_type).setVisibility(View.GONE);

            }
        };
        recyclerview.setAdapter(adapter);

    }

    private void getData() {
        String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("兑换记录",Constant.baseUrl+"goods/integralgoods.php?m=imm_exchange&userid="+userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"goods/integralgoods.php?m=imm_exchange")
                .addParams("userid",userId)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                               beanList=response.getData();
                               adapter.add(beanList,true);
                    }
                });
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back(){
       onBackPressed();
   }
}
