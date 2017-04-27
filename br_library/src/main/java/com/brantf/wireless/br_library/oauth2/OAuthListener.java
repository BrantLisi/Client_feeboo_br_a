/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by FacilityONE Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: OAuthListener.java
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

import com.brantf.wireless.br_library.oauth2.token.Token;

/**
 * OAuth2.0授权监听类
 * @author tessi.lu
 * @version V1.0，2015-04-02
 * @see
 * @since Shang
 */
public interface OAuthListener {
	void onSuccess(Token token);
	void onCancel();
	void onError(String error);
}