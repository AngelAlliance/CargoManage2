package com.sz.ljs.inventory.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.adapter.MenuAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.ExpressModel;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.model.MenuModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.inventory.R;
import com.sz.ljs.inventory.contract.InventoryContract;
import com.sz.ljs.inventory.model.BsListModel;
import com.sz.ljs.inventory.model.FindExpressRuesltModel;
import com.sz.ljs.inventory.model.InventoryModel;
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

public class InventoryActivity extends BaseActivity implements InventoryContract.View, View.OnClickListener {
    private EditText et_qudao, et_yundanhao;
    private TextView tv_zongshizhong, tv_piaoshu, tv_zongjianshu;
    private LinearLayout ll_qudao;
    private ImageView iv_qudao, iv_scan;
    private FourSidesSlidingListView fs_cxkcxx_list;
    private RelativeLayout rl_gxkczt;
    private List<FourSidesSlidListTitileModel> panKuHeaderList = new ArrayList<>();
    private List<ExpressModel> panKulistData = new ArrayList<>();
    private List<MenuModel> pkMenuList = new ArrayList<>();
    private InventoryPresenter mPresenter;
    private WaitingDialog waitingDialog;
    private AlertDialog alertDialog;
    private List<ExpressModel> selectList = new ArrayList<>();
    private List<BsListModel> bsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mPresenter = new InventoryPresenter(this);
        waitingDialog = new WaitingDialog(this);
        getPankuHeaderData();
        et_qudao = (EditText) findViewById(R.id.et_qudao);
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        ll_qudao = (LinearLayout) findViewById(R.id.ll_qudao);
        iv_qudao = (ImageView) findViewById(R.id.iv_qudao);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        fs_cxkcxx_list = (FourSidesSlidingListView) findViewById(R.id.fs_cxkcxx_list);
        rl_gxkczt = (RelativeLayout) findViewById(R.id.rl_gxkczt);
        tv_zongshizhong = (TextView) findViewById(R.id.tv_zongshizhong);
        tv_zongjianshu = (TextView) findViewById(R.id.tv_zongjianshu);
    }

    private void setListener() {
        ll_qudao.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        rl_gxkczt.setOnClickListener(this);
        fs_cxkcxx_list.setSidesSlidNoGroupCheckListener(new FourSidesSlidingListView.SidesSlidNoGroupCheckListener() {
            @Override
            public void OnClick(int childPosition) {
                if (null != panKulistData && panKulistData.size() > 0) {
                    if ("false".equals(panKulistData.get(childPosition).getIsSelect())) {
                        panKulistData.get(childPosition).setIsSelect("true");
                        panKulistData.get(childPosition).setOrder_status("选中");
                    } else if ("true".equals(panKulistData.get(childPosition).getIsSelect())) {
                        panKulistData.get(childPosition).setIsSelect("false");
                        panKulistData.get(childPosition).setOrder_status("已收件");
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
                    mPresenter.getFindExpressByID(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initData() {
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
        } else if (id == R.id.rl_gxkczt) { //更新库存状态
            addExpressTrack();
        }
    }

    //TODO 批量提交盘库
    private void addExpressTrack() {
        if (null != panKulistData && panKulistData.size() > 0) {
            selectList.clear();
            bsList.clear();
            for (ExpressModel model : panKulistData) {
                if ("true".equals(model.getIsSelect())) {
                    selectList.add(model);
                    bsList.add(new BsListModel(model.getBs_id()));
                }
            }
            if (null != bsList && bsList.size() > 0) {
                mPresenter.addExpressTrack(bsList);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog(InventoryActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg("请先选择需要盘库的运单")
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
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialog = new AlertDialog(InventoryActivity.this)
                            .builder()
                            .setTitle(getResources().getString(R.string.str_alter))
                            .setMsg("请先选择需要盘库的运单")
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

    private void getPankuHeaderData() {
        panKuHeaderList.add(new FourSidesSlidListTitileModel(1, getResources().getString(R.string.str_gx)
                , "", getResources().getString(R.string.str_zzzt)
                , getResources().getString(R.string.str_ydh), getResources().getString(R.string.str_zdtm)
                , getResources().getString(R.string.str_js), getResources().getString(R.string.str_shizhong)
                , getResources().getString(R.string.str_ckg)));
    }

    //TODO 设置总实重和总件数
    private void setweightAndPices() {
        if (null != panKulistData && panKulistData.size() > 0) {
            tv_zongjianshu.setText("" + panKulistData.size());
            double weight = 0;
            for (ExpressModel model : panKulistData) {
                if (!TextUtils.isEmpty(model.getOutvolume_grossweight())) {
                    weight = weight + Double.valueOf(model.getOutvolume_grossweight());
                }
            }
            tv_zongshizhong.setText("" + weight);
        } else {
            tv_zongjianshu.setText("");
            tv_zongshizhong.setText("");
        }
    }

    @Override
    public <T> void OnSuccess(T result) {
        int id = (Integer) result;
        switch (id) {
            case InventoryContract.REQUEST_SUCCESS_ID:
                //TODO 根据运单号查询数据成功
                if (null != InventoryModel.getInstance().getExpressList()
                        && InventoryModel.getInstance().getExpressList().size() > 0) {
                    panKulistData.clear();
                    panKulistData.addAll(InventoryModel.getInstance().getExpressList());
                    fs_cxkcxx_list.setContentDataForNoPackage(panKulistData);
                    setweightAndPices();
                }
                break;
            case InventoryContract.ADD_EXPRESSS_TRACK_SUCCESS:
                //TODO 批量提交盘库成功
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog = new AlertDialog(InventoryActivity.this)
                                        .builder()
                                        .setTitle(getResources().getString(R.string.str_alter))
                                        .setMsg(mPresenter.getMessage())
                                        .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (null != selectList && selectList.size() > 0) {
                                                    panKulistData.removeAll(selectList);
                                                    InventoryModel.getInstance().removeExpressList(selectList);
                                                    selectList.clear();
                                                    bsList.clear();
                                                    fs_cxkcxx_list.setContentDataForNoPackage(panKulistData);
                                                    setweightAndPices();
                                                }
                                                alertDialog.dissmiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        });
                    }
                });
                break;
        }
    }

    @Override
    public <T> void OnError(T Error) {
        int id = (Integer) Error;
        switch (id) {
            case InventoryContract.REQUEST_FAIL_ID:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog = new AlertDialog(InventoryActivity.this)
                                        .builder()
                                        .setTitle(getResources().getString(R.string.str_alter))
                                        .setMsg(mPresenter.getMessage())
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
                });
                break;
        }
    }

    @Override
    public void showWaiting(boolean show) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(show);
        }
    }
}
