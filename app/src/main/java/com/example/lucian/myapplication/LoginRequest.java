package com.example.lucian.myapplication;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucian on 02-05-18.
 */

public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://192.168.0.15/Login.php";
    private Map<String, String> params;
    public LoginRequest(String email, String pass, Response.Listener<String> listener){
        super (Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("pass", pass);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
