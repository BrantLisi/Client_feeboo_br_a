package com.brantf.wireless.a.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brantf.wireless.a.R;


/**
 * Author：gary.xu
 * <p/>
 * Email: xuhaozv@163.com
 * <p/>
 * description:底部菜单View
 * <p/>
 * Date: 2016/9/12 16:44
 */
public class CustomTabItemView extends LinearLayout {
    private ImageView mImageView;
    private TextView mTextView;
    private TextView mNumTv;
    private Context mContext;
    private int mId;

    /**
     * The icons unselected in tab item.
     */
    private static int mTabIconsUnselected[] = {R.mipmap.tabhost_task_unselected,
            R.mipmap.tabhost_function_unselected, R.mipmap.tabhost_user_unselected,};

    /**
     * The icons selected in tab item.
     */
    private static int mTabIconsSelected[] = {R.mipmap.tabhost_task_selected,
            R.mipmap.tabhost_function_selected, R.mipmap.tabhost_user_selected,};

    private String mTabTitles[];

    public CustomTabItemView(Context context) {
        this(context, null);
    }

    public CustomTabItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTabItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }
    public CustomTabItemView(Context context, int index) {
        super(context);
        mTabTitles = getResources().getStringArray(R.array.home_tab_host_tabs);
        mContext = context;
        mId=index;
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_tab_item_view, this);
        mImageView = (ImageView) view.findViewById(R.id.imageview);
        mImageView.setImageResource(mTabIconsUnselected[mId]);
        mTextView = (TextView) view.findViewById(R.id.textview);
        mNumTv = (TextView) view.findViewById(R.id.num_tv);
        mTextView.setText(mTabTitles[mId]);
        mTextView.setTextColor(mContext.getResources().getColor(
                R.color.grey_level2));
    }

    private void init(Context context, AttributeSet attrs) {
        mTabTitles = getResources().getStringArray(R.array.home_tab_host_tabs);
        mContext = context;
        if(attrs!=null){
            TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTabItemView);
            mId=attrsArray.getInt(R.styleable.CustomTabItemView_ctv_menu_index,0);
            attrsArray.recycle();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_tab_item_view, this);
        mImageView = (ImageView) view.findViewById(R.id.imageview);
        mImageView.setImageResource(mTabIconsUnselected[mId]);
        mTextView = (TextView) view.findViewById(R.id.textview);
        mNumTv = (TextView) view.findViewById(R.id.num_tv);
        mTextView.setText(mTabTitles[mId]);
        mTextView.setTextColor(mContext.getResources().getColor(
                R.color.grey_level2));
    }


    public void setSelected(boolean selected) {
        if (selected) {
            mImageView.setImageResource(mTabIconsSelected[mId]);
            mTextView.setTextColor(mContext.getResources().getColor(
                    R.color.main_theme_color));
        } else {
            mImageView.setImageResource(mTabIconsUnselected[mId]);
            mTextView.setTextColor(mContext.getResources().getColor(
                    R.color.grey_level2));
        }
        this.invalidate();
    }

    public TextView getNumTv() {
        return mNumTv;
    }
}
