package car.tzxb.b2b.fragments;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import car.tzxb.b2b.Util.SPUtil;


public class MyFragment extends MyBaseFragment implements RadioGroup.OnCheckedChangeListener{


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

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initData() {
       rg_order.setOnCheckedChangeListener(this);
       rg_service.setOnCheckedChangeListener(this);
       rg_collect.setOnCheckedChangeListener(this);
        initUi();
       initRecommend();
    }

    private void initUi() {
        String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        if(userId==null){
            rb_collect_goods.setText("0"+"\n收藏商品");
            rb_collect_shop.setText("0"+"\n收藏店铺");
            rb_browse_record.setText("0"+"\n浏览记录");
        }else {

        }
    }
    @OnClick(R.id.tv_exit_app)
    public void exit(){
        SPUtil.getInstance(MyApp.getContext()).dele("UserId");
        MyToast.makeTextAnim(MyApp.getContext(),"退出成功",1, Gravity.BOTTOM,0,0).show();
    }

    private void initRecommend() {
        String [] str={"哈哈哈哈","呵呵呵","嗯嗯嗯","哦哦哦","好好好"};
        List<BaseStringBean> lists=new ArrayList<>();
        for (int i = 0; i <str.length ; i++) {
            BaseStringBean bean=new BaseStringBean(str[i],null);
            lists.add(bean);
        }

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10,2));
        recyclerView.setNestedScrollingEnabled(false);
      CommonAdapter<BaseStringBean> adapter=new CommonAdapter<BaseStringBean>(MyApp.getContext(),R.layout.recommend_layout,lists) {
          @Override
          protected void convert(ViewHolder holder, BaseStringBean bean, int position) {
              ImageView iv=holder.getView(R.id.iv_recommend);
              Glide.with(MyApp.getContext()).load(R.mipmap.ic_launcher).override(256,256).into(iv);
              holder.setText(R.id.tv_recommend_title,bean.getShop_name());
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
        RadioButton rb=radioGroup.findViewById(i);
        rb.setChecked(false);
        String userId=SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        if(userId==null){
            Intent intent=new Intent(getActivity(), LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            return;
        }

           switch (i){
               case R.id.rb_address:
                   Intent intent1=new Intent(getActivity(), MyAddressActivity.class);
                   startActivity(intent1);
                   break;
               case R.id.rb_collect_goods:
                   Intent intent2=new Intent(getActivity(), CollectActivity.class);
                   intent2.putExtra("index",1);
                   startActivity(intent2);
                   break;
               case R.id.rb_collect_shop:
                   Intent intent3=new Intent(getActivity(), CollectActivity.class);
                   intent3.putExtra("index",2);
                   startActivity(intent3);
                   break;
               case R.id.rb_browse_record:
                   Snackbar.make(rg_order,"浏览记录",Snackbar.LENGTH_LONG).show();
                   break;
           }

    }

}
