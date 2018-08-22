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

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.SelectionPopForBottomView;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.warehousing.R;
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
public class AddServiceActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_addView;
    private Button btn_queren, btn_xinzeng;
    private List<ServiceModel> serviceList = new ArrayList<>();
    private WaitingDialog mWaitingDialog = null;
    private List<GsonIncidentalModel.DataBean> beanList = new ArrayList<>();
    private List<ListialogModel> showList = new ArrayList<>();
    private WarehouPresenter mPresenter;
    private ListDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addservice);
        initView();
        setListener();
    }

    private void initView() {
        mPresenter = new WarehouPresenter();
        mWaitingDialog = new WaitingDialog(this);
        ll_addView = (LinearLayout) findViewById(R.id.ll_addView);
        btn_queren = (Button) findViewById(R.id.btn_queren);
        btn_xinzeng = (Button) findViewById(R.id.btn_xinzeng);
        addView();
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
        } else if (id == R.id.btn_xinzeng) {
            //TODO 新增
            handelAddView();
        } else if (id == R.id.btn_shanchu) {
            Log.i("点击删除按钮", "position=" + v.getTag());
            removeView(v);
        } else if (id == R.id.ll_zafeixiang) {
            Log.i("选择杂费项", "position=" + v.getTag());
            choseZaFeiXiang(v);
        }
    }

    //TODO 处理新增（这里需要判断上一个费用是否输入完整）
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
            ServiceModel model = new ServiceModel();
            model.setKind(beanList.get(index).getExtra_service_kind());
            model.setZafeixiang(et_zafeixiang.getText().toString().trim());
            model.setFeiyong(Double.valueOf(et_feiyong.getText().toString().trim()));
            serviceList.add(model);
            WareHouSingModel.getInstance().setServiceModelList(serviceList);
            addView();
        }
    }


    //TODO 选择杂费项内容
    private void choseZaFeiXiang(final View view) {
        showWaiting(true);
        if (null == mPresenter) {
            mPresenter = new WarehouPresenter();
        }
        mPresenter.getIncidental()
                .compose(this.<GsonIncidentalModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GsonIncidentalModel>() {
                    @Override
                    public void accept(GsonIncidentalModel result) throws Exception {
                        showWaiting(false);
                        if (0 == result.getCode()) {
                            Utils.showToast(getBaseActivity(), result.getMsg());
                        } else if (1 == result.getCode()) {
                            handelServiceResult(result, view);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //获取失败，提示
                        showWaiting(false);
                        Utils.showToast(getBaseActivity(), R.string.str_dlsb);
                    }
                });
    }

    //TODO 杂费项数据处理
    private void handelServiceResult(GsonIncidentalModel result, final View view) {
        if (null != result && null != result.getData() && result.getData().size() > 0) {
            beanList.clear();
            beanList.addAll(result.getData());
            for (GsonIncidentalModel.DataBean brean : beanList) {
                showList.add(new ListialogModel(brean.getExtra_service_cnname(), false));
            }
            dialog = new ListDialog(AddServiceActivity.this,R.style.AlertDialogStyle)
                    .creatDialog()
                    .setTitle("请选择杂费项")
                    .setListData(showList)
                    .setCallBackListener(new ListDialog.CallBackListener() {
                        @Override
                        public void Result(int position, String name) {
                            int index = (Integer) view.getTag();
                            View view1 = ll_addView.getChildAt(index);
                            EditText et_zafeixiang = (EditText) view1.findViewById(R.id.et_zafeixiang);
                            et_zafeixiang.setText(showList.get(position).getName());
                            dialog.dismiss();
                        }
                    });
            dialog.show();
//            SelectionPopForBottomView.SelectionPopForBottomView(AddServiceActivity.this, "请选择杂费项", showList, new SelectionPopForBottomView.ContentItemOnClickListener() {
//                @Override
//                public void ItemOclick(int position) {
//
//                }
//            });
        }
    }

    //TODO 移除
    private void removeView(View view) {
        if (0 < ll_addView.getChildCount()) {
            int position = (Integer) view.getTag();
            if (null != serviceList && serviceList.size() == position + 1) {
                serviceList.remove(position);
                WareHouSingModel.getInstance().removeServiceModel(position);
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
        int position = ll_addView.getChildCount();
        Button button = (Button) view.findViewById(R.id.btn_shanchu);
        button.setTag(position);
        button.setOnClickListener(this);
        LinearLayout ll_zafeixiang = (LinearLayout) view.findViewById(R.id.ll_zafeixiang);
        ll_zafeixiang.setTag(position);
        ll_zafeixiang.setOnClickListener(this);
        ll_addView.addView(view);
    }

    private void showWaiting(boolean isShow) {
        if (null != mWaitingDialog) {
            mWaitingDialog.showDialog(isShow);
        }
    }

}
