package com.example.coco.liveproject.ui.create;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.app.QiNiuConfig;
import com.example.coco.liveproject.model.PhotoHelper;
import com.example.coco.liveproject.qiniu.QiniuUploadHelper;
import com.example.coco.liveproject.utils.ImageUtils;
import com.example.coco.liveproject.utils.ToastUtils;
import com.example.coco.liveproject.widget.EditProfileHeadImgDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.io.File;

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
        PhotoHelper.getInstance(this).onActivityResult(requestCode, resultCode, data, PhotoHelper.CropType.Cover, new PhotoHelper.onEditHeadImgListener() {
            @Override
            public void onReady(Uri outUri) {
                mImg_live_cover.setVisibility(View.VISIBLE);
                mImg_live_cover.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageUtils.getInstance().loadPic(outUri,mImg_live_cover);
                picDialog.dismiss();
                String path = outUri.getPath();
                File file = new File(path);
                if (!file.exists()){
                    ToastUtils.show("获取本地图片失败");
                }else {
                    try {
                        QiniuUploadHelper.uploadPic(file.getAbsolutePath(), file.getName(), new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                coverUrl = QiNiuConfig.HOST + key;
                                ToastUtils.show("封面设置成功");
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
