/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by FacilityONE Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: FMConfig.java
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
 * FacilityOne OAuth2.0配置类
 * @author tessi.lu
 * @version V1.0，2015-04-02
 * @see
 * @since Shang
 */
public class FMConfig extends OAuthConfig {

	public FMConfig(String appKey, String appSecret,
                    String codeUrl,
                    String accessTokenUrl,
                    String refreshTokenUrl,
                    String codeRedirectUrl,
                    String accessTokenRedirectUrl,
                    String refreshTokenRedirectUrl) {
		super(appKey, appSecret,
				codeUrl, accessTokenUrl, refreshTokenUrl,
				codeRedirectUrl, accessTokenRedirectUrl, refreshTokenRedirectUrl);
	}

    /**
     * 封装Code Url
     * @return Code url
     */
	@Override
	public String getCodeUrl() {
		return codeUrl + "?client_id=" + appKey
				+ "&redirect_uri="
				+ codeRedirectUrl + "&response_type=code";
	}

    /**
     * 封装获取AccessToken Url
     * @param code 前一步获取到的code
     * @return AccessToken Url
     */
	@Override
	public String getAccessTokenUrl(String code) {
		return accessTokenUrl + "?client_id=" + appKey
				+ "&client_secret=" + appSecret
				+ "&grant_type=authorization_code&redirect_uri="
				+ accessTokenRedirectUrl + "&code=" + code;

	}

    /**
     * 封装获取refreshToken Url
     * @param refreshToken 之前的保存的refreshToken
     * @return refreshToken Url
     */
	@Override
	public String getRefreshTokenUrl(String refreshToken) {
		return refreshTokenUrl + "?client_id=" + appKey
				+ "&client_secret=" + appSecret
				+ "&grant_type=refresh_token&redirect_uri="
				+ accessTokenRedirectUrl + "&refresh_token=" + refreshToken;
	}
}