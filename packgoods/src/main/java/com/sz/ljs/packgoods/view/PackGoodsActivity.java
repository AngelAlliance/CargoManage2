package com.sz.ljs.packgoods.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
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
import com.sz.ljs.packgoods.model.BagPutBusinessReqModel;
import com.sz.ljs.packgoods.model.GsonAddBussinessPackageModel;
import com.sz.ljs.packgoods.model.GsonServiceChannelModel;
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

public class PackGoodsActivity extends BaseActivity implements View.OnClickListener {
    private EditText  et_yundanhao;
    private TextView tv_yundanhao,et_qudao;
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
        mPresnter = new PackgoodsPresenter();
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
    }

    private void setListener() {
        ll_qudao.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        btn_daichuhuo.setOnClickListener(this);
        btn_yisaomiaobao.setOnClickListener(this);

        //TODO 设置待出运点击勾选按钮监听
        fs_daichuyun_list.setSidesSlidNoGroupCheckListener(new FourSidesSlidingListView.SidesSlidNoGroupCheckListener() {
            @Override
            public void OnClick(int childPosition) {
                if (null != danChuYunlistData && danChuYunlistData.size() > 0) {
                    if (TextUtils.isEmpty(danChuYunlistData.get(childPosition).getIsSelect()) || "false" == danChuYunlistData.get(childPosition).getIsSelect()) {
                        danChuYunlistData.get(childPosition).setIsSelect("true");
                        danChuYunlistData.get(childPosition).setOrder_status("选中");
//                        ExpressModel model =danChuYunlistData.get(childPosition);
//                        //TODO 先移除这一项数据
//                        danChuYunlistData.remove(childPosition);
//                        //TODO 再将刚刚已点击的数据添加到集合的第一项
//                        danChuYunlistData.add(0,model);
                    } else if ("true" == danChuYunlistData.get(childPosition).getIsSelect()) {
                        danChuYunlistData.get(childPosition).setIsSelect("false");
                        danChuYunlistData.get(childPosition).setOrder_status("已收件");
//                        ExpressModel model =danChuYunlistData.get(childPosition);
//                        //TODO 先移除这一项数据
//                        danChuYunlistData.remove(childPosition);
//                        danChuYunlistData.add(danChuYunlistData.size(),model);
                    }
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
                                yiSaoMiaolistData.get(groupPosition).getCn_list().get(i).setOrder_status("选中");
                            }
                        }
                    } else if ("true" == yiSaoMiaolistData.get(groupPosition).getIsSelect()) {
                        yiSaoMiaolistData.get(groupPosition).setIsSelect("false");
                        if (null != yiSaoMiaolistData.get(groupPosition).getCn_list()
                                && yiSaoMiaolistData.get(groupPosition).getCn_list().size() > 0) {
                            //TODO 将其所有子单状态全部取消勾选状态
                            for (int i = 0; i < yiSaoMiaolistData.get(groupPosition).getCn_list().size(); i++) {
                                yiSaoMiaolistData.get(groupPosition).getCn_list().get(i).setIsSelect("false");
                                yiSaoMiaolistData.get(groupPosition).getCn_list().get(i).setOrder_status("已收件");
                            }
                        }
                    }
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
                    } else if ("true".equals(yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).getIsSelect())) {
                        yiSaoMiaolistData.get(groupPosition).getCn_list().get(childPosition).setIsSelect("false");
                    }
                }
                fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
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
        } else if (id == R.id.btn_yisaomiaobao) {  //已扫描
            ll_daichuyun.setVisibility(View.GONE);
            ll_yisaomiao.setVisibility(View.VISIBLE);
            tv_yundanhao.setText(getResources().getString(R.string.str_ydh));
        }
    }

    //TODO 运单打包 strExpressCode:包的号码 例如：PPNO-9908    og_id:机构id 登陆时返回  server_channelid:服务渠道   list:运单集合
    private void addBussinessPackage(String strExpressCode, String og_id
            , String server_channelid, List<PackGoodsRequestBsListMode> list){
        showWaiting(true);
        mPresnter.addBussinessPackage(strExpressCode,og_id,server_channelid,list)
                .compose(this.<GsonAddBussinessPackageModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<GsonAddBussinessPackageModel>() {
                    @Override
                    public void accept(GsonAddBussinessPackageModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
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
                        } else if (1 == result.getCode()) {

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        alertDialog = new AlertDialog(PackGoodsActivity.this)
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

    //TODO 称量包的重量  strBagCode:包号码   og_id:机构id  strlength:长  strwidth:宽  strheight:高  txtWeight:称重的重量  txtbag_grossweight:包重量
    private void bagWeighing(String strBagCode, String og_id, String strlength, String strwidth
            , String strheight, String txtWeight, String txtbag_grossweight){
        showWaiting(true);
        mPresnter.bagWeighing(strBagCode,og_id,strlength,strwidth,strheight,txtWeight,txtbag_grossweight)
                .compose(this.<BaseResultModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<BaseResultModel>() {
                    @Override
                    public void accept(BaseResultModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            alertDialog = new AlertDialog(PackGoodsActivity.this)
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
                        } else if (1 == result.getCode()) {

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        alertDialog = new AlertDialog(PackGoodsActivity.this)
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

    //TODO 拆包  bag_labelcode:包号码  og_id:机构id
    private void unpacking(String bag_labelcode, String og_id){
        showWaiting(true);
        mPresnter.unpacking(bag_labelcode,og_id)
                .compose(this.<BaseResultModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<BaseResultModel>() {
                    @Override
                    public void accept(BaseResultModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            alertDialog = new AlertDialog(PackGoodsActivity.this)
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
                        } else if (1 == result.getCode()) {

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        alertDialog = new AlertDialog(PackGoodsActivity.this)
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

    //TODO 把运单从某个包提出 list:运单的集合
    private void bagPutBusiness(List<BagPutBusinessReqModel> list){
        showWaiting(true);
        mPresnter.bagPutBusiness(list)
                .compose(this.<BaseResultModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<BaseResultModel>() {
                    @Override
                    public void accept(BaseResultModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            alertDialog = new AlertDialog(PackGoodsActivity.this)
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
                        } else if (1 == result.getCode()) {
                            alertDialog = new AlertDialog(PackGoodsActivity.this)
                                    .builder()
                                    .setTitle(getResources().getString(R.string.str_alter))
                                    .setMsg(result.getMsg())
                                    .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getDepltList();
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
                        alertDialog = new AlertDialog(PackGoodsActivity.this)
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

    //TODO 查询生效渠道
    private void getServiceChannel() {
        showWaiting(true);
        mPresnter.getServiceChannel()
                .compose(this.<GsonServiceChannelModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<GsonServiceChannelModel>() {
                    @Override
                    public void accept(GsonServiceChannelModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            alertDialog = new AlertDialog(PackGoodsActivity.this)
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
                        } else if (1 == result.getCode()) {
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

                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        alertDialog = new AlertDialog(PackGoodsActivity.this)
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

    //TODO 获取打包界面初始化数据
    private void getDepltList() {
        showWaiting(true);
        String server_id="";
        String server_channelid="";
        if(!TextUtils.isEmpty(et_qudao.getText().toString().trim())&&null!=serviceChanneModel){
            server_id=""+serviceChanneModel.getServer_id();
            server_channelid=""+serviceChanneModel.getServer_channelid();
        }
        mPresnter.getDepltList("" + UserModel.getInstance().getOg_id(), server_id, server_channelid)
                .compose(this.<GsonDepltListModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GsonDepltListModel>() {
                    @Override
                    public void accept(GsonDepltListModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);

                            alertDialog = new AlertDialog(PackGoodsActivity.this)
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
                        } else if (1 == result.getCode()) {
                            showWaiting(false);
                            if (null != result.getData()) {
                                handDepltListResult(result);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        alertDialog = new AlertDialog(PackGoodsActivity.this)
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
        //TODO 先把子单数据遍历出来
        if (null != result.getData().getBaleList() && result.getData().getBaleList().size() > 0) {
            danChuYunlistData.clear();
            danChuYunlistData.addAll(result.getData().getBaleList());
            fs_daichuyun_list.setContentDataForNoPackage(danChuYunlistData);
        }

        if (null != result.getData().getShppingCnList() && result.getData().getShppingCnList().size() > 0) {
            yiSaoMiaolistData.clear();
            yiSaoMiaolistData.addAll(result.getData().getShppingCnList());
            fs_yisaomiao_list.setContentData(yiSaoMiaolistData);
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


    private void showWaiting(boolean isShow) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(isShow);
        }
    }

}
