package com.example.coco.liveproject.widget.gift;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.GiftMsgInfo;
import com.example.coco.liveproject.utils.ImageUtils;

import java.util.LinkedList;

/**
 * Created by coco on 2018/1/26.
 */

public class GiftFullView extends FrameLayout {

    private LayoutInflater inflater;
    private WindowManager manager;
    private int screenWidth;
    private int screenHeight;
    private ImageView mImg_sender_full;
    private TextView mTv_sender_id_full;
    private TextView mTv_gift_info_full;
    private GiftFullItem giftFullItem;

    LinkedList<GiftMsgInfo> list = new LinkedList<>();

    public GiftFullView(@NonNull Context context) {
        super(context);
        init();
    }

    public GiftFullView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.full_gift_view, this, true);

        manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        screenWidth = defaultDisplay.getWidth();
        screenHeight = defaultDisplay.getHeight();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenWidth, screenHeight);
        view.setLayoutParams(layoutParams);

        mImg_sender_full = view.findViewById(R.id.mImg_sender_full);
        mTv_sender_id_full = view.findViewById(R.id.mTv_sender_id_full);
        mTv_gift_info_full = view.findViewById(R.id.mTv_gift_info_full);
        giftFullItem = view.findViewById(R.id.gift_full_item);

        view.setBackgroundColor(getResources().getColor(R.color.transprant));

        giftFullItem.setVisibility(INVISIBLE);

        giftFullItem.setOnGiftAnimationCompletedListener(new GiftFullItem.onGiftAnimationCompletedListener() {
            @Override
            public void onCompleted() {
                setVisibility(INVISIBLE);
                if (!list.isEmpty()) {
                    GiftMsgInfo giftMsgInfo = list.removeFirst();
                    bindData(giftMsgInfo);
                    setVisibility(VISIBLE);
                    giftFullItem.porcheGo();
                }
            }
        });
    }

    private void bindData(GiftMsgInfo giftMsgInfo) {
        if (giftMsgInfo != null) {
            if (!TextUtils.isEmpty(giftMsgInfo.getAvatar())) {
                ImageUtils.getInstance().loadCircle(giftMsgInfo.getAvatar(), mImg_sender_full);
            } else {
                ImageUtils.getInstance().loadCircle(R.mipmap.ic_launcher_round, mImg_sender_full);
            }
            if (!TextUtils.isEmpty(giftMsgInfo.getLiveId())) {
                mTv_sender_id_full.setText(giftMsgInfo.getLiveId());
            } else {
                mTv_sender_id_full.setText("直播号：");
            }
        }

    }

    public GiftFullItem getGiftFullItem() {
        if (giftFullItem.getVisibility() == INVISIBLE) {
            return giftFullItem;
        } else {
            return null;
        }
    }
    public void showFullGift(GiftMsgInfo msgInfo){
        GiftFullItem giftFullItem = getGiftFullItem();
        if (giftFullItem!=null){
            bindData(msgInfo);
            setVisibility(VISIBLE);
            giftFullItem.porcheGo();
        }
        else {
            list.add(msgInfo);
        }
    }
}
