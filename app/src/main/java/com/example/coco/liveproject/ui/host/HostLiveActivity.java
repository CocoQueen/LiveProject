package com.example.coco.liveproject.ui.host;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.coco.liveproject.R;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVLiveManager;

public class HostLiveActivity extends AppCompatActivity implements View.OnClickListener, HostLiveContract.HostLiveView {

    private Toolbar mTool_host;
    private AVRootView mAv_room;
    private ImageView mImg_close;
    private ImageView mImg_swich_camera;

    private HostLivePresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_live);

        initView();
        initToolbar();
        initListener();
        initPresenter();
        initCreateHost();
    }

    private void initCreateHost() {
        presenter.createHost();
    }

    private void initPresenter() {
        this.presenter = new HostLivePresenterImpl(this);
    }

    private void initListener() {
        mImg_close.setOnClickListener(this);
    }

    private void initToolbar() {
        mTool_host.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTool_host = findViewById(R.id.mTool_host);
        mAv_room = findViewById(R.id.mAv_room);
        mImg_close = findViewById(R.id.mImg_close);
        mImg_swich_camera = findViewById(R.id.mImg_swich_camera);

        ILVLiveManager.getInstance().setAvVideoView(mAv_room);//添加avrootview
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mImg_close:
                finish();
                break;
            case R.id.mImg_swich_camera:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ILVLiveManager.getInstance().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ILVLiveManager.getInstance().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ILVLiveManager.getInstance().onDestory();
    }
}
