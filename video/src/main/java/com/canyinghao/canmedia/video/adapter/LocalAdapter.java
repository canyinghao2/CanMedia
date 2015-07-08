package com.canyinghao.canmedia.video.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canmedia.video.R;
import com.canyinghao.canmedia.video.bean.POMedia;
import com.canyinghao.canmedia.video.utils.FileUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yangjian on 15/7/9.
 */
public class LocalAdapter extends ArrayAdapter<POMedia> {

    private HashMap<String, POMedia> maps = new HashMap<String, POMedia>();

    public LocalAdapter(Context ctx, List<POMedia> l) {
        super(ctx, l);
        maps.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final POMedia f = getItem(position);
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.fragment_file_item, null);
        }
        ((TextView) convertView.findViewById(R.id.title)).setText(f.title);

        //显示文件大小
        String file_size;
        if (f.temp_file_size > 0) {
            file_size = FileUtils.showFileSize(f.temp_file_size) + " / " + FileUtils.showFileSize(f.file_size);
        } else {
            file_size = FileUtils.showFileSize(f.file_size);
        }
        ((TextView) convertView.findViewById(R.id.file_size)).setText(file_size);

        //显示进度表
        final ImageView status = (ImageView) convertView.findViewById(R.id.status);
        if (f.status > -1) {
//            int resStauts = getStatusImage(f.status);
//            if (resStauts > 0) {
//                if (status.getVisibility() != View.VISIBLE)
//                    status.setVisibility(View.VISIBLE);
//                status.setImageResource(resStauts);
//            }
        } else {
            if (status.getVisibility() != View.GONE)
                status.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void add(POMedia item, String url) {
        super.add(item);
        if (!maps.containsKey(url))
            maps.put(url, item);
    }

    public void delete(int position) {
        synchronized (mLock) {
            mObjects.remove(position);
        }
        notifyDataSetChanged();
    }

    public POMedia getItem(String url) {
        return maps.get(url);
    }


}
