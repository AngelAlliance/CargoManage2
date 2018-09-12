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
import android.view.WindowManager;
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
import com.ljs.examinegoods.model.ExamineGoodsModel;
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
import com.sz.ljs.common.utils.MediaPlayerUtils;
import com.sz.ljs.common.utils.MscManager;
import com.sz.ljs.common.utils.PrintManager;
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

public class ExamineGoodsActivity extends BaseActivity implements View.OnClickListener, ExamineGoodsContract.View {
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
    private AlertDialog alertDialog;
    private NoscrollListView listview;
    private InspectionItemAdapter inspectionAdapter;
    private List<DetectionByModel.DataBean> detectionList = new ArrayList<>();
    private Bitmap bitmaps;
    private String yunDanHao = "";
    private String pice = "";
    private RadioButton yes_jianshu;
    private LinearLayout ll_wentimiaoshu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让布局向上移来显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_examine_goods);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        PrintManager.getInstance().init(this);
        waitingDialog = new WaitingDialog(this);
        mPresenter = new ExamineGoodsPresenter(this);
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
        ll_wentimiaoshu = (LinearLayout) findViewById(R.id.ll_wentimiaoshu);
        bt_yes = (Button) findViewById(R.id.bt_yes);
        yes_jianshu = (RadioButton) findViewById(R.id.yes_jianshu);
        inspectionAdapter = new InspectionItemAdapter(this, detectionList);
        listview.setAdapter(inspectionAdapter);
        setListViewHeight(listview);
    }


    private void initData() {
        MediaPlayerUtils.setRingVolume(true, ExamineGoodsActivity.this);
        MscManager.getInstance().init(ExamineGoodsActivity.this, 0);
        adapter = new PhotoGridAdapter(this, photoList);
        gv_photo.setAdapter(adapter);
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
//                    MscManager.getInstance().speech("运单号扫描成功");
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
                            bitmaps = bitmap;
                            mPresenter.uploadFile(str);
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
//                        int code = mTts.startSpeaking(result, mTtsListener);
//                        Log.i("语音播报", "code=" + code);
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
            isWenTiJian = false;
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

    @Override
    public void onResult(int Id, final String result) {
        switch (Id) {
            case ExamineGoodsContract.REQUEST_FAIL_ID:
                MscManager.getInstance().speech(result);
                showTipeDialog(result);
                break;
            case ExamineGoodsContract.GETORDERBYNUMBER_SUCCESS:
                //TODO 根据订单号查询订单信息
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        yes_jianshu.setChecked(true);
                        isWenTiJian=false;
                        if (null != ExamineGoodsModel.getInstance().getOrderModel() && null != ExamineGoodsModel.getInstance().getOrderModel().getData()) {
                            if (null == orderModel) {
                                orderModel = new OrderModel();
                            }
                            orderModel = ExamineGoodsModel.getInstance().getOrderModel();
                            if (!TextUtils.isEmpty(ExamineGoodsModel.getInstance().getOrderModel().getData().getOrder_info())) {
                                tv_goods_type.setText(ExamineGoodsModel.getInstance().getOrderModel().getData().getOrder_info());
                                getDetectionBy(ExamineGoodsModel.getInstance().getOrderModel().getData().getOrder_info());
                            }
                            if (!TextUtils.isEmpty(ExamineGoodsModel.getInstance().getOrderModel().getData().getOrder_pieces())) {
                                tv_goods_jianshu.setText(ExamineGoodsModel.getInstance().getOrderModel().getData().getOrder_pieces());
                            }
                            ExamineGoodsModel.getInstance().setOrderModel(null);
                        } else {
                            showTipeDialog("暂无订单信息数据");
                        }
                    }
                });
                break;
            case ExamineGoodsContract.GET_ITEM_TYPE_SUCCESS:
                //TODO 查询所有得货物类型
                if (null != ExamineGoodsModel.getInstance().getItemTypeList() && ExamineGoodsModel.getInstance().getItemTypeList().size() > 0) {
                    typeList.clear();
                    typeList.addAll(ExamineGoodsModel.getInstance().getItemTypeList());
                    showList.clear();
                    for (ItemTypeModel.DataBean bean : ExamineGoodsModel.getInstance().getItemTypeList()) {
                        showList.add(new ListialogModel("", bean.getItem_cn_name(), bean.getItem_en_name(), false));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
                    });
                } else {
                    showTipeDialog("暂无数据");
                }
                break;
            case ExamineGoodsContract.GET_DETECTIONBY_SUCCESS:
                //TODO 根据货物类型差检查项
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != ExamineGoodsModel.getInstance().getDetectionList() && ExamineGoodsModel.getInstance().getDetectionList().size() > 0) {
                            for (DetectionByModel.DataBean model : ExamineGoodsModel.getInstance().getDetectionList()) {
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
                            ExamineGoodsModel.getInstance().getDetectionList().clear();
                        } else {
                            detectionList.clear();
                            inspectionAdapter.notifyDataSetChanged();
                            showTipeDialog("暂无货物类型检查项数据");
                        }
                    }
                });

                break;
            case ExamineGoodsContract.SAVE_DETECTIONORDER_SUCCESS:
                //TODO 添加问题件或者保存验货结果
                yunDanHao = et_yundanhao.getText().toString().trim();
                pice = et_jianshu.getText().toString().trim();
                MscManager.getInstance().speech("操作成功");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog(ExamineGoodsActivity.this)
                                .builder()
                                .setTitle(getResources().getString(R.string.str_alter))
                                .setMsg(result)
                                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dissmiss();
                                        //TODO 打印标签
                                        if (isWenTiJian) {

                                        } else {
                                            if (TextUtils.isEmpty(pice)) {
                                                print(1, yunDanHao);
                                            } else {
                                                print(Integer.parseInt(pice)
                                                        , yunDanHao);
                                            }
                                        }


                                    }
                                });
                        alertDialog.show();
                        clean();
                    }
                });
                break;
            case ExamineGoodsContract.UPLOAD_FILE_SUCCESS:
                //TODO 图片上传
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != ExamineGoodsModel.getInstance().getFileResultModel()) {
                            Imagelist.add(new ImageType(ExamineGoodsModel.getInstance().getFileResultModel().getData(), "A"));
                            photoList.add(bitmaps);
                            adapter.notifyDataSetChanged();
                            bitmaps = null;
                            ExamineGoodsModel.getInstance().setFileResultModel(null);
                        } else {

                        }
                    }
                });
                break;
        }
    }

    //TODO 问题描述和拍照布局是否显示(只有是问题件的时候才显示)
    private void showWenTiMiaoShu(boolean isShow){
        if(isShow){
            ll_wentimiaoshu.setVisibility(View.VISIBLE);
            gv_photo.setVisibility(View.VISIBLE);
            ll_photo.setVisibility(View.VISIBLE);
        }else {
            ll_wentimiaoshu.setVisibility(View.GONE);
            gv_photo.setVisibility(View.GONE);
            ll_photo.setVisibility(View.GONE);
        }
    }

    //TODO 打印标签
    private void print(int pices, String code) {
        List<String> str = new ArrayList<>();
        if (pices == 1) {
            str.add(code);
        } else {
            if (pices > 20) {
                MscManager.getInstance().speech("输入件数太多，无法打印");
                showTipeDialog("输入件数太多，无法打印");
                return;
            }
            for (int i = 1; i <= pices; i++) {
                str.add(code + "-00" + i);
            }
        }
        Log.i("验货这里打印标签", "标签数据:" + str);
        PrintManager.getInstance().creatOneDimensionalCode(str);
        pice = "";
        yunDanHao = "";
    }

    //TODO 提交问题件或者保存验货单
    private void saveDetecTionOrder() {
        //TODO 检查是否是问题件
        if (null != detectionList && detectionList.size() > 0) {
            for (DetectionByModel.DataBean model : detectionList) {
                if (true == model.isChaYi()) {
                    isWenTiJian = true;
                    break;
                }
            }
        }
        if (TextUtils.isEmpty(et_yundanhao.getText().toString().trim())) {
            showTipeDialog(getResources().getString(R.string.str_ydhbnwk));
            return;
        }
        if (true == isWenTiJian) {
            if (TextUtils.isEmpty(et_wenti.getText().toString().trim())) {
                showTipeDialog(getResources().getString(R.string.str_wtmsbnwk));
                return;
            }
            if (null == Imagelist || Imagelist.size() <= 0) {
                showTipeDialog(getResources().getString(R.string.str_zpbnwk));
                return;
            }
        } else {
            if (false == isYanHuo) {
                showTipeDialog(getResources().getString(R.string.str_qgxyyh));
                return;
            }
            if (TextUtils.isEmpty(et_jianshu.getText().toString().trim())) {
                showTipeDialog(getResources().getString(R.string.str_jsbnwk));
                return;
            }
        }
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
        mPresenter.saveDetecTionOrder(requestModel);
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
        iv_yiyanhuo.setImageResource(R.mipmap.fb_b);
        isWenTiJian = false;
        showList.clear();
        typeList.clear();
        orderModel = null;
        Imagelist.clear();
        photoList.clear();
        adapter.notifyDataSetChanged();
        detectionList.clear();
        adapter.notifyDataSetChanged();
        inspectionAdapter.notifyDataSetChanged();
    }

    //TODO 查询所有得货物类型
    private void getItemType() {
        mPresenter.getItemType();
    }

    //TODO 根据货物类型差检查项  detection_name:货物类型中文名称
    private void getDetectionBy(String detection_name) {
        mPresenter.getDetectionBy(detection_name);
    }


    //TODO 根据运单号请求运单数据
    private void getOrderByNumber() {
        mPresenter.getOrderByNumber(et_yundanhao.getText().toString().trim());
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

    public void showWaiting(boolean isShow) {
        if (null != waitingDialog) {
            waitingDialog.showDialog(isShow);
        }
    }

    private void showTipeDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog = new AlertDialog(ExamineGoodsActivity.this)
                        .builder()
                        .setTitle(getResources().getString(R.string.str_alter))
                        .setTitle(msg)
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
    protected void onDestroy() {
        super.onDestroy();
        PrintManager.getInstance().release();
    }
}
