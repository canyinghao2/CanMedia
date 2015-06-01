package com.canyinghao.canmedia.activity.video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.canyinghao.canhelper.ThreadHelper;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.BaseBarActivity;
import com.canyinghao.canmedia.bean.video.POMedia;
import com.canyinghao.canmedia.utils.VideoUtils.VideoUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class VideoActivity extends BaseBarActivity {
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv)
    TextView tv;



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

        ThreadHelper.getInstance().runAsync(new ThreadHelper.ThreadCallBack() {
            @Override
            public void progress(Integer... values) {

            }

            @Override
            public Object run(ThreadHelper.ThreadAsync at) {


                return VideoUtils.eachAllMedias(Environment.getExternalStorageDirectory());
            }

            @Override
            public void result(Object result) {
               final List<POMedia> list= (List<POMedia>) result;
                tv.setText(list.get(0).title);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        POMedia f=  list.get(0);
                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        intent.putExtra("path", f.path);
                        intent.putExtra("title", f.title);
                        startActivity(intent);
                    }
                });
            }
        });


    }


}
