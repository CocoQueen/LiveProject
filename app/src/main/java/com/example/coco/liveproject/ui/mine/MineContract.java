package com.example.coco.liveproject.ui.mine;

import com.example.coco.liveproject.bean.UserProfile;

/**
 * Created by coco on 2018/1/12.
 */

public interface MineContract {
    interface MineView{
        void updateProfile(UserProfile profile);
        void updateProfileError();
    }
    interface MinePresenter{
        void getUserProfile();
    }
}
