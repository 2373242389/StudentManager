package com.example.studentmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studentmanager.Adapter.ChatAdapter;
import com.example.studentmanager.Model.TeacherModel;
import com.example.studentmanager.Model.i.Model;
import com.example.studentmanager.Model.i.QueryUserListener;
import com.example.studentmanager.Model.i.UpdateCacheListener;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.ChatActivity;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;

public class MessageHandler extends BmobIMMessageHandler {



    private Context context;
    public MessageHandler(Context context){
        this.context = context;
    }
    //在线接收
    @Override
    public void onMessageReceive(MessageEvent messageEvent) {
        Logger.i("在线接收到信息"+messageEvent.getConversation().getConversationTitle() + "，" + messageEvent.getMessage().getMsgType() + "," + messageEvent.getMessage().getContent());
        excuteMessage(messageEvent);
    }


private void excuteMessage(final MessageEvent event){
    EventBus.getDefault().post(event);
}
    public void updateUserInfo(MessageEvent event, final UpdateCacheListener listener) {
        final BmobIMConversation conversation = event.getConversation();
        final BmobIMUserInfo info = event.getFromUserInfo();
        final BmobIMMessage msg = event.getMessage();
        String username = info.getName();
        String title = conversation.getConversationTitle();
        //SDK内部将新会话的会话标题用objectId表示，因此需要比对用户名和私聊会话标题，后续会根据会话类型进行判断
        if (!username.equals(title)) {
            TeacherModel.getInstance().queryUserInfo(info.getUserId(), new QueryUserListener() {
                @Override
                public void done(Object s, BmobException e) {
                    if (e == null) {
                       Student stu = (Student)s;
                       String name = stu.getName();
                        conversation.setConversationTitle(name);
                        info.setName(name);
                        //TODO 会话：2.7、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                        BmobIM.getInstance().updateUserInfo(info);
                        //TODO 会话：4.7、更新会话资料-如果消息是暂态消息，则不更新会话资料
                        if (!msg.isTransient()) {
                            BmobIM.getInstance().updateConversation(conversation);
                        }
                    } else {
                        Logger.e(e);
                    }
                    listener.done(null);
                }
            });
        } else {
            listener.done(null);
        }
    }
    //离线接收
    @Override
    public void onOfflineReceive(OfflineMessageEvent event) {
        Map<String, List<MessageEvent>> map = event.getEventMap();
        Logger.i("离线消息属于" + map.size() + "个用户");
        Logger.i("离线消息"+map.toString());
        for (Map.Entry<String, List<MessageEvent>> entry : map.entrySet()) {
            List<MessageEvent> list = entry.getValue();
            int size = list.size();
            for (int i = 0; i < size; i++) {
             excuteMessage(list.get(i));
            }

        }
    }
}
