package com.ljs.examinegoods.view;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ljs.examinegoods.R;
import com.ljs.examinegoods.adapter.PhotoGridAdapter;
import com.ljs.examinegoods.model.OrderModel;
import com.ljs.examinegoods.presenter.ExamineGoodsPresenter;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.common.model.ExpressPackageModel;
import com.sz.ljs.common.model.FourSidesSlidListTitileModel;
import com.sz.ljs.common.utils.Utils;
import com.sz.ljs.common.view.FourSidesSlidingListView;
import com.sz.ljs.common.view.NoscrollListView;
import com.sz.ljs.common.view.PhotosUtils;
import com.sz.ljs.common.view.ScanView;
import com.sz.ljs.common.view.adapter.FourSidesSlidListTitleAdapter;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liujs on 2018/8/13.
 * 验货界面
 */

public class ExamineGoodsActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_yundanhao, et_kehucankaodanhao, et_wenti, et_jianshu;
    private ImageView iv_scan, iv_yiyanhuo, iv_scan2;
    private TextView tv_goods_type, tv_goods_shifoudaidian, tv_goods_shifoudaici, tv_goods_shifoudaipai, tv_goods_jianshu, tv_goods_suihuofapiao, tv_goods_fapiaoziliao, tv_goods_baoguanziliao, tv_goods_dandubaoguan, tv_goods_huowusunhuai, tv_goods_baozhuangposun, tv_goods_weijinpin, tv_goods_yisuipin;
    private RadioGroup rg_shifoudaidian, rg_shifoudaici, rg_shifoudaipai, rg_jianshu, rg_suihuofapiao, rg_fapiaoziliao, rg_baoguanziliao, rg_dandubaoguan, rg_huowusunhuai, rg_baozhuangposun, rg_weijinpin, rg_yisuipin;
    private RadioButton yes_shifoudaidian, no_shifoudaidian, yes_shifoudaici, no_shifoudaici, yes_shifoudaipai, no_shifoudaipai, yes_jianshu, no_jianshu, yes_suihuofapiao, no_suihuofapiao, yes_fapiaoziliao, no_fapiaoziliao, yes_baoguanziliao, no_baoguanziliao, yes_dandubaoguan, no_dandubaoguan, yes_huowusunhuai, no_huowusunhuai, yes_baozhuangposun, no_baozhuangposun, yes_weijinpin, no_weijinpin, yes_yisuipin, no_yisuipin;
    private GridView gv_photo;
    private LinearLayout ll_photo;
    private Button bt_yes;
    private boolean isYanHuo = false;
    private List<Bitmap> photoList = new ArrayList<>();
    private PhotoGridAdapter adapter;
    private ExamineGoodsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_goods);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        mPresenter = new ExamineGoodsPresenter();
        et_yundanhao = (EditText) findViewById(R.id.et_yundanhao);
        et_kehucankaodanhao = (EditText) findViewById(R.id.et_kehucankaodanhao);
        et_wenti = (EditText) findViewById(R.id.et_wenti);
        et_jianshu = (EditText) findViewById(R.id.et_jianshu);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        iv_scan2 = (ImageView) findViewById(R.id.iv_scan2);
        iv_yiyanhuo = (ImageView) findViewById(R.id.iv_yiyanhuo);
        tv_goods_type = (TextView) findViewById(R.id.tv_goods_type);
        tv_goods_shifoudaidian = (TextView) findViewById(R.id.tv_goods_shifoudaidian);
        tv_goods_shifoudaici = (TextView) findViewById(R.id.tv_goods_shifoudaici);
        tv_goods_shifoudaipai = (TextView) findViewById(R.id.tv_goods_shifoudaipai);
        tv_goods_jianshu = (TextView) findViewById(R.id.tv_goods_jianshu);
        tv_goods_suihuofapiao = (TextView) findViewById(R.id.tv_goods_suihuofapiao);
        tv_goods_fapiaoziliao = (TextView) findViewById(R.id.tv_goods_fapiaoziliao);
        tv_goods_baoguanziliao = (TextView) findViewById(R.id.tv_goods_baoguanziliao);
        tv_goods_dandubaoguan = (TextView) findViewById(R.id.tv_goods_dandubaoguan);
        tv_goods_huowusunhuai = (TextView) findViewById(R.id.tv_goods_huowusunhuai);
        tv_goods_baozhuangposun = (TextView) findViewById(R.id.tv_goods_baozhuangposun);
        tv_goods_huowusunhuai = (TextView) findViewById(R.id.tv_goods_huowusunhuai);
        tv_goods_baozhuangposun = (TextView) findViewById(R.id.tv_goods_baozhuangposun);
        tv_goods_weijinpin = (TextView) findViewById(R.id.tv_goods_weijinpin);
        tv_goods_yisuipin = (TextView) findViewById(R.id.tv_goods_yisuipin);
        rg_shifoudaidian = (RadioGroup) findViewById(R.id.rg_shifoudaidian);
        rg_shifoudaici = (RadioGroup) findViewById(R.id.rg_shifoudaici);
        rg_shifoudaipai = (RadioGroup) findViewById(R.id.rg_shifoudaipai);
        rg_jianshu = (RadioGroup) findViewById(R.id.rg_jianshu);
        rg_suihuofapiao = (RadioGroup) findViewById(R.id.rg_suihuofapiao);
        rg_fapiaoziliao = (RadioGroup) findViewById(R.id.rg_fapiaoziliao);
        rg_baoguanziliao = (RadioGroup) findViewById(R.id.rg_baoguanziliao);
        rg_dandubaoguan = (RadioGroup) findViewById(R.id.rg_dandubaoguan);
        rg_huowusunhuai = (RadioGroup) findViewById(R.id.rg_huowusunhuai);
        rg_baozhuangposun = (RadioGroup) findViewById(R.id.rg_baozhuangposun);
        rg_weijinpin = (RadioGroup) findViewById(R.id.rg_weijinpin);
        rg_yisuipin = (RadioGroup) findViewById(R.id.rg_yisuipin);
        yes_shifoudaidian = (RadioButton) findViewById(R.id.yes_shifoudaidian);
        no_shifoudaidian = (RadioButton) findViewById(R.id.no_shifoudaidian);
        yes_shifoudaici = (RadioButton) findViewById(R.id.yes_shifoudaici);
        no_shifoudaici = (RadioButton) findViewById(R.id.no_shifoudaici);
        yes_shifoudaipai = (RadioButton) findViewById(R.id.yes_shifoudaipai);
        no_shifoudaipai = (RadioButton) findViewById(R.id.no_shifoudaipai);
        yes_jianshu = (RadioButton) findViewById(R.id.yes_jianshu);
        no_jianshu = (RadioButton) findViewById(R.id.no_jianshu);
        yes_suihuofapiao = (RadioButton) findViewById(R.id.yes_suihuofapiao);
        no_suihuofapiao = (RadioButton) findViewById(R.id.no_suihuofapiao);
        yes_fapiaoziliao = (RadioButton) findViewById(R.id.yes_fapiaoziliao);
        no_fapiaoziliao = (RadioButton) findViewById(R.id.no_fapiaoziliao);
        yes_baoguanziliao = (RadioButton) findViewById(R.id.yes_baoguanziliao);
        no_baoguanziliao = (RadioButton) findViewById(R.id.no_baoguanziliao);
        yes_dandubaoguan = (RadioButton) findViewById(R.id.yes_dandubaoguan);
        no_dandubaoguan = (RadioButton) findViewById(R.id.no_dandubaoguan);
        yes_huowusunhuai = (RadioButton) findViewById(R.id.yes_huowusunhuai);
        no_huowusunhuai = (RadioButton) findViewById(R.id.no_huowusunhuai);
        yes_baozhuangposun = (RadioButton) findViewById(R.id.yes_baozhuangposun);
        no_baozhuangposun = (RadioButton) findViewById(R.id.no_baozhuangposun);
        yes_weijinpin = (RadioButton) findViewById(R.id.yes_weijinpin);
        no_weijinpin = (RadioButton) findViewById(R.id.no_weijinpin);
        yes_yisuipin = (RadioButton) findViewById(R.id.yes_yisuipin);
        no_yisuipin = (RadioButton) findViewById(R.id.no_yisuipin);
        gv_photo = (GridView) findViewById(R.id.gv_photo);
        ll_photo = (LinearLayout) findViewById(R.id.ll_photo);
        bt_yes = (Button) findViewById(R.id.bt_yes);
    }


    private void initData() {
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
                if (s.length() > 8) {
                    //TODO 当运单号大于8位的时候就开始请求数据
                    getOrderByNumber();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rg_shifoudaidian.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_shifoudaidian) {

                } else if (checkedId == R.id.no_shifoudaidian) {

                }
            }
        });
        rg_shifoudaici.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_shifoudaici) {

                } else if (checkedId == R.id.no_shifoudaici) {

                }
            }
        });
        rg_shifoudaipai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_shifoudaipai) {

                } else if (checkedId == R.id.no_shifoudaipai) {

                }
            }
        });
        rg_jianshu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_jianshu) {

                } else if (checkedId == R.id.no_jianshu) {

                }
            }
        });
        rg_suihuofapiao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_suihuofapiao) {

                } else if (checkedId == R.id.no_suihuofapiao) {

                }
            }
        });
        rg_fapiaoziliao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_fapiaoziliao) {

                } else if (checkedId == R.id.no_fapiaoziliao) {

                }
            }
        });
        rg_baoguanziliao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_baoguanziliao) {

                } else if (checkedId == R.id.no_baoguanziliao) {

                }
            }
        });
        rg_dandubaoguan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_dandubaoguan) {

                } else if (checkedId == R.id.no_dandubaoguan) {

                }
            }
        });
        rg_huowusunhuai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_huowusunhuai) {

                } else if (checkedId == R.id.no_huowusunhuai) {

                }
            }
        });
        rg_baozhuangposun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_baozhuangposun) {

                } else if (checkedId == R.id.no_baozhuangposun) {

                }
            }
        });
        rg_weijinpin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_weijinpin) {

                } else if (checkedId == R.id.no_weijinpin) {

                }
            }
        });
        rg_yisuipin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_yisuipin) {

                } else if (checkedId == R.id.no_yisuipin) {

                }
            }
        });
        adapter.setDelOnclickCallBack(new PhotoGridAdapter.DelOnclickCallBack() {
            @Override
            public void CallBack(int position) {
                if (null != photoList && photoList.size() > 0) {
                    photoList.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        ll_photo.setOnClickListener(this);
        bt_yes.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        iv_scan2.setOnClickListener(this);
        iv_yiyanhuo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_photo) {
            //TODO 拍照
            PhotosUtils.selectUserPhotos(new PhotosUtils.IUserPhotosCallBack() {
                @Override
                public void onResult(Bitmap bitmap) {
                    if (null != bitmap) {
                        String str = PhotosUtils.bitmapToHexString(bitmap);
                        Log.i("图片转成16进制之后的字符串", "str=" + PhotosUtils.bitmapToHexString(bitmap));
                        photoList.add(bitmap);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        } else if (id == R.id.bt_yes) {
            //TODO 确认

        } else if (id == R.id.iv_scan) {
            //TODO 运单号扫描
            ScanView.ScanView(new ScanView.SacnCallBack() {
                @Override
                public void onResult(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        et_yundanhao.setText(result);
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

    //TODO 根据运单号请求运单数据
    private void getOrderByNumber() {

        mPresenter.getOrderByNumber(et_yundanhao.getText().toString().trim())
                .compose(this.<OrderModel>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderModel>() {
                    @Override
                    public void accept(OrderModel result) throws Exception {
                        if (0 == result.getCode()) {
                            Utils.showToast(ExamineGoodsActivity.this, result.getMsg());
                        } else if (1 == result.getCode()) {
                            handelOrderResult(result);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //获取失败，提示
                        Utils.showToast(getBaseActivity(), R.string.str_qqsb);
                    }
                });
    }

    //TODO 处理运单数据
    private void handelOrderResult(OrderModel result){

    }
}
