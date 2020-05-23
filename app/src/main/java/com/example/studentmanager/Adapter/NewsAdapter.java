package com.example.studentmanager.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.TimeUtil;
import com.example.studentmanager.entity.Conversation;
import com.example.studentmanager.entity.Student;

import java.util.List;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NewsAdapter extends BaseRecyclerViewAdapter<Conversation> {

    public NewsAdapter(Context context, List<Conversation> data) {
        super(context, data, R.layout.news_item);
    }

    @Override

    public void onBindData(BaseRecyclerHolder holder, Conversation item, int position) {
        ((TextView) holder.getView(R.id.wx_item_stuNo)).setText(item.getcId());
        ((TextView) holder.getView(R.id.wx_item_name)).setText(item.getcName());
        ((TextView) holder.getView(R.id.wx_item_content)).setText(item.getLastMessageContent());
        ((TextView) holder.getView(R.id.wx_item_time)).setText( TimeUtil.getChatTime(false,item.getLastMessageTime()));
        long unread = item.getUnReadCount();
        if(unread>0){
            holder.getView(R.id.tv_recent_unread).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_recent_unread, String.valueOf(unread));
        }else{
            holder.getView(R.id.tv_recent_unread).setVisibility(View.GONE);
        }
    }
}
