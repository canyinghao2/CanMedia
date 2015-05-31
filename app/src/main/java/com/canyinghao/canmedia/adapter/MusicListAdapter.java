package com.canyinghao.canmedia.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.music.MusicActivity;
import com.canyinghao.canmedia.bean.music.AudioBean;
import com.canyinghao.canmedia.view.dialog.CustomListDialog;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yangjian on 15/5/30.
 */
public class MusicListAdapter extends  NewBaseAdapter<AudioBean> {



    private  String[] options={"收藏","添加","分享","信息","删除","设为铃声"};


    public MusicListAdapter(Context c, List<AudioBean> list) {
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
        final  AudioBean bean=list.get(i);

        holder. tv_name.setText(bean.getName());

        holder.tv_num.setText(i+1+"");
        holder.tv_type.setText(bean.getArtist());



        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                new CustomListDialog(context,bean.getName(),options,new CustomListDialog.OnItemClick() {
                    @Override
                    public void itemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {



                    }
                }).show();
                return false;
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                MusicActivity.getMusicView().setPlayList(list).setPlayBean(bean).play();




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
