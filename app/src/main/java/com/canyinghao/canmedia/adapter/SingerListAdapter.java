package com.canyinghao.canmedia.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.music.MusicListActivity;
import com.canyinghao.canmedia.bean.music.PlaylistBean;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yangjian on 15/5/30.
 */
public class SingerListAdapter extends  NewBaseAdapter<PlaylistBean> {





    public SingerListAdapter(Context c, List<PlaylistBean> list) {
        super(c, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view==null) {
            view = inflater.inflate(R.layout.item_music_list, null);
            holder=new ViewHolder(view);
           view.setTag(holder);
        }

        holder= (ViewHolder) view.getTag();
       final PlaylistBean bean=list.get(i);


        holder. tv_name.setText(bean.getName());

        holder.tv_num.setText(i+1+"");
        holder.tv_type.setText(bean.getCountAudio()+"");



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentHelper.getInstance().showIntent(context, MusicListActivity.class,new String[]{"bean"},new Serializable[]{bean});
            }
        });





        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_num)
        TextView tv_num;
        @InjectView(R.id.tv_name)
        TextView tv_name;
        @InjectView(R.id.tv_type)
        TextView tv_type;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }









}
