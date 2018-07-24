package car.tzxb.b2b.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.BageView.BadgeView;
import car.myview.CircleImageView.CircleImageView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.MyCenterBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Uis.MeCenter.BrowhistoryActivity;
import car.tzxb.b2b.Uis.MeCenter.CollectActivity;
import car.tzxb.b2b.Uis.MeCenter.MyAddressActivity;
import car.tzxb.b2b.Uis.MeCenter.SettingsActivity;
import car.tzxb.b2b.Uis.OpenShopPackage.OpenShopEntranceActivity;
import car.tzxb.b2b.Uis.Order.OrderStatusActivity;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;


public class MyFragment extends MyBaseFragment implements RadioGroup.OnCheckedChangeListener,RadioButton.OnCheckedChangeListener,View.OnClickListener{

    @BindView(R.id.rg_my_service)
    RadioGroup rg_service;
    @BindView(R.id.recycler_recommend)
    RecyclerView recyclerView;
    @BindView(R.id.rg_collect)
    RadioGroup rg_collect;
    @BindView(R.id.txt_user_name)
    TextView tv_username;
    @BindView(R.id.tv_all_order)
    TextView tv_all_order;
    @BindView(R.id.iv_my_setup)
    ImageView iv_setup;
    @BindView(R.id.ll_login_regist)
    LinearLayout ll_login_regist;
    @BindView(R.id.img_avatar)
    CircleImageView cv_headerImager;
    @BindViews({ R.id.rb_yhq_number, R.id.rb_gold_number, R.id.rb_jf_number,R.id.rb_balance_number })
    List<RadioButton> PropertyViews;
    @BindViews({R.id.rb_collect_goods,R.id.rb_collect_shop,R.id.rb_browse_record})
    List<RadioButton> CollectViews;
    @BindViews({R.id.rb_all,R.id.rb_dfh,R.id.rb_dsh,R.id.rb_dpj,R.id.rb_tk})
    List<RadioButton> OrderViews;
    @BindView(R.id.bv1)
    BadgeView bv1;
    @BindView(R.id.bv2)
    BadgeView bv2;
    @BindView(R.id.bv3)
    BadgeView bv3;
    @BindView(R.id.bv4)
    BadgeView bv4;
    @BindView(R.id.bv5)
    BadgeView bv5;
    private MyCenterBean.DataBean.UserInfoBean userBean;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initData() {

        rg_service.setOnCheckedChangeListener(this);
        rg_collect.setOnCheckedChangeListener(this);
        OrderViews.get(0).setOnCheckedChangeListener(this);
        OrderViews.get(1).setOnCheckedChangeListener(this);
        OrderViews.get(2).setOnCheckedChangeListener(this);
        OrderViews.get(3).setOnCheckedChangeListener(this);
        OrderViews.get(4).setOnCheckedChangeListener(this);
        iv_setup.setOnClickListener(this);
        tv_all_order.setOnClickListener(this);
        initRecommend();
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("走onResume","aaa");
        Judge();
    }

    private void Judge() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
       Log.i("我的个人中心",Constant.baseUrl+"item/index.php?c=Home&m=MyCenter&user_id="+userId);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl+"item/index.php?c=Home&m=MyCenter")
                .tag(this)
                .addParams("user_id",userId)
                .build()
                .execute(new GenericsCallback<MyCenterBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                         notLogin();
                    }

                    @Override
                    public void onResponse(MyCenterBean response, int id) {
                        hasLogin(response);
                    }
                });
    }

    /**
     * 未登录
     */
    private void notLogin() {
        ll_login_regist.setVisibility(View.VISIBLE);
        tv_username.setVisibility(View.INVISIBLE);
        cv_headerImager.setImageResource(R.mipmap.my_icon_dhi);
        //收藏
        CollectViews.get(0).setText("0" + "\n收藏商品");
        CollectViews.get(1).setText("0" + "\n收藏店铺");
        CollectViews.get(2).setText("0" + "\n浏览记录");
        //金库
        PropertyViews.get(0).setText("0"+"\n优惠券");
        PropertyViews.get(1).setText("0"+"\n金币");
        PropertyViews.get(2).setText("0"+"\n积分");
        PropertyViews.get(3).setText("0"+"\n余额");
    }


    /**
     * 已登录
     * @param response
     */
    private void hasLogin(MyCenterBean response) {
        userBean = response.getData().getUserInfo();
        ll_login_regist.setVisibility(View.INVISIBLE);
        tv_username.setVisibility(View.VISIBLE);
        tv_username.setText(userBean.getNackname());
        Glide.with(MyApp.getContext()).load(userBean.getHead_img()).asBitmap().into(cv_headerImager);
        //收藏
        CollectViews.get(0).setText(response.getData().getUserCollect().get(0) + "\n收藏商品");
        CollectViews.get(1).setText(response.getData().getUserCollect().get(1) + "\n收藏店铺");
        CollectViews.get(2).setText(response.getData().getUserCollect().get(2) + "\n浏览记录");
        //金库
        PropertyViews.get(0).setText(response.getData().getMyProperty().get(0)+"\n优惠券");
        PropertyViews.get(1).setText(response.getData().getMyProperty().get(1)+"\n金币");
        PropertyViews.get(2).setText(response.getData().getMyProperty().get(2)+"\n积分");
        PropertyViews.get(3).setText(response.getData().getMyProperty().get(3)+"\n余额");

        //订单数
        bv1.setBackground(getResources().getDrawable(R.drawable.circle_bg2));
        bv1.setTextColor(Color.RED);
        bv1.setTypeface(Typeface.DEFAULT);
        bv1.setText(response.getData().getOrderNumber().get(0)+"");

        bv2.setBackground(getResources().getDrawable(R.drawable.circle_bg2));
        bv2.setTextColor(Color.RED);
        bv2.setTypeface(Typeface.DEFAULT);
        bv2.setText(response.getData().getOrderNumber().get(1)+"");

        bv3.setBackground(getResources().getDrawable(R.drawable.circle_bg2));
        bv3.setTextColor(Color.RED);
        bv3.setTypeface(Typeface.DEFAULT);
        bv3.setText(response.getData().getOrderNumber().get(2)+"");

        bv4.setBackground(getResources().getDrawable(R.drawable.circle_bg2));
        bv4.setTextColor(Color.RED);
        bv4.setTypeface(Typeface.DEFAULT);
        bv4.setText(response.getData().getOrderNumber().get(3)+"");

        bv5.setBackground(getResources().getDrawable(R.drawable.circle_bg2));
        bv5.setTextColor(Color.RED);
        bv5.setTypeface(Typeface.DEFAULT);
        bv5.setText(response.getData().getOrderNumber().get(4)+"");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("走可见时","aaa");
        if(!hidden){
            Judge();
        }
    }

    /**
     * 登录
     */
    @OnClick(R.id.tv_login)
    public void login(){
        Intent intent=new Intent(getActivity(),LoginActivity.class);
        startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    /**
     * 注册
     */
    @OnClick(R.id.tv_regist)
    public void regist(){
        Intent intent=new Intent(getActivity(), OpenShopEntranceActivity.class);
        intent.putExtra("from","login");
        startActivity(intent);
    }


    private void initRecommend() {
        String[] str = {"哈哈哈哈", "呵呵呵", "嗯嗯嗯", "哦哦哦", "好好好"};
        List<BaseStringBean> lists = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            BaseStringBean bean = new BaseStringBean(str[i], null);
            lists.add(bean);
        }

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10, 2));
        recyclerView.setNestedScrollingEnabled(false);
        CommonAdapter<BaseStringBean> adapter = new CommonAdapter<BaseStringBean>(MyApp.getContext(), R.layout.recommend_layout, lists) {
            @Override
            protected void convert(ViewHolder holder, BaseStringBean bean, int position) {
                ImageView iv = holder.getView(R.id.iv_recommend);
                Glide.with(MyApp.getContext()).load(R.mipmap.ic_launcher).override(256, 256).into(iv);
                holder.setText(R.id.tv_recommend_title, bean.getShop_name());
            }
        };
        recyclerView.setAdapter(adapter);

    }



    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        RadioButton rb = radioGroup.findViewById(i);
        rb.setChecked(false);
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);

        if (userId == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            return;
        }
        Intent intent = new Intent();
        switch (i) {
            case R.id.rb_collect_goods:
                intent.setClass(getActivity(), CollectActivity.class);
                intent.putExtra("index",0);
                break;
            case R.id.rb_collect_shop:
                intent.setClass(getActivity(), CollectActivity.class);
                intent.putExtra("index",1);
                break;
            case R.id.rb_browse_record:
                intent.setClass(getActivity(), BrowhistoryActivity.class);
                break;
            case R.id.rb_address:
                intent.setClass(getActivity(),MyAddressActivity.class);
                break;
           default:
               intent.setClass(getActivity(),MyAddressActivity.class);
               break;
        }
        startActivity(intent);
    }


   @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        buttonView.setChecked(false);
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);

        if (userId == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            return;
        }

         Intent intent = new Intent();
          switch (buttonView.getId()){
              case R.id.rb_all:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "all");
                intent.putExtra("index", 0);
                break;
            case R.id.rb_dfh:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "stay_payment");
                intent.putExtra("index", 1);
                break;
            case R.id.rb_dsh:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "stay_shipment");
                intent.putExtra("index", 2);
                break;
            case R.id.rb_dpj:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "stay_take");
                intent.putExtra("index", 3);
                break;
            case R.id.rb_tk:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "stay_evaluate");
                intent.putExtra("index", 4);
                break;
          }
          startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(isFastClick()){
            String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
            Intent intent = new Intent();
            if (userId == null) {
                intent.setClass(getActivity(), LoginActivity.class);
            }else {
                switch (v.getId()){
                    case R.id.tv_all_order:
                        intent.setClass(getActivity(), OrderStatusActivity.class);
                        intent.putExtra("index", 0);
                        intent.putExtra("type", "all");
                        break;
                    case R.id.iv_my_setup:
                        intent.setClass(getActivity(), SettingsActivity.class);
                        break;
                }
            }
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        }
    }

}
