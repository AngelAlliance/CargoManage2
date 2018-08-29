package com.sz.ljs.shipments.view;

import android.content.Intent;
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
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.model.MenuModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.shipments.model.GsonServiceChannelModel;
import com.sz.ljs.shipments.model.ShipMentsModel;
import com.sz.ljs.shipments.presenter.ShipmentsPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    private List<GsonServiceChannelModel.DataBean> serviceChannelList = new ArrayList<>();
    private GsonServiceChannelModel.DataBean serviceChanneModel;
    private ShipmentsPresenter mPresnter;
    private WaitingDialog waitingDialog;
    private AlertDialog alertDialog;
    private ListDialog dialog;
    private String packGoodsexpressCode; //已扫描界面最后一个选择的运单
    private String packGoodsCode; // 最后一个选择的包
    private List<ExpressPackageModel> selectList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipments);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        getHeaderData();
        setDaiChuYunMenu();
        mPresnter=new ShipmentsPresenter();
        waitingDialog=new WaitingDialog(this);
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
        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ID = dcyMenuList.get(position).getId();
                if (ID == 1) {
                    //TODO  撤销
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWaiting(true);
                        }
                    });
                    if (!TextUtils.isEmpty(packGoodsexpressCode)) {
                        for (int i = 0; i < listData.size(); i++) {
                            for (int j = 0; j < listData.get(i).getCn_list().size(); j++) {
                                if (listData.get(i).getCn_list().get(j).getShipper_hawbcode().equals(packGoodsexpressCode)) {
                                    if ("false".equals(listData.get(i).getCn_list().get(j).getIsSelect())) {
                                        listData.get(i).getCn_list().get(j).setIsSelect("true");
                                        listData.get(i).getCn_list().get(j).setOrder_status("选中");
                                    } else if ("true".equals(listData.get(i).getCn_list().get(j).getIsSelect())) {
                                        listData.get(i).getCn_list().get(j).setIsSelect("false");
                                        listData.get(i).getCn_list().get(j).setOrder_status("已收件");
                                    }
                                    packGoodsexpressCode = "";
                                    fs_listView.setContentData(listData);
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
                        for (int i = 0; i < listData.size(); i++) {
                            if (listData.get(i).getBag_lable_code().equals(packGoodsCode)) {
                                if ("false".equals(listData.get(i).getIsSelect())) {
                                    listData.get(i).setIsSelect("true");
                                    for (int j = 0; j < listData.get(i).getCn_list().size(); j++) {
                                        listData.get(i).getCn_list().get(j).setIsSelect("true");
                                        listData.get(i).getCn_list().get(j).setOrder_status("选中");
                                    }
                                } else if ("true".equals(listData.get(i).getIsSelect())) {
                                    listData.get(i).setIsSelect("false");
                                    for (int j = 0; j < listData.get(i).getCn_list().size(); j++) {
                                        listData.get(i).getCn_list().get(j).setIsSelect("false");
                                        listData.get(i).getCn_list().get(j).setOrder_status("已收件");
                                    }
                                }
                                packGoodsCode = "";
                                fs_listView.setContentData(listData);
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
                } else if (ID == 2) {
                    //TODO  生成主单
                    if(null!=selectList&&selectList.size()>0){
                        ShipMentsModel.getInstance().setSelectList(selectList);
                        startActivityForResult(new Intent(ShipMentsActivity.this,ProductionMasterActivity.class),100);
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog = new AlertDialog(ShipMentsActivity.this)
                                        .builder()
                                        .setTitle(getResources().getString(R.string.str_alter))
                                        .setMsg("请选择要生成主单的包数据")
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
                } else if (ID == 3) {
                    //TODO  刷新
                    getDepltList();
                }
            }
        });

        //TODO 设置已扫描界面点击包层次勾选按钮监听
        fs_listView.setSidesSlidGroupCheckListener(new FourSidesSlidingListView.SidesSlidGroupCheckListener() {
            @Override
            public void OnClick(int groupPosition) {
                //TODO 点击父勾选按钮
                if (null != listData && listData.size() > 0) {
                    if (TextUtils.isEmpty(listData.get(groupPosition).getIsSelect())
                            || "false" == listData.get(groupPosition).getIsSelect()) {
                        listData.get(groupPosition).setIsSelect("true");
                        selectList.add(listData.get(groupPosition));
                        if (null != listData.get(groupPosition).getCn_list()
                                && listData.get(groupPosition).getCn_list().size() > 0) {
                            //TODO 将其所有子单状态全部设置成勾选状态
                            for (int i = 0; i < listData.get(groupPosition).getCn_list().size(); i++) {
                                listData.get(groupPosition).getCn_list().get(i).setIsSelect("true");
                            }
                        }
                    } else if ("true" == listData.get(groupPosition).getIsSelect()) {
                        listData.get(groupPosition).setIsSelect("false");
                        selectList.remove(listData.get(groupPosition));
                        if (null != listData.get(groupPosition).getCn_list()
                                && listData.get(groupPosition).getCn_list().size() > 0) {
                            //TODO 将其所有子单状态全部取消勾选状态
                            for (int i = 0; i < listData.get(groupPosition).getCn_list().size(); i++) {
                                listData.get(groupPosition).getCn_list().get(i).setIsSelect("false");
                            }
                        }
                    }
                    packGoodsexpressCode = "";//因为点击了包，这时候最后一个选择的就应该是包
                    packGoodsCode = listData.get(groupPosition).getBag_lable_code();
                    fs_listView.setContentData(listData);
                }
            }
        });

        //TODO 设置已扫描界面点击包底下运单勾选按钮监听
        fs_listView.setSidesSlidChildCheckListener(new FourSidesSlidingListView.SidesSlidChildCheckListener() {
            @Override
            public void OnClick(int groupPosition, int childPosition) {
                //TODO 点击包底下的子单勾选按钮
                if (null != listData && listData.size() > 0
                        && null != listData.get(groupPosition).getCn_list()
                        && listData.get(groupPosition).getCn_list().size() > 0) {
                    if (TextUtils.isEmpty(listData.get(groupPosition).getCn_list().get(childPosition).getIsSelect())
                            || "false".equals(listData.get(groupPosition).getCn_list().get(childPosition).getIsSelect())) {
                        listData.get(groupPosition).getCn_list().get(childPosition).setIsSelect("true");
                    } else if ("true".equals(listData.get(groupPosition).getCn_list().get(childPosition).getIsSelect())) {
                        listData.get(groupPosition).getCn_list().get(childPosition).setIsSelect("false");
                    }
                }
                packGoodsCode = "";
                packGoodsexpressCode = listData.get(groupPosition).getCn_list().get(childPosition).getShipper_hawbcode();
                fs_listView.setContentData(listData);
            }
        });
    }

    private void initData() {
        dcyMenuAdapter = new MenuAdapter(this, dcyMenuList);
        gv_menu.setAdapter(dcyMenuAdapter);
        fs_listView.setHeaderData(headerList);
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
        }
    }

    //TODO 获取打包界面初始化数据
    private void getDepltList() {
        showWaiting(true);
        String server_id = "";
        String server_channelid = "";
        if (!TextUtils.isEmpty(et_qudao.getText().toString().trim()) && null != serviceChanneModel) {
            server_id = "" + serviceChanneModel.getServer_id();
            server_channelid = "" + serviceChanneModel.getServer_channelid();
        }
        mPresnter.getDepltList("" + UserModel.getInstance().getOg_id(), server_id, server_channelid)
                .compose(this.<GsonDepltListModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GsonDepltListModel>() {
                    @Override
                    public void accept(GsonDepltListModel result) throws Exception {
                        if (1 == result.getCode()) {
                            if (null != result.getData()) {
                                handDepltListResult(result);
                            }
                        } else {
                            showWaiting(false);
                            alertDialog = new AlertDialog(ShipMentsActivity.this)
                                    .builder()
                                    .setTitle(getResources().getString(R.string.str_alter))
                                    .setMsg(result.getMsg())
                                    .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dissmiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        alertDialog = new AlertDialog(ShipMentsActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg(getResources().getString(R.string.str_qqsb))
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


    private void handDepltListResult(GsonDepltListModel result) {
        if (null != result.getData().getShppingCnList() && result.getData().getShppingCnList().size() > 0) {
            listData.clear();
            listData.addAll(result.getData().getShppingCnList());
            fs_listView.setContentData(listData);
        }
        showWaiting(false);
    }

    //TODO 查询生效渠道
    private void getServiceChannel() {
        showWaiting(true);
        mPresnter.getServiceChannel()
                .compose(this.<GsonServiceChannelModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<GsonServiceChannelModel>() {
                    @Override
                    public void accept(GsonServiceChannelModel result) throws Exception {
                        if (1 == result.getCode()) {
                            showWaiting(false);
                            if (null != result.getData() && result.getData().size() > 0) {
                                serviceChannelList.clear();
                                serviceChannelList.addAll(result.getData());
                                final List<ListialogModel> showList = new ArrayList<>();
                                for (GsonServiceChannelModel.DataBean bean : result.getData()) {
                                    showList.add(new ListialogModel("" + bean.getServer_channelid(), bean.getServer_channel_cnname(), bean.getServer_channel_enname(), false));
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog = new ListDialog(ShipMentsActivity.this, R.style.AlertDialogStyle)
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

                            }
                        } else {
                            showWaiting(false);
                            alertDialog = new AlertDialog(ShipMentsActivity.this)
                                    .builder()
                                    .setTitle(getResources().getString(R.string.str_alter))
                                    .setMsg(result.getMsg())
                                    .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dissmiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        alertDialog = new AlertDialog(ShipMentsActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg(getResources().getString(R.string.str_qqsb))
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



    private void showWaiting(boolean isShow){
        if(null!=waitingDialog){
            waitingDialog.showDialog(isShow);
        }
    }

    //TODO 设置待出运菜单
    private void setDaiChuYunMenu() {
        dcyMenuList.add(new MenuModel(1, getResources().getString(R.string.str_cx), R.mipmap.ic_chexiao));
        dcyMenuList.add(new MenuModel(2, getResources().getString(R.string.str_sczd), R.mipmap.ic_tichu));
        dcyMenuList.add(new MenuModel(3, getResources().getString(R.string.str_sx), R.mipmap.ic_shuaxin));
    }

    //TODO 设置标题栏
    private void getHeaderData() {
        headerList.add(new FourSidesSlidListTitileModel(2, getResources().getString(R.string.str_gx)
                , getResources().getString(R.string.str_bbh), getResources().getString(R.string.str_zzzt)
                , getResources().getString(R.string.str_ydh), getResources().getString(R.string.str_zdtm)
                , getResources().getString(R.string.str_js), getResources().getString(R.string.str_shizhong)
                , getResources().getString(R.string.str_ckg)));
    }
}
