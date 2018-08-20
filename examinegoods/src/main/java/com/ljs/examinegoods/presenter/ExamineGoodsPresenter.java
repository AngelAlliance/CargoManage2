package com.ljs.examinegoods.presenter;

import com.google.gson.Gson;
import com.ljs.examinegoods.contract.ExamineGoodsContract;
import com.ljs.examinegoods.model.DetectionByModel;
import com.ljs.examinegoods.model.ItemTypeModel;
import com.ljs.examinegoods.model.OrderModel;
import com.ljs.examinegoods.model.SaveDeteTionOrderRequestModel;
import com.ljs.examinegoods.model.SaveDetecTionOrderResultModel;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.ApiTimestampToken;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.common.utils.MD5Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liujs on 2018/8/20.
 */

public class ExamineGoodsPresenter {

    private ExamineGoodsContract mContract;

    public ExamineGoodsPresenter() {
        mContract = new Retrofit.Builder()
                .baseUrl(GenApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(HDateGsonAdapter.createGson()))
                .build().create(ExamineGoodsContract.class);
    }

    public void release() {
        mContract = null;
    }

    //TODO 根据订单号查询订单信息
    public Flowable<OrderModel> getOrderByNumber(String number) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(ExamineGoodsContract.NUMBER, number);
        param.put(ExamineGoodsContract.SUMMARY, ExamineGoodsContract.summary);
//        String json= new Gson().toJson(param);
//        RequestBody requestBody= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        return mContract.getOrderByNumber(token, param);
    }

    //TODO 查询所有得货物类型
    public Flowable<ItemTypeModel> getItemType() {
        Map<String, String> param = new HashMap<>();
        param.put(ExamineGoodsContract.SUMMARY, ExamineGoodsContract.summary);
        return mContract.getItemType(param);
    }

    //TODO 根据货物类型差检查项  detection_name:货物类型中文名称
    public Flowable<DetectionByModel> getDetectionBy(String detection_name) {
        Map<String, String> param = new HashMap<>();
        param.put(ExamineGoodsContract.SUMMARY, ExamineGoodsContract.summary);
        param.put(ExamineGoodsContract.DETECTIONNAME, detection_name);
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        return mContract.getDetectionBy(token,param);
    }

    //TODO 添加问题件或者保存验货结果
    public Flowable<SaveDetecTionOrderResultModel> saveDetecTionOrder(SaveDeteTionOrderRequestModel requestModel) {
        String json = "";
        if (null != requestModel) {
            json = new Gson().toJson(requestModel);
        } else {
            json = "";
        }
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return mContract.saveDetecTionOrder(requestBody);
    }
}
