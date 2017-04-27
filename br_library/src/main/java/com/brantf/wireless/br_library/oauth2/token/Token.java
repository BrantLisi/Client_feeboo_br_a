/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by FacilityONE Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: Token.java
* Author: tessi.lu
* Version: 1.0
* Create: 2015-04-02
*
* Changes (from 2015-04-02)
* -----------------------------------------------------------------------------------
* Modify:
* -----------------------------------------------------------------------------------
*/

package com.brantf.wireless.br_library.oauth2.token;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.brantf.wireless.br_library.oauth2.config.FMConfig;
import com.brantf.wireless.br_library.oauth2.config.OAuthConfig;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用于管理AccessToken的Token类
 * @author tessi.lu
 * @version V1.0，2015-04-02
 * @see
 * @since Shang
 */
public class Token {
	private long mExpiresTime = 0;
	private String mAccessToken = "";
	private String mRefreshToken = "";
	private long mExpiresIn;
	private String mUid = "";

	public Token() {
	}

    /**
     * 从返回的数据解析Token数据
     * 如果是FMToken，则走FMToken解析的过程
     * @param response 服务器返回的响应数据
     * @param config OAuth2.0配置
     * @return 解析好的Token
     */
	public static Token make(String response, OAuthConfig config) {
		Logger.d("token make response: " + response);

		if (config instanceof FMConfig) {
			try {
				return new FMToken(new JSONObject(response));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public void setAccessToken(String mAccessToken) {
		this.mAccessToken = mAccessToken;
	}

	public String getRefreshToken() {
		return mRefreshToken;
	}

	public void setRefreshToken(String mRefreshToken) {
		this.mRefreshToken = mRefreshToken;
	}

	public String getUid() {
		return mUid;
	}

	public void setUid(String mUid) {
		this.mUid = mUid;
	}

	public String getAccessToken() {
		return mAccessToken;
	}

	public void setExpiresIn(long expiresIn) {
		if (expiresIn != 0) {
			setExpiresTime(System.currentTimeMillis() + expiresIn * 1000);
			this.mExpiresIn = expiresIn;
		}
	}

	public void setExpiresTime(long mExpiresTime) {
		this.mExpiresTime = mExpiresTime;
	}

	public long getExpiresTime() {
		return mExpiresTime;
	}

    @SuppressWarnings("unused")
	public long getExpiresIn() {
		return mExpiresIn;
	}

    /**
     * 判断AccessToken是否有效
     * @return 有效true,无效false.
     */
	public boolean isSessionValid() {
		return (!TextUtils.isEmpty(mAccessToken) && (mExpiresTime == 0 || (System
				.currentTimeMillis() < mExpiresTime)));
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public String toString() {
		String date = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
				.format(new java.util.Date(mExpiresTime));
		return "mAccessToken:" + mAccessToken + ";mExpiresTime:" + date
				+ ";mRefreshToken:" + mRefreshToken + ";mUid:" + mUid;
	}
}
