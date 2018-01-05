package com.example.coco.liveproject.ui.editprofile;

import com.example.coco.liveproject.bean.UserProfile;

/**
 * Created by coco on 2018/1/4.
 */

public interface EditProfileContract {
    interface EditProfileView {
        void updateView(UserProfile profile);

        void onGetInfoFailed();

        void updateInfoError();

        void updateInfoSuccess();
    }

    interface EditProfilePresenter {
        void getUserInfo();

        void onUpdateInfoSuccess();
    }
}
