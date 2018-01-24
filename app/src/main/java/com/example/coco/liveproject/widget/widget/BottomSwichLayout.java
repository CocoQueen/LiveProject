package com.example.coco.liveproject.widget.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.coco.liveproject.R;

/**
 * Created by coco on 2018/1/16.
 * 切换聊天的自定义类
 */

public class BottomSwichLayout extends FrameLayout implements View.OnClickListener {

    private LayoutInflater inflater;
    private ImageView mImg_bsl_chat;
    private ImageView mImg_bsl_close;
    public ImageView mImg_bsl_gift;
    private onSwichLayoutListener listener;

    public BottomSwichLayout(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public BottomSwichLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        View view = inflater.inflate(R.layout.bottom_swich_layout, this, true);

        mImg_bsl_chat = view.findViewById(R.id.mImg_bsl_chat);
        mImg_bsl_close = view.findViewById(R.id.mImg_bsl_close);
        mImg_bsl_gift = view.findViewById(R.id.mImg_bsl_gift);

        mImg_bsl_chat.setOnClickListener(this);
        mImg_bsl_close.setOnClickListener(this);
        mImg_bsl_gift.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mImg_bsl_chat:
                if (listener != null) {
                    listener.onChat();
                }
                break;
            case R.id.mImg_bsl_close:
                if (listener != null) {
                    listener.onClose();
                }
                break;
            case R.id.mImg_bsl_gift:
                if (listener!=null){
                    listener.onGift();
                }
                break;
        }
    }

    public interface onSwichLayoutListener {
        void onChat();

        void onClose();

        void onGift();
    }

    public void setOnSwichLayoutListener(onSwichLayoutListener listener) {
        this.listener = listener;
    }
}
