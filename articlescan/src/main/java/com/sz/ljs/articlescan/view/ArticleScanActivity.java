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
 * Description:
 */

public class ArticleScanActivity extends BaseActivity{
    private List<MenuModel> dcyMenuList = new ArrayList<>();
    private List<FourSidesSlidListTitileModel> yiSaoMiaoHeaderList = new ArrayList<>();
    private FourSidesSlidingListView  fs_yisaomiao_list;
    private GridView gv_menu;
    private MenuAdapter dcyMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_scan);

        initView();
        setListener();
        initData();

    }

    private void initView() {
        gv_menu=(GridView)findViewById(R.id.gv_menu);
        fs_yisaomiao_list = (FourSidesSlidingListView) findViewById(R.id.fs_yisaomiao_list);
        setDaiChuYunMenu();
        getYiSaoMiaoHeaderData();

    }
    private void initData() {
        dcyMenuAdapter = new MenuAdapter(this, dcyMenuList);
        gv_menu.setAdapter(dcyMenuAdapter);
        fs_yisaomiao_list.setHeaderData(yiSaoMiaoHeaderList);
    }

    private void setListener() {

        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int menuId = dcyMenuList.get(position).getId();
                switch (menuId){
                    case 1:
                        Intent intent=new Intent(ArticleScanActivity.this,ScanDataActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    //TODO 设置待出运菜单
    private void setDaiChuYunMenu() {
        dcyMenuList.add(new MenuModel(1, getResources().getString(R.string.str_ysmsj), R.mipmap.icon_panku));
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
