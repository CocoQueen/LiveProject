package com.example.coco.liveproject.app;

import android.app.Application;

import com.example.coco.liveproject.model.MessageObservable;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveLog;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

/**
 * Created by coco on 2018/1/1.
 */

public class LiveApplication extends Application {
    static LiveApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initLiveSDK();
    }

    private void initLiveSDK() {
        if (MsfSdkUtils.isMainProcess(this)) {    // 仅在主线程初始化
            // 初始化LiveSDK
            ILiveLog.setLogLevel(ILiveLog.TILVBLogLevel.DEBUG);
            ILiveSDK.getInstance().initSdk(this, 1400059305, 18395);
            ILVLiveManager.getInstance().init(new ILVLiveConfig()
                    .setLiveMsgListener(MessageObservable.getInstance()));
        }
    }

    public static LiveApplication getApp() {
        return app;
    }
}
