package com.example.coco.liveproject.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.ui.register.RegisterActivity;
import com.example.coco.liveproject.utils.ToastUtils;

public class LoginActivity extends Activity implements View.OnClickListener, LoginContract.LoginView {

    private EditText mEd_pass;
    private EditText mEd_user;
    private TextView mTv_regist;
    private Button mBtn_login;
    private LoginContract.LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        initPresenter();
    }

    private void initPresenter() {
        this.presenter = new LoginPresenterImpl(this);
    }

    private void initListener() {
        mBtn_login.setOnClickListener(this);
        mTv_regist.setOnClickListener(this);
    }

    private void initView() {
        mEd_pass = findViewById(R.id.mEd_pass);
        mEd_user = findViewById(R.id.mEd_user);
        mTv_regist = findViewById(R.id.mTv_regist);
        mBtn_login = findViewById(R.id.mBtn_login);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtn_login:
                String user = mEd_user.getText().toString().trim();
                String pass = mEd_pass.getText().toString().trim();
                presenter.login(user, pass);
                break;
            case R.id.mTv_regist:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess() {
        ToastUtils.show("登录成功");
    }

    @Override
    public void loginFailed(int errCode, String errMsg) {
        ToastUtils.show("登录失败"+errCode+errMsg);
    }

    @Override
    public void loginInfoEmpty() {
        ToastUtils.show("账号或者密码不能为空");

    }

    @Override
    public void loginInfoError() {
        ToastUtils.show("账号或者密码不能低于8位");

    }
}
