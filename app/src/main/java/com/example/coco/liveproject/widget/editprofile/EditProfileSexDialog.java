package com.example.coco.liveproject.widget.editprofile;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.coco.liveproject.R;

/**
 * Created by coco on 2018/1/6.
 */

public class EditProfileSexDialog implements View.OnClickListener {
    private static final String TAG = "EditProfileSexDialog";
    private OnProfileSexChangedListener listener;
    Activity activity;
    private final Dialog dialog;
    private LayoutInflater inflater;
    private WindowManager manager;
    private Display display;
    private int screenWidth;
    private View view;
    private ImageView mImg_icon;
    private TextView mTv_title;
    private RadioGroup mRg_sex;
    private RadioButton mRb_boy;
    private RadioButton mRb_girl;
    private Button mBtn_ok;
    private Button mBtn_cancle;

    public EditProfileSexDialog(OnProfileSexChangedListener listener, Activity activity) {
        this.listener = listener;
        this.activity = activity;
        dialog = new Dialog(activity);
        initView();
    }

    public EditProfileSexDialog(OnProfileSexChangedListener listener, Activity activity, int styleId) {
        this.listener = listener;
        this.activity = activity;
        dialog = new Dialog(activity, styleId);
        initView();
    }

    private void initView() {
        inflater = LayoutInflater.from(activity);
        manager = activity.getWindowManager();
        display = manager.getDefaultDisplay();
        screenWidth = display.getWidth();
        view = inflater.inflate(R.layout.edit_profile_sex_dialog, null, false);
        mImg_icon = view.findViewById(R.id.mImg_icon);
        mTv_title = view.findViewById(R.id.mTv_title);
        mRg_sex = view.findViewById(R.id.mRg_sex);
        mRb_boy = view.findViewById(R.id.mRb_boy);
        mRb_girl = view.findViewById(R.id.mRb_girl);
        mBtn_ok = view.findViewById(R.id.mBtn_ok);
        mBtn_cancle = view.findViewById(R.id.mBtn_cancle);

        mBtn_ok.setOnClickListener(this);
        mBtn_cancle.setOnClickListener(this);

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = screenWidth * 80 / 100;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void hide() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setCancelable(boolean value) {
        dialog.setCancelable(value);
    }

    //设置标题和图标
    public void setTitleAndIcon(String title, int resId) {
        if (!TextUtils.isEmpty(title)) {
            mTv_title.setText(title);
        }
        if (resId > 0) {
            mImg_icon.setBackgroundResource(resId);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtn_ok:
                if (mRb_girl.isChecked() || mRb_boy.isChecked()) {
                    if (listener != null) {
                        listener.onChangedSuccess(mRb_boy.isChecked() ? "男" : "女");
                    }
                } else {
                    if (listener != null) {
                        listener.onChangedSuccess("未知");
                    }
                }


                break;
            case R.id.mBtn_cancle:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
        }
    }

    public interface OnProfileSexChangedListener {
        void onChangedSuccess(String value);

        void onChangedError();
    }

}
