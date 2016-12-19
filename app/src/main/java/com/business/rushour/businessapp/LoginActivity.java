package com.business.rushour.businessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by deepak on 04/12/16.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText editTextmobilenumber;
    private EditText editTextpassword;
    Button loginbtn;
    TextView forgotpasswordlink;

    private static final String LOGIN_URL = "http://sampletestingservice.3eeweb.com/Rushour/rushour_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextmobilenumber = (EditText) findViewById(R.id.input_mobilenumber);
        editTextpassword = (EditText) findViewById(R.id.input_password);
        loginbtn = (Button) findViewById(R.id.btn_login);
        forgotpasswordlink = (TextView) findViewById(R.id.link_forgotpassword);

        loginbtn.setOnClickListener(this);

        forgotpasswordlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        if (v == loginbtn)
        {
            loginUser();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loginUser() {
        String mobilenumber = editTextmobilenumber.getText().toString().trim().toLowerCase();
        String password = editTextpassword.getText().toString().trim().toLowerCase();

        login(mobilenumber, password);
    }

    private void login(String mobilenumber, String password)
    {
        String urlSuffix = "?mobilenumber="+mobilenumber+"&password="+password;

        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(LOGIN_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

    public void forgotPassword()
    {
        Intent login = new Intent(this, ForgotPasswordActivity.class);
        startActivity(login);
    }
}
