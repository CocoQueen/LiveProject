package com.example.coco.liveproject.ui.watcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.coco.liveproject.R;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVLiveManager;

public class WatcherActivity extends AppCompatActivity {


    private AVRootView mAv_watcher;
    private int roomId;
    private String hostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcher);
        initView();
        getSomething();
    }

    private void getSomething() {
        Intent intent = getIntent();
        roomId = intent.getIntExtra("roomId", -1);
        hostId = intent.getStringExtra("hostId");
        watcher(roomId+"");
    }

    private void watcher(String id) {

    }

    private void initView() {
        mAv_watcher = findViewById(R.id.mAv_watcher);
        ILVLiveManager.getInstance().setAvVideoView(mAv_watcher);

    }
}
