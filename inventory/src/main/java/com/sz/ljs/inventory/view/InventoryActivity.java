package com.sz.ljs.inventory.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.adapter.MenuAdapter;
import com.sz.ljs.common.model.ExpressModel;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.model.MenuModel;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.inventory.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujs on 2018/8/16.
 * 盘库界面
 */

public class InventoryActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_qudao, et_yundanhao;
    private LinearLayout ll_qudao;
    private ImageView iv_qudao, iv_scan;
    private GridView gv_cxkcxx_menu;
    private FourSidesSlidingListView fs_cxkcxx_list;
    private RelativeLayout rl_gxkczt;
    private List<FourSidesSlidListTitileModel> panKuHeaderList = new ArrayList<>();
    private List<ExpressModel> panKulistData = new ArrayList<>();
    private List<MenuModel> pkMenuList = new ArrayList<>();
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        getPankuHeaderData();
        panKulistData.add(new ExpressModel(false, "", "正常走货", 104343
                , 105550, 12, 11, 4, 2, 1));
        et_qudao = (EditText) findViewById(R.id.et_qudao);
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        ll_qudao = (LinearLayout) findViewById(R.id.ll_qudao);
        iv_qudao = (ImageView) findViewById(R.id.iv_qudao);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        gv_cxkcxx_menu = findViewById(R.id.gv_cxkcxx_menu);
        fs_cxkcxx_list = (FourSidesSlidingListView)findViewById(R.id.fs_cxkcxx_list);
        rl_gxkczt = (RelativeLayout)findViewById(R.id.rl_gxkczt);
    }

    private void setListener() {
        ll_qudao.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        rl_gxkczt.setOnClickListener(this);
        fs_cxkcxx_list.setSidesSlidNoGroupCheckListener(new FourSidesSlidingListView.SidesSlidNoGroupCheckListener() {
            @Override
            public void OnClick(int childPosition) {
                if (null != panKulistData && panKulistData.size() > 0) {
                    if (false == panKulistData.get(childPosition).isChecked()) {
                        panKulistData.get(childPosition).setChecked(true);
                    } else {
                        panKulistData.get(childPosition).setChecked(false);
                    }
                    fs_cxkcxx_list.setContentDataForNoPackage(panKulistData);
                }
            }
        });
    }

    private void initData() {
        menuAdapter = new MenuAdapter(this, pkMenuList);
        gv_cxkcxx_menu.setAdapter(menuAdapter);
        fs_cxkcxx_list.setHeaderData(panKuHeaderList);
        fs_cxkcxx_list.setContentDataForNoPackage(panKulistData);
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
        } else if (id == R.id.rl_gxkczt){ //更新库存状态

        }

    }

    private void getPankuHeaderData() {
        panKuHeaderList.add(new FourSidesSlidListTitileModel(getResources().getString(R.string.str_gx)
                , "", getResources().getString(R.string.str_zzzt)
                , getResources().getString(R.string.str_ydh), getResources().getString(R.string.str_zdtm)
                , getResources().getString(R.string.str_js), getResources().getString(R.string.str_shizhong)
                , getResources().getString(R.string.str_ckg)));
    }

}
