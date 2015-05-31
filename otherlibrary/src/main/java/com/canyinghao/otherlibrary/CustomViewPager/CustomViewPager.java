package com.canyinghao.otherlibrary.CustomViewPager;

/**
 * Created by yangjian on 15/3/31.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.canyinghao.otherlibrary.textview.TextDrawable;

public class CustomViewPager extends LinearLayout {

    private RadioGroup rg;
    private ViewPager pager;

    private PagerType defaultType = PagerType.LINEAR_TOP;

    public CustomViewPager(Context context) {
        super(context);
        setType(defaultType);
    }

    @SuppressLint("NewApi")
    public CustomViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setType(defaultType);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(defaultType);
    }


    public void setType(PagerType type) {
        defaultType = type;
        initView(type);
        initPager();

    }

    public enum PagerType {
        LINEAR_TOP, LINEAR_BOTTOM, FRAME_TOP_CENTER, FRAME_TOP_RIGHT, FRAME_TOP_LEFT, FRAME_BOTTOM_CENTER, FRAME_BOTTOM_RIGHT, FRAME_BOTTOM_LEFT

    }

    private void initView(PagerType type) {
        removeAllViews();

        rg = new RadioGroup(getContext());
        rg.setOrientation(RadioGroup.HORIZONTAL);
        rg.setGravity(Gravity.CENTER);
        rg.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pager = new ViewPager(getContext());
        pager.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        switch (type) {
            case LINEAR_TOP:

            case LINEAR_BOTTOM:
                llAddView(type);
                break;
            case FRAME_TOP_CENTER:

            case FRAME_TOP_RIGHT:

            case FRAME_TOP_LEFT:

            case FRAME_BOTTOM_CENTER:

            case FRAME_BOTTOM_RIGHT:

            case FRAME_BOTTOM_LEFT:
                fmAddView(type);
                break;

        }

    }

    private void llAddView(PagerType type) {

        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 1.0f);

        pager.setLayoutParams(params);
        switch (type) {
            case LINEAR_TOP:
                ll.addView(rg);

                ll.addView(pager);
                break;
            case LINEAR_BOTTOM:
                ll.addView(pager);
                ll.addView(rg);
                break;
            default:
                break;
        }

        addView(ll);
    }

    private void fmAddView(PagerType type) {
        FrameLayout fm = new FrameLayout(getContext());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        switch (type) {

            case FRAME_TOP_CENTER:
                params.gravity = Gravity.CENTER_HORIZONTAL;
                break;
            case FRAME_TOP_RIGHT:
                params.gravity = Gravity.RIGHT;
                break;
            case FRAME_TOP_LEFT:
                params.gravity = Gravity.LEFT;
                break;
            case FRAME_BOTTOM_CENTER:
                params.gravity = Gravity.BOTTOM;
                break;
            case FRAME_BOTTOM_RIGHT:
                params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                break;
            case FRAME_BOTTOM_LEFT:
                params.gravity = Gravity.BOTTOM | Gravity.LEFT;
                break;
            default:
                break;

        }

        rg.setLayoutParams(params);
        fm.addView(pager);
        fm.addView(rg);
        addView(fm);

    }

    public void setAdapter(PagerAdapter adapter) {
        pager.setAdapter(adapter);
        if (adapter.getCount() > 0) {

            View v = rg.getChildAt(0);
            if (v instanceof RadioButton) {
                RadioButton rb = (RadioButton) v;
                rb.setChecked(true);
            }
        }

    }



    public RadioGroup getRg() {
        return rg;
    }

    public void setRg(RadioGroup rg) {
        this.rg = rg;
    }

    @SuppressLint("NewApi")
    public void setRadioButtonTexts(String[] strs, int normalColor,
                                    int checkedColor, float fontSize) {
        rg.removeAllViews();
        if (normalColor <= 0) {
            normalColor = Color.WHITE;
        }
        if (checkedColor <= 0) {
            checkedColor = Color.BLUE;
        }
        for (String text : strs) {
            RadioButton rb = new RadioButton(getContext());
            rb.setText(text);
            rb.setButtonDrawable(android.R.color.transparent);

            rb.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            ColorStateList color = SelectorHepler.getInstance()
                    .getCheckedSelectorColor(normalColor, checkedColor);
            rb.setTextColor(color);
            rb.setTextSize(fontSize);
            rb.setGravity(Gravity.CENTER);
            if (defaultType == PagerType.LINEAR_TOP
                    || defaultType == PagerType.LINEAR_BOTTOM) {
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.MATCH_PARENT, 1.0f);
                rb.setLayoutParams(params);
            }

            rg.addView(rb);
        }

    }

    public void setRadioButtonPonit(int nums,
                                    int size,int... color) {
        rg.removeAllViews();
        int normalColor=Color.WHITE;
        int checkedColor=Color.BLUE;
        if (color!=null&&color.length>=2) {
            normalColor = color[0];
            checkedColor = color[1];
        }

        for (int i = 0; i < nums; i++) {
            RadioButton rb = new RadioButton(getContext());

            rb.setGravity(Gravity.CENTER);
            TextDrawable normal = TextDrawable.builder().buildRound("",
                    normalColor);
            TextDrawable checked = TextDrawable.builder().buildRound("",
                    checkedColor);
            StateListDrawable selector = SelectorHepler.getInstance()
                    .getCheckedSelectorDrawable( normal, checked);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(size,
                    size);
            params.setMargins(10, 10, 10, 10);
            rb.setLayoutParams(params);
            rb.setButtonDrawable(android.R.color.transparent);

            rb.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

            rb.setBackgroundDrawable(selector);
            rg.addView(rb);
        }

    }

    private void initPager() {

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int p) {
                RadioButton childAt = (RadioButton) rg.getChildAt(p);
                childAt.setChecked(true);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup rg, int rid) {
                int count = rg.getChildCount();
                int num = 0;
                for (int i = 0; i < count; i++) {

                    if (rg.getChildAt(i).getId() == rid) {
                        num = i;
                        break;
                    }
                }

                pager.setCurrentItem(num);

            }
        });

    }

}
