package com.example.shipments.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shipments.R;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.adapter.MenuAdapter;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.model.MenuModel;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.ScanView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujs on 2018/8/16.
 * 出库界面
 */

public class ShipMentsActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_qudao, et_yundanhao;
    private LinearLayout ll_qudao;
    private ImageView iv_qudao, iv_scan;
    private GridView gv_menu;
    private FourSidesSlidingListView fs_listView;
    private List<MenuModel> dcyMenuList = new ArrayList<>();
    private MenuAdapter dcyMenuAdapter;
    private List<FourSidesSlidListTitileModel> headerList = new ArrayList<>();
    private List<ExpressPackageModel> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipments);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        setDaiChuYunMenu();
        et_qudao = (EditText) findViewById(R.id.et_qudao);
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        ll_qudao = (LinearLayout) findViewById(R.id.ll_qudao);
        iv_qudao = (ImageView) findViewById(R.id.iv_qudao);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        gv_menu = (GridView) findViewById(R.id.gv_menu);
        fs_listView = (FourSidesSlidingListView) findViewById(R.id.fs_listView);
    }

    private void setListener() {
        ll_qudao.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
    }

    private void initData() {
        dcyMenuAdapter = new MenuAdapter(this, dcyMenuList);
        gv_menu.setAdapter(dcyMenuAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_qudao) { //渠道

        } else if (id == R.id.iv_scan) { //扫描
            ScanView.ScanView(new ScanView.SacnCallBack() {
                @Override
                public void onResult(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        et_yundanhao.setText(result);
                    }
                }
            });
        }
    }


    //TODO 设置待出运菜单
    private void setDaiChuYunMenu() {
        dcyMenuList.add(new MenuModel(1, getResources().getString(R.string.str_cx), R.mipmap.ic_chexiao));
        dcyMenuList.add(new MenuModel(2, getResources().getString(R.string.str_sczd), R.mipmap.ic_tichu));
        dcyMenuList.add(new MenuModel(3, getResources().getString(R.string.str_sx), R.mipmap.ic_shuaxin));
    }
}
