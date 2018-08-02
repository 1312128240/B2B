package car.tzxb.b2b.fragments;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.FlexRadioGroupPackage.FlexRadioGroup;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.EvBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class EvaluateFragment extends MyBaseFragment {
    @BindView(R.id.frg_label)
    FlexRadioGroup rg;
    @BindView(R.id.recy_ev)
    RecyclerView recy;
    @BindView(R.id.tv_hpl)
    TextView tv_hpl;
    private String str[] = {"全部", "有图", "好评", "中评", "差评"};
    private int index;
    private CommonAdapter<EvBean.DataBean.EvaluteBean> adapter;
    private List<EvBean.DataBean.EvaluteBean> beanList = new ArrayList<>();
    private String goodsId;
    private EvBean.DataBean dataBean;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_evaluate;
    }

    @Override
    public void initData() {

        initUi();
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    private void initRg() {
        int w = DeviceUtils.dip2px(MyApp.getContext(), 80);
        int h = DeviceUtils.dip2px(MyApp.getContext(), 25);
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(w, h);
        params.setMargins(15, 15, 0, 0);
        for (int i = 0; i < str.length; i++) {
            RadioButton rb = new RadioButton(getContext());
            rb.setText(str[i]);
            rb.setId(i);
            rb.setGravity(Gravity.CENTER);
            rb.setTextColor(getContext().getResources().getColorStateList(R.color.textview));
            rb.setBackground(getContext().getResources().getDrawable(R.drawable.ev_rb_swich));
            rb.setButtonDrawable(null);
            rb.setLayoutParams(params);
            rg.addView(rb);
        }
        RadioButton rb1 = rg.findViewById(0);
        rb1.setChecked(true);
        rg.setOnCheckedChangeListener(new FlexRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@IdRes int checkedId) {
                index = checkedId;
                Refresh();
            }
        });
    }

    private void initUi() {
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommonAdapter<EvBean.DataBean.EvaluteBean>(MyApp.getContext(), R.layout.custmenr_item, beanList) {
            @Override
            protected void convert(ViewHolder holder, EvBean.DataBean.EvaluteBean evaluteBean, int position) {
                //头像
                ImageView iv = holder.getView(R.id.iv_custmenr_art);
                Glide.with(MyApp.getContext()).load(evaluteBean.getHead_img()).into(iv);
                //手机号
                holder.setText(R.id.tv_custmenr_name, evaluteBean.getUser_name());
                //时间
                holder.setText(R.id.tv_custment_date, evaluteBean.getAdd_time());
                //内容
                holder.setText(R.id.tv_custmenr_content, evaluteBean.getRemark());
                //规格
                TextView tv_gg = holder.getView(R.id.tv_ev_specification);
                String title = evaluteBean.getShop_name();
                //判断是否包含"【 和 】"
                if (title.lastIndexOf("【") != -1 && title.lastIndexOf("】") != -1) {
                    String str1 = title.substring(title.lastIndexOf("【"), title.length());
                    tv_gg.setText("规格: " + str1);
                } else {
                    tv_gg.setText("规格: 【" + title + "】");
                }

                //九宫图
                RecyclerView recy_img = holder.getView(R.id.recy_ev_img);
                recy_img.setLayoutManager(new GridLayoutManager(MyApp.getContext(), 3));
                List<String> imgList = evaluteBean.getImg_urls();
                recy_img.addItemDecoration(new SpaceItemDecoration(8, 3));
                CommonAdapter<String> imgAdapter = new CommonAdapter<String>(MyApp.getContext(), R.layout.iv_item, imgList) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                        ImageView iv = holder.getView(R.id.iv_item);
                        Glide.with(MyApp.getContext()).load(s).override(210, 210).into(iv);
                    }
                };
                recy_img.setAdapter(imgAdapter);
            }
        };
        recy.setAdapter(adapter);
    }

    /**
     * 如果添加fragment是add或show才会调用onHiddenChanged,
     * setUserVisibleHint，这个是每次都会调用的
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Refresh();
            initRg();
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        GoodsXqActivity goodsActivity = (GoodsXqActivity) activity;
        goodsId = goodsActivity.getIntent().getStringExtra("mainId");
    }

    private void Refresh() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("评论的是", Constant.baseUrl + "item/index.php?c=Goods&m=GetGoodsCommentInfo" + "&id=" + goodsId + "&user_id=" + userId + "&succ=" + index);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "item/index.php?c=Goods&m=GetGoodsCommentInfo")
                .tag(this)
                .addParams("id", goodsId)
                .addParams("user_id", userId)
                .addParams("succ", String.valueOf(index))
                .build()
                .execute(new GenericsCallback<EvBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {


                    }

                    @Override
                    public void onResponse(EvBean response, int id) {
                        dataBean = response.getData();
                        beanList = dataBean.getEvalute();
                        adapter.add(beanList, true);
                        //好评率
                        int whole = dataBean.getWhole();
                        if (whole == 0) {
                            tv_hpl.setText(Html.fromHtml("<font color='#FA3314'><big>"+"100%"+"</big></font>" +"<br>"+"好评度"));
                        } else {
                            int hpl = dataBean.getGood() / whole * 100;
                            tv_hpl.setText(Html.fromHtml("<font color='#FA3314'><big>"+hpl +"%"+"</big></font>" +"<br>"+"好评度"));
                        }

                    }
                });
    }


}
