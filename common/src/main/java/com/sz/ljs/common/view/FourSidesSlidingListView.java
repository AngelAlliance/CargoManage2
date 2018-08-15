package com.sz.ljs.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sz.ljs.common.R;
import com.sz.ljs.common.base.BaseCustomView;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.view.adapter.FourSidesSlidListAdapter;
import com.sz.ljs.common.view.adapter.FourSidesSlidListTitleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/15.
 */

public class FourSidesSlidingListView extends BaseCustomView {
    private View view;
    private SyncHorizontalScrollView header_horizontal;
    private SyncHorizontalScrollView data_horizontal;
    private NoscrollListView  lv_data;
    private NoscrollListView lv_header;
    private FourSidesSlidListTitleAdapter headerAdapter;
    private List<FourSidesSlidListTitileModel> headerList = new ArrayList<>();
    private Context mContext;
    private FourSidesSlidListAdapter dataAdapter;
    private List<ExpressPackageModel> listData = new ArrayList<>();

    public FourSidesSlidingListView(Context context) {
        this(context, null);
        mContext = context;
    }
    public FourSidesSlidingListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_four_sides_sliding_listview, this, true);
        initView();
    }

    private void initView(){
        header_horizontal = (SyncHorizontalScrollView) findViewById(R.id.header_horizontal);
        data_horizontal = (SyncHorizontalScrollView) findViewById(R.id.data_horizontal);
        lv_header = (NoscrollListView) findViewById(R.id.lv_header);
        lv_data = (NoscrollListView) findViewById(R.id.lv_data);
        header_horizontal.setScrollView(data_horizontal);
        data_horizontal.setScrollView(header_horizontal);
        headerAdapter = new FourSidesSlidListTitleAdapter(mContext, headerList);
        lv_header.setAdapter(headerAdapter);
        setListViewHeight(lv_header);
        dataAdapter=new FourSidesSlidListAdapter(mContext,listData);
        lv_data.setAdapter(dataAdapter);
        setListViewHeight(lv_data);
    }

    //TODO 设置头部数据
    public void setHeaderData(List<FourSidesSlidListTitileModel> list) {
        headerList.clear();
        headerList = list;
//        setListViewHeight(lv_header);
        headerAdapter.setData(headerList);
    }

    //TODO 设置底下运单数据
    public void setContentData(List<ExpressPackageModel> list) {
        listData.clear();
        listData = list;
        dataAdapter.setData(listData);
    }

    //为listview动态设置高度（有多少条目就显示多少条目）
    public void setListViewHeight(ListView listView) {
        //获取listView的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount()返回数据项的数目
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
