package com.ats.pos_app.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.pos_app.R;
import com.ats.pos_app.activity.UserListActivity;
import com.ats.pos_app.adapter.ItemAdapter;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.model.DashboardCount;
import com.ats.pos_app.model.FrGraph;
import com.ats.pos_app.model.FrLogin;
import com.ats.pos_app.model.PieGraph;
import com.ats.pos_app.model.PieItem;
import com.ats.pos_app.model.UserLogin;
import com.ats.pos_app.mpchart.XYMarkerViewFr;
import com.ats.pos_app.utils.CommonDialog;
import com.ats.pos_app.utils.CustomSharedPreference;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {

    public BarChart horizontalBarChart;
    public PieChart horizontalPieChart;
    ArrayList<FrGraph> graphList = new ArrayList<>();
    ArrayList<DashboardCount> dashboardCntList = new ArrayList<>();
    ArrayList<PieGraph> pieGraphList = new ArrayList<>();
    ArrayList<PieItem> pieItemList = new ArrayList<>();
    ArrayList<String> pieNameList = new ArrayList<>();
    ArrayList<Float> pieIdList = new ArrayList<>();
    ArrayList<Integer> pieCatIdList = new ArrayList<>();
    private String[] xData;
    private Float[] yData;
    private Integer[] zData;

    public RecyclerView recyclerView;

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;

    FrLogin frLogin;
    UserLogin userLogin;


    public RadioButton rbToday,rbWeek,rbMonth,rbCustom;
    public RadioGroup rgType;
    public LinearLayout linearLayoutDate;
    public EditText edFromDate,edToDate;
    public TextView tvFromDate,tvToDate;
    public TextView tvSales,tvSalesCnt,tvDiscount,tvDiscountCnt,tvBill,tvBillCnt,tvCreditBill,tvCreditBillCnt,tvExpenses,tvExpensesCnt,tvEpayCnt,tvCashCnt,tvCardCnt;
    public Button btnSubmit;
    public static String selectedText = null;
    String fromDate,toDate;

   // private float[] yData = {25.4f, 23.7f, 11.33f,11.33f,11.33f};
   // private String[] xData = {"Present", "Absents", "Leaves","Leaves","Leaves"};
    public static String TAG = "MainActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        getActivity().setTitle("Dashboard");

        tvSales=(TextView)view.findViewById(R.id.tvSales);
        tvSalesCnt=(TextView)view.findViewById(R.id.tvSalesCnt);
        tvDiscount=(TextView)view.findViewById(R.id.tvDiscount);
        tvDiscountCnt=(TextView)view.findViewById(R.id.tvDiscountCnt);
        tvBill=(TextView)view.findViewById(R.id.tvBill);
        tvBillCnt=(TextView)view.findViewById(R.id.tvBillCnt);
        tvCreditBill=(TextView)view.findViewById(R.id.tvCreditBill);
        tvCreditBillCnt=(TextView)view.findViewById(R.id.tvCreditBillCnt);
        tvExpenses=(TextView)view.findViewById(R.id.tvExpenses);
        tvExpensesCnt=(TextView)view.findViewById(R.id.tvExpensesCnt);
        tvEpayCnt=(TextView)view.findViewById(R.id.tvEpayCnt);
        tvCashCnt=(TextView)view.findViewById(R.id.tvCashCnt);
        tvCardCnt=(TextView)view.findViewById(R.id.tvCardCnt);

        horizontalBarChart=(BarChart)view.findViewById(R.id.horizontalBarChart);
        horizontalPieChart=(PieChart) view.findViewById(R.id.horizontalPieChart);

        rbToday=(RadioButton) view.findViewById(R.id.rbToday);
        rbWeek=(RadioButton) view.findViewById(R.id.rbWeek);
        rbMonth=(RadioButton) view.findViewById(R.id.rbMonth);
        rbCustom=(RadioButton) view.findViewById(R.id.rbCustom);

        btnSubmit=(Button) view.findViewById(R.id.btnSubmit);

        linearLayoutDate=(LinearLayout) view.findViewById(R.id.linearLayoutDate);
        edFromDate=(EditText) view.findViewById(R.id.edFromDate);
        edToDate=(EditText) view.findViewById(R.id.edToDate);

        tvFromDate=(TextView) view.findViewById(R.id.tvFromDate);
        tvToDate=(TextView) view.findViewById(R.id.tvToDate);

        rgType=(RadioGroup) view.findViewById(R.id.rgType);

        String frStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.FR_LOGIN);
        Gson gson = new Gson();
        frLogin = gson.fromJson(frStr, FrLogin.class);
        Log.e("HOME_ACTIVITY : ", "--------------------FR LOGIN--------------------" + frLogin);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.USER_LOGIN);
        userLogin = gson.fromJson(userStr, UserLogin.class);
        Log.e("HOME_ACTIVITY : ", "-------------------USER LOGIN--------------------" + userLogin);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        final SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            selectedText = getArguments().getString("model");
            Log.e("Selected Text", "----------INTENT----------------" + selectedText);
        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("Exception","------------------------------"+e);
        }

        Log.e("Selected Text","----------------out------------------------"+selectedText);

        if(selectedText == null || selectedText.isEmpty()) {
            linearLayoutDate.setVisibility(View.GONE);

            rbToday.setChecked(true);
            selectedText="Today";

            Log.e("Selected Text","--------------in--------------------------"+selectedText);

            Date todayDate = Calendar.getInstance().getTime();
            String currentDate = formatter.format(todayDate);
            edFromDate.setText(""+currentDate);
            edToDate.setText(""+currentDate);
            tvToDate.setText(""+currentDate);
            tvToDate.setText(""+currentDate);

        }else {
            if (selectedText.equalsIgnoreCase("Today")) {
                linearLayoutDate.setVisibility(View.GONE);
                rbToday.setChecked(true);

                Date todayDate = Calendar.getInstance().getTime();
                String currentDate = formatter.format(todayDate);
                edFromDate.setText("" + currentDate);
                edToDate.setText("" + currentDate);
                tvFromDate.setText(""+currentDate);
                tvToDate.setText(""+currentDate);

            } else if (selectedText.equalsIgnoreCase("Week")) {
                linearLayoutDate.setVisibility(View.GONE);
                rbWeek.setChecked(true);

                Calendar calendar4 = Calendar.getInstance();
                while (calendar4.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                    calendar4.add(Calendar.DATE, -1);
                }
                Date d = calendar4.getTime();
                String firstWeek = formatter.format(d);

                Calendar calendar2 = Calendar.getInstance();
                while (calendar2.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    calendar2.add(Calendar.DATE, 1);
                }
                Date d2 = calendar2.getTime();
                String lastWeek = formatter.format(d2);

                Log.e("First Date","----------------------------DATE WEEK--------------------------------"+firstWeek);
                Log.e("Last Date","------------------------------DATE WEEK------------------------------"+lastWeek);

                edFromDate.setText("" + firstWeek);
                tvFromDate.setText("" + firstWeek);

                edToDate.setText("" + lastWeek);
                tvToDate.setText("" + lastWeek);

            }
            if (selectedText.equalsIgnoreCase("Month")) {

                linearLayoutDate.setVisibility(View.GONE);
                rbMonth.setChecked(true);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.MONTH, 0);
                calendar1.set(Calendar.DATE, calendar1.getActualMinimum(Calendar.DAY_OF_MONTH));
                Date nextMonthFirstDay = calendar1.getTime();
                calendar1.set(Calendar.DATE, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date nextMonthLastDay = calendar1.getTime();

                Log.e("First Date","------------------------------------------------------------"+nextMonthFirstDay);
                Log.e("Last Date","------------------------------------------------------------"+nextMonthLastDay);

                String firstMonth=formatter.format(nextMonthFirstDay);
                String lastMonth=formatter.format(nextMonthLastDay);

                Log.e("First Date","----------------------------DATE--------------------------------"+firstMonth);
                Log.e("Last Date","------------------------------DATE------------------------------"+lastMonth);

                edFromDate.setText("" + firstMonth);
                tvFromDate.setText("" + firstMonth);

                edToDate.setText("" + lastMonth);
                tvToDate.setText("" + lastMonth);

            }
            if (selectedText.equalsIgnoreCase("Custom")) {
                linearLayoutDate.setVisibility(View.VISIBLE);
                edFromDate.setText("");
                edToDate.setText("");

                Date todayDate = Calendar.getInstance().getTime();
                String currentDate = formatter.format(todayDate);
                tvToDate.setText("" + currentDate);
                tvToDate.setText("" + currentDate);

            }
        }

        String strFromdate=edFromDate.getText().toString();
        Log.e(" Date","------------------------------------------"+strFromdate);
        Date myDate = null;
        try {
            myDate = formatter.parse(strFromdate);
            Log.e(" Date","---------------DATE---------------------------"+myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         fromDate = formatter1.format(myDate);

        String strTodate=edToDate.getText().toString();
        Log.e(" Date","------------------------------------------"+strTodate);
        Date myToDate = null;
        try {
            myToDate = formatter.parse(strTodate);
            Log.e(" Date","---------------DATE---------------------------"+myToDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        toDate = formatter1.format(myToDate);

        getGraph(userLogin.getFranchisee().getFrId(),fromDate,toDate);
        getPieGraph(userLogin.getFranchisee().getFrId(),fromDate,toDate);
        getDashboardCount(userLogin.getFranchisee().getFrId(),userLogin.getFranchisee().getFrRateCat(),fromDate,toDate);

       // horizontalPieChart.setDescription(""+);

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int idx = group.indexOfChild(radioButton);
                RadioButton r = (RadioButton) group.getChildAt(idx);
                selectedText = r.getText().toString();
                Log.e(" Radio", "----------" + idx);
                Log.e(" Radio Text", "----------" + selectedText);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

                if(selectedText.equalsIgnoreCase("Today"))
                {
                    linearLayoutDate.setVisibility(View.GONE);

                    Date todayDate = Calendar.getInstance().getTime();
                    String currentDate = formatter.format(todayDate);
                    edFromDate.setText(""+currentDate);
                    edToDate.setText(""+currentDate);
                    tvFromDate.setText(""+currentDate);
                    tvToDate.setText(""+currentDate);

                }else if(selectedText.equalsIgnoreCase("Week"))
                {
                    linearLayoutDate.setVisibility(View.GONE);

                    Calendar calendar4 = Calendar.getInstance();
                    while (calendar4.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                        calendar4.add(Calendar.DATE, -1);
                    }
                    Date d = calendar4.getTime();
                    String firstWeek = formatter.format(d);

                    Calendar calendar2 = Calendar.getInstance();
                    while (calendar2.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        calendar2.add(Calendar.DATE, 1);
                    }
                    Date d2 = calendar2.getTime();
                    String lastWeek = formatter.format(d2);

                    Log.e("First Date","----------------------------DATE WEEK--------------------------------"+firstWeek);
                    Log.e("Last Date","------------------------------DATE WEEK------------------------------"+lastWeek);

                    edFromDate.setText("" + firstWeek);
                    tvFromDate.setText("" + firstWeek);

                    edToDate.setText("" + lastWeek);
                    tvToDate.setText("" + lastWeek);

                }if(selectedText.equalsIgnoreCase("Month"))
                {
                    linearLayoutDate.setVisibility(View.GONE);

                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.add(Calendar.MONTH, 0);
                    calendar1.set(Calendar.DATE, calendar1.getActualMinimum(Calendar.DAY_OF_MONTH));
                    Date nextMonthFirstDay = calendar1.getTime();
                    calendar1.set(Calendar.DATE, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
                    Date nextMonthLastDay = calendar1.getTime();

                    Log.e("First Date","------------------------------------------------------------"+nextMonthFirstDay);
                    Log.e("Last Date","------------------------------------------------------------"+nextMonthLastDay);

                    String firstMonth=formatter.format(nextMonthFirstDay);
                    String lastMonth=formatter.format(nextMonthLastDay);

                    Log.e("First Date","----------------------------DATE--------------------------------"+firstMonth);
                    Log.e("Last Date","------------------------------DATE------------------------------"+lastMonth);

                    edFromDate.setText("" + firstMonth);
                    tvFromDate.setText("" + firstMonth);

                    edToDate.setText("" + lastMonth);
                    tvToDate.setText("" + lastMonth);

                }if(selectedText.equalsIgnoreCase("Custom"))
                {
                    linearLayoutDate.setVisibility(View.VISIBLE);
                    edFromDate.setText("");
                    edToDate.setText("");

                    Date todayDate = Calendar.getInstance().getTime();
                    String currentDate = formatter.format(todayDate);
                    tvFromDate.setText(""+currentDate);
                    tvToDate.setText(""+currentDate);

                }
            }
        });


        horizontalPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                Log.e("ValueLisner","------------------------------------"+e.toString());
                Log.e("ValueLisner","------------------------------------"+h.toString());

                int pos1=e.toString().indexOf("y: ");
                String sales =e.toString().substring(pos1+2);
               // String sales =e.toString();
                Log.e("Sale","----------------------------------------"+sales);
                for(int i=0;i<yData.length;i++)
                {
                    Log.e("Iiiiiiiii","----------------------------------------"+yData[i]);
                    Log.e("Sale","-----------------For-----------------------"+sales);
                    if(yData[i] == Float.parseFloat(sales))
                    {
                        pos1=i;
                        break;
                    }
                }
                String item=xData[pos1];
                int catId=zData[pos1];
               // Toast.makeText(getActivity(), " Item "+item+"\n"+" Sales "+sales, Toast.LENGTH_SHORT).show();


                String strFromdate=edFromDate.getText().toString();
                Log.e(" Date","------------------------------------------"+strFromdate);
                Date myDate = null;
                try {
                    myDate = formatter.parse(strFromdate);
                    Log.e(" Date","---------------DATE---------------------------"+myDate);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
               String fromDate = formatter1.format(myDate);

                String strTodate=edToDate.getText().toString();
                Log.e(" Date","------------------------------------------"+strTodate);
                Date myToDate = null;
                try {
                    myToDate = formatter.parse(strTodate);
                    Log.e(" Date","---------------DATE---------------------------"+myToDate);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                String toDate = formatter1.format(myToDate);
                getItem(fromDate,toDate,userLogin.getFranchisee().getFrId(),catId,3,item,sales);


            }

            @Override
            public void onNothingSelected() {

            }
        });

        horizontalBarChart.setOnChartValueSelectedListener(this);
        edFromDate.setOnClickListener(this);
        edToDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        return view;
    }

    private void getDashboardCount(int frId, int frRateCat, String fromDate, String toDate) {

        Log.e("PARAMETER DASH COUNT","            FR ID       "+ frId        +"            FR RATE CAT       "+ frRateCat        +"            FROM DATE       "+ fromDate        +"          TO DATE     " +   toDate );

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<DashboardCount> listCall = Constants.myInterface.getPosDashCounts(frId,frRateCat,fromDate,toDate);
            listCall.enqueue(new Callback<DashboardCount>() {
                @Override
                public void onResponse(Call<DashboardCount> call, Response<DashboardCount> response) {

                    Log.e("DASHBOARD COUNT : ", " ------------------------------- " + response.body());

                    try {
                        if (response.body() != null) {
                            dashboardCntList.clear();
                           // dashboardCntList=response.body();
                            DashboardCount dashboardCount=response.body();

                            Log.e("Selected Text","----------------------------------------"+selectedText);
                            Log.e("DASHBOARD","----------------------------------------"+dashboardCount);

                           NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));


                            /* Create NumberFormats using Locales */
                          // NumberFormat currencyInstance     = NumberFormat.getCurrencyInstance(Locale.US);
                          // NumberFormat currencyInstance  = NumberFormat.getCurrencyInstance(indiaLocale);
                           // NumberFormat china  = NumberFormat.getCurrencyInstance(Locale.CHINA);
                            //NumberFormat france = NumberFormat.getCurrencyInstance(Locale.FRANCE);

                            if(selectedText.equalsIgnoreCase("Today")) {
                                tvSales.setText("TODAY'S SALES");
                                tvSalesCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getSaleAmt()));
                                tvDiscount.setText("TODAY'S DISCOUNT");
                                tvDiscountCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getDiscountAmt()));
                                tvBill.setText("TODAY'S NO. OF BILL");
                                tvBillCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getNoOfBillGenerated()));
                                tvCreditBill.setText("TODAY'S CREDIT BILL AMT");
                                tvCreditBillCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCreditAmt()));
                                tvExpenses.setText("TODAY'S EXPENSES");
                                tvExpensesCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getExpenseAmt()));
                                tvEpayCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getEpayAmt()));
                                tvCashCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCashAmt()));
                                tvCardCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCardAmt()));
                            }else if(selectedText.equalsIgnoreCase("Week"))
                            {
                                tvSales.setText("WEEEK SALES");
                                tvSalesCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getSaleAmt()));
                                tvDiscount.setText("WEEEK DISCOUNT");
                                tvDiscountCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getDiscountAmt()));
                                tvBill.setText("WEEEK NO. OF BILL");
                                tvBillCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getNoOfBillGenerated()));
                                tvCreditBill.setText("WEEEK CREDIT BILL AMT");
                                tvCreditBillCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCreditAmt()));
                                tvExpenses.setText("WEEEK EXPENSES");
                                tvExpensesCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getExpenseAmt()));
                                tvEpayCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getEpayAmt()));
                                tvCashCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCashAmt()));
                                tvCardCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCardAmt()));

                            }else if(selectedText.equalsIgnoreCase("Month"))
                            {
                                tvSales.setText("MONTH SALES");
                                tvSalesCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getSaleAmt()));
                                tvDiscount.setText("MONTH DISCOUNT");
                                tvDiscountCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getDiscountAmt()));
                                tvBill.setText("MONTH NO. OF BILL");
                                tvBillCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getNoOfBillGenerated()));
                                tvCreditBill.setText("MONTH CREDIT BILL AMT");
                                tvCreditBillCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCreditAmt()));
                                tvExpenses.setText("MONTH EXPENSES");
                                tvExpensesCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getExpenseAmt()));
                                tvEpayCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getEpayAmt()));
                                tvCashCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCashAmt()));
                                tvCardCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCardAmt()));

                            }else if(selectedText.equalsIgnoreCase("Custom"))
                            {
                                tvSales.setText("SALES");
                                tvSalesCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getSaleAmt()));
                                tvDiscount.setText("DISCOUNT");
                                tvDiscountCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getDiscountAmt()));
                                tvBill.setText("NO. OF BILL");
                                tvBillCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getNoOfBillGenerated()));
                                tvCreditBill.setText("CREDIT BILL AMT");
                                tvCreditBillCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCreditAmt()));
                                tvExpenses.setText("EXPENSES");
                                tvExpensesCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getExpenseAmt()));
                                tvEpayCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getEpayAmt()));
                                tvCashCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCashAmt()));
                                tvCardCnt.setText("Rs. "+currencyInstance.format(dashboardCount.getCardAmt()));

                            }

//                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
//                            ft.commit();

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
                public void onFailure(Call<DashboardCount> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getItem(String fromDate, String toDate, int frId, final int catId, int flag, final String item, final String sales) {

        Log.e("PARAMETER","            FR ID       "+ frId        +"            CAT ID       "+ catId        +"            FLAG       "+ flag        +"            FROM DATE       "+ fromDate        +"          TO DATE     " +   toDate );

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<PieItem>> listCall = Constants.myInterface.getCatwiseItemSell(fromDate,toDate,frId,catId,flag);
            listCall.enqueue(new Callback<ArrayList<PieItem>>() {
                @Override
                public void onResponse(Call<ArrayList<PieItem>> call, Response<ArrayList<PieItem>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PIE GRAPH ITEM  LIST : ", " ------------------------------- " + response.body());
                            pieItemList.clear();
                            pieItemList=response.body();

                            new PieGraphDialog(getContext(),item,sales,catId,pieItemList).show();


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
                public void onFailure(Call<ArrayList<PieItem>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getItemDialog(String fromDate, String toDate, int frId, int catId, int flag, final String item, final String sales) {

        Log.e("PARAMETER","            FR ID       "+ frId        +"            FROM DATE       "+ fromDate        +"          TO DATE     " +   toDate );

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<PieItem>> listCall = Constants.myInterface.getCatwiseItemSell(fromDate,toDate,frId,catId,flag);
            listCall.enqueue(new Callback<ArrayList<PieItem>>() {
                @Override
                public void onResponse(Call<ArrayList<PieItem>> call, Response<ArrayList<PieItem>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PIE GRAPH ITEM  LIST : ", " ------------------------------- " + response.body());
                            pieItemList.clear();
                            pieItemList=response.body();

                            ItemAdapter mAdapter = new ItemAdapter(pieItemList, getActivity());
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
                public void onFailure(Call<ArrayList<PieItem>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPieGraph(int frId, String fromDate, String toDate) {

        Log.e("PARAMETER PIE","            FR ID       "+ frId        +"            FROM DATE       "+ fromDate        +"          TO DATE     " +   toDate );

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<PieGraph>> listCall = Constants.myInterface.getCatwiseSell(frId,fromDate,toDate);
            listCall.enqueue(new Callback<ArrayList<PieGraph>>() {
                @Override
                public void onResponse(Call<ArrayList<PieGraph>> call, Response<ArrayList<PieGraph>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PIE GRAPH  LIST : ", " - " + response.body());
                            pieGraphList.clear();
                            pieIdList.clear();
                            pieNameList.clear();
                            pieCatIdList.clear();
                            pieGraphList=response.body();

                            for(int i=0;i<pieGraphList.size();i++)
                            {
                                pieIdList.add(Float.valueOf(pieGraphList.get(i).getCatTotal()));
                                pieNameList.add(pieGraphList.get(i).getCatName());
                                pieCatIdList.add(pieGraphList.get(i).getCatId());
                            }

                            for (int i = 0; i < pieNameList.size(); i++) {
                                xData= pieNameList.toArray(new String[i]);
                                Log.e("DEFAULT ARRAY", "---------------------------------" + xData);
                            }

                            for (int i = 0; i < pieIdList.size(); i++) {
                                yData= pieIdList.toArray(new Float[i]);
                                Log.e("DEFAULT ARRAY", "---------------------------------" + yData);
                            }

                            for (int i = 0; i < pieCatIdList.size(); i++) {
                                zData= pieCatIdList.toArray(new Integer[i]);
                                Log.e("DEFAULT ARRAY", "---------------------------------" + zData);
                            }

                            Log.e("DEFAULT X DATA", "---------------------------------" + xData);
                            Log.e("DEFAULT Y DATA", "---------------------------------" + yData);
                            Log.e("DEFAULT Z DATA", "---------------------------------" + zData);
             
                            horizontalPieChart.setRotationEnabled(true);
                            horizontalPieChart.setHoleRadius(25);
                            horizontalPieChart.setTransparentCircleAlpha(0);
                            horizontalPieChart.setCenterText("Item Categori");
                            horizontalPieChart.setCenterTextSize(10);
                            horizontalPieChart.setDrawEntryLabels(true);

                            addDataSet();



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
                public void onFailure(Call<ArrayList<PieGraph>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private class PieGraphDialog extends Dialog {

        public TextView tvLable,tvAll,tvTop,tvBottom;

        public ImageView ivClose;
        String item,Sales;
        int catId;
        ArrayList<PieItem> pieItemList = new ArrayList<>();


        public PieGraphDialog(@NonNull Context context,String item,String Sales,int catId,ArrayList<PieItem> pieItemList) {
            super(context);
            this.item = item;
            this.Sales = Sales;
            this.catId = catId;
            this.pieItemList = pieItemList;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.pie_graph_layout);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            tvLable=(TextView)findViewById(R.id.tvLable);
            tvAll=(TextView)findViewById(R.id.tvAll);
            tvTop=(TextView)findViewById(R.id.tvTop);
            tvBottom=(TextView)findViewById(R.id.tvBottom);

            ivClose=(ImageView) findViewById(R.id.ivClose);

            recyclerView=(RecyclerView) findViewById(R.id.recyclerView);

            tvLable.setText(""+item+"("+Sales+")");

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            final SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

            final String strFromdate=edFromDate.getText().toString();
            Log.e(" Date","------------------------------------------"+strFromdate);
            Date myDate = null;
            try {
                myDate = formatter.parse(strFromdate);
                Log.e(" Date","---------------DATE---------------------------"+myDate);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            final String fromDate = formatter1.format(myDate);

            String strTodate=edToDate.getText().toString();
            Log.e(" Date","------------------------------------------"+strTodate);
            Date myToDate = null;
            try {
                myToDate = formatter.parse(strTodate);
                Log.e(" Date","---------------DATE---------------------------"+myToDate);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            final String toDate = formatter1.format(myToDate);

            tvAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getItemDialog(fromDate,toDate,userLogin.getFranchisee().getFrId(),catId,3,item,Sales);
                }
            });

            tvTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getItemDialog(fromDate,toDate,userLogin.getFranchisee().getFrId(),catId,1,item,Sales);
                }
            });

            tvBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getItemDialog(fromDate,toDate,userLogin.getFranchisee().getFrId(),catId,2,item,Sales);
                }
            });

            ItemAdapter mAdapter = new ItemAdapter(pieItemList, getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        }
    }

        private void addDataSet() {

        ArrayList<PieEntry> yEntry =new ArrayList<>();
        ArrayList<String> xEntry =new ArrayList<>();

        for(int i=0;i<yData.length;i++)
        {
            yEntry.add(new PieEntry(yData[i],i));
        }

        for(int i=1;i<xData.length;i++)
        {
            xEntry.add(xData[i]);
        }

        Log.e("ENTRY","---------------------------------"+yEntry);

        PieDataSet pieDataSet = new PieDataSet(yEntry,"Item");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        Legend legend=horizontalPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        PieData pieData = new PieData(pieDataSet);
        horizontalPieChart.setData(pieData);
        horizontalPieChart.invalidate();



    }


    private void getGraph(int frId, final String fromDate, final String toDate) {
        Log.e("PARAMETER BAR","            FR ID       "+ frId        +"            FROM DATE       "+ fromDate        +"          TO DATE     " +   toDate );

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<FrGraph>> listCall = Constants.myInterface.getDatewiseSell(frId,fromDate,toDate);
            listCall.enqueue(new Callback<ArrayList<FrGraph>>() {
                @Override
                public void onResponse(Call<ArrayList<FrGraph>> call, Response<ArrayList<FrGraph>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("GRAPH BAR  LIST : ", " - " + response.body());
                            graphList.clear();
                            graphList=response.body();

                            ArrayList<BarEntry> barEntry=new ArrayList<>();
                            final ArrayList<String> lable=new ArrayList<>();


                            for(int i=0;i<graphList.size();i++)
                            {
                                barEntry.add(new BarEntry(i, Float.parseFloat(graphList.get(i).getSellAmount())));
                                // lable.add(frDashboards.get(i).getFrName());
                                lable.add(graphList.get(i).getSellDate().substring(0, Math.min(graphList.get(i).getSellDate().length(), 10)));

                            }

                            BarDataSet barDataSet = new BarDataSet(barEntry,"Amount");
                            barDataSet.setColor(getResources().getColor(R.color.colorPrimaryDark));

                            YAxis rightYAxis = horizontalBarChart.getAxisRight();
                            rightYAxis.setEnabled(false);

                            String[] ds = new String[lable.size()];
                            ds = lable.toArray(ds);

                            Log.e("GraphAct WEEK", " ---------------------------Lable GraphDetail-------------------- " + lable);
                            Log.e("GraphAct WEEK", " ---------------------------Lable GraphDetail size-------------------- " + lable.size());
                            Log.e("GraphAct WEEK", " ---------------------------Ds GraphDetail-------------------- " + ds);

                            XAxis xval = horizontalBarChart.getXAxis();
                            xval.setDrawLabels(true);

                            // xval.setLabelCount(ds.length, false);

                            final String[] finalDs = ds;
                            xval.setValueFormatter(new ValueFormatter() {
                                @Override
                                public String getAxisLabel(float value, AxisBase axis) {
                                    Log.e("VALUE", "--------------------------------------VALUE----------------------" + value);
                                    // return finalDs[Math.round(value)];
                                    return lable.get((int) (value));

                                }
                            });

                            ValueFormatter xAxisFormatter = new ValueFormatter() {
                                @Override
                                public String getAxisLabel(float value, AxisBase axis) {
                                    Log.e("VALUE", "--------------------------------------VALUE----------------------" + value);
                                    // return finalDs[Math.round(value)];
                                    return lable.get((int) (value));

                                } };


                            XYMarkerViewFr mv = new XYMarkerViewFr(getActivity(), xAxisFormatter);
                            mv.frGraph=graphList;
                            mv.type=3;
                            mv.setChartView(horizontalBarChart); // For bounds control
                            horizontalBarChart.setMarker(mv);

                            BarData barData =new BarData(barDataSet);
                            barData.setBarWidth(0.7f);
                            horizontalBarChart.setVisibility(View.VISIBLE);
                            horizontalBarChart.animateY(3000);
                            horizontalBarChart.setData(barData);
                            horizontalBarChart.setFitBars(true);

                            Description description=new Description();
                            description.setText("Fr Name");
                            horizontalBarChart.setDescription(description);
                            horizontalBarChart.invalidate();


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
                public void onFailure(Call<ArrayList<FrGraph>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
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
        }else if(v.getId()==R.id.btnSubmit)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

            String strFromdate=edFromDate.getText().toString();
            Log.e(" Date","------------------------------------------"+strFromdate);
            Date myDate = null;
            try {
                myDate = formatter.parse(strFromdate);
                Log.e(" Date","---------------DATE---------------------------"+myDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String fromDate = formatter1.format(myDate);

            String strTodate=edToDate.getText().toString();
            Log.e(" Date","------------------------------------------"+strTodate);
            Date myToDate = null;
            try {
                myToDate = formatter.parse(strTodate);
                Log.e(" Date","---------------DATE---------------------------"+myToDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String toDate = formatter1.format(myToDate);

            getGraph(userLogin.getFranchisee().getFrId(),fromDate,toDate);
            getPieGraph(userLogin.getFranchisee().getFrId(),fromDate,toDate);
            getDashboardCount(userLogin.getFranchisee().getFrId(),userLogin.getFranchisee().getFrRateCat(),fromDate,toDate);
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


    private final RectF onValueSelectedRectF = new RectF();
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        RectF bounds = onValueSelectedRectF;
        horizontalBarChart.getBarBounds((BarEntry) e, bounds);

//        MPPointF position = horizontalBarChart.getPosition(e, YAxis.AxisDependency.LEFT);
//
//        Log.i("bounds", bounds.toString());
//        Log.i("position", position.toString());
//
//        Log.i("x-index",
//                "low: " + horizontalBarChart.getLowestVisibleX() + ", high: "
//                        + horizontalBarChart.getHighestVisibleX());


        MPPointF position = horizontalBarChart.getPosition(e, horizontalBarChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);

        Log.e("ENTRY","------------------ "+e.getData());
        Log.e("X","------------------ "+e.toString());

    }

    @Override
    public void onNothingSelected() {

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.action_logout);
        item.setVisible(true);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure ! you want to logout?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CustomSharedPreference.deletePreferenceByKey(getActivity(),CustomSharedPreference.USER_LOGIN);
                        Intent intent = new Intent(getActivity(), UserListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
