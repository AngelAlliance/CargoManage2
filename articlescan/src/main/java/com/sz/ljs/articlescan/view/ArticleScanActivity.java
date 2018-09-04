package com.sz.ljs.articlescan.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.articlescan.R;
import com.sz.ljs.articlescan.contract.ArticleScanContract;
import com.sz.ljs.articlescan.model.ArticleScanModel;
import com.sz.ljs.articlescan.model.GsonOrgServerModel;
import com.sz.ljs.articlescan.model.GsonSelectShipmentBagReceiveModel;
import com.sz.ljs.articlescan.presenter.ArticleScanPresenter;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.adapter.MenuAdapter;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.model.MenuModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.WaitingDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.WheelPicker;

/**
 * Author: Mr. Duan
 * Date: 2018/8/30
 * Description:
 */

public class ArticleScanActivity extends BaseActivity implements ArticleScanContract.View, View.OnClickListener {
    private TextView tv_arrive_time, tv_last_station, tv_qudao;
    private LinearLayout ll_arrive_time, ll_last_station, ll_qudao;
    private List<MenuModel> dcyMenuList = new ArrayList<>();
    private List<FourSidesSlidListTitileModel> yiSaoMiaoHeaderList = new ArrayList<>();
    private FourSidesSlidingListView fs_yisaomiao_list;
    private GridView gv_menu;
    private MenuAdapter dcyMenuAdapter;
    private WaitingDialog waitingDialog;
    private AlertDialog alertDialog;
    private ArticleScanPresenter mPresenter;
    private List<GsonSelectShipmentBagReceiveModel.DataBean> shipmentBagList = new ArrayList<>();
    private List<GsonOrgServerModel.DataBean.OrgListBean> orgList = new ArrayList<>();
    private GsonOrgServerModel.DataBean.OrgListBean orgListBean;
    private List<GsonOrgServerModel.DataBean.ServerListBean> serverList = new ArrayList<>();
    private GsonOrgServerModel.DataBean.ServerListBean serverListBean;
    private ListDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_scan);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        waitingDialog = new WaitingDialog(this);
        mPresenter = new ArticleScanPresenter(this);
        tv_arrive_time = (TextView) findViewById(R.id.tv_arrive_time);
        tv_last_station = (TextView) findViewById(R.id.tv_last_station);
        ll_arrive_time = (LinearLayout) findViewById(R.id.ll_arrive_time);
        ll_last_station = (LinearLayout) findViewById(R.id.ll_last_station);
        ll_qudao = (LinearLayout) findViewById(R.id.ll_qudao);
        tv_qudao = (TextView) findViewById(R.id.tv_qudao);
        gv_menu = (GridView) findViewById(R.id.gv_menu);
        fs_yisaomiao_list = (FourSidesSlidingListView) findViewById(R.id.fs_yisaomiao_list);
        setDaiChuYunMenu();
        getYiSaoMiaoHeaderData();

    }

    private void initData() {
//        mPresenter.SelectShipmentBagReceive();
        dcyMenuAdapter = new MenuAdapter(this, dcyMenuList);
        gv_menu.setAdapter(dcyMenuAdapter);
        fs_yisaomiao_list.setHeaderData(yiSaoMiaoHeaderList);
    }


    private void setListener() {
        ll_arrive_time.setOnClickListener(this);
        ll_last_station.setOnClickListener(this);
        ll_qudao.setOnClickListener(this);
        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int menuId = dcyMenuList.get(position).getId();
                switch (menuId) {
                    case 1:
                        Intent intent = new Intent(ArticleScanActivity.this, ScanDataActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_arrive_time) {
            //TODO 到达时间
            handlerLeaveDate();
        } else if (id == R.id.ll_last_station) {
            //TODO 上一站
            selectXiaYiZhan();
        } else if (id == R.id.ll_qudao) {
            //TODO 请选择服务渠道
            selectShouHuoFuWuShang();
        }
    }

    @Override
    public void onResult(int Id, String result) {
        switch (Id) {
            case ArticleScanContract.REQUEST_FAIL_ID:
                showTipeDialog(result);
                break;
            case ArticleScanContract.REQUEST_SUCCESS_ID:
                //TODO 初始化界面数据请求成功
                if (null != ArticleScanModel.getInstance().getShipmentBagList()
                        && ArticleScanModel.getInstance().getShipmentBagList().size() > 0) {
                    shipmentBagList.clear();
                    shipmentBagList.addAll(ArticleScanModel.getInstance().getShipmentBagList());

                } else {
                    showTipeDialog("暂无数据");
                }
                break;

            case ArticleScanContract.BUSINESS_RECEIPT_SUCCESS_ID:
                showTipeDialog(result);
                break;
            case ArticleScanContract.GET_ORG_SERVER_SUCCESS:
                //TODO 获取收货服务机构及上一站数据
                if (null != ArticleScanModel.getInstance().getOrg_list()
                        && ArticleScanModel.getInstance().getOrg_list().size() > 0) {
                    orgList.clear();
                    orgList.addAll(ArticleScanModel.getInstance().getOrg_list());
                }

                if (null != ArticleScanModel.getInstance().getServer_list()
                        && ArticleScanModel.getInstance().getServer_list().size() > 0) {
                    serverList.clear();
                    serverList.addAll(ArticleScanModel.getInstance().getServer_list());
                }
                break;
        }
    }


    //TODO 选择上一站
    private void selectXiaYiZhan() {
        final List<ListialogModel> showList = new ArrayList<>();
        if (null != orgList && orgList.size() > 0) {
            for (GsonOrgServerModel.DataBean.OrgListBean model : orgList) {
                showList.add(new ListialogModel("" + model.getOg_id(), model.getOg_name(), model.getOg_ename(), false));
            }
            dialog = new ListDialog(ArticleScanActivity.this, R.style.AlertDialogStyle)
                    .creatDialog()
                    .setTitle("选择上一站")
                    .setListData(showList)
                    .setSeachEditTextShow(true)
                    .setCallBackListener(new ListDialog.CallBackListener() {
                        @Override
                        public void Result(int position, String name) {
                            orgListBean = new GsonOrgServerModel.DataBean.OrgListBean();
                            for (GsonOrgServerModel.DataBean.OrgListBean model : orgList) {
                                if (name.equals(model.getOg_name())) {
                                    orgListBean = model;
                                    tv_last_station.setText(model.getOg_name());
                                    break;
                                }
                            }
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }

    }


    //TODO 请选择服务渠道
    private void selectShouHuoFuWuShang() {
        final List<ListialogModel> showList = new ArrayList<>();
        if (null != serverList && serverList.size() > 0) {
            for (GsonOrgServerModel.DataBean.ServerListBean model : serverList) {
                showList.add(new ListialogModel("" + model.getServer_id(), model.getServer_shortname(), model.getServer_code(), false));
            }
            dialog = new ListDialog(ArticleScanActivity.this, R.style.AlertDialogStyle)
                    .creatDialog()
                    .setTitle("请选择服务渠道")
                    .setListData(showList)
                    .setSeachEditTextShow(true)
                    .setCallBackListener(new ListDialog.CallBackListener() {
                        @Override
                        public void Result(int position, String name) {
                            serverListBean = new GsonOrgServerModel.DataBean.ServerListBean();
                            for (GsonOrgServerModel.DataBean.ServerListBean model : serverList) {
                                if (name.equals(model.getServer_shortname())) {
                                    serverListBean = model;
                                    tv_qudao.setText(model.getServer_shortname());
                                    break;
                                }
                            }
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }

    }

    //TODO 设置待出运菜单
    private void setDaiChuYunMenu() {
        dcyMenuList.add(new MenuModel(1, getResources().getString(R.string.str_ysmsj), R.mipmap.icon_panku));
    }

    //TODO 设置打包界面已扫描界面数据标题栏
    private void getYiSaoMiaoHeaderData() {
        yiSaoMiaoHeaderList.add(new FourSidesSlidListTitileModel(2, getResources().getString(R.string.str_gx)
                , getResources().getString(R.string.str_bbh), ""
                , getResources().getString(R.string.str_ydh), getResources().getString(R.string.str_zdtm)
                , getResources().getString(R.string.str_js), getResources().getString(R.string.str_shizhong)
                , getResources().getString(R.string.str_ckg)));
    }

    @Override
    public void showWaiting(boolean show) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(show);
        }
    }

    private void showTipeDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog = new AlertDialog(ArticleScanActivity.this)
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

    //TODO 设置时间
    private void handlerLeaveDate() {
        final DateTimePicker dateTimePicker = new DateTimePicker(this,
                DateTimePicker.YEAR_MONTH_DAY, DateTimePicker.NONE);
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
                String strTime = getString(R.string.str_leave_date_strings);
                strTime = String.format(strTime, years, months, days);
                tv_arrive_time.setText(strTime);
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


}
