package com.example.coco.liveproject.ui.register;

/**
 * Created by coco on 2018/1/2.
 */

public interface RegisterContract {
    interface RegisterView{
        void registSuccess();
        void registError(int errCode, String errMsg);
        void registInfoEmpty();
        void registInfoError();
        void registRepassError();

    }
    interface RegisterPresenter{
        void regist(String user,String pass,String repass);

    }

}
