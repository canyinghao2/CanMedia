package com.canyinghao.canmedia.activity.picture;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canmedia.Constant;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;
import com.canyinghao.canmedia.adapter.picture.PictureAdapter;
import com.canyinghao.canmedia.bean.picture.PictrueAibumBean;
import com.canyinghao.canmedia.utils.picture.PictureUtil;
import com.canyinghao.canmedia.view.flipviewpager.FlipSettings;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PictureActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
 @InjectView(R.id.listView)
 ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictrue);
        ButterKnife.inject(this);



        setToolbar(toolbar, R.mipmap.ic_arrow_back_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);



        FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();
       final  List<PictrueAibumBean> list =PictureUtil.getPhotoAlbum(context);
        listView.setAdapter(new PictureAdapter(this, list, settings));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                IntentHelper.getInstance().showIntent(context,PictureListActivity.class,new String[]{Constant.bean},new Serializable[]{list.get(position)});
            }
        });



        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {


            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

//              int  f= (int)(firstVisibleItem /(float)totalItemCount*255);
//                  toolbar.setBackgroundColor(Color.argb(255,255,255,255));

            }
        });







    }





}
