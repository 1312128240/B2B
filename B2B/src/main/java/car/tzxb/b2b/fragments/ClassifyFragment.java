package car.tzxb.b2b.fragments;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CircleImageView.CircleImageView;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.Presenter.ClassifyPresenterIml;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.ClassifyPackage.GoodsClassifyActivity;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.config.Constant;

public class ClassifyFragment extends MyBaseFragment implements MvpViewInterface {

    @BindView(R.id.et_classify)
    EditText et_class;
    @BindView(R.id.recy_brand_classify)
    RecyclerView recy_brand;
    @BindView(R.id.recy_gods_classify)
    RecyclerView recy_gods;
    @BindView(R.id.iv_search_bar_left)
    ImageView iv_search_left;
    @BindView(R.id.iv_search_bar_right)
    ImageView iv_search_right;
    private MvpContact.Presenter presenter = new ClassifyPresenterIml(this);
    private List<BaseDataBean.DataBean.CategoryBean> goodsBeanlist=new ArrayList<>();
    private List<BaseDataBean.DataBean.BrandBean> brandBeanlist=new ArrayList<>();
    private CommonAdapter<BaseDataBean.DataBean.CategoryBean> godsAdapter;
    private CommonAdapter<BaseDataBean.DataBean.BrandBean> brandAdapter;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initData() {
        String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        String url= Constant.baseUrl+"item/index.php?c=Goods&m=Category"+"&user_id="+userId;
        presenter.PresenterGetData(url,null);
        Log.i("分类",url);
        initUi();
    }

    private void initUi() {
        iv_search_left.setImageResource(R.drawable.navbar_icon_scan);
        recy_gods.setLayoutManager(new GridLayoutManager(getContext(),3));
        recy_gods.setNestedScrollingEnabled(false);
        final int i= DeviceUtils.dip2px(MyApp.getContext(),45);

        final int top=DeviceUtils.dip2px(MyApp.getContext(),30);
        //商品分类
        godsAdapter = new CommonAdapter<BaseDataBean.DataBean.CategoryBean>(MyApp.getContext(), R.layout.iv_layout,goodsBeanlist) {
            @Override
            protected void convert(ViewHolder holder, BaseDataBean.DataBean.CategoryBean bean, int position) {
                View parent=holder.getView(R.id.iv_layout_parent);
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,top,0,0);
                parent.setLayoutParams(layoutParams);
                CircleImageView iv=holder.getView(R.id.iv_item);
                Glide.with(MyApp.getContext()).load(bean.getImg_url()).override(i,i).into(iv);
                //标题
                holder.setText(R.id.iv_layout_title,bean.getCategory_name());
            }
        };
        recy_gods.setAdapter(godsAdapter);
        godsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataBean.DataBean.CategoryBean bean=goodsBeanlist.get(position);
                Intent intent=new Intent(getActivity(), GoodsClassifyActivity.class);
                intent.putExtra("cate",bean.getId());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        //品牌分类
        recy_brand.setLayoutManager(new GridLayoutManager(getContext(),3));
        recy_brand.setNestedScrollingEnabled(false);
        brandAdapter = new CommonAdapter<BaseDataBean.DataBean.BrandBean>(getContext(), R.layout.iv_layout,brandBeanlist) {
            @Override
            protected void convert(ViewHolder holder, BaseDataBean.DataBean.BrandBean bean, int position) {
                View parent=holder.getView(R.id.iv_layout_parent);
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,top,0,0);
                parent.setLayoutParams(layoutParams);
                CircleImageView iv=holder.getView(R.id.iv_item);
                Glide.with(MyApp.getContext()).load(bean.getImg_url()).override(i,i).into(iv);
                //标题
                holder.setText(R.id.iv_layout_title,bean.getTitle());
            }
        };
        recy_brand.setAdapter(brandAdapter);
        brandAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataBean.DataBean.BrandBean bean=brandBeanlist.get(position);
                Intent intent=new Intent(getActivity(), GoodsClassifyActivity.class);
                intent.putExtra("brand",bean.getId());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    @Override
    protected BasePresenter bindPresenter() {
        return presenter;
    }


    @Override
    public void showData(Object o) {
        BaseDataBean bean= (BaseDataBean) o;
        //商品
        goodsBeanlist = bean.getData().getCategory();
        //品牌
        brandBeanlist = bean.getData().getBrand();
        godsAdapter.add(goodsBeanlist,true);
        brandAdapter.add(brandBeanlist,true);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {
       // http://172.20.10.142/mobile_api//mobile_api/item/index.php?c=Goods&m=ProductList&cate=207&brand=6&pagesize=10&page=0&search=%E5%90%8C%E8%87%B4%E5%93%81%E7%89%8C&price=asc&sales=desc&network_ids=4,5
    }

    @Override
    public void showErro() {

    }
}
