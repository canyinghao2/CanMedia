package com.canyinghao.canmedia.activity.music;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;
import com.canyinghao.canmedia.fragment.BaseFragment;
import com.canyinghao.canmedia.fragment.MusicListFragment;
import com.canyinghao.otherlibrary.CustomViewPager.SupportFragmentAdapter;
import com.canyinghao.otherlibrary.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MusicLocalActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.pager)
    ViewPager pager;
    @InjectView(R.id.rg)
    RadioGroup rg;
    @InjectView(R.id.indicator)
    UnderlinePageIndicator indicator;

   private List<BaseFragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_local);
        ButterKnife.inject(this);


        setToolbar(toolbar, R.mipmap.ic_arrow_back_grey, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);




        list=new ArrayList<BaseFragment>();



        Bundle b1= new Bundle();
        b1.putInt(MusicListFragment.TYPE,0);
        BaseFragment bf1= MusicListFragment.getInstance(b1);
        Bundle b2= new Bundle();
        b2.putInt(MusicListFragment.TYPE,1);
        BaseFragment bf2= MusicListFragment.getInstance(b2);
        Bundle b3= new Bundle();
        b3.putInt(MusicListFragment.TYPE,2);
        BaseFragment bf3= MusicListFragment.getInstance(b3);

        list.add(bf1);
        list.add(bf2);
        list.add(bf3);


        initPager();
        SupportFragmentAdapter adapter= new SupportFragmentAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(adapter);

        indicator.setViewPager(pager);



    }




    private void initPager() {
        pager.setOffscreenPageLimit(3);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int p) {
                RadioButton childAt = (RadioButton) rg.getChildAt(p);
                childAt.setChecked(true);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup rg, int rid) {
                int count = rg.getChildCount();
                int num = 0;
                for (int i = 0; i < count; i++) {

                    if (rg.getChildAt(i).getId() == rid) {
                        num = i;
                        break;
                    }
                }

                pager.setCurrentItem(num);


                LogHelper.logi(num+"pagecurrent");

            }
        });

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton childAt = (RadioButton) rg.getChildAt(position);
                childAt.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }








}
