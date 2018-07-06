package com.nearsoft.referralsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("jobs")
    Call<List<NearsoftJob>> getJob();

    @GET("recruiters")
    Call<List<Recruiter>> getRecruiter();

    @POST("refer")
    @FormUrlEncoded
    Call<Mail> sendMail(@Body Mail mail);
}
