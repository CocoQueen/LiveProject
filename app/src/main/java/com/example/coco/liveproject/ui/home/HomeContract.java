package com.example.coco.liveproject.ui.home;

import com.example.coco.liveproject.adapter.HomeInfoAdapter;

/**
 * Created by coco on 2018/1/12.
 */

public interface HomeContract {
    interface HomeView{
        void setHomeInfoAdapter(HomeInfoAdapter adapter);
        void updataHomeInfoAdapter(HomeInfoAdapter adapter);

    }
    interface HomePresenter{
        void getHomeList(int page);

    }
}
