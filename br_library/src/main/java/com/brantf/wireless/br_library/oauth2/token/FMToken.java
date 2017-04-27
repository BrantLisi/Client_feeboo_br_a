/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by FacilityONE Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: FMToken.java
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

import org.json.JSONObject;

/**
 * FacilityOne定制Token
 * @author tessi.lu
 * @version V1.0，2015-04-02
 * @see
 * @since Shang
 */
public class FMToken extends Token {

    /**
     * 从返回的JSON数据解析Token数据
     * @param json 包括token的json数据
     */
	public FMToken(JSONObject json) {
		setAccessToken(json.optString("access_token"));
		setExpiresIn(json.optLong("expires_in"));
		setRefreshToken(json.optString("refresh_token"));
		setUid(json.optString("uid"));
	}
}
