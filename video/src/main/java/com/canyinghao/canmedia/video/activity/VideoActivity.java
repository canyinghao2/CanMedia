package com.canyinghao.canmedia.video.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.canyinghao.canhelper.base.BaseActivity;
import com.canyinghao.canhelper.utils.PhoneHelper;
import com.canyinghao.canhelper.utils.SPHepler;
import com.canyinghao.canmedia.video.MyApp;
import com.canyinghao.canmedia.video.R;
import com.canyinghao.canmedia.video.adapter.LocalAdapter;
import com.canyinghao.canmedia.video.bean.POMedia;
import com.canyinghao.canmedia.video.service.MediaScannerService;
import com.canyinghao.canmedia.video.vitamio.LibsChecker;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;



public class VideoActivity extends BaseActivity implements AdapterView.OnItemClickListener, MediaScannerService.IMediaScannerObserver {
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    boolean isFirst=true;


    private MediaScannerService mMediaScannerService;

    private ServiceConnection mMediaScannerServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMediaScannerService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMediaScannerService = ((MediaScannerService.MediaScannerServiceBinder) service).getService();
            mMediaScannerService.addObserver(VideoActivity.this);
            //			Toast.makeText(ComponentServiceActivity.this, "Service绑定成功!", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictrue);
        ButterKnife.inject(this);


        if (!LibsChecker.checkVitamioLibs(this, R.string.init_decoders))
            return;


        //	首次运行，扫描SD卡
        if (SPHepler.getInstance().getInt(MyApp.PREF_KEY_FIRST)!=0) {
            isFirst=false;

        }

        setToolbar(toolbar, R.mipmap.ic_arrow_back_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);

        listView.setOnItemClickListener(this);
        if (isFirst){
            getApplicationContext().startService(new Intent(getApplicationContext(), MediaScannerService.class).putExtra(MediaScannerService.EXTRA_DIRECTORY, Environment.getExternalStorageDirectory().getAbsolutePath()));
            bindService(new Intent(getApplicationContext(), MediaScannerService.class), mMediaScannerServiceConnection, Context.BIND_AUTO_CREATE);

        }else{
            try {
                List list=  MyApp.getInstance().getDbUtils().findAll(POMedia.class);
                if (list!=null){
                    PhoneHelper.getInstance().show(list.size()+" video");

                    LocalAdapter  mAdapter = new LocalAdapter(context, list);
                    listView.setAdapter(mAdapter);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }

        }






    }

    List list=  new ArrayList();
    /**
     *
     * @param flag 0 开始扫描 1 正在扫描 2 扫描完成
     * @param media 扫描到的视频文件
     */
    @Override
    public void update(int flag, POMedia media) {
        //		Logger.i(flag + " " + media.path);


        switch (flag) {
            case MediaScannerService.SCAN_STATUS_START:

                break;
            case MediaScannerService.SCAN_STATUS_END://扫描完成

                PhoneHelper.getInstance().show(list.size()+" video");
                break;
            case MediaScannerService.SCAN_STATUS_RUNNING://扫到一个文件
                if (media != null) {
                    list.add(media);
                    try {
                        MyApp.getInstance().getDbUtils().saveBindingId(media);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final POMedia f = (POMedia) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("path", f.path);
        intent.putExtra("title", f.title);
        startActivity(intent);
    }


}
