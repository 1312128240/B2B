package car.tzxb.b2b.Model;

import android.util.Log;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.HomeBean;
import car.tzxb.b2b.Bean.ShopCarBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class HomeModelIml implements MvpContact.Model<HomeBean> {

    private BaseCallbackListener<HomeBean> listener;
    @Override
    public void ModelGetData(String url, Map<String, String> params, BaseCallbackListener<HomeBean> callbackListener) {
        this.listener=callbackListener;
        OkHttpUtils
                .get()
                .url(url)
                .params(params)
                .build()
                .execute(new GenericsCallback<HomeBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(HomeBean response, int id) {
                        listener.onSucceed(response);
                    }
                });

        Log.i("首页详情路径", url+"");
    }
}
