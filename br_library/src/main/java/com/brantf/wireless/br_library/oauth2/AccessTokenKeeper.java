/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by FacilityONE Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: AccessTokenKeeper.java
* Author: tessi.lu
* Version: 1.0
* Create: 2015-04-02
*
* Changes (from 2015-04-02)
* -----------------------------------------------------------------------------------
* Modify:
* -----------------------------------------------------------------------------------
*/

package com.brantf.wireless.br_library.oauth2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.brantf.wireless.br_library.oauth2.token.Token;
import com.brantf.wireless.br_library.oauth2.utils.DesEncrypt;
import com.brantf.wireless.br_library.oauth2.utils.Utils;

/**
 * AccessToken保存类
 * 将AccessToken保存到本地SharedPreferences中
 * @author tessi.lu
 * @version V1.0，2015-04-02
 * @see
 * @since Shang
 */
class AccessTokenKeeper {
	private static String OAUTH_PREFS = "oauth";
	private String OAUTH_UID = "uid";
	private String OAUTH_TOKEN = "access_token";
	private String OAUTH_REFRESH_TOKEN = "refresh_token";
	private String OAUTH_EXPIRES_TIME = "expires_time";

	public AccessTokenKeeper(String name) {
		OAUTH_UID += ("-" + name);
		OAUTH_TOKEN += ("-" + name);
		OAUTH_REFRESH_TOKEN += ("-" + name);
		OAUTH_EXPIRES_TIME += ("-" + name);
	}

    /**
     * 保存AccessToken
     * 保存时进行DES加密
     * @param context 关联的Context
     * @param token 要保存的Token
     */
	public void keepAccessToken(Context context, Token token) {
		SharedPreferences pref = context.getSharedPreferences(OAUTH_PREFS,
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();

		DesEncrypt des = new DesEncrypt(Utils.getDeviceId(context));

		editor.putString(OAUTH_TOKEN, des.getEncString(token.getAccessToken()));
		editor.putLong(OAUTH_EXPIRES_TIME, token.getExpiresTime());
		editor.putString(OAUTH_REFRESH_TOKEN,
				des.getEncString(token.getRefreshToken()));
		editor.putString(OAUTH_UID, des.getEncString(token.getUid()));

		editor.commit();
	}

    /**
     * 读取AccessToken
     * @param context 联的Context
     * @return 读取到的AccessToken
     */
	public Token readAccessToken(Context context) {
		Token token = new Token();
		DesEncrypt des = new DesEncrypt(Utils.getDeviceId(context));
		SharedPreferences pref = context.getSharedPreferences(OAUTH_PREFS,
				Context.MODE_PRIVATE);

		token.setAccessToken(des.getDesString(pref.getString(OAUTH_TOKEN, "")));
		token.setExpiresTime(pref.getLong(OAUTH_EXPIRES_TIME, 0));
		token.setRefreshToken(des.getDesString(pref.getString(
				OAUTH_REFRESH_TOKEN, "")));
		token.setUid(des.getDesString(pref.getString(OAUTH_UID, "")));

		if (TextUtils.isEmpty(token.getAccessToken())) {
			return null;
		}

		return token;
	}

    /**
     * 清除保存的AccessToken
     * @param context
     */
    public void clear(Context context) {
        SharedPreferences pref = context.getSharedPreferences(OAUTH_PREFS,
                Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    @SuppressWarnings("unused")
	public String getUid(Context context) {
		String deviceId = Utils.getDeviceId(context);
		DesEncrypt des = new DesEncrypt(deviceId);
		SharedPreferences prefs = context.getSharedPreferences(OAUTH_PREFS,
				Context.MODE_PRIVATE);
		String uid = des.getDesString(prefs.getString(OAUTH_UID, ""));
		return uid;
	}
}
