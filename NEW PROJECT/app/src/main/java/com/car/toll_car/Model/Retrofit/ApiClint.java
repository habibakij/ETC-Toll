package com.car.toll_car.Model.Retrofit;

import com.car.toll_car.Model.PostRequestModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiClint {
    /*@POST("RFIDApicbank/registr.php")
    Call<PostRequestModel> postRequest(@Body PostRequestModel postRequestModel);*/

    @FormUrlEncoded
    @POST("RFIDApicbank/registr.php")
    Call<PostRequestModel> post (
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile") String mobile
    );
}
