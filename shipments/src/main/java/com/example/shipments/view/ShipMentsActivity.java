package com.example.shipments.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    private EditText et_qudao, et_yundanhao, et_fahuozhan, et_xiayizhan, et_shouhuofuwushao, et_fahuoshijian, et_yunshubianhao;
    private LinearLayout ll_qudao, ll_zhudanbuju, ll_xiayizhan, ll_shouhuofuwushao;
    private ImageView iv_qudao, iv_scan, iv_xiayizhan, iv_shouhuofuwushao;
    private GridView gv_menu;
    private FourSidesSlidingListView fs_listView;
    private TextView tv_zongjianshu, tv_zongshizhong, tv_zongcaiji;
    private Button btn_queding, btn_guanbi;
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
        et_fahuozhan = (EditText) findViewById(R.id.et_fahuozhan);
        et_xiayizhan = (EditText) findViewById(R.id.et_xiayizhan);
        et_shouhuofuwushao = (EditText) findViewById(R.id.et_shouhuofuwushao);
        et_fahuoshijian = (EditText) findViewById(R.id.et_fahuoshijian);
        et_yunshubianhao = (EditText) findViewById(R.id.et_yunshubianhao);
        ll_zhudanbuju = (LinearLayout) findViewById(R.id.ll_zhudanbuju);
        ll_xiayizhan = (LinearLayout) findViewById(R.id.ll_xiayizhan);
        ll_shouhuofuwushao = (LinearLayout) findViewById(R.id.ll_shouhuofuwushao);
        iv_xiayizhan = (ImageView) findViewById(R.id.iv_xiayizhan);
        iv_shouhuofuwushao = (ImageView) findViewById(R.id.iv_shouhuofuwushao);
        tv_zongjianshu = (TextView) findViewById(R.id.tv_zongjianshu);
        tv_zongshizhong = (TextView) findViewById(R.id.tv_zongshizhong);
        tv_zongcaiji = (TextView) findViewById(R.id.tv_zongcaiji);
        btn_queding = (Button) findViewById(R.id.btn_queding);
        btn_guanbi = (Button) findViewById(R.id.btn_guanbi);
    }

    private void setListener() {
        ll_qudao.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        ll_xiayizhan.setOnClickListener(this);
        ll_shouhuofuwushao.setOnClickListener(this);
        btn_queding.setOnClickListener(this);
        btn_guanbi.setOnClickListener(this);
        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ID = dcyMenuList.get(position).getId();
                if (ID == 1) {
                    //TODO  撤销

                } else if (ID == 2) {
                    //TODO  生成主单
                    fs_listView.setVisibility(View.GONE);
                    ll_zhudanbuju.setVisibility(View.VISIBLE);
                } else if (ID == 3) {
                    //TODO  刷新

                }
            }
        });
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
        } else if (id == R.id.ll_xiayizhan) {
            //TODO 下一站

        } else if (id == R.id.ll_shouhuofuwushao) {
            //TODO 收货商

        } else if (id == R.id.btn_queding) {
            //TODO 确定

        } else if (id == R.id.btn_guanbi) {
            //TODO 关闭
            fs_listView.setVisibility(View.VISIBLE);
            ll_zhudanbuju.setVisibility(View.GONE);
        }
    }


    //TODO 设置待出运菜单
    private void setDaiChuYunMenu() {
        dcyMenuList.add(new MenuModel(1, getResources().getString(R.string.str_cx), R.mipmap.ic_chexiao));
        dcyMenuList.add(new MenuModel(2, getResources().getString(R.string.str_sczd), R.mipmap.ic_tichu));
        dcyMenuList.add(new MenuModel(3, getResources().getString(R.string.str_sx), R.mipmap.ic_shuaxin));
    }
}
