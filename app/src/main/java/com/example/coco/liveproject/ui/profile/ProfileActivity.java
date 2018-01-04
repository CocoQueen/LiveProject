package com.example.coco.liveproject.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.UserProfile;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, ProfileContract.ProfileView {

    private ConstraintLayout mCon_profile;
    private ImageView mImg_profie_headImg;
    private TextView mTv_profile_nickname;
    private TextView mTv_profile_fans;
    private TextView mTv_profile_fork;
    private TextView mTv_profile_grade;
    private TextView mTv_profile_usernum;
    private ImageView mImg_profile_sex;
    private ProfileContract.ProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        initListener();
        initPresenter();

    }

    private void initPresenter() {
       presenter=new ProfilePresenterImpl(this);
       presenter.getUserProfile();
    }

    private void initListener() {
        mCon_profile.setOnClickListener(this);
    }

    private void initView() {
        mCon_profile = findViewById(R.id.mCon_profile);
        mImg_profie_headImg = findViewById(R.id.mImg_profie_headImg);
        mTv_profile_nickname = findViewById(R.id.mTv_profile_nickname);
        mTv_profile_fans = findViewById(R.id.mTv_profile_fans);
        mTv_profile_fork = findViewById(R.id.mTv_profile_fork);
        mTv_profile_grade = findViewById(R.id.mTv_profile_grade);
        mTv_profile_usernum = findViewById(R.id.mTv_profile_usernum);
        mImg_profile_sex = findViewById(R.id.mImg_profile_sex);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mCon_profile:
                Intent intent = new Intent(this,EditProfileActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void updateProfile(UserProfile profile) {

    }

    @Override
    public void updateProfileError() {

    }
}
