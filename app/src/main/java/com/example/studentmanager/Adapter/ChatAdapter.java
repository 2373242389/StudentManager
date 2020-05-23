package com.example.studentmanager.Adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.entity.Teacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<BmobIMMessage> msgs = new ArrayList<>();
    BmobIMConversation c;
    private String currentUid = "";
    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView chat_send_time;
        public ImageView chat_send_image;
        public TextView chat_send_content;

        public TextView chat_recieve_time;
        public ImageView chat_recieve_image;
        public TextView chat_recieve_content;

        public LinearLayout leftLayout;
        public LinearLayout rightLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout = (LinearLayout)itemView.findViewById(R.id.left_layout);
            rightLayout =(LinearLayout)itemView.findViewById(R.id.right_layout);
            chat_send_time = (TextView) itemView.findViewById(R.id.chat_send_time);
            chat_send_image = (ImageView) itemView.findViewById(R.id.chat_send_picture);
            chat_send_content = (TextView) itemView.findViewById(R.id.chat_send_content);
            chat_recieve_time = (TextView) itemView.findViewById(R.id.chat_recieve_time);
            chat_recieve_image = (ImageView) itemView.findViewById(R.id.chat_receive_picture);
            chat_recieve_content = (TextView) itemView.findViewById(R.id.chat_receive_content);
        }
    }

    public ChatAdapter(Context context,BmobIMConversation conversation) {
        if (Constant.teacher!= null) {
            currentUid = Constant.teacher.getTeacherNo();
        }else{currentUid = Constant.student.getStuno();}
        this.c =conversation;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg,parent,false);
        return new ViewHolder(view);
}


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BmobIMMessage chatMessageInfo = msgs.get(position);

        if (chatMessageInfo.getMsgType().equals(BmobIMMessageType.TEXT.getType())){
            if (chatMessageInfo.getFromId().equals(currentUid)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                String time = dateFormat.format(chatMessageInfo.getCreateTime());
                holder.leftLayout.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.VISIBLE);
                holder.chat_send_content.setText(chatMessageInfo.getContent());
                holder.chat_send_time.setText(time);
            }
        else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                String time = dateFormat.format(chatMessageInfo.getCreateTime());
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.chat_recieve_content.setText(chatMessageInfo.getContent());
            holder.chat_recieve_time.setText(time);
         }
        }
    }
    public void addMessages(List<BmobIMMessage> messages) {
        msgs.addAll(0, messages);
        notifyDataSetChanged();
    }
    public void addMessage(BmobIMMessage message) {
        msgs.addAll(Arrays.asList(message));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }


    public int getCount() {
        return this.msgs == null?0:this.msgs.size();
    }
    public int findPosition(BmobIMMessage message) {
        int index = this.getCount();
        int position = -1;
        while(index-- > 0) {
            if(message.equals(this.getItem(index))) {
                position = index;
                break;
            }
        }
        return position;
    }
    public BmobIMMessage getItem(int position){
        return this.msgs == null?null:(position >= this.msgs.size()?null:this.msgs.get(position));
    }

    public BmobIMMessage getFirstMessage() {
        if (null != msgs && msgs.size() > 0) {
            return msgs.get(0);
        } else {
            return null;
        }
    }
}
