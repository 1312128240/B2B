package car.tzxb.b2b.fragments;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.ImageView;
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
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.SpaceItemDecoration;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.BasePackage.MyBaseFragment2;
import car.tzxb.b2b.Bean.EvBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class EvaluateFragment extends MyBaseFragment2 implements RadioGroup.OnCheckedChangeListener {



    @BindView(R.id.rg1)
    RadioGroup rg1;
    @BindView(R.id.recy_ev)
    RecyclerView recy;
    @BindView(R.id.tv_hpl)
    TextView tv_hpl;
    @BindView(R.id.rb_all_ev)
    RadioButton rb_all;

    private int index;
    private CommonAdapter<EvBean.DataBean.EvaluteBean> adapter;
    private List<EvBean.DataBean.EvaluteBean> beanList=new ArrayList<>();
    private String goodsId;

    @Override
    protected int setContentView() {
        return R.layout.fragment_evaluate;
    }

    @Override
    protected void lazyLoad() {
         rb_all.setChecked(true);
         rg1.setOnCheckedChangeListener(this);
         Refresh();
         initUi();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        GoodsXqActivity goodsActivity = (GoodsXqActivity) activity;
        goodsId = goodsActivity.getIntent().getStringExtra("mainId");
    }

    private void Refresh() {
        String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        Log.i("评论的是",Constant.baseUrl+"item/index.php?c=Goods&m=GetGoodsCommentInfo"+"&id="+goodsId+"&user_id="+userId+"&succ="+index);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl+"item/index.php?c=Goods&m=GetGoodsCommentInfo")
                .tag(this)
                .addParams("id",goodsId)
                .addParams("user_id",userId)
                .addParams("succ", String.valueOf(index))
                .build()
                .execute(new GenericsCallback<EvBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(EvBean response, int id) {
                          beanList=response.getData().getEvalute();
                          adapter.add(beanList,true);
                         //好评率
                        int hpl=response.getData().getGood()/response.getData().getWhole()*100;
                        tv_hpl.setText(hpl+"%"+"\n"+"好评度");
                    }
                });
    }


    private void initUi() {
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommonAdapter<EvBean.DataBean.EvaluteBean>(MyApp.getContext(),R.layout.custmenr_item,beanList) {
            @Override
            protected void convert(ViewHolder holder, EvBean.DataBean.EvaluteBean evaluteBean, int position) {
                //头像
                ImageView iv = holder.getView(R.id.iv_custmenr_art);
                Glide.with(MyApp.getContext()).load(evaluteBean.getHead_img()).into(iv);
                //手机号
                holder.setText(R.id.tv_custmenr_name,evaluteBean.getUser_name());
                //时间
                holder.setText(R.id.tv_custment_date,evaluteBean.getAdd_time());
                //内容
                holder.setText(R.id.tv_custmenr_content,evaluteBean.getRemark());
                //规格
                TextView tv_gg=holder.getView(R.id.tv_ev_specification);
                String title=evaluteBean.getShop_name();
                //判断是否包含"【 和 】"
                if(title.lastIndexOf("【")!=-1&&title.lastIndexOf("】")!=-1){
                    String str1 = title.substring(title.lastIndexOf("【"), title.length());
                    tv_gg.setText("规格: "+str1);
                }else {
                    tv_gg.setText("规格: 【"+title+"】");
                }

                //九宫图
                RecyclerView recy_img=holder.getView(R.id.recy_ev_img);
                recy_img.setLayoutManager(new GridLayoutManager(MyApp.getContext(),3));
                List<String> imgList=evaluteBean.getImg_urls();
                recy_img.addItemDecoration(new SpaceItemDecoration(8,3));
                CommonAdapter<String> imgAdapter=new CommonAdapter<String>(MyApp.getContext(),R.layout.iv_item,imgList) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                        ImageView iv=holder.getView(R.id.iv_item);
                        Glide.with(MyApp.getContext()).load(s).override(210,210).into(iv);
                    }
                };
                recy_img.setAdapter(imgAdapter);
            }
        };
        recy.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

           switch (checkedId){
               case R.id.rb_all_ev:
                   index=0;
                   break;
               case R.id.rb_yt_ev:
                   index=1;
                   break;
               case R.id.rb_hp_ev:
                   index=2;
                   break;
               case R.id.rb_zp_ev:
                   index=3;
                   break;
               case R.id.rb_cp_ev:
                   index=4;
                   break;
           }
           Refresh();
    }
}
