package com.sz.ljs.packgoods.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.packgoods.R;
import com.sz.ljs.common.adapter.MenuAdapter;
import com.sz.ljs.common.model.MenuModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujs on 2018/8/16.
 * 打包界面
 */

public class PackGoodsActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_qudao, et_yundanhao;
    private TextView tv_yundanhao;
    private LinearLayout ll_qudao, ll_daichuyun, ll_yisaomiao;
    private ImageView iv_qudao, iv_scan;
    private GridView gv_daichuyun_menu, gv_yisaomiao_menu;
    private FourSidesSlidingListView fs_daichuyun_list, fs_yisaomiao_list;
    private Button btn_daichuhuo, btn_yisaomiaobao;
    private List<FourSidesSlidListTitileModel> headerList = new ArrayList<>();
    private List<ExpressPackageModel> listData = new ArrayList<>();
    private List<MenuModel> dcyMenuList = new ArrayList<>();
    private List<MenuModel> ysmMenuList = new ArrayList<>();
    private MenuAdapter dcyMenuAdapter;
    private MenuAdapter ysmMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packgoods);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        setDaiChuYunMenu();
        setYiSaoMiaoMenu();
        et_qudao = (EditText) findViewById(R.id.et_qudao);
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        tv_yundanhao = (TextView) findViewById(R.id.tv_yundanhao);
        ll_qudao = (LinearLayout) findViewById(R.id.ll_qudao);
        ll_daichuyun = (LinearLayout) findViewById(R.id.ll_daichuyun);
        ll_yisaomiao = (LinearLayout) findViewById(R.id.ll_yisaomiao);
        iv_qudao = (ImageView) findViewById(R.id.iv_qudao);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        gv_daichuyun_menu = (GridView) findViewById(R.id.gv_daichuyun_menu);
        gv_yisaomiao_menu = (GridView) findViewById(R.id.gv_yisaomiao_menu);
        fs_daichuyun_list = (FourSidesSlidingListView) findViewById(R.id.fs_daichuyun_list);
        fs_yisaomiao_list = (FourSidesSlidingListView) findViewById(R.id.fs_yisaomiao_list);
        btn_daichuhuo = (Button) findViewById(R.id.btn_daichuhuo);
        btn_yisaomiaobao = (Button) findViewById(R.id.btn_yisaomiaobao);
    }

    private void setListener() {
        ll_qudao.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        btn_daichuhuo.setOnClickListener(this);
        btn_yisaomiaobao.setOnClickListener(this);
    }

    private void initData() {
        dcyMenuAdapter = new MenuAdapter(this, dcyMenuList);
        gv_daichuyun_menu.setAdapter(dcyMenuAdapter);
        ysmMenuAdapter = new MenuAdapter(this, ysmMenuList);
        gv_yisaomiao_menu.setAdapter(ysmMenuAdapter);
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
        } else if (id == R.id.btn_daichuhuo) { //待出货
            ll_daichuyun.setVisibility(View.VISIBLE);
            ll_yisaomiao.setVisibility(View.GONE);
            tv_yundanhao.setText(getResources().getString(R.string.str_tmbh));
        } else if (id == R.id.btn_yisaomiaobao) {  //已扫描
            ll_daichuyun.setVisibility(View.GONE);
            ll_yisaomiao.setVisibility(View.VISIBLE);
            tv_yundanhao.setText(getResources().getString(R.string.str_ydh));
        }
    }

    //TODO 设置待出运菜单
    private void setDaiChuYunMenu() {
        dcyMenuList.add(new MenuModel(1, getResources().getString(R.string.str_cx), R.mipmap.ic_chexiao));
        dcyMenuList.add(new MenuModel(2, getResources().getString(R.string.str_db), R.mipmap.ic_dabao));
        dcyMenuList.add(new MenuModel(3, getResources().getString(R.string.str_sx), R.mipmap.ic_shuaxin));
    }

    //TODO 设置已扫描菜单
    private void setYiSaoMiaoMenu() {
        ysmMenuList.add(new MenuModel(1, getResources().getString(R.string.str_cx), R.mipmap.ic_chexiao));
        ysmMenuList.add(new MenuModel(2, getResources().getString(R.string.str_sx), R.mipmap.ic_shuaxin));
        ysmMenuList.add(new MenuModel(3, getResources().getString(R.string.str_tc), R.mipmap.ic_tichu));
        ysmMenuList.add(new MenuModel(4, getResources().getString(R.string.str_cb), R.mipmap.ic_chaibao));
        ysmMenuList.add(new MenuModel(5, getResources().getString(R.string.str_cz), R.mipmap.ic_tichu));
    }
}
