package car.tzxb.b2b.Uis.GoodsXqPackage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Views.PopWindow.AddShoppingCarPop;
import car.tzxb.b2b.fragments.DetailsFragment;
import car.tzxb.b2b.fragments.EvaluateFragment;
import car.tzxb.b2b.fragments.GoodsFragment;

public class GoodsXqActivity extends MyBaseAcitivity implements RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.xq_tab)
    TabLayout tabLayout;
    @BindView(R.id.xq_vp)
    ViewPager vp;
    @BindView(R.id.iv_xq_sc)
    ImageView iv_sc;
    @BindView(R.id.rg_xq)
    RadioGroup rg;
    @BindView(R.id.goods_xq_parent)
    View parent;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_goods_xq;
    }

    @Override
    public void doBusiness(Context mContext) {
          initToolbar();
        rg.setOnCheckedChangeListener(this);
    }

    private void initToolbar() {
        final List<Fragment> fragments=new ArrayList<>();
        fragments.add(new GoodsFragment());
        fragments.add(new DetailsFragment());
        fragments.add(new EvaluateFragment());
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "商品";
                    case 1:
                        return "详情";
                    case 2:
                        return "评价";
                }
                return "";
            }
        });
        tabLayout.setupWithViewPager(vp);
        vp.setCurrentItem(0);
        vp.setOffscreenPageLimit(2);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    public void collect(boolean b){

           if(!b){
               iv_sc.setImageResource(R.drawable.navbar_icon_nc);
           }else {
               iv_sc.setImageResource(R.drawable.navbar_icon_yc);
           }

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        RadioButton rb=radioGroup.findViewById(i);
        rb.setChecked(false);
        switch (i){
            case R.id.rb_xq_shop:
                MyToast.makeTextAnim(MyApp.getContext(),"卖家",0, Gravity.CENTER,0,0).show();
                break;
            case R.id.rb_xq_sc:
                MyToast.makeTextAnim(MyApp.getContext(),"店铺",0, Gravity.CENTER,0,0).show();
                break;
            case R.id.rb_to_gwc:
                MyToast.makeTextAnim(MyApp.getContext(),"购物车",0, Gravity.CENTER,0,0).show();
                break;
            case R.id.rb_add_shoppingcar:
                AddShoppingCarPop addShoppingCarPop=new AddShoppingCarPop(MyApp.getContext());
                addShoppingCarPop.show(parent);
                break;
        }
    }
}
