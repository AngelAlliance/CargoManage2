package com.sz.ljs.packgoods.contract;

import com.sz.ljs.base.interfacecallback.IGenView;
import com.sz.ljs.common.model.BaseResultModel;
import com.sz.ljs.common.utils.MD5Util;
import com.sz.ljs.common.model.GsonDepltListModel;
import com.sz.ljs.packgoods.model.BagPutBusinessReqModel;
import com.sz.ljs.packgoods.model.GsonAddBussinessPackageModel;
import com.sz.ljs.packgoods.model.GsonServiceChannelModel;
import com.sz.ljs.packgoods.model.PackGoodsRequestBsListMode;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PackgoodsContract {
    public static final String summary = MD5Util.get32MD5Str("zhbg_ips2018_cn");
    public static final String SUMMARY = "summary";
    public static final String OG_ID = "og_id";
    public static final String SERVRE_ID = "service_id";
    public static final String SERVRE_CHANNELID = "server_channelid";
    public static final String STR_BAG_CODE = "strBagCode";
    public static final String STR_LENGTH = "strlength";
    public static final String STR_WIDTH = "strwidth";
    public static final String STR_HEIGHT = "strheight";
    public static final String TEXT_WEIGHT = "txtWeight";
    public static final String TEXTBAG_GROSSWEIGHT = "txtbag_grossweight";
    public static final String LIST_PARAMSTRING = "listParamString";
    public static final String USERID = "userId";
    public static final String STR_EXPRESS_CODE = "strExpressCode";
    public static final String BS_LIST = "bs_list";
    public static final String BAG_LABEL_CODE = "bag_labelcode";

    public static final int REQUEST_FAIL_ID = -1;//网络失败，网络请求失败
    public static final int REQUEST_SUCCESS_ID = 1;//网络请求成功
    public static final int BAG_PUTBUSINESS_SUCCESS = 2;//把运单从某个包提出
    public static final int BAG_WEIGHING_SUCCESS = 3;//称量包的重量
    public static final int GET_SERVICECHANNEL_SUCCESS = 4;//生效渠道
    public static final int ADD_BUSSINESSPACKAGE_SUCCESS = 5;//运单打包
    public static final int UNPACKING_SUCCESS = 6;//拆包

    interface View extends IGenView {

    }

    interface Presenter {
        //TODO 获取打包页面初始化数据 og_id:机构id   service_id:服务id   server_channelid:服务渠道id
        public void getDepltList(String og_id, String service_id, String server_channelid);

        //TODO 把运单从某个包提出 listParamString:运单的集合 json字符串
        public void bagPutBusiness(List<BagPutBusinessReqModel> listParamString);

        //TODO 称量包的重量  strBagCode:包号码   og_id:机构id  strlength:长  strwidth:宽  strheight:高  txtWeight:称重的重量  txtbag_grossweight:包重量
        public void bagWeighing(String strBagCode, String og_id, String strlength, String strwidth
                , String strheight, String txtWeight, String txtbag_grossweight);

        //TODO 生效渠道
        public void getServiceChannel();

        //TODO 运单打包   strExpressCode:包的号码 例如：PPNO-9908    og_id:机构id 登陆时返回  server_channelid:服务渠道   list:运单集合
        public void addBussinessPackage(String strExpressCode, String og_id
                , String server_channelid, List<PackGoodsRequestBsListMode> list);

        //TODO 拆包  bag_labelcode:包号码  og_id:机构id
        public void unpacking(String bag_labelcode, String og_id);
    }

    @POST("user/GetDepltList")
    @FormUrlEncoded
    Flowable<GsonDepltListModel> getDepltList(@Header("token") String token, @FieldMap Map<String, String> param);




    @POST("user/BagPutBusiness")
    @FormUrlEncoded
    Flowable<BaseResultModel> bagPutBusiness(@Header("token") String token, @FieldMap Map<String, String> param);


    @POST("user/BagWeighing")
    @FormUrlEncoded
    Flowable<BaseResultModel> bagWeighing(@Header("token") String token, @FieldMap Map<String, String> param);

    @POST("user/GetServiceChannel")
    @FormUrlEncoded
    Flowable<GsonServiceChannelModel> getServiceChannel(@Header("token") String token, @FieldMap Map<String, String> param);



    @POST("user/AddBussinessPackage")
    @FormUrlEncoded
    Flowable<GsonAddBussinessPackageModel> addBussinessPackage(@Header("token") String token, @FieldMap Map<String, String> param);




    @POST("user/Unpacking")
    @FormUrlEncoded
    Flowable<BaseResultModel> unpacking(@Header("token") String token, @FieldMap Map<String, String> param);


}
