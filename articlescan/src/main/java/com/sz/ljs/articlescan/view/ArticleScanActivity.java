package com.sz.ljs.articlescan.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.ljs.articlescan.R;
import com.sz.ljs.articlescan.adapter.DataExpandableListAdapter;
import com.sz.ljs.articlescan.adapter.TitleAdapter;
import com.sz.ljs.articlescan.contract.ArticleScanContract;
import com.sz.ljs.articlescan.model.ArticleScanModel;
import com.sz.ljs.articlescan.model.GsonOrgServerModel;
import com.sz.ljs.articlescan.model.GsonSelectShipmentBagReceiveModel;
import com.sz.ljs.articlescan.model.TitleModel;
import com.sz.ljs.articlescan.presenter.ArticleScanPresenter;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.adapter.MenuAdapter;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.model.MenuModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.MediaPlayerUtils;
import com.sz.ljs.common.utils.MscManager;
import com.sz.ljs.common.utils.PrintManager;
import com.sz.ljs.common.utils.TimeUtils;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.NoscrollExpandableListView;
import com.sz.ljs.common.view.NoscrollListView;
import com.sz.ljs.common.view.SyncHorizontalScrollView;
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
    private EditText et_yundanhao;
    private SyncHorizontalScrollView header_horizontal, data_horizontal;
    private NoscrollListView lv_header;
    private NoscrollExpandableListView lv_data;
    private List<MenuModel> dcyMenuList = new ArrayList<>();
    private GridView gv_menu;
    private MenuAdapter dcyMenuAdapter;
    private WaitingDialog waitingDialog;
    private AlertDialog alertDialog;
    private ArticleScanPresenter mPresenter;
    private List<GsonOrgServerModel.DataBean.OrgListBean> orgList = new ArrayList<>();
    private GsonOrgServerModel.DataBean.OrgListBean orgListBean;
    private List<GsonOrgServerModel.DataBean.ServerListBean> serverList = new ArrayList<>();
    private GsonOrgServerModel.DataBean.ServerListBean serverListBean;
    private ListDialog dialog;
    private DataExpandableListAdapter dataAdapter;
    private TitleAdapter titleAdapter;
    private List<GsonSelectShipmentBagReceiveModel.DataBean> shipmentBagList = new ArrayList<>();
    private List<TitleModel> titleList = new ArrayList<>();
    private String serviceId = "";
    private String og_Id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_scan);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        PrintManager.getInstance().init(this);
        waitingDialog = new WaitingDialog(this);
        mPresenter = new ArticleScanPresenter(this);
        tv_arrive_time = (TextView) findViewById(R.id.tv_arrive_time);
        tv_last_station = (TextView) findViewById(R.id.tv_last_station);
        ll_arrive_time = (LinearLayout) findViewById(R.id.ll_arrive_time);
        ll_last_station = (LinearLayout) findViewById(R.id.ll_last_station);
        ll_qudao = (LinearLayout) findViewById(R.id.ll_qudao);
        tv_qudao = (TextView) findViewById(R.id.tv_qudao);
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        gv_menu = (GridView) findViewById(R.id.gv_menu);
        header_horizontal = (SyncHorizontalScrollView) findViewById(R.id.header_horizontal);
        data_horizontal = (SyncHorizontalScrollView) findViewById(R.id.data_horizontal);
        lv_header = (NoscrollListView) findViewById(R.id.lv_header);
        lv_data = (NoscrollExpandableListView) findViewById(com.sz.ljs.common.R.id.lv_data);
        header_horizontal.setScrollView(data_horizontal);
        data_horizontal.setScrollView(header_horizontal);
        setDaiChuYunMenu();
        getHeaderData();

    }

    private void initData() {
        MediaPlayerUtils.setRingVolume(true, ArticleScanActivity.this);
        MscManager.getInstance().init(ArticleScanActivity.this, 0);
        tv_arrive_time.setText(TimeUtils.getDate());
        mPresenter.getOrgServer();
        mPresenter.SelectShipmentBagReceive(TimeUtils.getDateTime(), "", "");
        dcyMenuAdapter = new MenuAdapter(this, dcyMenuList);
        gv_menu.setAdapter(dcyMenuAdapter);
        titleAdapter = new TitleAdapter(this, titleList);
        lv_header.setAdapter(titleAdapter);
        setListViewHeight(lv_header);
        dataAdapter = new DataExpandableListAdapter(this, shipmentBagList);
        lv_data.setAdapter(dataAdapter);
        setListViewHeight(lv_data);
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
        et_yundanhao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    forEach(s.toString());
//                    if ("PPNO-00981".equals(s.toString())) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mPresenter.TransportBatchBusinessReceipt(s.toString());
//                            }
//                        });
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                MscManager.getInstance().speech(result);
                showTipeDialog(result);
                break;
            case ArticleScanContract.REQUEST_SUCCESS_ID:
                //TODO 初始化界面数据请求成功
                if (null != ArticleScanModel.getInstance().getShipmentBagList()
                        && ArticleScanModel.getInstance().getShipmentBagList().size() > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_yundanhao.setText("");
                            et_yundanhao.setFocusable(true);
                            shipmentBagList.clear();
                            shipmentBagList.addAll(ArticleScanModel.getInstance().getShipmentBagList());
                            dataAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    shipmentBagList.clear();
                    et_yundanhao.setText("");
                    et_yundanhao.setFocusable(true);
                    dataAdapter.notifyDataSetChanged();
                    showTipeDialog("暂无数据");
                }
                break;
            case ArticleScanContract.BAG_RECEIVE_FAIL_ID:
                shipmentBagList.clear();
                dataAdapter.notifyDataSetChanged();
                showTipeDialog("暂无数据");
                break;
            case ArticleScanContract.BUSINESS_RECEIPT_SUCCESS_ID:
                MscManager.getInstance().speech("操作成功");
                showTipeDialog(result);
                slectShipmentBagReceive();
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


    //TODO 遍历查询数据，并且进行扫描处理
    private void forEach(String str) {
        if (null != shipmentBagList) {
            for (int i = 0; i < shipmentBagList.size(); i++) {
                if (null != shipmentBagList.get(i).getBagReceiveList() &&
                        shipmentBagList.get(i).getBagReceiveList().size() > 0) {
                    for (int j = 0; j < shipmentBagList.get(i).getBagReceiveList().size(); j++) {
                        if (str.equals(shipmentBagList.get(i).getBagReceiveList().get(j).getHawb_code())) {
                            mPresenter.TransportBatchBusinessReceipt(shipmentBagList.get(i).getBagReceiveList().get(j).getTb_id());
                            return;
                        }
                    }
                }
            }
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
                                    og_Id = "" + model.getOg_id();
                                    slectShipmentBagReceive();
                                    break;
                                }
                            }
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }

    }

    //TODO 到件扫描初始化数据接口  arrival_date:到件时间  from_og_id:上一站  server_channelid:服务渠道
    private void slectShipmentBagReceive(){
        mPresenter.SelectShipmentBagReceive(tv_arrive_time.getText().toString().trim()
                , og_Id, serviceId);
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
                                    serviceId = "" + model.getServer_id();
                                    slectShipmentBagReceive();
//                                    mPresenter.SelectShipmentBagReceive(tv_arrive_time.getText().toString().trim()
//                                            , og_Id, serviceId);
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
    private void getHeaderData() {
        titleList.add(new TitleModel(getResources().getString(R.string.str_bbh), getResources().getString(R.string.str_fgs)
                , getResources().getString(R.string.str_fwqd), getResources().getString(R.string.str_js)
                , getResources().getString(R.string.str_ps)));
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
                slectShipmentBagReceive();
//                mPresenter.SelectShipmentBagReceive(tv_arrive_time.getText().toString().trim()
//                        , og_Id, serviceId);
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

    //为listview动态设置高度（有多少条目就显示多少条目）
    public void setListViewHeight(ListView listView) {
        //获取listView的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount()返回数据项的数目
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
