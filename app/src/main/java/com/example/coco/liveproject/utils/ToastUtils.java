package com.example.coco.liveproject.utils;

import android.widget.Toast;

import com.example.coco.liveproject.app.LiveApplication;

/**
 * Created by coco on 2018/1/2.
 */

public class ToastUtils {
    static Toast toast;

    public static void show(String str) {
        if (toast == null) {
            toast = Toast.makeText(LiveApplication.getApp().getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }
}
