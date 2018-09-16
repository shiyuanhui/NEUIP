package com.shiyuanhui.neucse;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;
import com.shiyuanhui.bean.PushMessage;
import com.shiyuanhui.neuip.R;
import com.shiyuanhui.util.DirectorySplit;

import cn.bmob.push.PushConstants;

/**
 * android 9.0推送失效，亲测6.0及以下可用，7.0与8.0未测试
 * @author shiyuanhui
 *
 */
public class MyPushMessageReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //oldPush(context,intent);
        newPush(context,intent);
    }


    /**
     * 新推送方式，后台以json格式进行推送
     * example:{"tag":"link","title":"test","text":"hahahah","url":"http://www.baidu.com"}
     * @param context
     * @param intent
     */
    private void newPush(Context context, Intent intent)
    {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE))
        {
            String result = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Gson gson = new Gson();
            PushMessage pushMessage = gson.fromJson(result,PushMessage.class);
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification;
            Notification.Builder builder = new Notification.Builder(context).setTicker(pushMessage.getTitle())
                    .setSmallIcon(R.drawable.notify_logo);
            if (pushMessage.getTag().equals("text"))
            {
                notification = builder.setContentIntent(null).setContentTitle(pushMessage.getTitle()).setContentText(pushMessage.getText()).build();

            }
            else
            {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pushMessage.getUrl()));
                PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                // notification.setLatestEventInfo(context, msg.get(0).toString(), content, pi);
                notification = builder.setContentIntent(pi).setContentTitle(pushMessage.getTitle()).setContentText(pushMessage.getText()).build();

            }
            manager.notify(1, notification);

        }

    }












    /**
     * 旧推送接收数据方式，推送广播接收器类,后台推送时例子1.纯文本推送：你好;;;nothing;;;大家好才是真的好2.带链接推送：你好;;;http:~~www.baidu.com;;;大家好才是真的好
     * @param context
     * @param intent
     */
    private void oldPush(Context context, Intent intent)
    {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            String result = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            String temp = result.substring(10);
            result="";
            for(int j =0;j<temp.length()-2;j++){
                result+=temp.charAt(j);
            }
            ArrayList<String> msg = DirectorySplit.handleDirectory(result);
            String content = "";
            for(int i =2;i<msg.size();i++){
                content+=msg.get(i).toString();
            }

            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            //Notification notification = new Notification(R.drawable.notify_logo, msg.get(0).toString(), System.currentTimeMillis());
            Notification notification;
            Notification.Builder builder = new Notification.Builder(context).setTicker(msg.get(0).toString())
                    .setSmallIcon(R.drawable.notify_logo);
            if(!msg.get(1).toString().equals("nothing"))
            {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(msg.get(1).toString()));
                PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                // notification.setLatestEventInfo(context, msg.get(0).toString(), content, pi);
                notification = builder.setContentIntent(pi).setContentTitle(msg.get(0)).setContentText(content).build();

            }
            else{
                //notification.setLatestEventInfo(context, msg.get(0).toString(), content, null);
                notification = builder.setContentIntent(null).setContentTitle(msg.get(0)).setContentText(content).build();
            }
            manager.notify(1, notification);
        }
    }

}
