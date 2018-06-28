package car.tzxb.b2b.Model;

import android.util.Log;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class GoodsXqModelIml implements MvpContact.Model<GoodsXqBean> {
    private BaseCallbackListener<GoodsXqBean> listener;

    @Override
    public void ModelGetData(String url, Map<String, String> params, BaseCallbackListener<GoodsXqBean> callbackListener) {
              this.listener=callbackListener;

        OkHttpUtils
                .get()
                .tag(this)
                .url(url)
                .params(params)
                .build()
                .execute(new GenericsCallback<GoodsXqBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(GoodsXqBean response, int id) {
                        listener.onSucceed(response);
                    }
                });
        StringBuilder sb =new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {

            sb.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        Log.i("商品详情路径", url + sb);
    }

}
