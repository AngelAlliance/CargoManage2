package com.sz.ljs.inventory.presenter;

import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.inventory.contract.InventoryContract;
import com.sz.ljs.inventory.model.ResultBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: Mr. Duan
 * Date: 2018/8/29
 * Description:盘库 P
 */

public class InventoryPresenter {
    private InventoryContract mContract;

    public InventoryPresenter() {
        mContract= new Retrofit.Builder()
                .baseUrl(GenApi.URL)
//                .client()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(HDateGsonAdapter.createGson()))
                .build().create(InventoryContract.class);
    }

    //TODO 根据订单号查询订单信息
    public Flowable<ResultBean> getFindExpressByID(String number) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(InventoryContract.NUMBER, number);
        param.put(InventoryContract.SUMMARY, InventoryContract.summary);
//        String json= new Gson().toJson(param);
//        RequestBody requestBody= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        return mContract.getFindExpressByID(token, param);
    }

}
