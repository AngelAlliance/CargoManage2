package com.ljs.examinegoods.view;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.ljs.examinegoods.R;
import com.ljs.examinegoods.adapter.InspectionItemAdapter;
import com.ljs.examinegoods.adapter.PhotoGridAdapter;
import com.ljs.examinegoods.contract.ExamineGoodsContract;
import com.ljs.examinegoods.model.DetectionByModel;
import com.ljs.examinegoods.model.ImageType;
import com.ljs.examinegoods.model.ItemTypeModel;
import com.qiloo.ble.AndBleScanner;
import com.qiloo.ble.bean.ScanInfo;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.ListialogModel;
import com.sz.ljs.common.model.OrderModel;
import com.ljs.examinegoods.model.SaveDeteTionOrderRequestModel;
import com.ljs.examinegoods.model.SaveDetecTionOrderResultModel;
import com.ljs.examinegoods.model.UploadFileResultModel;
import com.ljs.examinegoods.presenter.ExamineGoodsPresenter;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.BluetoothManager;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.AlertDialog;
import com.sz.ljs.common.view.ListDialog;
import com.sz.ljs.common.view.NoscrollListView;
import com.sz.ljs.common.view.PhotosUtils;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.common.view.SelectionPopForBottomView;
import com.sz.ljs.common.view.WaitingDialog;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liujs on 2018/8/13.
 * 验货界面
 */

public class ExamineGoodsActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "ExamineGoodsActivity";
    private EditText et_yundanhao, et_kehucankaodanhao, et_wenti, et_jianshu;
    private ImageView iv_scan, iv_yiyanhuo, iv_scan2;
    private TextView tv_goods_type, tv_goods_jianshu;
    private RadioGroup rg_jianshu;
    private GridView gv_photo;
    private LinearLayout ll_photo, ll_jianshu;
    private Button bt_yes;
    private boolean isYanHuo = false, isWenTiJian = false, isWenTiJian2 = false;
    private List<Bitmap> photoList = new ArrayList<>();
    private PhotoGridAdapter adapter;
    private ExamineGoodsPresenter mPresenter;
    private List<ListialogModel> showList = new ArrayList<>();
    private List<ItemTypeModel.DataBean> typeList = new ArrayList<>();
    private WaitingDialog waitingDialog;
    private OrderModel orderModel;
    private List<ImageType> Imagelist = new ArrayList<>();
    private ListDialog dialog;
    private BluetoothManager bluetoothManager;
    private AlertDialog alertDialog;
    private NoscrollListView listview;
    private InspectionItemAdapter inspectionAdapter;
    private List<DetectionByModel.DataBean> detectionList = new ArrayList<>();
    private Handler handl;
    //蓝牙搜索
    private AndBleScanner mScanner;
    private List<ScanInfo> bleList = new ArrayList<ScanInfo>();
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 默认发音人
    private String voicer = "vixy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_goods);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        mScanner = new AndBleScanner.Builder()
                .setScanTimeOut(30)
                .build();
        waitingDialog = new WaitingDialog(this);
        mPresenter = new ExamineGoodsPresenter();
        listview = (NoscrollListView) findViewById(R.id.lv_listView);
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        et_kehucankaodanhao = (EditText) findViewById(R.id.et_kehucankaodanhao);
        rg_jianshu = (RadioGroup) findViewById(R.id.rg_jianshu);
        ll_jianshu = (LinearLayout) findViewById(R.id.ll_jianshu);
        tv_goods_jianshu = (TextView) findViewById(R.id.tv_goods_jianshu);
        et_wenti = (EditText) findViewById(R.id.et_wenti);
        et_jianshu = (EditText) findViewById(R.id.et_jianshu);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        iv_scan2 = (ImageView) findViewById(R.id.iv_scan2);
        iv_yiyanhuo = (ImageView) findViewById(R.id.iv_yiyanhuo);
        tv_goods_type = (TextView) findViewById(R.id.tv_goods_type);
        gv_photo = (GridView) findViewById(R.id.gv_photo);
        ll_photo = (LinearLayout) findViewById(R.id.ll_photo);
        bt_yes = (Button) findViewById(R.id.bt_yes);
        inspectionAdapter = new InspectionItemAdapter(this, detectionList);
        listview.setAdapter(inspectionAdapter);
        setListViewHeight(listview);
    }


    private void initData() {
        adapter = new PhotoGridAdapter(this, photoList);
        gv_photo.setAdapter(adapter);
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(this, new InitListener() {
            @Override
            public void onInit(int i) {
                if (i != ErrorCode.SUCCESS) {
                    Utils.showToast(getBaseActivity(), "初始化失败,错误码：" + i);
                } else {
                    // 初始化成功，之后可以调用startSpeaking方法
                    // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                    // 正确的做法是将onCreate中的startSpeaking调用移至这里
                }
            }
        });

        // 设置参数
        setParam();
    }


    private void setListener() {
        et_yundanhao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= GenApi.ScanNumberLeng) {
                    //TODO 当运单号大于8位的时候就开始请求数据
                    int code = mTts.startSpeaking(s.toString(), mTtsListener);
                        Log.i("语音播报", "code=" + code);
                    getOrderByNumber();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rg_jianshu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.no_jianshu) {
                    //TODO 勾选了否，则表示件数与运单返回件数不符,为问题件
                    isWenTiJian = true;
                    ll_jianshu.setBackgroundResource(R.drawable.login_edittext2_bg);
                } else if (checkedId == R.id.yes_jianshu) {
                    isWenTiJian = false;
                    ll_jianshu.setBackgroundResource(R.drawable.login_edittext_bg);
                }
            }
        });
        inspectionAdapter.setCallBack(new InspectionItemAdapter.SelsectRadioButtonCallBack() {
            @Override
            public void callBack(int position, String select) {
                if (null != detectionList && detectionList.size() > 0) {
//                    Log.i("接收回来的","position="+position);
                    String value = detectionList.get(position).getValue();
                    detectionList.get(position).setSelect_value(select);
                    if ("D".equals(detectionList.get(position).getValue())) {
                        //TODO 不做检查
                        detectionList.get(position).setChaYi(false);
                    } else {
                        if (value.equals(select)) {
                            detectionList.get(position).setChaYi(false);
                        } else {
                            detectionList.get(position).setChaYi(true);
                        }
                    }
                    inspectionAdapter.notifyDataSetChanged();
                }
            }
        });
        adapter.setDelOnclickCallBack(new PhotoGridAdapter.DelOnclickCallBack() {
            @Override
            public void CallBack(int position) {
                if (null != photoList && photoList.size() > 0) {
                    photoList.remove(position);
                    Imagelist.get(position).setImage_type("D");
                    adapter.notifyDataSetChanged();
                }
            }
        });
        ll_photo.setOnClickListener(this);
        bt_yes.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        iv_scan2.setOnClickListener(this);
        iv_yiyanhuo.setOnClickListener(this);
//        tv_goods_type.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_photo) {
            if (photoList.size() >= 3) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog(ExamineGoodsActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg(getResources().getString(R.string.str_zpzds))
                                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dissmiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });
//                Utils.showToast(getBaseActivity(), R.string.str_zpzds);
                return;
            }
            //TODO 拍照
            PhotosUtils.selectUserPhotos(new PhotosUtils.IUserPhotosCallBack() {
                @Override
                public void onResult(Bitmap bitmap) {
                    if (null != bitmap) {
                        String str = PhotosUtils.bitmapToHexString(bitmap);
                        Log.i("图片转成16进制之后的字符串", "str=" + PhotosUtils.bitmapToHexString(bitmap));
                        if (!TextUtils.isEmpty(str)) {
                            uploadFile(bitmap, str);
                        }
                    }
                }
            });
        } else if (id == R.id.bt_yes) {
            //TODO 确认
            saveDetecTionOrder();
        } else if (id == R.id.iv_scan) {
            //TODO 运单号扫描
            ScanView.ScanView(new ScanView.SacnCallBack() {
                @Override
                public void onResult(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        et_yundanhao.setText(result);
                        int code = mTts.startSpeaking(result, mTtsListener);
                        Log.i("语音播报", "code=" + code);
                    }
                }
            });
        } else if (id == R.id.iv_scan2) {
            //TODO 客户参考单号扫描
            ScanView.ScanView(new ScanView.SacnCallBack() {
                @Override
                public void onResult(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        et_kehucankaodanhao.setText(result);
                    }
                }
            });
        } else if (id == R.id.iv_yiyanhuo) {
            isWenTiJian=false;
            if (null != detectionList && detectionList.size() > 0) {
                for (DetectionByModel.DataBean model : detectionList) {
                    if (true == model.isChaYi()) {
                        isWenTiJian = true;
                        break;
                    }
                }
            }
            if (true == isWenTiJian) {
                //TODO 表示为问题件，不给点击已验货按钮
                return;
            }
            //TODO 已验货按钮
            if (false == isYanHuo) {
                isYanHuo = true;
                iv_yiyanhuo.setImageResource(R.mipmap.fb_g);
            } else {
                isYanHuo = false;
                iv_yiyanhuo.setImageResource(R.mipmap.fb_b);
            }
        }
    }

    //TODO 图片上传
    private void uploadFile(final Bitmap bitmap, String str) {
        showWaiting(true);
        mPresenter.uploadFile(str)
                .compose(this.<UploadFileResultModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UploadFileResultModel>() {
                    @Override
                    public void accept(final UploadFileResultModel result) throws Exception {
                        if (1 == result.getCode()) {
                            showWaiting(false);
                            Imagelist.add(new ImageType(result.getData(), "A"));
                            photoList.add(bitmap);
                            adapter.notifyDataSetChanged();
                        } else {
                            showWaiting(false);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog = new AlertDialog(ExamineGoodsActivity.this)
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
                            });
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        showWaiting(false);
                        //获取失败，提示
                        showWaiting(false);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog = new AlertDialog(ExamineGoodsActivity.this)
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
                });
    }

    //TODO 提交问题件或者保存验货单
    private void saveDetecTionOrder() {
        //TODO 检查是否是问题件
        isWenTiJian=false;
        if (null != detectionList && detectionList.size() > 0) {
            for (DetectionByModel.DataBean model : detectionList) {
                if (true == model.isChaYi()) {
                    isWenTiJian = true;
                    break;
                }
            }
        }
        if (TextUtils.isEmpty(et_yundanhao.getText().toString().trim())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialog = new AlertDialog(ExamineGoodsActivity.this)
                            .builder()
                            .setTitle(getResources().getString(R.string.str_alter))
                            .setMsg(getResources().getString(R.string.str_ydhbnwk))
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
//        if (TextUtils.isEmpty(et_kehucankaodanhao.getText().toString().trim())) {
//            Utils.showToast(getBaseActivity(), getResources().getString(R.string.str_khckdhbnwk));
//            return;
//        }
        if (true == isWenTiJian) {
            if (TextUtils.isEmpty(et_wenti.getText().toString().trim())) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog(ExamineGoodsActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg(getResources().getString(R.string.str_wtmsbnwk))
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
            if (null == Imagelist || Imagelist.size() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog(ExamineGoodsActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg(getResources().getString(R.string.str_zpbnwk))
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
        } else {
            if (false == isYanHuo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog(ExamineGoodsActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg(getResources().getString(R.string.str_qgxyyh))
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
            if (TextUtils.isEmpty(et_jianshu.getText().toString().trim())) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog(ExamineGoodsActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg(getResources().getString(R.string.str_jsbnwk))
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
        }
        showWaiting(true);
        SaveDeteTionOrderRequestModel requestModel = new SaveDeteTionOrderRequestModel();
        requestModel.setNumber(et_yundanhao.getText().toString().trim());
        requestModel.setReference_number(et_kehucankaodanhao.getText().toString());
        if (true == isWenTiJian) {
            //TODO 问题件，需要上传问题描述与图片集合，不需要上传检验结果
            requestModel.setRequest_type("Y");
            if (null != Imagelist && Imagelist.size() > 0) {
                requestModel.setImage_url(Imagelist);
            }
            requestModel.setQuest_note(et_wenti.getText().toString().trim());
        } else {
            //TODO 不是问题件，不需要上传问题描述与图片集合，需要上传检验结果
            requestModel.setRequest_type("N");
            String detection_note = "";
            if (null != detectionList && detectionList.size() > 0) {
                for (DetectionByModel.DataBean model : detectionList) {
                    detection_note = detection_note + model.getDetection_cn_name() + ":" + model.getSelect_value() + ",";
                }
            }
            detection_note = detection_note + getResources().getString(R.string.str_zjs) + et_jianshu.getText().toString().trim();
            requestModel.setDetection_note(detection_note);
        }
        if (null != orderModel && null != orderModel.getData() && !TextUtils.isEmpty(orderModel.getData().getOrder_id())) {
            requestModel.setOrder_id(orderModel.getData().getOrder_id());
        }
        if (null != UserModel.getInstance()) {
            requestModel.setUserId(String.valueOf(UserModel.getInstance().getSt_id()));
        }
        requestModel.setSummary(ExamineGoodsContract.summary);
        mPresenter.saveDetecTionOrder(requestModel)
                .compose(this.<SaveDetecTionOrderResultModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SaveDetecTionOrderResultModel>() {

                    @Override
                    public void accept(final SaveDetecTionOrderResultModel result) throws Exception {
                        if (1 == result.getCode()) {
                            showWaiting(false);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog = new AlertDialog(ExamineGoodsActivity.this)
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
                            });
                            clean();
                        } else {
                            showWaiting(false);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog = new AlertDialog(ExamineGoodsActivity.this)
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
                            });
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog = new AlertDialog(ExamineGoodsActivity.this)
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
                });
    }

    //TODO 清空界面数据
    private void clean() {
        et_yundanhao.setText("");
        et_kehucankaodanhao.setText("");
        et_wenti.setText("");
        et_jianshu.setText("");
        tv_goods_type.setText("");
        tv_goods_jianshu.setText("");
        ll_jianshu.setBackgroundResource(R.drawable.login_edittext_bg);
        isYanHuo = false;
        isWenTiJian = false;
        showList.clear();
        typeList.clear();
        orderModel = null;
        Imagelist.clear();
        detectionList.clear();
        adapter.notifyDataSetChanged();
        inspectionAdapter.notifyDataSetChanged();
    }

    //TODO 查询所有得货物类型
    private void getItemType() {
        showWaiting(true);
        mPresenter.getItemType()
                .compose(this.<ItemTypeModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ItemTypeModel>() {
                    @Override
                    public void accept(ItemTypeModel result) throws Exception {
                        if (0 == result.getCode()) {
                            showWaiting(false);
                            Utils.showToast(ExamineGoodsActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            showWaiting(false);
                            if (null != result && null != result.getData() && result.getData().size() > 0) {
                                typeList.clear();
                                typeList.addAll(result.getData());
                                showList.clear();
                                for (ItemTypeModel.DataBean bean : result.getData()) {
                                    showList.add(new ListialogModel("", bean.getItem_cn_name(), bean.getItem_en_name(), false));
                                }
                                dialog = new ListDialog(ExamineGoodsActivity.this)
                                        .creatDialog()
                                        .setTitle("请选择货物类型")
                                        .setListData(showList)
                                        .setCallBackListener(new ListDialog.CallBackListener() {
                                            @Override
                                            public void Result(int position, String name) {

                                            }
                                        });
                                dialog.show();

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

    //TODO 根据货物类型差检查项  detection_name:货物类型中文名称
    private void getDetectionBy(String detection_name) {
        showWaiting(true);
        mPresenter.getDetectionBy(detection_name)
                .compose(this.<DetectionByModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetectionByModel>() {
                    @Override
                    public void accept(final DetectionByModel result) throws Exception {
                        if (1 == result.getCode()) {
                            showWaiting(false);
                            if (null != result.getData() && result.getData().size() > 0) {
                                handelDetectionResult(result);
                            }
                        } else {
                            showWaiting(false);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog = new AlertDialog(ExamineGoodsActivity.this)
                                            .builder()
                                            .setTitle(getResources().getString(R.string.str_alter))
                                            .setMsg(result.getMsg())
                                            .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    detectionList.clear();
                                                    inspectionAdapter.notifyDataSetChanged();
                                                    alertDialog.dissmiss();
                                                }
                                            });
                                    alertDialog.show();
                                }
                            });
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showWaiting(false);
                        //获取失败，提示
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog = new AlertDialog(ExamineGoodsActivity.this)
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
                });
    }

    private void handelDetectionResult(DetectionByModel result) {
        if (null != result.getData() && result.getData().size() > 0) {
            for (DetectionByModel.DataBean model : result.getData()) {
                model.setSelect_value(model.getDefult_value());
                if ("D".equals(model.getValue()) || model.getDefult_value().equals(model.getValue())) {
                    //TODO 如果相同，则表示没有差异
                    model.setChaYi(false);
                } else {
                    model.setChaYi(true);
                }
                detectionList.add(model);
            }
            inspectionAdapter.notifyDataSetChanged();
        }

    }

    //TODO 根据运单号请求运单数据
    private void getOrderByNumber() {

        mPresenter.getOrderByNumber(et_yundanhao.getText().toString().trim())
                .compose(this.<OrderModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderModel>() {
                    @Override
                    public void accept(final OrderModel result) throws Exception {
                        if (1 == result.getCode()) {
                            handelOrderResult(result);
                        } else {
                            showWaiting(false);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog = new AlertDialog(ExamineGoodsActivity.this)
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
                            });
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //获取失败，提示
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog = new AlertDialog(ExamineGoodsActivity.this)
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
                });
    }

    //TODO 处理运单数据
    private void handelOrderResult(OrderModel result) {
        if (null != result && null != result.getData()) {
            if (null == orderModel) {
                orderModel = new OrderModel();
            }
            orderModel = result;
            if (!TextUtils.isEmpty(result.getData().getOrder_info())) {
                tv_goods_type.setText(result.getData().getOrder_info());
                getDetectionBy(result.getData().getOrder_info());
            }
            if (!TextUtils.isEmpty(result.getData().getOrder_pieces())) {
                tv_goods_jianshu.setText(result.getData().getOrder_pieces());
            }

        }
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

    //TODO 打印
    private void Printing() {
        bluetoothManager = new BluetoothManager(ExamineGoodsActivity.this)
                .setStateChangCallBack(new BluetoothManager.IBluetoothStateChangCallBack() {
                    @Override
                    public void onStateChang(int state) {
                        switch (state) {
                            case BluetoothAdapter.STATE_ON: {
                                Scan();
                            }
                            break;
                            case BluetoothAdapter.STATE_OFF: {
                                stopScan();
                            }
                            break;
                        }
                    }
                });
        if (!BluetoothManager.BluetoothState()) { //表示未开启
            alertDialog = new AlertDialog(ExamineGoodsActivity.this).builder()
                    .setMsg(getString(R.string.str_bluetooth_is_close))
                    .setPositiveButton(getString(R.string.str_yes), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BluetoothManager.openBluetooth();
                            alertDialog.dissmiss();
                        }
                    })
                    .setNegativeButton(getString(R.string.str_cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dissmiss();
                        }
                    });
            alertDialog.show();
        } else {
            Scan();
        }
    }

    //TODO 蓝牙搜索
    private void Scan() {
        mScanner.searchBle()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ScanInfo>() {

                    @Override
                    public void accept(ScanInfo scanInfo) throws Exception {
                        String tmp = "Addr=" + scanInfo.getScanDevice().getAddress()
                                + " Rssi=" + scanInfo.getRssi()
                                + " UUID=" + scanInfo.getAdvData().getAdvData()
                                + " LocalName=" + scanInfo.getAdvData().getLocalName();
                        Log.i("设备信息", "tmp=" + tmp);
                        for (final ScanInfo mdevice : bleList) { // 将设备储存进扫描列表
                            if (mdevice.getScanDevice().getAddress().equals(scanInfo.getScanDevice().getAddress())) {
                                handl.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            bleList.remove(mdevice);
                                        } catch (Exception e) {
                                            Log.e(TAG
                                                    , e.toString());
                                        }
                                    }
                                });
                            }
                        }
                        bleList.add(scanInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {//出错的信息
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {//扫描完成的
                        handl.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Scan();
                                } catch (Exception e) {

                                    Log.e(TAG
                                            , e.toString());
                                }
                            }
                        });
                    }
                });
    }

    //TODO 停止搜索
    private void stopScan() {
        mScanner.stopScan();
    }

    private void showWaiting(boolean isShow) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(isShow);
        }
    }

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            //onevent回调接口实时返回音频流数据
            //mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "100");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.pcm");
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度

            if (!"henry".equals(voicer) || !"xiaoyan".equals(voicer) ||
                    !"xiaoyu".equals(voicer) || !"catherine".equals(voicer))
                endPos++;
            Log.e(TAG, "beginPos = " + beginPos + "  endPos = " + endPos);
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                showTip("播放完成");
            } else if (error != null) {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            Log.e(TAG, "TTS Demo onEvent >>>" + eventType);
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                Log.d(TAG, "session id =" + sid);
            }
        }
    };

    private void showTip(final String str) {
        Utils.showToast(getBaseActivity(), str);
    }
}
