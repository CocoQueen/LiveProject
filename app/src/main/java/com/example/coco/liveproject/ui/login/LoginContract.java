package com.example.coco.liveproject.ui.login;

/**
 * Created by coco on 2018/1/1.
 */

public interface LoginContract {
    interface LoginView {
        void loginSuccess();

        void loginFailed(int errCode, String errMsg);

        void loginInfoEmpty();

        void loginInfoError();
    }

    interface LoginPresenter {
        void login(String user, String pass);
    }
}
