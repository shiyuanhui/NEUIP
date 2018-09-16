package com.shiyuanhui.model;

public interface NetworkCallback {

    /**
     * 网络请求成功回调接口
     * @param success
     */
    void onSuccess(String success);

    /**
     * 网络请求失败回调接口
     * @param fail
     */
    void onFailure(String fail);
}
