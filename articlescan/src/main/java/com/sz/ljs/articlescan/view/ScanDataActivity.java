package com.sz.ljs.articlescan.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sz.ljs.articlescan.R;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.adapter.MenuAdapter;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.model.MenuModel;
import com.sz.ljs.common.view.FourSidesSlidingListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mr. Duan
 * Date: 2018/8/30
 * Description:已扫描数据
 */

public class ScanDataActivity extends BaseActivity{
    private List<MenuModel> dcyMenuList = new ArrayList<>();
    private List<FourSidesSlidListTitileModel> yiSaoMiaoHeaderList = new ArrayList<>();
    private FourSidesSlidingListView fs_yisaomiao_list;
    private MenuAdapter dcyMenuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_data);
        initView();
        setListener();
        initData();

    }

    private void initView() {
        fs_yisaomiao_list = (FourSidesSlidingListView) findViewById(R.id.fs_yisaomiao_list);
        getYiSaoMiaoHeaderData();

    }
    private void initData() {
        fs_yisaomiao_list.setHeaderData(yiSaoMiaoHeaderList);
    }

    private void setListener() {


    }


    //TODO 设置打包界面已扫描界面数据标题栏
    private void getYiSaoMiaoHeaderData() {
        yiSaoMiaoHeaderList.add(new FourSidesSlidListTitileModel(2, getResources().getString(R.string.str_gx)
                , getResources().getString(R.string.str_bbh), ""
                , getResources().getString(R.string.str_ydh), getResources().getString(R.string.str_zdtm)
                , getResources().getString(R.string.str_js), getResources().getString(R.string.str_shizhong)
                , getResources().getString(R.string.str_ckg)));
    }

}
