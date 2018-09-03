package com.sz.ljs.packgoods.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.ExpressModel;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.packgoods.R;
import com.sz.ljs.common.adapter.MenuAdapter;
import com.sz.ljs.common.model.MenuModel;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.packgoods.contract.PackgoodsContract;
import com.sz.ljs.packgoods.model.BagPutBusinessReqModel;
import com.sz.ljs.packgoods.model.GsonAddBussinessPackageModel;
import com.sz.ljs.packgoods.model.GsonServiceChannelModel;
import com.sz.ljs.packgoods.model.PackGoodsModel;
import com.sz.ljs.packgoods.model.PackGoodsRequestBsListMode;
import com.sz.ljs.packgoods.presenter.PackgoodsPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liujs on 2018/8/16.
 * 打包界面
 */

public class PackGoodsActivity extends BaseActivity implements View.OnClickListener,PackgoodsContract.View {
    private EditText et_yundanhao;
    private TextView tv_yundanhao, et_qudao;
    private LinearLayout ll_qudao, ll_daichuyun, ll_yisaomiao;
    private ImageView iv_qudao, iv_scan;
    private GridView gv_daichuyun_menu, gv_yisaomiao_menu;
    private FourSidesSlidingListView fs_daichuyun_list, fs_yisaomiao_list;
    private Button btn_daichuhuo, btn_yisaomiaobao;
    private List<FourSidesSlidListTitileModel> danChuYunHeaderList = new ArrayList<>();
    private List<ExpressModel> danChuYunlistData = new ArrayList<>();
    private List<FourSidesSlidListTitileModel> yiSaoMiaoHeaderList = new ArrayList<>();
    private List<ExpressPackageModel> yiSaoMiaolistData = new ArrayList<>();
    private List<MenuModel> dcyMenuList = new ArrayList<>();
    private List<MenuModel> ysmMenuList = new ArrayList<>();
    private MenuAdapter dcyMenuAdapter;
    private MenuAdapter ysmMenuAdapter;
    private PackgoodsPresenter mPresnter;
    private WaitingDialog waitingDialog;
    private AlertDialog alertDialog;
    private ListDialog dialog;
    private List<GsonServiceChannelModel.DataBean> serviceChannelList = new ArrayList<>();
    private GsonServiceChannelModel.DataBean serviceChanneModel;
    private String expressCode; //最后一个选择的运单
    private String packGoodsexpressCode; //已扫描界面最后一个选择的运单
    private String packGoodsCode; // 最后一个选择的包
    private String strExpressCode;//扫描的报编号
    private BagWeightDialog bagWeightDialog;
    private ExpressPackageModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packgoods);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        waitingDialog = new WaitingDialog(this);
        mPresnter = new PackgoodsPresenter(this);
        setDaiChuYunMenu();
        setYiSaoMiaoMenu();
        getDaiChuYunHeaderData();
        getYiSaoMiaoHeaderData();
        et_qudao = (TextView) findViewById(R.id.et_qudao);
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
        btn_daichuhuo.setBackgroundResource(R.color.secondary_color_cccccc);
    }

    private void setListener() {
        ll_qudao.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        btn_daichuhuo.setOnClickListener(this);
        btn_yisaomiaobao.setOnClickListener(this);
        et_yundanhao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() >= GenApi.ScanNumberLeng) {
                    handlerScanResult(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //TODO 设置待出运点击勾选按钮监听
        fs_daichuyun_list.setSidesSlidNoGroupCheckListener(new FourSidesSlidingListView.SidesSlidNoGroupCheckListener() {
            @Override
            public void OnClick(int childPosition) {
                if (null != danChuYunlistData && danChuYunlistData.size() > 0) {
                    if (TextUtils.isEmpty(danChuYunlistData.get(childPosition).getIsSelect()) || "false" == danChuYunlistData.get(childPosition).getIsSelect()) {
                        danChuYunlistData.get(childPosition).setIsSelect("true");
                        danChuYunlistData.get(childPosition).setOrder_status("选中");
                    } else if ("true" == danChuYunlistData.get(childPosition).getIsSelect()) {
                        danChuYunlistData.get(childPosition).setIsSelect("false");
                        danChuYunlistData.get(childPosition).setOrder_status("已收件");
                    }
                    expressCode = danChuYunlistData.get(childPosition).getShipper_hawbcode();
                    fs_daichuyun_list.setContentDataForNoPackage(danChuYunlistData);
                }
            }
        });

        //TODO 设置已扫描界面点击包层次勾选按钮监听
        fs_yisaomiao_list.setSidesSlidGroupCheckListener(new FourSidesSlidingListView.SidesSlidGroupCheckListener() {
            @Override
            public void OnClick(int groupPosition) {
                //TODO 点击父勾选按钮
                if (null != yiSaoMiaolistData && yiSaoMiaolistData.size() > 0) {
                    if (TextUtils.isEmpty(yiSaoMiaolistData.get(groupPosition).getIsSelect())
                            || "false" == yiSaoMiaolistData.get(groupPosition).getIsSelect()) {
                        yiSaoMiaolistData.get(groupPosition).setIsSelect("true");
                        if (null != yiSaoMiaolistData.get(groupPosition).getCn_list()
                                && yiSaoMiaolistData.get(groupPosition).getCn_list().size() > 0) {
                            //TODO 将其所有子单状态全部设置成勾选状态
                            for (int i = 0; i < yiSaoMiaolistData.get(groupPosition).getCn_list().size(); i++) {
                                yiSaoMiaolistData.get(groupPosition).getCn_list().get(i).setIsSelect("true");
//                                yiSaoMiaolistData.get(groupPosition).getCn_list().get(i).setOrder_status("选中");
                            }
                        }
                    } else if ("true" == yiSaoMiaolistData.get(groupPosition).getIsSelect()) {
                        yiSaoMiaolistData.get(groupPosition).setIsSelect("false");
                        if (null != yiSaoMiaolistData.get(groupPosition).getCn_list()
                                && yiSaoMiaolistData.get(groupPosition).getCn_list().size() > 0) {
                            //TODO 将其所有子单状态全部取消勾选状态
                            for (int i = 0; i < yiSaoMiaolistData.get(groupPosition).getCn_list().size(); i++) {
                                yiSaoMiaolistData.get(groupPosition).getCn_list().get(i).setIsSelect("false");
//                                yiSaoMiaolistData.get(groupPosition).getCn_list().get(i).setOrder_status("已收件");
                            }
                        }
                    }
                    packGoodsexpressCode = "";//因为点击了包，这时候最后一个选择的就应该是包
                    packGoodsCode = yiSaoMiaolistData.get(groupPosition).getBag_lable_code();
                    fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
                }
            }
        });

        //TODO 设置已扫描界面点击包底下运单勾选按钮监听
        fs_yisaomiao_list.setSidesSlidChildCheckListener(new FourSidesSlidingListView.SidesSlidChildCheckListener() {
            @Override
            public void OnClick(int groupPosition, int childPosition) {
                //TODO 点击包底下的子单勾选按钮
                if (null != yiSaoMiaolistData && yiSaoMiaolistData.size() > 0
                        && null != yiSaoMiaolistData.get(groupPosition).getCn_list()
                        && yiSaoMiaolistData.get(groupPosition).getCn_list().size() > 0) {
                    if (TextUtils.isEmpty(yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).getIsSelect())
                            || "false".equals(yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).getIsSelect())) {
                        yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).setIsSelect("true");
//                        yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).setOrder_status("选中");
                    } else if ("true".equals(yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).getIsSelect())) {
                        yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).setIsSelect("false");
//                        yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).setOrder_status("已收件");
                    }
                }
                packGoodsCode = "";
                packGoodsexpressCode = yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).getShipper_hawbcode();
                fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
            }
        });

        //TODO 设置待出运菜单按钮监听
        gv_daichuyun_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int menuId = dcyMenuList.get(position).getId();
                switch (menuId) {
                    case 1:
                        //TODO 撤销
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showWaiting(true);
                            }
                        });
                        for (int i = 0; i < danChuYunlistData.size(); i++) {
                            if (danChuYunlistData.get(i).getShipper_hawbcode().equals(expressCode)) {
                                if ("false".equals(danChuYunlistData.get(i).getIsSelect())) {
                                    danChuYunlistData.get(i).setIsSelect("true");
                                    danChuYunlistData.get(i).setOrder_status("选中");
                                } else if ("true".equals(danChuYunlistData.get(i).getIsSelect())) {
                                    danChuYunlistData.get(i).setIsSelect("false");
                                    danChuYunlistData.get(i).setOrder_status("已收件");
                                }
                                expressCode = "";
                                fs_daichuyun_list.setContentDataForNoPackage(danChuYunlistData);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showWaiting(false);
                                    }
                                });
                                break;
                            }
                        }
                        break;
                    case 2:
                        //TODO 打包
                        alertDialog = new AlertDialog(PackGoodsActivity.this, true)
                                .builder()
                                .setTitle("请输入或者扫描包编号")
                                .setPositiveButton(getResources().getString(R.string.str_yes), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (null != v.getTag()) {
                                            strExpressCode = (String) v.getTag();
                                            alertDialog.dissmiss();
                                            packageGoods();
                                        } else {

                                        }
                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.str_cancel), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dissmiss();
                                    }
                                });
                        alertDialog.show();
                        break;
                    case 3:
                        //TODO 刷新
                        getDepltList();
                        break;
                }
            }
        });

        //TODO 设置已扫描界面菜单按钮监听
        gv_yisaomiao_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int menuId = ysmMenuList.get(position).getId();
                switch (menuId) {
                    case 1:
                        //TODO 撤销
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showWaiting(true);
                            }
                        });
                        if (!TextUtils.isEmpty(packGoodsexpressCode)) {
                            for (int i = 0; i < yiSaoMiaolistData.size(); i++) {
                                for (int j = 0; j < yiSaoMiaolistData.get(i).getCn_list().size(); j++) {
                                    if (yiSaoMiaolistData.get(i).getCn_list().get(j).getShipper_hawbcode().equals(packGoodsexpressCode)) {
                                        if ("false".equals(yiSaoMiaolistData.get(i).getCn_list().get(j).getIsSelect())) {
                                            yiSaoMiaolistData.get(i).getCn_list().get(j).setIsSelect("true");
                                            yiSaoMiaolistData.get(i).getCn_list().get(j).setOrder_status("选中");
                                        } else if ("true".equals(yiSaoMiaolistData.get(i).getCn_list().get(j).getIsSelect())) {
                                            yiSaoMiaolistData.get(i).getCn_list().get(j).setIsSelect("false");
                                            yiSaoMiaolistData.get(i).getCn_list().get(j).setOrder_status("已收件");
                                        }
                                        packGoodsexpressCode = "";
                                        fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showWaiting(false);
                                            }
                                        });
                                        break;
                                    }
                                }
                            }
                        } else if (!TextUtils.isEmpty(packGoodsCode)) {
                            for (int i = 0; i < yiSaoMiaolistData.size(); i++) {
                                if (yiSaoMiaolistData.get(i).getBag_lable_code().equals(packGoodsCode)) {
                                    if ("false".equals(yiSaoMiaolistData.get(i).getIsSelect())) {
                                        yiSaoMiaolistData.get(i).setIsSelect("true");
                                        for (int j = 0; j < yiSaoMiaolistData.get(i).getCn_list().size(); j++) {
                                            yiSaoMiaolistData.get(i).getCn_list().get(j).setIsSelect("true");
                                            yiSaoMiaolistData.get(i).getCn_list().get(j).setOrder_status("选中");
                                        }
                                    } else if ("true".equals(yiSaoMiaolistData.get(i).getIsSelect())) {
                                        yiSaoMiaolistData.get(i).setIsSelect("false");
                                        for (int j = 0; j < yiSaoMiaolistData.get(i).getCn_list().size(); j++) {
                                            yiSaoMiaolistData.get(i).getCn_list().get(j).setIsSelect("false");
                                            yiSaoMiaolistData.get(i).getCn_list().get(j).setOrder_status("已收件");
                                        }
                                    }
                                    packGoodsCode = "";
                                    fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showWaiting(false);
                                        }
                                    });
                                    break;
                                }
                            }
                        }

                        break;
                    case 2:
                        //TODO 刷新
                        getDepltList();
                        break;
                    case 3:
                        //TODO 提出
                        alertDialog = new AlertDialog(PackGoodsActivity.this)
                                .builder()
                                .setTitle("确定将运单号从包裹中提出？")
                                .setPositiveButton(getResources().getString(R.string.str_yes), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        putBusiness();
                                        alertDialog.dissmiss();
                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.str_cancel), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dissmiss();
                                    }
                                });
                        alertDialog.show();
                        break;
                    case 4:
                        //TODO 拆包
                        alertDialog = new AlertDialog(PackGoodsActivity.this)
                                .builder()
                                .setTitle("确定将所选包裹进行拆包处理？")
                                .setPositiveButton(getResources().getString(R.string.str_yes), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        chaiBao();
                                        alertDialog.dissmiss();
                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.str_cancel), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dissmiss();
                                    }
                                });
                        alertDialog.show();
                        break;
                    case 5:
                        //TODO 称重
                        chengZhong();
                        break;
                }
            }
        });
    }

    private void initData() {
        dcyMenuAdapter = new MenuAdapter(this, dcyMenuList);
        gv_daichuyun_menu.setAdapter(dcyMenuAdapter);
        ysmMenuAdapter = new MenuAdapter(this, ysmMenuList);
        gv_yisaomiao_menu.setAdapter(ysmMenuAdapter);
        fs_daichuyun_list.setHeaderData(danChuYunHeaderList);
        fs_daichuyun_list.setContentDataForNoPackage(danChuYunlistData);
        fs_yisaomiao_list.setHeaderData(yiSaoMiaoHeaderList);
        fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
        getDepltList();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_qudao) { //渠道
            getServiceChannel();
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
            btn_daichuhuo.setBackgroundResource(R.color.secondary_color_cccccc);
            btn_yisaomiaobao.setBackgroundResource(R.color.secondary_color_e7e7e7);
        } else if (id == R.id.btn_yisaomiaobao) {  //已扫描
            ll_daichuyun.setVisibility(View.GONE);
            ll_yisaomiao.setVisibility(View.VISIBLE);
            btn_yisaomiaobao.setBackgroundResource(R.color.secondary_color_cccccc);
            btn_daichuhuo.setBackgroundResource(R.color.secondary_color_e7e7e7);
            tv_yundanhao.setText(getResources().getString(R.string.str_ydh));
        }
    }

    //TODO 处理扫描的单号（扫描就选中）
    private void handlerScanResult(String result) {
        if (getResources().getString(R.string.str_tmbh).equals(tv_yundanhao.getText().toString().trim())) {
            //TODO 待出运
            if (null != danChuYunlistData && danChuYunlistData.size() > 0) {
                for (int i = 0; i < danChuYunlistData.size(); i++) {
                    if (result.equals(danChuYunlistData.get(i).getShipper_hawbcode())
                            && ("false".equals(danChuYunlistData.get(i).getIsSelect())
                            ||TextUtils.isEmpty(danChuYunlistData.get(i).getIsSelect()))) {
                        danChuYunlistData.get(i).setIsSelect("true");
                        danChuYunlistData.get(i).setOrder_status("选中");
                        break;
                    } else if (result.equals(danChuYunlistData.get(i).getShipper_hawbcode())
                            && "true".equals(danChuYunlistData.get(i).getIsSelect())) {

                        break;
                    }
                }
                fs_daichuyun_list.setContentDataForNoPackage(danChuYunlistData);
            }
        } else if (getResources().getString(R.string.str_ydh).equals(tv_yundanhao.getText().toString().trim())) {
            //TODO 已扫描
            if (null != yiSaoMiaolistData && yiSaoMiaolistData.size() > 0) {
                if (result.contains("PPNO")) {
                    //TODO 证明扫描的是包编号
                    for (int i = 0; i < yiSaoMiaolistData.size(); i++) {
                        if (result.equals(yiSaoMiaolistData.get(i).getBag_lable_code())
                                && ("false".equals(yiSaoMiaolistData.get(i).getIsSelect())
                                ||TextUtils.isEmpty(yiSaoMiaolistData.get(i).getIsSelect()))) {
                            yiSaoMiaolistData.get(i).setIsSelect("true");
                            packGoodsCode = result;
                            break;
                        } else if (result.equals(yiSaoMiaolistData.get(i).getBag_lable_code())
                                && "true".equals(yiSaoMiaolistData.get(i).getIsSelect())) {

                            break;
                        }
                    }
                    fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
                } else {
                    //TODO 扫描的是子单号
                    for (int i = 0; i < yiSaoMiaolistData.size(); i++) {
                        if (null != yiSaoMiaolistData.get(i).getCn_list() && yiSaoMiaolistData.get(i).getCn_list().size() > 0) {
                            for (int j = 0; j < yiSaoMiaolistData.get(i).getCn_list().size(); j++) {
                                if (result.equals(yiSaoMiaolistData.get(i).getCn_list().get(j).getShipper_hawbcode())
                                        && ("false".equals(yiSaoMiaolistData.get(i).getCn_list().get(j).getIsSelect())
                                        ||TextUtils.isEmpty(yiSaoMiaolistData.get(i).getCn_list().get(j).getIsSelect()))) {
                                    packGoodsexpressCode = result;
                                    yiSaoMiaolistData.get(i).getCn_list().get(j).setIsSelect("true");
                                    yiSaoMiaolistData.get(i).getCn_list().get(j).setOrder_status("选中");
                                    break;
                                } else if (result.equals(yiSaoMiaolistData.get(i).getCn_list().get(j).getShipper_hawbcode())
                                        && "true".equals(yiSaoMiaolistData.get(i).getCn_list().get(j).getIsSelect())) {

                                    break;
                                }
                            }
                        }
                    }
                    fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
                }
            }
        }
    }

    @Override
    public void onResult(int Id, String result) {
        switch (Id){
            case PackgoodsContract.REQUEST_FAIL_ID:
                showTipeDialog(result);
                break;
            case PackgoodsContract.REQUEST_SUCCESS_ID:
                //TODO 获取初始化数据
                //TODO 先把子单数据遍历出来
                if (null != PackGoodsModel.getInstance().getBaleList() && PackGoodsModel.getInstance().getBaleList().size() > 0) {
                    danChuYunlistData.clear();
                    danChuYunlistData.addAll(PackGoodsModel.getInstance().getBaleList());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fs_daichuyun_list.setContentDataForNoPackage(danChuYunlistData);
                        }
                    });
                }
                if (null != PackGoodsModel.getInstance().getShppingCnList() && PackGoodsModel.getInstance().getShppingCnList().size() > 0) {
                    yiSaoMiaolistData.clear();
                    yiSaoMiaolistData.addAll(PackGoodsModel.getInstance().getShppingCnList());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
                        }
                    });
                }
                break;
            case PackgoodsContract.BAG_PUTBUSINESS_SUCCESS:
                //TODO 把运单从某个包提出
                getDepltList();
                break;
            case PackgoodsContract.BAG_WEIGHING_SUCCESS:
                //TODO 称量包的重量
                getDepltList();
                break;
            case PackgoodsContract.GET_SERVICECHANNEL_SUCCESS:
                //TODO 生效渠道
                if (null != PackGoodsModel.getInstance().getServiceChannelList()
                        && PackGoodsModel.getInstance().getServiceChannelList().size() > 0) {
                    serviceChannelList.clear();
                    serviceChannelList.addAll(PackGoodsModel.getInstance().getServiceChannelList());
                    final List<ListialogModel> showList = new ArrayList<>();
                    for (GsonServiceChannelModel.DataBean bean : PackGoodsModel.getInstance().getServiceChannelList()) {
                        showList.add(new ListialogModel("" + bean.getServer_channelid(), bean.getServer_channel_cnname(), bean.getServer_channel_enname(), false));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog = new ListDialog(PackGoodsActivity.this, R.style.AlertDialogStyle)
                                    .creatDialog()
                                    .setTitle("请选择服务渠道")
                                    .setSeachEditTextShow(true)
                                    .setListData(showList)
                                    .setCallBackListener(new ListDialog.CallBackListener() {
                                        @Override
                                        public void Result(int position, String name) {
                                            for (GsonServiceChannelModel.DataBean bean : serviceChannelList) {
                                                if (name.equals(bean.getServer_channelid()) ||
                                                        name.equals(bean.getServer_channel_cnname()) ||
                                                        name.equals(bean.getServer_channel_enname())) {
                                                    serviceChanneModel = new GsonServiceChannelModel.DataBean();
                                                    serviceChanneModel = bean;
                                                    et_qudao.setText(name);
                                                    getDepltList();
                                                    break;
                                                }
                                            }
                                            dialog.dismiss();
                                        }
                                    });
                            dialog.show();
                        }
                    });
                }else {
                    showTipeDialog("暂无数据");
                }
                break;
            case PackgoodsContract.ADD_BUSSINESSPACKAGE_SUCCESS:
                //TODO 运单打包
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog(PackGoodsActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg("打包成功")
                                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getDepltList();
                                        alertDialog.dissmiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });

                break;
            case PackgoodsContract.UNPACKING_SUCCESS:
                //TODO 拆包
                getDepltList();
                break;
        }
    }
    //TODO 运单打包 strExpressCode:包的号码 例如：PPNO-9908    og_id:机构id 登陆时返回  server_channelid:服务渠道   list:运单集合
    private void packageGoods() {
        if (!TextUtils.isEmpty(strExpressCode)) {
            if (null == serviceChanneModel) {
                showTipeDialog("服务渠道不能为空");
                return;
            }
            List<PackGoodsRequestBsListMode> list = new ArrayList<>();
            for (ExpressModel model : danChuYunlistData) {
                PackGoodsRequestBsListMode entety = new PackGoodsRequestBsListMode();
                if ("true".equals(model.getIsSelect())) {
                    entety.setBs_id(model.getBs_id());
                    entety.setShipper_hawbcode(model.getShipper_hawbcode());
                    entety.setServe_hawbcode(model.getShipper_hawbcode());
                    if (TextUtils.isEmpty(model.getChild_number())) {
                        entety.setChild_number(model.getShipper_hawbcode());
                    } else {
                        entety.setChild_number(model.getChild_number());
                    }
                    entety.setServer_channelid("" + serviceChanneModel.getServer_channelid());
                    entety.setFormal_code(serviceChanneModel.getFormal_code());
                    entety.setServer_channel_cnname(serviceChanneModel.getServer_channel_cnname());
                    entety.setInvoice_totalcharge("");
                    entety.setCargo_type_cnname("");
                    entety.setLast_comment("");
                    entety.setCheckin_date("" + model.getCheckin_date());
                    entety.setShipper_pieces("" + model.getShipper_pieces());
                    entety.setOutvolume_grossweight("" + model.getOutvolume_grossweight());
                    entety.setOutvolume_volumeweight("");
                    entety.setOutvolume_chargeweight("");
                    entety.setOutvolume_height("" + model.getOutvolume_height());
                    entety.setOutvolume_length("" + model.getOutvolume_length());
                    entety.setOutvolume_width("" + model.getOutvolume_width());
                    entety.setBalance_sign("" + model.getBalance_sign());
                    entety.setHolding("" + model.getHolding());
                    list.add(entety);
                }
            }
            mPresnter.addBussinessPackage(strExpressCode, "" + UserModel.getInstance().getOg_id(), "" + serviceChanneModel.getServer_channelid(), list);
        }
    }


    //TODO 称量包的重量  strBagCode:包号码   og_id:机构id  strlength:长  strwidth:宽  strheight:高  txtWeight:称重的重量  txtbag_grossweight:包重量
    private void chengZhong() {
        int num = 0;
        for (int i = 0; i < yiSaoMiaolistData.size(); i++) {
            if ("true".equals(yiSaoMiaolistData.get(i).getIsSelect())) {
                model = yiSaoMiaolistData.get(i);
                num++;
                if (num > 1) {
                    //TODO 点击的只一个包数据，不给拆包，提示用户
                    showWaiting(false);
                    showTipeDialog("只能选择一个包裹");
                    return;
                }
            }
        }

        if (null != model) {
            bagWeightDialog = new BagWeightDialog(PackGoodsActivity.this, R.style.AlertDialogStyle)
                    .creatDialog()
                    .setPackCode(model.getBag_lable_code())
                    .setPackWeight(model.getBag_weight())
                    .setCallBackListener(new BagWeightDialog.CallBackListener() {
                        @Override
                        public void Result(String dzcWeight, String chang, String kuan, String gao) {
                            mPresnter.bagWeighing(model.getBag_lable_code(), "" + UserModel.getInstance().getOg_id()
                                    , chang, kuan, gao, dzcWeight, model.getBag_weight());
                        }
                    })
                    .setCallBackQuXiao(new BagWeightDialog.CallBackQuXiao() {
                        @Override
                        public void Onclick() {
                            bagWeightDialog.dismiss();
                        }
                    });
            bagWeightDialog.show();
        }else {
            showTipeDialog("请选择要称重的包");
        }
    }

    //TODO 拆包  bag_labelcode:包号码  og_id:机构id
    private void chaiBao() {
        int num = 0;
        for (int i = 0; i < yiSaoMiaolistData.size(); i++) {
            if ("true".equals(yiSaoMiaolistData.get(i).getIsSelect())) {
                model = yiSaoMiaolistData.get(i);
                num++;
                if (num > 1) {
                    //TODO 点击的只一个包数据，不给拆包，提示用户
                    showWaiting(false);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final AlertDialog dialogs = new AlertDialog(PackGoodsActivity.this)
                                    .builder()
                                    .setTitle(getResources().getString(R.string.str_alter))
                                    .setMsg("只能选择一个包裹")
                                    .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    });
                            dialogs.show();
                        }
                    });
                    return;
                }
            }
        }
        if (null != model) {
            mPresnter.unpacking(model.getBag_lable_code(), "" + UserModel.getInstance().getOg_id());
        } else {
            showTipeDialog("请选择要拆包的包裹");
        }
    }


    //TODO 把运单从某个包提出 list:运单的集合
    private void putBusiness() {
        List<BagPutBusinessReqModel> list = new ArrayList<>();
        for (int i = 0; i < yiSaoMiaolistData.size(); i++) {
            if (null != yiSaoMiaolistData.get(i).getCn_list() && yiSaoMiaolistData.get(i).getCn_list().size() > 0) {
                for (int j = 0; j < yiSaoMiaolistData.get(i).getCn_list().size(); j++) {
                    BagPutBusinessReqModel model = new BagPutBusinessReqModel();
                    if ("true".equals(yiSaoMiaolistData.get(i).getCn_list().get(j).getIsSelect())) {
                        if (TextUtils.isEmpty(yiSaoMiaolistData.get(i).getCn_list().get(j).getBs_id())) {
                            model.setBs_id("");
                        } else {
                            model.setBs_id(yiSaoMiaolistData.get(i).getCn_list().get(j).getBs_id());
                        }
                        if (TextUtils.isEmpty(yiSaoMiaolistData.get(i).getCn_list().get(j).getShipper_hawbcode())) {
                            model.setHawbcode("");
                        } else {
                            model.setHawbcode(yiSaoMiaolistData.get(i).getCn_list().get(j).getShipper_hawbcode());
                        }
                        model.setHawbcode_mode("");
                        if (TextUtils.isEmpty(yiSaoMiaolistData.get(i).getCn_list().get(j).getCheckin_date())) {
                            model.setScan_date("");
                        } else {
                            model.setScan_date(yiSaoMiaolistData.get(i).getCn_list().get(j).getCheckin_date());
                        }
                        if (TextUtils.isEmpty(yiSaoMiaolistData.get(i).getBag_id())) {
                            model.setBag_id("");
                        } else {
                            model.setBag_id(yiSaoMiaolistData.get(i).getBag_id());
                        }
                        if (TextUtils.isEmpty(yiSaoMiaolistData.get(i).getBag_lable_code())) {
                            model.setBag_labelcode("");
                        } else {
                            model.setBag_labelcode(yiSaoMiaolistData.get(i).getBag_lable_code());
                        }
                        list.add(model);
                    }
                }
            }
        }
        mPresnter.bagPutBusiness(list);
    }


    //TODO 查询生效渠道
    private void getServiceChannel() {
        mPresnter.getServiceChannel();
    }

    //TODO 获取打包界面初始化数据
    private void getDepltList() {
        String server_id = "";
        String server_channelid = "";
        if (!TextUtils.isEmpty(et_qudao.getText().toString().trim()) && null != serviceChanneModel) {
            server_id = "" + serviceChanneModel.getServer_id();
            server_channelid = "" + serviceChanneModel.getServer_channelid();
        }
        mPresnter.getDepltList("" + UserModel.getInstance().getOg_id(), server_id, server_channelid);
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
        ysmMenuList.add(new MenuModel(5, getResources().getString(R.string.str_cz), R.mipmap.icon_chengzhong));
    }

    //TODO 设置打包界面待出运界面数据标题栏
    private void getDaiChuYunHeaderData() {
        danChuYunHeaderList.add(new FourSidesSlidListTitileModel(1, getResources().getString(R.string.str_gx)
                , "", getResources().getString(R.string.str_zzzt)
                , getResources().getString(R.string.str_ydh), getResources().getString(R.string.str_zdtm)
                , getResources().getString(R.string.str_js), getResources().getString(R.string.str_shizhong)
                , getResources().getString(R.string.str_ckg)));
    }

    //TODO 设置打包界面已扫描界面数据标题栏
    private void getYiSaoMiaoHeaderData() {
        yiSaoMiaoHeaderList.add(new FourSidesSlidListTitileModel(2, getResources().getString(R.string.str_gx)
                , getResources().getString(R.string.str_bbh), ""
                , getResources().getString(R.string.str_ydh), getResources().getString(R.string.str_zdtm)
                , getResources().getString(R.string.str_js), getResources().getString(R.string.str_shizhong)
                , getResources().getString(R.string.str_ckg)));
    }


    public void showWaiting(boolean isShow) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(isShow);
        }
    }



    private void showTipeDialog(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog = new AlertDialog(PackGoodsActivity.this)
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
