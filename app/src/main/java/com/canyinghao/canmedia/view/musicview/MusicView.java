package com.canyinghao.canmedia.view.musicview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.canyinghao.canmedia.R;

/**
 * Created by yangjian on 15/5/30.
 */
public class MusicView extends FrameLayout{
    public MusicView(Context context) {
        super(context);
        initView();
    }

    public MusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView(){

       View v= LayoutInflater.from(getContext()).inflate(R.layout.music_view,null);


        addView(v);




    }










}
