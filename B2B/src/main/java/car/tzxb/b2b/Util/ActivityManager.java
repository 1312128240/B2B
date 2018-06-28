package car.tzxb.b2b.Util;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class ActivityManager {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ActivityManager instance;
    // 单例模式中获取唯一的MyApplication实例
    public static ActivityManager getInstance() {
        if (null == instance) {
            instance = new ActivityManager();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 移除一个activity
    public void deleteActivity(Activity activity) {
        if (activityList != null && activityList.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityList.remove(activity);
                activity = null;
            }

        }
    }

    // 遍历所有Activity并finish
    public void exit() {

        for (Activity activity : activityList) {
            activity.finish();
        }

    }
}
