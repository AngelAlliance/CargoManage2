package com.sz.ljs.packgoods.presenter;

import com.google.gson.Gson;
import com.sz.ljs.common.base.HDateGsonAdapter;
import com.sz.ljs.common.constant.GenApi;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.model.UserModel;
import com.sz.ljs.packgoods.contract.PackgoodsContract;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.packgoods.model.BagPutBusinessReqModel;
import com.sz.ljs.packgoods.model.GsonAddBussinessPackageModel;
import com.sz.ljs.packgoods.model.GsonServiceChannelModel;
import com.sz.ljs.packgoods.model.PackGoodsRequestBsListMode;

import java.util.HashMap;
import java.util.List;
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
        param.put(PackgoodsContract.USERID, ""+UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.OG_ID, og_id);
        param.put(PackgoodsContract.SERVRE_ID, service_id);
        param.put(PackgoodsContract.SERVRE_CHANNELID, server_channelid);
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        return mContract.getDepltList(token, param);
    }


    //TODO 把运单从某个包提出 listParamString:运单的集合 json字符串
    public Flowable<BaseResultModel> bagPutBusiness(List<BagPutBusinessReqModel> listParamString) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.LIST_PARAMSTRING, new Gson().toJson(listParamString));
        param.put(PackgoodsContract.USERID, ""+UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        return mContract.bagPutBusiness(token, param);
    }

    //TODO 称量包的重量  strBagCode:包号码   og_id:机构id  strlength:长  strwidth:宽  strheight:高  txtWeight:称重的重量  txtbag_grossweight:包重量
    public Flowable<BaseResultModel> bagWeighing(String strBagCode, String og_id, String strlength, String strwidth
            , String strheight, String txtWeight, String txtbag_grossweight) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.USERID, ""+UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        param.put(PackgoodsContract.STR_BAG_CODE, strBagCode);
        param.put(PackgoodsContract.OG_ID, og_id);
        param.put(PackgoodsContract.STR_LENGTH, strlength);
        param.put(PackgoodsContract.STR_WIDTH, strwidth);
        param.put(PackgoodsContract.STR_HEIGHT, strheight);
        param.put(PackgoodsContract.TEXT_WEIGHT, txtWeight);
        param.put(PackgoodsContract.TEXTBAG_GROSSWEIGHT, txtbag_grossweight);
        return mContract.bagWeighing(token, param);
    }

    //TODO 生效渠道
    public Flowable<GsonServiceChannelModel> getServiceChannel() {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.USERID, ""+UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        return mContract.getServiceChannel(token, param);
    }


    //TODO 运单打包   strExpressCode:包的号码 例如：PPNO-9908    og_id:机构id 登陆时返回  server_channelid:服务渠道   list:运单集合
    public Flowable<GsonAddBussinessPackageModel> addBussinessPackage(String strExpressCode, String og_id
            , String server_channelid, List<PackGoodsRequestBsListMode> list) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        param.put(PackgoodsContract.USERID, "" + UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.STR_EXPRESS_CODE, strExpressCode);
        param.put(PackgoodsContract.OG_ID, og_id);
        param.put(PackgoodsContract.SERVRE_CHANNELID, server_channelid);
        param.put(PackgoodsContract.BS_LIST, new Gson().toJson(list));
        return mContract.addBussinessPackage(token, param);
    }

    //TODO 拆包  bag_labelcode:包号码  og_id:机构id
    public Flowable<BaseResultModel> unpacking(String bag_labelcode, String og_id) {
        Map<String, String> param = new HashMap<>();
        String token = "";
        if (null != UserModel.getInstance() && null != UserModel.getInstance().getTokenModel()) {
            token = UserModel.getInstance().getTokenModel().getToken();
        } else {
            token = "";
        }
        param.put(PackgoodsContract.SUMMARY, PackgoodsContract.summary);
        param.put(PackgoodsContract.USERID, ""+UserModel.getInstance().getSt_id());
        param.put(PackgoodsContract.BAG_LABEL_CODE, bag_labelcode);
        param.put(PackgoodsContract.OG_ID, og_id);
        return mContract.unpacking(token, param);
    }
}
