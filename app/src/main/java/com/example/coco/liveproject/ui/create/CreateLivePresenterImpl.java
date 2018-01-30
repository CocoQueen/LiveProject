package com.example.coco.liveproject.ui.create;

import android.content.Intent;
import android.text.TextUtils;

import com.example.coco.liveproject.app.AppConstant;
import com.example.coco.liveproject.app.LiveApplication;
import com.example.coco.liveproject.base.BaseOnRequestComplete;
import com.example.coco.liveproject.bean.HostRoomInfo;
import com.example.coco.liveproject.bean.UserProfile;
import com.example.coco.liveproject.ui.host.HostLiveActivity;
import com.example.coco.liveproject.utils.OkHttpHelper;
import com.tencent.TIMUserProfile;

import java.util.HashMap;

/**
 * Created by coco on 2018/1/11.
 */

public class CreateLivePresenterImpl implements CreateLiveContract.CreateLivePresenter {
    CreateLiveContract.CreateLiveView view;
    CreateLiveActivity activity;

    public CreateLivePresenterImpl(CreateLiveContract.CreateLiveView view) {
        this.view = view;
        activity = (CreateLiveActivity) view;
    }


    @Override
    public void createLive(String url, String liveName) {

        if (/*TextUtils.isEmpty(url) ||*/ TextUtils.isEmpty(liveName)) {
            view.onCreateFail();
        } else {
            //创建房间
            //先把房间名称、封面、主播id、主播昵称、主播头像（application中有缓存）
            //传给服务器，服务器返回信息中包含roomid
            requestRoomId(/*url,*/ liveName);
        }

    }

    //获取房间id
    private void requestRoomId(/*String cover,*/ String liveName) {
        HashMap<String, String> map = new HashMap<>();
        UserProfile profile = LiveApplication.getApp().getUserProfile();
        TIMUserProfile profile1 = profile.getProfile();

        map.put("action", "create");
        map.put("userId", profile1.getIdentifier());
        map.put("userAvatar", profile1.getFaceUrl());
        map.put("userName", profile1.getNickName());
        map.put("liveTitle", liveName);
//        map.put("liveCover", cover);

        OkHttpHelper.getInstance().postObject(AppConstant.HOST,map,new BaseOnRequestComplete<HostRoomInfo>(){

            @Override
            public void onSuccess(HostRoomInfo info) {
//                HostRoomInfo.DataBean data = hostRoomInfo.getData();
                if (info!=null){
                    int roomId = info.getData().getRoomId();
                    if (roomId!=0){
                        Intent intent = new Intent(activity, HostLiveActivity.class);
                        intent.putExtra("roomId",roomId);
                        activity.startActivity(intent);
                    }
                }else {
                    onEmpty();
                }

            }
        },HostRoomInfo.class);
    }
}
