package com.example.coco.liveproject.ui.home;

import android.content.Intent;

import com.example.coco.liveproject.adapter.HomeInfoAdapter;
import com.example.coco.liveproject.app.AppConstant;
import com.example.coco.liveproject.base.BaseOnRequestComplete;
import com.example.coco.liveproject.bean.HomeInfo;
import com.example.coco.liveproject.ui.watcher.WatcherActivity;
import com.example.coco.liveproject.utils.OkHttpHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by coco on 2018/1/12.
 */

public class HomePresenterImpl implements HomeContract.HomePresenter {
    HomeContract.HomeView view;
    HomeFragment fragment;
    private ArrayList<HomeInfo.DataBean> list;

    public HomePresenterImpl(HomeContract.HomeView view) {
        this.view = view;
        fragment = (HomeFragment) view;
    }

    @Override
    public void getHomeList(int page) {
        String url = AppConstant.HOST;

        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getList");
        map.put("pageIndex", page + "");

        OkHttpHelper.getInstance().postObject(url, map, new BaseOnRequestComplete<HomeInfo>() {

            private HomeInfoAdapter adapter;

            @Override
            public void onSuccess(HomeInfo homeInfo) {
                if (homeInfo.getData() != null) {
                    list = homeInfo.getData();
                    adapter = new HomeInfoAdapter(fragment.getActivity(), list);
                    adapter.setOnHomeInfoListener(new HomeInfoAdapter.OnHomeInfoListener() {
                        @Override
                        public void onHomeInfoListener(HomeInfo.DataBean bean) {
                            Intent intent = new Intent(fragment.getActivity(), WatcherActivity.class);
                            intent.putExtra("roomId", bean.getRoomId());
                            intent.putExtra("hostId", bean.getUserId());
                            fragment.getActivity().startActivity(intent);
                        }
                    });
                    view.setHomeInfoAdapter(adapter);

                } else {
                    onEmpty();
                }
                fragment.srl.setRefreshing(false);
            }

            @Override
            public void onEmpty() {
                super.onEmpty();
                fragment.srl.setRefreshing(false);
            }

            @Override
            public void onError() {
                super.onError();
                fragment.srl.setRefreshing(false);
            }

            @Override
            public void onFailed() {
                super.onFailed();
                fragment.srl.setRefreshing(false);
            }
        }, HomeInfo.class);

    }
}
