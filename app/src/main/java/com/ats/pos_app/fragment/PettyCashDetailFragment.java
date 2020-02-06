package com.ats.pos_app.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.pos_app.BuildConfig;
import com.ats.pos_app.R;
import com.ats.pos_app.adapter.PettyCashDetailAdapter;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.model.PettyCashDetail;
import com.ats.pos_app.model.UserLogin;
import com.ats.pos_app.utils.CommonDialog;
import com.ats.pos_app.utils.CustomSharedPreference;
import com.ats.pos_app.utils.PermissionsUtil;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
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
public class PettyCashDetailFragment extends Fragment implements View.OnClickListener {

    public TextView tvPettyCash,tvFromDate,tvToDate;
    public EditText edFromDate,edToDate;
    public Button btnSubmit,btnPdf;
    public RecyclerView recyclerView;

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;

    UserLogin userLogin;

    ArrayList<PettyCashDetail> pettyCashDetailsList = new ArrayList<>();


    //------PDF------
    private PdfPCell cell;
    private String path;
    private File dir;
    private File file;
    private TextInputLayout inputLayoutBillTo, inputLayoutEmailTo;
    double totalAmount = 0;
    int day, month, year;
    long dateInMillis;
    long amtLong;
    private Image bgImage;
    BaseColor myColor = WebColors.getRGBColor("#ffffff");
    BaseColor myColor1 = WebColors.getRGBColor("#cbccce");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_petty_cash_detail, container, false);
        getActivity().setTitle("Petty Cash Management");

        tvPettyCash=(TextView)view.findViewById(R.id.tvPettyCash);

        tvFromDate=(TextView)view.findViewById(R.id.tvFromDate);
        tvToDate=(TextView)view.findViewById(R.id.tvToDate);

        edFromDate=(EditText)view.findViewById(R.id.edFromDate);
        edToDate=(EditText)view.findViewById(R.id.edToDate);

        btnSubmit=(Button)view.findViewById(R.id.btnSubmit);
        btnPdf=(Button)view.findViewById(R.id.btnPdf);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(todayDate);

        fromDateMillis = todayDate.getTime();
        toDateMillis = todayDate.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date nextMonthFirstDay = calendar.getTime();
        String firstDate = formatter.format(nextMonthFirstDay);

        Log.e("Date First","---------------String----------------------------"+firstDate);

        edFromDate.setText(firstDate);
        edToDate.setText(currentDate);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.USER_LOGIN);
        Gson gson = new Gson();
        userLogin = gson.fromJson(userStr, UserLogin.class);
        Log.e("HOME_ACTIVITY : ", "-------------------USER LOGIN--------------------" + userLogin);

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

        getDetail(userLogin.getFranchisee().getFrId(),fromDate,toDate);

        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        dir = new File(Environment.getExternalStorageDirectory() + File.separator, "PosApp" + File.separator + "PettyCash");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        tvPettyCash.setOnClickListener(this);
        edFromDate.setOnClickListener(this);
        edToDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnPdf.setOnClickListener(this);

        return view;
    }

    private void getDetail(Integer frId, String fromDate, String toDate) {
        Log.e("PARAMETERS : ", "         FR ID     : " + frId+"            FROM DATE :     "+fromDate+"             TO DAET  :        "+toDate);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<PettyCashDetail>> listCall = Constants.myInterface.getPettyCashListDateWise(frId,fromDate,toDate);
            listCall.enqueue(new Callback<ArrayList<PettyCashDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<PettyCashDetail>> call, Response<ArrayList<PettyCashDetail>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Petty Cash : ", "----------------------------Detail List-------------------------" + response.body());

                            pettyCashDetailsList.clear();
                            pettyCashDetailsList = response.body();


                            PettyCashDetailAdapter mAdapter = new PettyCashDetailAdapter(pettyCashDetailsList, getActivity());
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
                public void onFailure(Call<ArrayList<PettyCashDetail>> call, Throwable t) {
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
        }else if(v.getId()==R.id.tvPettyCash)
        {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new PettyCashFragment(), "DashFragment");
            ft.commit();
        }else if(v.getId()==R.id.btnSubmit)
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

            getDetail(userLogin.getFranchisee().getFrId(),fromDate,toDate);

        }else if(v.getId()==R.id.btnPdf)
        {
            createPettyCashPDF(pettyCashDetailsList);
        }
    }

    private void createPettyCashPDF(ArrayList<PettyCashDetail> pettyCashDetailsList) {
        final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
        commonDialog.show();

        Document doc = new Document();

        doc.setMargins(-16, -17, 10, 40);
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
        Font boldFont1WithUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD | Font.UNDERLINE);
        Font boldTextFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD | Font.UNDERLINE);
        Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        Font textFontWithUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL | Font.UNDERLINE);
        try {

            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            dateInMillis = calendar.getTimeInMillis();


            String fileName = pettyCashDetailsList.get(0).getPettycashId() + "_PettyCash.pdf";


            file = new File(dir, fileName);
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(doc, fOut);

            Log.d("File Name-------------", "" + file.getName());
            //open the document
            doc.open();


            try {

                //create table

                PdfPTable pdf = new PdfPTable(1);
                pdf.setWidthPercentage(100);

                cell = new PdfPCell(new Paragraph("Petty Cash Detail", boldTextFont));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(1);
                pdf.addCell(cell);

                cell = new PdfPCell(new Paragraph(" ", textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(1);
                pdf.addCell(cell);

                cell = new PdfPCell(new Paragraph(" ", textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(1);
                pdf.addCell(cell);

                cell = new PdfPCell(new Paragraph("", boldTextFont));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setHorizontalAlignment(1);
                pdf.addCell(cell);

                cell = new PdfPCell(new Paragraph(" ", textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(1);
                pdf.addCell(cell);

                cell = new PdfPCell(new Paragraph(" ", textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(1);
                pdf.addCell(cell);


                //---------------------------------------------------------------


                PdfPTable ptDate = new PdfPTable(4);
                float[] colWidth = new float[]{17, 50, 50, 15};
                ptDate.setWidths(colWidth);
                ptDate.setTotalWidth(colWidth);
                ptDate.setWidthPercentage(100);

                cell = new PdfPCell(new Paragraph("From Date: ", textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(0);
                //cell.setColspan(1);
                ptDate.addCell(cell);

                String fromDate=edFromDate.getText().toString();
                String toDate= edToDate.getText().toString();

                cell = new PdfPCell(new Paragraph("" +fromDate,boldFont1));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(0);
                cell.setColspan(1);
                ptDate.addCell(cell);

                cell = new PdfPCell(new Paragraph("To Date: ", textFont));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(2);
                cell.setColspan(1);
                ptDate.addCell(cell);

                cell = new PdfPCell(new Paragraph("" +toDate, boldFont1));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(2);
                cell.setColspan(1);
                ptDate.addCell(cell);

                //------------------------------------------------------------------

                PdfPTable ptItemHead = new PdfPTable(7);
                float[] colItemHeadWidth = new float[]{10, 30, 15, 15, 20, 15, 25};
                ptItemHead.setWidths(colItemHeadWidth);
                ptItemHead.setTotalWidth(colItemHeadWidth);
                ptItemHead.setWidthPercentage(100);

                cell = new PdfPCell(new Paragraph("Sr No", textFont));
                cell.setHorizontalAlignment(1);
                ptItemHead.addCell(cell);

                cell = new PdfPCell(new Paragraph("Date", textFont));
                cell.setHorizontalAlignment(1);
                ptItemHead.addCell(cell);

                cell = new PdfPCell(new Paragraph("Opening Amt", textFont));
                cell.setHorizontalAlignment(1);
                ptItemHead.addCell(cell);

                cell = new PdfPCell(new Paragraph("Cash Amt", textFont));
                cell.setHorizontalAlignment(1);
                ptItemHead.addCell(cell);

                cell = new PdfPCell(new Paragraph("Withdrawal Amt", textFont));
                cell.setHorizontalAlignment(1);
                ptItemHead.addCell(cell);

                cell = new PdfPCell(new Paragraph("ClosingAmt", textFont));
                cell.setHorizontalAlignment(1);
                ptItemHead.addCell(cell);

                cell = new PdfPCell(new Paragraph("Calculated Cash Amt", textFont));
                cell.setHorizontalAlignment(1);
                ptItemHead.addCell(cell);

                //-------------------------------------------------------------------------------

             /*   PdfPTable ptItem = new PdfPTable(7);
                float[] colItemWidth = new float[]{10, 30, 15, 15, 20, 15, 25};
                ptItem.setWidths(colItemWidth);
                ptItem.setTotalWidth(colItemWidth);
                ptItem.setWidthPercentage(100);*/

                if (pettyCashDetailsList.size() > 0) {

                    for (int i = 0; i < pettyCashDetailsList.size(); i++) {

                        cell = new PdfPCell(new Paragraph("" + (i + 1), textFont));
                        // cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(0);
                        ptItemHead.addCell(cell);

                        long millisecond = Long.parseLong(pettyCashDetailsList.get(i).getDate());
                        // or you already have long value of date, use this instead of milliseconds variable.
                        String dateString = DateFormat.format("dd-MM-yyyy", new Date(millisecond)).toString();

                        cell = new PdfPCell(new Paragraph("" + dateString, textFont));
                        // cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(0);
                        ptItemHead.addCell(cell);

                        cell = new PdfPCell(new Paragraph("" +pettyCashDetailsList.get(i).getOpeningAmt(), textFont));
                        // cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(0);
                        ptItemHead.addCell(cell);

                        cell = new PdfPCell(new Paragraph("" + pettyCashDetailsList.get(i).getCashAmt(), textFont));
                        // cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(0);
                        ptItemHead.addCell(cell);

                        cell = new PdfPCell(new Paragraph("" + pettyCashDetailsList.get(i).getWithdrawalAmt(), textFont));
                        // cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(0);
                        ptItemHead.addCell(cell);

                        cell = new PdfPCell(new Paragraph("" + pettyCashDetailsList.get(i).getClosingAmt(), textFont));
                        //  cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(0);
                        ptItemHead.addCell(cell);

                        cell = new PdfPCell(new Paragraph("" + pettyCashDetailsList.get(i).getCashEditAmt(), textFont));
                        // cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(0);
                        ptItemHead.addCell(cell);

                    }
                }

                //---------------------------------------------------------------

                PdfPTable pTable = new PdfPTable(1);
                pTable.setWidthPercentage(100);


                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setColspan(1);
                cell.addElement(pdf);
                pTable.addCell(cell);


                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setColspan(1);
                cell.addElement(ptDate);
                pTable.addCell(cell);


                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setColspan(1);
                cell.addElement(ptItemHead);
                pTable.addCell(cell);



                PdfPTable table = new PdfPTable(2);
                float[] columnWidth = new float[]{10, 50};
                table.setWidths(columnWidth);
                table.setTotalWidth(columnWidth);

                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor);
                cell.setColspan(2);
                cell.addElement(pTable);

                table.addCell(cell);//image cell&address

                doc.add(table);

            } finally {
                doc.close();
                commonDialog.dismiss();

                File file1 = new File(dir, fileName);
                Log.e("File","file1-------------------"+file1);

//                if (emailQuote == 0 && quotStatus == 1) {

                if (file1.exists()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        intent.setDataAndType(Uri.fromFile(file1), "application/pdf");
                    } else {
                        if (file1.exists()) {
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri uri = FileProvider.getUriForFile(getActivity(), authorities, file1);
                            intent.setDataAndType(uri, "application/pdf");
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        }
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } else {
                    commonDialog.dismiss();
                }


            }
        } catch (Exception e) {
            commonDialog.dismiss();
            e.printStackTrace();
            // Log.e("Mytag","-------------------Error--------------------"+ e.getStackTrace());
            Toast.makeText(getActivity(), "Unable To Generate PDF", Toast.LENGTH_SHORT).show();
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
