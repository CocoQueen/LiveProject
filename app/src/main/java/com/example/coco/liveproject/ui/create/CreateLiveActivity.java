package com.example.coco.liveproject.ui.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.utils.ToastUtils;
import com.example.coco.liveproject.widget.EditProfileHeadImgDialog;

public class CreateLiveActivity extends AppCompatActivity implements CreateLiveContract.CreateLiveView, View.OnClickListener {

    private ImageView mImg_live_cover;
    private EditText mEd_live_title;
    private Button mBtn_create_live;

    private CreateLiveContract.CreateLivePresenter presenter;
    private EditProfileHeadImgDialog picDialog;
    private String title;
    private String coverUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_live);

        initView();
        initListener();
        initPresenter();
    }

    private void initPresenter() {
        this.presenter = new CreateLivePresenterImpl(this);

    }

    private void initListener() {
        mBtn_create_live.setOnClickListener(this);
        mImg_live_cover.setOnClickListener(this);

    }

    private void initView() {
        mImg_live_cover = findViewById(R.id.mImg_live_cover);
        mEd_live_title = findViewById(R.id.mEd_live_title);
        mBtn_create_live = findViewById(R.id.mBtn_create_live);
    }

    @Override
    public void onCreateSuccess() {

    }

    @Override
    public void onCreateFail() {
        ToastUtils.show("创建房间失败，信息不能为空");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtn_create_live:
                title = mEd_live_title.getText().toString().trim();
                presenter.createLive(coverUrl,title);
                break;
            case R.id.mImg_live_cover:
                showPicSelect();
                break;
        }
    }

    private void showPicSelect() {
        picDialog = new EditProfileHeadImgDialog(this, R.style.common_dialog_style);
        picDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        PhotoHelper.getInstance(this).onActivityResult(requestCode,resultCode,data);
    }
}
