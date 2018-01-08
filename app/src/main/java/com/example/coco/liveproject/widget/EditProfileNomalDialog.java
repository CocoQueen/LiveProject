package com.example.coco.liveproject.widget;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coco.liveproject.R;

/**
 * Created by coco on 2018/1/6.
 */

public class EditProfileNomalDialog implements View.OnClickListener {

    private OnProfileNomalChangedListener listener;
    private Activity activity;
    private final Dialog dialog;
    private LayoutInflater inflater;
    private WindowManager manager;
    private int screenWidth;
    private View view;
    private ImageView mImg_edit_dialog;
    private TextView mTv_edit_dialog_title;
    private EditText mEd_edit_dialog_content;
    private Button mBtn_edit_dialog_ok;
    private Button mBtn_edit_dialog_cancle;

    public EditProfileNomalDialog(Activity activity, OnProfileNomalChangedListener listener) {
        this.activity = activity;
        this.listener = listener;
        dialog = new Dialog(activity);
        initView();
    }

    public EditProfileNomalDialog(Activity activity, OnProfileNomalChangedListener listener, int styleId) {
        this.activity = activity;
        this.listener = listener;
        dialog = new Dialog(activity, styleId);
        initView();

    }

    private void initView() {
        inflater = LayoutInflater.from(activity);
        manager = activity.getWindowManager();
        Display display = manager.getDefaultDisplay();
        screenWidth = display.getWidth();
        view = inflater.inflate(R.layout.edit_profile_dialog, null, false);

        mImg_edit_dialog = view.findViewById(R.id.mImg_edit_dialog);
        mTv_edit_dialog_title = view.findViewById(R.id.mTv_edit_dialog_title);
        mEd_edit_dialog_content = view.findViewById(R.id.mEd_edit_dialog_content);
        mBtn_edit_dialog_ok = view.findViewById(R.id.mBtn_edit_dialog_ok);
        mBtn_edit_dialog_cancle = view.findViewById(R.id.mBtn_edit_dialog_cancle);

        mBtn_edit_dialog_ok.setOnClickListener(this);
        mBtn_edit_dialog_cancle.setOnClickListener(this);

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = screenWidth * 80 / 100;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtn_edit_dialog_ok:
                String content = mEd_edit_dialog_content.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    if (listener != null) {
                        listener.onChangeSucess(content);
                    }
                } else {
                    if (listener != null) {
                        listener.onChangeError();
                    }

                }

                break;
            case R.id.mBtn_edit_dialog_cancle:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
        }

    }

    //设置标题和图标
    public void setTitleAndIcon(String title, int resId) {
        if (!TextUtils.isEmpty(title)) {
            mTv_edit_dialog_title.setText(title);
        }
        if (resId > 0) {
            mImg_edit_dialog.setBackgroundResource(resId);
        }

    }

    //对话框显示
    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    //对话框隐藏
    public void hide() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    //设置对话框可否取消
    public void setCancelable(boolean value) {
        dialog.setCancelable(value);
    }

    //接口  包含改变内容成功和失败的方法
    public interface OnProfileNomalChangedListener {
        //改变内容成功
        void onChangeSucess(String value);

        void onChangeError();
    }


}
