package com.canyinghao.canmedia.bean.picture;

import com.canyinghao.canmedia.R;
import com.canyinghao.canmedia.bean.BaseBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yalantis
 */
public class PictureBaseBean extends BaseBean{

    private String nickname;
    private int background= R.color.blue;
    private List<String> interests = new ArrayList<>();
    public PictureBaseBean(){


    }


    public PictureBaseBean( String nickname, int background, String... interest) {

        this.nickname = nickname;
        this.background = background;
        interests.addAll(Arrays.asList(interest));
    }



    public String getNickname() {
        return nickname;
    }

    public int getBackground() {
        return background;
    }

    public List<String> getInterests() {
        return interests;
    }
}
