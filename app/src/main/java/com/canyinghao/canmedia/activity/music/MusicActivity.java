package com.canyinghao.canmedia.activity.music;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.SPHepler;
import com.canyinghao.canmedia.App;
import com.canyinghao.canmedia.Constant;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;
import com.canyinghao.canmedia.bean.music.AudioBean;
import com.canyinghao.canmedia.utils.MusicList.AudioUtils;
import com.canyinghao.canmedia.utils.MusicList.PlaylistUtils;
import com.canyinghao.canmedia.view.musicview.MusicView;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MusicActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;


    @InjectView(R.id.histroy_count)
    TextView histroy_count;
    @InjectView(R.id.mylove_count)
    TextView mylove_count;
    @InjectView(R.id.local_count)
    TextView local_count;

    public static MusicView musicView;


    public static MusicView getMusicView() {

        return musicView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.inject(this);

        PlaylistUtils.createPlaylist("我的最爱");

        setToolbar(toolbar, R.mipmap.ic_arrow_back_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);






        musicView=   new MusicView(getApplicationContext());
        musicView.showToWindowTop();
        Long localCount=  AudioUtils.getInstance().getAudioListCountByPlaylistId(Constant.local);
        if (localCount>0){
            local_count.setText(localCount+"首");


          String playList=  SPHepler.getInstance().getString(Constant.playList);
           int savePosition= SPHepler.getInstance().getInt(Constant.savePosition);
            if (TextUtils.isEmpty(playList)){

                playList=Constant.local;
            }
            List<AudioBean> musicList=   AudioUtils.getInstance().getAudioListByPlaylistId(playList);

            if (musicList!=null&&musicList.size()>0){

                if (musicList.size()<=savePosition){

                    savePosition=0;
                }
                AudioBean bean=musicList.get(savePosition);
                musicView.setPlayListAddBean(musicList,bean);

            }


        }else{
            List<AudioBean> musicList = AudioUtils.getInstance().getLocalAudioList();

            local_count.setText(musicList.size()+"首");
            try {
                App.getInstance().getDbUtils().saveBindingIdAll(musicList);
            } catch (DbException e) {
                e.printStackTrace();
            }
            AudioBean bean=musicList.get(0);

            musicView.setPlayListAddBean(musicList,bean);



        }



        Long historyCount=  AudioUtils.getInstance().getAudioListCountByPlaylistId(Constant.history);
        histroy_count.setText(historyCount+"首");
        Long myloveCount=  AudioUtils.getInstance().getAudioListCountByPlaylistId(Constant.mylove);
        mylove_count.setText(myloveCount+"首");












    }

@OnClick({R.id.local_music,R.id.histroy_music,R.id.playlist_music})
    public void click(View v){

        switch (v.getId()){
            case R.id.local_music:


                IntentHelper.getInstance().showIntent(this,MusicLocalActivity.class);


                break;
            case R.id.histroy_music:

                IntentHelper.getInstance().showIntent(this,MusicListActivity.class,new String[]{Constant.history},new String[]{Constant.history});


                break;
            case R.id.playlist_music:

                IntentHelper.getInstance().showIntent(this,MusicListActivity.class,new String[]{Constant.playList},new String[]{Constant.playList});


                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        musicView.showOrHide(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        musicView.showOrHide(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        musicView.reMoveWindow();
    }
}
