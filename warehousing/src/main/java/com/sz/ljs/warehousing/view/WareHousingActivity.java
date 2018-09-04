package com.sz.ljs.warehousing.view;

import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.model.OrderModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.common.view.SelectionPopForBottomView;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.warehousing.R;
import com.sz.ljs.warehousing.contract.WarehouContract;
import com.sz.ljs.warehousing.model.CalculationVolumeWeightModel;
import com.sz.ljs.warehousing.model.ChenckInModel;
import com.sz.ljs.warehousing.model.ChenckInRequestModel;
import com.sz.ljs.warehousing.model.CountryModel;
import com.sz.ljs.warehousing.model.CustomerModel;
import com.sz.ljs.warehousing.model.ProductModel;
import com.sz.ljs.warehousing.model.SelectCurrentDayBatchModel;
import com.sz.ljs.warehousing.model.ServiceModel;
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
 * 入库界面
 */

public class WareHousingActivity extends BaseActivity implements View.OnClickListener, WarehouContract.View {

    private ImageView iv_yubaokehu, iv_scan, iv_mudiguojia, iv_xiaoshouchanpin;
    private EditText et_yundanhao, et_kehucankaodanhao, et_shizhong, et_chang, et_kuan, et_gao, et_jianshu, et_khdaimabiaoji;
    private TextView et_mudiguojia, et_xiaoshouchanpin, et_kehudaima, tv_jianshu, et_daohuozongdan, et_daohuozongdan1;
    private LinearLayout ll_mudiguojia, ll_xiaoshouchanpin, ll_duojian, ll_jianshu, ll_changkuangao;
    private Button btn_qianru, btn_fujiafuwu;
    private String countryCode;//国家简码
    private boolean isYuBaoKeHu = true; //是否预报客户
    private int customerId; //客户id
    private WarehouPresenter mPresenter;
    private WaitingDialog mWaitingDialog;
    private SelectCurrentDayBatchModel.DataEntity selectCurrentDayBatchEntity;
    private OrderModel orderModel;
    private List<CountryModel.DataEntity> countryList = new ArrayList<>();
    private List<ProductModel.DataEntity> productList = new ArrayList<>();
    private List<SubnitModel> subnitList = new ArrayList<>();
    private List<ServiceModel> serviceList = new ArrayList<>();
    private ListDialog dialog;
    private CountryModel.DataEntity countryModel;
    private ProductModel.DataEntity productModel;
    private int pice = 0;//件数
    private boolean isWenTiDan = false;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让布局向上移来显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.acivity_warehousing);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mPresenter = new WarehouPresenter(this);
        mWaitingDialog = new WaitingDialog(this);
        iv_yubaokehu = (ImageView) findViewById(R.id.iv_yubaokehu);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        iv_mudiguojia = (ImageView) findViewById(R.id.iv_mudiguojia);
        iv_xiaoshouchanpin = (ImageView) findViewById(R.id.iv_xiaoshouchanpin);
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        et_khdaimabiaoji = (EditText) findViewById(R.id.et_khdaimabiaoji);
        et_kehudaima = (TextView) findViewById(R.id.et_kehudaima);
        et_kehucankaodanhao = (EditText) findViewById(R.id.et_kehucankaodanhao);
        et_mudiguojia = (TextView) findViewById(R.id.et_mudiguojia);
        et_xiaoshouchanpin = (TextView) findViewById(R.id.et_xiaoshouchanpin);
        et_shizhong = (EditText) findViewById(R.id.et_shizhong);
        et_chang = (EditText) findViewById(R.id.et_chang);
        et_kuan = (EditText) findViewById(R.id.et_kuan);
        et_gao = (EditText) findViewById(R.id.et_gao);
        et_daohuozongdan = (TextView) findViewById(R.id.et_daohuozongdan);
        et_daohuozongdan1 = (TextView) findViewById(R.id.et_daohuozongdan1);
        ll_mudiguojia = (LinearLayout) findViewById(R.id.ll_mudiguojia);
        ll_xiaoshouchanpin = (LinearLayout) findViewById(R.id.ll_xiaoshouchanpin);
        ll_duojian = (LinearLayout) findViewById(R.id.ll_duojian);
        ll_duojian.setBackgroundResource(R.drawable.pack_btn_clickbg);
        ll_duojian.setClickable(false);
        ll_changkuangao = (LinearLayout) findViewById(R.id.ll_changkuangao);
        ll_jianshu = (LinearLayout) findViewById(R.id.ll_jianshu);
        et_jianshu = (EditText) findViewById(R.id.et_jianshu);
        tv_jianshu = (TextView) findViewById(R.id.tv_jianshu);
        btn_qianru = (Button) findViewById(R.id.btn_qianru);
        btn_fujiafuwu = (Button) findViewById(R.id.btn_fujiafuwu);
    }

    private void initData() {

    }

    private void setListener() {
        ll_mudiguojia.setOnClickListener(this);
        ll_xiaoshouchanpin.setOnClickListener(this);
        ll_duojian.setOnClickListener(this);
        btn_qianru.setOnClickListener(this);
        btn_fujiafuwu.setOnClickListener(this);
        iv_yubaokehu.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        et_kehudaima.setOnClickListener(this);
        et_yundanhao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() >= GenApi.ScanNumberLeng) {
                    if (true == isYuBaoKeHu) {
                        //TODO 自动请求接口
                        getOrderByNumber();
                    } else {
                        //TODO 不做任何处理
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_jianshu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && 1 == Integer.parseInt(s.toString())) {
                    pice = Integer.parseInt(s.toString());
                    //TODO 当输入1件的时候当1件处理
                    ll_changkuangao.setVisibility(View.VISIBLE);
                    ll_duojian.setBackgroundResource(R.drawable.pack_btn_clickbg);
                    ll_duojian.setClickable(false);
                } else {
                    if (!TextUtils.isEmpty(s)) {
                        pice = Integer.parseInt(s.toString());
                        ll_changkuangao.setVisibility(View.GONE);
                        ll_duojian.setBackgroundResource(R.drawable.pack_btn_bg);
                        ll_duojian.setClickable(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_khdaimabiaoji.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (false == isYuBaoKeHu) {
                    if (TextUtils.isEmpty(s)) {
                        //TODO 客户代码为空，不给其他操作
                        et_kehucankaodanhao.setFocusable(false);
                        ll_mudiguojia.setClickable(false);
                        ll_xiaoshouchanpin.setClickable(false);
                        et_jianshu.setFocusable(false);
                        et_shizhong.setFocusable(false);
                        et_chang.setFocusable(false);
                        et_kuan.setFocusable(false);
                        et_gao.setFocusable(false);
                        btn_fujiafuwu.setClickable(false);
                        btn_qianru.setClickable(false);
                    } else {
                        et_kehucankaodanhao.setFocusable(true);
                        ll_mudiguojia.setClickable(true);
                        ll_xiaoshouchanpin.setClickable(true);
                        et_jianshu.setFocusable(true);
                        et_shizhong.setFocusable(true);
                        et_chang.setFocusable(true);
                        et_kuan.setFocusable(true);
                        et_gao.setFocusable(true);
                        btn_fujiafuwu.setClickable(true);
                        btn_qianru.setClickable(true);
                    }
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
        if (id == R.id.iv_yubaokehu) {
            //TODO 预报客户
            if (false == isYuBaoKeHu) {
                isYuBaoKeHu = true;
                clean(true);
                iv_yubaokehu.setImageResource(R.mipmap.fb_g);
                if (!TextUtils.isEmpty(et_yundanhao.getText().toString().trim())
                        && et_yundanhao.getText().toString().trim().length() >= GenApi.ScanNumberLeng) {
                    //TODO 这里防止用户先填运单号后点击预报客户，因此这里也要做判断，如果满足上述条件一样得请求接口
                    getOrderByNumber();
                }
            } else {
                isYuBaoKeHu = false;
                iv_yubaokehu.setImageResource(R.mipmap.fb_b);
            }
        } else if (id == R.id.ll_mudiguojia) {
            //TODO 目的国家
            getCountry();
        } else if (id == R.id.ll_xiaoshouchanpin) {
            //TODO 销售产品
            if (TextUtils.isEmpty(et_mudiguojia.getText().toString())) {
                showTipeDialog("请先选择目的国家");
                return;
            }
            getProduct();
        } else if (id == R.id.ll_duojian) {
            //TODO 多件
            if (TextUtils.isEmpty(et_jianshu.getText().toString().trim())) {
                return;
            }
            Intent intent = new Intent(WareHousingActivity.this, AddSubunitActivity.class);
            if (TextUtils.isEmpty(et_jianshu.getText().toString().trim())) {
                intent.putExtra("pice", 0);
            } else {
                intent.putExtra("pice", Integer.valueOf(et_jianshu.getText().toString().trim()));
            }

            if (TextUtils.isEmpty(et_yundanhao.getText().toString().trim())) {
                intent.putExtra("orderId", "");
            } else {
                intent.putExtra("orderId", Integer.valueOf(et_yundanhao.getText().toString().trim()));
            }
            if (null != selectCurrentDayBatchEntity) {
                intent.putExtra("arrival_date", selectCurrentDayBatchEntity.getArrival_date());
            } else {
                intent.putExtra("arrival_date", "");
            }
            intent.putExtra("customerId", customerId);
            startActivityForResult(intent, 1000);
        } else if (id == R.id.btn_qianru) {
            //TODO 签入
            if (pice == 1) {
                if (!TextUtils.isEmpty(et_shizhong.getText().toString().trim()) && !TextUtils.isEmpty(et_chang.getText().toString().trim())
                        && !TextUtils.isEmpty(et_kuan.getText().toString().trim()) && !TextUtils.isEmpty(et_gao.getText().toString().trim())) {
                    calculationVolumeWeight(et_shizhong.getText().toString().trim(), et_chang.getText().toString().trim(), et_kuan.getText().toString().trim(), et_gao.getText().toString().trim());
                } else {
                    showTipeDialog("长宽高不能为空");
                }
            } else {
                chenckIn();
            }
        } else if (id == R.id.btn_fujiafuwu) {
            //TODO 附加服务
            Intent intent = new Intent(WareHousingActivity.this, AddServiceActivity.class);
            if (null != serviceList && serviceList.size() > 0) {
                //TODO 订单中带有服务的，
                intent.putExtra("pice", serviceList.size());
            } else {
                intent.putExtra("pice", 1);
            }
            startActivityForResult(intent, 1001);
        } else if (id == R.id.iv_scan) {
            //TODO 扫描
            ScanView.ScanView(new ScanView.SacnCallBack() {
                @Override
                public void onResult(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        et_yundanhao.setText(result);
                    }
                }
            });
        } else if (id == R.id.et_kehudaima) {
            //TODO 客户代码
//            if (false == isYuBaoKeHu) {
            //TODO 如果不是预报客户，点击跳转到客户检索界面
            startActivityForResult(new Intent(WareHousingActivity.this, CustomerRetrievalActivity.class), 1002);
//            }
        }
    }

    @Override
    public void onResult(int Id, String result) {
        switch (Id) {
            case WarehouContract.REQUEST_FAIL_ID:
                showTipeDialog(result);
                if ("没有数据".equals(result)) {
                    //TODO 没有返回订单详情的时候
                    isWenTiDan = true;
                    et_kehucankaodanhao.setFocusable(false);
                    ll_mudiguojia.setClickable(false);
                    ll_xiaoshouchanpin.setClickable(false);
                    et_jianshu.setFocusable(false);
                    et_shizhong.setFocusable(false);
                    et_chang.setFocusable(false);
                    et_kuan.setFocusable(false);
                    et_gao.setFocusable(false);
                    btn_fujiafuwu.setClickable(false);
                    btn_qianru.setClickable(false);
                }
                break;
            case WarehouContract.CALCULATION_VOLUME_WEIGHT:
                //TODO 获取材积重等
                if (null != WareHouSingModel.getInstance().getCalculationVolumWeightModel()) {
                    SubnitModel model = new SubnitModel();
                    if (null != WareHouSingModel.getInstance().getCalculationVolumWeightModel().getLstCargoVolume()) {
                        model.setList(WareHouSingModel.getInstance().getCalculationVolumWeightModel().getLstCargoVolume());
                    }
                    subnitList.add(model);
                    WareHouSingModel.getInstance().setCalculationVolumWeightModel(null);
                    chenckIn();
                }
                break;
            case WarehouContract.CHENCK_IN_SUCCESS:
                //TODO 入库
                if (null != WareHouSingModel.getInstance().getChenckInResultModel()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog = new AlertDialog(WareHousingActivity.this).builder()
                                    .setTitle("入库成功")
                                    .setMsg("运单号：" + et_yundanhao.getText().toString().trim() + "\n" + "渠道名称:" + WareHouSingModel.getInstance().getChenckInResultModel().getServer_code())
                                    .setPositiveButton(getResources().getString(R.string.str_yes), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            WareHouSingModel.getInstance().release(); //情况入库缓存
                                            clean(true);
                                            isWenTiDan = false;
                                            isYuBaoKeHu = true;
                                            iv_yubaokehu.setImageResource(R.mipmap.fb_g);
                                        }
                                    });
                            alertDialog.show();
                        }
                    });
                }
                break;
            case WarehouContract.GET_ORDER_BY_NUMBER_SUCCESS:
                //TODO 根据运单号请求运单数据
                handgetOrderByNumber();
                break;

            case WarehouContract.SELECTCURRENT_DAYBATCH:
                //TODO 入库时选择客户生成到货总单
                if (null != WareHouSingModel.getInstance().getSelectCurrentDayBatchModel()) {
                    if (null == selectCurrentDayBatchEntity) {
                        selectCurrentDayBatchEntity = new SelectCurrentDayBatchModel.DataEntity();
                    }
                    selectCurrentDayBatchEntity = WareHouSingModel.getInstance().getSelectCurrentDayBatchModel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_daohuozongdan.setText("总单号：" + WareHouSingModel.getInstance().getSelectCurrentDayBatchModel().getArrivalbatch_labelcode());
                            et_daohuozongdan1.setText("到货时间：" + WareHouSingModel.getInstance().getSelectCurrentDayBatchModel().getArrival_date());
                        }
                    });
                }
                break;

            case WarehouContract.GET_COUNTRY_SUCCESS:
                //TODO 查询国家
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != WareHouSingModel.getInstance().getCountryList() && WareHouSingModel.getInstance().getCountryList().size() > 0) {
                            countryList.clear();
                            countryList.addAll(WareHouSingModel.getInstance().getCountryList());
                            final List<ListialogModel> showList = new ArrayList<>();
                            for (CountryModel.DataEntity model : WareHouSingModel.getInstance().getCountryList()) {
                                showList.add(new ListialogModel(model.getCountry_code(), model.getCountry_cnname(), model.getCountry_enname(), false));
                            }
                            dialog = new ListDialog(WareHousingActivity.this, R.style.AlertDialogStyle)
                                    .creatDialog()
                                    .setTitle("请选择国家")
                                    .setListData(showList)
                                    .setSeachEditTextShow(true)
                                    .setCallBackListener(new ListDialog.CallBackListener() {
                                        @Override
                                        public void Result(int position, String name) {
                                            countryModel = new CountryModel.DataEntity();
                                            for (CountryModel.DataEntity mode : countryList) {
                                                if (name.equals(mode.getCountry_cnname())) {
                                                    countryModel = mode;
                                                    et_mudiguojia.setText(mode.getCountry_code() + "-" + mode.getCountry_cnname());
                                                    break;
                                                }
                                            }
                                            dialog.dismiss();
                                        }
                                    });
                            dialog.show();
                        }
                    }
                });
                break;

            case WarehouContract.GET_PRODUCT_SUCCESS:
                //TODO 查询生效得销售产品
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != WareHouSingModel.getInstance().getProductList() && WareHouSingModel.getInstance().getProductList().size() > 0) {
                            productList.clear();
                            productList.addAll(WareHouSingModel.getInstance().getProductList());
                            final List<ListialogModel> showList = new ArrayList<>();
                            for (ProductModel.DataEntity model : WareHouSingModel.getInstance().getProductList()) {
                                showList.add(new ListialogModel(model.getProduct_code(), model.getProduct_cnname(), model.getProduct_enname(), false));
                            }
                            dialog = new ListDialog(WareHousingActivity.this, R.style.AlertDialogStyle)
                                    .creatDialog()
                                    .setTitle("请选择销售产品")
                                    .setListData(showList)
                                    .setSeachEditTextShow(false)
                                    .setCallBackListener(new ListDialog.CallBackListener() {
                                        @Override
                                        public void Result(int position, String name) {

                                            if (null == productModel) {
                                                productModel = new ProductModel.DataEntity();
                                            }
                                            for (ProductModel.DataEntity model : productList) {
                                                if (name.equals(model.getProduct_cnname())) {
                                                    et_xiaoshouchanpin.setText(model.getProduct_cnname());
                                                    productModel = model;
                                                }
                                            }
                                            dialog.dismiss();
                                        }
                                    });
                            dialog.show();
                        }
                    }
                });
                break;
        }
    }

    //TODO 获取材积重等
    private void calculationVolumeWeight(String grossweight, String length, String width, String height) {
        String arrival_date = "";
        if (null != selectCurrentDayBatchEntity) {
            arrival_date = selectCurrentDayBatchEntity.getArrival_date();
        } else {
            showTipeDialog("请选择客户代码或者填写正确运单号");
            return;
        }
        mPresenter.calculationVolumeWeight(grossweight, length, width, height, "", ""
                , arrival_date, "" + customerId);
    }

    //TODO 入库
    private void chenckIn() {
        if (TextUtils.isEmpty(et_yundanhao.getText().toString().trim())) {
            showTipeDialog("运单号不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_kehudaima.getText().toString().trim())) {
            showTipeDialog("客户代码不能为空");
            return;
        }
//        if (TextUtils.isEmpty(et_kehucankaodanhao.getText().toString().trim())) {
//            showTipeDialog("客户参考单号不能为空");
//            return;
//        }
        if (TextUtils.isEmpty(et_mudiguojia.getText().toString().trim())) {
            showTipeDialog("目的国家不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_xiaoshouchanpin.getText().toString().trim())) {
            showTipeDialog("销售产品不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_shizhong.getText().toString().trim())) {
            showTipeDialog("实重不能为空");
            return;
        }
        if (1 == pice) {
            //TODO 只有一件
            if (TextUtils.isEmpty(et_chang.getText().toString().trim())) {
                showTipeDialog("长度不能为空");
                return;
            }
            if (TextUtils.isEmpty(et_kuan.getText().toString().trim())) {
                showTipeDialog("宽度不能为空");
                return;
            }
            if (TextUtils.isEmpty(et_gao.getText().toString().trim())) {
                showTipeDialog("高度不能为空");
                return;
            }
        } else {
            if (TextUtils.isEmpty(et_jianshu.getText().toString().trim())) {
                showTipeDialog("件数不能为空");
                return;
            }
        }
        ChenckInRequestModel requestModel = new ChenckInRequestModel();
        if (null != orderModel && null != orderModel.getData()) {
            requestModel.setOrder_id(orderModel.getData().getOrder_id());
        } else {
            requestModel.setOrder_id("");
        }
        requestModel.setShipper_number(et_yundanhao.getText().toString().trim());
        requestModel.setReference_number(et_kehucankaodanhao.getText().toString().trim());
        requestModel.setCustomer_id("" + customerId);
        if (null != productModel) {
            requestModel.setPk_code(productModel.getProduct_code());
        } else {
            requestModel.setPk_code("");
        }
        if (null != countryModel) {
            requestModel.setCountry_code(countryModel.getCountry_code());
        } else {
            requestModel.setCountry_code("");
        }
        if (null != selectCurrentDayBatchEntity) {
            requestModel.setArrivalbatch_id("" + selectCurrentDayBatchEntity.getArrivalbatch_id());
            requestModel.setArrival_date(selectCurrentDayBatchEntity.getArrival_date());
            requestModel.setCustomer_channelid("" + selectCurrentDayBatchEntity.getCustomer_channelid());
        } else {
            requestModel.setArrivalbatch_id("");
            requestModel.setArrival_date("");
            requestModel.setCustomer_channelid("");
        }
        requestModel.setCheckin_og_id("" + UserModel.getInstance().getOg_id());
        requestModel.setShipper_weight(et_shizhong.getText().toString().trim());
        if (TextUtils.isEmpty(et_jianshu.getText().toString())) {
            requestModel.setShipper_pieces("" + pice);
        } else {
            requestModel.setShipper_pieces(et_jianshu.getText().toString());
        }
        if (1 == pice) {
            requestModel.setLen(et_chang.getText().toString().trim());
            requestModel.setHeight(et_gao.getText().toString().trim());
            requestModel.setWidth(et_kuan.getText().toString().trim());
        } else {
            requestModel.setLen("");
            requestModel.setHeight("");
            requestModel.setWidth("");
        }
        requestModel.setUser_Name(UserModel.getInstance().getSt_name());
        requestModel.setOG_CityEnName(UserModel.getInstance().getOg_cityenname());
        if (null != subnitList && subnitList.size() > 0) {
            List<CalculationVolumeWeightModel.DataEntity.LstCargoVolumeEntity> list = new ArrayList<>();
            for (SubnitModel model : subnitList) {
                list.add(model.getList().get(0));
            }
            requestModel.setCargoVolumes(new Gson().toJson(list));
        } else {
            requestModel.setCargoVolumes("");
        }
        if (null != serviceList && serviceList.size() > 0) {
            requestModel.setM_lstExtraService(new Gson().toJson(serviceList));
        } else {
            requestModel.setM_lstExtraService("");
        }
        mPresenter.chenckIn(requestModel);
    }

    //TODO 根据运单号请求运单数据
    private void getOrderByNumber() {
        mPresenter.getOrderByNumber(et_yundanhao.getText().toString().trim());
    }

    //TODO 处理运单数据
    private void handgetOrderByNumber() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != WareHouSingModel.getInstance().getOrderModel()
                        && null != WareHouSingModel.getInstance().getOrderModel().getData()) {
                    isWenTiDan = false;
                    et_kehudaima.setClickable(true);
                    et_kehucankaodanhao.setFocusable(true);
                    ll_mudiguojia.setClickable(true);
                    ll_xiaoshouchanpin.setClickable(true);
                    et_jianshu.setFocusable(true);
                    et_shizhong.setFocusable(true);
                    et_chang.setFocusable(true);
                    et_kuan.setFocusable(true);
                    et_gao.setFocusable(true);
                    btn_fujiafuwu.setClickable(true);
                    btn_qianru.setClickable(true);
                    if (null == orderModel) {
                        orderModel = new OrderModel();
                    }
                    orderModel = WareHouSingModel.getInstance().getOrderModel();
                    pice = Integer.parseInt(WareHouSingModel.getInstance().getOrderModel().getData().getOrder_pieces());
                    customerId = Integer.parseInt(orderModel.getData().getCustomer_id());
                    if (!TextUtils.isEmpty(WareHouSingModel.getInstance().getOrderModel().getData().getCustomer_id()) && !TextUtils.isEmpty(WareHouSingModel.getInstance().getOrderModel().getData().getCountry_code())) {
                        et_kehudaima.setText(WareHouSingModel.getInstance().getOrderModel().getData().getCustomer_code());
                        selectCurrentDayBatch(WareHouSingModel.getInstance().getOrderModel().getData().getCustomer_id(), WareHouSingModel.getInstance().getOrderModel().getData().getCountry_code());
                    }
                    if (!TextUtils.isEmpty(WareHouSingModel.getInstance().getOrderModel().getData().getCountry_code())) {
                        countryModel = new CountryModel.DataEntity();
                        countryModel.setCountry_code(WareHouSingModel.getInstance().getOrderModel().getData().getCountry_code());
                        et_mudiguojia.setText(WareHouSingModel.getInstance().getOrderModel().getData().getCountry_code());
                    }
                    if (!TextUtils.isEmpty(WareHouSingModel.getInstance().getOrderModel().getData().getProduct_cnname())) {
                        productModel = new ProductModel.DataEntity();
                        productModel.setProduct_code(WareHouSingModel.getInstance().getOrderModel().getData().getProduct_code());
                        productModel.setProduct_cnname(WareHouSingModel.getInstance().getOrderModel().getData().getProduct_cnname());
                        et_xiaoshouchanpin.setText(WareHouSingModel.getInstance().getOrderModel().getData().getProduct_cnname());
                    }

                    if (null != orderModel.getData() && null != orderModel.getData().getExtraservice() && orderModel.getData().getExtraservice().size() > 0) {
                        WareHouSingModel.getInstance().setExtrasList(orderModel.getData().getExtraservice());
                        serviceList.clear();
                        for (int i = 0; i < orderModel.getData().getExtraservice().size(); i++) {
                            ServiceModel models = new ServiceModel(i, "", "" + UserModel.getInstance().getSt_id(), "", ""
                                    , "", Double.valueOf(orderModel.getData().getExtraservice().get(i).getExtra_servicevalue())
                                    , orderModel.getData().getExtraservice().get(i).getExtra_servicecode(), orderModel.getData().getExtraservice().get(i).getExtra_service_cnname(), "");
                            serviceList.add(models);
                        }
                        WareHouSingModel.getInstance().setServiceModelList(serviceList);
                    }
                    if (1 == Integer.parseInt(WareHouSingModel.getInstance().getOrderModel().getData().getOrder_pieces())) {
                        tv_jianshu.setText("1");
                        tv_jianshu.setVisibility(View.VISIBLE);
                        et_jianshu.setVisibility(View.GONE);
                        ll_changkuangao.setVisibility(View.VISIBLE);
                        ll_duojian.setBackgroundResource(R.drawable.pack_btn_clickbg);
                        ll_duojian.setClickable(false);

                    } else if (Integer.parseInt(WareHouSingModel.getInstance().getOrderModel().getData().getOrder_pieces()) > 1) {
                        tv_jianshu.setVisibility(View.GONE);
                        et_jianshu.setVisibility(View.VISIBLE);
                        ll_changkuangao.setVisibility(View.GONE);
                        et_jianshu.setText(WareHouSingModel.getInstance().getOrderModel().getData().getOrder_pieces());
                        ll_duojian.setBackgroundResource(R.drawable.pack_btn_bg);
                        ll_duojian.setClickable(true);
                    }
                } else {
                    //TODO 没有返回订单详情的时候
                    isWenTiDan = true;
                    et_kehucankaodanhao.setFocusable(false);
                    ll_mudiguojia.setClickable(false);
                    ll_xiaoshouchanpin.setClickable(false);
                    et_jianshu.setFocusable(false);
                    et_shizhong.setFocusable(false);
                    et_chang.setFocusable(false);
                    et_kuan.setFocusable(false);
                    et_gao.setFocusable(false);
                    btn_fujiafuwu.setClickable(false);
                    btn_qianru.setClickable(false);
                }
            }
        });
    }

    //TODO 入库时选择客户生成到货总单
    private void selectCurrentDayBatch(String customer_id, String customer_code) {
        mPresenter.selectCurrentDayBatch(customer_id, customer_code);
    }

    //TODO 查询国家
    private void getCountry() {
        mPresenter.getCountry();
    }

    //TODO 查询生效得销售产品
    private void getProduct() {
        mPresenter.getProduct();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000: {
                    subnitList.clear();
                    List<SubnitModel> list = new ArrayList<>();
                    list = WareHouSingModel.getInstance().getSubnitList();
                    subnitList.addAll(list);
                    et_shizhong.setText("" + WareHouSingModel.getInstance().getTotalGrossWeight());
                    et_jianshu.setText("" + subnitList.size());
                    Log.i("多件返回后", "subnitList.size()=" + subnitList.size());
                }
                break;
                case 1001: {
                    serviceList.clear();
                    List<ServiceModel> list = new ArrayList<>();
                    list = WareHouSingModel.getInstance().getServiceModelList();
                    serviceList.addAll(list);
                    Log.i("服务费返回后", "serviceList.size()=" + serviceList.size());
                }
                break;
                case 1002: {
                    //TODO 客户代码选择
                    if (null != data) {
                        customerId = data.getIntExtra("id", -1);
                        if (null != WareHouSingModel.getInstance().getCustomerModel(customerId)) {
                            et_kehudaima.setText(WareHouSingModel.getInstance().getCustomerModel(customerId).getCustomer_code());
                            selectCurrentDayBatch(String.valueOf(customerId), WareHouSingModel.getInstance().getCustomerModel(customerId).getCustomer_code());
                            if (pice > 1) {
                                ll_duojian.setBackgroundResource(R.drawable.pack_btn_bg);
                                ll_duojian.setClickable(true);
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    //TODO 清空所有内容，然后在通过传入的boolean值对控件进行对应的操作
    private void clean(boolean isA) {
        et_kehucankaodanhao.setFocusable(isA);
        ll_mudiguojia.setClickable(isA);
        ll_xiaoshouchanpin.setClickable(isA);
        et_jianshu.setFocusable(isA);
        et_shizhong.setFocusable(isA);
        et_chang.setFocusable(isA);
        et_kuan.setFocusable(isA);
        et_gao.setFocusable(isA);
        btn_fujiafuwu.setClickable(isA);
        btn_qianru.setClickable(isA);
        et_yundanhao.setText("");
        et_kehudaima.setText("");
        et_kehucankaodanhao.setText("");
        et_mudiguojia.setText("");
        et_xiaoshouchanpin.setText("");
        et_jianshu.setText("");
        et_shizhong.setText("");
        et_chang.setText("");
        et_kuan.setText("");
        et_gao.setText("");
        et_daohuozongdan.setText("");
        et_daohuozongdan1.setText("");
        WareHouSingModel.getInstance().release();
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
                alertDialog = new AlertDialog(WareHousingActivity.this).builder()
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
