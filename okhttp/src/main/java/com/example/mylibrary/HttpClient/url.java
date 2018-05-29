package com.example.mylibrary.HttpClient;



import com.example.mylibrary.HttpClient.Bean.BaseStringBean;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class url {
  public static String baseUrl="https://wx.aiucar.com/mobile_api/";
 // public static String baseUrl="https://www.yntzxb.cn/mobile_api/";



  /*  public static void getUrl(){
        OkHttpUtils
                .get()
                .url("http://www.aiucar.cn/mobile_api/url/url.php?m=url")
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        String host=response.getData();

                       //baseUrl = "https://"+host+"/mobile_api/";


                    }
                });
    }*/
}
