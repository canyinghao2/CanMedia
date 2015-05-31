package com.canyinghao.canmedia.adapter.music;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.activity.music.MusicActivity;
import com.canyinghao.canmedia.adapter.NewBaseAdapter;
import com.canyinghao.canmedia.bean.music.AudioBean;
import com.canyinghao.canmedia.bean.music.PlaylistBean;
import com.canyinghao.canmedia.utils.MusicList.AudioUtils;
import com.canyinghao.canmedia.utils.MusicList.PlaylistUtils;
import com.canyinghao.canmedia.view.dialog.CustomListDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yangjian on 15/5/30.
 */
public class MusicListAdapter extends NewBaseAdapter<AudioBean> {



    private  String[] options={"收藏","分享","信息","删除","设为铃声"};


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
                    public void itemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
                         switch (i){

                             case  0:


                                List<PlaylistBean> list=    PlaylistUtils.getAllPlaylist();

                                 final  List<String> strList=new ArrayList<String>();
                                 for (PlaylistBean bean1:list){
                                     strList.add(bean1.getName());

                                 }

                                 new CustomListDialog(context,bean.getName(),strList,new CustomListDialog.OnItemClick() {
                                     @Override
                                     public void itemClick(AdapterView<?> arg0, View arg1, int p, long arg3) {




                                         AudioUtils.getInstance().addMediaToPlaylist(bean,strList.get(p));



                                     }
                                 }).show();

                                 break;
                              case  1:
                                 break;
                              case  2:
                                 break;
                              case  3:
                                 break;
                              case  4:
                                 break;

                         }



                    }
                }).show();
                return false;
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                MusicActivity.getMusicView().setPlayListAddBean(list, bean).play();




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
