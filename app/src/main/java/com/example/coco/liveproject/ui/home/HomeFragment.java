package com.example.coco.liveproject.ui.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.ui.main.MainActivity;

/**
 * Created by coco on 2018/1/10.
 */

public class HomeFragment extends Fragment{
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity= (MainActivity) getActivity();
        activity.initToolbar("首页");
    }
}
