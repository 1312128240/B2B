package car.tzxb.b2b.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/6/25 0025.
 */

public class XqPagerAdapter extends FragmentPagerAdapter {
     private List<Fragment> fragmentList;

    public XqPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.fragmentList=list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
