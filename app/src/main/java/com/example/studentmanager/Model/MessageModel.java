package com.example.studentmanager.Model;

import java.util.List;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MessageModel {
    private static MessageModel ourInstance = new MessageModel();
    public static MessageModel getInstance(){return ourInstance;}
    private MessageModel(){}

    public void findSbAllNews(String No, FindListener<BmobIMMessage> listener){
        BmobQuery<BmobIMMessage> query = new BmobQuery<>();
        query.addWhereEqualTo("toId",No);
        query.findObjects(new FindListener<BmobIMMessage>() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                listener.done(list,e);
            }
        });

    }

}
