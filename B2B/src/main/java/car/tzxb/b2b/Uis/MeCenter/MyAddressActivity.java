package car.tzxb.b2b.Uis.MeCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mylibrary.HttpClient.OkHttpUtils;
import com.example.mylibrary.HttpClient.callback.GenericsCallback;
import com.example.mylibrary.HttpClient.utils.JsonGenericsSerializator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.MultiItemTypeAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.Bean.MyAddressBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.Views.DialogFragments.AlterDialogFragment;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class MyAddressActivity extends MyBaseAcitivity {

    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.recy_address)
    RecyclerView recy;
    @BindView(R.id.tv_empth_address)
    TextView tv_empty;
    private List<MyAddressBean.DataBean.AddressBean> beanList = new ArrayList<>();
    private CommonAdapter<MyAddressBean.DataBean.AddressBean> adapter;
    private String from;

    @Override
    public void initParms(Bundle parms) {
        from = getIntent().getStringExtra("from");

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_address;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("收货地址管理");
        initRecy();
    }


    private void initRecy() {
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setItemAnimator(new DefaultItemAnimator());
        adapter = new CommonAdapter<MyAddressBean.DataBean.AddressBean>(MyApp.getContext(), R.layout.my_address_item, beanList) {
            @Override
            protected void convert(ViewHolder holder, final MyAddressBean.DataBean.AddressBean addressBean, final int position) {
                //名字
                holder.setText(R.id.tv_address_name, addressBean.getUser_name());
                //手机号
                holder.setText(R.id.tv_address_mobile, addressBean.getMobile());
                //详细地址
                String pcd = addressBean.getProvince() + addressBean.getCity() + addressBean.getArea();
                holder.setText(R.id.tv_detailed_address, pcd + addressBean.getAddress());
                //是否是默认地址
                CheckBox cb = holder.getView(R.id.cb_default_address);
                if ("1".equals(addressBean.getStatus())) {
                    cb.setChecked(true);
                    cb.setClickable(false);
                } else {
                    cb.setChecked(false);
                    cb.setClickable(true);
                    cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = addressBean.getID();
                            defultAddress(id);
                        }
                    });
                }
                //删除地址
                holder.getView(R.id.tv_del_address).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = addressBean.getID();
                        showDialogFragment(id,position);
                    }
                });
                //修改地址
                holder.getView(R.id.tv_modify_address).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(MyAddressActivity.this,EditAddressActivity.class);
                        i.putExtra("bean",addressBean);
                        startActivity(i);
                    }
                });
            }
        };
        recy.setAdapter(adapter);
        if("order".equals(from)){
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                     MyAddressBean.DataBean.AddressBean bean=beanList.get(position);
                       Intent intent=new Intent();
                       intent.putExtra("bean",bean);
                       setResult(RESULT_OK,intent);
                       finish();

                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }

    }

    private void showDialogFragment(final String id, final int posi) {

        final AlterDialogFragment dialog = new AlterDialogFragment();
        dialog.setCancelable(false);
        Bundle bundle=new Bundle();
        bundle.putString("title","确定要删除该地址吗?");
        bundle.putString("ok","确定");
        bundle.putString("no","取消");
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "del");
        dialog.setOnClick(new AlterDialogFragment.CustAlterDialgoInterface() {
            @Override
            public void cancle() {
                dialog.dismiss();
            }

            @Override
            public void sure() {
                delAddress(id,posi);
                dialog.dismiss();
                if (beanList.size() == 0) {
                    tv_empty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 删除地址
     *
     * @00000param id
     */
    private void delAddress(String id, final int index) {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        OkHttpUtils
                .post()
                .url(Constant.baseUrl + "orders/address.php?m=dell")
                .tag(this)
                .addParams("user_id", userId)
                .addParams("id", id)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        if (1 == response.getStatus()) {
                            adapter.del(index);
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), response.getMsg(), 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }

    /**
     * 设置为默认地址
     *
     * @param id
     */
    private void defultAddress(String id) {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        OkHttpUtils
                .post()
                .tag(this)
                .url(Constant.baseUrl + "orders/address.php?m=stat")
                .addParams("user_id", userId)
                .addParams("id", id)
                .build()
                .execute(new GenericsCallback<BaseStringBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseStringBean response, int id) {
                        Log.i("设置为默认地址", response.getMsg());
                        if (response.getStatus() == 1) {
                            Refresh();
                        } else {
                            MyToast.makeTextAnim(MyApp.getContext(), response.getMsg(), 0, Gravity.CENTER, 0, 0).show();
                        }
                    }
                });
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.btn_add_address)
    public void add() {

        /**
         * id添加时传：-1；修改时传：修改的id
         */
         if(isFastClick()){
             Intent intent = new Intent(this, EditAddressActivity.class);
             intent.putExtra("id", "-1");
             startActivity(intent);
         }

    }

    @OnClick(R.id.tv_actionbar_back)
    public void bcak() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    private void Refresh() {
        String userId = SPUtil.getInstance(MyApp.getContext()).getUserId("UserId", null);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "orders/address.php?m=address_list")
                .addParams("user_id", userId)
                .build()
                .execute(new GenericsCallback<MyAddressBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(MyAddressBean response, int id) {
                        List<MyAddressBean.DataBean.AddressBean> tempList = response.getData().getAddress();

                        adapter.add(tempList, true);
                        if (tempList.size() == 0) {
                            tv_empty.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
