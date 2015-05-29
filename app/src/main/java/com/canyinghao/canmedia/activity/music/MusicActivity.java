package com.canyinghao.canmedia.activity.music;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MusicActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        setToolbar(toolbar, R.mipmap.ic_arrow_back_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);
    }




}
