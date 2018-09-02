package com.sz.ljs.inventory.contract;

import com.sz.ljs.base.interfacecallback.IBaseView;
import com.sz.ljs.inventory.model.BsListModel;

import java.util.List;

/**
 * Author: Mr. Duan
 * Date: 2018/8/29
 * Description:请求调用接口
 */

public interface InventoryContract {
    public static final int REQUEST_FAIL_ID = -1;//网络失败，网络请求失败
    public static final int REQUEST_SUCCESS_ID = 1;//网络请求成功
    public static final int ADD_EXPRESSS_TRACK_SUCCESS = 2;//批量提交盘库成功

    interface View extends IBaseView {

    }

    interface Presenter {
        public void getFindExpressByID(String number);

        public void addExpressTrack(List<BsListModel> list);
    }
}
