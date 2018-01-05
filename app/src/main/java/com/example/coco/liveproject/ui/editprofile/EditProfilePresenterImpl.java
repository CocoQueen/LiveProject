package com.example.coco.liveproject.ui.editprofile;

import com.example.coco.liveproject.bean.UserProfile;
import com.example.coco.liveproject.model.ProfileHelper;

/**
 * Created by coco on 2018/1/4.
 */

public class EditProfilePresenterImpl implements EditProfileContract.EditProfilePresenter {
    EditProfileContract.EditProfileView view;
    EditProfileActivity activity;

    public EditProfilePresenterImpl(EditProfileContract.EditProfileView view) {
        this.view = view;
        activity = (EditProfileActivity) view;
    }

    @Override
    public void getUserInfo() {
        //首先从app拿，如果没有再自己获取
        ProfileHelper.getInstance().getSelfProfile(activity, new ProfileHelper.OnProfileGet() {
            @Override
            public void onGet(UserProfile profile) {
                view.updateView(profile);
            }

            @Override
            public void noGet() {
                view.onGetInfoFailed();

            }
        });

    }

    @Override
    public void onUpdateInfoSuccess() {
        //将application中存储的信息更新
        ProfileHelper.getInstance().resetAllMsg(new ProfileHelper.OnProfileGet() {
            @Override
            public void onGet(UserProfile profile) {
                view.updateView(profile);
            }

            @Override
            public void noGet() {

            }
        });

    }
}
