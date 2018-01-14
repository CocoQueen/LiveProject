package com.example.coco.liveproject.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.UserProfile;
import com.example.coco.liveproject.ui.editprofile.EditProfileActivity;
import com.example.coco.liveproject.ui.main.MainActivity;
import com.example.coco.liveproject.utils.ImageUtils;
import com.example.coco.liveproject.utils.ToastUtils;
import com.tencent.TIMFriendGenderType;

/**
 * Created by coco on 2018/1/10.
 */

public class MineFragment extends Fragment implements MineContract.MineView, View.OnClickListener {

    MainActivity activity;
    MineContract.MinePresenter presenter;
    private ConstraintLayout mCon_profile;
    private ImageView mImg_profie_headImg;
    private TextView mTv_profile_nickname;
    private TextView mTv_profile_fans;
    private TextView mTv_profile_fork;
    private TextView mTv_profile_grade;
    private TextView mTv_profile_usernum;
    private ImageView mImg_profile_sex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, null, false);
        initView(view);

        return view;
    }

    private void initPresenter() {
        presenter = new MinePresenterImpl(this);
    }


    private void initView(View view) {


        mCon_profile = view.findViewById(R.id.mCon_profile);
        mImg_profie_headImg = view.findViewById(R.id.mImg_profie_headImg);

        mTv_profile_nickname = view.findViewById(R.id.mTv_profile_nickname);
        mTv_profile_fans = view.findViewById(R.id.mTv_profile_fans);
        mTv_profile_fork = view.findViewById(R.id.mTv_profile_fork);
        mTv_profile_grade = view.findViewById(R.id.mTv_profile_grade);
        mTv_profile_usernum = view.findViewById(R.id.mTv_profile_usernum);
        mImg_profile_sex = view.findViewById(R.id.mImg_profile_sex);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListener();
        initPresenter();
        activity = (MainActivity) getActivity();
        activity.initToolbar("我");
    }

    private void initListener() {
        mCon_profile.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getUserProfile();
    }

    @Override
    public void updateProfile(UserProfile profile) {
        //更新view，设置显示信息
        if (!TextUtils.isEmpty(profile.getProfile().getFaceUrl())) {
            ImageUtils.getInstance().loadCircle(profile.getProfile().getFaceUrl(), mImg_profie_headImg);
        } else {
            ImageUtils.getInstance().loadCircle(R.mipmap.logo, mImg_profie_headImg);
        }
        if (!TextUtils.isEmpty(profile.getProfile().getNickName())) {
            mTv_profile_nickname.setText(profile.getProfile().getNickName());
        } else {
            mTv_profile_nickname.setText("暂无");
        }
        if (!TextUtils.isEmpty(profile.getProfile().getIdentifier())) {
            mTv_profile_usernum.setText("直播号：" + profile.getProfile().getIdentifier());
        }
        if (profile.getProfile().getGender() == TIMFriendGenderType.Male) {
            mImg_profile_sex.setBackgroundResource(R.mipmap.male);
        } else if (profile.getProfile().getGender() == TIMFriendGenderType.Female) {
            mImg_profile_sex.setBackgroundResource(R.mipmap.female);
        } else {
            mImg_profile_sex.setImageResource(R.mipmap.ic_launcher);
        }
        mTv_profile_grade.setText(profile.getGrade() + "");
        mTv_profile_fans.setText(profile.getFans() + "");
        mTv_profile_fork.setText(profile.getFork() + "");
    }

    @Override
    public void updateProfileError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show("获取信息失败");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mCon_profile:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
