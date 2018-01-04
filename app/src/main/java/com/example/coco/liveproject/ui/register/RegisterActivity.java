package com.example.coco.liveproject.ui.register;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.utils.ToastUtils;

public class RegisterActivity extends Activity implements View.OnClickListener, RegisterContract.RegisterView {

    private EditText mEd_use_register;
    private EditText mEd_pass_register;
    private EditText mEd_repass_register;
    private Button mBtn_register;
    private Toolbar toolbar;
    private RegisterContract.RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
//        initToolbar();//v7包错误
        initPresenter();

    }

    private void initPresenter() {
        this.presenter = new RegisterPresenterImpl(this);
    }

    private void initToolbar() {
        toolbar.setTitle("注册");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        mBtn_register.setOnClickListener(this);
    }

    private void initView() {
        mEd_use_register = findViewById(R.id.mEd_use_register);
        mEd_pass_register = findViewById(R.id.mEd_pass_register);
        mEd_repass_register = findViewById(R.id.mEd_repass_register);
        mBtn_register = findViewById(R.id.mBtn_register);
        toolbar = findViewById(R.id.mTool);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtn_register:
                String user = mEd_use_register.getText().toString().trim();
                String pass = mEd_pass_register.getText().toString().trim();
                String repass = mEd_repass_register.getText().toString().trim();
                presenter.regist(user, pass, repass);
                break;
        }
    }

    @Override
    public void registSuccess() {
        ToastUtils.show("注册成功");
        finish();
    }

    @Override
    public void registError() {
        ToastUtils.show("注册失败");
    }

    @Override
    public void registInfoEmpty() {
        ToastUtils.show("账号或者密码不能为空");
    }

    @Override
    public void registInfoError() {
        ToastUtils.show("账号或者密码不能低于8位");
    }

    @Override
    public void registRepassError() {
        ToastUtils.show("两次输入密码不一致");
    }
}
