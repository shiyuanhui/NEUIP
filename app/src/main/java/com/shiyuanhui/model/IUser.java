package com.shiyuanhui.model;

import com.shiyuanhui.util.SharedPreferencesUtil;

public interface IUser {


    void connectNetwork(String userName, String password, SharedPreferencesUtil sharedPreferencesUtil,NetworkCallback callback);

    void disconnectMobile(String ipAddress,NetworkCallback callback);

    void disconnectAll(String userName,String password,NetworkCallback callback);

    void getAcount(SharedPreferencesUtil sharedPreferencesUtil, NetworkCallback callback);
}
