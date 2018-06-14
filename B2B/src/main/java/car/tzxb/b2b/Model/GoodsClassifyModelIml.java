package car.tzxb.b2b.Model;

import android.util.Log;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class GoodsClassifyModelIml implements MvpContact.Model<BaseDataListBean> {
    private BaseCallbackListener<BaseDataListBean> listener;

    @Override
    public void ModelGetData(String url, Map<String, String> params, BaseCallbackListener<BaseDataListBean> callbackListener) {
              this.listener=callbackListener;

        OkHttpUtils
                .get()
                .tag(this)
                .url(url)
                .params(params)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        listener.onSucceed(response);
                    }
                });
        StringBuilder sb =new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {

            sb.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        Log.i("筛选路径是", url + sb);
    }

}
