package car.tzxb.b2b.Uis.MeCenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import com.jaiky.imagespickers.utils.FileUtils;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CircleImageView.CircleImageView;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.MyCenterBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.GlideLoader;
import car.tzxb.b2b.Util.PermissionUtil;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.LoadingDialog;
import car.tzxb.b2b.Views.PopWindow.ChosePopWindow;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class PersonalDataActivity extends MyBaseAcitivity implements PermissionUtil.OnRequestPermissionsResultCallbacks {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.civ_infor)
    CircleImageView civ;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_province)
    TextView tv_address;
    @BindView(R.id.et_open_shop_address)
    EditText et_xx_address;
    @BindView(R.id.personal_data_parent)
    View parent;
    private String p, c, a,head_Img;
    private File file;
    private LoadingDialog loadingDialog;


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_personal_data;
    }

    @Override
    public void doBusiness(Context mContext) {
        //android 7.0后调用相机方式发生改变，有2种方式 ，此方法相对简单
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        tv_title.setText("个人资料");
        showLoad();
        Refresh();
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }

    /**
     * 选择头像
     */
    @OnClick(R.id.civ_infor)
    public void headImg() {
        String[] str = {"拍照", "从手机相册选择"};
        List<String> stringList = Arrays.asList(str);
        show(stringList);
    }

    /**
     * 选择性别
     *
     * @param
     */
    @OnClick(R.id.tv_sex)
    public void sex() {
        String[] str = {"男", "女"};
        List<String> stringList = Arrays.asList(str);
        show(stringList);
    }

    /**
     * 昵称
     *
     * @param
     */
    @OnClick(R.id.tv_nick)
    public void nick() {
        startActivityForResult(NickNameActivity.class,null,99);
    }

    /**
     * 选择省市区
     *
     * @param
     */
    @OnClick(R.id.tv_province)
    public void address() {
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
                    p = citySelected[0];
                    //城市
                    c = citySelected[1];
                    //区县（如果设定了两级联动，那么该项返回空）
                    a = citySelected[2];
                    //邮编
                    // String code = citySelected[3];
                   /* address = province.trim() + "," + city.trim() + "," + district.trim();
                    //为TextView赋值
                    tv_province.setText(province.trim() + "-" + city.trim() + "-" + district.trim());*/
                    tv_address.setText(p + "-" + c + "-" + a);
                    showLoad();
                    save();
                }

                @Override
                public void onCancel() {

                }


            });
        }
    }

    public void show(List<String> lis) {
        ChosePopWindow cp = new ChosePopWindow(MyApp.getContext(), lis);
        cp.showPow(parent);
        cp.setClickListener(new ChosePopWindow.ClickListener() {
            @Override
            public void click(String str, int position) {
                if ("男".equals(str) || "女".equals(str)) {
                    tv_sex.setText(str);
                    showLoad();
                    save();
                } else {
                    //判断权限
                    CheckPermission(str);
                }

            }
        });
    }

    private void CheckPermission(String str) {
        boolean b = PermissionUtil.hasPermissons(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (!b) {
            PermissionUtil.getCameraPermissions(this, 101);
        } else {
            if ("拍照".equals(str)) {
                 openCanmer();
            } else {
                openPhotos();
            }
        }
    }

    /**
     * 打开相机
     */
    private void openCanmer() {
        // 璺宠浆鍒扮郴缁熺収鐩告満
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            ImageConfig imageConfig = new ImageConfig.Builder(new GlideLoader()).requestCode(100).filePath("/temp").build();
            file = FileUtils.createTmpFile(this, imageConfig.getFilePath());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(cameraIntent, 100);
        }

    }

    /**
     * 打开相册
     */
    private void openPhotos() {
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

    /**
     * 上传到oss
     * @param file
     */
    private void upOss(final File file) {
        final String endpoint = "https://oss-cn-shanghai.aliyuncs.com ";
        OkHttpUtils.get()
                .url("https://www.yntzxb.cn/mobile_api/sts-server/sts.php")
                .tag(this)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(response.getAccessKeyId(), response.getAccessKeySecret(), response.getSecurityToken());
                        //该配置类如果不设置，会有默认配置，具体可看该类
                        ClientConfiguration conf = new ClientConfiguration();
                        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
                        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
                        StringBuilder sb = new StringBuilder();
                        //生成日期加随机数,再拼接上图片的文件名
                        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                        int random = (int) ((Math.random() * 9 + 1) * 100000);
                        String fileStr = String.valueOf(file);
                        String ntr = fileStr.substring(fileStr.lastIndexOf("."), fileStr.length());
                        sb.append("head_img/").append(time).append(random).append(ntr);
                        head_Img=sb.toString();
                        //构造上传体
                        PutObjectRequest put = new PutObjectRequest("yntz2", "head_img/" + time + random + ntr, String.valueOf(file));
                        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                            @Override
                            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                                Log.i("上传成功结果是", String.valueOf(result));

                            }

                            @Override
                            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                                Log.i("上传失败结果是", clientExcepion + "-------" + serviceException);

                            }
                        });
                        task.waitUntilFinished();
                        //保存到公司服务器
                        save();
                    }
                });

    }

    private void save() {
     /*   String url = Constant.baseUrl + "item/index.php?c=Home&m=UpdateUsersInfo" +
                "&user_id=1&nackname=emmmm&head_img=head_img/201819/141321553.jpg&sex=女" +
                "&province=广东&city=深圳&area=观澜&address=观澜湖国际大厦13楼BC" +
                "&coordinate_Longitude=101.2742796241143&coordinate_Latitude=25.202099267118424";
        final LoadingDialog dialog = new LoadingDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "aaa");*/
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        String sex = tv_sex.getText().toString();
        String xxAddress = et_xx_address.getText().toString();
        OkHttpUtils
                .post()
                .tag(this)
                .url(Constant.baseUrl + "item/index.php?c=Home&m=UpdateUsersInfo")
                .addParams("user_id", userId)
                .addParams("head_img",head_Img+"")
                .addParams("sex", sex)
                .addParams("province", p)
                .addParams("city", c)
                .addParams("area", a)
                .addParams("address", xxAddress+"")
                .addParams("coordinate_Longitude", "")
                .addParams("coordinate_Latitude", "")
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadingDialog.dismiss();
                        Log.i("保存信息失败", e.toString());
                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                       loadingDialog.dismiss();
                    }
                });
    }

    private void Refresh() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        Log.i("我的个人粢料", Constant.baseUrl + "item/index.php?c=Home&m=MyCenter&user_id=" + userId);
        OkHttpUtils
                .get()
                .url(Constant.baseUrl + "item/index.php?c=Home&m=MyCenter")
                .tag(this)
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<MyCenterBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(MyCenterBean response, int id) {
                        loadingDialog.dismiss();
                        MyCenterBean.DataBean.UserInfoBean userBean = response.getData().getUserInfo();
                        initUi(userBean);
                    }
                });
    }

    /**
     * 初始化控件
     *
     * @param userBean
     */
    private void initUi(MyCenterBean.DataBean.UserInfoBean userBean) {

        //头像
      String  headImg = userBean.getHead_img();
        if (headImg != null) {
            Glide.with(MyApp.getContext()).load(headImg).asBitmap().placeholder(R.mipmap.my_icon_dhi).error(R.mipmap.my_icon_dhi).into(civ);
        }
        //昵称
        String nick = userBean.getNackname();
        if (!"".equals(nick)) {
            tv_nick.setText(nick);
        } else {
            tv_nick.setHint("未设置");
        }
        //性别
        String sex = userBean.getSex();
        if (!"".equals(sex)) {
            tv_sex.setText(sex);
        } else {
            tv_sex.setHint("未设置");
        }
        //地址
        p = userBean.getProvince();
        c = userBean.getCity();
        a = userBean.getArea();
        tv_address.setText(p + "-" + c + "-" + a);
        et_xx_address.setText(userBean.getShop_address());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK && data != null) {
                String imgUrl= data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT).get(0);
                Glide.with(MyApp.getContext()).load(imgUrl).asBitmap().error(R.mipmap.my_icon_dhi).into(civ);
                file = new File(imgUrl);
                showLoad();
                upOss(file);
            }
        }else if(requestCode==100){
            if(resultCode==RESULT_OK){
                if(file!=null){
                    Glide.with(MyApp.getContext()).load(file).asBitmap().error(R.mipmap.my_icon_dhi).into(civ);
                    showLoad();
                    upOss(file);
                }
            } else {
                if (file != null && file.exists()) {
                    file.delete();
                }
            }
        }else if(requestCode==99){
            if(resultCode==RESULT_OK&&data!=null){
                String nick=data.getStringExtra("nick");
                tv_nick.setText(nick);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms, boolean isAllGranted) {
        if (requestCode == 101) {
            Log.i("权限", "同意:" + perms.size() + "个权限,isAllGranted=" + isAllGranted);
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms, boolean isAllDenied) {
        if (requestCode == 101) {
            if (isAllDenied) {
                Log.i("权限", "拒绝:" + perms.size() + "个权限,isAllDenied=" + isAllDenied);
                MyToast.makeTextAnim(MyApp.getContext(), "需要开启权限才能使用", 1, Gravity.CENTER, 0, 0).show();
            }
        } else {
            if (isAllDenied) {
                Log.i("权限", "拒绝:" + perms.size() + "个权限,isAllDenied=" + isAllDenied);
                MyToast.makeTextAnim(MyApp.getContext(), "需要开启权限才能使用", 1, Gravity.CENTER, 0, 0).show();
            }
        }
    }

    public void showLoad(){
        loadingDialog = new LoadingDialog();
        loadingDialog.setCancelable(false);
        loadingDialog.show(getSupportFragmentManager(),"set");
    }
}
