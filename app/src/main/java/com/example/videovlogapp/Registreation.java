package com.example.videovlogapp;

import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Registreation extends AppCompatActivity implements View.OnClickListener {


    private EditText mobile, uname, pass, email;
    private Button btn_res;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreation);
        mobile = (EditText) findViewById(R.id.mobile);
        uname = (EditText) findViewById(R.id.uname);
        pass = (EditText) findViewById(R.id.pass);
        email = (EditText) findViewById(R.id.email);

        btn_res = (Button) findViewById(R.id.btn_rg);
        btn_res.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_rg) {

            registerUser();
        }
    }

    private void registerUser() {
        final String umobile = mobile.getText().toString().trim();
        final String username = uname.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        final String u_email = email.getText().toString().trim();

        progressDialog.setMessage("Registering User..");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ROOT_REGISTER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Registreation.this,Login.class));

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> stringMap = new HashMap<>();

                stringMap.put("username", username);
                stringMap.put("mobile", umobile);
                stringMap.put("password", password);
                stringMap.put("email", u_email);
                return stringMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}



