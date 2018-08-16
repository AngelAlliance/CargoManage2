package com.sz.ljs.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.ljs.common.R;
import com.sz.ljs.common.utils.ScreenUtil;
import com.sz.ljs.common.model.MenuModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/16.
 */

public class MenuAdapter extends BaseAdapter {

    private List<MenuModel> menuList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private int itemWidth;

    public MenuAdapter(Context context, List<MenuModel> list) {
        mContext = context;
        menuList = list;
        inflater = LayoutInflater.from(context);
        itemWidth = (ScreenUtil.getScreenWidth(context) - (3 * ScreenUtil.dipToPix(context, 3))) / 3 - 30;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_menu, null);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (null != menuList && menuList.size() > 0) {
            holder.iv_img.setImageResource(menuList.get(position).getImgResource());
            holder.tv_name.setText(menuList.get(position).getName());
        }
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(itemWidth, itemWidth/2);
        convertView.setLayoutParams(param);
        return convertView;
    }

    class ViewHolder {
        ImageView iv_img;
        TextView tv_name;
    }
}
