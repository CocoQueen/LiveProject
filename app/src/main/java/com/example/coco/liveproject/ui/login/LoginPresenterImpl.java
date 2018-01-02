package com.example.coco.liveproject.ui.login;

import android.text.TextUtils;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by coco on 2018/1/1.
 */

public class LoginPresenterImpl implements LoginContract.LoginPresenter {
        LoginContract.LoginView view;
        LoginActivity loginActivity;

    public LoginPresenterImpl(LoginContract.LoginView view) {
        this.view = view;
        this.loginActivity = (LoginActivity) view;
    }

    @Override
    public void login(String user, String pass) {
        if (TextUtils.isEmpty(user)||TextUtils.isEmpty(pass)){
            view.loginInfoEmpty();
            return;
        }
        if (user.length()<8||pass.length()<8){
            view.loginInfoError();
            return;
        }
        loginMethod(user,pass);
    }

    private void loginMethod(String user,String pass) {
        ILiveLoginManager.getInstance().tlsLoginAll(user, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.loginSuccess();

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                view.loginFailed();
            }
        });
    }
}
