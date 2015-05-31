package com.canyinghao.canmedia.utils.MusicList;

import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canmedia.App;
import com.canyinghao.canmedia.bean.music.AudioBean;
import com.canyinghao.canmedia.bean.music.PlaylistBean;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PlaylistUtils {


    public static void createPlaylist(String name) {


        try {

           long count= App.getInstance().getDbUtils().count(Selector.from(PlaylistBean.class).where("name","=",name));
            if (count>0){

                PhoneHelper.getInstance().show("此歌单已存在");
                return;
            }
            PlaylistBean bean = new PlaylistBean();
            bean.setName(name);
            bean.setCountAudio(0);
            bean.setAddDate(new Date());
            bean.setUpdateDate(new Date());
            App.getInstance().getDbUtils().saveBindingId(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public static List<PlaylistBean> getAllPlaylist() {
        List<PlaylistBean> list = new ArrayList<>();
        try {
            List<PlaylistBean> mList = App.getInstance().getDbUtils().findAll(PlaylistBean.class);

            if (mList != null) {
                list.addAll(mList);
            }

            for (PlaylistBean bean: list){

               List<AudioBean> aList = App.getInstance().getDbUtils().findAll(Selector.from(AudioBean.class).where("playlistId","=",bean.getName()));

                bean.setCountAudio(aList.size());
                bean.setList(aList);


            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static void removePlaylist(String name) {

        try {
            App.getInstance().getDbUtils().delete(PlaylistBean.class, WhereBuilder.b("name","=",name));
            App.getInstance().getDbUtils().delete(AudioBean.class, WhereBuilder.b("playlistId", "=", name));

        } catch (DbException e) {
            e.printStackTrace();
        }


    }

}
