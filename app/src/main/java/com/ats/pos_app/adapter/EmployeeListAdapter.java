package com.ats.pos_app.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import com.ats.pos_app.activity.MainActivity;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.fragment.EditProfileFragment;
import com.ats.pos_app.model.Employee;
import com.ats.pos_app.model.Info;
import com.ats.pos_app.utils.CommonDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder>  {
    private List<Employee> empList;
    //private EmployeeList[] empList;
    private Context context;


    public EmployeeListAdapter(List<Employee> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.employee_list_layout_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListAdapter.MyViewHolder myViewHolder, int i) {
        final Employee model=empList.get(i);

        myViewHolder.tvEmpName.setText(""+model.getFrEmpName());
        myViewHolder.tvContact.setText(""+model.getFrEmpContact());
        myViewHolder.tvAddess.setText(""+model.getFrEmpAddress());
        myViewHolder.tvJoiningDate.setText(""+model.getFrEmpJoiningDate());
        myViewHolder.tvCode.setText(""+model.getEmpCode());

        myViewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new addEmpDialog(context,model).show();
            }
        });

        myViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //empDelete(model.getFrEmpId());
            }
        });
    }

    private void empDelete(Integer frEmpId) {
        Log.e("PARAMETER","             EMP ID  :     "+frEmpId);
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.deleteExpense(frEmpId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("DELETE EMPLOYEE: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

//                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//                                ft.replace(R.id.content_frame, new ExpenseListFragment(), "DashFragment");
//                                ft.commit();

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
        return empList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmpName,tvContact,tvAddess,tvJoiningDate,tvCode;
        public ImageView ivEdit,ivDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEmpName=(itemView).findViewById(R.id.tvEmpName);
            tvContact=(itemView).findViewById(R.id.tvContact);
            tvAddess=(itemView).findViewById(R.id.tvAddess);
            tvJoiningDate=(itemView).findViewById(R.id.tvJoiningDate);
            tvCode=(itemView).findViewById(R.id.tvCode);
            ivEdit=(itemView).findViewById(R.id.ivEdit);
            ivDelete=(itemView).findViewById(R.id.ivDelete);
        }
    }

    private class addEmpDialog extends Dialog implements View.OnClickListener {

        public Button btnSave;
        public EditText edEmpName, edEmpMob,edJoiningDate,edAddress,edPassword,edEmployeeCode;
        public Spinner spType;
        public ImageView ivClose;
        long fromDateMillis, toDateMillis;
        int yyyy, mm, dd;
        Employee employee;

        ArrayList<String> desigArray = new ArrayList<>();
        final ArrayList<Integer> desigIdArray = new ArrayList<>();


        public addEmpDialog(@NonNull Context context, Employee employee) {
            super(context);
            this.employee = employee;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.add_employee_layout);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            btnSave=(Button)findViewById(R.id.btnSave);

            edEmpName=(EditText) findViewById(R.id.edEmpName);
            edEmpMob=(EditText)findViewById(R.id.edEmpMob);
            edJoiningDate=(EditText)findViewById(R.id.edJoiningDate);
            edAddress=(EditText)findViewById(R.id.edAddress);
            edPassword=(EditText)findViewById(R.id.edPassword);
            edEmployeeCode=(EditText)findViewById(R.id.edEmployeeCode);


            spType=(Spinner) findViewById(R.id.spType);

            ivClose=(ImageView) findViewById(R.id.ivClose);

            desigArray.clear();
            desigArray.add("Admin");
            desigArray.add("Manager");
            desigArray.add("cashier");

            desigIdArray.add(1);
            desigIdArray.add(2);
            desigIdArray.add(3);

            ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, desigArray);
            spType.setAdapter(typeAdapter);

            edEmpName.setText(""+employee.getFrEmpName());
            edEmpMob.setText(""+employee.getFrEmpContact());
            edJoiningDate.setText(""+employee.getFrEmpJoiningDate());
            edAddress.setText(""+employee.getFrEmpAddress());
            edPassword.setText(""+employee.getPassword());
            edEmployeeCode.setText(""+employee.getEmpCode());

            int pos=0;
            for(int i=0;i<desigIdArray.size();i++)
            {
                if(desigIdArray.get(i)== employee.getDesignation())
                {
                    pos=i;

                    break;
                }
            }
            spType.setSelection(pos);

            edJoiningDate.setOnClickListener(this);
            btnSave.setOnClickListener(this);
            ivClose.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.edJoiningDate)
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
                DatePickerDialog dialog = new DatePickerDialog(context, fromDateListener, yr, mn, dy);
                dialog.show();
            }else if(v.getId()==R.id.btnSave)
            {
                String strEmpName,strEmpMOB,strDate,strAddress,strPass,strEmpCode;
                boolean isValidEmpName = false, isValidDate = false, isValidMob = false, isValidAddress = false, isValidPass = false,isValidEmpCode = false;

                strEmpName = edEmpName.getText().toString();
                strEmpMOB = edEmpMob.getText().toString();
                strDate =  edJoiningDate.getText().toString();
                strAddress = edAddress.getText().toString();
                strPass = edPassword.getText().toString();
                strEmpCode = edEmployeeCode.getText().toString();

                int type = desigIdArray.get(spType.getSelectedItemPosition());
                String desgName = desigArray.get(spType.getSelectedItemPosition());

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

                Date myFromDate = null;
                try {
                    myFromDate = formatter.parse(strDate);
                    Log.e(" Date","---------------DATE-FROM--------------------------"+myFromDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String fromDate = formatter1.format(myFromDate);

                if (strEmpName.isEmpty()) {
                    edEmpName.setError("required");
                } else {
                    edEmpName.setError(null);
                    isValidEmpName = true;
                }

                if (strEmpMOB.isEmpty()) {
                    edEmpMob.setError("required");
                } else {
                    edEmpMob.setError(null);
                    isValidMob = true;
                }

                if (strDate.isEmpty()) {
                    edJoiningDate.setError("required");
                } else {
                    edJoiningDate.setError(null);
                    isValidDate = true;
                }

                if (strAddress.isEmpty()) {
                    edAddress.setError("required");
                } else {
                    edAddress.setError(null);
                    isValidAddress = true;
                }

                if (strPass.isEmpty()) {
                    edPassword.setError("required");
                } else {
                    edPassword.setError(null);
                    isValidPass = true;
                }

                if (strEmpCode.isEmpty()) {
                    edEmployeeCode.setError("required");
                } else {
                    edEmployeeCode.setError(null);
                    isValidEmpCode = true;
                }

                if(isValidEmpName && isValidDate && isValidMob && isValidAddress && isValidPass && isValidEmpCode)
                {
                    dismiss();
                    Employee employee1=new Employee(employee.getFrEmpId(),strEmpName,strEmpMOB,strAddress,fromDate,employee.getFrId(),employee.getDelStatus(),employee.getIsActive(),employee.getTotalLimit(),employee.getFromDate(),employee.getToDate(),employee.getCurrentBillAmt(),employee.getUpdateDatetime(),strPass,strEmpCode,type,employee.getExInt1(),employee.getExInt2(),employee.getExInt3(),employee.getExVar1(),employee.getExVar2(),employee.getExVar3());
                    saveEmployee(employee1);
                }

            }else if(v.getId()==R.id.ivClose)
            {
                dismiss();
            }
        }

        DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyy = year;
                mm = month + 1;
                dd = dayOfMonth;
                edJoiningDate.setText(dd + "-" + mm + "-" + yyyy);

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

    private void saveEmployee(Employee employee) {
        Log.e("PARAMETER","                 EMPLOYEE LIST              "+employee);
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Employee> listCall = Constants.myInterface.saveFrEmpDetails(employee);
            listCall.enqueue(new Callback<Employee>() {
                @Override
                public void onResponse(Call<Employee> call, Response<Employee> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE EMPLOYEE : ", " ------------------------------SAVE EMPLOYEE------------------------ " + response.body());
                            MainActivity activity = (MainActivity) context;
                            FragmentTransaction ft =activity.getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new EditProfileFragment(), "DashFragment");
                            ft.commit();
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("" + context.getResources().getString(R.string.app_name));
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
                    }
                }

                @Override
                public void onFailure(Call<Employee> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

}
