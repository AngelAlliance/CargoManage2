package com.sz.ljs.inventory.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.adapter.MenuAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.ExpressModel;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.common.model.MenuModel;
import com.sz.ljs.common.model.OrderModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.inventory.R;
import com.sz.ljs.inventory.model.ResultBean;
import com.sz.ljs.inventory.presenter.InventoryPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    private InventoryPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mPresenter = new InventoryPresenter();
        getPankuHeaderData();
//        panKulistData.add(new ExpressModel(false, "", "正常走货", 104343
//                , 105550, 12, 11, 4, 2, 1));
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
                    if ( "false".equals(panKulistData.get(childPosition).getIsSelect())) {
                        panKulistData.get(childPosition).setIsSelect("true");
                    } else if ( "true".equals(panKulistData.get(childPosition).getIsSelect())){
                        panKulistData.get(childPosition).setIsSelect("false");
                    }
                    fs_cxkcxx_list.setContentDataForNoPackage(panKulistData);
                }
            }
        });

        et_yundanhao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= GenApi.ScanNumberLeng) {
                    //TODO 当运单号大于8位的时候就开始请求数据
                    getOrderByNumber();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        panKuHeaderList.add(new FourSidesSlidListTitileModel(1,getResources().getString(R.string.str_gx)
                , "", getResources().getString(R.string.str_zzzt)
                , getResources().getString(R.string.str_ydh), getResources().getString(R.string.str_zdtm)
                , getResources().getString(R.string.str_js), getResources().getString(R.string.str_shizhong)
                , getResources().getString(R.string.str_ckg)));
    }

    //TODO 根据运单号请求运单数据
    private void getOrderByNumber() {
        mPresenter.getFindExpressByID(et_yundanhao.getText().toString().trim())
                .compose(this.<ResultBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean result) throws Exception {
                        if (0 == result.getCode()) {
//                            Utils.showToast(InventoryActivity.this, result.getMsg());
                            Toast.makeText(InventoryActivity.this,result.getMsg(),Toast.LENGTH_LONG).show();
                        } else if (1 == result.getCode()) {
                            handDepltListResult(result);

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

    //TODO 处理运单数据
    private void handDepltListResult(ResultBean result) {
        //TODO 先把子单数据遍历出来
        if (null != result.getData().getExpressEntity()) {
//            panKulistData.clear();
            result.getData().getExpressEntity().setBs_id("1");
            result.getData().getExpressEntity().setShipper_hawbcode("xiajiba");
            result.getData().getExpressEntity().setShipper_pieces("85");
            result.getData().getExpressEntity().setChild_number("66");
            result.getData().getExpressEntity().setShipper_pieces("85");
            result.getData().getExpressEntity().setServer_id("44");
            panKulistData.add(result.getData().getExpressEntity());
            fs_cxkcxx_list.setContentDataForNoPackage(panKulistData);
        }

    }
    }
