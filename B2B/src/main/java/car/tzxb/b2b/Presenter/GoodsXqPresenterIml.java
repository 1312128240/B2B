package car.tzxb.b2b.Presenter;

import android.util.Log;
import android.view.View;

import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.Bean.GoodsXqBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.Model.GoodsClassifyModelIml;
import car.tzxb.b2b.Model.GoodsXqModelIml;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public class GoodsXqPresenterIml implements MvpContact.Presenter {
    private MvpViewInterface view;
    private GoodsXqModelIml modelIml;

    public GoodsXqPresenterIml(MvpViewInterface viewInterface) {
        this.view = viewInterface;
        modelIml=new GoodsXqModelIml();
    }

    @Override
    public void onDestroy() {
        view = null;
        System.gc();
    }

    @Override
    public void PresenterGetData(String url, Map<String, String> params) {
             modelIml.ModelGetData(url, params, new BaseCallbackListener<GoodsXqBean>() {
                 @Override
                 public void onSucceed(GoodsXqBean result) {
                      view.showData(result);
                 }

                 @Override
                 public void onError(Throwable errorMsg) {
                     Log.i("商品详情错误",errorMsg.toString());
                 }
             });
    }
}
