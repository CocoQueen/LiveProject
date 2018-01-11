package com.example.coco.liveproject.ui.create;

/**
 * Created by coco on 2018/1/11.
 */

public interface CreateLiveContract {
    interface CreateLiveView {
        void onCreateSuccess();

        void onCreateFail();

    }

    interface CreateLivePresenter {
        void createLive(String url, String liveName);

    }

}
