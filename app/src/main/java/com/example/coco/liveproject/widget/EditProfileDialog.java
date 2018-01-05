package com.example.coco.liveproject.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
 * Created by coco on 2018/1/5.
 */

public class EditProfileDialog implements View.OnClickListener {
    private onEditChangedListener listener;
    private Context context;
    private LayoutInflater inflater;
    private Dialog dialog;
    private final AlertDialog.Builder builder;
    private WindowManager manager;
    private Display display;
    private View view;
    private ImageView mImg_edit_dialog;
    private TextView mTv_edit_dialog_title;
    private EditText mEd_edit_dialog_content;
    private Button mBtn_edit_dialog_ok;
    private Button mBtn_edit_dialog_cancle;


    public interface onEditChangedListener {
        void onChanged(String value);

        void onEmpty();
    }

    public EditProfileDialog(onEditChangedListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        builder = new AlertDialog.Builder(context);
        initView();
    }

    public EditProfileDialog(onEditChangedListener listener, Context context, int themeResId) {
        this.listener = listener;
        this.context = context;
        builder = new AlertDialog.Builder(context);
        initView();
    }

    private void initView() {
        manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = manager.getDefaultDisplay();
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.edit_profile_dialog, null, false);
        mImg_edit_dialog = view.findViewById(R.id.mImg_edit_dialog);
        mTv_edit_dialog_title = view.findViewById(R.id.mTv_edit_dialog_title);
        mEd_edit_dialog_content = view.findViewById(R.id.mEd_edit_dialog_content);
        mBtn_edit_dialog_ok = view.findViewById(R.id.mBtn_edit_dialog_ok);
        mBtn_edit_dialog_cancle = view.findViewById(R.id.mBtn_edit_dialog_cancle);

        mBtn_edit_dialog_ok.setOnClickListener(this);
        mBtn_edit_dialog_cancle.setOnClickListener(this);

        builder.setView(view);
        dialog = builder.create();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtn_edit_dialog_ok:
                String content = mEd_edit_dialog_content.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    if (listener != null) {
                        listener.onChanged(content);
                    }
                } else {
                    if (listener != null) {
                        listener.onEmpty();
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

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setTitleAndIcon(String title, int resId) {
        if (!TextUtils.isEmpty(title)) {
            mTv_edit_dialog_title.setText(title);
        }
        if (resId > 0) {
            mImg_edit_dialog.setBackgroundResource(resId);
        }

    }
}
