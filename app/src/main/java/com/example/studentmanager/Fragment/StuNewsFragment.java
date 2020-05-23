package com.example.studentmanager.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.NewsAdapter;
import com.example.studentmanager.Model.MessageModel;
import com.example.studentmanager.Model.TeacherModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.ChatActivity;
import com.example.studentmanager.activity.student.StuContactActivity;
import com.example.studentmanager.activity.teacher.TeacherContactActivity;
import com.example.studentmanager.entity.Conversation;
import com.example.studentmanager.entity.PrivateConversation;
import com.example.studentmanager.entity.Teacher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.http.I;
import cn.bmob.v3.listener.FindListener;

public class StuNewsFragment extends Fragment implements View.OnClickListener {
    Intent intent;
    private NewsAdapter newsAdapter;
    private RecyclerView news_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_news_layout, container, false);
        ImageView friends = (ImageView) v.findViewById(R.id.friends);
        news_list = (RecyclerView) v.findViewById(R.id.news_list);
        news_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        friends.setOnClickListener(this);
        initNewsList();
        return v;

    }

    private void initNewsList() {
        newsAdapter = new NewsAdapter(getActivity(),getConversations());
        ToastUtils.showMessage(getActivity(),getConversations().toString());
        news_list.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String s = getConversations().get(position).getcId();
                TeacherModel.getInstance().findTeacherById(s, new FindListener<Teacher>() {
                    @Override
                    public void done(List<Teacher> list, BmobException e) {
                        Intent intent = new Intent(getActivity(),ChatActivity.class);
                        intent.putExtra("t",list.get(0));
                        startActivity(intent);
                    }
                });
            }
        });
        newsAdapter.setOnItemLongClickListener(new NewsAdapter.OnItemLongClickListener() {
            @Override
            public void onIntemLongClickListener(View v, int position) {
                newsAdapter.delete(position);
                BmobIM.getInstance().deleteConversation(getConversations().get(position).getcId());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initNewsList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friends:
                intent = new Intent(getActivity(), StuContactActivity.class);
                startActivity(intent);
                break;
        }
    }

    private List<Conversation> getConversations() {
        //添加会话
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        if (list != null && list.size() > 0) {
            for (BmobIMConversation item : list) {
                switch (item.getConversationType()) {
                    case 1://私聊
                        if (!TextUtils.equals(item.getConversationId(),Constant.student.getStuno())) {
                            conversationList.add(new PrivateConversation(item));
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return conversationList;
    }
}
