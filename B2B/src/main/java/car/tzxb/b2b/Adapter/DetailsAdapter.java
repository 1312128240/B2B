package car.tzxb.b2b.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import car.tzxb.b2b.R;

/**
 * Created by Administrator on 2018/6/19 0019.
 */

public class DetailsAdapter extends MyLvBaseAdapter<String> {

    public DetailsAdapter(Context context, List<String> strings) {
        super(context, strings);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {


        convertView = LayoutInflater.from(getContext()).inflate(R.layout.iv_item, parent, false);

        ImageView iv = convertView.findViewById(R.id.iv_item);


        Glide.with(getContext()).load(getItem(position)).into(iv);


        return convertView;
    }
}
