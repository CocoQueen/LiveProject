package com.example.coco.liveproject.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.coco.liveproject.R;
import com.example.coco.liveproject.ui.home.HomeFragment;
import com.example.coco.liveproject.ui.mine.MineFragment;

public class MainActivity extends FragmentActivity {


    private FragmentManager fm;
    private Toolbar mTool_main;
    private FragmentTabHost mTabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        initView();
        initToolbar("");

    }

    private void initView() {
        mTool_main = findViewById(R.id.mTool_main);
        mTabhost = findViewById(R.id.mTabhost);
        mTabhost.setup(this, fm, R.id.mTab_content);

        TabHost.TabSpec tabSpec_home = mTabhost.newTabSpec("home").setIndicator(getView("home"));
        TabHost.TabSpec tabSpec_create = mTabhost.newTabSpec("create").setIndicator(getView("create"));
        TabHost.TabSpec tabSpec_mine = mTabhost.newTabSpec("mine").setIndicator(getView("mine"));
        mTabhost.addTab(tabSpec_home, HomeFragment.class, null);
        mTabhost.addTab(tabSpec_create, HomeFragment.class, null);
        mTabhost.addTab(tabSpec_mine, MineFragment.class, null);
        mTabhost.getTabWidget().setDividerDrawable(R.color.transprant);
    }

    private View getView(String str) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabspec, null, false);
        ImageView mImg_tabspec = view.findViewById(R.id.mImg_tabspec);
        if ("home".equals(str)) {
            mImg_tabspec.setBackgroundResource(R.mipmap.logo);
        } else if ("mine".equals(str)) {
            mImg_tabspec.setBackgroundResource(R.mipmap.female);
        } else if ("live".equals(str)) {
            mImg_tabspec.setBackgroundResource(R.mipmap.male);
        }
        return view;
    }

    public void initToolbar(String str) {
        mTool_main.setTitle(str);

    }
}
