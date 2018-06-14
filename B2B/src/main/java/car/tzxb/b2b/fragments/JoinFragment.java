package car.tzxb.b2b.fragments;


import android.util.Log;
import android.view.View;
import android.widget.TextView;


import butterknife.BindView;
import car.tzxb.b2b.BasePackage.BasePresenter;

import car.tzxb.b2b.BasePackage.MvpViewInterface;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.ContactPackage.MvpContact;
import car.tzxb.b2b.Presenter.JoinPresenterIml;
import car.tzxb.b2b.R;


public class JoinFragment extends MyBaseFragment implements MvpViewInterface{
      @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    @BindView(R.id.tv_actionbar_back)
            TextView tv_back;
    MvpContact.Presenter presenter=new JoinPresenterIml(this);

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_join;
    }

    @Override
    public void initData() {

        String url="";
        presenter.PresenterGetData(url,null);
        tv_back.setVisibility(View.INVISIBLE);
        tv_title.setText("项目加盟");
    }

    @Override
    protected BasePresenter bindPresenter() {
        return presenter;
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }
}
