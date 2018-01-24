package com.example.coco.liveproject.widget.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coco.liveproject.R;

/**
 * Created by coco on 2018/1/16.
 */

public class BottomChatLayout extends FrameLayout implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private LayoutInflater inflater;
    private TextView mTv_bcl_send;
    private LinearLayout mLl_bcl_send;
    private EditText mEd_bcl;
    private CheckBox mCb_bcl;
    private onChatSendMsgListener listener;

    public BottomChatLayout(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public BottomChatLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        View view = inflater.inflate(R.layout.bottom_chat_layout, this, true);

        mCb_bcl = view.findViewById(R.id.mCb_bcl);
        mEd_bcl = view.findViewById(R.id.mEd_bcl);
        mLl_bcl_send = view.findViewById(R.id.mLl_bcl_send);
        mTv_bcl_send = view.findViewById(R.id.mTv_bcl_send);

        mCb_bcl.setOnCheckedChangeListener(this);
        mLl_bcl_send.setOnClickListener(this);
        mTv_bcl_send.setOnClickListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            mEd_bcl.setHint("弹幕聊天");
        }else {
            mEd_bcl.setHint("普通聊天");
        }

    }

    @Override
    public void onClick(View v) {
        String text = mEd_bcl.getText().toString().trim();
        if(mCb_bcl.isChecked()){
            if (listener!=null){
                listener.sendDanMu(text);
            }
        }else {
            if (listener!=null){
                listener.sendMsg(text);
            }
        }

    }
    public interface onChatSendMsgListener{
        void sendMsg(String msg);
        void sendDanMu(String msg);
    }
    public void setOnChatSendMsgListener(onChatSendMsgListener listener){
        this.listener=listener;
    }
}
