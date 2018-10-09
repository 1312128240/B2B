package car.tzxb.b2b.Views.DialogFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.umeng.commonsdk.debug.W;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import car.tzxb.b2b.R;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

public class NationalDayDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mystyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sign,container);
        view.findViewById(R.id.iv_close_sign).setVisibility(View.GONE);
        view.findViewById(R.id.tv_sign_success).setVisibility(View.GONE);
        view.findViewById(R.id.tv_sign_gold).setVisibility(View.GONE);
        view.findViewById(R.id.btn_check_gold).setVisibility(View.GONE);

        ImageView iv=view.findViewById(R.id.iv_sign_bg);
        iv.setImageResource(R.drawable.fjtz_pop);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
//        super.show(manager, tag);
        try {
            Class c = Class.forName("android.support.v4.app.DialogFragment");
            Constructor con = c.getConstructor();
            Object obj = con.newInstance();
            Field dismissed = c.getDeclaredField(" mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(obj, false);
            Field shownByMe = c.getDeclaredField("mShownByMe");
            shownByMe.setAccessible(true);
            shownByMe.set(obj, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

}
