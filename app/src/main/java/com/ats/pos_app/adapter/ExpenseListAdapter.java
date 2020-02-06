package com.ats.pos_app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.pos_app.R;
import com.ats.pos_app.activity.MainActivity;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.fragment.AddExpenseFragment;
import com.ats.pos_app.fragment.ExpenseListFragment;
import com.ats.pos_app.model.ExpenseList;
import com.ats.pos_app.model.Info;
import com.ats.pos_app.utils.CommonDialog;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.MyViewHolder>  {

    private List<ExpenseList> expenseList;
    private Context context;

    public ExpenseListAdapter(List<ExpenseList> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.expense_list_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListAdapter.MyViewHolder myViewHolder, int i) {
        final ExpenseList model=expenseList.get(i);

        myViewHolder.tvDate.setText(""+model.getExpDate());
        myViewHolder.tvCalanNo.setText(""+model.getChalanNo());
        myViewHolder.tvAmount.setText(""+model.getChAmt());

        if(model.getExpType().equalsIgnoreCase("1"))
        {
            myViewHolder.tvStatus.setText("Regular");
        }else if(model.getExpType().equalsIgnoreCase("2"))
        {
            myViewHolder.tvStatus.setText("Payment Chalan");
        }

        myViewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_edit) {
                            Gson gson = new Gson();
                            String json = gson.toJson(model);

                            MainActivity activity = (MainActivity) context;
                            Fragment adf = new AddExpenseFragment();
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            adf.setArguments(args);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "ExpenseListFragment").commit();

                        }else if(menuItem.getItemId()==R.id.action_delete)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("Confirm Action");
                            builder.setMessage("Do you want to delete expense?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   deleteExpense(model.getExpId());
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void deleteExpense(Integer expId) {
        Log.e("PARAMETER","            EXPENCE ID  :      "+expId);
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.deleteExpense(expId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("DELETE EXPENCES: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new ExpenseListFragment(), "DashFragment");
                                ft.commit();

                            } else {
                                Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCalanNo,tvDate,tvAmount,tvStatus;
        public ImageView ivEdit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate=(itemView).findViewById(R.id.tvDate);
            tvCalanNo=(itemView).findViewById(R.id.tvCalanNo);
            tvAmount=(itemView).findViewById(R.id.tvAmount);
            tvStatus=(itemView).findViewById(R.id.tvStatus);
            ivEdit=(itemView).findViewById(R.id.ivEdit);
        }
    }
}
