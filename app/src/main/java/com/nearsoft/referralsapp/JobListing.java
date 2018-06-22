package com.nearsoft.referralsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class JobListing extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private static final String TAG = "TAG";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing);

        mTextView = findViewById(R.id.text);

        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String url ="https://my-json-server.typicode.com/haydeeR/API-test/positions";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = "";
                        for(String line : response.split("\n"))
                        {
                            if(line.contains("name"))
                                result += line.replace("\"name\": ", "") + "\n";

                        }
                        mTextView.setText("Response is: "+ result);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }
}
