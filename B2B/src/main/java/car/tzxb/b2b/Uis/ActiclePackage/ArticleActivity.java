package car.tzxb.b2b.Uis.ActiclePackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
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
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.Bean.BaseDataListBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;
import car.tzxb.b2b.config.Constant;
import okhttp3.Call;

public class ArticleActivity extends MyBaseAcitivity {
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.article_tablayout)
    TabLayout tablayout;
    @BindView(R.id.recy_article)
    RecyclerView recy_article;
    private CommonAdapter<BaseDataListBean.DataBean> adapter;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_article;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText("同致头条");
        Refresh();
        initAdapter();
        getAcricle("qb");
    }

    private void initAdapter() {
        recy_article.setLayoutManager(new LinearLayoutManager(this));
        final List<BaseDataListBean.DataBean> lists = new ArrayList<>();
        adapter = new CommonAdapter<BaseDataListBean.DataBean>(MyApp.getContext(), R.layout.commn_item, lists) {
            @Override
            protected void convert(ViewHolder holder, BaseDataListBean.DataBean bean, int position) {
                holder.setText(R.id.tv_catagroy_name, bean.getTitle());
                holder.getView(R.id.tv_goods_type).setVisibility(View.GONE);
                holder.getView(R.id.iv_category).setVisibility(View.GONE);

                TextView tv = holder.getView(R.id.tv_category_pice);
                tv.setTextColor(Color.parseColor("#BDBDBD"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv.setText("同致相伴编缉");
            }
        };
        recy_article.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseDataListBean.DataBean bean = lists.get(position);
                Intent intent = new Intent(ArticleActivity.this, ArticleWebViewActivity.class);
                intent.putExtra("title", bean.getTitle());
                intent.putExtra("content", bean.getContent());
                startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }


    private void Refresh() {
        Log.i("文章", Constant.baseUrl + "messages/information.php?m=titles&api=xw");
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "messages/information.php?m=titles&api=xw")
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        List<BaseDataListBean.DataBean> list = response.getData();
                        initTab(list);
                    }
                });
    }

    private void initTab(final List<BaseDataListBean.DataBean> list) {
        BaseDataListBean.DataBean bean1 = new BaseDataListBean.DataBean();
        bean1.setTitle("全部");
        bean1.setId("qb");
        BaseDataListBean.DataBean bean2 = new BaseDataListBean.DataBean();
        bean2.setTitle("推荐");
        bean2.setId("tj");
        list.add(0,bean1);
        list.add(1,bean2);
        for (int i = 0; i < list.size(); i++) {
            BaseDataListBean.DataBean bean =list.get(i);
            tablayout.addTab(tablayout.newTab().setText(bean.getTitle()));
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String id = list.get(tab.getPosition()).getId();
                getAcricle(id);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void getAcricle(String id) {
        Log.i("点击后的文章", Constant.baseUrl + "messages/information.php?m=infolist" + "&title=" + id);
        OkHttpUtils
                .get()
                .tag(this)
                .url(Constant.baseUrl + "messages/information.php?m=infolist")
                .addParams("title", id)
                .build()
                .execute(new GenericsCallback<BaseDataListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseDataListBean response, int id) {
                        List<BaseDataListBean.DataBean> list = response.getData();
                        adapter.add(list, true);
                    }
                });
    }

    @OnClick(R.id.tv_actionbar_back)
    public void bcak() {
        onBackPressed();
    }
}
