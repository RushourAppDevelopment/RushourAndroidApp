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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by deepak on 04/12/16.
 */

public class RiderRegistrationActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText editTextfirstname;
    private EditText editTextlastname;
    private EditText editTextpreferredname;
    private RadioGroup editgender;
    private EditText editTextmobilenumber;
    private EditText editTextemailid;
    private EditText editTextpassword;
    private EditText editTextidproofnumber;
    private Button buttonRegister;

    String userGender="";
    String idproof="";

    private static final String REGISTER_URL = "http://sampletestingservice.3eeweb.com/Rushour/rushour_riderregister.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riderregistration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextfirstname = (EditText) findViewById(R.id.input_firstname);
        editTextlastname = (EditText) findViewById(R.id.input_lastname);
        editTextpreferredname = (EditText) findViewById(R.id.input_preferredname);
        editgender = (RadioGroup) findViewById(R.id.gender);
        editTextmobilenumber = (EditText) findViewById(R.id.input_mobilenumber);
        editTextemailid = (EditText) findViewById(R.id.input_emailid);
        editTextpassword = (EditText) findViewById(R.id.input_password);
        editTextidproofnumber = (EditText) findViewById(R.id.input_idproofnumber);

        buttonRegister = (Button) findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

    public void GenderClicked(View view)
    {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId())
        {
            case R.id.male:
                if (checked)
                    userGender = "male";
                break;

            case R.id.female:
                if (checked)
                    userGender = "female";
                break;
        }
    }

    public void IdProofClicked(View view)
    {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId())
        {
            case R.id.drivinglicense:
                if (checked)
                    idproof = "driving license";
                break;

            case R.id.aadhaar:
                if (checked)
                    idproof = "aadhaar Card";
                break;

            case R.id.passport:
                if (checked)
                    idproof = "passport";
                break;

            case R.id.voterid:
                if (checked)
                    idproof = "voter id";
                break;
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

    private void registerUser() {
        String firstname = editTextfirstname.getText().toString().trim().toLowerCase();
        String lastname = editTextlastname.getText().toString().trim().toLowerCase();
        String preferredname = editTextpreferredname.getText().toString().trim().toLowerCase();
        String mobilenumber = editTextmobilenumber.getText().toString().trim().toLowerCase();
        String emailid = editTextemailid.getText().toString().trim().toLowerCase();
        String password = editTextpassword.getText().toString().trim().toLowerCase();
        String idproofnumber = editTextidproofnumber.getText().toString().trim().toLowerCase();

        register(firstname,lastname,preferredname,mobilenumber,emailid,password,idproofnumber,userGender,idproof);
    }

    private void register(String firstname, String lastname, String preferredname, String mobilenumber, String emailid, String password, String idproofnumber, String gender, String idproof) {
        String urlSuffix = "?firstname="+firstname+"&lastname="+lastname+"&preferredname="+preferredname+"&mobilenumber="+mobilenumber+"&emailid="+emailid+"&password="+password+"&idproofnumber="+idproofnumber+"&gender="+gender+"&idproof="+idproof;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RiderRegistrationActivity.this, "Please Wait",null, true, true);
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
                    URL url = new URL(REGISTER_URL+s);
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

}
