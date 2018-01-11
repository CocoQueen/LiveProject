package com.example.coco.liveproject.ui.host;

import com.example.coco.liveproject.model.live.LiveHelper;

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
    public void createHost() {
        LiveHelper.getInstance(activity).createRoom("00000001");

    }
}
