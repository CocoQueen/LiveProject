package com.example.coco.liveproject.widget.gift;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.GiftMsgInfo;
import com.example.coco.liveproject.utils.ImageUtils;

/**
 * Created by coco on 2018/1/22.
 */

public class GiftItem extends FrameLayout {

    private LayoutInflater inflater;
    private RelativeLayout mRl_gift_item;
    private ImageView mImg_gift_icon;
    private ImageView mImg_sender;
    private TextView mTv_gift_info;
    private TextView mTv_gift_num;
    private TextView mTv_sender_id;
    private Animation gift_left_in;
    private Animation gift_icon_left_in;
    private Animation gift_num_scale;
    private int sendNum = 0;
    private FrameLayout mFl;
    private Animation gift_left_out;
    boolean isRepeat = false;
    private View view;

    public GiftItem(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        initView();
    }

    public GiftItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        view = inflater.inflate(R.layout.item_send_gift, this, true);
        mRl_gift_item = view.findViewById(R.id.mRl_gift_item);
        mImg_gift_icon = view.findViewById(R.id.mImg_gift_icon);
        mImg_sender = view.findViewById(R.id.mImg_sender);
        mTv_gift_info = view.findViewById(R.id.mTv_gift_info);
        mTv_gift_num = view.findViewById(R.id.mTv_gift_num);
        mTv_sender_id = view.findViewById(R.id.mTv_sender_id);
        mFl = view.findViewById(R.id.mFl);


        mRl_gift_item.setVisibility(INVISIBLE);
        mImg_gift_icon.setVisibility(INVISIBLE);
        mTv_gift_num.setVisibility(INVISIBLE);

        gift_left_in = AnimationUtils.loadAnimation(getContext(), R.anim.gift_left_in);
        gift_icon_left_in = AnimationUtils.loadAnimation(getContext(), R.anim.gift_icon_left_in);
        gift_num_scale = AnimationUtils.loadAnimation(getContext(), R.anim.gift_num_scale);
        gift_left_out = AnimationUtils.loadAnimation(getContext(), R.anim.gift_left_out);

        gift_left_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                mImg_gift_icon.setVisibility(INVISIBLE);
//                mTv_gift_num.setVisibility(INVISIBLE);
                mRl_gift_item.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImg_gift_icon.setAnimation(gift_icon_left_in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        gift_icon_left_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mImg_gift_icon.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTv_gift_num.setAnimation(gift_num_scale);
//                mTv_gift_num.setVisibility(VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        gift_num_scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mTv_gift_num.setVisibility(VISIBLE);
                mTv_gift_num.setText("X" + sendNum);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (sendNum > 1 && isRepeat) {
                    gift_num_scale.start();

                } else {
                    mRl_gift_item.startAnimation(gift_left_out);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        gift_left_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mImg_gift_icon.setVisibility(INVISIBLE);
                mTv_gift_num.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRl_gift_item.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void bindData(GiftMsgInfo info) {
        if (info != null) {
            if (TextUtils.isEmpty(info.getAvatar())) {
                ImageUtils.getInstance().loadCircle(R.mipmap.ic_launcher_round, mImg_sender);
            } else {
                ImageUtils.getInstance().loadCircle(info.getAvatar(), mImg_sender);
            }
            if (!TextUtils.isEmpty(info.getLiveId())) {
                mTv_sender_id.setText(info.getLiveId());
            }
            if (info.getInfo() != null) {
                mTv_gift_info.setText("送出了" + info.getInfo().getGiftName());
                mImg_gift_icon.setBackgroundResource(info.getInfo().getResId());
            }
            if (info.getGiftNum() >= 1) {
                mTv_gift_num.setText("X" + info.getGiftNum());
            }
        }

    }

    public void setGiftNum(int num) {
        if (num > 1) {
            mTv_gift_num.setText("X" + num);
        }
    }

    public void startAnimtion() {
        mRl_gift_item.setAnimation(gift_left_in);
        sendNum = 1;
    }

    public void repeatSend() {
        sendNum++;
        gift_num_scale.start();

    }

    public void repeatSendWithoutAddNum() {
        mRl_gift_item.setAnimation(gift_num_scale);
        gift_left_out.start();
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }
}
