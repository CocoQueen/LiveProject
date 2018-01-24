package com.example.coco.liveproject.widget.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

/**
 * Created by coco on 2018/1/16.
 */

public class HeightConstraintLayout extends ConstraintLayout {
    private onHeightConstraintLayoutChangedListener listener;

    public HeightConstraintLayout(Context context) {
        super(context);
    }

    public HeightConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h > oldh) {
            if (listener != null) {
                listener.showNormal();
            }

        } else if (h < oldh) {
            if (listener != null) {
                listener.showChat();
            }
        }
    }

    public interface onHeightConstraintLayoutChangedListener {
        void showNormal();

        void showChat();
    }

    public void setOnHeightConstraintLayoutChangedListener(onHeightConstraintLayoutChangedListener listener) {
        this.listener = listener;
    }
}
