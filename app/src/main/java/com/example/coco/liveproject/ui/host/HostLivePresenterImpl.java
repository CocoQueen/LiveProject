package com.example.coco.liveproject.ui.host;

import com.example.coco.liveproject.app.AppConstant;
import com.example.coco.liveproject.app.LiveApplication;
import com.example.coco.liveproject.base.BaseOnRequestComplete;
import com.example.coco.liveproject.model.live.LiveHelper;
import com.example.coco.liveproject.utils.OkHttpHelper;
import com.tencent.TIMUserProfile;

import java.util.HashMap;

/**
 * Created by coco on 2018/1/11.
 */

public class HostLivePresenterImpl implements HostLiveContract.HostLivePresenter {
    HostLiveContract.HostLiveView view;
    HostLiveActivity activity;

    public HostLivePresenterImpl(HostLiveContract.HostLiveView view) {
        this.view = view;
        activity = (HostLiveActivity) view;
    }


    @Override
    public void createHost(int roomId) {
        LiveHelper.getInstance(activity).createRoom(roomId+"");

    }

    @Override
    public void quitHost(int roomId) {
        TIMUserProfile profile = LiveApplication.getApp().getUserProfile().getProfile();
        String userId = profile.getIdentifier();
        HashMap<String, String> map = new HashMap<>();
        map.put("action","quit");
        map.put("userId",userId);
        map.put("roomId",roomId+"");
        OkHttpHelper.getInstance().postString(AppConstant.HOST, map, new BaseOnRequestComplete<String>() {
            @Override
            public void onSuccess(String s) {
                activity.finish();
            }
        });

    }
}
