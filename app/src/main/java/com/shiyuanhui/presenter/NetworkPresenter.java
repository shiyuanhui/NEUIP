package com.shiyuanhui.presenter;

import com.shiyuanhui.model.NetworkCallback;
import com.shiyuanhui.model.User;
import com.shiyuanhui.util.SharedPreferencesUtil;
import com.shiyuanhui.view.INetworkView;

import java.util.Map;

public class NetworkPresenter extends BasePresenter<INetworkView> implements INetworkPresenter{
    private User user;
    private SharedPreferencesUtil preferencesUtil;
    private String username;
    private String password;
    private String ipAddress;

    public NetworkPresenter(User user,SharedPreferencesUtil preferencesUtil) {
        this.user = user;
        this.preferencesUtil = preferencesUtil;
        Init();
    }

    private void Init()
    {
        Map<String, String> map = preferencesUtil.getUsercount();
        username = (String) map.get("username");
        password = (String) map.get("password");
        ipAddress = preferencesUtil.getIPAddress();
    }

    @Override
    public void connctNetwork(SharedPreferencesUtil sharedPreferencesUtil) {
        try {
            checkViewAttach();
            final INetworkView iNetworkView = getView();
            user.connectNetwork(username, password,sharedPreferencesUtil,new NetworkCallback() {
                @Override
                public void onSuccess(String success) {
                    iNetworkView.showMessage(success);
                }

                @Override
                public void onFailure(String fail) {
                    iNetworkView.showMessage(fail);

                }
            });
        } catch (ViewNotAttachException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disConnectMobile() {
        try {
            checkViewAttach();
            final INetworkView iNetworkView = getView();
            user.disconnectMobile(ipAddress, new NetworkCallback() {
                @Override
                public void onSuccess(String success) {
                    iNetworkView.showMessage(success);
                }

                @Override
                public void onFailure(String fail) {
                    iNetworkView.showMessage(fail);

                }
            });
        } catch (ViewNotAttachException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disConnectAll() {
        try {
            checkViewAttach();
            final INetworkView iNetworkView = getView();
            user.disconnectAll(username, password, new NetworkCallback() {
                @Override
                public void onSuccess(String success) {
                    iNetworkView.showMessage(success);
                }

                @Override
                public void onFailure(String fail) {
                    iNetworkView.showMessage(fail);

                }
            });
        } catch (ViewNotAttachException e) {
            e.printStackTrace();
        }

    }
}
