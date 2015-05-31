package com.canyinghao.canmedia.activity.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;
import com.canyinghao.canmedia.activity.music.MusicActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isSwipe=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        setToolbar(toolbar, R.mipmap.ic_list_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                finish();
                IntentHelper.getInstance().showIntent(context, MusicActivity.class);

            }
        }, null);




    }




}
