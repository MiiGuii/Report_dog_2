package com.example.lucian.myapplication;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucian on 01-08-18.
 */

public class GetReporte extends StringRequest {

    private static final String GET_REPORTE_URL = "http://192.168.1.9/get.php";
    private Map<String, String> params;
    public GetReporte(double latitud, double longitud, Response.Listener<String> listener){
        super (Request.Method.POST, GET_REPORTE_URL, listener, null);
        params = new HashMap<>();
        params.put("latitud", latitud+"");
        params.put("longitud", longitud+"");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
