package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class CollectActivity extends MyBaseAcitivity implements RadioGroup.OnCheckedChangeListener, CheckBox.OnCheckedChangeListener {

    private int index;
    @BindView(R.id.rb_visit)
    RadioButton rb_visit;
    @BindView(R.id.rb_self)
    RadioButton rb_self;
    @BindView(R.id.rg_order_swich)
    RadioGroup rg;
    @BindView(R.id.recy_collect)
    RecyclerView recy;
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    @BindView(R.id.tv_empty_collect)
    TextView tv_empty;
    @BindView(R.id.ll_collect)
    LinearLayout contentView;
    @BindView(R.id.cb_all_collect)
    CheckBox cb_all;
    private boolean flag;
    private boolean flag2 = true;
    private LoadingDialog loadingDialog;
    private CommonAdapter<BaseDataListBean.DataBean> shopAdapter;
    private CommonAdapter<BaseDataListBean.DataBean> goodsAdapte;
    private List<BaseDataListBean.DataBean> beanList1 = new ArrayList<>();
    private List<BaseDataListBean.DataBean> beanList2 = new ArrayList<>();

    @Override
    public void initParms(Bundle parms) {
        index = getIntent().getIntExtra("index", -1);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_collect;
    }

    @Override
    public void doBusiness(Context mContext) {
        rb_visit.setText("收藏商品");
        rb_self.setText("收藏店铺");
        tv_edit.setText("编缉");
        rg.setOnCheckedChangeListener(this);
        cb_all.setOnCheckedChangeListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        switch (index) {
            case 0:
                rb_visit.setChecked(true);
                getData1();
                break;
            case 1:
                rb_self.setChecked(true);
                getData2();
                break;
        }
    }

    private void initRecy1(final List<BaseDataListBean.DataBean> beanList) {
        recy.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapte = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.commn_item, beanList) {
            @Override
            protected void convert(ViewHolder holder, final BaseDataListBean.DataBean dataBean, final int position) {
                //图片
                ImageView iv = holder.getView(R.id.iv_category);
                Glide.with(MyApp.getContext()).load(dataBean.getImg_url()).into(iv);
                //名字
                holder.setText(R.id.tv_catagroy_name, dataBean.getTitle());
                //价格
                TextView tv_price = holder.getView(R.id.tv_seales2);
                tv_price.setTextColor(getResources().getColor(R.color.red1));
                tv_price.setText(Html.fromHtml("¥" + "<big>" + dataBean.getSeal_price() + "</big>"));
                //收藏时间
                TextView tv_time = holder.getView(R.id.tv_category_pice);
                tv_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv_time.setText(Html.fromHtml("<font color='#E1E1E1'>" + "收藏时间" + dataBean.getAdd_time() + "</font>"));
                //购物车图标
                ImageView iv_gwc = holder.getView(R.id.iv_gwc_icon);
                iv_gwc.setVisibility(View.VISIBLE);
                iv_gwc.setImageResource(R.mipmap.commodity_icon_atc2);
                //隐藏控件
                holder.getView(R.id.tv_goods_type).setVisibility(View.GONE);
                final CheckBox cb = holder.getView(R.id.cb_refund);
                cb.setChecked(dataBean.isCheck());
                if (flag2) {
                    cb.setVisibility(View.GONE);
                } else {
                    cb.setVisibility(View.VISIBLE);
                }
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataBean.setCheck(cb.isChecked());
                    }
                });
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){ //此时要判断是否全选中了
                            List<BaseDataListBean.DataBean> isCheckList=new ArrayList<>();
                            for (int i = 0; i <beanList.size() ; i++) {
                                BaseDataListBean.DataBean bean=beanList.get(i);
                                if(bean.isCheck()){
                                    isCheckList.add(bean);
                                }
                            }
                            if(isCheckList.size()+1==beanList.size()){
                                cb_all.setChecked(true);
                            }

                        }else {

                            if(cb_all.isChecked()){
                                cb_all.setChecked(false);
                                flag2=false;
                                flag=true;
                                tv_edit.setText("完成");
                            }
                        }
                    }
                });

            }
        };
        recy.setAdapter(goodsAdapte);
    }

    private void initRecy2(final List<BaseDataListBean.DataBean> beanList) {
        recy.setLayoutManager(new LinearLayoutManager(this));
        shopAdapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.commn_item, beanList) {
            @Override
            protected void convert(ViewHolder holder, final BaseDataListBean.DataBean dataBean, final int position) {
                //图片
                ImageView iv = holder.getView(R.id.iv_category);
                Glide.with(MyApp.getContext()).load(dataBean.getShop_img()).into(iv);
                //店名
                holder.setText(R.id.tv_catagroy_name, dataBean.getShop_name());
                //隐藏控件
                holder.getView(R.id.tv_goods_type).setVisibility(View.GONE);
                final CheckBox cb = holder.getView(R.id.cb_refund);
                cb.setChecked(dataBean.isCheck());
                if (flag2) {
                    cb.setVisibility(View.GONE);
                } else {
                    cb.setVisibility(View.VISIBLE);
                }

                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataBean.setCheck(cb.isChecked());
                    }
                });
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                             if(isChecked){
                                 List<BaseDataListBean.DataBean> isCheckList=new ArrayList<>();
                                 for (int i = 0; i <beanList.size() ; i++) {
                                     BaseDataListBean.DataBean bean=beanList.get(i);
                                     if(bean.isCheck()){
                                         isCheckList.add(bean);
                                     }
                                 }
                                 if(isCheckList.size()+1==beanList.size()){
                                     cb_all.setChecked(true);
                                 }
                             }else {
                                 if(cb_all.isChecked()){
                                     cb_all.setChecked(false);
                                     flag2=false;
                                     flag=true;
                                     tv_edit.setText("完成");
                                 }
                             }
                    }
                });

            }
        };
        recy.setAdapter(shopAdapter);
    }


    /**
     * 收藏的门店
     */
    private void getData2() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("收藏门店", Constant.baseUrl + "item/index.php?c=Home&m=UserShopCollectList" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UserShopCollectList")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeLoad();
                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        closeLoad();
                        beanList2 = response.getData();
                        if (beanList2.size() == 0) {
                            tv_empty.setVisibility(View.VISIBLE);
                            contentView.setVisibility(View.GONE);
                            tv_edit.setVisibility(View.INVISIBLE);
                        } else {
                            tv_empty.setVisibility(View.GONE);
                            contentView.setVisibility(View.VISIBLE);
                            tv_edit.setVisibility(View.VISIBLE);
                            initRecy2(beanList2);
                        }
                    }
                });

    }


    /**
     * 收藏的商品数据
     */
    private void getData1() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("收藏商品", Constant.baseUrl + "item/index.php?c=Home&m=UserGoodsCollectList&user_id=29602" + "&user_id=" + userId);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UserGoodsCollectList&user_id=29602")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeLoad();
                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        closeLoad();
                        beanList1 = response.getData();
                        if (beanList1.size() == 0) {
                            tv_empty.setVisibility(View.VISIBLE);
                            contentView.setVisibility(View.GONE);
                            tv_edit.setVisibility(View.INVISIBLE);
                        } else {
                            tv_empty.setVisibility(View.GONE);
                            contentView.setVisibility(View.VISIBLE);
                            tv_edit.setVisibility(View.VISIBLE);
                            initRecy1(beanList1);
                        }

                    }
                });

    }


    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        showLoad();
        flag = false;
        flag2 = true;
        cb_all.setChecked(false);
        switch (checkedId) {
            case R.id.rb_visit:
                getData1();
                break;
            case R.id.rb_self:
                getData2();
                break;
        }

    }

    /**
     * 显示等待框
     */
    public void showLoad() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(getSupportFragmentManager(), "collect");
    }

    /**
     * 关闭等待框
     */
    public void closeLoad() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    @OnClick(R.id.tv_edit)
    public void edit() {
        if (!flag) {
            tv_edit.setText("完成");
            flag = true;
            flag2 = false;
        } else {
            tv_edit.setText("编缉");
            flag = false;
            flag2 = true;
        }
        cb_all.setChecked(false);
        if (rb_visit.isChecked()) {
            goodsAdapte.notifyDataSetChanged();
        } else {
            shopAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.tv_cancle_collect)
    public void cancle() {
        if (flag2) {
            MyToast.makeTextAnim(MyApp.getContext(), "请点击编缉选择", 0, Gravity.CENTER, 0, 0).show();
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (rb_visit.isChecked()) {
            for (int i = 0; i < beanList1.size(); i++) {
                BaseDataListBean.DataBean bean = beanList1.get(i);
                if (bean.isCheck()) {
                    sb.append(bean.getGoods_id()).append(",");
                }
            }

            if (sb.length() == 0) {
                MyToast.makeTextAnim(MyApp.getContext(), "请选择商品", 0, Gravity.CENTER, 0, 0).show();
                return;
            }

            cancleGoods(sb);
        } else {
            for (int i = 0; i < beanList2.size(); i++) {
                BaseDataListBean.DataBean bean = beanList2.get(i);
                if (bean.isCheck()) {
                    sb.append(bean.getShop_id()).append(",");
                }
            }
            if (sb.length() == 0) {
                MyToast.makeTextAnim(MyApp.getContext(), "请选择店铺", 0, Gravity.CENTER, 0, 0).show();
                return;
            }
            cancleShop(sb);
        }

    }

    /**
     * 店铺收藏取消
     */
    private void cancleShop(StringBuilder sb) {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("取消店铺收藏", Constant.baseUrl + "item/index.php?c=Home&m=UserCollectShopDeleteAll" + "&user_id=" + userId + "&shop_ids=" + sb);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UserCollectShopDeleteAll")
                .addParams("user_id", userId)
                .addParams("shop_ids", sb.toString())
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if (response.getStatus() == 1) {
                            getData2();
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), "取消失败", 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }

    /**
     * 商品收藏取消
     *
     * @param sb
     */
    private void cancleGoods(StringBuilder sb) {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);

        Log.i("店铺收藏", Constant.baseUrl + "item/index.php?c=Home&m=UserCollectGoodsDeleteAll" + "&user_id=" + userId + "&goods_ids=" + sb);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UserCollectGoodsDeleteAll")
                .addParams("user_id", userId)
                .addParams("goods_ids", sb.toString())
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if (response.getStatus() == 1) {
                            getData1();
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), "取消失败", 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            flag=false;
            tv_edit.setText("编缉");
            if (rb_visit.isChecked()) {
                showCheckAllGoods(true);
            } else {
                showCheckAllShop(true);
            }
        } else {
            flag=true;
            tv_edit.setText("完成");
            if (rb_visit.isChecked()) {
                showCheckAllGoods(false);

            } else {
                showCheckAllShop(false);

            }
        }
    }

    /**
     * 商品全选
     * @param b
     */
    public void showCheckAllGoods(boolean b) {
        if (goodsAdapte != null) {
            flag2 = b;
            for (int i = 0; i <beanList1.size() ; i++) {
                BaseDataListBean.DataBean bean=beanList1.get(i);
                bean.setCheck(!b);
            }
            goodsAdapte.notifyDataSetChanged();
        }
    }

    /**
     * 店铺全选
     * @param b
     */
    public void showCheckAllShop(boolean b) {
        if (shopAdapter != null) {
            flag2 = b;
            for (int i = 0; i <beanList2.size() ; i++) {
                BaseDataListBean.DataBean bean=beanList2.get(i);
                bean.setCheck(!b);
            }
            shopAdapter.notifyDataSetChanged();

        }
    }

}
