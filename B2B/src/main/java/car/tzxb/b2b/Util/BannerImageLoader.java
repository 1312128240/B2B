package car.tzxb.b2b.Util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import car.tzxb.b2b.MyApp;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(MyApp.getContext()).load(path).into(imageView);
           }
}
