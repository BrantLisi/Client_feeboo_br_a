package com.brantf.wireless.br_library.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by brant.fei on 2016/4/26.
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpToPx(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float pxTodp(int px) {
        return (px / Resources.getSystem().getDisplayMetrics().density);
    }


    /**
     * 設置View的高度（像素），若設置爲自適應則應該傳入MarginLayoutParams.WRAP_CONTENT
     * @param view
     * @param height
     */
    public static void setLayoutHeight(View view, int height){
        if (view.getParent() instanceof FrameLayout){
            FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams) view.getLayoutParams();
            lp.height=height;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
        else if (view.getParent() instanceof RelativeLayout){
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams)view.getLayoutParams();
            lp.height=height;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
        else if (view.getParent() instanceof LinearLayout){
            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)view.getLayoutParams();
            lp.height=height;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
    }

    /**
     * 設置View的寬度（像素），若設置爲自適應則應該傳入MarginLayoutParams.WRAP_CONTENT
     * @param view
     * @param width
     */
    public static void setLayoutWidth(View view, int width){
        if (view.getParent() instanceof FrameLayout){
            FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams) view.getLayoutParams();
            lp.width=width;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
        else if (view.getParent() instanceof RelativeLayout){
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams)view.getLayoutParams();
            lp.width=width;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
        else if (view.getParent() instanceof LinearLayout){
            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)view.getLayoutParams();
            lp.width=width;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
    }

    /**
     * 設置View的宽高度（像素），若設置爲自適應則應該傳入MarginLayoutParams.WRAP_CONTENT
     * @param view
     * @param width
     * @param height
     */
    public static void setLayoutWidthHeight(View view, int width, int height){
        if (view.getParent() instanceof FrameLayout){
            FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams) view.getLayoutParams();
            lp.width=width;
            lp.height=height;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
        else if (view.getParent() instanceof RelativeLayout){
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams)view.getLayoutParams();
            lp.width=width;
            lp.height=height;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
        else if (view.getParent() instanceof LinearLayout){
            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)view.getLayoutParams();
            lp.width=width;
            lp.height=height;
            view.setLayoutParams(lp);
            view.requestLayout();
        }
    }

    /**
     * 获得屏幕宽度
     * @param mContext
     * @return
     */
    public static int getDisplayWidth(Context mContext){
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;     // 屏幕宽度（像素）
    }

    /**
     * 获得屏幕高度
     * @param mContext
     * @return
     */
    public static int getDisplayHeight(Context mContext){
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;   // 屏幕高度（像素）
    }

    /**
     * 获得应用高度
     * @param activity
     * @return
     */
    public static int getApplicationHeight(Activity activity){
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.height();
    }
}
