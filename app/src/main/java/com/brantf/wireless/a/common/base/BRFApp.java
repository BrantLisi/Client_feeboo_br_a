package com.brantf.wireless.a.common.base;

import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.support.multidex.MultiDexApplication;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.brantf.wireless.a.common.config.SystemConfig;
import com.brantf.wireless.br_library.oauth2.OAuthFM;

import java.util.UUID;


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
        mContext = getApplicationContext();

        OAuthFM.getInstance().setParams(this, SystemConfig.APP_KEY, SystemConfig.APP_SECRET,
                SystemConfig.SERVER_URL, getDeviceId());
    }


    /**
     * 获得手机的DeviceId
     *
     * @return
     */
    @SuppressWarnings("finally")
    public static String getDeviceId() {
        String deviceId = "00000000";

        if (TextUtils.isEmpty(deviceId) || "00000000".equals(deviceId)) {
            try {
                // 先获取保存的deviceid
                deviceId = UserPrefs.getPrefs(mContext).getGlobalString(UserPrefs.DEVICEID, "");

                if (TextUtils.isEmpty(deviceId)) {
                    // 先获取androidid
                    deviceId = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
                    // 在主流厂商生产的设备上，有一个很经常的bug，
                    // 就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
                    if (TextUtils.isEmpty(deviceId) || "9774d56d682e549c".equals(deviceId)) {
                        TelephonyManager telephonyManager =
                                (TelephonyManager) mContext
                                        .getSystemService(Context.TELEPHONY_SERVICE);
                        deviceId = telephonyManager.getDeviceId();
                    }
                    if (TextUtils.isEmpty(deviceId)) {
                        deviceId = UUID.randomUUID().toString();
                        deviceId = deviceId.replaceAll("-", "");
                    }
                    UserPrefs.getPrefs(mContext).putGlobalString(UserPrefs.DEVICEID, deviceId);
                }
            } catch (Exception e) {
                deviceId = "00000000";
            } finally {
                return deviceId;
            }
        }

        if (TextUtils.isEmpty(deviceId)) {
            deviceId = "00000000";
        }

        return deviceId;
    }

    public static Context getAppContext(){
        return mContext;
    }
}
