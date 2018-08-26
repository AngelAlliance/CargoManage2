package com.sz.ljs.packgoods.presenter;

import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.packgoods.contract.PackgoodsContract;
import com.sz.ljs.packgoods.model.GsonDepltListModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackgoodsPresenter {
    private PackgoodsContract mContract;

    public PackgoodsPresenter() {
        mContract = new Retrofit.Builder()
                .baseUrl(GenApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(HDateGsonAdapter.createGson()))
                .build().create(PackgoodsContract.class);
    }

    public void release() {
        mContract = null;
    }

    //TODO 获取打包页面初始化数据 og_id:机构id   service_id:服务id   server_channelid:服务渠道id
    public Flowable<GsonDepltListModel> getDepltList(String og_id, String service_id, String server_channelid) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.OG_ID, og_id);
        param.put(PackgoodsContract.SERVRE_ID, service_id);
        param.put(PackgoodsContract.SERVRE_CHANNELID, server_channelid);
        return mContract.getDepltList(token, param);
    }
}
