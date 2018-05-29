package car.tzxb.b2b.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends MyBaseFragment {


    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick(R.id.tv_login)
    public void login(){
        startActivity(LoginActivity.class);
    }
}
