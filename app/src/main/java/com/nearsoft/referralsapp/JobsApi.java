package com.nearsoft.referralsapp;

import com.android.volley.toolbox.JsonArrayRequest;
import java.util.ArrayList;

public interface JobsApi {
    String BASE_URL = "https://my-json-server.typicode.com/haydeeR/API-test/";
    String JOB_LIST_URL = BASE_URL + "positions";

    JsonArrayRequest getRequest();
}
