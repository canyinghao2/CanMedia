package com.canyinghao.canmedia.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.canyinghao.otherlibrary.swipe.SwipeBackLayout;

public class SwipeBackActivity extends BaseActivity {
    public SwipeBackLayout mSwipeBackLayout;

    public boolean isSwipe = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        if (isSwipe) {
            mSwipeBackLayout = new SwipeBackLayout(this);

            mSwipeBackLayout
                    .addSwipeListener(new SwipeBackLayout.SwipeListener() {
                        @Override
                        public void onScrollStateChange(int state,
                                                        float scrollPercent) {

                        }

                        @Override
                        public void onEdgeTouch(int edgeFlag) {

                        }

                        @Override
                        public void onScrollOverThreshold() {

                        }
                    });
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
            mSwipeBackLayout.setEnableGesture(true);
        }

    }

    @SuppressLint("NewApi")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (isSwipe) {
            mSwipeBackLayout.attachToActivity(this);
        }


    }


}
