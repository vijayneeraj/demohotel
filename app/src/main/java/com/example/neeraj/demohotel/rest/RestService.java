package com.example.neeraj.demohotel.rest;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RestService {

//    @GET(ApiUrls.TASK_LIST + "/{machine_type}" + "/{category}" + "/{machine_id}" + "/{date}")
//    Call<ResponseBody> getTaskList(@Path(ParamName.MACHINE_TYPE) String machine_type, @Path(ParamName.CATEGORY) String category, @Path(ParamName.MACHINE_ID) String machine_id, @Path(ParamName.DATE) String date);
//
//    @FormUrlEncoded
//    @POST(ApiUrls.COMPLETE_TASK)
//    Call<ResponseBody> submitTask(@Field(ParamName.USER_ID) String user_id, @Field(ParamName.TASKS) String tasks, @Field(ParamName.DATE) String date);


    @FormUrlEncoded
    @POST(ApiUrls.SIGNUP)
    Call<ResponseBody> doSignup(@Field(ParamName.EMAIL) String email, @Field(ParamName.MOBILE) String mobile, @Field(ParamName.PASSWORD) String password
            , @Field(ParamName.ADDRESS) String address, @Field(ParamName.HOTELNAME) String name);

    @FormUrlEncoded
    @POST(ApiUrls.LOGIN)
    Call<ResponseBody> doLogin(@Field(ParamName.EMAIL) String email, @Field(ParamName.PASSWORD) String password);

    @FormUrlEncoded
    @POST(ApiUrls.GUESTREGISTER)
    Call<ResponseBody> doGuestSignup(@Field(ParamName.EMAIL) String email, @Field(ParamName.MOBILE) String mobile, @Field(ParamName.PASSWORD) String password
            , @Field(ParamName.ADDRESS) String address, @Field(ParamName.HOTELNAME) String name,@Field(ParamName.ROOMNO)String room,
                                     @Field(ParamName.ArrivalDateTime)String arrival,@Field(ParamName.DEPARTUREDATETIME)String departure,@Field(ParamName.DOB)String dob,
                                     @Field(ParamName.NUMBEROFPERSON)String numberPerson,@Field(ParamName.GENDER)String gender,@Field(ParamName.IDNAME)String id_name,@Field(ParamName.IDNO)String id_no,
                                     @Field(ParamName.IDPIC)String id_pic,@Field(ParamName.GUESTPIC)String guestPic);

}
