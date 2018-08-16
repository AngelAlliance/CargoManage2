package com.sz.ljs.patchlabel.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sz.ljs.patchlabel.R;

/**
 * Created by Administrator on 2018/8/16.
 */

public class YunDanTypePopuwindow extends PopupWindow {
    private View conentView;
    private Activity context;
    public OnclickYunDanClickListener yunDanClickListener;
    public OnclickZiDanClickListener ziDanClickListener;

    public interface OnclickYunDanClickListener {
        void onclick();
    }

    public interface OnclickZiDanClickListener {
        void onclick();
    }

    public void setYunDanClickListener(OnclickYunDanClickListener listener) {
        yunDanClickListener = listener;
    }

    public void setZiDanClickListener(OnclickZiDanClickListener listener) {
        ziDanClickListener = listener;
    }

    public YunDanTypePopuwindow(Activity activity) {
        super(activity);
        context = activity;
        this.initPopupWindow();
    }

    private void initPopupWindow() {
        //使用view来引入布局
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.view_yundan_type_popuwindow, null);
        //获取popupwindow的高度与宽度
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(w / 2 + 50);
//        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果，设置动画，一会会讲解
        this.setAnimationStyle(R.style.AnimationPreview);
        //布局控件初始化与监听设置
        TextView tv_upload_album = (TextView) conentView
                .findViewById(R.id.tv_upload_album);
        TextView tv_upload_video = (TextView) conentView
                .findViewById(R.id.tv_upload_video);
        tv_upload_album.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (null != yunDanClickListener) {
                    yunDanClickListener.onclick();
                }
            }
        });

        tv_upload_video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != ziDanClickListener) {
                    ziDanClickListener.onclick();
                }
            }
        });
    }

    /**
     * 显示popupWindow的方式设置，当然可以有别的方式。
     * 一会会列出其他方法
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 0);
        } else {
            this.dismiss();
        }
    }

}
