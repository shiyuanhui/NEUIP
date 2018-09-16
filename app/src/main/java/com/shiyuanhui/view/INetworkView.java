package com.shiyuanhui.view;

//主页上网络请求相关的View
public interface INetworkView extends IBaseView{
    /**
     * 显示网络请求的结果
     * @param message
     */
    void showMessage(String message);
}
