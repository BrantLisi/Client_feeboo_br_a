package com.brantf.wireless.a.business.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.brantf.wireless.a.R;
import com.brantf.wireless.a.common.base.BaseFragmentActivity;
import com.brantf.wireless.a.common.views.CustomTabItemView;
import com.brantf.wireless.br_library.views.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseFragmentActivity  implements View.OnClickListener {
    @BindView(R.id.menu_message)
    CustomTabItemView mMenuMessage;//消息
    @BindView(R.id.menu_work)
    CustomTabItemView mMenuWork;//工作
    @BindView(R.id.menu_mine)
    CustomTabItemView mMenuMine;//我的
    @BindView(R.id.fragment_container)//滑动菜单视图
    CustomViewPager mViewPager;

    private FragmentManager mFm;//用于对Fragment进行管理
    private int mCurrentIndex = -1;//当前Tab的Index

    private List<Fragment> mFragmentList;
    private MessageFragment messageFragment;
    private WorkFragment workFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    private void initView() {
        mFm = getSupportFragmentManager();
        mMenuMessage.setOnClickListener(this);
        mMenuWork.setOnClickListener(this);
        mMenuMine.setOnClickListener(this);

        // 设置Tab页面集合
        mFragmentList = new ArrayList<>();

        messageFragment = new MessageFragment();
        workFragment = new WorkFragment();
        myFragment = new MyFragment();

        mFragmentList.add(messageFragment);
        mFragmentList.add(workFragment);
        mFragmentList.add(myFragment);

        mViewPager.setNoScroll(false);//设置可否滑动
        mViewPager.setAdapter(new MyFragmentPagerAdapter(mFm, mFragmentList));
        // 设置一边加载的page数
        mViewPager.setOffscreenPageLimit(2);
        // 切换渐变
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTabSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // 启动程序后选中第一个tab
        setTabSelection(0);
    }

    /**
     * 设置选中的Tab
     *
     * @param index 每个tab对应的下标。0表示消息，1表示工作，2表示我的。
     */
    private void setTabSelection(int index) {
        // 当重复选中相同Tab时不进行任何处理
        if (mCurrentIndex == index) {
            return;
        }
        // 设置当前Tab的Index值为传入的Index值
        mCurrentIndex = index;
        // 改变ViewPager视图
        mViewPager.setCurrentItem(index, false);
        // 清除掉上次的选中状态
        clearSelection();
        // 判断传入的Index
        switch (index) {
            case 0:
                if (messageFragment != null) {
                    messageFragment.refreshFragData();
                }
                mMenuMessage.setSelected(true);
                break;
            case 1:
                if (workFragment != null) {
                    workFragment.refreshFragData();
                }
                mMenuWork.setSelected(true);
                break;
            case 2:
                if (myFragment != null) {
                    myFragment.refreshFragData();
                }
                mMenuMine.setSelected(true);
                break;
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        mMenuMessage.setSelected(false);
        mMenuWork.setSelected(false);
        mMenuMine.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        // 判断选中的Tab
        switch (v.getId()) {
            case R.id.menu_message:
                // 切换消息视图
                setTabSelection(0);
                break;
            case R.id.menu_work:
                // 切换工作视图
                setTabSelection(1);
                break;
            case R.id.menu_mine:
                // 切换我的视图
                setTabSelection(2);
                break;
            default:
                break;
        }
    }

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, MainActivity.class);
        fromActivity.startActivity(intent);
    }
}
