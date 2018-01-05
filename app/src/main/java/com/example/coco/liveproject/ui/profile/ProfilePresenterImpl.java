package com.example.coco.liveproject.ui.profile;

import com.example.coco.liveproject.bean.UserProfile;
import com.example.coco.liveproject.model.ProfileHelper;

/**
 * Created by coco on 2018/1/3.
 */

public class ProfilePresenterImpl implements ProfileContract.ProfilePresenter {
    ProfileContract.ProfileView view;
    ProfileActivity profileActivity;

    public ProfilePresenterImpl(ProfileContract.ProfileView view) {
        this.view = view;
        profileActivity = (ProfileActivity) view;
    }

    @Override
    public void getUserProfile() {
//        UserProfile profile = LiveApplication.getApp().getUserProfile();
//        if (profile != null) {
//            view.updateProfile(profile);
//        } else {
            new ProfileHelper().getSelfProfile(profileActivity, new ProfileHelper.OnProfileGet() {
                @Override
                public void onGet(UserProfile profile) {
                    view.updateProfile(profile);
                }

                @Override
                public void noGet() {
                    //拉取信息失败
                    view.updateProfileError();

                }
            });
//        }

    }
}
