package com.example.coco.liveproject.app;

import android.app.Application;

import com.example.coco.liveproject.bean.UserProfile;
import com.example.coco.liveproject.model.MessageObservable;
import com.example.coco.liveproject.qiniu.QiniuUploadHelper;
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
    UserProfile profile;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initLiveSDK();//初始化直播
        initQiniuSdk();//初始化七牛
    }

    private void initQiniuSdk() {
        QiniuUploadHelper.init(QiNiuConfig.SPACENAME, QiNiuConfig.SK, QiNiuConfig.AK);
    }

    private void initLiveSDK() {
        if (MsfSdkUtils.isMainProcess(this)) {    // 仅在主线程初始化
            // 初始化LiveSDK
            ILiveLog.setLogLevel(ILiveLog.TILVBLogLevel.DEBUG);
            ILiveSDK.getInstance().initSdk(this, 1400059305, 18395);
            ILVLiveManager.getInstance().init(new ILVLiveConfig()
                    .setLiveMsgListener(MessageObservable.getInstance()));

//            long type = ProfileInfoCustom.ALL_BASE_INFO;
//            List<String> customList = new ArrayList<>();
//            customList.add(ProfileInfoCustom.INFO_FANS);
//            customList.add(ProfileInfoCustom.INFO_FORK);
//            customList.add(ProfileInfoCustom.INFO_GRADE);
//            customList.add(ProfileInfoCustom.INFO_RECEIVE);
//            customList.add(ProfileInfoCustom.INFO_SEND);
//            customList.add(ProfileInfoCustom.INFO_XINGZUO);
//            TIMManager.getInstance().initFriendshipSettings(type, customList);
        }
    }

    public static LiveApplication getApp() {
        return app;
    }

    public void setUserProfile(UserProfile mprofile) {
        if (mprofile != null) {
            profile = mprofile;
        }

    }

    public UserProfile getUserProfile() {
        return profile;
    }
}
