package com.ats.pos_app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.pos_app.R;
import com.ats.pos_app.model.PettyCashHandOverTransaction;

import java.util.List;

public class PettyCashHandOverTransactionAdapter extends RecyclerView.Adapter<PettyCashHandOverTransactionAdapter.MyViewHolder> {

    private List<PettyCashHandOverTransaction> pettyCashList;
  // private PettyCashHandOverTransaction[] pettyCashList;
    private Context context;

    public PettyCashHandOverTransactionAdapter(List<PettyCashHandOverTransaction> pettyCashList, Context context) {
        this.pettyCashList = pettyCashList;
        this.context = context;
    }

    @NonNull
    @Override
    public PettyCashHandOverTransactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.petty_cash_handover_layout_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PettyCashHandOverTransactionAdapter.MyViewHolder myViewHolder, int i) {
        PettyCashHandOverTransaction model=pettyCashList.get(i);
        myViewHolder.tvTranDate.setText(""+model.getTransactionDate());
        myViewHolder.tvFromEmp.setText(""+model.getFromUsername());
        myViewHolder.tvToEmp.setText(""+model.getToUsername());
        myViewHolder.tvOpeningAmt.setText(""+model.getOpeningAmt());
        myViewHolder.tvSellingAmt.setText(""+model.getSellingAmt());
        myViewHolder.tvTotalCashAmt.setText(""+model.getActualAmtHandover());
        myViewHolder.tvActualCashHandAmt.setText(""+model.getAmtHandover());

        int diff=model.getActualAmtHandover()-model.getAmtHandover();

        myViewHolder.tvDiff.setText(""+diff);
        myViewHolder.tvEmpSell.setText(""+model.getExVar1());
    }

    @Override
    public int getItemCount() {
        return pettyCashList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTranDate,tvFromEmp,tvToEmp,tvOpeningAmt,tvSellingAmt,tvTotalCashAmt,tvActualCashHandAmt,tvDiff,tvEmpSell;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTranDate=(itemView).findViewById(R.id.tvTranDate);
            tvFromEmp=(itemView).findViewById(R.id.tvFromEmp);
            tvToEmp=(itemView).findViewById(R.id.tvToEmp);
            tvOpeningAmt=(itemView).findViewById(R.id.tvOpeningAmt);
            tvSellingAmt=(itemView).findViewById(R.id.tvSellingAmt);
            tvTotalCashAmt=(itemView).findViewById(R.id.tvTotalCashAmt);
            tvActualCashHandAmt=(itemView).findViewById(R.id.tvActualCashHandAmt);
            tvDiff=(itemView).findViewById(R.id.tvDiff);
            tvEmpSell=(itemView).findViewById(R.id.tvEmpSell);
        }
    }
}
