package com.ats.pos_app.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.pos_app.BuildConfig;
import com.ats.pos_app.R;
import com.ats.pos_app.adapter.EmployeeListAdapter;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.model.Employee;
import com.ats.pos_app.model.ExpenseList;
import com.ats.pos_app.model.Info;
import com.ats.pos_app.model.Route;
import com.ats.pos_app.model.Setting;
import com.ats.pos_app.model.Supplyment;
import com.ats.pos_app.model.UserLogin;
import com.ats.pos_app.utils.CommonDialog;
import com.ats.pos_app.utils.CustomSharedPreference;
import com.ats.pos_app.utils.PermissionsUtil;
import com.ats.pos_app.utils.RealPathUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener {

    public TextView tvAddEmp, tvEmpList, tvImageLable,tvPhoto1;
    public EditText edFranchiseeName, edEmailId, edMobileNo, edOwnerName, edCity, edRateType, edRoute, edDistance, edTaxType, edIsOwnStore, edPinCode, edShopOpeningDate, edOwnersBirthdate, edAgreementDate, edFDALicenseNo, edPestControlDate;
    public ImageView ivCamera1, ivPhoto1;
    public Button btnSubmit,btnAdmin,btnCashier,btnManager,btnSubmitPass;
    UserLogin userLogin;
    public LinearLayout llAdmin,llCashier;
    public TextView tvCashierLableConfi,tvCashierPassConfi,tvCashierPass,tvCashierLable,tvAdminPass,tvUpdatePass,tvCancel;
    String passType=null;

    ArrayList<Employee> employeesList=new ArrayList<>();

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "RUSA_DOCS");
    File f;
    Bitmap myBitmap = null;
    public static String path, imagePath;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        getActivity().setTitle("Edit Profile");

        tvAddEmp = (TextView) view.findViewById(R.id.tvAddEmp);
        tvEmpList = (TextView) view.findViewById(R.id.tvEmpList);
        tvImageLable = (TextView) view.findViewById(R.id.tvImageLable);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        btnAdmin = (Button) view.findViewById(R.id.btnAdmin);
        btnCashier = (Button) view.findViewById(R.id.btnCashier);
        btnManager = (Button) view.findViewById(R.id.btnManager);
        btnSubmitPass = (Button) view.findViewById(R.id.btnSubmitPass);

        llAdmin = (LinearLayout) view.findViewById(R.id.llAdmin);
        llCashier = (LinearLayout) view.findViewById(R.id.llCashier);

        tvUpdatePass=(TextView)view.findViewById(R.id.tvUpdatePass);
        tvCancel=(TextView)view.findViewById(R.id.tvCancel);


        edFranchiseeName = (EditText) view.findViewById(R.id.edFranchiseeName);
        edEmailId = (EditText) view.findViewById(R.id.edEmailId);
        edMobileNo = (EditText) view.findViewById(R.id.edMobileNo);
        edOwnerName = (EditText) view.findViewById(R.id.edOwnerName);
        edCity = (EditText) view.findViewById(R.id.edCity);
        edRateType = (EditText) view.findViewById(R.id.edRateType);
        edRoute = (EditText) view.findViewById(R.id.edRoute);
        edDistance = (EditText) view.findViewById(R.id.edDistance);
        edTaxType = (EditText) view.findViewById(R.id.edTaxType);
        edIsOwnStore = (EditText) view.findViewById(R.id.edIsOwnStore);
        edPinCode = (EditText) view.findViewById(R.id.edPinCode);
        edShopOpeningDate = (EditText) view.findViewById(R.id.edShopOpeningDate);
        edOwnersBirthdate = (EditText) view.findViewById(R.id.edOwnersBirthdate);
        edAgreementDate = (EditText) view.findViewById(R.id.edAgreementDate);
        edFDALicenseNo = (EditText) view.findViewById(R.id.edFDALicenseNo);
        edPestControlDate = (EditText) view.findViewById(R.id.edPestControlDate);

        ivCamera1=(ImageView)view.findViewById(R.id.ivCamera1);
        ivPhoto1=(ImageView)view.findViewById(R.id.ivPhoto1);
        tvPhoto1=(TextView)view.findViewById(R.id.tvPhoto1);

        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        createFolder();

        String frStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.FR_LOGIN);
        Gson gson = new Gson();
        userLogin = gson.fromJson(frStr, UserLogin.class);
        Log.e("HOME_ACTIVITY : ", "--------------------USER LOGIN--------------------" + userLogin);
        
        getRoute(userLogin.getFranchisee().getFrRouteId());
        getFrSupByFrId(userLogin.getFranchisee().getFrId());

        edFranchiseeName.setText(""+userLogin.getFranchisee().getFrName());
        edEmailId.setText(""+userLogin.getFranchisee().getFrEmail());
        edMobileNo.setText(""+userLogin.getFranchisee().getFrMob());
        edOwnerName.setText(""+userLogin.getFranchisee().getFrOwner());
        edCity.setText(""+userLogin.getFranchisee().getFrCity());
        edOwnersBirthdate.setText(""+userLogin.getFranchisee().getOwnerBirthDate());
        edAgreementDate.setText(""+userLogin.getFranchisee().getFrAgreementDate());
        edPinCode.setText(""+userLogin.getFranchisee().getFrKg2());
        edFDALicenseNo.setText(""+userLogin.getFranchisee().getFbaLicenseDate());
        edDistance.setText(""+userLogin.getFranchisee().getFrKg3());
        edShopOpeningDate.setText(""+userLogin.getFranchisee().getFrOpeningDate());

        if(userLogin.getFranchisee().getFrRateCat()==1)
        {
            edRateType.setText("Local Rate");
        }else if(userLogin.getFranchisee().getFrRateCat()==2)
        {
            edRateType.setText("Outsation Rate");
        }else if(userLogin.getFranchisee().getFrRateCat()==3)
        {
            edRateType.setText("Special Rate");
        }

        if(userLogin.getFranchisee().getFrGstType()==0)
        {
            edTaxType.setText("Not Register");
        }else if(userLogin.getFranchisee().getFrGstType()==200000)
        {
            edTaxType.setText("Composite");
        }
        else if(userLogin.getFranchisee().getFrGstType()==10000000)
        {
            edTaxType.setText("Regular");
        }else{
            edTaxType.setText("Composite");
        }

        if(userLogin.getFranchisee().getFrKg1()==1)
        {
            edIsOwnStore.setText("Yes");
        }else if(userLogin.getFranchisee().getFrKg1()==0)
        {
            edIsOwnStore.setText("No");
        }

        try {
            String imageUri = String.valueOf(userLogin.getFranchisee().getFrImage());
            Log.e("Image Path","---------------------"+Constants.IMAGE_URL+imageUri);
            Picasso.with(getActivity()).load(Constants.IMAGE_URL+imageUri).placeholder(getResources().getDrawable(R.drawable.ic_person)).into(ivPhoto1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        tvAddEmp.setOnClickListener(this);
        tvEmpList.setOnClickListener(this);
        ivCamera1.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        btnAdmin.setOnClickListener(this);
        btnCashier.setOnClickListener(this);
        btnManager.setOnClickListener(this);
        btnSubmitPass.setOnClickListener(this);

        tvUpdatePass.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        return view;
    }

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    private void getFrSupByFrId(Integer frId) {

        Log.e("PARAMETER","            FR ID  :      "+frId);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Supplyment> listCall = Constants.myInterface.getFrSupByFrId(frId);
            listCall.enqueue(new Callback<Supplyment>() {
                @Override
                public void onResponse(Call<Supplyment> call, Response<Supplyment> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SUPPLYMENT LIST: ", " - " + response.body());

                            Supplyment supplyment=response.body();

                            edPestControlDate.setText(""+supplyment.getPestControlDate());

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Supplyment> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getRoute(Integer frRouteId) {

        Log.e("PARAMETER","            ROUTE ID  :      "+frRouteId);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Route> listCall = Constants.myInterface.getRoute(frRouteId);
            listCall.enqueue(new Callback<Route>() {
                @Override
                public void onResponse(Call<Route> call, Response<Route> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("ROUTE LIST: ", " - " + response.body());

                            Route route=response.body();

                            edRoute.setText(""+route.getRouteName());

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Route> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvAddEmp) {
            getSettingValues(57);

        } else if (v.getId() == R.id.tvEmpList) {

            getEmployee(userLogin.getFranchisee().getFrId());
        }else if(v.getId()==R.id.btnSubmit)
        {
            saveEditProfile();
        }else if(v.getId()==R.id.ivCamera1)
        {
            showFileChooser();

        }
    }

    private void saveEditProfile() {
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();


            Call<Info> listCall = Constants.myInterface.updateFranchisee(userLogin.getFranchisee().getFrId(),userLogin.getFranchisee().getFrName(),userLogin.getFranchisee().getFrCode(),userLogin.getFranchisee().getFrOpeningDate(),userLogin.getFranchisee().getFrRate(),userLogin.getFranchisee().getFrImage(),userLogin.getFranchisee().getFrRouteId(),userLogin.getFranchisee().getFrCity(),userLogin.getFranchisee().getFrKg1(),userLogin.getFranchisee().getFrKg2(),userLogin.getFranchisee().getFrKg3(),userLogin.getFranchisee().getFrKg4(),userLogin.getFranchisee().getFrEmail(),userLogin.getFranchisee().getFrPassword(),userLogin.getFranchisee().getFrMob(),userLogin.getFranchisee().getFrOwner(),userLogin.getFranchisee().getFrRateCat(),userLogin.getFranchisee().getGrnTwo(),userLogin.getFranchisee().getDelStatus(),userLogin.getFranchisee().getOwnerBirthDate(),userLogin.getFranchisee().getFbaLicenseDate(),userLogin.getFranchisee().getFrAgreementDate(),userLogin.getFranchisee().getFrGstType(),userLogin.getFranchisee().getFrGstNo(),userLogin.getFranchisee().getStockType(),userLogin.getFranchisee().getFrAddress(),userLogin.getFranchisee().getFrTarget(),userLogin.getFranchisee().getIsSameState());
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE EDIT PROFILE: ", " - " + response.body());

                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new EditProfileFragment(), "DashFragment");
                            ft.commit();


                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
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
                            employeesList = response.body();

                            new empListDialog(getContext(),employeesList).show();

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


    private void getSettingValues(Integer settingId) {
        Log.e("PARAMETER","            SETTING ID  :      "+settingId);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Setting> listCall = Constants.myInterface.getSettingDataById(settingId);
            listCall.enqueue(new Callback<Setting>() {
                @Override
                public void onResponse(Call<Setting> call, Response<Setting> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SETTING VALUES: ", " - " + response.body());

                            Setting setting=response.body();

                            new addEmpDialog(getContext(),setting).show();

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Setting> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private class empListDialog extends Dialog{

        public RecyclerView recyclerView;
        public ImageView ivClose;
        ArrayList<Employee> empList;

        public empListDialog(@NonNull Context context,ArrayList<Employee> empList) {
            super(context);
            this.empList = empList;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.employee_list_layout);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            ivClose = (ImageView) findViewById(R.id.ivClose);


            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });


            EmployeeListAdapter mAdapter = new EmployeeListAdapter(empList, getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        }
    }

        private class addEmpDialog extends Dialog implements View.OnClickListener {

        public Button btnSave;
        public EditText edEmpName, edEmpMob,edJoiningDate,edAddress,edPassword,edEmployeeCode;
        public Spinner spType;
        public ImageView ivClose;
        long fromDateMillis, toDateMillis;
        int yyyy, mm, dd;
        Setting setting;

        ArrayList<String> desigArray = new ArrayList<>();
        final ArrayList<Integer> desigIdArray = new ArrayList<>();


        public addEmpDialog(@NonNull Context context, Setting setting) {
            super(context);
            this.setting = setting;
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

            edEmployeeCode.setText(""+setting.getSettingValue());

            spType=(Spinner) findViewById(R.id.spType);

            ivClose=(ImageView) findViewById(R.id.ivClose);

            desigArray.clear();
            desigArray.add("Admin");
            desigArray.add("Manager");
            desigArray.add("cashier");

            desigIdArray.add(-1);
            desigIdArray.add(1);
            desigIdArray.add(2);
            desigIdArray.add(3);

            ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, desigArray);
            spType.setAdapter(typeAdapter);

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
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), fromDateListener, yr, mn, dy);
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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Employee employee=new Employee(0,strEmpName,strEmpMOB,strAddress,fromDate,userLogin.getFranchisee().getFrId(),0,0,0,sdf.format(System.currentTimeMillis()),sdf.format(System.currentTimeMillis()),1,sdf1.format(System.currentTimeMillis()),strPass,strEmpCode,type,0,0,0,"","","");
                    saveEmployee(employee);
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
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Employee> listCall = Constants.myInterface.saveFrEmpDetails(employee);
            listCall.enqueue(new Callback<Employee>() {
                @Override
                public void onResponse(Call<Employee> call, Response<Employee> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE EMPLOYEE : ", " ------------------------------SAVE EMPLOYEE------------------------ " + response.body());

                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new EditProfileFragment(), "DashFragment");
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
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void showFileChooser() {
//        Intent intent = new Intent();
//        //sets the select file to all types of files
//        intent.setType("*/*");
//        String[] mimetypes = {"application/msword", "application/pdf", "application/vnd.ms-excel", "application/excel", "application/x-excel", "application/x-msexcel"};
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);//allows to select data and return it
//        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        //starts new activity to select file and return data
//        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), 1);

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Choose");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, 101);
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");

                        String authorities = BuildConfig.APPLICATION_ID + ".provider";
                        Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realPath;
        Bitmap bitmap = null;

        if (resultCode == getActivity().RESULT_OK && requestCode == 102) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivPhoto1.setImageBitmap(myBitmap);

                    myBitmap = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        //Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        //Log.e("Exception : ", "--------" + e.getMessage());
                        //  e.printStackTrace();
                    }
                }
                imagePath = f.getAbsolutePath();
                tvPhoto1.setText("" + f.getName());
            } catch (Exception e) {
                // e.printStackTrace();
            }

        } else if (resultCode == getActivity().RESULT_OK && requestCode == 101) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap = getBitmapFromCameraData(data, getContext());

                ivPhoto1.setImageBitmap(myBitmap);
                imagePath = uriFromPath.getPath();
                tvPhoto1.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    ////Log.e("Image Saved  ", "---------------");

                } catch (Exception e) {
                    // Log.e("Exception : ", "--------" + e.getMessage());
                    //  e.printStackTrace();
                }

            } catch (Exception e) {
                //  e.printStackTrace();
                // Log.e("Image Compress : ", "-----Exception : ------" + e.getMessage());
            }
        }
    }

    public static Bitmap shrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }

    public static Bitmap getBitmapFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        String picturePath = cursor.getString(columnIndex);
        path = picturePath;
        cursor.close();

        Bitmap bitm = shrinkBitmap(picturePath, 720, 720);
        //Log.e("Image Size : ---- ", " " + bitm.getByteCount());

        return bitm;
        // return BitmapFactory.decodeFile(picturePath, options);
    }

    private void sendDocToServer(final String filename, final ExpenseList expenseList) {

        Log.e("PARAMETER","          FILE NAME    "+filename+"          EXPENSE LIST      "+expenseList);

        final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
        commonDialog.show();

        File imgFile = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("file"), imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgFile.getName(), requestFile);

        RequestBody imgName = RequestBody.create(MediaType.parse("text/plain"), filename);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "1");

        Log.e("BODY","-------------------------------------"+body.toString());

        Call<Info> call = Constants.myInterface.saveUploadedFiles(body, imgName, type);
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                commonDialog.dismiss();
                //  addNewNotification(bean);
                imagePath = "";
                Log.e("Response : ", "--" + response.body());


            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                //Toast.makeText(EventDetailListActivity.this, "Unable To Process", Toast.LENGTH_SHORT).show();
                imagePath = "";
                // getAppliedEvent(eventId, regId, filename);

                Toast.makeText(getContext(), "Unable to upload document!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}