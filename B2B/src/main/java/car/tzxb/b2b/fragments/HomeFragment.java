package car.tzxb.b2b.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseFragment;
import car.tzxb.b2b.R;
import car.tzxb.b2b.Uis.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends MyBaseFragment {


    @Override
    public int getLayoutResId() {
        return  R.layout.fragment_home;
    }

    @Override
    public void initData() {
        Log.i("第1个fragment","aaa");
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }
}
