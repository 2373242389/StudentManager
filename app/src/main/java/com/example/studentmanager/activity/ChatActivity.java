package com.example.studentmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.ChatAdapter;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private Button chat_send;
    private EditText message_et;
    BmobIMConversation c ;
    BmobIMConversation  messageManager;
    private Intent intent;
    private Student student;
    BmobIMUserInfo info;
    private BackTitleView friendName_title;
    public static ChatAdapter adapter;
    public static RecyclerView msg_list;
    private LinearLayoutManager layoutManager;
    private Teacher teacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        layoutManager = new LinearLayoutManager(this);
        chat_send = (Button)findViewById(R.id.chat_send);
        message_et = (EditText)findViewById(R.id.message_et);
        msg_list = (RecyclerView)findViewById(R.id.msgs);
        msg_list.setLayoutManager(layoutManager);
        friendName_title = (BackTitleView)findViewById(R.id.friendName_title);
        intent = getIntent();
        if ((student = (Student) intent.getSerializableExtra("u")) != null){
        info = new BmobIMUserInfo(student.getStuno(),student.getName(),null);
        c  = BmobIM.getInstance().startPrivateConversation(info,null);
        messageManager  =BmobIMConversation.obtain(BmobIMClient.getInstance(),c);
        friendName_title.setTitleText(student.getName());
        }else{   teacher = (Teacher) intent.getSerializableExtra("t");
        info = new BmobIMUserInfo(teacher.getTeacherNo(),teacher.getName(),null);
        c  = BmobIM.getInstance().startPrivateConversation(info,null);
        messageManager  =BmobIMConversation.obtain(BmobIMClient.getInstance(),c);
        friendName_title.setTitleText(teacher.getName());
        }
        adapter = new ChatAdapter(ChatActivity.this,messageManager);
        msg_list.setAdapter(adapter);
        chat_send.setOnClickListener(this);
        BmobIMMessage msg = adapter.getFirstMessage();
        queryMessages(msg);
    }


    @Override
    protected void onResume() {
        super.onResume();
        }

    public MessageSendListener listener =new MessageSendListener() {

        @Override
        public void onStart(BmobIMMessage bmobIMMessage) {
            super.onStart(bmobIMMessage);
            adapter.addMessage(bmobIMMessage);
            Logger.i("发送的消息是"+bmobIMMessage.toString());
            message_et.setText("");
            scrollToBottom();

        }


        @Override
        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
            adapter.notifyDataSetChanged();
            message_et.setText("");
            scrollToBottom();

        }
    };
    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
    }
    private void sendMessage(){
        String text=message_et.getText().toString();
        if(TextUtils.isEmpty(text.trim())){
            ToastUtils.showMessage(ChatActivity.this,"请输入内容");
            return;
        }
        BmobIMTextMessage msg =new BmobIMTextMessage();
        msg.setContent(text);
        Map<String,Object> map =new HashMap<>();
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat_send:
                sendMessage();
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event){
        ToastUtils.showMessage(ChatActivity.this,"你是猪");
        addMessage2Chat(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(OfflineMessageEvent event){
      ToastUtils.showMessage(ChatActivity.this,"我进来了");
        Map<String, List<MessageEvent>> map =event.getEventMap();
        if(map!=null&&map.size()>0){
            //只获取当前聊天对象的离线消息
            List<MessageEvent> list = map.get(messageManager.getConversationId());
            if(list!=null && list.size()>0){
                for (int i=0;i<list.size();i++){
                    addMessage2Chat(list.get(i));
                }
            }
        }
    }

    private void queryMessages(BmobIMMessage msg){
            c.queryMessages(msg, 10, new MessagesQueryListener() {
                @Override
                public void done(List<BmobIMMessage> list, BmobException e) {
                    if (e == null){
                        if (null != list && list.size()>0){
                            adapter.addMessages(list);
                            layoutManager.scrollToPositionWithOffset(list.size() - 1, 0);
                        }
                    }
                }
            });
    }
    private void addMessage2Chat(MessageEvent event){
        BmobIMMessage msg =event.getMessage();
        if(messageManager!=null && event!=null && messageManager.getConversationId().equals(event.getConversation().getConversationId()) //如果是当前会话的消息
                && !msg.isTransient()){//并且不为暂态消息
            if(adapter.findPosition(msg)<0){//如果未添加到界面中
                adapter.addMessage(msg);
                //更新该会话下面的已读状态
                messageManager.updateReceiveStatus(msg);
            }
            scrollToBottom();
        }else{
            Logger.i("不是与当前聊天对象的消息");
        }
    }
//    @Override
//    public void onMessageReceive(List<MessageEvent> list) {
//        Logger.i("聊天页面接收到消息：" + list.size());
//        ToastUtils.showMessage(ChatActivity.this, String.valueOf(list.size()));
//        //当注册页面消息监听时候，有消息（包含离线消息）到来时会回调该方法
//        for (int i=0;i<list.size();i++){
//            onEventMainThread(list.get(i));
//        }
//    }
}
