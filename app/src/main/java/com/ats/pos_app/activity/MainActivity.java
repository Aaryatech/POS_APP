package com.ats.pos_app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.pos_app.R;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.fragment.AddExpenseFragment;
import com.ats.pos_app.fragment.DashboardFragment;
import com.ats.pos_app.fragment.EditProfileFragment;
import com.ats.pos_app.fragment.ExpenseListFragment;
import com.ats.pos_app.fragment.PettyCashDetailFragment;
import com.ats.pos_app.fragment.PettyCashFragment;
import com.ats.pos_app.model.FrLogin;
import com.ats.pos_app.model.UserLogin;
import com.ats.pos_app.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;
    FrLogin frLogin;
    UserLogin userLogin;
    private CircleImageView ivNavHeadPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        String frStr = CustomSharedPreference.getString(this, CustomSharedPreference.FR_LOGIN);
        Gson gson = new Gson();
        frLogin = gson.fromJson(frStr, FrLogin.class);
        Log.e("HOME_ACTIVITY : ", "--------------------FR LOGIN--------------------" + frLogin);

        String userStr = CustomSharedPreference.getString(this, CustomSharedPreference.USER_LOGIN);
        userLogin = gson.fromJson(userStr, UserLogin.class);
        Log.e("HOME_ACTIVITY : ", "-------------------USER LOGIN--------------------" + userLogin);

        View header = navigationView.getHeaderView(0);

        TextView tvNavHeadName = header.findViewById(R.id.tvNavHeadName);
        TextView tvNavHeadDesg = header.findViewById(R.id.tvNavHeadDesg);
        ivNavHeadPhoto = header.findViewById(R.id.ivNavHeadPhoto);

        if (userLogin != null) {
            tvNavHeadName.setText("" + userLogin.getFrEmp().getFrEmpName());
            tvNavHeadDesg.setText("" + userLogin.getFranchisee().getFrName());

            try {
                Picasso.with(MainActivity.this).load(Constants.IMAGE_URL + "" + userLogin.getFranchisee().getFrImage()).placeholder(getResources().getDrawable(R.drawable.ic_person)).into(ivNavHeadPhoto);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment exit = getSupportFragmentManager().findFragmentByTag("Exit");
        Fragment dashboardFragment = getSupportFragmentManager().findFragmentByTag("DashFragment");
        Fragment pettyCashFragment = getSupportFragmentManager().findFragmentByTag("PettyCashFragment");
        Fragment expenseListFragment = getSupportFragmentManager().findFragmentByTag("ExpenseListFragment");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (exit instanceof DashboardFragment && exit.isVisible()) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(MainActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else if (dashboardFragment instanceof PettyCashFragment && dashboardFragment.isVisible() ||
                    dashboardFragment instanceof AddExpenseFragment && dashboardFragment.isVisible() ||
                    dashboardFragment instanceof EditProfileFragment && dashboardFragment.isVisible() ||
                    dashboardFragment instanceof ExpenseListFragment && dashboardFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
            ft.commit();

        } else if (pettyCashFragment instanceof PettyCashDetailFragment && pettyCashFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new PettyCashFragment(), "DashFragment");
            ft.commit();

        }else if (expenseListFragment instanceof AddExpenseFragment && expenseListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new ExpenseListFragment(), "DashFragment");
            ft.commit();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
//            ft.commit();

            Fragment adf1 = new DashboardFragment();
            Bundle args1 = new Bundle();
            args1.putString("model","Today");
            adf1.setArguments(args1);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf1, "Exit").commit();

        } else if (id == R.id.nav_pettyCash) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new PettyCashFragment(), "DashFragment");
            ft.commit();
        }else if(id==R.id.nav_expenseList)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new ExpenseListFragment(), "DashFragment");
            ft.commit();
        }else if(id==R.id.nav_addExpense)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new AddExpenseFragment(), "DashFragment");
            ft.commit();
        }else if(id==R.id.nav_editProfile)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new EditProfileFragment(), "DashFragment");
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
