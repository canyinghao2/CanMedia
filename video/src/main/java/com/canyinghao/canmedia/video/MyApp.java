package com.canyinghao.canmedia.video;

import android.os.Environment;

import com.canyinghao.canhelper.base.App;
import com.canyinghao.canmedia.video.utils.FileUtils;

/**
 * Created by yangjian on 15/7/8.
 */
public class MyApp extends App {


    private static MyApp myApp;

    /** OPlayer SD卡缓存路径 */
    public static final String OPLAYER_CACHE_BASE = Environment.getExternalStorageDirectory() + "/oplayer";
    /** 视频截图缓冲路径 */
    public static final String OPLAYER_VIDEO_THUMB = OPLAYER_CACHE_BASE + "/thumb/";
    /** 首次扫描 */
    public static final String PREF_KEY_FIRST = "application_first";

    @Override
    public void onCreate() {
        super.onCreate();


        init();
    }

    private void init() {
        //创建缓存目录
        FileUtils.createIfNoExists(OPLAYER_CACHE_BASE);
        FileUtils.createIfNoExists(OPLAYER_VIDEO_THUMB);
    }
}
