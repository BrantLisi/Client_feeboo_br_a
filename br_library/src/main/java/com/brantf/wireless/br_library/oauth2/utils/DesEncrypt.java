/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by FacilityONE Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: DesEncrypt.java
* Author: tessi.lu
* Version: 1.0
* Create: 2015-04-02
*
* Changes (from 2015-04-02)
* -----------------------------------------------------------------------------------
* Modify:
* -----------------------------------------------------------------------------------
*/

package com.brantf.wireless.br_library.oauth2.utils;

import android.text.TextUtils;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 加密工具
 * 用于对保存于本地的AccessToken进行加密存储
 * @author tessi.lu
 * @version V1.0，2015-04-02
 * @see
 * @since Shang
 */
public class DesEncrypt {
	private String password;
	private Key key;

	public DesEncrypt(String password) {
		this.password = password;
	}

    /**
     * 加密数据
     * @param strMing 要进行加密的数据
     * @return 加密好的数据
     */
	public String getEncString(String strMing) {
		String strMi = "";
		try {
			return byte2hex(getEncCode(strMing.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strMi;
	}

    /**
     * 解码数据
     * @param strMi 加密的数据
     * @return 解码后的数据
     */
	public String getDesString(String strMi) {
		String strMing = "";
		try {
			return TextUtils.isEmpty(strMi)?strMing:
					new String(getDesCode(hex2byte(strMi.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strMing;
	}

    /**
     * 设置密钥
     */
    protected void setKey(String strKey) {
        KeyGenerator generator;
        try {
            generator = KeyGenerator.getInstance("DES");
            generator.init(new SecureRandom(strKey.getBytes()));
            KeySpec keySpec = new DESKeySpec(strKey.getBytes());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            Key key = factory.generateSecret(keySpec);
            this.key = key;
            generator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新密钥
     */
	private void validateKey() {
		if (this.key == null) {
            setKey(password);
        }
	}

    /**
     * 字节数据DES加密
     * @param byteS
     * @return
     */
	private byte[] getEncCode(byte[] byteS) {
		Cipher cipher;
		byte[] byteFina = null;

		try {
			validateKey();
			cipher = Cipher.getInstance("DES");
			cipher.init(1, this.key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return byteFina;
	}

    /**
     * 字节数据DES解密
     * @param byteD
     * @return
     */
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;

		try {
			validateKey();
			cipher = Cipher.getInstance("DES");
			cipher.init(2, this.key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return byteFina;
	}

    /**
     * 将byte转换成十六进制字符串
     * @param b
     * @return
     */
	private String byte2hex(byte[] b) {
		String hs = "";
		String tmpStr;

		for (int n = 0; n < b.length; ++n) {
			tmpStr = Integer.toHexString(b[n] & 0xFF);
			if (tmpStr.length() == 1) {
                hs = hs + "0" + tmpStr;
            } else {
                hs = hs + tmpStr;
            }
		}

		return hs.toUpperCase();
	}

    /**
     * 将十六进制字符串转换成byte
     * @param b
     * @return
     */
    private byte[] hex2byte(byte[] b) {
		if (b.length % 2 != 0) {
            throw new IllegalArgumentException("length error");
        }

		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);

			b2[(n / 2)] = (byte) Integer.parseInt(item, 16);
		}

		return b2;
	}
}