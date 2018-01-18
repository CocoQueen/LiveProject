package com.example.coco.liveproject.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.ui.create.CreateLiveActivity;
import com.example.coco.liveproject.ui.home.HomeFragment;
import com.example.coco.liveproject.ui.mine.MineFragment;

public class MainActivity extends FragmentActivity {
    private FragmentManager fm;
    private Toolbar mTool_main;
    private FragmentTabHost mTabhost;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        initView();
        upDataView();
        initToolbar("");
        initPermission();
    }

    private void upDataView() {
//        Intent intent = getIntent();
//        if (intent!=null){
//            String extra = intent.getStringExtra("from");
//            if ("profileActivity".equals(extra)){
//                mTabhost.setCurrentTab(2);
//            }
//        }
    }

    private void initView() {
        mTool_main = findViewById(R.id.mTool_main);
        mTabhost = findViewById(R.id.mTabhost);
        mTabhost.setup(this, fm, R.id.mTab_content);

        TabHost.TabSpec tabSpec_home = mTabhost.newTabSpec("home").setIndicator(getView("home"));
        TabHost.TabSpec tabSpec_create = mTabhost.newTabSpec("create").setIndicator(getView("create"));
        TabHost.TabSpec tabSpec_mine = mTabhost.newTabSpec("mine").setIndicator(getView("mine"));

        mTabhost.addTab(tabSpec_home, HomeFragment.class, null);
        mTabhost.addTab(tabSpec_create, null, null);
        mTabhost.addTab(tabSpec_mine, MineFragment.class, null);

        mTabhost.getTabWidget().setDividerDrawable(R.color.transprant);

        mTabhost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateLiveActivity.class));
            }
        });
    }

    private View getView(String str) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabspec, null, false);
        ImageView mImg_tabspec = view.findViewById(R.id.mImg_tabspec);
        if ("home".equals(str)) {
            mImg_tabspec.setBackgroundResource(R.mipmap.logo);
        } else if ("create".equals(str)) {
            mImg_tabspec.setBackgroundResource(R.mipmap.male);
        } else if ("mine".equals(str)) {
            mImg_tabspec.setBackgroundResource(R.mipmap.pass);
        }
        return view;
    }

    public void initToolbar(String str) {
        mTool_main.setTitle(str);
    }

    private void initPermission() {
        String[] strings = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        boolean isAllGranted = checkPermissionAllGranted(strings);
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            return;
        }
        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
