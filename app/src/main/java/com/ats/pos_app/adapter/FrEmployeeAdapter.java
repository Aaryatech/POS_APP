package com.ats.pos_app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.pos_app.R;
import com.ats.pos_app.activity.MainActivity;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.model.Employee;
import com.ats.pos_app.model.FrLogin;
import com.ats.pos_app.model.UserLogin;
import com.ats.pos_app.utils.CommonDialog;
import com.ats.pos_app.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrEmployeeAdapter extends RecyclerView.Adapter<FrEmployeeAdapter.MyViewHolder> {

    private ArrayList<Employee> employeesList;
    private Context context;

    public FrEmployeeAdapter(ArrayList<Employee> employeesList, Context context) {
        this.employeesList = employeesList;
        this.context = context;
    }

    @NonNull
    @Override
    public FrEmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.franchaisee_employeee_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FrEmployeeAdapter.MyViewHolder myViewHolder, int i) {
        final Employee model=employeesList.get(i);
        myViewHolder.tvEmpName.setText(""+model.getFrEmpName());

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EmployeeLoginDialog(context,model).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmpName;
        public LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmpName=(itemView).findViewById(R.id.tvEmpName);
            linearLayout=(itemView).findViewById(R.id.linearLayout);
        }
    }

    private class EmployeeLoginDialog extends Dialog {

        Employee employee;
        public ImageView ivClose;
        public EditText edPassword;
        public Button btnSubmit;
        public TextView tvEmpName;
        Context context;
        FrLogin frLogin;

        public EmployeeLoginDialog(@NonNull Context context,Employee employee) {
            super(context);
            this.employee = employee;
            this.context = context;
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.emp_dialog_layout);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            ivClose=(ImageView)findViewById(R.id.ivClose);
            edPassword=(EditText) findViewById(R.id.edPassword);
            tvEmpName=(TextView) findViewById(R.id.tvEmpName);
            btnSubmit=(Button)findViewById(R.id.btnSubmit);

            tvEmpName.setText(""+employee.getFrEmpName());

            String userStr = CustomSharedPreference.getString(context, CustomSharedPreference.FR_LOGIN);
            Gson gson = new Gson();
            frLogin = gson.fromJson(userStr, FrLogin.class);
            Log.e("HOME_ACTIVITY : ", "------------------------------FR LOGIN----------------------------------------------" + frLogin);


            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strPass;
                    boolean isValidPass = false;

                    strPass = edPassword.getText().toString().trim();

                    if (strPass.isEmpty()) {
                        edPassword.setError("required");
                    } else {
                        edPassword.setError(null);
                        isValidPass = true;
                    }

                    if(isValidPass)
                    {
                        doLogin(frLogin.getFranchisee().getFrId(),employee.getFrEmpId(),strPass);
                    }

                }
            });

        }

    }

    private void doLogin(Integer frId, Integer frEmpId, final String strPass) {
        Log.e("PARAMETERS : ", "       FR ID : " + frId + "      FR EMP ID : " + frEmpId);
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<UserLogin> listCall = Constants.myInterface.frEmpById(frEmpId,frId);
            listCall.enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("User Data : ", "------------" + response.body());

                            UserLogin data = response.body();
                            if (data.getLoginInfo().getError()) {
                                commonDialog.dismiss();
                                //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                                builder.setTitle(context.getResources().getString(R.string.app_name));
                                builder.setMessage("Oops something went wrong! please check password.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            } else {

                                if(data.getFrEmp().getPassword().equalsIgnoreCase(strPass)) {
                                    commonDialog.dismiss();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(data);
                                    CustomSharedPreference.putString(context, CustomSharedPreference.USER_LOGIN, json);

                                    context.startActivity(new Intent(context, MainActivity.class));
                                }else{
                                    commonDialog.dismiss();
                                    //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                                    builder.setTitle(context.getResources().getString(R.string.app_name));
                                    builder.setMessage("Oops something went wrong! please check password.");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }

                            }
                        } else {
                            commonDialog.dismiss();
                            //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle(context.getResources().getString(R.string.app_name));
                            builder.setMessage("Oops something went wrong! please check password.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                        //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                        builder.setTitle(context.getResources().getString(R.string.app_name));
                        builder.setMessage("Oops something went wrong! please check password.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                public void onFailure(Call<UserLogin> call, Throwable t) {
                    commonDialog.dismiss();
                    // Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                    builder.setTitle(context.getResources().getString(R.string.app_name));
                    builder.setMessage("Oops something went wrong! please check password.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
