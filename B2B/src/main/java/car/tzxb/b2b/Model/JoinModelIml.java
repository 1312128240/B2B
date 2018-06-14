package car.tzxb.b2b.Model;


import java.util.Map;

import car.tzxb.b2b.BasePackage.BaseCallbackListener;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.ContactPackage.MvpContact;


/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class JoinModelIml implements MvpContact.Model<BaseStringBean> {
    private BaseCallbackListener<BaseStringBean> listener;
    @Override
    public void ModelGetData(String url, Map<String, String> params, BaseCallbackListener<BaseStringBean> callbackListener) {
        this.listener=callbackListener;

    }

}
