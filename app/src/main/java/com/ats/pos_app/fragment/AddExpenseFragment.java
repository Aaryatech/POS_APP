package com.ats.pos_app.fragment;


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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.pos_app.BuildConfig;
import com.ats.pos_app.R;
import com.ats.pos_app.constants.Constants;
import com.ats.pos_app.model.Company;
import com.ats.pos_app.model.ExpenseList;
import com.ats.pos_app.model.FrSetting;
import com.ats.pos_app.model.Info;
import com.ats.pos_app.model.UserLogin;
import com.ats.pos_app.utils.CommonDialog;
import com.ats.pos_app.utils.CustomSharedPreference;
import com.ats.pos_app.utils.PermissionsUtil;
import com.ats.pos_app.utils.RealPathUtil;
import com.google.gson.Gson;

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
public class AddExpenseFragment extends Fragment implements View.OnClickListener {
    Spinner spType;
    public EditText edChalanNo,edDate,edAmount,edRemark;
    public TextView btn_upload,tv_uploadText;
    public Button btnSubmit;
    UserLogin userLogin;
    ExpenseList expenseList;

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    int chSeq;
    String myString,myString1;

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "RUSA_DOCS");
    File f;
    Bitmap myBitmap = null;


    private final int RESULT_OK = -1;
    public static String path, imagePath;

    ArrayList<String> typeArray = new ArrayList<>();
    final ArrayList<Integer> typeIdArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        spType = (Spinner)view.findViewById(R.id.spType);

        edChalanNo = (EditText)view.findViewById(R.id.edChalanNo);
        edDate = (EditText)view.findViewById(R.id.edDate);
        edAmount = (EditText)view.findViewById(R.id.edAmount);
        edRemark = (EditText)view.findViewById(R.id.edRemark);

        btn_upload = (TextView)view.findViewById(R.id.btn_upload);
        tv_uploadText = (TextView)view.findViewById(R.id.tv_uploadText);

        btnSubmit = (Button)view.findViewById(R.id.btnSubmit);

        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        createFolder();

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.USER_LOGIN);
        Gson gson = new Gson();
        userLogin = gson.fromJson(userStr, UserLogin.class);
        Log.e("HOME_ACTIVITY : ", "-------------------USER LOGIN--------------------" + userLogin);


        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(todayDate);

        fromDateMillis = todayDate.getTime();
        toDateMillis = todayDate.getTime();

        edDate.setText(currentDate);

        typeArray.clear();
        typeArray.add("Regular Work");
        typeArray.add("Payment Chalan");

        typeIdArray.add(1);
        typeIdArray.add(2);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, typeArray);
        spType.setAdapter(typeAdapter);


        try {
            String quoteStr = getArguments().getString("model");
            expenseList = gson.fromJson(quoteStr, ExpenseList.class);

            if(expenseList!=null)
            {
                edChalanNo.setText(expenseList.getChalanNo());
                edDate.setText(expenseList.getExpDate());
                edAmount.setText(expenseList.getChAmt());
                edRemark.setText(expenseList.getRemark());
                tv_uploadText.setText(expenseList.getImgName());

                Log.e("TYPE ID","----------------"+expenseList.getExpType());
                int expType = Integer.parseInt(expenseList.getExpType());

                int pos=0;
                for(int i=0;i<typeIdArray.size();i++)
                {
                    if(typeIdArray.get(i)== expType)
                    {
                        pos=i;

                        break;
                    }
                }
                spType.setSelection(pos);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(expenseList==null)
        {
            getActivity().setTitle("Add Expense");
        }else{
            getActivity().setTitle("Edit Expense");
        }

        getSettingValues(userLogin.getFranchisee().getFrId());
        
        btn_upload.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        return view;
    }

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    private void getSettingValues(Integer frId) {
        Log.e("PARAMETER","            FR ID  :      "+frId);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<FrSetting> listCall = Constants.myInterface.getFrSettingValue(frId);
            listCall.enqueue(new Callback<FrSetting>() {
                @Override
                public void onResponse(Call<FrSetting> call, Response<FrSetting> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SETTING VALUES: ", " - " + response.body());

                            FrSetting frSetting=response.body();
                            chSeq = frSetting.getCount();
                            String gfg3 = String.format("%04d", chSeq);

                            getCompany(gfg3);

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
                public void onFailure(Call<FrSetting> call, Throwable t) {
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

    private void getCompany(final String chSeq) {
        Log.e("PARAMETER","            CHAR SEQ  :      "+chSeq);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Company> listCall = Constants.myInterface.getCompany();
            listCall.enqueue(new Callback<Company>() {
                @Override
                public void onResponse(Call<Company> call, Response<Company> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("COMPANY LIST: ", " - " + response.body());
                            Company company=response.body();

                            String strFromDate=company.getFromDate();
                            String strToDate=company.getToDate();

                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

                            Date myFromDate = null;
                            try {
                                myFromDate = formatter1.parse(strFromDate);
                                Log.e(" Date","---------------DATE-FROM--------------------------"+myFromDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String fromDate = formatter.format(myFromDate);

                            Date myToDate = null;
                            try {
                                myToDate = formatter1.parse(strToDate);
                                Log.e(" Date","---------------DATE--TO-------------------------"+myToDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                          String toDate = formatter.format(myToDate);

                            myString = fromDate.substring(fromDate.indexOf('-') + 3);

                            myString1 = toDate.substring(toDate.indexOf('-') + 3);

                            Log.e(" Date","---------------SPLIT FROM------------------------"+myString);
                            Log.e(" Date","---------------SPLIT TO------------------------"+myString1);

                            String a = userLogin.getFranchisee().getFrCode().concat("-").concat(String.valueOf(myString.charAt(3)))
                                    .concat(String.valueOf(myString.charAt(4))).concat("-").concat(String.valueOf(myString1.charAt(3)))
                                    .concat(String.valueOf(myString1.charAt(4))).concat("-").concat(chSeq);

                            edChalanNo.setText(""+a);

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
                public void onFailure(Call<Company> call, Throwable t) {
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
        if(v.getId()==R.id.btn_upload)
        {
            showFileChooser();

        }else if(v.getId()==R.id.btnSubmit)
        {
            String strChalan=edChalanNo.getText().toString().trim();
            String strDate=edDate.getText().toString().trim();
            String strAmount=edAmount.getText().toString().trim();
            String strRemark=edRemark.getText().toString().trim();
            String strUploadText=tv_uploadText.getText().toString().trim();

            if (strChalan.isEmpty()) {
                edChalanNo.setError("required");

            }else if (strDate.isEmpty()) {
                edChalanNo.setError(null);
                edDate.setError("required");

            }else if (strAmount.isEmpty()) {
                edDate.setError(null);
                edAmount.setError("required");

            } else if (strUploadText.isEmpty()) {
                edAmount.setError(null);
                tv_uploadText.setError("Please select document file to upload");

                //Toast.makeText(getContext(), "Please select document file to upload", Toast.LENGTH_SHORT).show();

              //  TextView viewType = (TextView) spType.getSelectedView();
               // viewType.setError(null);
            } else {

                tv_uploadText.setError(null);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

                File imgFile = new File(imagePath);
                int pos = imgFile.getName().lastIndexOf(".");
                String ext = imgFile.getName().substring(pos + 1);
                String docFileName = sdf1.format(System.currentTimeMillis()) + "_" + userLogin.getFrEmp().getFrEmpId() + "." + ext;

                int typeId = typeIdArray.get(spType.getSelectedItemPosition());
                String type= String.valueOf(typeId);
                String typeName = typeArray.get(spType.getSelectedItemPosition());
                long fileSize = imgFile.length();

                String currDate = sdf2.format(Calendar.getInstance().getTimeInMillis());

                if(expenseList==null) {

                    ExpenseList expenseList1 = new ExpenseList(0, strChalan, type, docFileName, strAmount, userLogin.getFranchisee().getFrId(), strRemark, 0, currDate, 0, 0, 0, 0, 0, "", "", "", "");

                    sendDocToServer(docFileName, expenseList1);
                }else{
                    ExpenseList expenseList2 = new ExpenseList(expenseList.getExpId(), strChalan, type, docFileName, strAmount, userLogin.getFranchisee().getFrId(), strRemark, expenseList.getStatus(), currDate, expenseList.getExInt1(), expenseList.getExInt2(), expenseList.getExInt3(), expenseList.getExInt4(), expenseList.getDelStatus(), expenseList.getExVar1(), expenseList.getExVar2(), expenseList.getExVar3(), expenseList.getExVar4());

                    sendDocToServer(docFileName, expenseList2);
                }

            }
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

    //--------------------------------------------------------------------

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String realPath;
//
//        Log.e("EVENT DET LIST ACT", "---------------------- onActivityResult " + requestCode + " - " + resultCode);
//
//        if (resultCode == RESULT_OK && requestCode == 1) {
//            try {
//
//                if (data == null) {
//                    //no data present
//                    return;
//                }
//                Uri selectedFileUri = data.getData();
//                Log.e("UriPath", "----------" + selectedFileUri.getPath());
//
//                Log.e("FILE URI ", "********************* " + getActivity().getContentResolver().openInputStream(selectedFileUri).toString());
//
//
//                Log.e("DATA PATH", "---------------------- " + getPath(getActivity(), selectedFileUri));
//
//                imagePath = getPath(getActivity(), selectedFileUri);
//                if (getPath(getActivity(), selectedFileUri) == null) {
//                    imagePath = "";
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
//                    builder.setMessage("Please select other file");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                } else {
//                    tv_uploadText.setText("" + getPath(getActivity(), selectedFileUri));
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("EVENT DET LIST ACT : ", "-----Exception : ------" + e.getMessage());
//            }
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static String getPath(final Context context, final Uri uri) {
//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//
//        // DocumentProvider
//        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//            System.out.println("getPath() uri: " + uri.toString());
//            System.out.println("getPath() uri authority: " + uri.getAuthority());
//            System.out.println("getPath() uri path: " + uri.getPath());
//
//            // ExternalStorageProvider
//            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//                System.out.println("getPath() docId: " + docId + ", split: " + split.length + ", type: " + type);
//
//                // This is for checking Main Memory
//                if ("primary".equalsIgnoreCase(type)) {
//                    if (split.length > 1) {
//                        return Environment.getExternalStorageDirectory() + "/" + split[1] + "/";
//                    } else {
//                        return Environment.getExternalStorageDirectory() + "/";
//                    }
//                    // This is for checking SD Card
//                } else {
//                    return "storage" + "/" + docId.replace(":", "/");
//                }
//
//            } else {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//                System.out.println("getPath() docId: " + docId + ", split: " + split.length + ", type: " + type);
//
//                // This is for checking Main Memory
//                if ("primary".equalsIgnoreCase(type)) {
//                    if (split.length > 1) {
//                        return Environment.getDataDirectory() + "/" + split[1] + "/";
//                    } else {
//                        return Environment.getDataDirectory() + "/";
//                    }
//                    // This is for checking SD Card
//                } else {
//                    return "storage" + "/" + docId.replace(":", "/");
//                }
//
//            }
//        }
//        return null;
//    }

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
                    //imageView.setImageBitmap(myBitmap);

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
                tv_uploadText.setText("" + f.getName());
            } catch (Exception e) {
                // e.printStackTrace();
            }

        } else if (resultCode == getActivity().RESULT_OK && requestCode == 101) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap = getBitmapFromCameraData(data, getContext());

                //imageView.setImageBitmap(myBitmap);
                imagePath = uriFromPath.getPath();
                tv_uploadText.setText("" + uriFromPath.getPath());

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

               saveExpense(expenseList);

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

    private void saveExpense(ExpenseList expenseList) {
        Log.e("PARAMETER","                 EXPENSE LIST              "+expenseList);
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ExpenseList> listCall = Constants.myInterface.saveExpense(expenseList);
            listCall.enqueue(new Callback<ExpenseList>() {
                @Override
                public void onResponse(Call<ExpenseList> call, Response<ExpenseList> response) {
                    try {
                        if (response.body() == null) {

                            Toast.makeText(getContext(), "Unable to upload file!", Toast.LENGTH_SHORT).show();

                        } else {

                            //Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                            updateFrSettingCount(userLogin.getFranchisee().getFrId(),chSeq+1);

                        }
                        commonDialog.dismiss();

                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ExpenseList> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateFrSettingCount(Integer frId, int chSeq) {
        Log.e("PARAMETER","            FR ID  :      "+frId+"         CHAR SEQ  :  "+chSeq);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateFrSettingCount(frId,chSeq);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE SETTING VALUES: ", " - " + response.body());

                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new ExpenseListFragment(), "DashFragment");
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

}
