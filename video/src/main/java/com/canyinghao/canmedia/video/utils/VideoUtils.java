package com.canyinghao.canmedia.video.utils;


import com.canyinghao.canmedia.video.bean.POMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjian on 15/6/1.
 */
public class VideoUtils {


    /** 递归查找视频 */
    public static List<POMedia> eachAllMedias(File f) {
        List<POMedia> list=new ArrayList<>();
        if (f != null && f.exists() && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : f.listFiles()) {
                    //					Logger.i(f.getAbsolutePath());
                    if (file.isDirectory()) {
                        //忽略.开头的文件夹
                        if (!file.getAbsolutePath().startsWith("."))
                            eachAllMedias(file);
                    } else if (file.exists() && file.canRead() && FileUtils.isVideo(file)) {



                        POMedia media=  new POMedia(file);
                        media.last_access_time = System.currentTimeMillis();

                        //提取缩略图
                        //			extractThumbnail(media);
                        media.mime_type = FileUtils.getMimeType(media.path);
                        list.add(media);

                    }
                }
            }
        }
        return list;
    }
}
