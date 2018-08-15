package com.sz.ljs.common.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.sz.ljs.common.model.ExpressModel;

import java.util.ArrayList;
import java.util.List;

public class ExpressAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExpressModel> listData = new ArrayList<ExpressModel>();
    private LayoutInflater inflater;

    public ExpressAdapter(Context context, List<ExpressModel> list) {
        mContext = context;
        listData = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
