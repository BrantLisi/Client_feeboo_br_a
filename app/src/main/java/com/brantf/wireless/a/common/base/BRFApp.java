package com.brantf.wireless.a.common.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;


/**
 * Created by brant.fei on 2016/11/25.
 */

public class BRFApp extends MultiDexApplication {

    private static Context mContext;

    // TODO 上线时，需要关闭
    public static boolean isDebug() {
        return true;
    }

    public static boolean isTest() {
        return false;
    }

    public static boolean isUseGuide(){
        return false;
    }

    public static boolean isAutoLogin(){
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        mContext = getAppContext();
    }

    public static Context getAppContext(){
        return mContext;
    }
}
