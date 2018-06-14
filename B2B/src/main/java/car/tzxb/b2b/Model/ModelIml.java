package car.tzxb.b2b.Model;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import java.util.Map;
import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class ModelIml implements MvpContact.Model<BaseDataBean>{

    private BaseCallbackListener<BaseDataBean> listener;

    @Override
    public void ModelGetData(String url, Map<String, String> params, BaseCallbackListener<BaseDataBean> callbackListener) {
        this.listener=callbackListener;

        OkHttpUtils
                .get()
                .url(url)
                .params(params)
                .build()
                .execute(new GenericsCallback<BaseDataBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(BaseDataBean response, int id) {
                        listener.onSucceed(response);
                    }
                });
    }
}
