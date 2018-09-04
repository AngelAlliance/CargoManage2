package com.sz.ljs.cargomanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sz.ljs.articlescan.view.ArticleScanActivity;
import com.sz.ljs.base.service.ScanService;
import com.sz.ljs.cargomanage.contract.LoginContract;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.inventory.view.InventoryActivity;
import com.sz.ljs.shipments.view.ShipMentsActivity;
import com.ljs.examinegoods.view.ExamineGoodsActivity;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.cargomanage.R;
import com.sz.ljs.cargomanage.adapter.HomeMenuAdapter;
import com.sz.ljs.cargomanage.model.HomeMenuModel;
import com.sz.ljs.cargomanage.model.ScanNumberLengModel;
import com.sz.ljs.cargomanage.presenter.LoginPresenter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.packgoods.view.PackGoodsActivity;
import com.sz.ljs.patchlabel.view.PatchlabelActivity;
import com.sz.ljs.setting.view.SettingActivity;
import com.sz.ljs.warehousing.view.WareHousingActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements LoginContract.View {
    private GridView gv_homeMenu;
    private List<HomeMenuModel> homeMenuList = new ArrayList<>();
    private HomeMenuAdapter homeMenuAdapter;
    private LoginPresenter mPresenter;
    private AlertDialog alertDialog;
    private WaitingDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getHomeMenu(0);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mPresenter = new LoginPresenter(this);
        waitingDialog = new WaitingDialog(this);
        gv_homeMenu = (GridView) findViewById(R.id.gv_homeMenu);
        homeMenuAdapter = new HomeMenuAdapter(this, homeMenuList);
        gv_homeMenu.setAdapter(homeMenuAdapter);
    }

    private void initData() {
        mPresenter.getScanNumberLeng();
//        //TODO 启动扫描服务
        Intent newIntent = new Intent(MainActivity.this, ScanService.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(newIntent);
    }

    @Override
    public void onResult(int Id, String result) {
        switch (Id) {
            case LoginContract.REQUEST_FAIL_ID:
                showTipeDialog(result);
                break;
            case LoginContract.REQUEST_SUCCESS_ID:
                break;
        }
    }

    private void setListener() {
        gv_homeMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != homeMenuList && homeMenuList.size() > 0) {
                    menuJump(homeMenuList.get(position).getId());
                }
            }
        });
    }

    //TODO 点击菜单跳转，根据id来进行跳转
    private void menuJump(int id) {
        switch (id) {
            case 0: {
                //TODO 验货
                BaseApplication.startActivity(ExamineGoodsActivity.class);
            }
            break;
            case 1: {
                //TODO 入库
                BaseApplication.startActivity(WareHousingActivity.class);
            }
            break;
            case 2: {
                //TODO 打包
                BaseApplication.startActivity(PackGoodsActivity.class);
            }
            break;
            case 3: {
                //TODO 出库
                BaseApplication.startActivity(ShipMentsActivity.class);
            }
            break;
            case 4: {
                //TODO 盘库
                BaseApplication.startActivity(InventoryActivity.class);
            }
            break;
            case 5: {
                //TODO 到件扫描
                BaseApplication.startActivity(ArticleScanActivity.class);
            }
            break;
            case 6: {
                //TODO 补打标签
                BaseApplication.startActivity(PatchlabelActivity.class);
            }
            break;
            case 7: {
                //TODO 设置
                BaseApplication.startActivity(SettingActivity.class);
            }
            break;
        }
    }

    //TODO 获取首页菜单，根据用户权限来获取
    private void getHomeMenu(int type) {
        homeMenuList.clear();
        switch (type) {
            case 0: {
                homeMenuList.add(new HomeMenuModel(0, getResources().getString(R.string.str_yh), R.mipmap.icon_yanhuo));
                homeMenuList.add(new HomeMenuModel(1, getResources().getString(R.string.str_rk), R.mipmap.icon_ruku));
                homeMenuList.add(new HomeMenuModel(2, getResources().getString(R.string.str_db), R.mipmap.icon_packgoods));
                homeMenuList.add(new HomeMenuModel(3, getResources().getString(R.string.str_ck), R.mipmap.icon_chuku));
                homeMenuList.add(new HomeMenuModel(4, getResources().getString(R.string.str_pk), R.mipmap.icon_panku));
                homeMenuList.add(new HomeMenuModel(5, getResources().getString(R.string.str_djsm), R.mipmap.icon_daojiansaomiao));
                homeMenuList.add(new HomeMenuModel(6, getResources().getString(R.string.str_bdbq), R.mipmap.icon_budabiaoqian));
                homeMenuList.add(new HomeMenuModel(7, getResources().getString(R.string.str_sz), R.mipmap.icon_setting));
            }
            break;
        }
    }


    @Override
    public void showWaiting(boolean show) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(show);
        }
    }

    public void showTipeDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog = new AlertDialog(MainActivity.this)
                        .builder()
                        .setTitle(getResources().getString(R.string.str_alter))
                        .setMsg(msg)
                        .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dissmiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

}
