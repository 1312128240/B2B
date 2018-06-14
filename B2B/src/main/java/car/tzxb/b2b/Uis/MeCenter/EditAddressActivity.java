package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.lljjcoder.citypickerview.widget.CityPicker;

import butterknife.BindView;
import butterknife.OnClick;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.AnimationUtil;


public class EditAddressActivity extends MyBaseAcitivity{

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
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void doBusiness(Context mContext) {
                 tv_title.setText("新增地址");
        textWatch();
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
    public void city(){
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
                   String city = citySelected[1];
                    //区县（如果设定了两级联动，那么该项返回空）
                    String district = citySelected[2];
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
    public void bcak(){
        onBackPressed();
    }
    @OnClick(R.id.btn_save_address)
    public void save(){
        String name=et_name.getText().toString();
        String phone=et_phone.getText().toString();
        String location=tv_location.getText().toString();
        String region=et_region.getText().toString();
        if(TextUtils.isEmpty(name)){
            AnimationUtil.Sharke(MyApp.getContext(),et_name);
            Snackbar.make(tv_title,"收货人姓名不能为空",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phone)){
            AnimationUtil.Sharke(MyApp.getContext(),et_phone);
            Snackbar.make(tv_title,"收货人手机号未填写",Snackbar.LENGTH_SHORT).show();
            return;
        }else if(phone.length()<11){
            AnimationUtil.Sharke(MyApp.getContext(),et_phone);
            Snackbar.make(tv_title,"手机号填写不正确",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(location)){
            AnimationUtil.Sharke(MyApp.getContext(),tv_location);
            Snackbar.make(tv_title,"未选择收货人地区",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(region)){
            AnimationUtil.Sharke(MyApp.getContext(),et_region);
            Snackbar.make(tv_title,"请填写收货人详细地址",Snackbar.LENGTH_SHORT).show();
            return;
        }
        MyToast.makeTextAnim(this,"完成",0, Gravity.CENTER,0,0).show();
    }
}
