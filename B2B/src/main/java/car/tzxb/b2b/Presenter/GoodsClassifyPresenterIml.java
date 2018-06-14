package car.tzxb.b2b.Presenter;

import java.util.Map;


import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.Model.GoodsClassifyModelIml;


/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class GoodsClassifyPresenterIml implements MvpContact.Presenter {

    private MvpViewInterface view;
    private MvpContact.Model modelIml;
    public GoodsClassifyPresenterIml(MvpViewInterface view) {
        this.view = view;
        modelIml=new GoodsClassifyModelIml();
    }


    @Override
    public void onDestroy() {
        view=null;
        System.gc();
    }

    @Override
    public void PresenterGetData(String url, Map<String, String> params) {
       modelIml.ModelGetData(url, params, new BaseCallbackListener<BaseDataListBean>() {
           @Override
           public void onSucceed(BaseDataListBean result) {
               view.showData(result);
               view.closeLoading();
           }

           @Override
           public void onError(Throwable errorMsg) {
              view.closeLoading();
           }
       });
    }
}
