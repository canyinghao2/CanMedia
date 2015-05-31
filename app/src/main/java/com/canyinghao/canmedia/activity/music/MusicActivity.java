package com.canyinghao.canmedia.activity.music;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canmedia.App;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;
import com.canyinghao.canmedia.bean.music.AudioBean;
import com.canyinghao.canmedia.utils.MusicList.AudioUtils;
import com.canyinghao.canmedia.view.musicview.MusicView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MusicActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    public static MusicView musicView;


    public static MusicView getMusicView() {

        return musicView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.inject(this);


        setToolbar(toolbar, R.mipmap.ic_arrow_back_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);


        AudioUtils audioDao = new AudioUtils(context);
        List<AudioBean> musicList = audioDao.getLocalAudioList();

        App.getInstance().getPlayerEngine().setMediaPlayList(musicList);
        AudioBean bean=musicList.get(0);








        musicView=   new MusicView(context);
        musicView.setPlayList(musicList).setPlayBean(bean);
        musicView.showToWindowTop();
    }

@OnClick({R.id.local_music,R.id.histroy_music,R.id.playlist_music})
    public void click(View v){

        switch (v.getId()){
            case R.id.local_music:


                IntentHelper.getInstance().showIntent(this,MusicLocalActivity.class);


                break;
            case R.id.histroy_music:
                break;
            case R.id.playlist_music:

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        musicView.reMoveWindow();
    }
}
