package car.tzxb.b2b.Uis.OpenShopPackage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.lljjcoder.citypickerview.widget.CityPicker;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.MainActivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.DeviceUtils;
import car.tzxb.b2b.Util.GlideLoader;
import car.tzxb.b2b.Util.PermissionUtil;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Util.StringUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.Views.PopWindow.ChosePopWindow;
import car.tzxb.b2b.Views.PopWindow.TjrPop;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;


public class OpenShopActivity extends MyBaseAcitivity implements PermissionUtil.OnRequestPermissionsResultCallbacks{

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.et_shop_name)
    EditText et_shop_name;
    @BindView(R.id.et_shop_lxr_name)
    EditText et_shop_lxr_name;
    @BindView(R.id.tv_tjr_infor)
    TextView tv_tjr;
    @BindView(R.id.et_open_shop_address)
    EditText et_shop_address;
    @BindView(R.id.tv_province)
    TextView tv_province;
    @BindView(R.id.open_shop_parent)
    RelativeLayout parent;
    @BindView(R.id.iv_upload1)
    ImageView iv1;
    @BindView(R.id.iv_upload2)
    ImageView iv2;
    @BindView(R.id.iv_upload3)
    ImageView iv3;
    @BindView(R.id.tv_lxr_phone)
    TextView tv_lxr_mobile;
    @BindView(R.id.tv_open_shop_type)
    TextView tv_type;
    @BindView(R.id.et_shop_lxr_tell)
    EditText et_tell;
    private String address;
    private int tag;
    private String path3;
    private String path2;
    private String path1;
    public boolean isUpload1 = false;
    public boolean isUpload2 = false;
    public boolean isUpload3 = false;
    public StringBuilder sb1 = new StringBuilder();
    public StringBuilder sb2 = new StringBuilder();
    public StringBuilder sb3 = new StringBuilder();
    private String mobile;
    private String password=null;
    private int  type;
    private String recommend;
    private String tjrId;
    private String city;
    private final int REQUEST_CODE_LOCATION = 101;
    private final int REQUEST_CODE_CAMERA = 102;
    private String from;

    @Override
    public void initParms(Bundle parms) {
        mobile = getIntent().getStringExtra("mobile");
        password = getIntent().getStringExtra("password");
        from = getIntent().getStringExtra("from");
    }


    @Override
    public int bindLayout() {
        return R.layout.activity_open_shop;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("填写信息");
        tv_lxr_mobile.setText(mobile);
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }


    @OnClick(R.id.tv_province)
    public void province() {
        if (isFastClick()) {
            CityPicker cityPicker = new CityPicker.Builder(this)
                    .textSize(14)
                    .title("地址选择")
                    .titleBackgroundColor("#FFFFFF")
                    .titleTextColor("#696969")
                    .confirTextColor("#696969")
                    .cancelTextColor("#696969")
                    .province("云南省")
                    .city("昆明市")
                    .district("东川区")
                    .textColor(Color.parseColor("#000000"))
                    .provinceCyclic(false)
                    .cityCyclic(false)
                    .districtCyclic(false)
                    .visibleItemsCount(7)
                    .itemPadding(10)
                    .onlyShowProvinceAndCity(false)
                    .build();
            cityPicker.show();
            //监听方法，获取选择结果
            cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                @Override
                public void onSelected(String... citySelected) {
                    //省份
                    String province = citySelected[0];
                    //城市
                    city = citySelected[1];
                    //区县（如果设定了两级联动，那么该项返回空）
                    String district = citySelected[2];
                    //邮编
                  //  String code = citySelected[3];
                    address = province.trim() + "," + city.trim() + "," + district.trim();
                    //为TextView赋值
                    tv_province.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
                }

                @Override
                public void onCancel() {

                }


            });
        }
    }

    //店铺类型
    @OnClick(R.id.tv_open_shop_type)
    public void type() {
        hideSoftInput();
        String[] str = {"一般合作店", "城市中心店", "社区中心店", "线下加盟店"};
        List<String> lists = Arrays.asList(str);
        ChosePopWindow cpw = new ChosePopWindow(MyApp.getContext(), lists);
        cpw.showPow(parent);
        cpw.setClickListener(new ChosePopWindow.ClickListener() {
            @Override
            public void click(String str,int i) {
                type = i;
                tv_type.setText(str);
            }
        });
    }

    //选择照片
    @OnClick({R.id.tv_upload_sign, R.id.tv_upload_shop_photo, R.id.tv_upload_business_license})
    public void upload_sign(final View v) {

      boolean b=  PermissionUtil.hasPermissons(this, Manifest.permission.READ_EXTERNAL_STORAGE);

     if(b==false){
         PermissionUtil.getCameraPermissions(this, REQUEST_CODE_CAMERA);

     }else {
        String[] str = {"相册"};
        List<String> lists = Arrays.asList(str);
        ChosePopWindow cpw = new ChosePopWindow(MyApp.getContext(), lists);
        cpw.showPow(parent);

        tag = Integer.parseInt((String) v.getTag());

        cpw.setClickListener(new ChosePopWindow.ClickListener() {
            @Override
            public void click(String str,int i) {
                if ("相册".equals(str)) {
                    selectPhoto();
                }
            }
        });
     }


    }

    //选择推荐人
    @OnClick(R.id.tv_tjr_infor)
    public void tjr() {
        if(isFastClick()){
            TjrPop tjrPop = new TjrPop(this,parent);
            DeviceUtils.showPopWindow(parent,tjrPop);
            tjrPop.setClickListener(new TjrPop.ClickListener() {
                @Override
                public void click(String name, String Id) {
                    tv_tjr.setText(name);
                    tjrId=Id;
                    recommend=name;
                }
            });
        }
    }

     //标记地图位置
    @OnClick(R.id.tv_open_shop_marker)
    public void marker(){
        boolean b=  PermissionUtil.hasPermissons(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if(b==false) {

            PermissionUtil.getLocationPermissions(this, REQUEST_CODE_LOCATION);

        }else {
            if (city == null) {
                Snackbar.make(tv_title, "请输入省市区", Snackbar.LENGTH_SHORT).show();
                AnimationUtil.Sharke(MyApp.getContext(), tv_province);
                return;
            }
            String address = et_shop_address.getText().toString();
            if (TextUtils.isEmpty(address)) {
                Snackbar.make(tv_title, "请填写详细地址", Snackbar.LENGTH_SHORT).show();
                AnimationUtil.Sharke(MyApp.getContext(), et_shop_address);
                return;
            }

            Intent intent = new Intent(this, OpenShopMapActivity.class);
            intent.putExtra("city", city);
            intent.putExtra("address", address);
            startActivityForResult(intent, 88);
        }

    }


    private void uploadImage(final String path, final int tag2) {
        final String endpoint = "https://oss-cn-shanghai.aliyuncs.com ";
        //生成日期加随机数,再拼接上图片的文件名
        StringBuilder sb_result = new StringBuilder();
        String mobile = SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile", null);
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        String ntr = path.substring(path.lastIndexOf("."), path.length());
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl+"sts-server/sts.php")
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean bean, int id) {
                      OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(bean.getAccessKeyId(), bean.getAccessKeySecret(), bean.getSecurityToken());
                        //该配置类如果不设置，会有默认配置，具体可看该类
                        ClientConfiguration conf = new ClientConfiguration();
                        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
                        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                        OSS oss = new OSSClient(MyApp.getContext(), endpoint, credentialProvider);
                        //生成日期加随机数,再拼接上图片的文件名
                        StringBuilder sb_result = new StringBuilder();
                        String mobile = SPUtil.getInstance(MyApp.getContext()).getMobile("Mobile", null);
                        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                        int random = (int) ((Math.random() * 9 + 1) * 100000);
                        String ntr = path.substring(path.lastIndexOf("."), path.length());

                        switch (tag2) {
                            case 123:
                                sb1.append("b2b_shop/shop_review/identification/").append(mobile).append(ntr);
                              sb_result = sb1;
                            case 124:
                                sb2.append("b2b_shop/shop_review/identification/").append(mobile).append(ntr);
                               sb_result = sb2;
                                break;
                            case 125:
                                sb3.append("b2b_shop/shop_review/chartered/").append(time).append(random).append(ntr);
                                sb_result = sb3;
                                break;
                        }
                         Log.i("上传路径是",sb_result.toString());
                    //构造上传体
                        PutObjectRequest put = new PutObjectRequest("yntz2", sb_result.toString(), path);
                        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                            @Override
                            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                                Log.i("上传成功结果是", String.valueOf(result));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        MyToast.makeTextAnim(MyApp.getContext(), "上传成功", 1, Gravity.BOTTOM, 0, 50).show();
                                        switch (tag2) {
                                            case 123:
                                                isUpload1 = true;
                                                break;
                                            case 124:
                                                isUpload2 = true;
                                                break;
                                            case 125:
                                                isUpload3 = true;
                                                break;
                                        }

                                    }
                                });

                            }

                            @Override
                            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        MyToast.makeTextAnim(MyApp.getContext(), "上传失败", 1, Gravity.BOTTOM, 0, 50).show();
                                    }
                                });

                                Log.i("上传失败结果是", clientExcepion + "-------" + serviceException);

                            }
                        });
                        task.waitUntilFinished();
                    }
                });


    }

    private void selectPhoto() {
        ImageConfig imageConfig = new ImageConfig.Builder(
                new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.titleBlue))
                .titleBgColor(getResources().getColor(R.color.titleBlue))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .requestCode(1001)
                .build();
        ImageSelector.open(this, imageConfig);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1001) {
            if(resultCode == RESULT_OK && data != null) {
                switch (tag) {
                    case 123:
                        path1 = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT).get(0);
                        Glide.with(this).load(path1).centerCrop().into(iv1);
                        uploadImage(path1,tag);
                        break;
                    case 124:
                        path2 = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT).get(0);
                        Glide.with(this).load(path2).centerCrop().into(iv2);
                        uploadImage(path2,tag);
                        break;
                    case 125:
                        path3 = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT).get(0);
                        Glide.with(this).load(path3).centerCrop().into(iv3);
                        uploadImage(path3,tag);
                        break;
                }

            }
        }else if(requestCode==88){
            if(resultCode == RESULT_OK && data != null) {
                 address = data.getStringExtra("ResultAddress");
                 et_shop_address.setText(address);
            }

        }

    }

    @OnClick(R.id.tv_submit_open_shop)
    public void submit() {
        if (isFastClick()) {
            String shop_name = et_shop_name.getText().toString();
            String shop_lxr_name = et_shop_lxr_name.getText().toString();
            String shop_lxr_address = et_shop_address.getText().toString();
            String shop_lxr_tell = et_tell.getText().toString();
           // String tjr=tv_tjr.getText().toString();
            if (TextUtils.isEmpty(shop_name)) {
                MyToast.makeTextAnim(this, "请输入店铺名字", 0, Gravity.CENTER, 0, 0).show();
                return;
            }
            if (TextUtils.isEmpty(shop_lxr_name)) {
                MyToast.makeTextAnim(this, "请填写联系人名字", 0, Gravity.CENTER, 0, 0).show();
                return;
            }
          if(tjrId==null){
                MyToast.makeTextAnim(this, "请填写推荐人信息", 0, Gravity.CENTER, 0, 0).show();
                return;
            }

            if (type==0) {
                MyToast.makeTextAnim(this, "请选择门店主营类型", 0, Gravity.CENTER, 0, 0).show();
                return;
            }
            if (address == null) {
                MyToast.makeTextAnim(this, "请输入门店所在省市区", 0, Gravity.CENTER, 0, 0).show();
                return;
            }

            if (TextUtils.isEmpty(shop_lxr_address)) {
                MyToast.makeTextAnim(this, "请输入门店详细地址", 0, Gravity.CENTER, 0, 0).show();
                return;
            }
            if (isUpload1 == false) {
                MyToast.makeTextAnim(this, "请上传门店招牌照片", 0, Gravity.CENTER, 0, 0).show();
                return;
            }
            if (isUpload2 == false) {
                MyToast.makeTextAnim(this, "请上传门店内部照片", 0, Gravity.CENTER, 0, 0).show();
                return;
            }
            if (isUpload3 == false) {
                MyToast.makeTextAnim(this, "请上传营业执照", 0, Gravity.CENTER, 0, 0).show();
                return;
            }

            //密码加密
            String strA = "password=" + password;
            String strB = strA + "&key=!qJwHh!8Ln6ELn3rbFMk5c$vW#l13QLe";
            String pwdMd5 = StringUtil.stringToMD5(strB);
            StringBuilder Uppass = StringUtil.UpperLowerCase(pwdMd5);

            Log.i("注册返回", Constant.baseUrl + "user_type/register.php?m=register" + "&mobile=" + mobile + "&username=" + shop_lxr_name + "&password=" + password
                    + "&group=4" + "&type=4" + "&selcity=" + address + "&tjr="+tjrId + "&recommend="+recommend+ "&shop_name=" + shop_name + "&shop_address=" + shop_lxr_address
                    + "&shop_img=" + sb1 + "," + sb2 + "&user_zhizhao=" + sb3);

            //开始注册
            OkHttpUtils
                    .post()
                    .tag(this)
                    .url(Constant.baseUrl + "user_type/register.php?m=register")
                    .addParams("mobile", mobile)
                    .addParams("username", shop_lxr_name)
                    .addParams("password", Uppass.toString())
                    .addParams("group", "4")
                    .addParams("shop_type", String.valueOf(type))
                    .addParams("tell", shop_lxr_tell)
                    .addParams("selcity", address)
                    .addParams("tjr", tjrId+"")
                    .addParams("recommend",recommend+"")
                    .addParams("shop_name", shop_name)
                    .addParams("shop_address", shop_lxr_address)
                    .addParams("shop_img", sb1 + "," + sb2)
                    .addParams("user_zhizhao", sb3.toString())
                    .build()
                    .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            tLog("注册的是" + e.toString());
                            Toast.makeText(MyApp.getContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(BaseStringBean response, int id) {
                            if (response.getStatus()==1) {
                                OpenShopEntranceActivity activity1 = OpenShopEntranceActivity.instance1;
                                activity1.finish();

                                if("apply".equals(from)){
                                    OpenShopEntranceActivity activity2 = OpenShopEntranceActivity.instance2;
                                    activity2.finish();
                                }

                                OpenShopEntrance2Activity activity3 = OpenShopEntrance2Activity.instance;
                                activity3.finish();


                               showDialogFragment();


                            } else {
                                Snackbar.make(tv_title, response.getMsg(), Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    private void showDialogFragment() {
        final AlterDialogFragment dialogFragment = new AlterDialogFragment();
        dialogFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString("title", "提交成功!申请结果将会以短信形式通知您,请注意查收");
        bundle.putString("ok","知道了");
        bundle.putString("no","好的");
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(),"Exit");
        dialogFragment.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                dialogFragment.dismiss();
                startActivity(MainActivity.class);
                finish();
            }

            @Override
            public void sure() {
                dialogFragment.dismiss();
                startActivity(MainActivity.class);
                finish();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms, boolean isAllGranted) {

        if(requestCode==101){
            Log.i("权限","同意:" + perms.size() + "个权限,isAllGranted=" + isAllGranted);
        }


    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms, boolean isAllDenied) {
        if (requestCode == 101) {
            if(isAllDenied){
                Log.i("权限", "拒绝:" + perms.size() + "个权限,isAllDenied=" + isAllDenied);
                MyToast.makeTextAnim(MyApp.getContext(),"需要开启定位权限才能使用",1,Gravity.CENTER,0,0).show();
            }
        }else {
            if(isAllDenied){
                Log.i("权限", "拒绝:" + perms.size() + "个权限,isAllDenied=" + isAllDenied);
                MyToast.makeTextAnim(MyApp.getContext(),"需要开启访问相册权限才能使用",1,Gravity.CENTER,0,0).show();
            }
        }
    }
}
