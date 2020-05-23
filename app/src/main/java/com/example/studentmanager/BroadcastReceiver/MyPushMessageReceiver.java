package com.example.studentmanager.BroadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.studentmanager.R;
import com.example.studentmanager.activity.student.StuTabActivity;
import com.example.studentmanager.entity.PushInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;

public class MyPushMessageReceiver extends BroadcastReceiver {
    private static final String TAG = "MyPushMessageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);

        /*推送的数据格式，json格式，alert为标题，articleurl为传递为要显示的网页内容
        {
            "alert": "xxxx",
            "articleurl": "http://xxx"
        }*/

//        Gson gson = new Gson();
//        PushInfo pushInfo = gson.fromJson(message, PushInfo.class);
        JSONTokener jsonTokener = new JSONTokener(message);
        try {
            JSONObject object = (JSONObject) jsonTokener.nextValue();
            message = object.getString("alert");//获取具体的消息内容
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        String alert = pushInfo.getAlert();
//        String articleurl = pushInfo.getArticleurl();

//        // 发送通知
//        Intent intent1 = new Intent(context, StuTabActivity.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent1.putExtra("articleurl",articleurl);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 130, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle("收到一条信息")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .build();



        //FLAG_UPDATE_CURRENT  更新当前的通知信息


        nm.notify(0, notification);
    }
}