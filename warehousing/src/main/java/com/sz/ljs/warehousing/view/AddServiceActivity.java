package com.sz.ljs.warehousing.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.SelectionPopForBottomView;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.warehousing.R;
import com.sz.ljs.warehousing.contract.WarehouContract;
import com.sz.ljs.warehousing.model.GsonIncidentalModel;
import com.sz.ljs.warehousing.model.ServiceModel;
import com.sz.ljs.warehousing.model.WareHouSingModel;
import com.sz.ljs.warehousing.presenter.WarehouPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 附加服务界面
 */
public class AddServiceActivity extends BaseActivity implements View.OnClickListener, WarehouContract.View {
    private LinearLayout ll_addView;
    private Button btn_queren, btn_xinzeng;
    private List<ServiceModel> serviceList = new ArrayList<>();
    private WaitingDialog mWaitingDialog = null;
    private List<GsonIncidentalModel.DataBean> beanList = new ArrayList<>();
    private List<ListialogModel> showList = new ArrayList<>();
    private WarehouPresenter mPresenter;
    private ListDialog dialog;
    private int feiyongIndex = 0;
    private boolean isXinZen = false, isQueRen = false;
    private int pice = 1;
    private boolean isHave = false;
    private AlertDialog alertDialog;
    private View views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addservice);
        initView();
        setListener();
    }

    private void initView() {
        serviceList = WareHouSingModel.getInstance().getServiceModelList();
        if (null != serviceList && serviceList.size() > 0) {
            pice = serviceList.size();
            isHave = true;
        } else {
            pice = 1;
        }
        mPresenter = new WarehouPresenter(this);
        mWaitingDialog = new WaitingDialog(this);
        ll_addView = (LinearLayout) findViewById(R.id.ll_addView);
        btn_queren = (Button) findViewById(R.id.btn_queren);
        btn_xinzeng = (Button) findViewById(R.id.btn_xinzeng);
        for (int i = 0; i < pice; i++) {
            addView();
        }
    }

    private void setListener() {
        btn_queren.setOnClickListener(this);
        btn_xinzeng.setOnClickListener(this);
        findViewById(R.id.btn_shanchu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_queren) {
            //TODO 确认
            isQueRen = true;
            inspectIsTianJia();
        } else if (id == R.id.btn_xinzeng) {
            //TODO 新增
            isXinZen = true;
//            handelAddView();
            inspectIsTianJia();
        } else if (id == R.id.btn_shanchu) {
            Log.i("点击删除按钮", "position=" + v.getTag());
            removeView(v);
        } else if (id == R.id.ll_zafeixiang) {
            Log.i("选择杂费项", "position=" + v.getTag());
            choseZaFeiXiang(v);
        }
    }

    //TODO 处理新增（这里需要判断上一个费用是否输入完整）（弃用）
    private void handelAddView() {
        int index;
        if (ll_addView.getChildCount() == 0) {
            index = 0;
        } else {
            index = ll_addView.getChildCount() - 1;
        }
        View childView = ll_addView.getChildAt(index);
        EditText et_zafeixiang = (EditText) childView.findViewById(R.id.et_zafeixiang);
        EditText et_feiyong = (EditText) childView.findViewById(R.id.et_feiyong);
        if (TextUtils.isEmpty(et_zafeixiang.getText().toString().trim())
                || TextUtils.isEmpty(et_feiyong.getText().toString().trim())) {
            Utils.showToast(getBaseActivity(), "请完善杂费项或费用数据");
        } else {
            ServiceModel model = new ServiceModel(index, "", "" + UserModel.getInstance().getSt_id(), "", ""
                    , "", Double.valueOf(et_feiyong.getText().toString().trim()), beanList.get(index).getExtra_service_kind(), et_zafeixiang.getText().toString().trim(), "");
//            if(null!=serviceList&&serviceList.size()>0){
//                for (int i=0;i<serviceList.size();i++){
//                    //TODO 遍历是否存在此
//                }
//            }
            serviceList.add(model);

            addView();
        }
    }

    @Override
    public void onResult(int Id, String result) {
        switch (Id) {
            case WarehouContract.REQUEST_FAIL_ID:
                showTipeDialog(result);
                break;
            case WarehouContract.GET_INCIDENTAL_SUCCESS:
                //TODO 请求杂费项内容
                if (null != WareHouSingModel.getInstance().getIncidentalList() && WareHouSingModel.getInstance().getIncidentalList().size() > 0) {
                    handelServiceResult(WareHouSingModel.getInstance().getIncidentalList(), views);
                }
                break;
        }
    }

    //TODO 请求杂费项内容
    private void choseZaFeiXiang(final View view) {
        views = view;
        if (null == mPresenter) {
            mPresenter = new WarehouPresenter(this);
        }
        mPresenter.getIncidental();
    }

    //TODO 杂费项数据处理
    private void handelServiceResult(final List<GsonIncidentalModel.DataBean> result, final View view) {
        if (null != result && result.size() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    beanList.clear();
                    beanList.addAll(result);
                    showList.clear();
                    for (GsonIncidentalModel.DataBean brean : beanList) {
                        showList.add(new ListialogModel(brean.getExtra_service_kind(), brean.getExtra_service_cnname(), brean.getExtra_service_enname(), false));
                    }
                    dialog = new ListDialog(AddServiceActivity.this, R.style.AlertDialogStyle)
                            .creatDialog()
                            .setTitle("请选择杂费项")
                            .setSeachEditTextShow(true)
                            .setListData(showList)
                            .setCallBackListener(new ListDialog.CallBackListener() {
                                @Override
                                public void Result(int position, String name) {
                                    int index = (Integer) view.getTag();
                                    View view1 = ll_addView.getChildAt(index);
                                    TextView et_zafeixiang = (TextView) view1.findViewById(R.id.et_zafeixiang);
                                    TextView tv_zfx_code = (TextView) view1.findViewById(R.id.tv_zfx_code);
                                    for (GsonIncidentalModel.DataBean brean : beanList) {
                                        if (name.equals(brean.getExtra_service_cnname())) {
                                            tv_zfx_code.setText(brean.getExtra_service_kind());
                                            et_zafeixiang.setText(brean.getExtra_service_cnname());
                                            break;
                                        }
                                    }
                                    views = null;
                                    dialog.dismiss();
                                }
                            });
                    dialog.show();
                }
            });
        }
    }

    //TODO 移除
    private void removeView(View view) {
        if (0 < ll_addView.getChildCount()) {
            int position = (Integer) view.getTag();
            if (null != serviceList && serviceList.size() == position + 1) {
                for (int i = 0; i < serviceList.size(); i++) {
                    if (serviceList.get(i).getPosition() == position) {
                        serviceList.remove(position);
                        WareHouSingModel.getInstance().removeServiceModel(position);
                        break;
                    }
                }
            }
            ll_addView.removeView(ll_addView.getChildAt((Integer) view.getTag()));
            if (0 == ll_addView.getChildCount()) {
                addView();
            }
        }
    }

    //TODO 新增
    private void addView() {
        View view = LayoutInflater.from(AddServiceActivity.this).inflate(R.layout.view_service, null);
        final int position = ll_addView.getChildCount();
        Button button = (Button) view.findViewById(R.id.btn_shanchu);
        button.setTag(position);
        button.setOnClickListener(this);
        EditText et_feiyong = (EditText) view.findViewById(R.id.et_feiyong);
        et_feiyong.setTag(position);
        et_feiyong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (true == hasFocus) {
                    feiyongIndex = position;
                } else {
                    inspectIsComplete(position);
                }
            }
        });
        LinearLayout ll_zafeixiang = (LinearLayout) view.findViewById(R.id.ll_zafeixiang);
        ll_zafeixiang.setTag(position);
        ll_zafeixiang.setOnClickListener(this);
        TextView et_zafeixiang = (TextView) view.findViewById(R.id.et_zafeixiang);
        TextView tv_zfx_code = (TextView) view.findViewById(R.id.tv_zfx_code);
        TextView tv_zfx_sftj = (TextView) view.findViewById(R.id.tv_zfx_sftj);
        if (null != serviceList && serviceList.size() > 0) {
            tv_zfx_code.setText(serviceList.get(position).getExtra_servicecode());
            et_feiyong.setText("" + serviceList.get(position).getExtra_servicevalue());
            tv_zfx_sftj.setText("已经添加");
            et_zafeixiang.setText(serviceList.get(position).getExtra_servicecname());
        }
        ll_addView.addView(view);
    }

    //TODO 新增
    private void addView2() {
        View view = LayoutInflater.from(AddServiceActivity.this).inflate(R.layout.view_service, null);
        final int position = ll_addView.getChildCount();
        Button button = (Button) view.findViewById(R.id.btn_shanchu);
        button.setTag(position);
        button.setOnClickListener(this);
        EditText et_feiyong = (EditText) view.findViewById(R.id.et_feiyong);
        et_feiyong.setTag(position);
        et_feiyong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (true == hasFocus) {
                    feiyongIndex = position;
                } else {
                    inspectIsComplete(position);
                }
            }
        });
        LinearLayout ll_zafeixiang = (LinearLayout) view.findViewById(R.id.ll_zafeixiang);
        ll_zafeixiang.setTag(position);
        ll_zafeixiang.setOnClickListener(this);
        TextView et_zafeixiang = (TextView) view.findViewById(R.id.et_zafeixiang);
        TextView tv_zfx_code = (TextView) view.findViewById(R.id.tv_zfx_code);
        TextView tv_zfx_sftj = (TextView) view.findViewById(R.id.tv_zfx_sftj);
        ll_addView.addView(view);
    }


    //TODO 检查所有item数据是否添加
    private void inspectIsTianJia() {
        for (int i = 0; i < ll_addView.getChildCount(); i++) {
            View view = ll_addView.getChildAt(i);
            TextView et_zafeixiang = (TextView) view.findViewById(R.id.et_zafeixiang);
            TextView tv_zfx_code = (TextView) view.findViewById(R.id.tv_zfx_code);
            EditText et_feiyong = (EditText) view.findViewById(R.id.et_feiyong);
            TextView tv_zfx_sftj = (TextView) view.findViewById(R.id.tv_zfx_sftj);
            if (TextUtils.isEmpty(tv_zfx_sftj.getText().toString().trim())) {
                //TODO 表示还有没添加的
//                && !TextUtils.isEmpty(et_feiyong.getText().toString().trim())
                if (!TextUtils.isEmpty(et_zafeixiang.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(et_feiyong.getText().toString().trim())){
                        serviceList.add(new ServiceModel(i, "", "" + UserModel.getInstance().getSt_id(), "", ""
                                , "", Double.valueOf(et_feiyong.getText().toString().trim()), tv_zfx_code.getText().toString().trim(), et_zafeixiang.getText().toString().trim(), ""));
                    }else {
                        serviceList.add(new ServiceModel(i, "", "" + UserModel.getInstance().getSt_id(), "", ""
                                , "",0.0, tv_zfx_code.getText().toString().trim(), et_zafeixiang.getText().toString().trim(), ""));
                    }

                    tv_zfx_sftj.setText("已经添加");
                } else {
                    showTipeDialog("杂费项不能为空");
                    return;
                }
                break;
            } else {
                //TODO 这时候先检测列表中是否含有这个数据
                for (int j = 0; j < serviceList.size(); j++) {
                    if (serviceList.get(j).getPosition() == i) {
                        //TODO 表示有此项，则只需要更改相应的参数即可
                        serviceList.get(j).setExtra_servicecode(tv_zfx_code.getText().toString().trim()); //修改杂费项代码
                        if(!TextUtils.isEmpty(et_feiyong.getText().toString().trim())){
                            serviceList.get(j).setExtra_servicevalue(Double.valueOf(et_feiyong.getText().toString().trim()));
                        }else {
                            serviceList.get(j).setExtra_servicevalue(0.0);
                        }
                    }
                }
            }
        }
        if (true == isXinZen) {
            isXinZen = false;
            addView2();
        } else if (true == isQueRen) {
            isQueRen = false;
            WareHouSingModel.getInstance().setServiceModelList(serviceList);
            setResult(RESULT_OK);
            finish();
        }
    }

    //TODO 检测是否输入完整
    private void inspectIsComplete(int position) {
        View view = ll_addView.getChildAt(position);
        TextView et_zafeixiang = (TextView) view.findViewById(R.id.et_zafeixiang);
        TextView tv_zfx_code = (TextView) view.findViewById(R.id.tv_zfx_code);
        EditText et_feiyong = (EditText) view.findViewById(R.id.et_feiyong);
        TextView tv_zfx_sftj = (TextView) view.findViewById(R.id.tv_zfx_sftj);
//         && !TextUtils.isEmpty(et_feiyong.getText().toString().trim())
        if (!TextUtils.isEmpty(et_zafeixiang.getText().toString().trim())) {
            //TODO 这时候先检测列表中是否含有这个数据
            for (int i = 0; i < serviceList.size(); i++) {
                if (serviceList.get(i).getPosition() == position) {
                    //TODO 表示有此项，则只需要更改相应的参数即可
                    serviceList.get(i).setExtra_servicecode(tv_zfx_code.getText().toString().trim()); //修改杂费项代码
                    if(!TextUtils.isEmpty(et_feiyong.getText().toString().trim())){
                        serviceList.get(i).setExtra_servicevalue(Double.valueOf(et_feiyong.getText().toString().trim()));
                    }else {
                        serviceList.get(i).setExtra_servicevalue(0.0);
                    }
                    return;
                }
            }
            //如果集合中没有此数据，那么直接添加到集合中去
            if(!TextUtils.isEmpty(et_feiyong.getText().toString().trim())){
                serviceList.add(new ServiceModel(position, "", "" + UserModel.getInstance().getSt_id(), "", ""
                        , "", Double.valueOf(et_feiyong.getText().toString().trim()), tv_zfx_code.getText().toString().trim(), et_zafeixiang.getText().toString().trim(), ""));
            }else {
                serviceList.add(new ServiceModel(position, "", "" + UserModel.getInstance().getSt_id(), "", ""
                        , "", 0.0, tv_zfx_code.getText().toString().trim(), et_zafeixiang.getText().toString().trim(), ""));
            }
            tv_zfx_sftj.setText("已经添加");
        } else {
            showTipeDialog("杂费项不能为空");
        }
    }

    public void showWaiting(boolean isShow) {
        if (null != mWaitingDialog) {
            mWaitingDialog.showDialog(isShow);
        }
    }

    private void showTipeDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog = new AlertDialog(AddServiceActivity.this).builder()
                        .setTitle(getResources().getString(R.string.str_alter))
                        .setMsg(msg)
                        .setPositiveButton(getResources().getString(R.string.str_yes), new View.OnClickListener() {
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
