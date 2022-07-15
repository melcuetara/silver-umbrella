package com.example.rentaland;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;

import org.json.JSONObject;

public class SmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        JsonObjectRequest objectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                "https://sms.teamssprogram.com/api/send?key=f48388598105a1516e489c527b15a94c46252cf3&phone=639665204431&message=Rentaland%3A+someone+sent+you+a+booking+request+just+now%21+%0D%0AAccept+booking+request+to+start+chatting",
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        );
//        requestQueue.add(objectRequest);
    }
}