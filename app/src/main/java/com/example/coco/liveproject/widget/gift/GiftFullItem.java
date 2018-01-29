package com.example.coco.liveproject.widget.gift;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.coco.liveproject.R;

/**
 * Created by coco on 2018/1/26.
 */

public class GiftFullItem extends FrameLayout {

    private LayoutInflater inflater;
    private ImageView mImg_porche_back;
    private ImageView mImg_porche_front;
    private AnimationDrawable proche_back;
    private AnimationDrawable proche_front;
    private Animation fill_gift_in;
    private Animation fill_gift_stay;
    private Animation fill_gift_out;
    private onGiftAnimationCompletedListener listener;

    public GiftFullItem(@NonNull Context context) {
        super(context);
        init();
    }

    public GiftFullItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.full_gift_item, this, true);

        mImg_porche_back = view.findViewById(R.id.mImg_porche_back);
        mImg_porche_front = view.findViewById(R.id.mImg_porche_front);

        proche_back = (AnimationDrawable) mImg_porche_back.getBackground();
        proche_front = (AnimationDrawable) mImg_porche_front.getBackground();

        proche_back.start();
        proche_front.start();

        initAnimation();

    }

    private void initAnimation() {
        fill_gift_in = AnimationUtils.loadAnimation(getContext(), R.anim.full_gift_in);
        fill_gift_stay = AnimationUtils.loadAnimation(getContext(), R.anim.full_gift_stay);
        fill_gift_out = AnimationUtils.loadAnimation(getContext(), R.anim.full_gift_out);

        fill_gift_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startAnimation(fill_gift_out);
                    }
                },2000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fill_gift_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(INVISIBLE);
                listener.onCompleted();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void cancleDrawableAnim(){
        proche_back.stop();
        proche_front.stop();
    }
    public void porcheGo(){
        startAnimation(fill_gift_in);
    }
    public interface onGiftAnimationCompletedListener{
        void onCompleted();
    }
    public void setOnGiftAnimationCompletedListener(onGiftAnimationCompletedListener listener){
            this.listener=listener;
    }
}
