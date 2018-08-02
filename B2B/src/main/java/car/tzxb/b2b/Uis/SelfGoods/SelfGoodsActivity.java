package car.tzxb.b2b.Uis.SelfGoods;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.Bean.HomeBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class SelfGoodsActivity extends MyBaseAcitivity implements NestedScrollView.OnTouchListener{

   @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tab_self)
    TabLayout  tabLayout;
    @BindView(R.id.recy_self)
    RecyclerView recyclerView;
    @BindView(R.id.nest_self)
    NestedScrollView nestedScrollView;
    private String  cateId;
    private int pager;
    private List<HomeBean.DataBean.CategoryBean> categoryBeanList;
    private List<BaseDataListBean.DataBean> beanList=new ArrayList<>();
    private CommonAdapter<BaseDataListBean.DataBean> adapter;
    private LoadingDialog dialog;

    @Override
    public void initParms(Bundle parms) {
        categoryBeanList = (List<HomeBean.DataBean.CategoryBean>) getIntent().getSerializableExtra("list");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_self_goods;
    }

    @Override
    public void doBusiness(Context mContext) {
         tv_title.setText("自营商品");
        //  getData();
          initEvent();
          initUi();
          nestedScrollView.setOnTouchListener(this);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    public void showLoad(){
        dialog = new LoadingDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(),"aaa");
    }

    private void initEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                  showLoad();
                  int position=tab.getPosition();
                  pager=0;
                  cateId=categoryBeanList.get(position).getId();
                  getData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initUi() {
        //tab
        for (int i = 0; i < categoryBeanList.size(); i++) {
            String title = categoryBeanList.get(i).getCategory_name();
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        //recycleview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.commn_item,beanList) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                 //图片
                ImageView iv=holder.getView(R.id.iv_category);
                Glide.with(MyApp.getContext()).load(bean.getImg_url()).into(iv);
                //标题
                 holder.setText(R.id.tv_catagroy_name,"\u3000\u3000"+bean.getGoods_name());
                //价格
                holder.setText(R.id.tv_category_pice,"¥"+bean.getSeal_price());
                TextView tv_maker=holder.getView(R.id.tv_maker_price);
                tv_maker.setText(Html.fromHtml("原价:¥" + bean.getMarket_price()));
                tv_maker.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
                //类型
                holder.setText(R.id.tv_goods_type,bean.getDealer());
                //加入
                TextView tv_add=holder.getView(R.id.tv_goods_count);
                tv_add.setText("立即购买");
                tv_add.setTextColor(Color.WHITE);
                tv_add.setBackground(getResources().getDrawable(R.drawable.bg1));
                tv_add.setPadding(5,0,5,0);

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                 BaseDataListBean.DataBean dataBeen=beanList.get(position);
                Intent intent=new Intent(SelfGoodsActivity.this, GoodsXqActivity.class);
                intent.putExtra("mainId", dataBeen.getGoods_id());
                startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void getData() {
        String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("自营商品", Constant.baseUrl+"item/index.php?c=Goods&m=SelfGoodsList&user_id="+userId+"&cate="+cateId+"&pager"+pager+"&pagesize=10"+"&sales=desc");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=SelfGoodsList&sales=desc")
                .addParams("user_id", userId)
                .addParams("cate", cateId)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .addParams("sales","desc")
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if(dialog!=null){
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        if(dialog!=null){
                            dialog.dismiss();
                        }
                        beanList=response.getData();
                        adapter.add(beanList,true);
                        if(beanList.size()!=0){
                            pager++;
                        }
                    }
                });
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                //当文本的measureheight 等于scroll滚动的长度+scroll的height
                View view = ((NestedScrollView) v).getChildAt(0);
                if (view.getMeasuredHeight() <= v.getScrollY() + v.getHeight()) {
                     showLoad();
                     LoadMore();
                }
                break;
            }
        }
        return false;
    }

    private void LoadMore() {
        Log.i("自营商品加载更多", Constant.baseUrl + "item/index.php?c=Goods&m=GoodsList" + "&cate=" + cateId + "&page=" + pager + "&pagesize=10"+"&sales=desc");
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=SelfGoodsList&sales=desc")
                .addParams("user_id", userId)
                .addParams("cate", cateId)
                .addParams("page", String.valueOf(pager))
                .addParams("pagesize", "10")
                .addParams("sales","desc")
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        List<BaseDataListBean.DataBean> tempList = response.getData();
                        beanList.addAll(tempList);
                        adapter.add(beanList, true);
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (tempList.size()!= 0) {
                            pager++;
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), "我也是有底线的", 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }


}
