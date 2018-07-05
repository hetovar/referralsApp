package com.nearsoft.referralsapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("jobs")
    Call<ArrayList<NearsoftJob>> getJob();
}
