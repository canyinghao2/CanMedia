package com.canyinghao.canmedia.picture.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.canyinghao.canhelper.base.BaseActivity;
import com.canyinghao.canhelper.utils.PhoneHelper;
import com.canyinghao.canmedia.picture.R;
import com.canyinghao.canmedia.picture.adapter.PictureListAdapter;
import com.canyinghao.canmedia.picture.bean.PictrueAibumBean;
import com.canyinghao.canmedia.picture.bean.PictrueItemBean;
import com.canyinghao.canmedia.picture.view.flipviewpager.FlipSettings;
import com.canyinghao.otherlibrary.Constant;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PictureListActivity extends BaseActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
 @InjectView(R.id.listView)
 ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictrue_list);
        ButterKnife.inject(this);


        setToolbar(toolbar, R.mipmap.ic_arrow_back_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);

        Intent intent=getIntent();

        if (intent.hasExtra(Constant.bean)){



             PictrueAibumBean bean= (PictrueAibumBean) intent.getSerializableExtra(Constant.bean);

            List<PictrueItemBean> list=   bean.getBitList();

            PhoneHelper.getInstance().show(list.size()+"dddd");
            FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();

            listView.setAdapter(new PictureListAdapter(this, list, settings));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    PhoneHelper.getInstance().show("ddd");
                }
            });
        }







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
