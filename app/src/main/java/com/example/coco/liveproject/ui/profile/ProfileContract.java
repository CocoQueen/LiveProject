package com.example.coco.liveproject.ui.profile;

import com.example.coco.liveproject.bean.UserProfile;

/**
 * Created by coco on 2018/1/3.
 */

public interface ProfileContract {
    interface ProfileView{
        void updateProfile(UserProfile profile);
        void updateProfileError();
    }
    interface ProfilePresenter{
        void getUserProfile();
    }
}
