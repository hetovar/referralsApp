package com.nearsoft.referralsapp;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
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
    Call<Mail> sendMail(@Field("recruiter_id") int recruiterId,
                        @Field("job_id") int jobId,
                        @Field("referred_name") String referName,
                        @Field("referred_email") String referEmail,
                        @Field("resume_file") File resumeFile
                        );
}
