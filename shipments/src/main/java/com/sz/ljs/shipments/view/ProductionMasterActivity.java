package com.sz.ljs.shipments.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shipments.R;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.model.ExpressModel;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.TimeUtils;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.shipments.model.GsonOrgServerModel;
import com.sz.ljs.shipments.model.ShipMentsModel;
import com.sz.ljs.shipments.model.TransportBatchBusinessParamModel;
import com.sz.ljs.shipments.presenter.ShipmentsPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//TODO 生成主单界面
public class ProductionMasterActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_fahuozhan, tv_xiayizhan, tv_shouhuofuwushao, tv_fahuoshijian, tv_zongshizhong, tv_piaoshu, tv_zongjianshu;
    private LinearLayout ll_xiayizhan, ll_shouhuofuwushao, ll_fahuoshijian;
    private EditText et_yunshubianhao;
    private Button btn_queren, btn_guanbi;
    private ShipmentsPresenter mPresnter;
    private WaitingDialog waitingDialog;
    private AlertDialog alertDialog;
    private ListDialog dialog;
    private List<ExpressPackageModel> selectListData = new ArrayList<>();
    private List<ExpressModel> expressListData = new ArrayList<>();
    private List<GsonOrgServerModel.DataBean.OrgListBean> orgList=new ArrayList<>();
    private GsonOrgServerModel.DataBean.OrgListBean orgListBean;
    private List<GsonOrgServerModel.DataBean.ServerListBean> serverList=new ArrayList<>();
    private GsonOrgServerModel.DataBean.ServerListBean serverListBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_master);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mPresnter = new ShipmentsPresenter();
        waitingDialog = new WaitingDialog(this);
        tv_fahuozhan = (TextView) findViewById(R.id.tv_fahuozhan);
        tv_xiayizhan = (TextView) findViewById(R.id.tv_xiayizhan);
        tv_shouhuofuwushao = (TextView) findViewById(R.id.tv_shouhuofuwushao);
        tv_fahuoshijian = (TextView) findViewById(R.id.tv_fahuoshijian);
        tv_zongshizhong = (TextView) findViewById(R.id.tv_zongshizhong);
        tv_piaoshu = (TextView) findViewById(R.id.tv_piaoshu);
        tv_zongjianshu = (TextView) findViewById(R.id.tv_zongjianshu);
        ll_xiayizhan = (LinearLayout) findViewById(R.id.ll_xiayizhan);
        ll_shouhuofuwushao = (LinearLayout) findViewById(R.id.ll_shouhuofuwushao);
        ll_fahuoshijian = (LinearLayout) findViewById(R.id.ll_fahuoshijian);
        et_yunshubianhao = (EditText) findViewById(R.id.et_yunshubianhao);
        btn_queren = (Button) findViewById(R.id.btn_queren);
        btn_guanbi = (Button) findViewById(R.id.btn_guanbi);
    }

    private void setListener() {
        ll_xiayizhan.setOnClickListener(this);
        ll_shouhuofuwushao.setOnClickListener(this);
        ll_fahuoshijian.setOnClickListener(this);
        btn_queren.setOnClickListener(this);
        btn_guanbi.setOnClickListener(this);
    }

    private void initData() {
        if(!TextUtils.isEmpty(UserModel.getInstance().getOg_cityenname())){
            tv_fahuozhan.setText(UserModel.getInstance().getOg_cityenname());
        }
        tv_fahuoshijian.setText(TimeUtils.getDateTime());
        selectListData = ShipMentsModel.getInstance().getSelectList();
        if (null != selectListData && selectListData.size() > 0) {
            showWaiting(true);
            tv_piaoshu.setText("" + selectListData.size());
            expressListData.clear();
            double weight=0;
            for (int i = 0; i < selectListData.size(); i++) {
                if(!TextUtils.isEmpty(selectListData.get(i).getBag_weight())){
                    weight=weight+Double.valueOf(selectListData.get(i).getBag_weight());
                }
                if(null!=selectListData.get(i).getCn_list()&&selectListData.get(i).getCn_list().size()>0){
                    expressListData.addAll(selectListData.get(i).getCn_list());
                }
            }
            tv_zongjianshu.setText("" + expressListData.size());
            tv_zongshizhong.setText(weight+"KG");
            getOrgServer();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_xiayizhan) {
            //TODO 下一站
            selectXiaYiZhan();
        } else if (id == R.id.ll_shouhuofuwushao) {
            //TODO 收货服务商
            selectShouHuoFuWuShang();
        } else if (id == R.id.ll_fahuoshijian) {
            //TODO 发货时间

        } else if (id == R.id.btn_queren) {
            //TODO 确认

        } else if (id == R.id.btn_guanbi) {
            //TODO 关闭
            finish();
        }
    }

    //TODO 选择下一站
    private void selectXiaYiZhan(){
        final List<ListialogModel> showList = new ArrayList<>();
        if(null!=orgList&&orgList.size()>0){
            for (GsonOrgServerModel.DataBean.OrgListBean model : orgList) {
                showList.add(new ListialogModel(""+model.getOg_id(), model.getOg_name(), model.getOg_ename(), false));
            }
            dialog = new ListDialog(ProductionMasterActivity.this, R.style.AlertDialogStyle)
                    .creatDialog()
                    .setTitle("请选择下一站")
                    .setListData(showList)
                    .setSeachEditTextShow(true)
                    .setCallBackListener(new ListDialog.CallBackListener() {
                        @Override
                        public void Result(int position, String name) {
                            orgListBean = new GsonOrgServerModel.DataBean.OrgListBean();
                            for (GsonOrgServerModel.DataBean.OrgListBean model : orgList){
                                if(name.equals(model.getOg_name())){
                                    orgListBean = model;
                                    tv_xiayizhan.setText( model.getOg_name());
                                    break;
                                }
                            }
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }

    }
    //TODO 选择收货服务商
    private void selectShouHuoFuWuShang(){
        final List<ListialogModel> showList = new ArrayList<>();
        if(null!=serverList&&serverList.size()>0){
            for (GsonOrgServerModel.DataBean.ServerListBean model : serverList) {
                showList.add(new ListialogModel(""+model.getServer_id(), model.getServer_shortname(), model.getServer_code(), false));
            }
            dialog = new ListDialog(ProductionMasterActivity.this, R.style.AlertDialogStyle)
                    .creatDialog()
                    .setTitle("请选择收货服务商")
                    .setListData(showList)
                    .setSeachEditTextShow(true)
                    .setCallBackListener(new ListDialog.CallBackListener() {
                        @Override
                        public void Result(int position, String name) {
                            serverListBean = new GsonOrgServerModel.DataBean.ServerListBean();
                            for (GsonOrgServerModel.DataBean.ServerListBean model : serverList){
                                if(name.equals(model.getServer_shortname())){
                                    serverListBean = model;
                                    tv_shouhuofuwushao.setText( model.getServer_shortname());
                                    break;
                                }
                            }
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }

    }

    //TODO 生成主单
    private void saveTransportBatchAndBusiness(){
        List<TransportBatchBusinessParamModel> list=new ArrayList<>();

        TransportBatchBusinessParamModel model=new TransportBatchBusinessParamModel();
    }
    //TODO 查看收货服务商跟机构
    private void getOrgServer(){
        mPresnter.getOrgServer()
                .compose(this.<GsonOrgServerModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GsonOrgServerModel>() {
                    @Override
                    public void accept(GsonOrgServerModel result) throws Exception {
                        if (1 == result.getCode()) {
                            if (null != result.getData()) {
                                handOrgServerResult(result);
                            }
                        } else {
                            showWaiting(false);
                            alertDialog = new AlertDialog(ProductionMasterActivity.this)
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
                        alertDialog = new AlertDialog(ProductionMasterActivity.this)
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

    private void handOrgServerResult(GsonOrgServerModel result){
        showWaiting(false);
        if(null!=result.getData()){
            if(null!=result.getData().getOrg_list()&&result.getData().getOrg_list().size()>0){
                orgList.clear();
                orgList.addAll(result.getData().getOrg_list());
            }

            if(null!=result.getData().getServer_list()&&result.getData().getServer_list().size()>0){
                serverList.clear();
                serverList.addAll(result.getData().getServer_list());
            }
        }
    }

    private void showWaiting(boolean isShow){
        if(null!=waitingDialog){
            waitingDialog.showDialog(isShow);
        }
    }
}
