package com.example.lucian.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucian on 02-05-18.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://192.168.0.15/Register.php";
    private Map<String, String> params;
    public RegisterRequest(String email, String name, String pass, int celular, String color, Response.Listener<String> listener){
        super (Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("name", name);
        params.put("pass", pass);
        params.put("celular", celular+"");
        params.put("color", color);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
