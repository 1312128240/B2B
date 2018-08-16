package car.tzxb.b2b.Util.UpdateApp;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import car.myview.CustomToast.MyToast;
import car.tzxb.b2b.MyApp;


/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class DownLoadApk {
    public static final String TAG = DownLoadApk.class.getSimpleName();

    public static void download(Context context, String url, String title, final String appName) {
        // 获取存储ID
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        long downloadId = sp.getLong(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
        if (downloadId != -1L) {
            Log.i("已经下载过了", "aaaa");
            FileDownloadManager fdm = FileDownloadManager.getInstance(context);
            int status = fdm.getDownloadStatus(downloadId);
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                //启动更新界面
                Uri uri = fdm.getDownloadUri(downloadId);
                if (uri != null) {
                    if (compare(getApkInfo(context, uri.getPath()), context)) {
                        Log.i("下载成功，并且是最新的安装包", "直接更新");
                        startInstall(context, uri);
                    } else {
                        Log.i("下载成功,不是最新的安装包", "删除");
                        fdm.getDownloadManager().remove(downloadId);
                        start(context, url, title, appName);
                    }
                }

            } else if (status == DownloadManager.STATUS_FAILED) {
                Log.i(TAG, "apk下载失败");
                start(context, url, title, appName);
            } else if (status == DownloadManager.STATUS_RUNNING) {
                MyToast.makeTextAnim(MyApp.getContext(),"进入通知栏中下载...",0, Gravity.CENTER,0,0).show();
            }
        } else {
            Log.i("第一次下载", "bbbb");
            start(context, url, title, appName);
        }
    }

    private static void start(Context context, String url, String title, String appName) {
        MyToast.makeTextAnim(MyApp.getContext(),"在通知栏中查看",0, Gravity.CENTER,0,0).show();
        long id = FileDownloadManager.getInstance(context).startDownload(url, title, "下载完成后自动安装", appName);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(DownloadManager.EXTRA_DOWNLOAD_ID, id).commit();
        Log.i(TAG, "apk start download " + id);
    }

    public static void startInstall(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }


    /**
     * 获取apk程序信息[packageName,versionName...]
     *
     * @param context Context
     * @param path    apk path
     */
    private static PackageInfo getApkInfo(Context context, String path) {
        PackageInfo info = context.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info;
        }
        return null;
    }


    /**
     * 下载的apk和当前程序版本比较
     *
     * @param apkInfo apk file's packageInfo
     * @param context Context
     * @return 如果当前应用版本小于apk的版本则返回true
     */
    private static boolean compare(PackageInfo apkInfo, Context context) {
        if (apkInfo == null) {
            return false;
        }
        String localPackage = context.getPackageName();
        if (apkInfo.packageName.equals(localPackage)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(localPackage, 0);
                Log.i("安装包信息和已安装比较", apkInfo.versionCode + "_" + packageInfo.versionCode);
                if (apkInfo.versionCode > packageInfo.versionCode) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
