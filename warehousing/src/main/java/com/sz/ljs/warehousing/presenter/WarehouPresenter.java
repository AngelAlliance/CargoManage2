package com.sz.ljs.warehousing.presenter;

import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.warehousing.contract.WarehouContract;
import com.sz.ljs.warehousing.model.GsonIncidentalModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 入库请求接口处理类
 */
public class WarehouPresenter {

    private WarehouContract warehouContract;

    public WarehouPresenter(){
        warehouContract = new Retrofit.Builder()
                .baseUrl(GenApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(HDateGsonAdapter.createGson()))
                .build().create(WarehouContract.class);
    }

    public Flowable<GsonIncidentalModel> getIncidental(){
        Map<String, String> param = new HashMap<>();
        param.put(WarehouContract.SUMMARY, MD5Util.get32MD5Str(WarehouContract.summary));
        return warehouContract.getIncidental(param);
    }

    public void release(){
        warehouContract = null;
    }
}
