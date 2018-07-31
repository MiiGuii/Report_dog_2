package com.example.lucian.myapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button boton,boton1,boton2;
    EditText et_email,email2,color;
    EditText et_password,pass2,pass22;
    TextView text,text2;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba);
        et_email = (EditText)findViewById(R.id.nombre);
        et_password = (EditText)findViewById(R.id.pass);
        boton = findViewById(R.id.tx_button);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = et_email.getText().toString();
                final String pass = et_password.getText().toString();

                Response.Listener<String>responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String name = jsonResponse.getString("name");
                                int idUser = jsonResponse.getInt("id");
                                Intent intent = new Intent(MainActivity.this,Menu.class);
                                intent.putExtra("name",name);
                                intent.putExtra("idUser",idUser);
                                MainActivity.this.startActivity(intent);

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("No se pudo conectar al servidor. Email o contraseña incorrectos?.")
                                        .setNegativeButton("Ok.",null)
                                        .create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(email,pass,responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });

        text = findViewById(R.id.textView2);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(intentReg);
            }
        });

        text2 = findViewById(R.id.textView4);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                View layout = getLayoutInflater().inflate(R.layout.passnew,null);
                alert.setView(layout);
                dialog = alert.create();
                email2 = layout.findViewById(R.id.emailPass);
                color = layout.findViewById(R.id.colorPass);
                pass2 = layout.findViewById(R.id.contraseña2);
                pass22 = layout.findViewById(R.id.newpass2);
                boton1 = layout.findViewById(R.id.buttonOkk);
                boton2 = layout.findViewById(R.id.buttonCancel);

                boton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //AlertDialog.Builder = new AlertDialog.Builder()
                        if (!pass2.getText().toString().equals(pass22.getText().toString())){
                            Toast.makeText(getApplicationContext(),"No coinciden las contraseñas",Toast.LENGTH_SHORT).show();
                        }
                        else if(email2.getText().toString().isEmpty() || color.getText().toString().isEmpty() || pass2.getText().toString().isEmpty() ||pass22.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),"Algun campo vacio",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse2 = new JSONObject(response);
                                        boolean success2 = jsonResponse2.getBoolean("success");
                                        Log.w("success", Boolean.toString(success2));
                                        if (success2) {
                                            Toast.makeText(MainActivity.this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else {
                                            Toast.makeText(MainActivity.this, "Error al cambiar la contraseña, intente mas tarde", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            NewPass newPass = new NewPass(email2.getText().toString(), color.getText().toString(), pass2.getText().toString(), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                            queue.add(newPass);
                        }
                    }
                });
                boton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
