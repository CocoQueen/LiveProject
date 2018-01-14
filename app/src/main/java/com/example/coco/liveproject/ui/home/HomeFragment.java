package com.example.coco.liveproject.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.adapter.HomeInfoAdapter;
import com.example.coco.liveproject.ui.main.MainActivity;
import com.example.coco.liveproject.utils.ToastUtils;

/**
 * Created by coco on 2018/1/10.
 */

public class HomeFragment extends Fragment implements HomeContract.HomeView {
    MainActivity activity;
    public SwipeRefreshLayout srl;
    HomeContract.HomePresenter presenter;
    int page;
    private RecyclerView mRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null, false);
        initView(view);
        initPresenter();
        return view;
    }

    private void initPresenter() {
        presenter = new HomePresenterImpl(this);
    }

    private void initView(View view) {
        srl = view.findViewById(R.id.mSrl);
        mRv = view.findViewById(R.id.mRv);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        activity = (MainActivity) getActivity();
        activity.initToolbar("首页");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPullToRefresh();

    }

    private void initPullToRefresh() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(0);
            }
        });
    }

    private void initData(int page) {
        presenter.getHomeList(page);
    }

    @Override
    public void setHomeInfoAdapter(HomeInfoAdapter adapter) {
        if (adapter != null) {
            mRv.setAdapter(adapter);
        } else {
            ToastUtils.show("数据为空");
        }

    }

    @Override
    public void updataHomeInfoAdapter(HomeInfoAdapter adapter) {
        if (adapter != null) {
            mRv.setAdapter(adapter);
        } else {
            ToastUtils.show("数据为空");
        }

    }
}



