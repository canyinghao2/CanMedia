package com.canyinghao.canmedia.utils.MusicList;

import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.bean.music.AudioBean;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;


public class AudioUtils extends ContextWrapper {

    private ContentResolver cr;

    private static AudioUtils utils;
    public static AudioUtils getInstance(){

        if (utils==null){

            utils=new AudioUtils();
        }

        return utils;

    }

    private  AudioUtils() {
        super(App.getInstance());
    }


    public List<AudioBean> getLocalAudioListByName(String name) {
        List<AudioBean> musicList = new ArrayList<AudioBean>();
        ContentResolver resolver = getContentResolver();
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA};
        String selection = MediaStore.Audio.Media.DATA + " like ?";
        String[] selectionArgs = {"%" + name + "%"};
        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                AudioBean audioBean = new AudioBean();
                audioBean.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                audioBean.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                audioBean.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                musicList.add(audioBean);
            }
        }
        cursor.close();
        return musicList;
    }

    public List<AudioBean> getLocalAudioList() {
        List<AudioBean> musicList = new ArrayList<AudioBean>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                AudioBean audioBean = new AudioBean();
                audioBean.setId(cursor.getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                audioBean.setAlbum_id(cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));


                audioBean.setArtist(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));

                audioBean
                        .setName(cursor
                                .getString(cursor
                                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                audioBean.setPath(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                musicList.add(audioBean);
            }
        }
        cursor.close();
        return musicList;
    }


    public List<String> getLocalAudioPathList() {
        List<String> musicList = new ArrayList<String>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                musicList.add(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
            }
        }
        cursor.close();
        return musicList;
    }


    public String getAlbumArt(int albumid) {
        String strAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{android.provider.MediaStore.Audio.AlbumColumns.ALBUM_ART};
        Cursor cur = this.getContentResolver().query(
                Uri.parse(strAlbums + "/" + Integer.toString(albumid)),
                projection, null, null, null);
        String strPath = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            strPath = cur.getString(0);
        }
        cur.close();
        cur = null;
        return strPath;
    }


    public void removeAudioFromPlaylist(int album_id, String playlistId) {


        try {
            App.getInstance().getDbUtils().delete(AudioBean.class, WhereBuilder.b("album_id", "=", album_id).and("playlistId", "=", playlistId));
        } catch (DbException e) {
            e.printStackTrace();
        }


    }


    public void addMediaToPlaylist(AudioBean bean, String playlistId) {

        try {
            long count = App.getInstance().getDbUtils().count(Selector.from(AudioBean.class).where("album_id", "=", bean.getAlbum_id()).and("playlistId", "=", playlistId));
            if (count > 0) {

                PhoneHelper.getInstance().show(getResources().getString(R.string.hint_music_exit));

                return;
            }

            bean.setPlaylistId(playlistId);
            App.getInstance().getDbUtils().saveBindingId(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }


    }


    public List<AudioBean> getAudioListByPlaylistId(String playlistId) {

        List<AudioBean> mList = null;
        try {
            mList = App.getInstance().getDbUtils().findAll(Selector.from(AudioBean.class).where("playlistId", "=", playlistId));
        } catch (DbException e) {
            e.printStackTrace();
        }


        return mList;
    }

    public long getAudioListCountByPlaylistId(String playlistId) {

       long count=0;
        try {
            count = App.getInstance().getDbUtils().count(Selector.from(AudioBean.class).where("playlistId", "=", playlistId));
        } catch (DbException e) {
            e.printStackTrace();
        }


        return count;
    }








}
