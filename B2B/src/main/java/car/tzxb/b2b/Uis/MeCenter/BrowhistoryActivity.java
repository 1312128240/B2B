package car.tzxb.b2b.Uis.MeCenter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.Adapter.BrowhistoryAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.BrowhistoryBean;
import car.tzxb.b2b.Bean.OrderBeans.GoodsOrderInfo;
import car.tzxb.b2b.Bean.OrderBeans.OrderGoodsItem;
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
    @BindView(R.id.ll_all_delete)
    LinearLayout ll_all_deletee;
    @BindView(R.id.ll_browhistory_parent)
    LinearLayout ll_browhistory_parent;
    private boolean flag;
    private List<BrowhistoryBean.DataBean> dataBeanList = new ArrayList<>();
    final List<Object> dataList=new ArrayList<>();
    private BrowhistoryAdapter adapter;

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

        adapter = new BrowhistoryAdapter(this,dataList);
        recy.setAdapter(adapter);

        adapter.setOnItemClick(new BrowhistoryAdapter.OnItemClick() {
            @Override
            public void goxq(String id) {
                Intent intent = new Intent(BrowhistoryActivity.this, GoodsXqActivity.class);
                intent.putExtra("mainId", id);
                startActivity(intent);
            }

            @Override
            public void dele(String id,int i1,int i2) {
              delBrow(id,i1,i2);
            }
        });
    }

   /* private void initRecy() {
        recy.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<BrowhistoryBean.DataBean>(MyApp.getContext(), R.layout.order_list_item, dataBeanList) {
            @Override
            protected void convert(ViewHolder holder, final BrowhistoryBean.DataBean dataBean, int position) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);
                View parent = holder.getView(R.id.parent);
                parent.setLayoutParams(layoutParams);
                holder.getView(R.id.ll_order_title).setBackgroundColor(Color.parseColor("#E1E1E1"));
                TextView tv_time = holder.getView(R.id.tv_order_status_shop_name);
                tv_time.setText(dataBean.getAdd_time());
                tv_time.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                //隐藏
                holder.getView(R.id.ll_order_status_infor).setVisibility(View.GONE);
                holder.getView(R.id.ll_order_status_infor2).setVisibility(View.GONE);
                holder.getView(R.id.et_mesg).setVisibility(View.GONE);
                //内部recyclerview
                RecyclerView InnerRecy = holder.getView(R.id.recy_order_inner);
                InnerRecy.setLayoutManager(new LinearLayoutManager(BrowhistoryActivity.this));
                final List<BrowhistoryBean.DataBean.ChildDataBean> InnList = dataBean.getChild_data();
                CommonAdapter<BrowhistoryBean.DataBean.ChildDataBean> InnAdaper = new CommonAdapter<BrowhistoryBean.DataBean.ChildDataBean>(MyApp.getContext(), R.layout.commn_item, InnList) {
                    @Override
                    protected void convert(ViewHolder holder, final BrowhistoryBean.DataBean.ChildDataBean childDataBean, int position) {
                        //图片
                        Glide.with(MyApp.getContext()).load(childDataBean.getImg_url()).into((ImageView) holder.getView(R.id.iv_category));
                        //名字
                        holder.setText(R.id.tv_catagroy_name, childDataBean.getTitle());
                        //价格
                        holder.setText(R.id.tv_category_pice, "¥" + childDataBean.getSeal_price());
                        //隐藏控件
                        holder.getView(R.id.tv_goods_type).setVisibility(View.GONE);
                        ImageView iv = holder.getView(R.id.iv_gwc_icon);
                        iv.setVisibility(View.VISIBLE);
                        iv.setImageResource(R.mipmap.commodity_icon_atc2);
                        //删除
                        TextView tv_del = holder.getView(R.id.tv_del_browhistory);
                        if (!isShow) {
                            tv_del.setVisibility(View.VISIBLE);
                        } else {
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
                        BrowhistoryBean.DataBean.ChildDataBean bean = InnList.get(position);
                        Intent intent = new Intent(BrowhistoryActivity.this, GoodsXqActivity.class);
                        intent.putExtra("mainId", bean.getId());
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
    }*/

    /**
     * 删除浏览
     *
     * @param id
     */
    private void delBrow(String id, final int i1, final int i2) {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("删除浏览历史", Constant.baseUrl + "item/index.php?c=Goods&m=DeleteUserHistory" + "&user_id=" + userId + "&goods_ids=" + id);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=DeleteUserHistory")
                .addParams("user_id", userId)
                .addParams("goods_ids", id)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if (response.getStatus() == 1) {
                                 Refresh();
                        }else {
                            MyToast.makeTextAnim(MyApp.getContext(), response.getMsg(), 0, Gravity.CENTER, 0, 0).show();
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
        Log.i("我的浏览记录", Constant.baseUrl + "item/index.php?c=Goods&m=GoodsHistoryList" + "&user_id="+userId);
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
                      dataBeanList=response.getData();
                      DataHelpers(dataBeanList);

                      if(dataBeanList.size()==0){
                          tv_right.setVisibility(View.INVISIBLE);
                          ll_browhistory_parent.setVisibility(View.GONE);
                          tv_empty.setVisibility(View.VISIBLE);
                      }
                    }
                });
    }

    private void DataHelpers(List<BrowhistoryBean.DataBean> lists) {
         List<Object> temp=new ArrayList<>();
        for (int i = 0; i <lists.size() ; i++) {
            BrowhistoryBean.DataBean xBean=lists.get(i);
            GoodsOrderInfo headerBean=new GoodsOrderInfo();
            headerBean.setTime(xBean.getAdd_time());

            List<BrowhistoryBean.DataBean.ChildDataBean> childBeanList=xBean.getChild_data();
            temp.add(headerBean);
            for (int j = 0; j <childBeanList.size() ; j++) {
                BrowhistoryBean.DataBean.ChildDataBean childBean=childBeanList.get(j);

                OrderGoodsItem itemBean=new OrderGoodsItem();
                itemBean.setProduct_title(childBean.getTitle());
                itemBean.setReal_price(childBean.getSeal_price());
                itemBean.setId(childBean.getId());
                itemBean.setImg_url(childBean.getImg_url());
                itemBean.setI1(i);
                itemBean.setI2(j);
                temp.add(itemBean);
            }
        }
        adapter.add(temp,true);

    }


    @OnClick(R.id.tv_actionbar_back)
    public void bcak() {
        onBackPressed();
    }

    @OnClick(R.id.tv_actionbar_right)
    public void edit() {
       if(!flag){
           tv_right.setText("完成");
           flag = true;
           adapter.isShow(true);
           ll_all_deletee.setVisibility(View.VISIBLE);
       }else {
           tv_right.setText("编缉");
           flag = false;
           adapter.isShow(false);
           ll_all_deletee.setVisibility(View.GONE);
       }
    }

  /*  @OnClick(R.id.tv_all_dele)
    public void all_dele() {
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i <dataBeanList.size() ; i++) {
           List<BrowhistoryBean.DataBean.ChildDataBean> childList =dataBeanList.get(i).getChild_data();
            for (int j = 0; j <childList.size() ; j++) {
                BrowhistoryBean.DataBean.ChildDataBean bean=childList.get(j);
                sb.append(bean.getId()).append(",");
            }
        }
       // delBrow(sb.toString());
    }*/
}
