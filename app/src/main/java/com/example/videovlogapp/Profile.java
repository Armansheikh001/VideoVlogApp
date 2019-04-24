package com.example.videovlogapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    private TextView username, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharePrefMng.getInstance(this).isLoggedIn()) {

            startActivity(new Intent(Profile.this, Login.class));
            finish();
        } // end


        username = (TextView) findViewById(R.id.uname);
        email = (TextView) findViewById(R.id.email);
        Button logout = (Button) findViewById(R.id.logout);

        username.setText(SharePrefMng.getInstance(this).getUsername());
        email.setText(SharePrefMng.getInstance(this).getUserEmail());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharePrefMng.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(Profile.this, Login.class));
            }
        });
    }
}
