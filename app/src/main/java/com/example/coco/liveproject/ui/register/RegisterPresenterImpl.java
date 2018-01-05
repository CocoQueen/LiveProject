package com.example.coco.liveproject.ui.register;

import android.text.TextUtils;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by coco on 2018/1/2.
 */

public class RegisterPresenterImpl implements RegisterContract.RegisterPresenter {
    RegisterContract.RegisterView view;
    RegisterActivity registerActivity;

    public RegisterPresenterImpl(RegisterContract.RegisterView view) {
        this.view = view;
        registerActivity = (RegisterActivity) view;
    }

    @Override
    public void regist(String user, String pass, String repass) {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)) {
            view.registInfoEmpty();
            return;
        }
        if (user.length() < 8 || pass.length() < 8 || repass.length() < 8) {
            view.registInfoError();
            return;
        }
        if (!pass.equals(repass)) {
            view.registRepassError();
            return;
        }
        registMethod(user, pass);

    }

    private void registMethod(String user, String pass) {
        ILiveLoginManager.getInstance().tlsRegister(user, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.registSuccess();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                view.registError(errCode,errMsg);
            }
        });
    }
}
