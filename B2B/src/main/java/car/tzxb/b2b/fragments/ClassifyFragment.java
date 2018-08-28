package car.tzxb.b2b.fragments;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import car.myrecyclerviewadapter.CommonAdapter;
import car.tzxb.b2b.Adapter.GrildTitleAdpaterPackage.GrideData;
import car.tzxb.b2b.Adapter.GrildTitleAdpaterPackage.GrildTitleAdapter;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.Bean.BaseDataBean;
import car.tzxb.b2b.Bean.BaseStringBean;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.Presenter.ClassifyPresenterIml;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.ClassifyPackage.GoodsClassifyActivity;
import car.tzxb.b2b.Uis.SeachPackage.SeachActivity;
import car.tzxb.b2b.Util.SPUtil;
import car.tzxb.b2b.config.Constant;


public class ClassifyFragment extends MyBaseFragment implements MvpViewInterface {
    @BindView(R.id.recy_classify)
    RecyclerView recy;
    @BindView(R.id.iv_search_bar_left)
    ImageView iv_search_left;
    @BindView(R.id.et_classify)
    EditText et_classify;
    List<GrideData> grideDataList=new ArrayList<>();
    private MvpContact.Presenter presenter = new ClassifyPresenterIml(this);


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initData() {


        String userId= SPUtil.getInstance(MyApp.getContext()).getUserId("UserId",null);
        String url= Constant.baseUrl+"item/index.php?c=Goods&m=Category"+"&user_id="+userId;
        presenter.PresenterGetData(url,null);
        Log.i("分类",url);
    }


    @Override
    protected BasePresenter bindPresenter() {
        return presenter;
    }


    private void initRecy() {

        GridLayoutManager manager = new GridLayoutManager(getContext(),3, LinearLayoutManager.VERTICAL,false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return grideDataList.get(position).spanCount;
            }
        });
        recy.setLayoutManager(manager);
        GrildTitleAdapter adapter = new GrildTitleAdapter(grideDataList);
        recy.setAdapter(adapter);
        adapter.SetClickListener(new GrildTitleAdapter.ClickListener() {
            @Override
            public void click(String id, String from) {
                Intent intent=new Intent(getActivity(), GoodsClassifyActivity.class);
                if("cate".equals(from)){
                    intent.putExtra("cate",id);
                    intent.putExtra("from",from);
                    startActivity(intent);
                }else {
                    intent.putExtra("brand",id);
                    intent.putExtra("from",from);
                    startActivity(intent);
                }

            }
        });
    }

    @OnClick(R.id.et_classify)
    public void seach() {
        startActivity(new Intent(getActivity(), SeachActivity.class));
    }


    @Override
    public void showData(Object o) {
        BaseDataBean bean= (BaseDataBean) o;
        List<BaseDataBean.DataBean.CategoryBean> goodsBeanlist = bean.getData().getCategory();
        List<BaseDataBean.DataBean.BrandBean> brandBeanlist = bean.getData().getBrand();
        //添加
        GrideData g1=new GrideData(0,"商品分类",null,null,null);
        grideDataList.add(g1);
        for (int i = 0; i <goodsBeanlist.size() ; i++) {
            BaseDataBean.DataBean.CategoryBean cateBean=goodsBeanlist.get(i);
            GrideData grideData=new GrideData(1,cateBean.getCategory_name(),cateBean.getImg_url(),cateBean.getId(),"cate");
            grideDataList.add(grideData);
         }
        GrideData g2=new GrideData(0,"品牌分类",null,null,null);
        grideDataList.add(g2);
        for (int i = 0; i <brandBeanlist.size() ; i++) {
            BaseDataBean.DataBean.BrandBean brandBean=brandBeanlist.get(i);
            GrideData grideData=new GrideData(1,brandBean.getTitle(),brandBean.getImg_url(),brandBean.getId(),"brand");
            grideDataList.add(grideData);
        }
        initRecy();

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showErro() {

    }

}
