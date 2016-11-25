package com.brantf.wireless.a.business.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.brantf.wireless.a.R;
import com.brantf.wireless.br_library.tools.DensityUtil;


public class EntryFragment extends Fragment {

    private RelativeLayout enterIv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry, null);
        enterIv = (RelativeLayout)v.findViewById(R.id.entry_fl);
        Button btn = new Button(getActivity());
        btn.setBackgroundResource(R.drawable.icon_use_now_btn);
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
                DensityUtil.dpToPx(151),
                DensityUtil.dpToPx(31));
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL,
                RelativeLayout.TRUE);
        //屏幕适配 动态设定 马上体验 位置
        int height = DensityUtil.getDisplayHeight(getActivity());
        lp1.setMargins(0, 0, 0, (height * 7 / 20));
        enterIv.addView(btn, lp1);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                GuideActivity activity = (GuideActivity) getActivity();
                activity.switchToLogin();
            }
        });
        return v;
    }
}
