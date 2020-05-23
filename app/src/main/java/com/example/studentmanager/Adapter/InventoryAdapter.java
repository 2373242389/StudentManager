package com.example.studentmanager.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.studentmanager.R;
import com.example.studentmanager.entity.Inventory;

import java.util.List;

public class InventoryAdapter extends BaseRecyclerViewAdapter<Inventory> {

    private OnDeleteClickLister mDeleteClickListener;

    public InventoryAdapter(Context context, List<Inventory> data) {
        super(context, data, R.layout.item_inventroy);
    }

  @Override
     public void onBindData(BaseRecyclerHolder holder, Inventory bean, int position) {
        View view = holder.getView(R.id.tv_delete);
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
        ((TextView) holder.getView(R.id.tv_item_desc)).setText(bean.getItemDesc());
        String quantity = bean.getQuantity() + "箱";
        ((TextView) holder.getView(R.id.tv_quantity)).setText(quantity);
        String detail = bean.getItemCode() + "/" + bean.getDate();
        ((TextView) holder.getView(R.id.tv_detail)).setText(detail);
        String volume = bean.getVolume() + "方";
        ((TextView) holder.getView(R.id.tv_volume)).setText(volume);
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

}