package com.example.coco.liveproject.ui.host;

/**
 * Created by coco on 2018/1/11.
 */

public interface HostLiveContract {

    interface HostLiveView {

    }

    interface HostLivePresenter {
        void createHost(int roomId);
        void quitHost(int roomId);

    }
}
