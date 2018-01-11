package com.example.coco.liveproject.ui.login;

import android.content.Intent;
import android.text.TextUtils;

import com.example.coco.liveproject.model.ProfileHelper;
import com.example.coco.liveproject.ui.profile.ProfileActivity;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by coco on 2018/1/1.
 */

public class LoginPresenterImpl implements LoginContract.LoginPresenter {
    private static final String TAG = "LoginPresenterImpl";
    LoginContract.LoginView view;
    LoginActivity loginActivity;

    public LoginPresenterImpl(LoginContract.LoginView view) {
        this.view = view;
        this.loginActivity = (LoginActivity) view;
    }

    @Override
    public void login(String user, String pass) {
        //判空
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            view.loginInfoEmpty();
            return;
        }
        if (user.length() < 8 || pass.length() < 8) {
            view.loginInfoError();
            return;
        }
        loginMethod(user, pass);
    }

    //真正的登陆操作
    private void loginMethod(String user, String pass) {
        ILiveLoginManager.getInstance().tlsLoginAll(user, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.loginSuccess();
                //获取用户信息
                getUserInfo();
                //登录成功后的跳转
                loginActivity.startActivity(new Intent(loginActivity, ProfileActivity.class));


            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                view.loginFailed(errCode, errMsg);
            }
        });
    }

    //获取用户信息
    private void getUserInfo() {
        new ProfileHelper().getSelfProfile(loginActivity, null);
    }
}
