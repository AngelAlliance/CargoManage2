package com.sz.ljs.articlescan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.sz.ljs.articlescan.R;
import com.sz.ljs.articlescan.model.TitleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljs on 2018/9/5.
 */

public class TitleAdapter extends BaseAdapter {

    private Context mContext;
    private List<TitleModel> listData = new ArrayList<>();
    private LayoutInflater inflater;

    public TitleAdapter(Context context, List<TitleModel> list) {
        mContext = context;
        listData = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (null == convertView) {
            hodler = new ViewHodler();
            convertView = inflater.inflate(R.layout.item_view, null);
            hodler.tv_packageNum = (TextView) convertView.findViewById(R.id.tv_packageNum);
            hodler.tv_fengongsi = (TextView) convertView.findViewById(R.id.tv_fengongsi);
            hodler.tv_fuwuqudao = (TextView) convertView.findViewById(R.id.tv_fuwuqudao);
            hodler.tv_jianshu = (TextView) convertView.findViewById(R.id.tv_jianshu);
            hodler.tv_piaoshu = (TextView) convertView.findViewById(R.id.tv_piaoshu);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        if (null != listData && listData.size() > 0) {
            hodler.tv_packageNum.setText(listData.get(position).getPackNumber());
            hodler.tv_fengongsi.setText(listData.get(position).getFenGongSi());
            hodler.tv_fuwuqudao.setText(listData.get(position).getFuWuQuDao());
            hodler.tv_jianshu.setText(listData.get(position).getPices());
            hodler.tv_piaoshu.setText(listData.get(position).getVotes());
        }
        return convertView;
    }

    class ViewHodler {
        TextView tv_packageNum, tv_fengongsi, tv_fuwuqudao, tv_jianshu, tv_piaoshu;
    }
}
