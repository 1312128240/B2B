package car.tzxb.b2b.Presenter;

import android.util.Log;

import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.ShopCarBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.Model.ShoppingCarModelIml;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class ShoppingCarPresenterIml implements MvpContact.Presenter {
    private MvpViewInterface view;
    private final MvpContact.Model shoppingCarModel;

    public ShoppingCarPresenterIml(MvpViewInterface view) {
        this.view = view;
        shoppingCarModel = new ShoppingCarModelIml();
    }


    @Override
    public void onDestroy() {
        view=null;
        System.gc();
    }

    @Override
    public void PresenterGetData(String url, Map<String, String> params) {

        shoppingCarModel.ModelGetData(url, params, new BaseCallbackListener<ShopCarBean>() {
            @Override
            public void onSucceed(ShopCarBean result) {
                view.showData(result);
               // view.closeLoading();
            }

            @Override
            public void onError(Throwable errorMsg) {
                Log.i("mvp回调错误",errorMsg.toString());
                view.closeLoading();
                view.showErro();
            }
        });
    }
}
