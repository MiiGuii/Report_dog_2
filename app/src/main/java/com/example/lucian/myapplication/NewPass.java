package com.example.lucian.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucian on 27-06-18.
 */

public class NewPass extends StringRequest {
    private Map<String, String> params;
    private static final String NEW_PASS_URL = "http://192.168.1.17/NewPass.php";
    public NewPass(String email, String color, String pass, Response.Listener<String> listener) {
        super(Method.POST, NEW_PASS_URL, listener,null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("color",color);
        params.put("pass",pass);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
