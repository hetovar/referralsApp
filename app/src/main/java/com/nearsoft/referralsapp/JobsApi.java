package com.nearsoft.referralsapp;


import com.android.volley.toolbox.StringRequest;

public interface JobsApi {
    String API_BASE_URL = "https://my-json-server.typicode.com/haydeeR/API-test/";

    String API_URL_GET_JOB_LIST = API_BASE_URL + "positions";

    StringRequest jobEntityList();
}
