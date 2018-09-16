package com.shiyuanhui.model;

import android.text.TextUtils;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class User implements IUser {



    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private final String CONNECT_URL = "https://ipgw.neu.edu.cn/srun_portal_pc.php?url=&ac_id=1";
    private final String ACCOUNT_BASE_URL = "https://ipgw.neu.edu.cn/include/auth_action.php?k=";
    private final String DISCONNECT_MOBILE_URL = "https://ipgw.neu.edu.cn/srun_portal_pc.php?url=&ac_id=1";
    private final String DISCONNECT_ALL_URL = "https://ipgw.neu.edu.cn/include/auth_action.php";

    //构造函数私有化
    private User()
    {

    }

    private static class Holder
    {
        private final static User instatce = new User();
    }

    //使用单例模式获取对象
    public static User getInstance()
    {
        return Holder.instatce;
    }


    @Override
    public void connectNetwork(final String userName, String password, final NetworkCallback callback) {
        RequestBody body = new FormBody.Builder()
                .add("action", "login")
                .add("ac_id", "1")
                .add("nas_ip", "")
                .add("user_ip", "")
                .add("user_mac", "")
                .add("url", "")
                .add("username", userName)
                .add("password", password)
                .add("save_me", "0")
                .build();
        Request request = new Request.Builder()
                .url(CONNECT_URL)
                .post(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("与校园网服务器通信失败。。。");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resString = response.body().string();
                if(resString.indexOf("网络已连接") != -1)
                {
                    getAcount(callback);
                }
                else {
                    if(resString.indexOf("密码错误") != -1)
                    {
                        resString = "密码错误，请返回重新登录";
                    }
                    else if(resString.indexOf("用户不存在") != -1)
                    {
                        resString = "用户不存在，请返回重新登录";
                    }
                    else if(resString.indexOf("已经在线了") != -1)
                    {
                        resString = "手机网络重复连接，请先断其它手机网络或者断开全部连接后重新连接";
                    }
                    else if(resString.indexOf("已欠费") != -1){
                        resString = "您的校园网已经欠费";
                    }
                    else if(userName.equals("")){
                        resString = "未先设置账号和密码，请在Settings中先设置";
                    }
                    else if(resString.indexOf("<p>E") != -1){
                        String str1[] = resString.split("<p>E");
                        String str2[] = str1[1].split("</p>");
                        resString = "E"+str2[0];
                    }
                    else resString = "未知错误，请返回重试";
                }

                //以上代码其实可以优化下，全是if-else看着不是很优雅
                callback.onSuccess(resString);

            }
        });

    }

    @Override
    public void disconnectMobile(String ipAddress, final NetworkCallback callback) {
        RequestBody body = new FormBody.Builder()
                .add("action", "auto_logout")
                .add("info", "")
                .add("user_ip", ipAddress)
                .build();
        Request request = new Request.Builder()
                .url(DISCONNECT_MOBILE_URL)
                .post(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("与校园网服务器通信失败。。。");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resString = response.body().string();
                if(resString.indexOf("网络已断开") != -1)
                {
                    resString = "手机网络已断开";
                }
                else if(resString.indexOf("您似乎未曾连接到网络...") != -1)
                {
                    resString = "您似乎未曾连接到网络...";
                }
                else resString = "未知错误，请返回重试";
                callback.onFailure(resString);

            }
        });

    }

    @Override
    public void disconnectAll(String userName, String password, final NetworkCallback callback) {
        RequestBody body = new FormBody.Builder()
                .add("action", "logout")
                .add("username", userName)
                .add("password", password)
                .add("ajax", "1")
                .build();
        Request request = new Request.Builder()
                .url(DISCONNECT_ALL_URL)
                .post(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("与校园网服务器通信失败。。。");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resString = response.body().string();
                if(resString.indexOf("网络已断开") != -1)
                {
                    resString = "网络已全部断开";
                }
                else if(resString.indexOf("您似乎未曾连接到网络...") != -1)
                {
                    resString = "您似乎未曾连接到网络...";
                }
                else resString = "未知错误，请返回重试";

                callback.onSuccess(resString);

            }
        });

    }


    /**
     * 网络请求，以获得流量余额等信息
     * @return
     */
    @Override
    public void getAcount(final NetworkCallback callback)
    {
        final int k = (int)Math.floor(Math.random() * ( 100000 + 1));//k的值看校园网源码函数栈时看到是这个函数
        RequestBody body = new FormBody.Builder()
                .add("action", "get_online_info")
                .add("key", ""+k)
                .build();
        Request request = new Request.Builder()
                .url(ACCOUNT_BASE_URL+k)
                .post(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("与校园网服务器通信失败。。。");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                String flowUsed = "已用流量：";
                String timeUsed = "已用时长：";
                String moneyLeft = "账户余额：";
                String ipAddress = "IP地址：";

                if(!TextUtils.isEmpty(res)){
                    String[] information = res.split(",");
                    if(information != null && information.length>0)
                    {
                        double flow = Double.parseDouble(information[0])/1024/1024/1024;
                        DecimalFormat decimalFormat = new DecimalFormat(".000");
                        flow = Double.parseDouble(decimalFormat.format(flow));
                        flowUsed = flowUsed+flow;//流量信息

                        double time = Long.parseLong(information[1]);
                        int h = (int)(time/3600);
                        int m = (int)(time%3600)/60;
                        int s = (int)(time%3600)%60;
                        timeUsed = timeUsed+h+":"+m+":"+s;//已用时长

                        moneyLeft += information[2];//余额
                        ipAddress += information[5];//ip地址

                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("网络已连接\n")
                                .append(flowUsed).append("G\n")
                                .append(timeUsed).append("\n")
                                .append(moneyLeft).append("元\n")
                                .append(ipAddress);
                        callback.onSuccess(stringBuilder.toString());

                        //preferencesUtil.saveIPAddress(information[5]);//保存ip地址
                        //sendHandler(handler, 0, ("网络已连接\n"+liuliangres+"G\n"+shichangres+"\n"+yueres+"元\n"+IPadress).toString());
                    }
                }

            }
        });

    }
}
