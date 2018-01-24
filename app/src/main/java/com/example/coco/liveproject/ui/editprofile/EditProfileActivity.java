package com.example.coco.liveproject.ui.editprofile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.app.QiNiuConfig;
import com.example.coco.liveproject.bean.UserProfile;
import com.example.coco.liveproject.model.PhotoHelper;
import com.example.coco.liveproject.qiniu.QiniuUploadHelper;
import com.example.coco.liveproject.utils.ToastUtils;
import com.example.coco.liveproject.widget.editprofile.EditProfileDialog;
import com.example.coco.liveproject.widget.editprofile.EditProfileHeadImgDialog;
import com.example.coco.liveproject.widget.editprofile.EditProfileItem;
import com.example.coco.liveproject.widget.editprofile.EditProfileNomalDialog;
import com.example.coco.liveproject.widget.editprofile.EditProfileSexDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendGenderType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;

import org.json.JSONObject;

import java.io.File;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, EditProfileContract.EditProfileView {

    private static final String TAG = "EditProfileActivity";
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
    private EditProfileSexDialog sexDialog;
    private EditProfileNomalDialog areaDialog;
    private EditProfileNomalDialog signDialog;
    private EditProfileHeadImgDialog headImgDialog;
//    private SharedPreferences sp;
//    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
//        sp = getSharedPreferences("isfirstenter", MODE_PRIVATE);
//        edit = sp.edit();
//        edit.putBoolean("isfirst", false);
//        edit.commit();
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
                showHeadImgDialog();
                break;
            //昵称
            case R.id.mEp_nickname:
                showNicknameEidtDialog();
                break;
            //性别
            case R.id.mEp_sex:
                showSexEditDialog();
                break;
            //活跃地带
            case R.id.mEp_area:
                showAreaEditDialog();
                break;
            //家乡
            case R.id.mEp_home_town:
                break;
            //个性签名
            case R.id.mEp_sign:
                showSignEditDialog();
                break;
            //星座
            case R.id.mEp_xingzuo:
                break;
            //职业
            case R.id.mEp_job:
                break;

        }

    }

    private void showHeadImgDialog() {
        headImgDialog = new EditProfileHeadImgDialog(this, R.style.common_dialog_style);
        headImgDialog.show();
    }

    private void showSignEditDialog() {
        signDialog = new EditProfileNomalDialog(this, new EditProfileNomalDialog.OnProfileNomalChangedListener() {
            @Override
            public void onChangeSucess(String value) {
                TIMFriendshipManager.getInstance().setSelfSignature(value, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess() {
                        signDialog.hide();
                        signDialog = null;
                        presenter.onUpdateInfoSuccess();

                    }
                });
            }

            @Override
            public void onChangeError() {

            }
        });
        signDialog.setTitleAndIcon("请输入您的个性签名", R.mipmap.logo);
        signDialog.show();
    }

    private void showAreaEditDialog() {
        areaDialog = new EditProfileNomalDialog(this, new EditProfileNomalDialog.OnProfileNomalChangedListener() {
            @Override
            public void onChangeSucess(String value) {
                TIMFriendshipManager.getInstance().setLocation(value, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        updateInfoError();
                    }

                    @Override
                    public void onSuccess() {
                        areaDialog.hide();
                        areaDialog = null;
                        presenter.onUpdateInfoSuccess();

                    }
                });
            }

            @Override
            public void onChangeError() {
                areaDialog.hide();
                areaDialog = null;
                updateInfoError();

            }
        });
        areaDialog.setTitleAndIcon("请输入您的活跃地带", R.mipmap.logo);
        areaDialog.show();
    }

    private void showSexEditDialog() {
        sexDialog = new EditProfileSexDialog(new EditProfileSexDialog.OnProfileSexChangedListener() {
            private TIMFriendGenderType type;

            @Override
            public void onChangedSuccess(String value) {
                if ("男".equals(value)) {
                    type = TIMFriendGenderType.Male;
                } else if ("女".equals(value)) {
                    type = TIMFriendGenderType.Female;
                } else {
                    type = TIMFriendGenderType.Unknow;
                }
                TIMFriendshipManager.getInstance().setGender(type, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess() {
                        sexDialog.hide();
                        sexDialog = null;
                        presenter.onUpdateInfoSuccess();

                    }
                });

            }

            @Override
            public void onChangedError() {

            }
        }, this);
        sexDialog.setTitleAndIcon("请选择您的性别：", R.mipmap.logo);
        sexDialog.show();
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
                editProfileDialog = null;
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
            if (!TextUtils.isEmpty(faceUrl)) {
                mEp_headImg.setHeadImg(faceUrl);
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoHelper.getInstance(this).onActivityResult(requestCode, resultCode, data, PhotoHelper.CropType.HeadImg, new PhotoHelper.onEditHeadImgListener() {
            @Override
            public void onReady(Uri uri) {
                mEp_headImg.setHeadImg(uri);
                headImgDialog.dismiss();

                String path = uri.getPath();
                File file = new File(path);
                String absolutePath = file.getAbsolutePath();
                String name = file.getName();
                try {
                    QiniuUploadHelper.uploadPic(absolutePath, name, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                updataNetHeadImgInfo(QiNiuConfig.HOST + key);
                            } else {
                                Log.e(TAG, "complete: upload fail");
                            }
                            Log.e(TAG, "complete: " + key + ",\r\n" + info + ",\r\n" + response);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updataNetHeadImgInfo(String url) {
        TIMFriendshipManager.getInstance().setFaceUrl(url, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                updateInfoError();
            }

            @Override
            public void onSuccess() {
                presenter.onUpdateInfoSuccess();

            }
        });

    }
}
