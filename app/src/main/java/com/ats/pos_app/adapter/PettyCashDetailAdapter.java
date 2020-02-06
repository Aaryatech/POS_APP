package com.ats.pos_app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.pos_app.R;
import com.ats.pos_app.model.PettyCashDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PettyCashDetailAdapter extends RecyclerView.Adapter<PettyCashDetailAdapter.MyViewHolder> {

    private ArrayList<PettyCashDetail> pettyCashDetailsList;
    private Context context;

    public PettyCashDetailAdapter(ArrayList<PettyCashDetail> pettyCashDetailsList, Context context) {
        this.pettyCashDetailsList = pettyCashDetailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public PettyCashDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.petty_cash_detail_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PettyCashDetailAdapter.MyViewHolder myViewHolder, int i) {
        PettyCashDetail model=pettyCashDetailsList.get(i);
        myViewHolder.tvOpnAmt.setText(""+model.getOpeningAmt());
        myViewHolder.tvCashAmt.setText(""+model.getCashAmt());
        myViewHolder.tvWithdrawalAmt.setText(""+model.getWithdrawalAmt());
        myViewHolder.tvClosingAmt.setText(""+model.getClosingAmt());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        long millisecond = Long.parseLong(model.getDate());
        // or you already have long value of date, use this instead of milliseconds variable.
        String dateString = DateFormat.format("dd-MM-yyyy", new Date(millisecond)).toString();

        myViewHolder.tvDate.setText(""+dateString);

    }

    @Override
    public int getItemCount() {
        return pettyCashDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate,tvOpnAmt,tvCashAmt,tvWithdrawalAmt,tvClosingAmt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate=(itemView).findViewById(R.id.tvDate);
            tvOpnAmt=(itemView).findViewById(R.id.tvOpnAmt);
            tvCashAmt=(itemView).findViewById(R.id.tvCashAmt);
            tvWithdrawalAmt=(itemView).findViewById(R.id.tvWithdrawalAmt);
            tvClosingAmt=(itemView).findViewById(R.id.tvClosingAmt);
        }
    }
}
