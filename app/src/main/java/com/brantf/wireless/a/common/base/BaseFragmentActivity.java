package com.brantf.wireless.a.common.base;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;

/**
 */
@SuppressLint("NewApi")
public abstract class BaseFragmentActivity extends FragmentActivity {
    private ActionBar mActionBar;
    private LinearLayout mActionbarBack;
    private ImageView mActionbarAppIcon;
    private TextView mActionbarTitle;
    private LinearLayout mActionBarRightLl;
    private View mActionBarView;
    private LinearLayout mAcitonBarLl;

    private Set<Integer> mMenuItemMap = new HashSet<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDetachedFromWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentView());
        ButterKnife.bind(this);
    }

    public abstract int getContentView();

    /**
     * Set the action bar background.
     * 
     * @param resId
     */
    public void setActionbarBackground(int resId) {
        mAcitonBarLl.setBackgroundResource(resId);
    }

    /**
     * Set the action bar title.
     * 
     * @param resId
     */
    public void setActionBarTitle(int resId) {
        if (mActionbarTitle != null) {
            mActionbarTitle.setText(getResources().getString(resId));
        }
    }

    /**
     * All menu items click events happen here.
     * 
     * @param itemId
     */
    protected void onClickMenuItem(int itemId) {}

    /**
     * The click listener on menu item.
     */
    private MenuClickListener mMenuClickListener = new MenuClickListener();

    public class MenuClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            onClickMenuItem(v.getId());
        }
    }

}
