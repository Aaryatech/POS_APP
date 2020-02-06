package com.ats.pos_app.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.pos_app.R;
import com.ats.pos_app.adapter.PettyCashHandOverTransactionAdapter;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.model.Employee;
import com.ats.pos_app.model.PettyCashDetail;
import com.ats.pos_app.model.PettyCashHandOverTransaction;
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
public class PettyCashFragment extends Fragment implements View.OnClickListener{

    public TextView tv_PettyCashDetail,tv_CashHandOverData;
    public EditText edDate,edOpeningAmt,edTodayesAmt,edWithdrawalAmt,edClosingAmt,edOpenAmt,edSellingAmt,edTotalHandCash,edActualHandCash;
    public Button btnDayEnd,btnCashHandOver,btnSubmit,btnClose;
    public Spinner spToEmp;
    public LinearLayout llCashHandOver;
    UserLogin userLogin;

    public RecyclerView recyclerView;
    PettyCashDetail pettyCashDetails;

    ArrayList<PettyCashHandOverTransaction> pettyCashHandOverTransactionsList =new ArrayList<>();
    ArrayList<Employee> employeesList = new ArrayList<>();
    ArrayList<String> employeesNameList = new ArrayList<>();
    ArrayList<Integer> employeesIdList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_petty_cash, container, false);
        getActivity().setTitle("Petty Cash Management");

        tv_PettyCashDetail=(TextView)view.findViewById(R.id.tv_PettyCashDetail);
        tv_CashHandOverData=(TextView)view.findViewById(R.id.tv_CashHandOverData);

        edDate=(EditText)view.findViewById(R.id.edDate);
        edOpeningAmt=(EditText)view.findViewById(R.id.edOpeningAmt);
        edTodayesAmt=(EditText)view.findViewById(R.id.edTodayesAmt);
        edWithdrawalAmt=(EditText)view.findViewById(R.id.edWithdrawalAmt);
        edClosingAmt=(EditText)view.findViewById(R.id.edClosingAmt);
        edOpenAmt=(EditText)view.findViewById(R.id.edOpenAmt);
        edSellingAmt=(EditText)view.findViewById(R.id.edSellingAmt);
        edTotalHandCash=(EditText)view.findViewById(R.id.edTotalHandCash);
        edActualHandCash=(EditText)view.findViewById(R.id.edActualHandCash);

        btnDayEnd=(Button)view.findViewById(R.id.btnDayEnd);
        btnCashHandOver=(Button)view.findViewById(R.id.btnCashHandOver);
        btnSubmit=(Button)view.findViewById(R.id.btnSubmit);
        btnClose=(Button)view.findViewById(R.id.btnClose);

        llCashHandOver=(LinearLayout) view.findViewById(R.id.llCashHandOver);

        spToEmp=(Spinner)view.findViewById(R.id.spToEmp);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.USER_LOGIN);
        Gson gson = new Gson();
        userLogin = gson.fromJson(userStr, UserLogin.class);
        Log.e("HOME_ACTIVITY : ", "-------------------USER LOGIN--------------------" + userLogin);

        getPettyCash(userLogin.getFranchisee().getFrId());
        getEmployee(userLogin.getFranchisee().getFrId());

        edTodayesAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int todayesAmt = Integer.parseInt(s.toString());
                pettyCashDetails.setTotalAmt(todayesAmt);

                int opening = pettyCashDetails.getClosingAmt();
                int todayes = pettyCashDetails.getTotalAmt();
                int withdraw = pettyCashDetails.getWithdrawalAmt();

                int Total = ((opening+todayes)-withdraw);

                Log.e("Opening","--------------------------------"+opening);
                Log.e("Todayes","--------------------------------"+todayes);
                Log.e("Withdraw","--------------------------------"+withdraw);
                Log.e("TodayesAmt","--------------------------------"+todayesAmt);
                Log.e("Total","--------------------------------"+Total);

                edClosingAmt.setText("" + Total);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        edWithdrawalAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int withdrawAmt=0;
                try{
                     withdrawAmt = Integer.parseInt(s.toString());
                }catch(Exception e)
                {
                    e.printStackTrace();
                }

                pettyCashDetails.setWithdrawalAmt(withdrawAmt);

                int opening = pettyCashDetails.getClosingAmt();
                int todayes = pettyCashDetails.getTotalAmt();
                int withdraw = pettyCashDetails.getWithdrawalAmt();

                int Total = ((opening+todayes)-withdraw);

                Log.e("Opening","--------------------------------"+opening);
                Log.e("Todayes","--------------------------------"+todayes);
                Log.e("Withdraw","--------------------------------"+withdraw);
                Log.e("TodayesAmt","--------------------------------"+withdrawAmt);
                Log.e("Total","--------------------------------"+Total);

                edClosingAmt.setText("" + Total);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        tv_PettyCashDetail.setOnClickListener(this);
        tv_CashHandOverData.setOnClickListener(this);
        btnDayEnd.setOnClickListener(this);
        btnCashHandOver.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        return view;
    }

    private void getEmployee(int frId) {
        Log.e("PARAMETER","             FR ID      "+frId);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();


            Call<ArrayList<Employee>> listCall = Constants.myInterface.getAllFrEmpByFrid(frId);
            listCall.enqueue(new Callback<ArrayList<Employee>>() {
                @Override
                public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMPLOYEE LIST : ", " - " + response.body());
                            employeesList.clear();
                            employeesIdList.clear();
                            employeesNameList.clear();
                            //employeesList = response.body();

                            employeesIdList.add(0);
                            employeesNameList.add("Select Employee");

                            for(int i=0;i<response.body().size();i++)
                            {
                                if(userLogin.getFrEmp().getFrEmpId()!=response.body().get(i).getFrEmpId())
                                {
                                    employeesList.add(response.body().get(i));
                                    employeesIdList.add(response.body().get(i).getFrEmpId());
                                    employeesNameList.add(response.body().get(i).getFrEmpName());
                                }
                            }
                            ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, employeesNameList);
                            spToEmp.setAdapter(sourceAdapter);

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
                public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getPettyCash(Integer frId) {
        Log.e("PARAMETERS : ", "         FR ID     : " + frId);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PettyCashDetail> listCall = Constants.myInterface.getPettyCashAmtForApp(frId);
            listCall.enqueue(new Callback<PettyCashDetail>() {
                @Override
                public void onResponse(Call<PettyCashDetail> call, Response<PettyCashDetail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Petty Cash : ", "----------------------------Detail List-------------------------" + response.body());

                            pettyCashDetails = response.body();

                            edOpeningAmt.setText(""+pettyCashDetails.getClosingAmt());
                            edTodayesAmt.setText(""+pettyCashDetails.getTotalAmt());
                            edWithdrawalAmt.setText(""+pettyCashDetails.getWithdrawalAmt());
                            int closing=((pettyCashDetails.getClosingAmt()+pettyCashDetails.getTotalAmt())-pettyCashDetails.getWithdrawalAmt());
                            edClosingAmt.setText(""+closing);

                            edOpenAmt.setText(""+pettyCashDetails.getClosingAmt());
                            edSellingAmt.setText(""+pettyCashDetails.getTotalAmt());
                            int handOver=(pettyCashDetails.getClosingAmt()+pettyCashDetails.getTotalAmt());
                            edTotalHandCash.setText(""+handOver);
                            edActualHandCash.setText(""+handOver);

                            long millisecond = Long.parseLong(pettyCashDetails.getDate());
                            String dateString = DateFormat.format("dd-MM-yyyy", new Date(millisecond)).toString();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            Date myDate = null;
                            try {
                                myDate = sdf.parse(dateString);
                                // Log.e(" Date","---------------DATE---------------------------"+myDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Calendar c = Calendar.getInstance();
                            c.setTime(myDate); // Now use today date.
                            c.add(Calendar.DATE, 1); // Adding 1 day
                            String date = sdf.format(c.getTime());


                            Log.e("Date","---------------------------------Date +1---------------------------"+date);
                            Log.e("Date","-----------------------------------Date---------------------------"+dateString);

                            edDate.setText(""+date);

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
                public void onFailure(Call<PettyCashDetail> call, Throwable t) {
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
        if(v.getId()==R.id.btnCashHandOver)
        {
            llCashHandOver.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.tv_PettyCashDetail)
        {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new PettyCashDetailFragment(), "PettyCashFragment");
            ft.commit();
        }else if(v.getId()==R.id.tv_CashHandOverData)
        {

            new CashHandDataDialog(getContext()).show();
        }else if(v.getId()==R.id.btnDayEnd)
        {
            String strDate = edDate.getText().toString().trim();
            String strOpeningAmt=edOpeningAmt.getText().toString().trim();
            String strTodayesAmt=edTodayesAmt.getText().toString().trim();
            String strWithdrawAmt=edWithdrawalAmt.getText().toString().trim();
            String strClosingAmt=edWithdrawalAmt.getText().toString().trim();

            int openingAmt=0,cashAmt=0,withdrawAmt=0,closingAmt=0;
            try{
                openingAmt= Integer.parseInt(strOpeningAmt);
                cashAmt= Integer.parseInt(strTodayesAmt);
                withdrawAmt= Integer.parseInt(strWithdrawAmt);
                closingAmt= Integer.parseInt(strClosingAmt);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

            Date myToDate = null;
            try {
                myToDate = formatter.parse(strDate);
                Log.e(" Date","---------------DATE---------------------------"+myToDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String date = formatter1.format(myToDate);

            PettyCashDetail pettyCashDetail=new PettyCashDetail(0,userLogin.getFranchisee().getFrId(),date,openingAmt,cashAmt,0,0,0,withdrawAmt,closingAmt,0,0,pettyCashDetails.getCashEditAmt(),0,0,0,0,"","");
            saveDayEnd(pettyCashDetail);
        }else if(v.getId()==R.id.btnSubmit)
        {
            int empId = employeesIdList.get(spToEmp.getSelectedItemPosition());
            String empName = employeesNameList.get(spToEmp.getSelectedItemPosition());

            String strActualAmt = edActualHandCash.getText().toString().trim();
            String strTotalAmt=edTotalHandCash.getText().toString().trim();
            String strOpeningAmt=edOpenAmt.getText().toString().trim();
            String strSellingAmt=edSellingAmt.getText().toString().trim();

            int actualAmt=0,totalAmt=0,opening=0,selling=0;

            try{
                actualAmt= Integer.parseInt(strActualAmt);
                totalAmt= Integer.parseInt(strTotalAmt);
                opening= Integer.parseInt(strOpeningAmt);
                selling= Integer.parseInt(strSellingAmt);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            PettyCashHandOverTransaction pettyCashHandOverTransaction =new PettyCashHandOverTransaction(0,userLogin.getFranchisee().getFrId(),userLogin.getFrEmp().getFrEmpId(),userLogin.getFrEmp().getFrEmpName(),empId,empName,actualAmt,totalAmt,opening,selling,sdf.format(System.currentTimeMillis()),sdf.format(System.currentTimeMillis()),0,sdf1.format(System.currentTimeMillis()),0,0,0,strSellingAmt,"","");

            getPreviousRecord(userLogin.getFranchisee().getFrId(),sdf.format(System.currentTimeMillis()),pettyCashHandOverTransaction);

//            saveHandOverCash(pettyCashHandOverTransaction);
        }else if(v.getId()==R.id.btnClose)
        {
            llCashHandOver.setVisibility(View.GONE);
        }

    }

    private void getPreviousRecord(int frId, String date, final PettyCashHandOverTransaction pettyCashHandOverTransaction) {
        Log.e("PARAMETERS : ", "         FR ID     : " + frId);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PettyCashHandOverTransaction> listCall = Constants.myInterface.getLastCashHandover(frId,date);
            listCall.enqueue(new Callback<PettyCashHandOverTransaction>() {
                @Override
                public void onResponse(Call<PettyCashHandOverTransaction> call, Response<PettyCashHandOverTransaction> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PREVIOUES RECORD : ", "----------------------------PREVIOUES RECORD-------------------------" + response.body());
                            Log.e("RESPONCE","---------------------------not-null----------------------");

                            String selling = String.valueOf(pettyCashHandOverTransaction.getSellingAmt()- response.body().getSellingAmt());
                            pettyCashHandOverTransaction.setExVar1(selling);
                            saveHandOverCash(pettyCashHandOverTransaction);

                            commonDialog.dismiss();
                        } else {
                            commonDialog.dismiss();
                            Log.e("RESPONCE","----------------------------null----------------------");
                            saveHandOverCash(pettyCashHandOverTransaction);
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<PettyCashHandOverTransaction> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveHandOverCash(PettyCashHandOverTransaction pettyCashHandOverTransaction) {
        Log.e("PARAMETER","                        PETTY CASH HAND OVER         "+pettyCashHandOverTransaction);
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PettyCashHandOverTransaction> listCall = Constants.myInterface.savePettyCashHandOver(pettyCashHandOverTransaction);
            listCall.enqueue(new Callback<PettyCashHandOverTransaction>() {
                @Override
                public void onResponse(Call<PettyCashHandOverTransaction> call, Response<PettyCashHandOverTransaction> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE PETTY CASH : ", " ------------------------------SAVE PETTY CASH HAND OVER------------------------- " + response.body());

                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new PettyCashFragment(), "Exit");
                            ft.commit();
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<PettyCashHandOverTransaction> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDayEnd(PettyCashDetail pettyCashDetail) {

        Log.e("PARAMETER","                        PETTY CASH         "+pettyCashDetail);
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
             commonDialog.show();

            Call<PettyCashDetail> listCall = Constants.myInterface.addPettyCash(pettyCashDetail);
            listCall.enqueue(new Callback<PettyCashDetail>() {
                @Override
                public void onResponse(Call<PettyCashDetail> call, Response<PettyCashDetail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE DAY END : ", " ------------------------------SAVE DAY END------------------------- " + response.body());

                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new PettyCashFragment(), "Exit");
                            ft.commit();
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<PettyCashDetail> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private class CashHandDataDialog extends Dialog implements View.OnClickListener{

        public Button btnSearch;
        public EditText edFromDate,edToDate;
        public TextView tvFromDate,tvToDate;
        public ImageView ivClose;
        long fromDateMillis, toDateMillis;
        int yyyy, mm, dd;


        public CashHandDataDialog(@NonNull Context context) {
            super(context);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_cash_hand_over_data_layout);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            btnSearch=(Button)findViewById(R.id.btnSearch);

            recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

            edFromDate=(EditText)findViewById(R.id.edFromDate);
            edToDate=(EditText)findViewById(R.id.edToDate);

            tvFromDate=(TextView)findViewById(R.id.tvFromDate);
            tvToDate=(TextView)findViewById(R.id.tvToDate);

            ivClose = (ImageView) findViewById(R.id.ivClose);

            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = formatter.format(todayDate);

            fromDateMillis = todayDate.getTime();
            toDateMillis = todayDate.getTime();

            edFromDate.setText(currentDate);
            edToDate.setText(currentDate);


            String strFromDate=edFromDate.getText().toString().trim();
            String strToDate=edToDate.getText().toString().trim();

            Date myFromDate = null;
            try {
                myFromDate = formatter.parse(strFromDate);
                Log.e(" Date","---------------DATE-FROM--------------------------"+myFromDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String fromDate = formatter1.format(myFromDate);

            Date myToDate = null;
            try {
                myToDate = formatter.parse(strToDate);
                Log.e(" Date","---------------DATE--TO-------------------------"+myToDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String toDate = formatter1.format(myToDate);

            getCashHandOver(userLogin.getFranchisee().getFrId(),fromDate,toDate);

            edFromDate.setOnClickListener(this);
            edToDate.setOnClickListener(this);
            ivClose.setOnClickListener(this);
            btnSearch.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.edFromDate)
            {
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
            }else if(v.getId()==R.id.edToDate)
            {
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
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), toDateListener, yr, mn, dy);
                dialog.show();
            }else if(v.getId()==R.id.ivClose)
            {
                dismiss();
            }else if(v.getId()==R.id.btnSearch)
            {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

                String strFromDate=edFromDate.getText().toString().trim();
                String strToDate=edToDate.getText().toString().trim();

                Date myFromDate = null;
                try {
                    myFromDate = formatter.parse(strFromDate);
                    Log.e(" Date","---------------DATE-FROM--------------------------"+myFromDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String fromDate = formatter1.format(myFromDate);

                Date myToDate = null;
                try {
                    myToDate = formatter.parse(strToDate);
                    Log.e(" Date","---------------DATE--TO-------------------------"+myToDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String toDate = formatter1.format(myToDate);

                getCashHandOver(userLogin.getFranchisee().getFrId(),fromDate,toDate);
            }
        }

        DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyy = year;
                mm = month + 1;
                dd = dayOfMonth;
                edFromDate.setText(dd + "-" + mm + "-" + yyyy);
                tvFromDate.setText(yyyy + "-" + mm + "-" + dd);

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
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

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                calendar.set(yyyy, mm - 1, dd);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                fromDateMillis = calendar.getTimeInMillis();


            }
        };
    }

    private void getCashHandOver(Integer frId, String fromDate, String toDate) {

        Log.e("PARAMETERS : ", "         FR ID     : " + frId+"            FROM DATE :     "+fromDate+"             TO DAET  :        "+toDate);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<PettyCashHandOverTransaction>> listCall = Constants.myInterface.getCashHandOverTransctn(frId,fromDate,toDate);
            listCall.enqueue(new Callback<ArrayList<PettyCashHandOverTransaction>>() {
                @Override
                public void onResponse(Call<ArrayList<PettyCashHandOverTransaction>> call, Response<ArrayList<PettyCashHandOverTransaction>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Petty Cash : ", "----------------------------Detail List-------------------------" + response.body());

                            pettyCashHandOverTransactionsList.clear();
                            pettyCashHandOverTransactionsList = response.body();

                            PettyCashHandOverTransactionAdapter mAdapter = new PettyCashHandOverTransactionAdapter(pettyCashHandOverTransactionsList, getActivity());
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
                public void onFailure(Call<ArrayList<PettyCashHandOverTransaction>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }
}
