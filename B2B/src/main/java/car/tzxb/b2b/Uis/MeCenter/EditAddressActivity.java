package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;
import com.lljjcoder.citypickerview.widget.CityPicker;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.MyAddressBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.AnimationUtil;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;


public class EditAddressActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_consignee_location)
    TextView tv_location;
    @BindView(R.id.et_consignee_name)
    EditText et_name;
    @BindView(R.id.et_consignee_phone)
    EditText et_phone;
    @BindView(R.id.et_consignee_region)
    EditText et_region;
    @BindView(R.id.cb_default_address)
    CheckBox cb;
    private String id;
    private String province;
    private String city;
    private String district;
    private String isDefault;
    private MyAddressBean.DataBean.AddressBean bean;

    @Override
    public void initParms(Bundle parms) {

        bean = (MyAddressBean.DataBean.AddressBean) getIntent().getSerializableExtra("bean");

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("新增地址");
        textWatch();
        if (bean != null) { //是否是修改
            et_name.setText(bean.getUser_name());
            et_phone.setText(bean.getMobile());
            tv_location.setText(bean.getProvince() + "-" + bean.getCity() + "-" + bean.getArea());
            et_region.setText(bean.getAddress());
            if("1".equals(bean.getStatus())){
                cb.setChecked(true);
            }else {
                cb.setChecked(false);
            }
            id=bean.getID();
        }else {

            id="-1";
        }
    }

    private void textWatch() {
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //不能超进11位
                if (s.length() > 15) {
                    String str = et_name.getText().toString();
                    String body = str.substring(0, str.length() - 1);
                    et_name.setText(body);
                    et_name.setSelection(body.length());
                } else if (s.length() == 15) {
                    hideSoftInput();
                }
            }
        });

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //不能超进11位
                if (s.length() > 11) {
                    String str = et_phone.getText().toString();
                    String body = str.substring(0, str.length() - 1);
                    et_phone.setText(body);
                    et_phone.setSelection(body.length());
                } else if (s.length() == 11) {
                    hideSoftInput();
                }
            }
        });
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_consignee_location)
    public void city() {
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
                    province = citySelected[0];
                    //城市
                    city = citySelected[1];
                    //区县（如果设定了两级联动，那么该项返回空）
                    district = citySelected[2];
                    //邮编
                    String code = citySelected[3];
                    String address = province.trim() + "," + city.trim() + "," + district.trim();
                    //为TextView赋值
                    tv_location.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
                }

                @Override
                public void onCancel() {

                }


            });
        }
    }

    @OnClick(R.id.tv_actionbar_back)
    public void bcak() {
        onBackPressed();
    }

    @OnClick(R.id.btn_save_address)
    public void save() {
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String location = tv_location.getText().toString();
        String region = et_region.getText().toString();
        if (TextUtils.isEmpty(name) || name.length() < 2) {
            AnimationUtil.Sharke(MyApp.getContext(), et_name);
            MyToast.makeTextAnim(MyApp.getContext(), "收货人姓名至少2个字符", 0, Gravity.CENTER, 0, 0).show();
            return;
        } else if (name.length() > 15) {
            AnimationUtil.Sharke(MyApp.getContext(), et_name);
            MyToast.makeTextAnim(MyApp.getContext(), "收货人姓名至多15个字符", 0, Gravity.CENTER, 0, 0).show();
            return;
        }

        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            AnimationUtil.Sharke(MyApp.getContext(), et_phone);
            MyToast.makeTextAnim(MyApp.getContext(), "请输入11位手机号码", 0, Gravity.CENTER, 0, 0).show();
            return;
        }

        if (TextUtils.isEmpty(location)) {
            AnimationUtil.Sharke(MyApp.getContext(), tv_location);
            MyToast.makeTextAnim(MyApp.getContext(), "请选择所在地区", 0, Gravity.CENTER, 0, 0).show();
            return;
        }
        if (TextUtils.isEmpty(region) || region.length() < 5) {
            AnimationUtil.Sharke(MyApp.getContext(), et_region);
            MyToast.makeTextAnim(MyApp.getContext(), "详细地址描述信息不得少于5个字符", 0, Gravity.CENTER, 0, 0).show();
            return;
        }

        //http://172.20.10.142/mobile_api/orders/address.php?m=add_ress&id=-1&user_id=446&name=我s&mobile=13075127899&province=广东&city=深圳&area=龙华&street=新区&address=大道

        saveInfor(name, phone, region);
    }

    private void saveInfor(String name, String phone, final String region) {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        if (cb.isChecked()) {
            isDefault = "1";
        } else {
            isDefault = "0";
        }

        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/address.php?m=add_ress")
                .addParams("id", id)
                .addParams("user_id", userId)
                .addParams("name", name)
                .addParams("mobile", phone)
                .addParams("province", province)
                .addParams("city", city)
                .addParams("area", district)
                .addParams("street", "")
                .addParams("address", region)
                .addParams("status", isDefault)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        Log.i("保存地址", response.getMsg() + "");
                        if (response.getStatus() == 1) {
                            onBackPressed();
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), response.getMsg(), 1, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }
}
