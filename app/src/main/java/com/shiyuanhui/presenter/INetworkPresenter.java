package com.shiyuanhui.presenter;

import com.shiyuanhui.util.SharedPreferencesUtil;

public interface INetworkPresenter {

    void connctNetwork(SharedPreferencesUtil sharedPreferencesUtil);
    void disConnectMobile();
    void disConnectAll();
}
