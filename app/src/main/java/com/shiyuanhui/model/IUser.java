package com.shiyuanhui.model;

public interface IUser {


    void connectNetwork(String userName,String password,NetworkCallback callback);

    void disconnectMobile(String ipAddress,NetworkCallback callback);

    void disconnectAll(String userName,String password,NetworkCallback callback);

    void getAcount(NetworkCallback callback);
}
