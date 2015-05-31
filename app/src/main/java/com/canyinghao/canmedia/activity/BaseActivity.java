package com.canyinghao.canmedia.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.canyinghao.canmedia.R;
import com.canyinghao.otherlibrary.SystemBarTintManager;

import de.greenrobot.event.EventBus;

public class BaseActivity extends ActionBarActivity {
    public Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        context = this;


        EventBus.getDefault().register(this);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);

        // 设置一个颜色给系统栏
        tintManager.setTintColor(getResources().getColor(R.color.blue_gray_500));
        // 设置一个样式背景给导航栏
        tintManager.setNavigationBarTintColor(getResources().getColor(R.color.blue_gray_500));
        // 设置一个状态栏资源
        tintManager.setStatusBarTintColor(getResources().getColor(R.color.blue_gray_500));


    }

    public void onEventMainThread(Intent intent) {

    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
