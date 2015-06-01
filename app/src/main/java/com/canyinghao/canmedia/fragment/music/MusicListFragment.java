package com.canyinghao.canmedia.fragment.music;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canmedia.Constant;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.adapter.music.MusicListAdapter;
import com.canyinghao.canmedia.adapter.music.SingerListAdapter;
import com.canyinghao.canmedia.bean.music.AudioBean;
import com.canyinghao.canmedia.bean.music.PlaylistBean;
import com.canyinghao.canmedia.fragment.BaseFragment;
import com.canyinghao.canmedia.utils.MusicList.AudioUtils;
import com.canyinghao.canmedia.utils.MusicList.PlaylistUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by yangjian on 15/5/30.
 */
public class MusicListFragment extends BaseFragment {

    public static final String TYPE = "type";

    public static final int music = 0;
    public static final int singer = 1;
    public static final int folder = 2;

    @InjectView(R.id.lisView)
    ListView listView;

    int type;
    CustomContentObserver observer;

    private List<AudioBean> list;


    public static BaseFragment getInstance(Bundle bundle) {

        BaseFragment bf = new MusicListFragment();


        bf.setArguments(bundle);


        return bf;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         observer=    new CustomContentObserver(null);
        // 注册一个内容观察者 观察数据库内容的改变
       context. getContentResolver().registerContentObserver(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true,observer
               );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_list, container, false);
        ButterKnife.inject(this, v);

        AnimeHepler.getInstance().setAnimationEmptyView(context,listView,null,null);


        list = new ArrayList<AudioBean>();
        if (mBundle != null) {

            PlaylistBean bean= (PlaylistBean) mBundle.getSerializable(Constant.bean);
            String playList= mBundle.getString(Constant.playList);
            String history= mBundle.getString(Constant.history);
            if (bean!=null){
             list.addAll(  bean.getList()) ;


                MusicListAdapter   adapter = new MusicListAdapter(context, list);

                listView.setAdapter(adapter);

            }else if(!TextUtils.isEmpty(playList)){


                SingerListAdapter adapter= new SingerListAdapter(context,PlaylistUtils.getAllPlaylist());
                listView.setAdapter(adapter);

            }else if(!TextUtils.isEmpty(history)) {
                List<AudioBean> list=AudioUtils.getInstance().getAudioListByPlaylistId(Constant.history);

                MusicListAdapter   adapter = new MusicListAdapter(context, list);

                listView.setAdapter(adapter);
            } else{
                type = mBundle.getInt(TYPE);
                getListData();
            }

        }


        AnimeHepler.getInstance().setNoDataEmptyView(context,listView,R.mipmap.ic_delete_grey,null,null);
        return v;
    }





    private void getListData() {

        switch (type) {


            case music:

                list.addAll(AudioUtils.getInstance().getLocalAudioList());


                MusicListAdapter   adapter = new MusicListAdapter(context, list);

                listView.setAdapter(adapter);


                break;
            case singer:
                list.addAll(AudioUtils.getInstance().getLocalAudioList());
                Map<String, PlaylistBean> map = new HashMap<String, PlaylistBean>();
                List pList = new ArrayList<PlaylistBean>();
                for (AudioBean bean : list) {


                    if (map.containsKey(bean.getArtist())) {

                        PlaylistBean ppBean = map.get(bean.getArtist());
                        ppBean.setCountAudio(ppBean.getCountAudio() + 1);
                        ppBean.getList().add(bean);

                    } else {
                        PlaylistBean pBean = new PlaylistBean();

                        pBean.setCountAudio(1);
                        pBean.setName(bean.getArtist());
                        pBean.getList().add(bean);
                        map.put(bean.getArtist(), pBean);
                    }


                }

                pList.addAll(map.values())  ;

                SingerListAdapter adapter1= new SingerListAdapter(context,pList);
                listView.setAdapter(adapter1);


                break;
            case folder:
                list.addAll(AudioUtils.getInstance().getLocalAudioList());
                Map<String, PlaylistBean> mapFolder = new HashMap<String, PlaylistBean>();
                List ListFolder = new ArrayList<PlaylistBean>();
                for (AudioBean bean : list) {

                    File file=  new File(bean.getPath());
                   String parent= file.getParent();
                    if (mapFolder.containsKey(parent)) {

                        PlaylistBean ppBean = mapFolder.get(parent);
                        ppBean.setCountAudio(ppBean.getCountAudio() + 1);
                        ppBean.getList().add(bean);

                    } else {
                        PlaylistBean pBean = new PlaylistBean();

                        pBean.setCountAudio(1);
                        pBean.setName(parent);
                        pBean.getList().add(bean);
                        mapFolder.put(parent, pBean);
                    }


                }

                ListFolder.addAll(mapFolder.values())  ;

                SingerListAdapter adapterFolder= new SingerListAdapter(context,ListFolder);
                listView.setAdapter(adapterFolder);

                break;


        }


    }

    @Override
    public void onDestroy() {
        context.getContentResolver().unregisterContentObserver(observer);
        super.onDestroy();

    }

    /**
     * 利用内容观察者 观察数据的变化 只有数据库发生变化的时候 才重新获取数据库里面的内容
     *
     */
    private class CustomContentObserver extends ContentObserver {

        public CustomContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {

            list.clear();
            getListData();

            super.onChange(selfChange);
        }

    }


}
