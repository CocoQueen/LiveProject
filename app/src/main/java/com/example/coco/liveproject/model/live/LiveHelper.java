package com.example.coco.liveproject.model.live;

import android.content.Context;

import com.example.coco.liveproject.utils.ToastUtils;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by coco on 2018/1/11.
 */

public class LiveHelper {
    Context context;
    static LiveHelper helper;
    OkHttpClient okHttpClient;

    public LiveHelper(Context context) {
        this.context = context;
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public static LiveHelper getInstance(Context context) {
        if (helper == null) {
            helper = new LiveHelper(context);
        }
        return helper;
    }

    public void createRoom(String roomNum) {
        int roomId = DemoFunc.getIntValue(roomNum, -1);
        if (-1 == roomId) {
            ToastUtils.show("房间号不合法");
            return;
        }
        ILVLiveRoomOption option = new ILVLiveRoomOption(ILiveLoginManager.getInstance().getMyUserId())
//                .setRoomMemberStatusLisenter(this)
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .controlRole(Constants.ROLE_MASTER)
                .autoFocus(true);
        UserInfo.getInstance().setRoom(roomId);
        ILVLiveManager.getInstance().createRoom(UserInfo.getInstance().getRoom(),
                option, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        afterCreate();
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        if (module.equals(ILiveConstants.Module_IMSDK) && 10021 == errCode) {
                            // 被占用，改加入
//                            showChoiceDlg();
                        } else {
                            ToastUtils.show("创建房间失败");
                        }
                    }
                });
    }

    private void afterCreate() {
        UserInfo.getInstance().setRoom(ILiveRoomManager.getInstance().getRoomId());
        UserInfo.getInstance().writeToCache(context);
    }
}
