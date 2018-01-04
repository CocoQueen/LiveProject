package com.example.coco.liveproject.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.coco.liveproject.app.LiveApplication;

/**
 * Created by coco on 2018/1/3.
 */

public class ImageUtils {
    static ImageUtils utils = new ImageUtils();

    public static ImageUtils getInstance() {
        return utils;
    }

    public void loadCircle(int resid, ImageView mImg) {
        Glide.with(LiveApplication.getApp())
                .load(resid)
                .apply(RequestOptions.circleCropTransform())
                .into(mImg);
    }

    public void loadCircle(String path, ImageView mImg) {
        Glide.with(LiveApplication.getApp())
                .load(path)
                .apply(RequestOptions.circleCropTransform())
                .into(mImg);
    }
}
