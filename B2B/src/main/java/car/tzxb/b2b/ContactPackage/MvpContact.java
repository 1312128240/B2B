package car.tzxb.b2b.ContactPackage;

import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.Bean.BaseStringBean;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class MvpContact {
      /*
     * M层，调用服务器接口
     */

    public interface Model<T> {



         void ModelGetData(String url, Map<String,String> params, BaseCallbackListener<T> callbackListener);

    }


    /**
     * P层
     */
    public interface Presenter extends BasePresenter{
        /**
         * View层调用此接口
         * @param url
         * @param params
         */

        void  PresenterGetData(String url, Map<String,String> params);
    }
}
