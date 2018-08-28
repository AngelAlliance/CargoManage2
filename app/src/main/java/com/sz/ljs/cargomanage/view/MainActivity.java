package com.sz.ljs.cargomanage.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private GridView gv_homeMenu;
    private List<HomeMenuModel> homeMenuList = new ArrayList<>();
    private HomeMenuAdapter homeMenuAdapter;
    private LoginPresenter mPresenter;

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
        mPresenter = new LoginPresenter();
        gv_homeMenu = (GridView) findViewById(R.id.gv_homeMenu);
        homeMenuAdapter = new HomeMenuAdapter(this, homeMenuList);
        gv_homeMenu.setAdapter(homeMenuAdapter);
    }

    private void initData() {
        getScanNumberLeng();
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
            case 2:{
                //TODO 打包
                BaseApplication.startActivity(PackGoodsActivity.class);
            }
            break;
            case 3:{
                //TODO 出库
                BaseApplication.startActivity(ShipMentsActivity.class);
            }
            break;
            case 6:{
                //TODO 补打标签
                BaseApplication.startActivity(PatchlabelActivity.class);
            }
            case 7:{
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
                homeMenuList.add(new HomeMenuModel(0, getResources().getString(R.string.str_yh), R.mipmap.common_lading_live1));
                homeMenuList.add(new HomeMenuModel(1, getResources().getString(R.string.str_rk), R.mipmap.common_lading_live1));
                homeMenuList.add(new HomeMenuModel(2, getResources().getString(R.string.str_db), R.mipmap.common_lading_live1));
                homeMenuList.add(new HomeMenuModel(3, getResources().getString(R.string.str_ck), R.mipmap.common_lading_live1));
                homeMenuList.add(new HomeMenuModel(4, getResources().getString(R.string.str_pk), R.mipmap.common_lading_live1));
                homeMenuList.add(new HomeMenuModel(5, getResources().getString(R.string.str_djsm), R.mipmap.common_lading_live1));
                homeMenuList.add(new HomeMenuModel(6, getResources().getString(R.string.str_bdbq), R.mipmap.common_lading_live1));
                homeMenuList.add(new HomeMenuModel(7, getResources().getString(R.string.str_sz), R.mipmap.common_lading_live1));
            }
            break;
        }
    }

    //TODO 返回输入运单N位得时候调用接口
    private void getScanNumberLeng() {
        mPresenter.getScanNumberLeng()
                .compose(this.<ScanNumberLengModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ScanNumberLengModel>() {
                    @Override
                    public void accept(ScanNumberLengModel result) throws Exception {
                        if (0 == result.getCode()) {
                            Utils.showToast(MainActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            if(!TextUtils.isEmpty(result.getData())){
                                Log.i("请求运单号位数","length="+result.getData());
                                GenApi.ScanNumberLeng=Integer.parseInt(result.getData());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //获取失败，提示
                        Utils.showToast(getBaseActivity(), R.string.str_qqsb);
                    }
                });
    }

}
