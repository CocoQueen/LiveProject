package com.example.coco.liveproject.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.DMMsgInfo;
import com.example.coco.liveproject.utils.ImageUtils;

/**
 * Created by coco on 2018/1/17.
 */

public class DanMuItemView extends FrameLayout {

    private LayoutInflater inflater;
    private ImageView mImg_danmu_headImg;
    private TextView mTv_danmu_liveid;
    private TextView mTv_danmu_msg;

    public DanMuItemView(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public DanMuItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        View view = inflater.inflate(R.layout.danmu_item_view, this, true);
        mImg_danmu_headImg = view.findViewById(R.id.mImg_danmu_headImg);
        mTv_danmu_liveid = view.findViewById(R.id.mTv_danmu_liveid);
        mTv_danmu_msg = view.findViewById(R.id.mTv_danmu_msg);
        setDefault();
    }

    private void setDefault() {
        ImageUtils.getInstance().loadCircle(R.mipmap.logo, mImg_danmu_headImg);
    }

    public void bindData(DMMsgInfo info) {
        if (TextUtils.isEmpty(info.getAvatar())) {
            ImageUtils.getInstance().loadCircle(R.mipmap.logo, mImg_danmu_headImg);
        } else {
            ImageUtils.getInstance().loadCircle(info.getAvatar(), mImg_danmu_headImg);
        }
        if (!TextUtils.isEmpty(info.getLiveId())) {
            mTv_danmu_liveid.setText(info.getLiveId());
        }
        if (!TextUtils.isEmpty(info.getText())) {
            mTv_danmu_msg.setText(info.getText());
        }
    }
}
