package com.example.lucian.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class VerReporte extends AppCompatActivity {

    double latitud;
    double longitud;
    GoogleMap mapaG;
    String link;
    ImageView imagen;
    MapView mapView;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_reporte);
        request = Volley.newRequestQueue(getApplicationContext());
        Intent intent = getIntent();
        imagen = (ImageView) findViewById(R.id.imageView);
        mapView = (MapView) findViewById(R.id.mapView);
        link = intent.getStringExtra("link");
        latitud = intent.getDoubleExtra("latitud",latitud);
        longitud = intent.getDoubleExtra("longitud",longitud);
        Picasso.get().load(Uri.parse(link)).fit().into(imagen);
        imagen.setScaleType(ImageView.ScaleType.CENTER);
        mapView.onCreate(savedInstanceState);
        Log.w("ssasa", latitud+"");
        Log.w("ssasa", longitud+"");
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapaG = googleMap;
                mapaG.setMinZoomPreference(15f);
                LatLng a = new LatLng(latitud,longitud);
                mapaG.moveCamera(CameraUpdateFactory.newLatLng(a));
                mapaG.addMarker(new MarkerOptions().position(a));
            }
        });
        //mapView.invalidate();


    }

    private void cargarImagen(String url){
        ImageRequest imageRequest = new ImageRequest(url,
            new Response.Listener<Bitmap>(){
                public void onResponse(Bitmap response){
                    imagen.setImageBitmap(response);
                }
            },0,0,ImageView.ScaleType.CENTER,null,new Response.ErrorListener(){

            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplication(),"Error al cargar la imagen",Toast.LENGTH_SHORT);
            }
        });
        request.add(imageRequest);
    }
}
