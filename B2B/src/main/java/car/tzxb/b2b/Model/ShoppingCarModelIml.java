package car.tzxb.b2b.Model;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.ShopCarBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class ShoppingCarModelIml implements MvpContact.Model<ShopCarBean> {
    private BaseCallbackListener<ShopCarBean> listener;
    @Override
    public void ModelGetData(String url, Map<String, String> params, BaseCallbackListener<ShopCarBean> callbackListener) {
         this.listener=callbackListener;
        OkHttpUtils
                .get()
                .url(url)
                .params(params)
                .build()
                .execute(new GenericsCallback<ShopCarBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(ShopCarBean response, int id) {
                        listener.onSucceed(response);
                    }
                });
    }
}
