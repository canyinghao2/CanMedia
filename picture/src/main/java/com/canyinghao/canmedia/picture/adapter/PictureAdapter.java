package com.canyinghao.canmedia.picture.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canyinghao.canhelper.utils.LogHelper;
import com.canyinghao.canmedia.picture.R;
import com.canyinghao.canmedia.picture.bean.PictrueAibumBean;
import com.canyinghao.canmedia.picture.view.flipviewpager.BaseFlipAdapter;
import com.canyinghao.canmedia.picture.view.flipviewpager.FlipSettings;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjian on 15/5/31.
 */
public class PictureAdapter extends BaseFlipAdapter<PictrueAibumBean> {

    private final int PAGES = 3;
    private int[] IDS_INTEREST = {R.id.interest_1, R.id.interest_2, R.id.interest_3, R.id.interest_4, R.id.interest_5};

    public PictureAdapter(Context context, List<PictrueAibumBean> items, FlipSettings settings) {
        super(context, items, settings);
    }

    @Override
    public View getPage(int position, View convertView, ViewGroup parent, PictrueAibumBean friend1, PictrueAibumBean friend2) {
        final FriendsHolder holder;

        if (convertView == null) {
            holder = new FriendsHolder();
            convertView = inflater.inflate(R.layout.item_picture_page_merge, parent, false);
            holder.leftAvatar = (SimpleDraweeView) convertView.findViewById(R.id.first);
            holder.rightAvatar = (SimpleDraweeView) convertView.findViewById(R.id.second);
            holder.infoPage = inflater.inflate(R.layout.item_picture_page_info, parent, false);
            holder.nickName = (TextView) holder.infoPage.findViewById(R.id.nickname);

            for (int id : IDS_INTEREST)
                holder.interests.add((TextView) holder.infoPage.findViewById(id));

            convertView.setTag(holder);
        } else {
            holder = (FriendsHolder) convertView.getTag();
        }

        switch (position) {
            // Merged page with 2 friends
            case 1:
                holder.leftAvatar.setImageURI(Uri.parse("file://"+friend1.getBitList().get(0).getPath()));
                LogHelper.e("file://" + friend1.getBitList().get(0).getPath());
                if (friend2 != null) {

                    holder.rightAvatar.setImageURI(Uri.parse("file://"+friend2.getBitList().get(0).getPath()));
                    LogHelper.e("file://"+friend2.getBitList().get(0).getPath());
                }
                break;
            default:
                fillHolder(holder, position == 0 ? friend1 : friend2);
                holder.infoPage.setTag(holder);
                return holder.infoPage;
        }
        return convertView;
    }

    @Override
    public int getPagesCount() {
        return PAGES;
    }

    private void fillHolder(FriendsHolder holder, PictrueAibumBean pictureBaseBean) {
        if (pictureBaseBean == null)
            return;
//        Iterator<TextView> iViews = holder.interests.iterator();
//        Iterator<String> iInterests = pictureBaseBean.getInterests().iterator();
//        while (iViews.hasNext() && iInterests.hasNext())
//            iViews.next().setText(iInterests.next());
        holder.infoPage.setBackgroundColor(pictureBaseBean.getBackground());
        holder.nickName.setText(pictureBaseBean.getName());
    }

    class FriendsHolder {
        SimpleDraweeView leftAvatar;
        SimpleDraweeView rightAvatar;
        View infoPage;

        List<TextView> interests = new ArrayList<>();
        TextView nickName;
    }
}