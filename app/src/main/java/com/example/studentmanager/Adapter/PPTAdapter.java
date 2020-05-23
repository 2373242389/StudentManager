package com.example.studentmanager.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.entity.PPTFile;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PPTAdapter extends RecyclerView.Adapter<PPTAdapter.PPTViewHolder> implements View.OnClickListener{
    private Context context;
    private List<PPTFile> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public PPTAdapter(Context context,List<PPTFile> list){
        this.context = context;
        this.list = list;
    }
    public interface OnItemClickListener{
        void onClick(View view,int position);
    }
    public void setOnItemClickListener(PPTAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public PPTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      PPTViewHolder holder = new PPTViewHolder(LayoutInflater.from(context).inflate(R.layout.ppt_item,parent,false));
      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PPTViewHolder holder, int position) {
        holder.ppt_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked());
                onItemClickListener.onClick(v,position);
            }
        });
        holder.ppt_name.setText(list.get(position).getFile().getFilename());
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }

    }

     class PPTViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView ppt_name;
        private LinearLayout ppt_layout;
         public PPTViewHolder(@NonNull View itemView) {
             super(itemView);
             ppt_layout = itemView.findViewById(R.id.ppt_layout);
             ppt_name = itemView.findViewById(R.id.name);
             checkBox = itemView.findViewById(R.id.id_check_box);
         }
     }
}
