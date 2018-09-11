package car.tzxb.b2b.Views.DialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import car.tzxb.b2b.R;


public class LoadingDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.TransDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading_dialog, container);
        return view;
    }

    // 使用framgment碎片出现上述异常的原因是在activity 调用onSaveInstanceState后，
// 调用了fragmenttransaction 的commit 方法所致，如果说onBackPressed 的异常是出现在用户按back 后，
// 那么在何处调用commit会导致IllegalStateException异常呢？实际上在api 11 （Honeycomb）之前，
// 如果onSaveInstanceState 方法被调用，那么肯定是在onPause 生命周期方法前，但api11 以后，
// 却只能保证在onStop 生命周期方法前，和onPause 方法并没有明确的先后调用顺序，
// 正是由于此处生命周期的微小变化，导致api11 后，如果在onPause 和 onStop 之间调用commit ，
// 将有可能抛出一个IllegalStateException异常告知状态丢失。
    // 所以可以用commitAllowingStateLoss 代替 commit ，commitAllowingStateLoss 在状态丢失时不会抛出任何异常。只需要在自定义的DialogFragment中重写show方法即可
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
