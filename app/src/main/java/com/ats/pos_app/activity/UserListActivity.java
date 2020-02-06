package com.ats.pos_app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ats.pos_app.R;
import com.ats.pos_app.adapter.FrEmployeeAdapter;
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

public class UserListActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    FrLogin frLogin;
    UserLogin userLogin;
    ArrayList<Employee> employeesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setTitle("Franchaisee Employee");

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);

        String frStr = CustomSharedPreference.getString(UserListActivity.this, CustomSharedPreference.FR_LOGIN);
        Gson gson = new Gson();
        frLogin = gson.fromJson(frStr, FrLogin.class);
        Log.e("HOME_ACTIVITY : ", "------------------------------FR LOGIN----------------------------------------------" + frLogin);

        String userStr = CustomSharedPreference.getString(UserListActivity.this, CustomSharedPreference.USER_LOGIN);
        userLogin = gson.fromJson(userStr, UserLogin.class);
        Log.e("HOME_ACTIVITY : ", "------------------------------FR LOGIN----------------------------------------------" + userLogin);

        if (frLogin == null) {
            startActivity(new Intent(UserListActivity.this, FrLoginActivity.class));
            finish();
        }else if(userLogin!=null)
        {
            startActivity(new Intent(UserListActivity.this, MainActivity.class));
            finish();
        }

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(UserListActivity.this,2);
        recyclerView.setLayoutManager(mGridLayoutManager);

        getEmployee(frLogin.getFranchisee().getFrId());

    }

    private void getEmployee(int frId) {
        Log.e("PARAMETER","             FR ID      "+frId);
        if (Constants.isOnline(UserListActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(getApplicationContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Employee>> listCall = Constants.myInterface.getAllFrEmpByFrid(frId);
            listCall.enqueue(new Callback<ArrayList<Employee>>() {
                @Override
                public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMPLOYEE LIST : ", " - " + response.body());
                            employeesList.clear();
                            employeesList = response.body();

                            FrEmployeeAdapter adapter = new FrEmployeeAdapter(employeesList, UserListActivity.this);
                            recyclerView.setAdapter(adapter);

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
            Toast.makeText(UserListActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item1 = menu.findItem(R.id.action_logout);
        item1.setVisible(true);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserListActivity.this, R.style.AlertDialogTheme);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure ! you want to logout?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    CustomSharedPreference.deletePreferenceByKey(UserListActivity.this,CustomSharedPreference.FR_LOGIN);
                    Intent intent = new Intent(UserListActivity.this, FrLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
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
        }

        return super.onOptionsItemSelected(item);
    }


}
