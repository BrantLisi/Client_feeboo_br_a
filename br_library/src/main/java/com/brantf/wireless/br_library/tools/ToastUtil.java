package com.brantf.wireless.br_library.tools;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * toast 工具
 *
 * @author brant.fei
 * @version v1.0，2017/4/27
 * @see
 * @since Shang
 */

public class ToastUtil {

    /**
     * 正常 短 toast
     */
    public static void showShortNormalToast(Context mContext, String resStr){
        Toasty.normal(mContext,resStr, Toast.LENGTH_SHORT).show();
    }

    /**
     * 正常 长 toast
     */
    public static void showLongNormalToast(Context mContext, String resStr){
        Toasty.normal(mContext,resStr, Toast.LENGTH_LONG).show();
    }

    /**
     * 错误 短 toast
     */
    public static void showShortErrorToast(Context mContext, String resStr){
        Toasty.error(mContext,resStr, Toast.LENGTH_SHORT).show();
    }

    /**
     * 错误 长 toast
     */
    public static void showLongErrorToast(Context mContext, String resStr){
        Toasty.error(mContext,resStr, Toast.LENGTH_LONG).show();
    }

    /**
     * 成功 短 toast
     */
    public static void showShortSuccessToast(Context mContext, String resStr){
        Toasty.success(mContext, resStr, Toast.LENGTH_SHORT).show();
    }

    /**
     * 成功 长 toast
     */
    public static void showLongSuccessToast(Context mContext, String resStr){
        Toasty.success(mContext,resStr, Toast.LENGTH_LONG).show();
    }

    /**
     * Warning 短 toast
     */
    public static void showShortWarningToast(Context mContext, String resStr){
        Toasty.warning(mContext,resStr, Toast.LENGTH_SHORT).show();
    }

    /**
     * Warning 长 toast
     */
    public static void showLongWarningToast(Context mContext, String resStr){
        Toasty.warning(mContext,resStr, Toast.LENGTH_LONG).show();
    }
}
