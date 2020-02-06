package com.ats.pos_app.interfaceAPI;

import com.ats.pos_app.model.Company;
import com.ats.pos_app.model.DashboardCount;
import com.ats.pos_app.model.Employee;
import com.ats.pos_app.model.ExpenseList;
import com.ats.pos_app.model.FrGraph;
import com.ats.pos_app.model.FrLogin;
import com.ats.pos_app.model.FrSetting;
import com.ats.pos_app.model.Info;
import com.ats.pos_app.model.PettyCashDetail;
import com.ats.pos_app.model.PettyCashHandOverTransaction;
import com.ats.pos_app.model.PieGraph;
import com.ats.pos_app.model.PieItem;
import com.ats.pos_app.model.Route;
import com.ats.pos_app.model.Setting;
import com.ats.pos_app.model.Supplyment;
import com.ats.pos_app.model.UserLogin;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface InterfaceAPI {

    @POST("getDatewiseSell")
    Call<ArrayList<FrGraph>> getDatewiseSell(@Query("frId") int frId,@Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @POST("getCatwiseSell")
    Call<ArrayList<PieGraph>> getCatwiseSell(@Query("frId") int frId,@Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @POST("getCatwiseItemSell")
    Call<ArrayList<PieItem>> getCatwiseItemSell(@Query("fromDate") String fromDate, @Query("toDate") String toDate,@Query("frId") int frId,@Query("catId") int catId,@Query("flag") int flag);

    @POST("getPosDashCounts")
    Call<DashboardCount> getPosDashCounts(@Query("frId") int frId,@Query("frRateCat") int frRateCat,@Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @POST("loginFr")
    Call<FrLogin> loginFr(@Query("frCode") String frCode, @Query("frPasswordKey") String frPasswordKey);

    @POST("getAllFrEmpByFrid")
    Call<ArrayList<Employee>> getAllFrEmpByFrid(@Query("frId") int frId);

    @POST("frEmpById")
    Call<UserLogin> frEmpById(@Query("empId") int empId, @Query("frId") int frId);

    @POST("getPettyCashListDateWise")
    Call<ArrayList<PettyCashDetail>> getPettyCashListDateWise(@Query("frId") int frId, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @POST("getCashHandOverTransctn")
    Call<ArrayList<PettyCashHandOverTransaction>> getCashHandOverTransctn(@Query("frId") int frId, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @POST("getPettyCashAmtForApp")
    Call<PettyCashDetail> getPettyCashAmtForApp(@Query("frId") int frId);

    @POST("addPettyCash")
    Call<PettyCashDetail> addPettyCash(@Body PettyCashDetail pettyCashDetail);

    @POST("savePettyCashHandOver")
    Call<PettyCashHandOverTransaction> savePettyCashHandOver(@Body PettyCashHandOverTransaction pettyCashHandOverTransaction);

    @POST("getLastCashHandover")
    Call<PettyCashHandOverTransaction> getLastCashHandover(@Query("frId") int frId,@Query("lastdate") String lastdate);

    @POST("getExpenseByFrId")
    Call<ArrayList<ExpenseList>> getExpenseByFrId(@Query("frId") int frId, @Query("type") int type, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @POST("deleteExpense")
    Call<Info> deleteExpense(@Query("expId") int expId);

    @Multipart
    @POST("photoUpload")
    Call<Info> saveUploadedFiles(@Part MultipartBody.Part filePath, @Part("imageName") RequestBody imageName, @Part("type") RequestBody type);

    @POST("saveExpense")
    Call<ExpenseList> saveExpense(@Body ExpenseList expenseList);

    @POST("updateFrSettingCount")
    Call<Info> updateFrSettingCount(@Query("frId") int frId,@Query("chSeq") int chSeq);

    @POST("getFrSettingValue")
    Call<FrSetting> getFrSettingValue(@Query("frId") int frId);

    @GET("getCompany")
    Call<Company> getCompany();

    @GET("getSettingDataById")
    Call<Setting> getSettingDataById(@Query("settingId") int settingId );

    @POST("saveFrEmpDetails")
    Call<Employee> saveFrEmpDetails(@Body Employee employee);

    @GET("getRoute")
    Call<Route> getRoute(@Query("routeId") int routeId );

    @POST("updateFranchisee")
    Call<Info> updateFranchisee (@Query("frId") int frId,@Query("frName") String frName,@Query("frCode") String frCode,
                                 @Query("frOpeningDate") String frOpeningDate,@Query("frRate") int frRate,@Query("frImage") String frImage,
                                 @Query("frRouteId") int frRouteId,@Query("frCity") String frCity,@Query("frKg1") int frKg1,
                                 @Query("frKg2") int frKg2,@Query("frKg3") int frKg3,@Query("frKg4") int frKg4,
                                 @Query("frEmail") String frEmail,@Query("frPassword") String frPassword,@Query("frMob") String frMob,
                                 @Query("frOwner") String frOwner,@Query("frRateCat") int frRateCat,@Query("grnTwo") int grnTwo,
                                 @Query("delStatus") int delStatus,@Query("ownerBirthDate") String ownerBirthDate,@Query("fbaLicenseDate") String fbaLicenseDate,
                                 @Query("frAgreementDate") String frAgreementDate,@Query("frGstType") int frGstType,@Query("frGstNo") String frGstNo,
                                 @Query("stockType") int stockType,@Query("frAddress") String frAddress,@Query("frTarget") int frTarget,
                                 @Query("isSameState") int isSameState);

    @POST("getFrSupByFrId")
    Call<Supplyment> getFrSupByFrId(@Query("frId") int frId);

}
