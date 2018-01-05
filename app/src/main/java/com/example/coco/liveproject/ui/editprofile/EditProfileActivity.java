package com.example.coco.liveproject.ui.editprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.bean.UserProfile;
import com.example.coco.liveproject.utils.ToastUtils;
import com.example.coco.liveproject.widget.EditProfileDialog;
import com.example.coco.liveproject.widget.EditProfileItem;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendGenderType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, EditProfileContract.EditProfileView {

    private Toolbar mTool_mEp;
    private EditProfileItem mEp_headImg;
    private EditProfileItem mEp_area;
    private EditProfileItem mEp_home_town;
    private EditProfileItem mEp_nickname;
    private EditProfileItem mEp_usernum;
    private EditProfileItem mEp_sign;
    private EditProfileItem mEp_sex;
    private EditProfileItem mEp_xingzuo;
    private EditProfileItem mEp_job;

    private EditProfileContract.EditProfilePresenter presenter;
    private EditProfileDialog editProfileDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initView();
        initListener();
        initPresenter();
    }

    private void initPresenter() {
        presenter = new EditProfilePresenterImpl(this);
        presenter.getUserInfo();

    }

    private void initListener() {
        mTool_mEp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEp_headImg.setOnClickListener(this);
        mEp_area.setOnClickListener(this);
        mEp_home_town.setOnClickListener(this);
        mEp_job.setOnClickListener(this);
        mEp_nickname.setOnClickListener(this);
        mEp_sex.setOnClickListener(this);
        mEp_sign.setOnClickListener(this);
        mEp_xingzuo.setOnClickListener(this);

    }

    private void initView() {
        mTool_mEp = findViewById(R.id.mTool_mEp);
        mEp_headImg = findViewById(R.id.mEp_headImg);
        mEp_area = findViewById(R.id.mEp_area);
        mEp_home_town = findViewById(R.id.mEp_home_town);
        mEp_nickname = findViewById(R.id.mEp_nickname);
        mEp_usernum = findViewById(R.id.mEp_usernum);
        mEp_sign = findViewById(R.id.mEp_sign);
        mEp_sex = findViewById(R.id.mEp_sex);
        mEp_xingzuo = findViewById(R.id.mEp_xingzuo);
        mEp_job = findViewById(R.id.mEp_job);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //头像
            case R.id.mEp_headImg:
                break;
            //昵称
            case R.id.mEp_nickname:
                showNicknameEidtDialog();

                break;
            //性别
            case R.id.mEp_sex:
                break;
            //活跃地带
            case R.id.mEp_area:
                break;
            //家乡
            case R.id.mEp_home_town:
                break;
            //个性签名
            case R.id.mEp_sign:
                break;
            //星座
            case R.id.mEp_xingzuo:
                break;
            //职业
            case R.id.mEp_job:
                break;

        }

    }

    private void showNicknameEidtDialog() {
        editProfileDialog = new EditProfileDialog(new EditProfileDialog.onEditChangedListener() {
            @Override
            public void onChanged(String value) {
                //更改服务器上的内容
                TIMFriendshipManager.getInstance().setNickName(value, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        updateInfoError();//更新失败
                    }

                    @Override
                    public void onSuccess() {
                        presenter.onUpdateInfoSuccess();//更新成功
                        editProfileDialog.dismiss();
                        editProfileDialog = null;

                    }
                });

            }

            @Override
            public void onEmpty() {
                editProfileDialog.dismiss();
                editProfileDialog=null;
                updateInfoError();

            }
        }, this);
        editProfileDialog.setTitleAndIcon("请输入您的昵称", R.mipmap.logo);
        editProfileDialog.show();
    }

    @Override
    public void updateView(UserProfile profile) {
        if (profile != null) {
            TIMUserProfile mProfile = profile.getProfile();
            String nickName = mProfile.getNickName();
            String faceUrl = mProfile.getFaceUrl();
            String identifier = mProfile.getIdentifier();
            TIMFriendGenderType gender = mProfile.getGender();
            String location = mProfile.getLocation();
            String selfSignature = mProfile.getSelfSignature();
            if (!TextUtils.isEmpty(nickName)) {
                mEp_nickname.setValue(nickName);
            }
            if (!TextUtils.isEmpty(identifier)) {
                mEp_usernum.setValue(identifier);
            }
            if (!TextUtils.isEmpty(location)) {
                mEp_area.setValue(location);
            }
            if (!TextUtils.isEmpty(selfSignature)) {
                mEp_sign.setValue(selfSignature);
            }
            if (gender == TIMFriendGenderType.Male) {
                mEp_sex.setValue("男");
            } else if (gender == TIMFriendGenderType.Female) {
                mEp_sex.setValue("女");
            } else {
                mEp_sex.setValue("未知");
            }

        }

    }

    @Override
    public void onGetInfoFailed() {

    }

    @Override
    public void updateInfoError() {
        ToastUtils.show("信息更新失败");

    }

    @Override
    public void updateInfoSuccess() {
        ToastUtils.show("信息更新成功");
        presenter.getUserInfo();

    }
}