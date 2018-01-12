package com.example.coco.liveproject.utils;

import android.net.Uri;
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

    public void loadCircle(Uri uri, ImageView mImg) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop();
        Glide.with(LiveApplication.getApp())
                .load(uri)
                .apply(requestOptions)
                .into(mImg);
    }

    public void loadPic(Uri uri, ImageView mImg) {
        Glide.with(LiveApplication.getApp())
                .load(uri)
                .into(mImg);
    }

    public void loadPic(int resId, ImageView mImg) {
        Glide.with(LiveApplication.getApp())
                .load(resId)
                .into(mImg);
    }

    public void loadPic(String url, ImageView mImg) {
        Glide.with(LiveApplication.getApp())
                .load(url)
                .into(mImg);
    }


}
