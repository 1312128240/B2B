package car.tzxb.b2b.Presenter;

import android.util.Log;

import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.HomeBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.Model.HomeModelIml;
import car.tzxb.b2b.Model.ModelIml;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class HomePresenterIml implements MvpContact.Presenter {
    private MvpViewInterface view;
    private MvpContact.Model homeModel;

    public HomePresenterIml(MvpViewInterface View) {
        this.view = View;
        homeModel = new HomeModelIml();

    }


    /**
     * 当页面销毁的时候,需要把View=null,
     * 然后调用 System.gc();//尽管不会马上回收，只是通知jvm可以回收了，等jvm高兴就会回收
     */
    @Override
    public void onDestroy() {
        view = null;
        System.gc();
    }


    /**
     *数据回调处理
     */

    @Override
    public void PresenterGetData(String url, Map<String, String> params) {

        homeModel.ModelGetData(url, params, new BaseCallbackListener<HomeBean>() {
            @Override
            public void onSucceed(HomeBean result) {
                view.showData(result);

            }

            @Override
            public void onError(Throwable errorMsg) {
                Log.i("首页mvp回调错误",errorMsg.toString());

            }
        });
    }


}
