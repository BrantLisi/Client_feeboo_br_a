package com.brantf.wireless.br_library.tools;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
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
}
