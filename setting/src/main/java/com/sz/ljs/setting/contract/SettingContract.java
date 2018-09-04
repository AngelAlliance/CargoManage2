package com.sz.ljs.setting.contract;

import com.sz.ljs.base.interfacecallback.IGenView;

public interface SettingContract {
    public static final int REQUEST_FAIL_ID = -1;//网络失败，网络请求失败
    public static final int REQUEST_SUCCESS_ID = 1;//网络请求成功
    public static final String USERID = "userId";

    interface View extends IGenView {

    }

    interface Presenter {
        public void updatePassword(String old_password,String confim_password,String new_password);
    }

}
