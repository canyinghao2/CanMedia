package com.canyinghao.canmedia.view.musicview;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canmedia.App;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.bean.music.AudioBean;
import com.canyinghao.canmedia.utils.PlayerEngine.PlayerEngineImpl;
import com.canyinghao.otherlibrary.ProgressWheel;

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
    ImageView head;


    private AudioBean bean;


    // ���������ʾ�����̳�TextView����дondraw������
    // ����һ���̲߳��ϵĵ���ondraw����ȥ��������д�ļ̳���TextView������
    // �������д�˸�������view��= =��������ص�


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


                        break;
                    case R.id.random:
                        App.getInstance().getPlayerEngine().setPlaybackMode(PlayerEngineImpl.PlaybackMode.SHUFFLE);
                        break;
                    case R.id.cycle:
                        App.getInstance().getPlayerEngine().setPlaybackMode(PlayerEngineImpl.PlaybackMode.REPEAT);
                        break;
                    case R.id.order:
                        App.getInstance().getPlayerEngine().setPlaybackMode(PlayerEngineImpl.PlaybackMode.NORMAL);
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


    private void getBitmap(ImageView albumImage, AudioBean bean) {

        if (bean == null) {
            return;
        }


        Bitmap bm = null;
        String albumArt = getAlbumArt(bean.getAlbum_id());
        if (albumArt == null) {
            albumImage.setBackgroundResource(R.drawable.ring_shap);
        } else {
            bm = BitmapFactory.decodeFile(albumArt);
            BitmapDrawable bmpDraw = new BitmapDrawable(bm);
            albumImage.setImageDrawable(bmpDraw);
        }



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




    public MusicView setPlayBean(AudioBean bean){




        this.bean=bean;
        tv_music.setText(bean.getName());
        tv_singer.setText(bean.getArtist());

        head.setImageResource(R.mipmap.ic_launcher);


        return  this;

    }



   public MusicView setPlayList(List<AudioBean> list){
        App.getInstance().getPlayerEngine().setMediaPlayList(list);

        return this;
    }


    public void play(){

        AudioBean playingBean=App.getInstance().getPlayerEngine().getPlayingBean();
        if (playingBean!=null&&App.getInstance().getPlayerEngine().isPlaying()&&playingBean.getPath().equals(bean.getPath())) {
            App.getInstance().getPlayerEngine().pause();

        } else if(playingBean!=null&&App.getInstance().getPlayerEngine().isPause()&&playingBean.getPath().equals(bean.getPath())){

            App.getInstance().getPlayerEngine().play();

        }else{

            App.getInstance().getPlayerEngine().setPlayingBean(bean);
            App.getInstance().getPlayerEngine().play();

            PhoneHelper.getInstance().show("play");
            setProgerss(pw_media);
        }


    }


    public  void up(){
        App.getInstance().getPlayerEngine().previous();

        AudioBean bean=    App.getInstance().getPlayerEngine().getPlayingBean();
        setPlayBean(bean);
        setProgerss(pw_media);
    }

    public  void down(){

        App.getInstance().getPlayerEngine().next();
        AudioBean bean=    App.getInstance().getPlayerEngine().getPlayingBean();
        setPlayBean(bean);
        setProgerss(pw_media);
    }




    public void showToWindowTop() {


        // ��������view WindowManager����
        mWM = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);



        WindowManager.LayoutParams params = new WindowManager.LayoutParams();


        // ����
        params.type = WindowManager.LayoutParams.TYPE_PHONE;

        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        // ����flag

        int flags = 40;
//        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // ���������WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE��������View�ղ���Back�����¼�
        params.flags = flags;

        // ����������������͸��������ʾΪ��ɫ
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL�������¼����ݵ�����Ĵ���
        // ���� FLAG_NOT_FOCUSABLE �������ڽ�Сʱ�������Ӧ��ͼ���ɲ��ɳ�����Ϊ�ɳ���
        // ���������flag�Ļ���homeҳ�Ļ�����������


//        params.gravity = Gravity.BOTTOM;
        params.width = PhoneHelper.getInstance().getScreenDisplayMetrics().widthPixels;
        params.height = BitmapHelper.getInstance().dp2Px(160);


        params.x = 0;
        params.y = PhoneHelper.getInstance().getScreenDisplayMetrics().heightPixels - BitmapHelper.getInstance().dp2Px(160);
        mWM.addView(this, params);// ������ص� ��WindowManager�ж���ղ����õ�ֵ
        // ֻ��addview�������ʾ��ҳ����ȥ��
        // ע�ᵽWindowManager win��Ҫ�ղ���������layout��wmParams�Ǹղ����õ�WindowManager������
        // Ч���ǽ�winע�ᵽWindowManager�в������Ĳ�����wmParams�����ö�



    }


    public void   reMoveWindow(){
        if (mWM!=null){

            mWM.removeView(this);
        }


    }

    public void showOrHide(){
//        if (getVisibility()==View.VISIBLE){
//
//            setVisibility(View.GONE);
//
//        }else{
//
//            setVisibility(View.VISIBLE);
//        }


    }

}
