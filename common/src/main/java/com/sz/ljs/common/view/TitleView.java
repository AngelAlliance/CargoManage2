package com.sz.ljs.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.common.R;
import com.sz.ljs.common.utils.StringUtils;


/**
 * Created by 刘劲松 on 2017/7/5.
 * 通用标题
 */

public class TitleView extends LinearLayout {
    public TextView tv_title,tv_right,tv_back;
    public ImageView iv_back,iv_right, iv_center;
    public Button bt_right;
    private LinearLayout ll_center;
    private boolean backVisiable,moreVisiable;
    private String moreText,backText;
    private int moreImg;
    private int backImg;
    private int center_icon_resid;
    private String titleText;
    private IBackButtonCallBack mBackButtonCB;

    public interface IBackButtonCallBack{
        public boolean onClick(View v);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_titleview, this, true);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleView, 0, 0);
        try {
            titleText = ta.getString(R.styleable.TitleView_titleText);
            backVisiable = ta.getBoolean(R.styleable.TitleView_backVisiable, true);
            moreVisiable = ta.getBoolean(R.styleable.TitleView_moreVisiable, false);
            moreImg = ta.getResourceId(R.styleable.TitleView_moreImg, 0);
            backImg = ta.getResourceId(R.styleable.TitleView_backImg, 0);
            if (isInEditMode()) {
                moreText = ta.getString(R.styleable.TitleView_moreText);
                backText = ta.getString(R.styleable.TitleView_backText);
            } else{
                moreText = ta.getString(R.styleable.TitleView_moreText);
                backText = ta.getString(R.styleable.TitleView_backText);
            }
            //houxiaojian
            center_icon_resid = ta.getResourceId(R.styleable.TitleView_title_center_icon, -1);

            tv_back= (TextView) findViewById(R.id.tv_back);
            tv_back.setText(backText);
            iv_back= (ImageView) findViewById(R.id.iv_back);
            if(0 < backImg){
                iv_back.setImageResource(backImg);
            }
            if(backVisiable){
                iv_back.setVisibility(VISIBLE);
            }else {
                iv_back.setVisibility(GONE);
            }

            tv_title= (TextView) findViewById(R.id.tv_title);
            tv_title.setText(titleText);
            tv_right= (TextView) findViewById(R.id.tv_right);
            tv_right.setText(moreText);
            iv_right= (ImageView) findViewById(R.id.iv_right);
            iv_right.setImageResource(moreImg);
            if(moreVisiable){
                iv_right.setVisibility(VISIBLE);
            }else {
                iv_right.setVisibility(GONE);
            }

            //houxiaojian
            iv_center = (ImageView)findViewById(R.id.title_center_icon);
            ll_center =(LinearLayout)findViewById(R.id.title_ll_center);
            if(-1 != center_icon_resid){
                iv_center.setVisibility(View.VISIBLE);
                iv_center.setImageResource(center_icon_resid);
            }else{
                iv_center.setVisibility(View.GONE);
            }

            initBackButtonListener();
        } finally {
            ta.recycle();
        }

    }

    /**
     * 设置标题
     * @param title
     */
    public void setTv_title(String title){
        this.titleText = title;
        tv_title.setText(titleText);
    }

    /**
     * 设置返回图标
     * @param img
     */
    public void setLeftImag(int img){
        if(0!=img){
            this.backImg=img;
            iv_back.setImageResource(backImg);
        }
    }

    /**
     * 设置右上角按钮内容及是否可见
     * @param rightText  文字内容
     * @param moreimg   图片资源
     * @param listener   点击监听
     */
    public void setBt_right(String rightText, int moreimg, OnClickListener listener){
        this.moreImg=moreimg;
        this.moreText=rightText;
        if(StringUtils.isEmpty(moreText)&&0==moreImg){
            iv_right.setVisibility(GONE);
            tv_right.setVisibility(GONE);
        }else{
            iv_right.setVisibility(VISIBLE);
            tv_right.setVisibility(VISIBLE);
            tv_right.setText(moreText);
            if(0!=moreImg){
                iv_right.setImageResource(moreImg);
            }
        }
        if(!StringUtils.isEmpty(moreText)){
            tv_right.setOnClickListener(listener);
        }
        if(0!=moreImg){
            iv_right.setOnClickListener(listener);
        }
    }

    //设置拦截返回监听按钮
    public void setBackOnclistener(IBackButtonCallBack callBack){
        mBackButtonCB = callBack;
    }

    /**
     * 右上角按钮文字点击监听方法
     * @param listener
     */
    public void setRightBtnOnclickListener(OnClickListener listener){
        tv_right.setOnClickListener(listener);
        iv_right.setOnClickListener(listener);
    }

    //TODO 设置左边返回按钮的监听
    private void initBackButtonListener(){
        mBackButtonCB = null;
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mBackButtonCB){
                    if(mBackButtonCB.onClick(v)){
                        ((Activity) getContext()).onBackPressed();
                    }
                }else{
                    ((Activity) getContext()).onBackPressed();
                }
            }
        });
    }

    //TODO 设置中间区域的点击事件
    public void setOnCenterClickListener(OnClickListener listener){
        if(null != ll_center){
            ll_center.setOnClickListener(listener);
        }
    }

    public void setMoreVisiable(boolean visiable){
        moreVisiable=visiable;
       if(true==moreVisiable){
           iv_right.setVisibility(VISIBLE);
           tv_right.setVisibility(VISIBLE);
       }else{
           iv_right.setVisibility(GONE);
           tv_right.setVisibility(GONE);
       }
    }

    public TextView getRightTextView(){
          return tv_right;
    }
}
