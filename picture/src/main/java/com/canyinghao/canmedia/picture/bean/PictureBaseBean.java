package com.canyinghao.canmedia.picture.bean;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yalantis
 */
public class PictureBaseBean implements Serializable{

    private String nickname;
    private int background= Color.parseColor("#0000ff");
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
