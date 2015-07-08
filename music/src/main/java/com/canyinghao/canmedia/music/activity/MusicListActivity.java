package com.canyinghao.canmedia.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.canyinghao.canhelper.base.BaseActivity;
import com.canyinghao.canhelper.base.BaseFragment;
import com.canyinghao.canmedia.music.R;
import com.canyinghao.canmedia.music.bean.PlaylistBean;
import com.canyinghao.canmedia.music.fragment.MusicListFragment;
import com.canyinghao.otherlibrary.Constant;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MusicListActivity extends BaseActivity {



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
