package com.nearsoft.referralsapp;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @GET("jobs")
    Call<List<NearsoftJob>> getJob();

    @GET("recruiters")
    Call<List<Recruiter>> getRecruiter();

    @Multipart
    @POST("refer")
    Call<ResponseBody> sendMail(@Part("recruiter_id") int recruiterId,
                                @Part("job_id") int jobId,
                                @Part("referred_name") RequestBody referName,
                                @Part("referred_email") RequestBody referEmail,
                                @Part MultipartBody.Part resumeFile
                        );
}
