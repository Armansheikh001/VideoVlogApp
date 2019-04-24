package com.example.videovlogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText mobile, pass;
    private Button btn_log;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharePrefMng.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(Login.this, Profile.class));
        }

        mobile = (EditText) findViewById(R.id.mobile_no);
        pass = (EditText) findViewById(R.id.password);

        btn_log = (Button) findViewById(R.id.buttonSignin);
        btn_log.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSignin) {

            Login();
        }else if (v.getId() == R.id.buttonReg) {

            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }
    private void Login() {
        final String username = mobile.getText().toString().trim();
        final String password = pass.getText().toString().trim();

        progressDialog.setMessage("Registering User..");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ROOT_LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            if (!object.getBoolean("error")) {

                                SharePrefMng.getInstance(getApplicationContext()).userLogin(
                                        object.getInt("id"),
                                        object.getString("username"),
                                        object.getString("email")

                                );
                                Toast.makeText(getApplicationContext(), "User Login Successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this, Profile.class));
                                finish();

                            } else {

                                Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Tets", Toast.LENGTH_LONG).show();

                        }
                    }
                },

                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> stringMap = new HashMap<>();

                stringMap.put("username", username);
                stringMap.put("mobile", password);
                return stringMap;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
