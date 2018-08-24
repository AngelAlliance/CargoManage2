package com.sz.ljs.warehousing.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.model.OrderModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.common.view.SelectionPopForBottomView;
import com.sz.ljs.common.view.WaitingDialog;
import com.sz.ljs.warehousing.R;
import com.sz.ljs.warehousing.model.CalculationVolumeWeightModel;
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

public class WareHousingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_yubaokehu, iv_scan, iv_mudiguojia, iv_xiaoshouchanpin;
    private EditText et_yundanhao, et_kehucankaodanhao, et_shizhong, et_chang, et_kuan, et_gao, et_jianshu;
    private TextView et_mudiguojia, et_xiaoshouchanpin, et_kehudaima, tv_jianshu, et_daohuozongdan;
    private LinearLayout ll_mudiguojia, ll_xiaoshouchanpin, ll_duojian, ll_jianshu, ll_changkuangao;
    private Button btn_qianru, btn_fujiafuwu;
    private String countryCode;//国家简码
    private boolean isYuBaoKeHu = false; //是否预报客户
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
    private boolean isWenTiDan=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_warehousing);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mPresenter = new WarehouPresenter();
        mWaitingDialog = new WaitingDialog(this);
        iv_yubaokehu = (ImageView) findViewById(R.id.iv_yubaokehu);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        iv_mudiguojia = (ImageView) findViewById(R.id.iv_mudiguojia);
        iv_xiaoshouchanpin = (ImageView) findViewById(R.id.iv_xiaoshouchanpin);
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        et_kehudaima = (TextView) findViewById(R.id.et_kehudaima);
        et_kehucankaodanhao = (EditText) findViewById(R.id.et_kehucankaodanhao);
        et_mudiguojia = (TextView) findViewById(R.id.et_mudiguojia);
        et_xiaoshouchanpin = (TextView) findViewById(R.id.et_xiaoshouchanpin);
        et_shizhong = (EditText) findViewById(R.id.et_shizhong);
        et_chang = (EditText) findViewById(R.id.et_chang);
        et_kuan = (EditText) findViewById(R.id.et_kuan);
        et_gao = (EditText) findViewById(R.id.et_gao);
        et_daohuozongdan = (TextView) findViewById(R.id.et_daohuozongdan);
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
                if (!TextUtils.isEmpty(s) && s.length() == GenApi.ScanNumberLeng) {
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
                    pice=Integer.parseInt(s.toString());
                    //TODO 当输入1件的时候当1件处理
                    ll_changkuangao.setVisibility(View.VISIBLE);
                    ll_duojian.setBackgroundResource(R.drawable.pack_btn_clickbg);
                    ll_duojian.setClickable(false);
                } else {
                    if(!TextUtils.isEmpty(s)){
                        pice=Integer.parseInt(s.toString());
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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_yubaokehu) {
            //TODO 预报客户
            if (false == isYuBaoKeHu) {
                isYuBaoKeHu = true;
                iv_yubaokehu.setImageResource(R.mipmap.fb_g);
                if (!TextUtils.isEmpty(et_yundanhao.getText().toString().trim())
                        && et_yundanhao.getText().toString().trim().length() == GenApi.ScanNumberLeng) {
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
            getProduct();
        } else if (id == R.id.ll_duojian) {
            //TODO 多件
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
            if(pice==1){
                if (!TextUtils.isEmpty(et_shizhong.getText().toString().trim())&&!TextUtils.isEmpty(et_chang.getText().toString().trim())
                &&!TextUtils.isEmpty(et_kuan.getText().toString().trim())&&!TextUtils.isEmpty(et_gao.getText().toString().trim())){
                    calculationVolumeWeight(et_shizhong.getText().toString().trim(),et_chang.getText().toString().trim(),et_kuan.getText().toString().trim(),et_gao.getText().toString().trim());
                }else {
                    Utils.showToast(getBaseActivity(),"长宽高不能为空");
                }
            }else {
                chenckIn();
            }
        } else if (id == R.id.btn_fujiafuwu) {
            //TODO 附加服务
            Intent intent = new Intent(WareHousingActivity.this, AddServiceActivity.class);
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
            if (false == isYuBaoKeHu) {
                //TODO 如果不是预报客户，点击跳转到客户检索界面
                startActivityForResult(new Intent(WareHousingActivity.this, CustomerRetrievalActivity.class), 1002);
            }
        }
    }

    //TODO 获取材积重等
    private void calculationVolumeWeight(String grossweight,String length, String width, String height){
        mPresenter.calculationVolumeWeight(grossweight, length, width, height, "", "", ""
                , "" + customerId)
                .compose(this.<CalculationVolumeWeightModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CalculationVolumeWeightModel>() {
                    @Override
                    public void accept(CalculationVolumeWeightModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            Utils.showToast(WareHousingActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            showWaiting(false);
                            SubnitModel model = new SubnitModel();
                            if (null != result.getData() && null != result.getData().getLstCargoVolume()) {
                                model.setList(result.getData().getLstCargoVolume());
                            }
                            subnitList.add(model);
                            chenckIn();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        Utils.showToast(getBaseActivity(), R.string.str_qqsb);
                    }
                });
    };
    //TODO 入库
    private void chenckIn(){
        if (TextUtils.isEmpty(et_yundanhao.getText().toString().trim())){
            Utils.showToast(getBaseActivity(),"运单号不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_kehudaima.getText().toString().trim())){
            Utils.showToast(getBaseActivity(),"客户代码不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_kehucankaodanhao.getText().toString().trim())){
            Utils.showToast(getBaseActivity(),"客户参考单号不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_mudiguojia.getText().toString().trim())){
            Utils.showToast(getBaseActivity(),"目的国家不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_xiaoshouchanpin.getText().toString().trim())){
            Utils.showToast(getBaseActivity(),"销售产品不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_shizhong.getText().toString().trim())){
            Utils.showToast(getBaseActivity(),"实重不能为空");
            return;
        }
        if(1==pice){
            //TODO 只有一件
            if (TextUtils.isEmpty(et_chang.getText().toString().trim())){
                Utils.showToast(getBaseActivity(),"长度不能为空");
                return;
            }
            if (TextUtils.isEmpty(et_kuan.getText().toString().trim())){
                Utils.showToast(getBaseActivity(),"宽度不能为空");
                return;
            }
            if (TextUtils.isEmpty(et_gao.getText().toString().trim())){
                Utils.showToast(getBaseActivity(),"高度不能为空");
                return;
            }
        }else {
            if (TextUtils.isEmpty(et_jianshu.getText().toString().trim())){
                Utils.showToast(getBaseActivity(),"件数不能为空");
                return;
            }
        }
        showWaiting(true);
        ChenckInRequestModel requestModel=new ChenckInRequestModel();
        if(null!=orderModel&&null!=orderModel.getData()){
            requestModel.setOrder_id(orderModel.getData().getOrder_id());
        }else {
            requestModel.setOrder_id("");
        }
        requestModel.setShipper_number(et_yundanhao.getText().toString().trim());
        requestModel.setReference_number(et_kehucankaodanhao.getText().toString().trim());
        requestModel.setCustomer_id(""+customerId);
        if(null!=productModel){
            requestModel.setPk_code(productModel.getProduct_code());
        }else {
            requestModel.setPk_code("");
        }
        if(null!=countryModel){
            requestModel.setCountry_code(countryModel.getCountry_code());
        }else {
            requestModel.setCountry_code("");
        }
        if(null!=selectCurrentDayBatchEntity){
            requestModel.setArrivalbatch_id(""+selectCurrentDayBatchEntity.getArrivalbatch_id());
            requestModel.setArrival_date(selectCurrentDayBatchEntity.getArrival_date());
            requestModel.setCustomer_channelid(""+selectCurrentDayBatchEntity.getCustomer_channelid());
        }else {
            requestModel.setArrivalbatch_id("");
            requestModel.setArrival_date("");
            requestModel.setCustomer_channelid("");
        }
        requestModel.setCheckin_og_id(""+UserModel.getInstance().getOg_id());
        requestModel.setShipper_weight(et_shizhong.getText().toString().trim());
        if(TextUtils.isEmpty(et_jianshu.getText().toString())){
            requestModel.setShipper_pieces(""+pice);
        }else {
            requestModel.setShipper_pieces(et_jianshu.getText().toString());
        }
        if(1==pice){
            requestModel.setLen(et_chang.getText().toString().trim());
            requestModel.setHeight(et_gao.getText().toString().trim());
            requestModel.setWidth(et_kuan.getText().toString().trim());
        }else {
            requestModel.setLen("");
            requestModel.setHeight("");
            requestModel.setWidth("");
        }
        requestModel.setUser_Name(UserModel.getInstance().getSt_name());
        requestModel.setOG_CityEnName(UserModel.getInstance().getOg_cityenname());
        if(null!=subnitList&&subnitList.size()>0){
            List<CalculationVolumeWeightModel.DataEntity.LstCargoVolumeEntity> list=new ArrayList<>();
            for (SubnitModel model:subnitList){
                list.add(model.getList().get(0));
            }
            requestModel.setCargoVolumes(new Gson().toJson(list));
        }else {
            requestModel.setCargoVolumes("");
        }
        if(null!=serviceList&&serviceList.size()>0){
            requestModel.setM_lstExtraService(new Gson().toJson(serviceList));
        }else {
            requestModel.setM_lstExtraService("");
        }
        mPresenter.chenckIn(requestModel)
                .compose(this.<BaseResultModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResultModel>() {
                    @Override
                    public void accept(BaseResultModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            Utils.showToast(WareHousingActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            showWaiting(false);
                            Utils.showToast(WareHousingActivity.this, result.getMsg());
                            WareHouSingModel.getInstance().release(); //情况入库缓存
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        Utils.showToast(getBaseActivity(), R.string.str_qqsb);
                    }
                });
    }
    //TODO 根据运单号请求运单数据
    private void getOrderByNumber() {
        showWaiting(true);
        mPresenter.getOrderByNumber(et_yundanhao.getText().toString().trim())
                .compose(this.<OrderModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderModel>() {
                    @Override
                    public void accept(OrderModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            Utils.showToast(WareHousingActivity.this, result.getMsg());
                            if("没有数据".equals(result.getMsg())){
                                //TODO 没有返回订单详情的时候
                                isWenTiDan=true;
                                et_kehudaima.setClickable(false);
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
                        } else if (1 == result.getCode()) {
                            showWaiting(false);
                            handelOrderResult(result);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        Utils.showToast(getBaseActivity(), R.string.str_qqsb);
                    }
                });
    }

    //TODO 处理运单数据
    private void handelOrderResult(OrderModel result) {
        if (null != result && null != result.getData()) {
            isWenTiDan=false;
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
            orderModel = result;
            pice = Integer.parseInt(result.getData().getOrder_pieces());
            customerId = Integer.parseInt(orderModel.getData().getCustomer_id());
            if (!TextUtils.isEmpty(result.getData().getCustomer_id()) && !TextUtils.isEmpty(result.getData().getCountry_code())) {
                et_kehudaima.setText(result.getData().getCountry_code());
                selectCurrentDayBatch(result.getData().getCustomer_id(), result.getData().getCountry_code());
            }
            if (1 == Integer.parseInt(result.getData().getOrder_pieces())) {
                tv_jianshu.setText("1");
                tv_jianshu.setVisibility(View.VISIBLE);
                et_jianshu.setVisibility(View.GONE);
                ll_changkuangao.setVisibility(View.VISIBLE);
                ll_duojian.setBackgroundResource(R.drawable.pack_btn_clickbg);
                ll_duojian.setClickable(false);

            } else if (Integer.parseInt(result.getData().getOrder_pieces()) > 1) {
                tv_jianshu.setVisibility(View.GONE);
                et_jianshu.setVisibility(View.VISIBLE);
                ll_changkuangao.setVisibility(View.GONE);
                et_jianshu.setText(result.getData().getOrder_pieces());
                ll_duojian.setBackgroundResource(R.drawable.pack_btn_bg);
                ll_duojian.setClickable(true);
            }

        }else {
            //TODO 没有返回订单详情的时候
            isWenTiDan=true;
            et_kehudaima.setClickable(false);
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

    //TODO 入库时选择客户生成到货总单
    private void selectCurrentDayBatch(String customer_id, String customer_code) {
        showWaiting(true);
        mPresenter.selectCurrentDayBatch(customer_id, customer_code)
                .compose(this.<SelectCurrentDayBatchModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SelectCurrentDayBatchModel>() {
                    @Override
                    public void accept(SelectCurrentDayBatchModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            Utils.showToast(WareHousingActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            showWaiting(false);
                            if (null != result.getData()) {
                                if (null == selectCurrentDayBatchEntity) {
                                    selectCurrentDayBatchEntity = new SelectCurrentDayBatchModel.DataEntity();
                                }
                                selectCurrentDayBatchEntity = result.getData();
                                et_daohuozongdan.setText("到货总单号："+result.getData().getArrivalbatch_labelcode()+"\n"
                                +"到货时间："+result.getData().getArrival_date());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        Utils.showToast(getBaseActivity(), R.string.str_qqsb);
                    }
                });
    }

    //TODO 查询国家
    private void getCountry() {
        showWaiting(true);
        mPresenter.getCountry()
                .compose(this.<CountryModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CountryModel>() {
                    @Override
                    public void accept(CountryModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            Utils.showToast(WareHousingActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            showWaiting(false);
                            if (null != result.getData() && result.getData().size() > 0) {
                                countryList.clear();
                                countryList.addAll(result.getData());
                                final List<ListialogModel> showList = new ArrayList<>();
                                for (CountryModel.DataEntity model : result.getData()) {
                                    showList.add(new ListialogModel(model.getCountry_cnname(), false));
                                }
                                dialog = new ListDialog(WareHousingActivity.this, R.style.AlertDialogStyle)
                                        .creatDialog()
                                        .setTitle("请选择国家")
                                        .setListData(showList)
                                        .setCallBackListener(new ListDialog.CallBackListener() {
                                            @Override
                                            public void Result(int position, String name) {
                                                et_mudiguojia.setText(showList.get(position).getName());
                                                if (null == countryModel) {
                                                    countryModel = new CountryModel.DataEntity();
                                                }
                                                countryModel = countryList.get(position);
                                                dialog.dismiss();
                                            }
                                        });
                                dialog.show();
//                                SelectionPopForBottomView.SelectionPopForBottomView(WareHousingActivity.this, "请选择国家"
//                                        , showList, new SelectionPopForBottomView.ContentItemOnClickListener() {
//                                            @Override
//                                            public void ItemOclick(int position) {
//
//                                            }
//                                        });
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        Utils.showToast(getBaseActivity(), R.string.str_qqsb);
                    }
                });
    }

    //TODO 查询生效得销售产品
    private void getProduct() {
        showWaiting(true);
        mPresenter.getProduct()
                .compose(this.<ProductModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProductModel>() {
                    @Override
                    public void accept(ProductModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            Utils.showToast(WareHousingActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            showWaiting(false);
                            if (null != result.getData() && result.getData().size() > 0) {
                                productList.clear();
                                productList.addAll(result.getData());
                                final List<ListialogModel> showList = new ArrayList<>();
                                for (ProductModel.DataEntity model : result.getData()) {
                                    showList.add(new ListialogModel(model.getProduct_cnname(), false));
                                }
                                dialog = new ListDialog(WareHousingActivity.this, R.style.AlertDialogStyle)
                                        .creatDialog()
                                        .setTitle("请选择销售产品")
                                        .setListData(showList)
                                        .setCallBackListener(new ListDialog.CallBackListener() {
                                            @Override
                                            public void Result(int position, String name) {
                                                et_xiaoshouchanpin.setText(showList.get(position).getName());
                                                if (null == productModel) {
                                                    productModel = new ProductModel.DataEntity();
                                                }
                                                productModel = productList.get(position);
                                                dialog.dismiss();
                                            }
                                        });
                                dialog.show();
//                                SelectionPopForBottomView.SelectionPopForBottomView(WareHousingActivity.this, "请选择销售产品"
//                                        , showList, new SelectionPopForBottomView.ContentItemOnClickListener() {
//                                            @Override
//                                            public void ItemOclick(int position) {
//                                                et_mudiguojia.setText(showList.get(position));
//                                            }
//                                        });
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        Utils.showToast(getBaseActivity(), R.string.str_qqsb);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000: {
                    subnitList.clear();
                    subnitList = WareHouSingModel.getInstance().getSubnitList();
                    et_shizhong.setText("" + WareHouSingModel.getInstance().getTotalGrossWeight());
                    et_jianshu.setText("" + subnitList.size());
                    Log.i("多件返回后", "subnitList.size()=" + subnitList.size());
                }
                break;
                case 1001: {
                    serviceList.clear();
                    serviceList=WareHouSingModel.getInstance().getServiceModelList();
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

    private void showWaiting(boolean isShow) {
        if (null != mWaitingDialog) {
            mWaitingDialog.showDialog(isShow);
        }
    }
}
