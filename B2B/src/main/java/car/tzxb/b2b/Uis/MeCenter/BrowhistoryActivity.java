package car.tzxb.b2b.Uis.MeCenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.BrowhistoryBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class BrowhistoryActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_browhistory)
    RecyclerView recy;
    @BindView(R.id.tv_empty_browhistory)
    TextView tv_empty;
    @BindView(R.id.tv_actionbar_right)
    TextView tv_right;
    private boolean flag;
    private boolean isShow=true;
    private List<BrowhistoryBean.DataBean> dataBeanList=new ArrayList<>();
    private CommonAdapter<BrowhistoryBean.DataBean> adapter;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_browhistory;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("浏览记录");
        tv_right.setText("编缉");
        initRecy();
    }

    private void initRecy() {
        recy.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<BrowhistoryBean.DataBean>(MyApp.getContext(), R.layout.order_list_item,dataBeanList) {
            @Override
            protected void convert(ViewHolder holder, final BrowhistoryBean.DataBean dataBean, int position) {
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,0,0,0);
                View parent=holder.getView(R.id.parent);
                parent.setLayoutParams(layoutParams);
                holder.getView(R.id.ll_order_title).setBackgroundColor(Color.parseColor("#E1E1E1"));
                TextView tv_time=holder.getView(R.id.tv_order_status_shop_name);
                tv_time.setText(dataBean.getAdd_time());
                tv_time.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                //隐藏
                holder.getView(R.id.ll_order_status_infor).setVisibility(View.GONE);
                holder.getView(R.id.ll_order_status_infor2).setVisibility(View.GONE);
                holder.getView(R.id.et_mesg).setVisibility(View.GONE);
                //内部recyclerview
                RecyclerView InnerRecy=holder.getView(R.id.recy_order_inner);
                InnerRecy.setLayoutManager(new LinearLayoutManager(BrowhistoryActivity.this));
                final List<BrowhistoryBean.DataBean.ChildDataBean> InnList=dataBean.getChild_data();
                CommonAdapter<BrowhistoryBean.DataBean.ChildDataBean> InnAdaper=new CommonAdapter<BrowhistoryBean.DataBean.ChildDataBean>(MyApp.getContext(),R.layout.commn_item,InnList) {
                    @Override
                    protected void convert(ViewHolder holder, final BrowhistoryBean.DataBean.ChildDataBean childDataBean, int position) {
                        //图片
                        Glide.with(MyApp.getContext()).load(childDataBean.getImg_url()).into((ImageView) holder.getView(R.id.iv_category));
                        //名字
                        holder.setText(R.id.tv_catagroy_name,childDataBean.getTitle());
                        //价格
                        holder.setText(R.id.tv_category_pice,"¥"+childDataBean.getSeal_price());
                        //隐藏控件
                        holder.getView(R.id.tv_goods_type).setVisibility(View.GONE);
                        ImageView iv= holder.getView(R.id.iv_gwc_icon);
                        iv.setVisibility(View.VISIBLE);
                        iv.setImageResource(R.mipmap.commodity_icon_atc2);
                        //删除
                        TextView tv_del=holder.getView(R.id.tv_del_browhistory);
                        if(!isShow){
                            tv_del.setVisibility(View.VISIBLE);
                        }else {
                            tv_del.setVisibility(View.GONE);
                        }
                        tv_del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delBrow(childDataBean.getId());
                            }
                        });

                    }
                };
                InnerRecy.setAdapter(InnAdaper);
                InnAdaper.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        BrowhistoryBean.DataBean.ChildDataBean bean=InnList.get(position);
                        Intent intent=new Intent(BrowhistoryActivity.this, GoodsXqActivity.class);
                        intent.putExtra("mainId",bean.getId());
                        startActivity(intent);
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });
            }
        };
        recy.setAdapter(adapter);
    }

    /**
     * 删除浏览
     * @param id
     */
    private void delBrow(String id) {
       String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("删除浏览历史",Constant.baseUrl+"item/index.php?c=Goods&m=DeleteUserHistory"+"&user_id="+userId+"&goods_ids="+id);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Goods&m=DeleteUserHistory")
                .addParams("user_id",userId)
                .addParams("goods_ids",id)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                           if(response.getStatus()==1){
                               MyToast.makeTextAnim(MyApp.getContext(),response.getMsg(),0, Gravity.CENTER,0,0).show();
                              Refresh();
                           }
                    }
                });
    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    private void Refresh() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的浏览记录", Constant.baseUrl + "item/index.php?c=Goods&m=GoodsHistoryList" + "&user_id=1");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=GoodsHistoryList")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BrowhistoryBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BrowhistoryBean response, int id) {
                        dataBeanList = response.getData();
                        adapter.add(dataBeanList,true);
                       if(dataBeanList.size()==0){
                           tv_empty.setVisibility(View.VISIBLE);
                           tv_right.setVisibility(View.VISIBLE);
                       }
                    }
                });
    }


    @OnClick(R.id.tv_actionbar_back)
    public void bcak() {
        onBackPressed();
    }
    @OnClick(R.id.tv_actionbar_right)
    public void edit(){
        if(!flag){
            tv_right.setText("完成");
            flag=true;
            isShow=false;
            adapter.notifyDataSetChanged();
        }else {
            tv_right.setText("编缉");
            flag=false;
            isShow=true;
            adapter.notifyDataSetChanged();
        }
    }
}
