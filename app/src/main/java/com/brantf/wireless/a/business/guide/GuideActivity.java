/*
* -----------------------------------------------------------------------------------
* Copyright (C) 2004-2015, by brant_feeboo Head Office Co. Ltd. All rights reserved.
* -----------------------------------------------------------------------------------
*
* File: GuideActivity.java
* Author: brant.fei
* Version: 1.0
* Create: 2015-04-20
*
* Changes (from 2015-04-01)
* -----------------------------------------------------------------------------------
* Modify:
* -----------------------------------------------------------------------------------
*/

package com.brantf.wireless.a.business.guide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.brantf.wireless.a.R;
import com.brantf.wireless.a.business.login.LoginActivity;
import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SinglePage;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 *
 * @author brant.fei
 * @version V1.0，2015-04-20
 * @see
 * @since Shang
 */
public class GuideActivity extends AbsGuideActivity {

    @Override
    public List<SinglePage> buildGuideContent() {
        // prepare the information for our guide
        List<SinglePage> guideContent = new ArrayList<SinglePage>();

        SinglePage page01 = new SinglePage();
        page01.mBackground = getResources().getDrawable(R.mipmap.guide_01);
        guideContent.add(page01);

        SinglePage page02 = new SinglePage();
        page02.mBackground = getResources().getDrawable(R.mipmap.guide_02);
        guideContent.add(page02);

        SinglePage page03 = new SinglePage();
        page03.mBackground = getResources().getDrawable(R.mipmap.guide_03);
        guideContent.add(page03);

        SinglePage page04 = new SinglePage();
        page04.mCustomFragment = new EntryFragment();
        guideContent.add(page04);

        return guideContent;
    }

    @Override
    public Bitmap dotDefault() {
        return null;
    }

    @Override
    public Bitmap dotSelected() {
        return null;
    }

    @Override
    public boolean drawDot() {
        return false;
    }

    /**
     * 切换到登录页面
     */
    public void switchToLogin() {
        LoginActivity.startActivity(this);
        GuideActivity.this.finish();
    }

    /**
     * You need provide an id to the pager. You could define an id in
     * values/ids.xml and use it.
     */
    @Override
    public int getPagerId() {
        return R.id.guide_container;
    }

    /**
     * 启动引导页面
     *
     * @param fromActivity 启动的Context
     */
    public static void startActivity(Context fromActivity) {
        Intent intent = new Intent(fromActivity, GuideActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        fromActivity.startActivity(intent);
    }
}
