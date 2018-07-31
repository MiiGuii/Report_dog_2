package com.example.lucian.myapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    EditText etemail, etusuario, etpass, etnumero, etcolor;
    Button btn_registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etemail = (EditText) findViewById(R.id.email);
        etusuario = (EditText) findViewById(R.id.nombreU);
        etpass = (EditText) findViewById(R.id.pass);
        etnumero = (EditText) findViewById(R.id.cel);
        etcolor = (EditText) findViewById(R.id.col);
        btn_registrar = (Button) findViewById(R.id.button2);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etemail.getText().toString();
                final String name = etusuario.getText().toString();
                final String pass = etpass.getText().toString();
                final int numero = Integer.parseInt(etnumero.getText().toString());
                final String color = etcolor.getText().toString();

                Response.Listener<String> respoListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean succes = jsonResponse.getBoolean("success");

                            if (succes){
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                Register.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                builder.setMessage("Error en el Registro")
                                        .setNegativeButton("Reintentar",null)
                                        .create().show();
                            }
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(email,name,pass,numero,color,respoListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);
            }

        });
    }
}
