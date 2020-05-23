package com.example.studentmanager.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

//万能Holder
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews; //介质对数组，相当于HashMap
    private Context  context;   //上下文

    BaseRecyclerHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        this.mViews = new SparseArray<>();
    }
    public static BaseRecyclerHolder getRecyclerHolder(Context context,View itemView){
        return new BaseRecyclerHolder(context, itemView);
    }

    public SparseArray<View> getViews(){
        return this.mViews;
    }
    /**
     * 获取需要的View，如果已经存在引用则直接获取，如果不存在则重新加载并保存
     *
     * @param viewId id
     * @return 需要的View
     */
    public<T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }
    public BaseRecyclerHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
    public BaseRecyclerHolder setImageResource(int viewId,int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }
    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }
    public BaseRecyclerHolder setImageByUrl(int viewId,String url){
        Picasso.with(context).load(url).into((ImageView) getView(viewId));
        return this;
    }

}