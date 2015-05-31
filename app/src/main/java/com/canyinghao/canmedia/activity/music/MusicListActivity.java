package com.canyinghao.canmedia.activity.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.canyinghao.canmedia.Constant;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;
import com.canyinghao.canmedia.bean.music.PlaylistBean;
import com.canyinghao.canmedia.fragment.BaseFragment;
import com.canyinghao.canmedia.fragment.music.MusicListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MusicListActivity extends BaseBarActivity {



    @InjectView(R.id.toolbar)
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        ButterKnife.inject(this);


        setToolbar(toolbar, R.mipmap.ic_arrow_back_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);

        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction transaction= fm.beginTransaction();
      Intent intent= getIntent();
        if (intent.hasExtra(Constant.history)){






            Bundle bundle=   new Bundle();
            bundle.putSerializable(Constant.history, Constant.history);
            BaseFragment fragment=  MusicListFragment.getInstance(bundle);
            transaction.replace(R.id.fragment,fragment);

        }else if (intent.hasExtra(Constant.bean)){
            PlaylistBean bean= (PlaylistBean) intent.getSerializableExtra("bean");
            Bundle bundle=   new Bundle();
            bundle.putSerializable(Constant.bean,bean);
            BaseFragment fragment=  MusicListFragment.getInstance(bundle);
            transaction.replace(R.id.fragment,fragment);


        }else if(intent.hasExtra(Constant.playList)){


            Bundle bundle=   new Bundle();
            bundle.putString(Constant.playList, Constant.playList);
            BaseFragment fragment=  MusicListFragment.getInstance(bundle);
            transaction.replace(R.id.fragment,fragment);


        }






        transaction.commit();





    }











}
