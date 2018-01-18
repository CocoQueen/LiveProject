package com.example.coco.liveproject.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.DMMsgInfo;

import java.util.LinkedList;

/**
 * Created by coco on 2018/1/18.
 */

public class DanmuView extends LinearLayout {


    private LayoutInflater inflater;
    private DanMuItemView mDan_item;
    private DanMuItemView mDan_item2;
    private int screenWidth;
    LinkedList<DMMsgInfo> list;

    public DanmuView(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public DanmuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        View view = inflater.inflate(R.layout.danmu_view, this, true);
        mDan_item = view.findViewById(R.id.mDan_item);
        mDan_item2 = view.findViewById(R.id.mDan_item2);
        setDefault();
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        screenWidth = display.getWidth();
    }

    private void setDefault() {
        mDan_item.setVisibility(INVISIBLE);
        mDan_item2.setVisibility(INVISIBLE);
    }

    public DanMuItemView getItemView() {
        if (mDan_item.getVisibility() == INVISIBLE) {
            return mDan_item;
        } else if (mDan_item2.getVisibility() == INVISIBLE) {
            return mDan_item2;
        } else {
            return null;
        }
    }

    public void addDanMu(DMMsgInfo info) {
        DanMuItemView view = getItemView();
        if (view != null) {
            startActionAnim(info, view);
        } else {

            if (list == null) {
                list = new LinkedList<>();
            }
            list.add(info);
        }
    }

    private void startActionAnim(DMMsgInfo info, final DanMuItemView view) {
        view.bindData(info);
        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        final int width = view.getMeasuredWidth();
        post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", screenWidth, -width);
                animator.setDuration(5000);
                animator.setInterpolator(new LinearInterpolator());
                view.setVisibility(VISIBLE);
                animator.start();

                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(INVISIBLE);
                        showCacheMsg(view);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });

    }

    private void showCacheMsg(DanMuItemView view) {
        if (list != null && !list.isEmpty()) {
            DMMsgInfo info = list.removeFirst();
            startActionAnim(info, view);
        }

    }
}
