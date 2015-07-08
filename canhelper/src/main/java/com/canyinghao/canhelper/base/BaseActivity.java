package com.canyinghao.canhelper.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import de.greenrobot.event.EventBus;

public class BaseActivity extends AppCompatActivity {
    public Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        context = this;


        EventBus.getDefault().register(this);



    }

    public void onEventMainThread(Intent intent) {

    }

    public void setToolbar(final Toolbar toolbar,int icon,String title,String subTitle,View.OnClickListener navigation,Toolbar.OnMenuItemClickListener menuItemClickListener){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(icon);
        toolbar.setTitle(title);
        toolbar.setSubtitle(subTitle);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.black_text_26));
//        toolbar.setSubtitleTextColor(getResources().getColor(R.color.black_text_26));


        toolbar.setNavigationOnClickListener(navigation);

        if (menuItemClickListener!=null){
            toolbar.setOnMenuItemClickListener(menuItemClickListener);

        }else{

            toolbar.setOnMenuItemClickListener( new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {




                    return true;
                }
            });

        }


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
//        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
