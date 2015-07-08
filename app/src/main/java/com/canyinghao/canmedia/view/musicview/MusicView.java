package com.canyinghao.canmedia.view.musicview;

import android.content.Context;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canhelper.SPHepler;
import com.canyinghao.canmedia.Constant;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;
import com.canyinghao.canmedia.activity.music.MusicPlayActivity;
import com.canyinghao.canmedia.bean.music.AudioBean;
import com.canyinghao.canmedia.utils.MusicList.AudioUtils;
import com.canyinghao.canmedia.utils.PlayerEngine.PlayerEngineImpl;
import com.canyinghao.otherlibrary.ProgressWheel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by yangjian on 15/5/30.
 */
public class MusicView extends FrameLayout {

    @InjectView(R.id.v_model)
    View v_model;
    @InjectView(R.id.v_play)
    View v_play;

    @InjectView(R.id.pw_media)
    ProgressWheel pw_media;
    @InjectView(R.id.tv_music)
    TextView tv_music;
    @InjectView(R.id.tv_singer)
    TextView tv_singer;
    @InjectView(R.id.head)
    SimpleDraweeView head;
    @InjectView(R.id.play)
    ImageView play;


    private AudioBean bean;




    WindowManager mWM;        // WindowManager


    public MusicView(Context context) {
        super(context);
        initView();
    }

    public MusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.music_view, this);

        ButterKnife.inject(this);
        setWhiteBackGround();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return true;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return false;
//    }

    public void setWhiteBackGround() {
        v_model.setBackgroundResource(R.drawable.ova_shap);
        v_play.setBackgroundResource(R.drawable.ova_shap);

    }

    public void setTransparentBackGround() {
        v_model.setBackgroundResource(R.drawable.ring_shap);
        v_play.setBackgroundResource(R.drawable.ring_shap);

    }


    @OnClick({R.id.play, R.id.up, R.id.down, R.id.set, R.id.head, R.id.random, R.id.cycle, R.id.order})
    public void click(final View v) {


        AnimeHepler.getInstance().startAnimation(getContext(), v, AnimeHepler.getInstance().animSmall2Big(), new AnimeHepler.OnAnimEnd() {
            @Override
            public void end() {
                switch (v.getId()) {

                    case R.id.play:


                        play();


                        break;

                    case R.id.up:
                        up();
                        break;
                    case R.id.down:
                        down();
                        break;
                    case R.id.set:


                        break;
                    case R.id.head:

                        showOrHide(false);
                        IntentHelper.getInstance().showIntent(BaseBarActivity.activity, MusicPlayActivity.class);


                        break;
                    case R.id.random:
                        App.getInstance().getPlayerEngine().setPlaybackMode(PlayerEngineImpl.PlaybackMode.SHUFFLE);
                        PhoneHelper.getInstance().show("shuffle");
                        break;
                    case R.id.cycle:
                        App.getInstance().getPlayerEngine().setPlaybackMode(PlayerEngineImpl.PlaybackMode.REPEAT);
                        PhoneHelper.getInstance().show("REPEAT");
                        break;
                    case R.id.order:
                        App.getInstance().getPlayerEngine().setPlaybackMode(PlayerEngineImpl.PlaybackMode.NORMAL);
                        PhoneHelper.getInstance().show("NORMAL");
                        break;

                }


            }
        });


    }


    private String getAlbumArt(int album_id) {

        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = App.getInstance().getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),
                projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        cur = null;
        return album_art;
    }





    private void setProgerss(final ProgressWheel pw) {

        final Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                int position = App.getInstance().getPlayerEngine().getCurrentPosition();
                int duration = App.getInstance().getPlayerEngine().getDuration();
                if (duration > 0) {


                    if (pw != null) {
                        long pos = 360 * position / duration;
                        pw.setProgress((int) pos);

                    }

                }
            }

            ;
        };
        Timer mTimer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                if (!App.getInstance().getPlayerEngine().isPlaying())
                    return;
                if (App.getInstance().getPlayerEngine().isPlaying()) {
                    handler.sendEmptyMessage(0);
                }
            }
        };

        mTimer.schedule(timerTask, 0, 1000);


    }





    public MusicView setPlayListAddBean(List<AudioBean> list,AudioBean bean) {
        if (list!=null){
            App.getInstance().getPlayerEngine().setMediaPlayList(list);

        }

        this.bean = bean;
        tv_music.setText(bean.getName());
        tv_singer.setText(bean.getArtist());

        head.setImageResource(R.mipmap.ic_launcher);

        SPHepler.getInstance().setString(Constant.playList,bean.getPlaylistId());

        int p=list.indexOf(bean);
        SPHepler.getInstance().setInt(Constant.savePosition,p);

        return this;
    }


    public void play() {

        AudioBean playingBean = App.getInstance().getPlayerEngine().getPlayingBean();
        if (playingBean != null && App.getInstance().getPlayerEngine().isPlaying() && playingBean.getPath().equals(bean.getPath())) {
            App.getInstance().getPlayerEngine().pause();
            play.setImageResource(R.mipmap.ic_play_arrow_grey);
        } else if (playingBean != null && App.getInstance().getPlayerEngine().isPause() && playingBean.getPath().equals(bean.getPath())) {

            App.getInstance().getPlayerEngine().start();
            play.setImageResource(R.mipmap.ic_pause_grey);

        } else {
            play.setImageResource(R.mipmap.ic_pause_grey);
            App.getInstance().getPlayerEngine().setPlayingBean(bean);
            App.getInstance().getPlayerEngine().play();


            setProgerss(pw_media);

            String content=AudioUtils.getInstance().getAlbumArt(bean.getAlbum_id());
            if (!TextUtils.isEmpty(content)){

                PhoneHelper.getInstance().show(content);
                head.setImageURI(Uri.parse(content));
            }

            AudioUtils.getInstance().addMediaToPlaylist(bean, Constant.history);

        }


    }


    public void up() {
        App.getInstance().getPlayerEngine().previous();

        AudioBean bean = App.getInstance().getPlayerEngine().getPlayingBean();
        setPlayListAddBean(null, bean);
        setProgerss(pw_media);
    }

    public void down() {

        App.getInstance().getPlayerEngine().next();
        AudioBean bean = App.getInstance().getPlayerEngine().getPlayingBean();
        setPlayListAddBean(null, bean);
        setProgerss(pw_media);
    }


    public void showToWindowTop() {


        mWM = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        int flags = 40;
        params.flags = flags;
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.BOTTOM;
        params.width = PhoneHelper.getInstance().getScreenDisplayMetrics().widthPixels;
        params.height = BitmapHelper.getInstance().dp2Px(160);


//        params.x = 0;
//        params.y = PhoneHelper.getInstance().getScreenDisplayMetrics().heightPixels - BitmapHelper.getInstance().dp2Px(160);
        mWM.addView(this, params);


    }


    public void reMoveWindow() {
        if (mWM != null) {

            mWM.removeView(this);
        }


    }

    public void showOrHide(boolean isShow) {
        if (isShow){
            setVisibility(View.VISIBLE);


        }else{
            setVisibility(View.GONE);

        }


    }

}
