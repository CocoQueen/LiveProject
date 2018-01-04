package com.example.coco.liveproject.model;

import android.app.Activity;

import com.example.coco.liveproject.app.LiveApplication;
import com.example.coco.liveproject.bean.UserProfile;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

/**
 * Created by coco on 2018/1/3.
 */

public class ProfileHelper {
    public interface OnProfileGet {
        void onGet(UserProfile profile);

        void noGet();
    }

    public void getSelfProfile(Activity act, final OnProfileGet onProfileGet) {
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {


            @Override
            public void onError(int i, String s) {
                if (onProfileGet != null) {
                    onProfileGet.noGet();
                }

            }

            @Override
            public void onSuccess(TIMUserProfile profile) {
                UserProfile mProfile = new UserProfile();
                if (profile != null) {
                    mProfile.setProfile(profile);
                }
                //附加信息
//                Map<String,byte[]>customMap=profile.getCustomInfo();
//                byte[] grade = customMap.get(ProfileInfoCustom.INFO_GRADE);
//                byte[] send = customMap.get(ProfileInfoCustom.INFO_SEND);
//                byte[] receive = customMap.get(ProfileInfoCustom.INFO_RECEIVE);
//                byte[] xingzuo = customMap.get(ProfileInfoCustom.INFO_XINGZUO);
//                if (grade!=null){
//                    mProfile.setGrade(Integer.parseInt(new String(grade)));
//                }
//                if (send!=null){
//                    mProfile.setGrade(Integer.parseInt(new String(send)));
//                }
//                if (receive!=null){
//                    mProfile.setGrade(Integer.parseInt(new String(receive)));
//                }
//                if (xingzuo!=null){
//                    mProfile.setGrade(Integer.parseInt(new String(xingzuo)));
//                }

                LiveApplication.getApp().setUserProfile(mProfile);
                if (onProfileGet != null) {
                    onProfileGet.onGet(mProfile);
                }
            }
        });
    }
}
