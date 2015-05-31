package com.canyinghao.canmedia.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.adapter.MusicListAdapter;
import com.canyinghao.canmedia.utils.MusicList.AudioUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by yangjian on 15/5/30.
 */
public class SingerListFragment extends BaseFragment {

    public static final String TYPE = "type";

    public static final int music = 0;
    public static final int singer = 1;
    public static final int folder = 2;
    public static final int history = 3;
    public static final int playlist = 4;
    @InjectView(R.id.lisView)
    ListView listView;

    int type;
    AudioUtils audioDao;

    private List list;
    MusicListAdapter adapter;

    public static BaseFragment getInstance(Bundle bundle) {

        BaseFragment bf = new SingerListFragment();


        bf.setArguments(bundle);


        return bf;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_list, container, false);
        ButterKnife.inject(this, v);
        audioDao = new AudioUtils(context);
        list = new ArrayList();
        if (mBundle != null) {
            type = mBundle.getInt(TYPE);
        }
        getListData();






        return v;
    }


    private void getListData() {

        switch (type) {


            case music:

                list.addAll(audioDao.getLocalAudioList());

                adapter = new MusicListAdapter(context, list);

                listView.setAdapter(adapter);


                break;
            case singer:






                list.addAll(audioDao.getLocalAudioList());

                break;
            case folder:
                list.addAll(audioDao.getLocalAudioList());

                break;
            case history:
                break;
            case playlist:
                break;

        }


    }


}
