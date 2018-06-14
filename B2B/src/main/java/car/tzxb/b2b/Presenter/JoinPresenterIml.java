package car.tzxb.b2b.Presenter;

import android.util.Log;

import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.Model.JoinModelIml;
import car.tzxb.b2b.Model.ModelIml;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class JoinPresenterIml implements MvpContact.Presenter {
    private MvpViewInterface view;
    private MvpContact.Model JoinModel;

    public JoinPresenterIml(MvpViewInterface View) {
        this.view = View;
        JoinModel = new JoinModelIml();

    }


    /**
     * 当页面销毁的时候,需要把View=null,
     * 然后调用 System.gc();//尽管不会马上回收，只是通知jvm可以回收了，等jvm高兴就会回收
     */
    @Override
    public void onDestroy() {
        Log.i("Mvp", "onStart(LoginPresenterIml.java:82):View已经被销毁了");
        view = null;
        System.gc();
    }


    /**
     *数据回调处理
     */

    @Override
    public void PresenterGetData(String url, Map<String, String> params) {
        view.showLoading();
        JoinModel.ModelGetData(url, params, new BaseCallbackListener<BaseStringBean>() {
            @Override
            public void onSucceed(BaseStringBean result) {
                view.showData(result);
                view.closeLoading();

            }

            @Override
            public void onError(Throwable errorMsg) {
                Log.i("mvp回调错误",errorMsg.toString());
                view.closeLoading();
            }
        });
    }


}
