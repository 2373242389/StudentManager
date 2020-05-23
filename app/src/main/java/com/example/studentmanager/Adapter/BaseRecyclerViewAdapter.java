package com.example.studentmanager.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.recyclerview.widget.*;


import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.ListIterator;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder>{

    private Context mContext;
    private List<T> mData; //数据源
    private LayoutInflater inflater; //布局器
    private int mLayoutId; //布局id
    private OnItemClickListener itemClickListener; //事件监听
    private OnItemLongClickListener longClickListener;
    private RecyclerView recyclerView;
    private List<T> datas =new ArrayList<T>();



    //在RecyclerView提供数据时调用
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    //RecyclerView消失时调用
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }


    public void insert(T item,int position){
        mData.add(position,item);
        notifyItemChanged(position);
    }
    public void delete(int position){
        mData.remove(position);
        notifyItemChanged(position);
    }

   public BaseRecyclerViewAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutId = layoutId;
        inflater = LayoutInflater.from(context);
    }

    public BaseRecyclerViewAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
    }

    public BaseRecyclerViewAdapter(){

    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(mLayoutId, parent, false);
        return BaseRecyclerHolder.getRecyclerHolder(mContext,view);
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    itemClickListener.onItemClick(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(longClickListener != null){
                    longClickListener.onIntemLongClickListener(v,position);
                }
                return true;
            }
        });
        onBindData(holder, mData.get(position), position);
    }

    public abstract void onBindData(BaseRecyclerHolder holder, T item, int position);
    @Override
    public int getItemCount() {
        return mData.size();
    }

       /*
    定义一个点击事件接口回调
     */

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public interface OnItemLongClickListener {
        void onIntemLongClickListener(View v,int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}