package car.tzxb.b2b.Uis.SeachPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.FlexRadioGroupPackage.FlexRadioGroup;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class SeachActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_seach)
    RecyclerView rech_history;
    @BindView(R.id.rg_hot_seach)
    FlexRadioGroup rg;
    @BindView(R.id.et_seach)
    EditText et_seach;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    @BindView(R.id.recy_content)
    RecyclerView recy_content;
    String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
    private CommonAdapter<BaseDataListBean.DataBean> historyAdapter;
    private List<BaseDataListBean.DataBean> historyBeanList = new ArrayList<>();
    private CommonAdapter<BaseDataListBean.DataBean> contentAdapter;
    private List<BaseDataListBean.DataBean> contentList=new ArrayList<>();

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_seach;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("搜索");
        rech_history.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recy_content.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        initHot();
        initRecy_history();
        getSeachHistory();
        initRecy_content();
        initEvent();
    }

    private void initEvent() {
        et_seach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  if(s.length()>0){
                      ll_content.setVisibility(View.GONE);
                      recy_content.setVisibility(View.VISIBLE);
                      Seach(s.toString());
                  }else {
                      ll_content.setVisibility(View.VISIBLE);
                      recy_content.setVisibility(View.GONE);
                  }
            }
        });
    }


    private void Seach(String s) {

        Log.i("搜索内容",Constant.baseUrl+"item/index.php?c=Goods&m=GoodsList"+"&search="+s);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"item/index.php?c=Goods&m=GoodsList")
                .addParams("search",s)
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        contentList = response.getData();
                        contentAdapter.add(contentList,true);
                    }
                });
    }

    private void initRecy_content() {
       recy_content.setLayoutManager(new LinearLayoutManager(this));

        contentAdapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(),R.layout.tv_item,contentList) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                //标题
                TextView tv=holder.getView(R.id.tv_item);
                tv.setPadding(20,20,20,20);
                tv.setSingleLine(true);
                tv.setEllipsize(TextUtils.TruncateAt.END);
                tv.setText(bean.getGoods_name());
            }
        };
        recy_content.setAdapter(contentAdapter);
        contentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataListBean.DataBean bean=contentList.get(position);
                Intent intent=new Intent(SeachActivity.this, GoodsXqActivity.class);
                intent.putExtra("mainId",bean.getId());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initHot() {
        String[] str = {"磁护", "金嘉护", "极护", "防冻液", "滤清器"};
        int h = DeviceUtils.dip2px(MyApp.getContext(), 25);
        int w = DeviceUtils.dip2px(MyApp.getContext(), 90);
        FlexRadioGroup.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, h);
        params.setMargins(0, 0, 20, 20);
        for (int i = 0; i < str.length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setBackground(getResources().getDrawable(R.drawable.bg3));
            rb.setButtonDrawable(null);
            rb.setMinWidth(w);
            rb.setText(str[i]);
            rb.setLayoutParams(params);
            rb.setGravity(Gravity.CENTER);
            rb.setTextColor(Color.parseColor("#303030"));
            rg.addView(rb);
        }
    }

    private void getSeachHistory() {

        Log.i("搜索历史", Constant.baseUrl + "item/index.php?c=Home&m=UserSearchHistory&user_id=" + userId);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UserSearchHistory")
                .tag(this)
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        historyBeanList = response.getData();
                        historyAdapter.add(historyBeanList, true);
                    }
                });

    }

    private void initRecy_history() {
        rech_history.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.my_gold_sign_item, historyBeanList) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                holder.getView(R.id.ll_gold_sign).setVisibility(View.GONE);
                LinearLayout ll_discounts = holder.getView(R.id.ll_dicounts);
                ll_discounts.setVisibility(View.VISIBLE);
                ll_discounts.setPadding(0, 20, 0, 20);
                //名字
                holder.setText(R.id.tv_discounts_content, bean.getTitle());
                //类型
                TextView tv_type = holder.getView(R.id.tv_discounts_num);
                tv_type.setPadding(3, 0, 3, 0);
                tv_type.setBackgroundDrawable(getDrawable(R.drawable.bg3));
                if ("goods" .equals(bean.getType())) {
                    tv_type.setText("商品");
                } else {
                    tv_type.setText("店铺");
                }
            }
        };
        rech_history.setAdapter(historyAdapter);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.tv_cancle_seach, R.id.tv_actionbar_back})
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.tv_delete_seach_history)
    public void dele() {
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UserSearchHistoryAll&user_id=" + userId)
                .tag(this)
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if (response.getStatus() == 1) {
                            historyBeanList.clear();
                            historyAdapter.add(historyBeanList, true);
                        }
                    }
                });
    }
}
