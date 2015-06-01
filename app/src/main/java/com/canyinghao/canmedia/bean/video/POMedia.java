package com.canyinghao.canmedia.bean.video;

import java.io.File;


public class POMedia {

    public long _id;
    /**
     * 视频标题
     */

    public String title;
    /**
     * 视频标题拼音
     */

    public String title_key;
    /**
     * 视频路径
     */

    public String path;
    /**
     * 最后一次访问时间
     */

    public long last_access_time;
    /**
     * 最后一次修改时间
     */

    public long last_modify_time;
    /**
     * 视频时长
     */

    public int duration;
    /**
     * 视频播放进度
     */

    public int position;
    /**
     * 视频缩略图路径
     */

    public String thumb_path;
    /**
     * 文件大小
     */

    public long file_size;
    /**
     * 视频宽度
     */

    public int width;
    /**
     * 视频高度
     */

    public int height;
    /**
     * MIME类型
     */
    public String mime_type;
    /**
     * 0 本地视频 1 网络视频
     */
    public int type = 0;

    /**
     * 文件状态0 - 10 分别代表 下载 0-100%
     */
    public int status = -1;
    /**
     * 文件临时大小 用于下载
     */
    public long temp_file_size = -1L;

    public POMedia() {

    }

    public POMedia(File f) {
        title = f.getName();
        path = f.getAbsolutePath();
        last_modify_time = f.lastModified();
        file_size = f.length();
    }

    public POMedia(String path, String mimeType) {
        this(new File(path));
        this.mime_type = mimeType;
    }
}
