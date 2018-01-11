package com.example.coco.liveproject.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.UserProfile;
import com.example.coco.liveproject.ui.create.CreateLiveActivity;
import com.example.coco.liveproject.ui.editprofile.EditProfileActivity;
import com.example.coco.liveproject.utils.ImageUtils;
import com.example.coco.liveproject.utils.ToastUtils;
import com.tencent.TIMFriendGenderType;

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
        presenter = new ProfilePresenterImpl(this);
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

        //TODO
        Button button =findViewById(R.id.mBtn_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,CreateLiveActivity.class));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getUserProfile();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mCon_profile:
                Intent intent = new Intent(this, EditProfileActivity.class);
                startActivity(intent);
                break;
        }
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
      runOnUiThread(new Runnable() {
          @Override
          public void run() {
              ToastUtils.show("获取信息失败");
          }
      });

    }
}
