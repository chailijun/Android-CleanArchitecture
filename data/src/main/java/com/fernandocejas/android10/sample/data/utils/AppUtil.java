package com.fernandocejas.android10.sample.data.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * author： Chailijun
 * date  ： 2017/9/27 23:02
 * e-mail： 1499505466@qq.com
 */

public class AppUtil {

    /**
     * 获取本地软件版本号
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件包名
     *
     * @param ctx
     * @return
     */
    public static String getLocalPackageName(Context ctx) {
        String localPackageName = "";
        try {
            localPackageName = ctx.getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localPackageName;
    }
}
