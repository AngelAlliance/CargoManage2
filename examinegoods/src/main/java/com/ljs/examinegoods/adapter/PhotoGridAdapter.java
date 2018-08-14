package com.ljs.examinegoods.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ljs.examinegoods.R;
import com.sz.ljs.common.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujs on 2018/8/14.
 * 验货界面照片适配器
 */

public class PhotoGridAdapter extends BaseAdapter {

    private List<Bitmap> photoList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private int itemWidth;

    public interface DelOnclickCallBack {
        void CallBack(int position);
    }

    DelOnclickCallBack callBack;

    public void setDelOnclickCallBack(DelOnclickCallBack listener) {
        callBack = listener;
    }

    public PhotoGridAdapter(Context context, List<Bitmap> list) {
        mContext = context;
        photoList = list;
        inflater = LayoutInflater.from(context);
        itemWidth = (ScreenUtil.getScreenWidth(context) - (3 * ScreenUtil.dipToPix(context, 3))) / 4 - 20;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (null == convertView) {
            hodler = new ViewHodler();
            convertView = inflater.inflate(R.layout.item_photo, null);
            hodler.fiv = (ImageView) convertView.findViewById(R.id.fiv);
            hodler.ll_del = (LinearLayout) convertView.findViewById(R.id.ll_del);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        if (null != photoList && photoList.size() > 0) {
            if (null != photoList.get(position)) {
                hodler.fiv.setImageBitmap(photoList.get(position));
            } else {
                hodler.fiv.setImageResource(R.mipmap.banner_default);
            }
        }
        hodler.ll_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callBack) {
                    callBack.CallBack(position);
                }
            }
        });
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(itemWidth, itemWidth);
        convertView.setLayoutParams(param);

        return convertView;
    }

    private class ViewHodler {
        ImageView fiv;
        LinearLayout ll_del;
    }
}
