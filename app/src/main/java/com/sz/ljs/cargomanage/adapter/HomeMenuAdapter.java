package com.sz.ljs.cargomanage.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.cargomanage.R;
import com.sz.ljs.cargomanage.model.HomeMenuModel;
import com.sz.ljs.common.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujs on 2018/8/13.
 * 首页菜单适配器
 */

public class HomeMenuAdapter extends BaseAdapter {
    private List<HomeMenuModel> homeMenuList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private int itemWidth;

    public HomeMenuAdapter(Context context, List<HomeMenuModel> list) {
        mContext = context;
        homeMenuList = list;
        inflater = LayoutInflater.from(context);
        itemWidth = (ScreenUtil.getScreenWidth(context)-(3* ScreenUtil.dipToPix(context, 3)))/2-40;
    }

    @Override
    public int getCount() {
        return homeMenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return homeMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (null == convertView) {
            hodler = new ViewHodler();
            convertView = inflater.inflate(R.layout.item_home_menu, null);
            hodler.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            hodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hodler.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        if (null != homeMenuList && homeMenuList.size() > 0) {
            hodler.iv_img.setImageResource(homeMenuList.get(position).getImgResource());
            hodler.tv_name.setText(homeMenuList.get(position).getName());
        }
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(itemWidth, itemWidth);
        convertView.setLayoutParams(param);
        return convertView;
    }

    private class ViewHodler {
        LinearLayout ll_item;
        ImageView iv_img;
        TextView tv_name;
    }
}
