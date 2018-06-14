package car.tzxb.b2b.fragments;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.GoodsXqPackage.GoodsXqActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends MyBaseFragment {


    @BindView(R.id.xq_banner)
    Banner banner;
    @BindView(R.id.tv_xq_goods_shop_collect)
    TextView tv_collect;
    private boolean collect;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_goods;
    }

    @Override
    public void initData() {
        initHeader();
    }

    private void initHeader() {
        String[] images = new String[]{
                "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg",
                "http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg",
                "http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg",
                "http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg",
                "http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg",
                "http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg"};

        banner.setBannerStyle(Banner.NUM_INDICATOR);

        banner.setIndicatorGravity(Banner.RIGHT);

        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true);

        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(3000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        banner.setImages(images);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_xq_goods_shop_collect)
    public void collect() {
        GoodsXqActivity activity = (GoodsXqActivity) getActivity();

        if (activity.isFastClick()) {
            if (!collect) {
                activity.collect(true);
                tv_collect.setBackground(getResources().getDrawable(R.drawable.bg3));
                tv_collect.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                tv_collect.setText("已收藏");
                collect=true;
            } else  {
                activity.collect(false);
                tv_collect.setBackground(getResources().getDrawable(R.drawable.bg1));
                Drawable drawable=getResources().getDrawable(R.drawable.list_icon_collection);
                tv_collect.setText("收藏");
                tv_collect.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
                collect=false;
            }

        }else {
            MyToast.makeTextAnim(MyApp.getContext(),"您手速太快啦,休息会吧....",0, Gravity.CENTER,0,0).show();
        }
    }
}
