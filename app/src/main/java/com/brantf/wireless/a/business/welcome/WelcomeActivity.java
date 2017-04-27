/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by brant_feeboo Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: WelcomeActivity.java
* Author: brant.fei
* Version: 1.0
* Create: 2015-04-20
*
* Changes (from 2015-04-01)
* -----------------------------------------------------------------------------------
* Modify:
* -----------------------------------------------------------------------------------
*/

package com.brantf.wireless.a.business.welcome;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.brantf.wireless.a.business.home.MainActivity;
import com.brantf.wireless.a.R;
import com.brantf.wireless.a.business.guide.GuideActivity;
import com.brantf.wireless.a.business.login.LoginActivity;
import com.brantf.wireless.a.common.base.BRFApp;
import com.umeng.analytics.MobclickAgent;


/**
 * 欢迎页面
 * @author brant.fei
 * @version V1.0，2015-04-20
 * @see
 * @since Shang
 */
public class WelcomeActivity extends Activity {
 
  /** 欢迎界面等待的时间. */
  public static final int DELAY_TIME = 2000;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //禁止默认的统计方式
        MobclickAgent.openActivityDurationTrack(false);
        //使用普通测试服务来测试数据
        MobclickAgent.setDebugMode(true);
        // 设置是否对日志信息进行加密, 默认false(不加密)
        MobclickAgent.enableEncrypt(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        work();
        if (BRFApp.isDebug()) {
            Log.d("....", getResolution());
            Log.d("....", getDeviceInfo(this));
        }
    }

    private String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getResolution() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float density = displayMetrics.density; //得到密度
        float width = displayMetrics.widthPixels;//得到宽度
        float height = displayMetrics.heightPixels;//得到高度
        return "density:" + density + "width:" + width + "height:" + height;
    }

    private void work() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (BRFApp.isTest()) {
                    switchToGuide();
                    return;
                }

                /** 如果未打开过引导页 */
                if (!BRFApp.isUseGuide()) {
                    switchToGuide();
                    return;
                }

                /** 如果不自动登录 */
                if (!BRFApp.isAutoLogin()) {
                    switchToLogin();
                    return;
                }

                /** 自动登录，跳转到首页 */
                switchToMain();
            } // end of run()
        }, DELAY_TIME);
    }

    /** 切换到首页 */
    private void switchToMain() {
        MainActivity.startActivity(WelcomeActivity.this);
        WelcomeActivity.this.finish();
    }

    /** 切换到引导页 */
    private void switchToGuide() {
        GuideActivity.startActivity(WelcomeActivity.this);
        WelcomeActivity.this.finish();
    }

    /** 切换到登录页 */
    private void switchToLogin() {
        LoginActivity.startActivity(WelcomeActivity.this);
        WelcomeActivity.this.finish();
    }
}
