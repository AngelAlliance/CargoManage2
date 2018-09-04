package com.sz.ljs.warehousing.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.warehousing.R;
import com.sz.ljs.warehousing.contract.WarehouContract;
import com.sz.ljs.warehousing.model.CalculationVolumeWeightModel;
import com.sz.ljs.warehousing.model.SubnitModel;
import com.sz.ljs.warehousing.model.WareHouSingModel;
import com.sz.ljs.warehousing.presenter.WarehouPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 添加子单界面
 */
public class AddSubunitActivity extends BaseActivity implements View.OnClickListener, WarehouContract.View {
    private LinearLayout ll_addView;
    private TextView tv_zongjianshu, tv_zongshizhong, tv_zongcaiji;
    private Button btn_queren, btn_xinzeng;
    private List<SubnitModel> subnitList = new ArrayList<>();
    private int pice = 0;//件数
    private int pices = 1;
    private double zongshizhong = 0; //总实重
    private double volume = 0;  //总材积
    private double TotalVolumeWeight;//总材积重
    private double TotalChargeWeight;   //总计费重
    private String orderId = ""; //运单id
    private int customerId = 0;//客户id
    private String arrival_date = "";//到货时间
    private WarehouPresenter mPresenter;
    private WaitingDialog mWaitingDialog;
    private int et_changIndex = 0, et_kuanIndex = 0, et_gaoIndex = 0;
    private boolean isXinZen = false, isQueRen = false;
    private AlertDialog alertDialog;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsubunit);
        initView();
        setListener();
    }

    private void initView() {
        mPresenter = new WarehouPresenter(this);
        mWaitingDialog = new WaitingDialog(this);
        pices = getIntent().getIntExtra("pice", 1);
        orderId = getIntent().getStringExtra("orderId");
        arrival_date = getIntent().getStringExtra("arrival_date");
        customerId = getIntent().getIntExtra("customerId", 0);
        ll_addView = (LinearLayout) findViewById(R.id.ll_addView);
        tv_zongjianshu = (TextView) findViewById(R.id.tv_zongjianshu);
        tv_zongshizhong = (TextView) findViewById(R.id.tv_zongshizhong);
        tv_zongcaiji = (TextView) findViewById(R.id.tv_zongcaiji);
        btn_queren = (Button) findViewById(R.id.btn_queren);
        btn_xinzeng = (Button) findViewById(R.id.btn_xinzeng);
        for (int i = 0; i < pices; i++) {
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
            inspectIsTiJiao();
        } else if (id == R.id.btn_xinzeng) {
            //TODO 新增
            isXinZen = true;
            inspectIsTiJiao();
        } else if (id == R.id.btn_shanchu) {
            Log.i("点击删除按钮", "position=" + v.getTag());
            removeView(v);
        } else if (id == R.id.iv_scan) {
            handelScan(v);
            Log.i("点击扫描按钮", "position=" + v.getTag());
        }
    }

    //TODO 处理扫描
    private void handelScan(final View view) {
        ScanView.ScanView(new ScanView.SacnCallBack() {
            @Override
            public void onResult(String result) {
                int index = (Integer) view.getTag();
                View view1 = ll_addView.getChildAt(index);
                TextView et_zidanhao = (TextView) view1.findViewById(R.id.et_zidanhao);
            }
        });
    }

    @Override
    public void onResult(int Id, String result) {
        switch (Id) {
            case WarehouContract.REQUEST_FAIL_ID:
                showTipeDialog(result);
                break;
            case WarehouContract.CALCULATION_VOLUME_WEIGHT:
                //TODO 获取材积重等
                handCalculationVolumeWeight();
                break;
        }
    }

    //TODO 处理材积重、计费重返回数据
    private void handCalculationVolumeWeight() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SubnitModel model = new SubnitModel();
                model.setPosition(index);
                if (null != WareHouSingModel.getInstance().getCalculationVolumWeightModel() && null != WareHouSingModel.getInstance().getCalculationVolumWeightModel().getLstCargoVolume()) {
                    model.setList(WareHouSingModel.getInstance().getCalculationVolumWeightModel().getLstCargoVolume());
                }
                if (null != subnitList && subnitList.size() > 0) {
                    for (int i = 0; i < subnitList.size(); i++) {
                        if (index == subnitList.get(i).getPosition()) {
                            TotalVolumeWeight = TotalVolumeWeight - subnitList.get(i).getList().get(0).getVolumeWeight();
                            TotalChargeWeight = TotalVolumeWeight - subnitList.get(i).getList().get(0).getChargeWeight();
                            zongshizhong = zongshizhong - subnitList.get(i).getList().get(0).getGrossWeight();
                            volume = volume + WareHouSingModel.getInstance().getCalculationVolumWeightModel().getVolume();
                            break;
                        }
                    }
                }
                subnitList.add(model);
                TotalVolumeWeight = TotalVolumeWeight + WareHouSingModel.getInstance().getCalculationVolumWeightModel().getTotalVolumeWeight();
                TotalChargeWeight = TotalVolumeWeight + WareHouSingModel.getInstance().getCalculationVolumWeightModel().getTotalChargeWeight();
                zongshizhong = zongshizhong + WareHouSingModel.getInstance().getCalculationVolumWeightModel().getTotalGrossWeight();
                volume = volume + WareHouSingModel.getInstance().getCalculationVolumWeightModel().getVolume();
                tv_zongshizhong.setText("" + zongshizhong + "KG");
                tv_zongcaiji.setText("" + volume + "M²");
                View view = ll_addView.getChildAt(index);
                TextView tv_isTiJiao = (TextView) view.findViewById(R.id.tv_isTiJiao);
                tv_isTiJiao.setText("已提交");
                if (true == isXinZen) {
                    addView();
                    isXinZen = false;
                } else if (true == isQueRen) {
                    isQueRen = false;
                    //TODO 点击了确认，要返回去了
                    WareHouSingModel.getInstance().setSubnitList(subnitList);
                    WareHouSingModel.getInstance().setVolume(volume);
                    WareHouSingModel.getInstance().setTotalChargeWeight(TotalChargeWeight);
                    WareHouSingModel.getInstance().setTotalGrossWeight(zongshizhong);
                    WareHouSingModel.getInstance().setTotalVolumeWeight(TotalVolumeWeight);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    //TODO 查询材积重、计费重
    private void calculationVolumeWeight(final int position, String grossweight, String length, String width, String height) {
        index = position;
        mPresenter.calculationVolumeWeight(grossweight, length, width, height, "", "", arrival_date
                , "" + customerId);
    }

    //TODO 移除
    private void removeView(View view) {
        if (0 < ll_addView.getChildCount()) {
            int position = (Integer) view.getTag();
            pice--;
            if (null != subnitList && subnitList.size() == position + 1) {
                for (int i = 0; i < subnitList.size(); i++) {
                    //TODO 为了找到删除的项
                    if (subnitList.get(i).getPosition() == position) {
                        zongshizhong = zongshizhong - subnitList.get(i).getList().get(0).getGrossWeight();
                        volume = volume - subnitList.get(i).getList().get(0).getVolume();
                        TotalVolumeWeight = TotalVolumeWeight - subnitList.get(i).getList().get(0).getVolumeWeight();
                        TotalChargeWeight = TotalChargeWeight - subnitList.get(i).getList().get(0).getChargeWeight();
                        subnitList.remove(i);
                        break;
                    }
                }
            }
            tv_zongjianshu.setText("" + pice);
            tv_zongshizhong.setText("" + zongshizhong + "KG");
            tv_zongcaiji.setText("" + volume + "M²");
            ll_addView.removeView(ll_addView.getChildAt((Integer) view.getTag()));
            if (0 == ll_addView.getChildCount()) {
                addView();
            }
        }
//        else {
//            subnitList.remove((Integer) view.getTag());
//            pice = 0;
//            zongshizhong = 0;
//            zongcaiji = 0;
//            tv_zongjianshu.setText("");
//            tv_zongshizhong.setText("");
//            tv_zongcaiji.setText("");
//        }
    }

    //TODO 新增
    private void addView() {
        View view = LayoutInflater.from(AddSubunitActivity.this).inflate(R.layout.view_subnit, null);
        int position = ll_addView.getChildCount();
        Button button = (Button) view.findViewById(R.id.btn_shanchu);
        button.setTag(position);
        button.setOnClickListener(this);
        TextView tv_isTiJiao = (TextView) view.findViewById(R.id.tv_isTiJiao);
        TextView textView = (TextView) view.findViewById(R.id.et_zidanhao);
        textView.setTag(position);
        if (TextUtils.isEmpty(orderId)) {
            textView.setText("000" + position);
        } else {
            textView.setText(orderId + "_000" + position);
        }
        ImageView ic_scan = (ImageView) view.findViewById(R.id.iv_scan);
        ic_scan.setTag(position);
        ic_scan.setOnClickListener(this);
        EditText et_shizhong = (EditText) view.findViewById(R.id.et_shizhong);
        et_shizhong.setTag(position);

        EditText et_chang = (EditText) view.findViewById(R.id.et_chang);
        et_chang.setTag(position);
        et_chang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_changIndex = (Integer) v.getTag();
                } else {
                    inspectIsComplete(et_changIndex);
                }
            }
        });
        EditText et_kuan = (EditText) view.findViewById(R.id.et_kuan);
        et_kuan.setTag(position);
        et_kuan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_kuanIndex = (Integer) v.getTag();
                } else {
                    inspectIsComplete(et_kuanIndex);
                }
            }
        });

        EditText et_gao = (EditText) view.findViewById(R.id.et_gao);
        et_gao.setTag(position);
        et_gao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_gaoIndex = (Integer) v.getTag();
                } else {
                    inspectIsComplete(et_gaoIndex);
                }
            }
        });
        ll_addView.addView(view);
        pice++;
        tv_zongjianshu.setText("" + pice);
    }

    //TODO 检查所有item数据是否提交后台
    private void inspectIsTiJiao() {
        for (int i = 0; i < ll_addView.getChildCount(); i++) {
            View view = ll_addView.getChildAt(i);
            TextView tv_isTiJiao = (TextView) view.findViewById(R.id.tv_isTiJiao);
            EditText et_shizhong = (EditText) view.findViewById(R.id.et_shizhong);
            EditText et_chang = (EditText) view.findViewById(R.id.et_chang);
            EditText et_kuan = (EditText) view.findViewById(R.id.et_kuan);
            EditText et_gao = (EditText) view.findViewById(R.id.et_gao);
            if (TextUtils.isEmpty(tv_isTiJiao.getText().toString().trim())) {
                if (TextUtils.isEmpty(et_shizhong.getText().toString().trim())) {
                    Utils.showToast(getBaseActivity(), "重量不能为空");
                    return;
                }
                //表示这个item还没完成提交
                if (!TextUtils.isEmpty(et_chang.getText().toString().trim())
                        && !TextUtils.isEmpty(et_kuan.getText().toString().trim())
                        && !TextUtils.isEmpty(et_gao.getText().toString().trim())) {
                    calculationVolumeWeight(i, et_shizhong.getText().toString().trim(), et_chang.getText().toString().trim(),
                            et_kuan.getText().toString().trim(), et_gao.getText().toString().trim());
                } else {
                    Utils.showToast(getBaseActivity(), "请输入完整");
                }
                break;
            }
        }
    }

    //  TODO 检查是否输入完整
    private void inspectIsComplete(int position) {
        if (ll_addView.getChildCount() > position) {
            View view1 = ll_addView.getChildAt(position);
            EditText et_chang = (EditText) view1.findViewById(R.id.et_chang);
            EditText et_shizhong = (EditText) view1.findViewById(R.id.et_shizhong);
            EditText et_kuan = (EditText) view1.findViewById(R.id.et_kuan);
            EditText et_gao = (EditText) view1.findViewById(R.id.et_gao);
            if (TextUtils.isEmpty(et_shizhong.getText().toString().trim())) {
                Utils.showToast(getBaseActivity(), "重量不能为空");
                return;
            }
            if (!TextUtils.isEmpty(et_chang.getText().toString().trim())
                    && !TextUtils.isEmpty(et_kuan.getText().toString().trim())
                    && !TextUtils.isEmpty(et_gao.getText().toString().trim())) {
                calculationVolumeWeight(position, et_shizhong.getText().toString().trim(), et_chang.getText().toString().trim(),
                        et_kuan.getText().toString().trim(), et_gao.getText().toString().trim());
//                Log.i("来了来了","position="+position);
            } else {
//                Utils.showToast(getBaseActivity(), "请输入完整");
            }
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
                alertDialog = new AlertDialog(AddSubunitActivity.this).builder()
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
