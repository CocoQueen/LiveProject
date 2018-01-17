package com.example.coco.liveproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by coco on 2018/1/16.
 */

public class HeightRelativeLayout extends RelativeLayout {
    private onHeightRelativeLayoutChangedListener listener;

    public HeightRelativeLayout(Context context) {
        super(context);
    }

    public HeightRelativeLayout(Context context, AttributeSet attrs) {
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

    public interface onHeightRelativeLayoutChangedListener {
        void showNormal();

        void showChat();
    }

    public void setOnHeightRelativeLayoutChangedListener(onHeightRelativeLayoutChangedListener listener) {
        this.listener = listener;
    }
}
