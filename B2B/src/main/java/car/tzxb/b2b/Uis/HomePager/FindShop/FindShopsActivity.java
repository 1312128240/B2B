package car.tzxb.b2b.Uis.HomePager.FindShop;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.SpacesItem_LinerLayoutManager;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.FindShopsBenn;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Uis.HomePager.SelfGoods.OemActivity;
import car.tzxb.b2b.Uis.MeCenter.BrowhistoryActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.PopWindow.FindShopPop;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class FindShopsActivity extends MyBaseAcitivity {
    @BindView(R.id.rg_find_shop)
    RadioGroup rg;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_find_shop)
    RecyclerView recy;
    @BindView(R.id.iv_shaixuan)
    ImageView iv_sx;
     @BindView(R.id.ll_test)
     LinearLayout popTop;
    private String userId;
    private String cate;
    private List<FindShopsBenn.DataBean> beanList=new ArrayList<>();
    private CommonAdapter<FindShopsBenn.DataBean> adapter;
    private FindShopPop findShopPop;


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_find_shops;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("发现好店");
        userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        getCate();
        initAdapter();
    }

    private void initAdapter() {
        recy.setLayoutManager(new LinearLayoutManager(this));
        final GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#FF9900"));
        drawable.setCornerRadius(50);

        adapter = new CommonAdapter<FindShopsBenn.DataBean>(MyApp.getContext(), R.layout.find_shop_item,beanList) {
            @Override
            protected void convert(ViewHolder holder, final FindShopsBenn.DataBean dataBean, int position) {
                //门店头像
                ImageView iv=holder.getView(R.id.iv_find_shop);
                Glide.with(MyApp.getContext()).load(dataBean.getImg()).error(R.drawable.bucket_no_picture).into(iv);
                //名字
                holder.setText(R.id.tv_find_shop_name,dataBean.getShop_name());
                //访客量
                holder.setText(R.id.tv_find_shop_fk,"今日访客量: "+dataBean.getDay_click_num());
                //进店
                TextView tv_go=holder.getView(R.id.tv_go_find_shop_xq);
                tv_go.setBackground(drawable);
                tv_go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(FindShopsActivity.this,FindShopXqActivity.class);
                        intent.putExtra("shop_id",dataBean.getID());
                        startActivity(intent);
                    }
                });
                //3张图片
                final ImageView iv1=holder.getView(R.id.iv_find_shop1);
                final ImageView iv2=holder.getView(R.id.iv_find_shop2);
                final ImageView iv3=holder.getView(R.id.iv_find_shop3);
                final List<FindShopsBenn.DataBean.GoodsBean> goodsBeen=dataBean.getGoods();
                //商品图片
                if(goodsBeen.size()>2){
                    String imgUrl1=goodsBeen.get(0).getImg_url();
                    String imgUrl2=goodsBeen.get(1).getImg_url();
                    String imgUrl3=goodsBeen.get(2).getImg_url();
                    Glide.with(MyApp.getContext()).load(imgUrl1).error(R.drawable.bucket_no_picture).into(iv1);
                    Glide.with(MyApp.getContext()).load(imgUrl2).error(R.drawable.bucket_no_picture).into(iv2);
                    Glide.with(MyApp.getContext()).load(imgUrl3).error(R.drawable.bucket_no_picture).into(iv3);
                }


                iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(FindShopsActivity.this, GoodsXqActivity.class);
                        String mainId=goodsBeen.get(0).getID();
                        intent.putExtra("mainId", mainId);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FindShopsActivity.this,iv1,"share").toBundle());
                    }
                });

                iv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(FindShopsActivity.this, GoodsXqActivity.class);
                        String mainId=goodsBeen.get(1).getID();
                        intent.putExtra("mainId", mainId);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FindShopsActivity.this,iv2,"share").toBundle());
                    }
                });

                 iv3.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent intent=new Intent(FindShopsActivity.this, GoodsXqActivity.class);
                         String mainId=goodsBeen.get(2).getID();
                         intent.putExtra("mainId", mainId);
                         startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FindShopsActivity.this,iv3,"share").toBundle());
                     }
                 });

            }
        };
        recy.setAdapter(adapter);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void getCate() {
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Goods&m=Category")
                .addParams("user_id",userId)
                .build()
                .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataBean response, int id) {
                        List<BaseDataBean.DataBean.CategoryBean> cateList = response.getData().getCategory();
                        initTabLayout(cateList);
                    }
                });
    }

    private void initTabLayout(final List<BaseDataBean.DataBean.CategoryBean> cateList) {
        int w=DeviceUtils.dip2px(MyApp.getContext(),80);
        for (int i = 0; i <cateList.size() ; i++) {
            RadioButton rb=new RadioButton(this);
            BaseDataBean.DataBean.CategoryBean cateBean=cateList.get(i);
            rb.setButtonDrawable(null);
            rb.setId(i);
            rb.setLayoutParams(new RadioGroup.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT));
            rb.setText(cateBean.getCategory_name());
            rb.setTextColor(Color.WHITE);
            rb.setGravity(Gravity.CENTER);
            rb.setBackground(getResources().getDrawable(R.drawable.yell_black_select));
            rg.addView(rb);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                cate = cateList.get(checkedId).getId();
                 Refresh();
            }
        });
        RadioButton rb1= rg.findViewById(0);
        rb1.setChecked(true);

        //初始化popwindow
        findShopPop = new FindShopPop(this,cateList);

    }

    //刷新数据
    private void Refresh() {
        Log.i("门店列表",Constant.baseUrl+"item/index.php?c=Home&m=UserShopListV2"+"&user_id="+userId+"&cate="+cate);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Home&m=UserShopListV2")
                .addParams("user_id",userId)
                .addParams("cate",cate)
                .build()
                .execute(new GenericsCallback<FindShopsBenn>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                      Log.i("发现好店",e.toString());
                    }

                    @Override
                    public void onResponse(FindShopsBenn response, int id) {
                        beanList=response.getData();
                        adapter.add(beanList,true);
                    }
                });
    }


    @OnClick(R.id.iv_shaixuan)
    public void sx(){
      /*  int[] location = new int[2];
        recy.getLocationOnScreen(location);
        int  y = location[1];*/
        findShopPop.showAsDropDown(popTop);
        if(findShopPop.isShowing()){
            iv_sx.setImageResource(R.drawable.find_icon_more2);
        }
        findShopPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_sx.setImageResource(R.drawable.find_icon_more);
            }
        });
        findShopPop.setItemClickListener(new FindShopPop.ItemClickListener() {
            @Override
            public void click(String cateId) {
                cate=cateId;
                Refresh();
            }
        });
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
}
