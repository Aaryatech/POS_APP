package com.ats.pos_app.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.pos_app.R;
import com.ats.pos_app.adapter.ExpenseListAdapter;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.model.ExpenseList;
import com.ats.pos_app.model.UserLogin;
import com.ats.pos_app.utils.CommonDialog;
import com.ats.pos_app.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseListFragment extends Fragment implements View.OnClickListener{
    public RecyclerView recyclerView;
    public FloatingActionButton fab;
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    UserLogin userLogin;

    ArrayList<ExpenseList> expenseLists = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        getActivity().setTitle("Expense List");
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);

        fab=(FloatingActionButton) view.findViewById(R.id.fab1);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.USER_LOGIN);
        Gson gson = new Gson();
        userLogin = gson.fromJson(userStr, UserLogin.class);
        Log.e("HOME_ACTIVITY : ", "-------------------USER LOGIN--------------------" + userLogin);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getExpense(userLogin.getFranchisee().getFrId(),sdf.format(System.currentTimeMillis()),sdf.format(System.currentTimeMillis()),1);


        fab.setOnClickListener(this);
        return view;
    }

    private void getExpense(Integer frId, String fromDate, String toDate, int type) {
        Log.e("PARAMETER","           FR ID  :     "+frId+"           FROM DATE   :     "+fromDate+"         TO DATE  :    "+toDate+"        TYPE  :      "+type);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ExpenseList>> listCall = Constants.myInterface.getExpenseByFrId(frId,type,fromDate,toDate);
            listCall.enqueue(new Callback<ArrayList<ExpenseList>>() {
                @Override
                public void onResponse(Call<ArrayList<ExpenseList>> call, Response<ArrayList<ExpenseList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Expense List : ", "----------------------------Expence List-------------------------" + response.body());

                            expenseLists.clear();
                            expenseLists = response.body();

                            ExpenseListAdapter mAdapter = new ExpenseListAdapter(expenseLists, getActivity());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);


                            commonDialog.dismiss();
                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ExpenseList>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fab1)
        {

            new FilterDialog(getContext()).show();
        }
    }


    public class FilterDialog extends Dialog {

        EditText edFromDate, edToDate;
        TextView tvFromDate, tvToDate;
        ImageView ivClose;
        Spinner spType;

        Context context;

        ArrayList<String> typeArray = new ArrayList<>();
        final ArrayList<Integer> typeIdArray = new ArrayList<>();


        public FilterDialog(@NonNull Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.filter_dialog_layout);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            edFromDate = findViewById(R.id.edFromDate);
            edToDate = findViewById(R.id.edToDate);
            tvFromDate = findViewById(R.id.tvFromDate);
            tvToDate = findViewById(R.id.tvToDate);
            ivClose = findViewById(R.id.ivClose);

            spType = findViewById(R.id.spType);

            Button btnFilter = findViewById(R.id.btnFilter);

            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = formatter.format(todayDate);

            fromDateMillis = todayDate.getTime();
            toDateMillis = todayDate.getTime();

            edFromDate.setText(currentDate);
            edToDate.setText(currentDate);


            typeArray.clear();
            typeArray.add("Regular Work");
            typeArray.add("Payment Chalan");

            typeIdArray.add(1);
            typeIdArray.add(2);

            ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, typeArray);
            spType.setAdapter(typeAdapter);


            edFromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int yr, mn, dy;
                    if (fromDateMillis > 0) {
                        Calendar purchaseCal = Calendar.getInstance();
                        purchaseCal.setTimeInMillis(fromDateMillis);
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    } else {
                        Calendar purchaseCal = Calendar.getInstance();
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    }
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), fromDateListener, yr, mn, dy);
                    dialog.show();
                }
            });

            edToDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int yr, mn, dy;
                    if (toDateMillis > 0) {
                        Calendar purchaseCal = Calendar.getInstance();
                        purchaseCal.setTimeInMillis(toDateMillis);
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    } else {
                        Calendar purchaseCal = Calendar.getInstance();
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    }
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), toDateListener, yr, mn, dy);
                    dialog.show();
                }
            });


            btnFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edFromDate.getText().toString().isEmpty()) {
                        edFromDate.setError("Select From Date");
                        edFromDate.requestFocus();
                    } else if (edToDate.getText().toString().isEmpty()) {
                        edToDate.setError("Select To Date");
                        edToDate.requestFocus();
                    } else {

                        dismiss();

                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat sdf12 = new SimpleDateFormat("yyyy-MM-dd");

                       // final int project = projectIdList.get(spProject.getSelectedItemPosition());

                        String strFromDate = edFromDate.getText().toString();
                        String strToDate = edToDate.getText().toString();

                        Log.e("Date Str","                From Date      "+strFromDate+"            to Date     "+strToDate);

                        Date FromDate = null;
                        try {
                            FromDate = sdf1.parse(strFromDate);//catch exception
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        String fromDate = sdf12.format(FromDate);

                        Log.e("Date From","                From Date      "+fromDate);

                        Date ToDate = null;
                        try {
                            ToDate = sdf1.parse(strToDate);//catch exception
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        String toDate = sdf12.format(ToDate);

                        Log.e("Date","                From Date      "+fromDate+"            to Date     "+toDate);

                        int type = typeIdArray.get(spType.getSelectedItemPosition());

                        getExpense(userLogin.getFranchisee().getFrId(),fromDate,toDate,type);

                    }
                }
            });

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

        }

        DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyy = year;
                mm = month + 1;
                dd = dayOfMonth;
                edFromDate.setText(dd + "-" + mm + "-" + yyyy);
                tvFromDate.setText(yyyy + "-" + mm + "-" + dd);
                //CustomSharedPreference.putString(getActivity(), CustomSharedPreference.KEY_SP_FROM_DATE, yyyy + "-" + mm + "-" + dd);

                Calendar calendar = Calendar.getInstance();
                calendar.set(yyyy, mm - 1, dd);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                fromDateMillis = calendar.getTimeInMillis();
            }
        };

        DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyy = year;
                mm = month + 1;
                dd = dayOfMonth;
                edToDate.setText(dd + "-" + mm + "-" + yyyy);
                tvToDate.setText(yyyy + "-" + mm + "-" + dd);
                // CustomSharedPreference.putString(getActivity(), CustomSharedPreference.KEY_SP_TO_DATE, yyyy + "-" + mm + "-" + dd);

                Calendar calendar = Calendar.getInstance();
                calendar.set(yyyy, mm - 1, dd);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                toDateMillis = calendar.getTimeInMillis();
            }
        };
    }
}
