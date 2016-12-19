package com.business.rushour.businessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button loginbtn, signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbtn = (Button) findViewById(R.id.btn_login);
        signupbtn = (Button) findViewById(R.id.btn_signup);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

    }

    public void login()
    {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    public void signup()
    {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle("SIGN UP");

            // Setting Dialog Message
            //alertDialog.setMessage("");

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("Sign Up as Owner", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    Intent ownersignup = new Intent(getApplicationContext(), OwnerRegistrationActivity.class);
                    startActivity(ownersignup);
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("Sign Up as Rider", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent ridersignup = new Intent(getApplicationContext(), RiderRegistrationActivity.class);
                    startActivity(ridersignup);
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }

}
