package com.example.coco.liveproject.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.model.PhotoHelper;

/**
 * Created by coco on 2018/1/6.
 */

public class EditProfileHeadImgDialog implements View.OnClickListener {
    Activity activity;
    private final Dialog dialog;
    private WindowManager manager;
    private Display display;
    private LayoutInflater inflater;
    private View view;
    private TextView mTv_photo;
    private TextView mTv_camera;
    private LinearLayout mLin_cancle;

    public EditProfileHeadImgDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        initView();
    }

    public EditProfileHeadImgDialog(Activity activity, int resId) {
        this.activity = activity;
        dialog = new Dialog(activity, resId);
        initView();
    }

    private void initView() {
        manager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        display = manager.getDefaultDisplay();
        inflater = LayoutInflater.from(activity);
        view = inflater.inflate(R.layout.edit_profile_headimg_dialog, null, false);

        mTv_photo = view.findViewById(R.id.mTv_photo);
        mTv_camera = view.findViewById(R.id.mTv_camera);
        mLin_cancle = view.findViewById(R.id.mLin_cancle);

        mTv_camera.setOnClickListener(this);
        mTv_photo.setOnClickListener(this);
        mLin_cancle.setOnClickListener(this);

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = display.getWidth();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);


    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public void show() {
        dialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTv_camera:
                PhotoHelper.getInstance(activity).startCameraIntent();
                break;
            case R.id.mTv_photo:
                PhotoHelper.getInstance(activity).startPhotoIntent();
                break;
            case R.id.mLin_cancle:
                dialog.dismiss();
                break;
        }
    }


}
