package com.ats.pos_app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.pos_app.R;
import com.ats.pos_app.model.PieItem;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

     private List<PieItem> itemList;
    private Context context;

    public ItemAdapter(List<PieItem> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_layout_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder myViewHolder, int i) {
        int pos=i+1;
        PieItem model=itemList.get(i);
        myViewHolder.tvSrNo.setText(""+pos);
        myViewHolder.tvItemName.setText(""+model.getItemName());
        myViewHolder.tvAmount.setText(""+model.getItemTotal());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSrNo,tvItemName,tvAmount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSrNo=(itemView).findViewById(R.id.tvSrNo);
            tvItemName=(itemView).findViewById(R.id.tvItemName);
            tvAmount=(itemView).findViewById(R.id.tvAmount);
        }
    }
}
