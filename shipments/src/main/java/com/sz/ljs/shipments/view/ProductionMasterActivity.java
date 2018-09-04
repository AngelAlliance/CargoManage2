package com.sz.ljs.shipments.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.sz.ljs.common.view.TitleView;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.shipments.contract.ShipmentsContract;
import com.sz.ljs.shipments.model.GsonOrgServerModel;
import com.sz.ljs.shipments.model.GsonSaveTransportBatchAndBusinessModel;
import com.sz.ljs.shipments.model.ShipMentsModel;
import com.sz.ljs.shipments.model.TransportBatchBusinessParamModel;
import com.sz.ljs.shipments.presenter.ShipmentsPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.WheelPicker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//TODO 生成主单界面
public class ProductionMasterActivity extends BaseActivity implements View.OnClickListener, ShipmentsContract.View {

    private TextView tv_fahuozhan, tv_xiayizhan, tv_shouhuofuwushao, tv_fahuoshijian, tv_zongshizhong, tv_piaoshu, tv_zongjianshu;
    private LinearLayout ll_xiayizhan, ll_shouhuofuwushao, ll_fahuoshijian;
    private EditText et_yunshubianhao;
    private TitleView tv_titles;
    private Button btn_queren, btn_guanbi;
    private ShipmentsPresenter mPresnter;
    private WaitingDialog waitingDialog;
    private AlertDialog alertDialog;
    private ListDialog dialog;
    private List<ExpressPackageModel> selectListData = new ArrayList<>();
    private List<ExpressModel> expressListData = new ArrayList<>();
    private List<GsonOrgServerModel.DataBean.OrgListBean> orgList = new ArrayList<>();
    private GsonOrgServerModel.DataBean.OrgListBean orgListBean;
    private List<GsonOrgServerModel.DataBean.ServerListBean> serverList = new ArrayList<>();
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
        mPresnter = new ShipmentsPresenter(this);
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
        tv_titles = (TitleView) findViewById(R.id.tv_titles);
    }

    private void setListener() {
        ll_xiayizhan.setOnClickListener(this);
        ll_shouhuofuwushao.setOnClickListener(this);
        ll_fahuoshijian.setOnClickListener(this);
        btn_queren.setOnClickListener(this);
        btn_guanbi.setOnClickListener(this);
        tv_titles.setBackOnclistener(new TitleView.IBackButtonCallBack() {
            @Override
            public boolean onClick(View v) {
                goBack(0);
                return false;
            }
        });
    }

    private void initData() {
        if (!TextUtils.isEmpty(UserModel.getInstance().getOg_cityenname())) {
            tv_fahuozhan.setText(UserModel.getInstance().getOg_cityenname());
        }
        tv_fahuoshijian.setText(TimeUtils.getDateTime());
        selectListData = ShipMentsModel.getInstance().getSelectList();
        if (null != selectListData && selectListData.size() > 0) {
            showWaiting(true);
            tv_piaoshu.setText("" + selectListData.size());
            expressListData.clear();
            double weight = 0;
            for (int i = 0; i < selectListData.size(); i++) {
                if (!TextUtils.isEmpty(selectListData.get(i).getBag_weight())) {
                    weight = weight + Double.valueOf(selectListData.get(i).getBag_weight());
                }
                if (null != selectListData.get(i).getCn_list() && selectListData.get(i).getCn_list().size() > 0) {
                    expressListData.addAll(selectListData.get(i).getCn_list());
                }
            }
            tv_zongjianshu.setText("" + expressListData.size());
            tv_zongshizhong.setText(weight + "KG");
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
            handlerLeaveDate();
        } else if (id == R.id.btn_queren) {
            //TODO 确认
            saveTransportBatchAndBusiness();
        } else if (id == R.id.btn_guanbi) {
            //TODO 关闭
            finish();
        }
    }

    //TODO 选择下一站
    private void selectXiaYiZhan() {
        final List<ListialogModel> showList = new ArrayList<>();
        if (null != orgList && orgList.size() > 0) {
            for (GsonOrgServerModel.DataBean.OrgListBean model : orgList) {
                showList.add(new ListialogModel("" + model.getOg_id(), model.getOg_name(), model.getOg_ename(), false));
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
                            for (GsonOrgServerModel.DataBean.OrgListBean model : orgList) {
                                if (name.equals(model.getOg_name())) {
                                    orgListBean = model;
                                    tv_xiayizhan.setText(model.getOg_name());
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
    private void selectShouHuoFuWuShang() {
        final List<ListialogModel> showList = new ArrayList<>();
        if (null != serverList && serverList.size() > 0) {
            for (GsonOrgServerModel.DataBean.ServerListBean model : serverList) {
                showList.add(new ListialogModel("" + model.getServer_id(), model.getServer_shortname(), model.getServer_code(), false));
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
                            for (GsonOrgServerModel.DataBean.ServerListBean model : serverList) {
                                if (name.equals(model.getServer_shortname())) {
                                    serverListBean = model;
                                    tv_shouhuofuwushao.setText(model.getServer_shortname());
                                    break;
                                }
                            }
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }

    }

    @Override
    public void onResult(int Id, final String result) {
        switch (Id) {
            case ShipmentsContract.REQUEST_FAIL_ID:
                showTipeDialog(result);
                break;
            case ShipmentsContract.GET_ORG_SERVER_SUCCESS:
                //TODO 查看收货服务商跟机构
                if (null != ShipMentsModel.getInstance().getOrg_list() && ShipMentsModel.getInstance().getOrg_list().size() > 0) {
                    orgList.clear();
                    orgList.addAll(ShipMentsModel.getInstance().getOrg_list());
                    for (GsonOrgServerModel.DataBean.OrgListBean model : orgList) {
                        if (UserModel.getInstance().getOg_cityenname().equals(model.getOg_ename())) {
                            orgList.remove(model);
                            break;
                        }
                    }
                }

                if (null != ShipMentsModel.getInstance().getServer_list()
                        && ShipMentsModel.getInstance().getServer_list().size() > 0) {
                    serverList.clear();
                    serverList.addAll(ShipMentsModel.getInstance().getServer_list());
                }
                break;
            case ShipmentsContract.SAVE_TRANSPORTBATCH_AND_BUSINESS_SUCCESS:
                //TODO 出库生成主单
                if (null != ShipMentsModel.getInstance().getBusinessDataBean()
                        && null != ShipMentsModel.getInstance().getBusinessDataBean().getTransportBatchResult()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog = new AlertDialog(ProductionMasterActivity.this)
                                    .builder()
                                    .setTitle(getResources().getString(R.string.str_alter))
                                    .setMsg(result + "\n" + getResources().getString(R.string.str_ysbh) + ":"
                                            + ShipMentsModel.getInstance().getBusinessDataBean().getTransportBatchResult().getTra_batch_code())
                                    .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dissmiss();
                                            goBack(1);
                                        }
                                    });
                            alertDialog.show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog = new AlertDialog(ProductionMasterActivity.this)
                                    .builder()
                                    .setTitle(getResources().getString(R.string.str_alter))
                                    .setMsg(result)
                                    .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dissmiss();
                                            goBack(1);
                                        }
                                    });
                            alertDialog.show();
                        }
                    });
                }
                break;
        }
    }

    private void goBack(int type) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        setResult(RESULT_OK, intent);
        finish();
    }

    //TODO 生成主单
    private void saveTransportBatchAndBusiness() {
        showWaiting(true);
        if (TextUtils.isEmpty(tv_xiayizhan.getText().toString())) {
            showWaiting(false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialog = new AlertDialog(ProductionMasterActivity.this)
                            .builder()
                            .setTitle(getResources().getString(R.string.str_alter))
                            .setMsg("请选择下一站地址")
                            .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dissmiss();
                                }
                            });
                    alertDialog.show();
                }
            });
            return;
        }
//        if (TextUtils.isEmpty(tv_shouhuofuwushao.getText().toString())) {
//            showWaiting(false);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    alertDialog = new AlertDialog(ProductionMasterActivity.this)
//                            .builder()
//                            .setTitle(getResources().getString(R.string.str_alter))
//                            .setMsg("请选择收货服务商")
//                            .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    alertDialog.dissmiss();
//                                }
//                            });
//                    alertDialog.show();
//                }
//            });
//            return;
//        }
        if (TextUtils.isEmpty(tv_fahuoshijian.getText().toString())) {
            showWaiting(false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialog = new AlertDialog(ProductionMasterActivity.this)
                            .builder()
                            .setTitle(getResources().getString(R.string.str_alter))
                            .setMsg("请选择发货时间")
                            .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dissmiss();
                                }
                            });
                    alertDialog.show();
                }
            });
            return;
        }
        List<TransportBatchBusinessParamModel> list = new ArrayList<>();
        if (null != selectListData && selectListData.size() > 0) {
            for (ExpressPackageModel model : selectListData) {
                TransportBatchBusinessParamModel entity = new TransportBatchBusinessParamModel();
                TransportBatchBusinessParamModel.BusinessTRBean businessTRBean = new TransportBatchBusinessParamModel.BusinessTRBean();
                businessTRBean.setReceive_date(TimeUtils.getDateTime());
                businessTRBean.setReceiver_id("");
                businessTRBean.setReceive_check_weight("");
                businessTRBean.setBusiness_gross_weight(model.getBag_weight());
                businessTRBean.setCheckout_gross_weight(model.getWeighing());
                businessTRBean.setHawb_bs_id(model.getBag_id());
                businessTRBean.setHawb_code(model.getBag_lable_code());
                businessTRBean.setHawb_type("B");//暂时不知道填什么
                businessTRBean.setTra_id("");
                businessTRBean.setTb_id("");
                businessTRBean.setTableName("");
                businessTRBean.setPrimaryKeys("");
                businessTRBean.setReturnName("");
                entity.setBusinessTR(businessTRBean);
                if (null != model.getCn_list() && model.getCn_list().size() > 0) {
                    List<TransportBatchBusinessParamModel.ListBusinessBean> list1 = new ArrayList<>();
                    //TODO 包底下含有子单
                    for (ExpressModel expressModel : model.getCn_list()) {
                        TransportBatchBusinessParamModel.ListBusinessBean listBusinessBean = new TransportBatchBusinessParamModel.ListBusinessBean();
                        listBusinessBean.setBs_id(expressModel.getBs_id());
                        listBusinessBean.setChild_number(expressModel.getChild_number());
                        list1.add(listBusinessBean);
                    }
                    entity.setList_business(list1);
                }
                list.add(entity);
            }
        }
        String server_id="";
        String server_code="";
        if(null!=serverListBean){
            server_id=""+serverListBean.getServer_id();
            server_code=""+serverListBean.getServer_code();
        }
        mPresnter.saveTransportBatchAndBusiness(tv_fahuoshijian.getText().toString(), "" + orgListBean.getOg_id()
                , server_id, server_code, list);
    }

    //TODO 查看收货服务商跟机构
    private void getOrgServer() {
        mPresnter.getOrgServer();
    }


    //TODO 设置出货时间
    private void handlerLeaveDate() {
        final DateTimePicker dateTimePicker = new DateTimePicker(this,
                DateTimePicker.YEAR_MONTH_DAY, DateTimePicker.HOUR_24);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        dateTimePicker.setDateRangeStart(calendar.get(Calendar.YEAR), 1, 1);
        dateTimePicker.setDateRangeEnd(calendar.get(Calendar.YEAR) + 1, 12, 31);
        calendar.setTime(new Date());
        dateTimePicker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

        dateTimePicker.setHeaderView(getPackerHeadView(dateTimePicker, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar selcal = Calendar.getInstance();
                int year = Integer.parseInt(dateTimePicker.getSelectedYear());
                int month = Integer.parseInt(dateTimePicker.getSelectedMonth());
                int day = Integer.parseInt(dateTimePicker.getSelectedDay());
                int hour = Integer.parseInt(dateTimePicker.getSelectedHour());
                int minute = Integer.parseInt(dateTimePicker.getSelectedMinute());
                selcal.set(year, month - 1, day, hour, minute);
                String years = dateTimePicker.getSelectedYear();
                String months = dateTimePicker.getSelectedMonth();
                String days = dateTimePicker.getSelectedDay();
                String hours = dateTimePicker.getSelectedHour();
                String minutes = dateTimePicker.getSelectedMinute();
                String strTime = getString(R.string.str_leave_date_string);
                strTime = String.format(strTime, years, months, days, hours, minutes);
//                String strTime = getString(R.string.str_leave_date_string,years,months,days,hours,minutes);
                tv_fahuoshijian.setText(strTime);
            }
        }));
        dateTimePicker.setResetWhileWheel(false);
        dateTimePicker.show();
    }

    private View getPackerHeadView(final WheelPicker picker, final View.OnClickListener complete_listener) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_leave_type_picker_head, null);
        view.findViewById(R.id.text_view_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.dismiss();
            }
        });
        view.findViewById(R.id.text_view_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != complete_listener) {
                    complete_listener.onClick(v);
                }
                picker.dismiss();
            }
        });
        return view;
    }

    @Override
    public void showWaiting(boolean isShow) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(isShow);
        }
    }


    private void showTipeDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog = new AlertDialog(ProductionMasterActivity.this)
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack(0);
        }
        return super.onKeyDown(keyCode, event);
    }
}
