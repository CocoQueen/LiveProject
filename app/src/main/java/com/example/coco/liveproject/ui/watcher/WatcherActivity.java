package com.example.coco.liveproject.ui.watcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.model.live.Constants;
import com.example.coco.liveproject.model.live.DemoFunc;
import com.example.coco.liveproject.utils.ToastUtils;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;

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
        watcher(roomId + "");
    }

    private void watcher(String id) {
        int roomId = DemoFunc.getIntValue(id, -1);
        if (-1 == roomId) {
            ToastUtils.show("房间号不合法");
            finish();
            return;
        }
        ILVLiveRoomOption option = new ILVLiveRoomOption("")
                .controlRole(Constants.ROLE_GUEST) //是一个浏览者
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .autoCamera(false)
                .autoMic(false);
        ILVLiveManager.getInstance().joinRoom(roomId,
                option, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        //成功的时候怎么办

                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        ToastUtils.show("加入房间失败，正在退出...");
                        //退出房间
                        finish();
                    }
                });

    }

    private void initView() {
        mAv_watcher = findViewById(R.id.mAv_watcher);
        ILVLiveManager.getInstance().setAvVideoView(mAv_watcher);

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
