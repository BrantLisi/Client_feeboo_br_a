/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by FacilityONE Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: OAuthConfig.java
* Author: tessi.lu
* Version: 1.0
* Create: 2015-04-02
*
* Changes (from 2015-04-02)
* -----------------------------------------------------------------------------------
* Modify:
* -----------------------------------------------------------------------------------
*/

package com.brantf.wireless.br_library.oauth2.config;

/**
 * OAuth2.0配置类
 * @author tessi.lu
 * @version V1.0，2015-04-02
 * @see
 * @since Shang
 */
public abstract class OAuthConfig {
	protected static final String TAG = OAuthConfig.class.getSimpleName();

	public String appKey = "";
	public String appSecret = "";
	public String codeUrl = "";
	public String accessTokenUrl = "";
	public String refreshTokenUrl = "";
	public String codeRedirectUrl = "";
	public String accessTokenRedirectUrl = "";
	public String refreshTokenRedirectUrl = "";

    /**
     * OAuth2.0配置构造函数
     * @param appKey 后台分配的key
     * @param appSecret 后台分配的密钥
     * @param codeUrl 获取Code Url
     * @param accessTokenUrl 获取AccessToken Url
     * @param refreshTokenUrl 刷新AccessToken Url
     * @param codeRedirectUrl 获取Code重定向 Url
     * @param accessTokenRedirectUrl 获取AccessToken重定向Url
     * @param refreshTokenRedirectUrl 刷新AccessToken重定向Url
     */
	public OAuthConfig(String appKey, String appSecret,
                       String codeUrl,
                       String accessTokenUrl,
                       String refreshTokenUrl,
                       String codeRedirectUrl,
                       String accessTokenRedirectUrl,
                       String refreshTokenRedirectUrl) {
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.codeUrl = codeUrl;
		this.accessTokenUrl = accessTokenUrl;
		this.refreshTokenUrl = refreshTokenUrl;
		this.codeRedirectUrl = codeRedirectUrl;
		this.accessTokenRedirectUrl = accessTokenRedirectUrl;
		this.refreshTokenRedirectUrl = refreshTokenRedirectUrl;
	}
	
	public abstract String getCodeUrl();

	public abstract String getAccessTokenUrl(String code);

	public abstract String getRefreshTokenUrl(String refreshToken);
}
