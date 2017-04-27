/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by FacilityONE Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: OAuthFM.java
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

import com.brantf.wireless.br_library.oauth2.config.FMConfig;
import com.brantf.wireless.br_library.oauth2.config.OAuthConfig;
import com.brantf.wireless.br_library.oauth2.token.Token;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * OAuth2.0授权类
 * @author tessi.lu
 * @version V1.0，2015-04-02
 * @see
 * @since Shang
 */
public class OAuthFM {
	private Context mContext;
	private OAuthConfig mConfig;
	private AccessTokenKeeper mKeeper;
	private OauthHttpClient mFMOauthHttpClient;
	private String mServerUrl;

	private String mLoginUrl = "/login/session";
	private HashMap<String, String> mHeaders;
    private static OAuthFM mOAuthFM;
	private UserInfo mUserInfo = new UserInfo();

	private final String LOGIN_URL = "/login/session";

    // 用户类型
	public enum UserType {
        LEADER,  // 领导层
        MANAGER, // 管理层
        LABORER, // 员工
    }

    private OAuthFM() {
    }

    public static OAuthFM getInstance() {
        if (mOAuthFM == null) {
            mOAuthFM = new OAuthFM();
        }

        return mOAuthFM;
    }

    /**
     * 设置参数
     * @param context 关联Context
     * @param appKey 分配的Key
     * @param appSecret 分配的密钥
     * @param serverUrl 服务器地址
     * @param deviceId 设备ID
     */
	public void setParams(Context context, String appKey, String appSecret, String serverUrl, String deviceId) {
		mContext = context;
		mServerUrl = serverUrl;
		mLoginUrl = mServerUrl + LOGIN_URL;
		mHeaders = new HashMap<String, String>();
		mHeaders.put("Device-Type", "android");
		mHeaders.put("Device-Id", deviceId);
		OAuthConfig config = new FMConfig(appKey, appSecret,
				mServerUrl + "/oauth2/auth",
				mServerUrl + "/oauth2/token",
				mServerUrl + "/oauth2/token",
				mServerUrl + "/main.html",
				mServerUrl + "/main/index",
				mServerUrl + "/main/index");
		mConfig = config;
		mKeeper = new AccessTokenKeeper(config.getClass().getSimpleName());
		mFMOauthHttpClient = new OauthHttpClient();
	}

	public OAuthConfig getConfig() {
		return mConfig;
	}

	public Token getToken() {
		return mKeeper.readAccessToken(mContext);
	}

	public void clearToken() {
		mKeeper.clear(mContext);
	}

	/**
	 * 开始授权.
	 * @param userName 用户名
	 * @param userPassword 用户密码
	 * @param listener 授权监听回调
	 */
	public void startOAuth(final String userName, final String userPassword,
                           final OAuthListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
                int fmcode = -1;
                String message = "";
                try {
                    // 登录
                    JSONObject user = new JSONObject();
                    user.put("username", userName);
                    user.put("password", userPassword);
                    String result = mFMOauthHttpClient
                            .login(mLoginUrl, user.toString(), mHeaders);
                    JSONObject jo = new JSONObject(result);

                    fmcode = jo.optInt("code");
                    message = jo.optString("message");
                    if (fmcode == 200) {
                        JSONObject userInfo = jo.optJSONObject("data");
                        mUserInfo.userId = userInfo.optLong("userId");
                        mUserInfo.userType = userInfo.optInt("userType");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

				if (fmcode == 200) {
					try {
						String code = mFMOauthHttpClient.getCode(mConfig
								.getCodeUrl(), mHeaders);
						if (code != null) {
							String result = mFMOauthHttpClient
									.getAccessToken(mConfig
											.getAccessTokenUrl(code), mHeaders);
							Logger.d(result);
							Token token = Token.make(result, mConfig);

							if (token == null) {
								if (listener != null) {
									listener.onError(message);
								}
								return;
							}

							if (token.isSessionValid()) {
								mKeeper.keepAccessToken(mContext, token);
							}
							if (listener != null) {
								listener.onSuccess(token);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						if (listener != null) {
							listener.onError(message);
						}
					}
				} else {
					if (listener != null) {
						listener.onError(message);
					}
				}
			}
		}).start();
	}

	/**
	 * 刷新AccessToken
	 * @param refreshToken 之前获取到的refreshToken
	 * @param listener 监听回调
	 */
	public void refreshToken(final String refreshToken,
			final OAuthListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String result = mFMOauthHttpClient
							.getAccessToken(mConfig
                                    .getRefreshTokenUrl(refreshToken), mHeaders);
					Token token = Token.make(result, mConfig);

					if (token == null) {
						if (listener != null) {
							listener.onError(null);
						}
						return;
					}

					if (token.isSessionValid()) {
						mKeeper.keepAccessToken(mContext, token);
					}
					if (listener != null) {
						listener.onSuccess(token);
					}
				} catch (IOException e) {
					e.printStackTrace();
					if (listener != null) {
						listener.onError("");
					}
				}
			}
		}).start();
	}
	
	public UserInfo getUserInfo() {
		return mUserInfo;
	}

    /**
     * 用户信息类，目前包含用户ID
     */
	public static class UserInfo {
		public long userId;
		public int userType;
	}
}
