package com.example.lucian.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Menu extends AppCompatActivity {
    ListView listView;
    ArrayList<String> a_descc = new ArrayList();
    ArrayList<String> a_imagen = new ArrayList();
    ArrayList<String> a_fecha = new ArrayList();
    //int[] a_imagen2;
    String[] a_descc2;
    String[] a_fecha2;
    ArrayList<Double> a_latitud = new ArrayList();
    ArrayList<Double> a_longitud = new ArrayList();

    TextView tx_name,lat,lon;
    EditText texto;
    private String descc;
    Button boton,botonDesc;
    private String encoded_string, image_name, fecha_queue;
    private Bitmap bitmap;
    private File file;
    AlertDialog dialog;
    private Uri file_uri;
    private LocationManager locManager;
    private Location loc;
    private double latitud, longitud;
    public int idUser;
    public String name,fecha3;
    private Handler handler;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        handler = new Handler(getMainLooper());
        //tx_name = findViewById(R.id.text_name);
        //lat = findViewById(R.id.lat);
        //lon = findViewById(R.id.lon);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        idUser = intent.getIntExtra("idUser",idUser);
        //tx_name.setText("Nombre: " + name);

        boton = findViewById(R.id.button_r);
        boton.setEnabled(false);
        if (android.os.Build.VERSION.SDK_INT >= 26){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(Menu.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            tx_name.setText("hola");
            return;
        }else {
            //Toast.makeText(getApplicationContext(), "HOLIIII", Toast.LENGTH_LONG).show();

            SingleShotLocationProvider.requestSingleUpdate(this, new SingleShotLocationProvider.LocationCallback() {
                @Override
                public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                    //Toast.makeText(getApplicationContext(), "HOLAA0", Toast.LENGTH_LONG).show();
                   actualizarCoordenadas(location);
                }
            });
        }
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                intent.putExtra(MediaStore.EXTRA_OUTPUT,file_uri);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                startActivityForResult(intent,10);
            }
        });

    }

    private void actualizarCoordenadas(SingleShotLocationProvider.GPSCoordinates location) {
        latitud = location.latitude;
        longitud = location.longitude;
        //lat.setText("Latiud: "+latitud);
        //lon.setText("Longitud: "+longitud);
        boton.setEnabled(true);
        mostarReporte();

    }

    public void mostarReporte(){

        Response.Listener<String>responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray array = jsonResponse.getJSONArray("reportes");

                    for (int i = 0; i < array.length();i++) {
                        JSONObject object = array.getJSONObject(i);
                        a_descc.add(object.getString("descripcion"));
                        a_imagen.add(object.getString("dir"));
                        a_latitud.add(object.getDouble("latitud"));
                        a_longitud.add(object.getDouble("longitud"));
                        a_fecha.add(object.getString("fecha"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                a_descc2 = new String[a_descc.size()];
                a_fecha2 = new String[a_fecha.size()];
                for (int j = 0; j < a_descc.size(); j++){
                    a_descc2[j] = a_descc.get(j);
                    a_fecha2[j] = a_fecha.get(j);
                }
                Log.w("ssasa", a_descc2.toString());
                Log.w("ssasa", a_imagen.toString());
                listView = (ListView)findViewById(R.id.Listview);
                CustomListAdapter customListAdapter = new CustomListAdapter(Menu.this, a_imagen, a_descc2, a_fecha2);
                listView.setAdapter(customListAdapter);
                listView.setDividerHeight(0);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String link = a_imagen.get(i);
                        Intent intent = new Intent(Menu.this,VerReporte.class);
                        intent.putExtra("link",link);
                        intent.putExtra("longitud",a_longitud.get(i));
                        intent.putExtra("latitud",a_latitud.get(i));
                        startActivity(intent);
                    }
                });
            }
        };

        GetReporte getReporte = new GetReporte(latitud, longitud, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Menu.this);
        queue.add(getReporte);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                SingleShotLocationProvider.requestSingleUpdate(this, new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        actualizarCoordenadas(location);
                    }
                });
            }


        }
    }

    private void getFileUri(){
        Date fecha = new Date();
        DateFormat fecha2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        DateFormat fecha3 = new SimpleDateFormat("dd-MM-yy-HH:mm:ss");
        fecha_queue = fecha2.format(fecha);
        image_name = fecha3.format(fecha)+"-" + name + ".jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + image_name
        );
        file_uri = Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK){
            new Encode_image().execute();
        }
    }

    private class Encode_image extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids){
            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array,0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            AlertDialog.Builder alert = new AlertDialog.Builder(Menu.this);
            View layout = getLayoutInflater().inflate(R.layout.descripcion,null);
            alert.setView(layout);
            dialog = alert.create();
            texto = layout.findViewById(R.id.descripcion);
            botonDesc = layout.findViewById(R.id.buttonDesc);
            botonDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    descc = texto.getText().toString();
                    makeRequest();
                    Toast.makeText(getApplicationContext(),"Publicando Reporte",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.show();
                }
            });
        }
    }

    private void makeRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.17/Repor.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("encoded_string",encoded_string);
                map.put("image_name",image_name);
                map.put("latitud",latitud+"");
                map.put("longitud",longitud+"");
                map.put("idUser",idUser+"");
                map.put("descc",descc);
                map.put("fecha",fecha_queue);
                return map;
            }
        };

        requestQueue.add(request);
    }

}