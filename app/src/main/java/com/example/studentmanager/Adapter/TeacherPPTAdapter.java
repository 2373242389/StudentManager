package com.example.studentmanager.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.studentmanager.R;

import java.util.List;

public class TeacherPPTAdapter extends BaseRecyclerViewAdapter<String> {
    private BaseRecyclerViewAdapter.OnDeleteClickLister mDeleteClickListener;

    public TeacherPPTAdapter(Context context, List<String> data) {
        super(context, data, R.layout.teacher_ppt_item);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, String item, int position) {
        View view = holder.getView(R.id.tv_item_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        ((TextView)holder.getView(R.id.teacher_ppt_name)).setText(item);
    }
    public void setOnDeleteClickListener(TeacherPPTAdapter.OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }
}
