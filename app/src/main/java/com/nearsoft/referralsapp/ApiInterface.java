package com.nearsoft.referralsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("jobs")
    Call<List<NearsoftJob>> getJob();

    @GET("recruiters")
    Call<List<Recruiter>> getRecruiter();
}
