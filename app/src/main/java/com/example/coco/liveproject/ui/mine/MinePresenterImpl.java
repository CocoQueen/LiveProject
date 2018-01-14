package com.example.coco.liveproject.ui.mine;

import com.example.coco.liveproject.bean.UserProfile;
import com.example.coco.liveproject.model.ProfileHelper;

/**
 * Created by coco on 2018/1/12.
 */

public class MinePresenterImpl implements MineContract.MinePresenter {
    MineContract.MineView view;
    MineFragment fragment;

    public MinePresenterImpl(MineContract.MineView view) {
        this.view = view;
        fragment = (MineFragment) view;
    }

    @Override
    public void getUserProfile() {
        new ProfileHelper().getSelfProfile(fragment.getActivity(), new ProfileHelper.OnProfileGet() {
            @Override
            public void onGet(UserProfile profile) {
                view.updateProfile(profile);

            }

            @Override
            public void noGet() {
                view.updateProfileError();

            }
        });

    }
}
