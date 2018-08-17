package com.sz.ljs.common.view;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.sz.ljs.common.model.ExpressModel;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.view.adapter.ExpressAdapter;
import com.sz.ljs.common.view.adapter.FourSidesSlidListTitleAdapter;
import com.sz.ljs.common.view.adapter.NoscrollExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/15.
 * 自定义上下左右滑动控件
 */

public class FourSidesSlidingListView extends BaseCustomView {
    private SyncHorizontalScrollView header_horizontal;
    private SyncHorizontalScrollView data_horizontal;
    private SyncHorizontalScrollView header_noPackagehorizontal;
    private SyncHorizontalScrollView data_noPackagehorizontal;
    private NoscrollExpandableListView lv_data;
    private NoscrollListView lv_noPackagedata;
    private NoscrollListView lv_header;
    private NoscrollListView lv_noPackageheader;
    private FourSidesSlidListTitleAdapter headerAdapter;
    private List<FourSidesSlidListTitileModel> headerList = new ArrayList<>();
    private Context mContext;
    private NoscrollExpandableListAdapter dataAdapter;
    private List<ExpressPackageModel> listData = new ArrayList<>();
    private List<ExpressModel> noPackageListData = new ArrayList<>();
    private ExpressAdapter expressAdapter;
    private int type; //1代表有包编号的，2代表没有报编号（未打包的）

    //TODO 带包数据的包勾选按钮回调
    public interface SidesSlidGroupCheckListener {
        void OnClick(int groupPosition);
    }

    //TODO 带包数据的子单勾选按钮回调
    public interface SidesSlidChildCheckListener {
        void OnClick(int groupPosition, int childPosition);
    }

    //TODO 不带包数据的子单勾选按钮回调
    public interface SidesSlidNoGroupCheckListener {
        void OnClick(int childPosition);
    }

    SidesSlidGroupCheckListener sidesSlidGroupCheckListener;
    SidesSlidChildCheckListener sidesSlidChildCheckListener;
    SidesSlidNoGroupCheckListener sidesSlidNoGroupCheckListener;

    public void setSidesSlidGroupCheckListener(SidesSlidGroupCheckListener listener) {
        sidesSlidGroupCheckListener = listener;
    }

    public void setSidesSlidChildCheckListener(SidesSlidChildCheckListener listener) {
        sidesSlidChildCheckListener = listener;
    }

    public void setSidesSlidNoGroupCheckListener(SidesSlidNoGroupCheckListener listener) {
        sidesSlidNoGroupCheckListener = listener;
    }

    public FourSidesSlidingListView(Context context) {
        this(context, null);
        mContext = context;
    }

    public FourSidesSlidingListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_four_sides_sliding_listview, this, true);
        try {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FourSidesSlidingListView, 0, 0);
            type = ta.getInt(R.styleable.FourSidesSlidingListView_type, 1);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        initView();
    }

    private void initView() {
        header_horizontal = (SyncHorizontalScrollView) findViewById(R.id.header_horizontal);
        header_noPackagehorizontal = (SyncHorizontalScrollView) findViewById(R.id.header_noPackagehorizontal);
        data_horizontal = (SyncHorizontalScrollView) findViewById(R.id.data_horizontal);
        data_noPackagehorizontal = (SyncHorizontalScrollView) findViewById(R.id.data_noPackagehorizontal);
        lv_header = (NoscrollListView) findViewById(R.id.lv_header);
        lv_noPackageheader = (NoscrollListView) findViewById(R.id.lv_noPackageheader);
        lv_data = (NoscrollExpandableListView) findViewById(R.id.lv_data);
        lv_noPackagedata = (NoscrollListView) findViewById(R.id.lv_noPackagedata);
        if (1 == type) {
            header_noPackagehorizontal.setVisibility(GONE);
            header_horizontal.setVisibility(VISIBLE);
            data_noPackagehorizontal.setVisibility(GONE);
            data_horizontal.setVisibility(VISIBLE);
            header_horizontal.setScrollView(data_horizontal);
            data_horizontal.setScrollView(header_horizontal);
            headerAdapter = new FourSidesSlidListTitleAdapter(mContext, headerList);
            lv_header.setAdapter(headerAdapter);
            setListViewHeight(lv_header);
            dataAdapter = new NoscrollExpandableListAdapter(mContext, listData);
            lv_data.setAdapter(dataAdapter);
            setListViewHeight(lv_data);
        } else if (2 == type) {
            header_noPackagehorizontal.setVisibility(VISIBLE);
            header_horizontal.setVisibility(GONE);
            data_noPackagehorizontal.setVisibility(VISIBLE);
            data_horizontal.setVisibility(GONE);
            header_noPackagehorizontal.setScrollView(data_noPackagehorizontal);
            data_noPackagehorizontal.setScrollView(header_noPackagehorizontal);
            headerAdapter = new FourSidesSlidListTitleAdapter(mContext, headerList);
            lv_noPackageheader.setAdapter(headerAdapter);
            setListViewHeight(lv_noPackageheader);
            expressAdapter = new ExpressAdapter(mContext, noPackageListData);
            lv_noPackagedata.setAdapter(expressAdapter);
            setListViewHeight(lv_noPackagedata);
        }
        setListener();
    }

    private void setListener() {
        if (1 == type) {
            dataAdapter.setGroupCheckedImgOnclick(new NoscrollExpandableListAdapter.GroupCheckedImgOnclick() {
                @Override
                public void onClick(int groupPosition) {
                    if (null != sidesSlidGroupCheckListener) {
                        sidesSlidGroupCheckListener.OnClick(groupPosition);
                    }
                }
            });
            dataAdapter.setChildCheckedImgOnclick(new NoscrollExpandableListAdapter.ChildCheckedImgOnclick() {
                @Override
                public void onClick(int groupPosition, int childPosition) {
                    if (null != sidesSlidChildCheckListener) {
                        sidesSlidChildCheckListener.OnClick(groupPosition, childPosition);
                    }
                }
            });
        } else if (2 == type) {
            expressAdapter.setChildCheckedImgOnclick(new ExpressAdapter.ChildCheckedImgOnclick() {
                @Override
                public void onClick(int childPosition) {
                    if (null != sidesSlidNoGroupCheckListener) {
                        sidesSlidNoGroupCheckListener.OnClick(childPosition);
                    }
                }
            });
        }
    }

    //TODO 设置头部数据
    public void setHeaderData(List<FourSidesSlidListTitileModel> list) {
        headerList.clear();
        headerList = list;
        headerAdapter.setData(headerList);
    }

    //TODO 设置底下运单数据(含有包的)
    public void setContentData(List<ExpressPackageModel> list) {
        List<ExpressPackageModel> list1 = new ArrayList<>();
        list1.addAll(list);
        lv_header.setVisibility(VISIBLE);
        lv_noPackagedata.setVisibility(GONE);
        data_noPackagehorizontal.setVisibility(GONE);
        data_horizontal.setVisibility(VISIBLE);
        listData.clear();
        listData = list1;
        dataAdapter.setData(listData);
    }

    //TODO 设置底部数据(纯子单，没有包)
    public void setContentDataForNoPackage(List<ExpressModel> list) {
        List<ExpressModel> list1 = new ArrayList<>();
        list1.addAll(list);
        lv_header.setVisibility(GONE);
        lv_noPackagedata.setVisibility(VISIBLE);
        data_noPackagehorizontal.setVisibility(VISIBLE);
        data_horizontal.setVisibility(GONE);
        noPackageListData.clear();
        noPackageListData = list1;
        expressAdapter.setData(noPackageListData);
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

    public int getType() {
        return type;
    }
}
