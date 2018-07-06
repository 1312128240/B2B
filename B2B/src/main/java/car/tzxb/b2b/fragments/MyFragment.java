package car.tzxb.b2b.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.LoginActivity;
import car.tzxb.b2b.Uis.MeCenter.CollectActivity;
import car.tzxb.b2b.Uis.MeCenter.MyAddressActivity;
import car.tzxb.b2b.Uis.MeCenter.SettingsActivity;
import car.tzxb.b2b.Uis.OpenShopPackage.OpenShopEntranceActivity;
import car.tzxb.b2b.Uis.Order.OrderStatusActivity;
import car.tzxb.b2b.Util.SPUtil;


public class MyFragment extends MyBaseFragment implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.rg_order_status)
    RadioGroup rg_order;
    @BindView(R.id.rg_my_service)
    RadioGroup rg_service;
    @BindView(R.id.recycler_recommend)
    RecyclerView recyclerView;
    @BindView(R.id.rg_collect)
    RadioGroup rg_collect;
    @BindView(R.id.rb_collect_goods)
    RadioButton rb_collect_goods;
    @BindView(R.id.rb_collect_shop)
    RadioButton rb_collect_shop;
    @BindView(R.id.rb_browse_record)
    RadioButton rb_browse_record;
    @BindView(R.id.txt_user_name)
    TextView tv_username;
    @BindView(R.id.ll_login_regist)
    LinearLayout ll_login_regist;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initData() {
        rg_order.setOnCheckedChangeListener(this);
      //  rg_service.setOnCheckedChangeListener(this);
        rg_collect.setOnCheckedChangeListener(this);
        initUi();
        initRecommend();
    }

    private void initUi() {

        rg_service.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                  RadioButton rb=group.findViewById(checkedId);
                  rb.setChecked(false);
                String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
                if (userId == null) {
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    return;
                }

                  switch (checkedId){
                      case R.id.rb_address:
                          Intent intent=new Intent(getActivity(),MyAddressActivity.class);
                          startActivity(intent);
                          break;
                  }
            }
        });
        rb_collect_goods.setText("0" + "\n收藏商品");
        rb_collect_shop.setText("0" + "\n收藏店铺");
        rb_browse_record.setText("0" + "\n浏览记录");
    }


    @Override
    public void onResume() {
        super.onResume();
        Judge();
    }

    private void Judge() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        String mobile=SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile",null);
        if (userId == null) {
            ll_login_regist.setVisibility(View.VISIBLE);
            tv_username.setVisibility(View.INVISIBLE);
        }else {
            ll_login_regist.setVisibility(View.INVISIBLE);
            tv_username.setVisibility(View.VISIBLE);
            tv_username.setText(mobile);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            Judge();
        }
    }

    /**
     * 查看全部订单
     */
    @OnClick(R.id.tv_all_order)
    public void all_order() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Intent intent = new Intent();
        if (userId == null) {
            intent.setClass(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            return;
        }
        intent.setClass(getActivity(), OrderStatusActivity.class);
        intent.putExtra("index", 0);
        intent.putExtra("type", "all");
        startActivity(intent);
    }

    /**
     * 去设置界面
     */
    @OnClick(R.id.iv_my_setup)
    public void setting(){
        Intent intent=new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    /**
     * 登录注册
     */
    @OnClick({R.id.tv_login,R.id.tv_regist})
    public void login(){
        Intent intent=new Intent(getActivity(),LoginActivity.class);
        startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
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
    protected BasePresenter bindPresenter() {
        return null;
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
                break;
            case R.id.rb_collect_shop:
                intent.setClass(getActivity(), CollectActivity.class);
                break;
            case R.id.rb_browse_record:
                intent.setClass(getActivity(), CollectActivity.class);
                break;
            case R.id.rb_all:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "all");
                intent.putExtra("index", 0);
                break;
            case R.id.rb_dfk:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "stay_payment");
                intent.putExtra("index", 1);
                break;
            case R.id.rb_dfh:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "stay_shipment");
                intent.putExtra("index", 2);
                break;
            case R.id.rb_dsh:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "stay_take");
                intent.putExtra("index", 3);
                break;
            case R.id.rb_dpj:
                intent.setClass(getActivity(), OrderStatusActivity.class);
                intent.putExtra("type", "stay_evaluate");
                intent.putExtra("index", 4);
                break;
        }
        startActivity(intent);
    }


}
