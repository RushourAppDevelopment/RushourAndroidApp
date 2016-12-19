package com.business.rushour.businessapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OwnerRegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextfirstname;
    private EditText editTextlastname;
    private EditText editTextpreferredname;
    private EditText editTextmobilenumber;
    private EditText editTextemailid;
    private EditText editTextpassword;
    private EditText editTextdrivinglicense;
    private EditText editTextvehicleregistration;
    private RadioGroup editgender;
    private RadioButton selectedradiobtn;
    private EditText editTextdrivinglicensevalidity;
    private EditText editTextvehiclemodel;
    private Spinner spinnervehiclebrand;

    private Button btn_drivinglicensefront;
    private Button btn_drivinglicenseback;
    private Button btn_vehiclerc;
    private int year, month, day, hour, minute;
    EditText editTextdatepicker;
    ImageView img_datepicker;
    String userGender="";


    //Button btn_driving = (Button) getView().findViewById(R.id.btn_drivinglicense);
    //ImageView driving = (ImageView) getView().findViewById(R.id.img_datepicker);

    final int PIC_CROP = 2;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Rushour";
    private Uri fileUri; // file url to store image/video
    private Button buttonRegister;

    private static final String REGISTER_URL = "http://sampletestingservice.3eeweb.com/Rushour/rushour_ownerregister.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerregistration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextfirstname = (EditText) findViewById(R.id.input_firstname);
        editTextlastname = (EditText) findViewById(R.id.input_lastname);
        editTextpreferredname = (EditText) findViewById(R.id.input_preferredname);
        editTextmobilenumber = (EditText) findViewById(R.id.input_mobilenumber);
        editTextemailid = (EditText) findViewById(R.id.input_emailid);
        editTextpassword = (EditText) findViewById(R.id.input_password);
        editTextdrivinglicense = (EditText) findViewById(R.id.input_drivinglicenseid);
        editTextvehicleregistration = (EditText) findViewById(R.id.input_vehicleregistrationnumber);
        editgender = (RadioGroup) findViewById(R.id.gender);
        editTextdrivinglicensevalidity = (EditText) findViewById(R.id.license_validity);
        editTextvehiclemodel = (EditText) findViewById(R.id.input_vehiclemodel);
        spinnervehiclebrand = (Spinner) findViewById(R.id.dropdown_vehicletype);

        //int id = editgender.getCheckedRadioButtonId();
        //selectedradiobtn = (RadioButton) findViewById(id);


        buttonRegister = (Button) findViewById(R.id.btn_register);

        buttonRegister.setOnClickListener(this);

        btn_drivinglicensefront = (Button) findViewById(R.id.btn_drivinglicensefront);
        btn_drivinglicenseback = (Button) findViewById(R.id.btn_drivinglicenseback);
        btn_vehiclerc = (Button) findViewById(R.id.btn_vehiclerc);

        img_datepicker =(ImageView) findViewById(R.id.img_datepicker);
        editTextdatepicker = (EditText) findViewById(R.id.license_validity);
        editTextdatepicker.setEnabled(false);
        img_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgpicker();
            }
        });

        btn_drivinglicensefront.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        btn_drivinglicenseback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        btn_vehiclerc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
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

    public void imgpicker()
    {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                editTextdatepicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void captureImage()
    {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(OwnerRegistrationActivity.this);
        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (options[item].equals("Take Photo"))
                {
                    try
                    {
                        final Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                        // start the image capture Intent
                        startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }

                    catch(ActivityNotFoundException anfe)
                    {
                        String errorMessage = "Whoops - your device doesn't support capturing images!";
                        //Toast toast = Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT);
                        //toast.show();
                    }
                }

                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
                else if (options[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // if the result is capturing Image
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                fileUri = data.getData();
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            }
        }

        else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                previewCapturedImage();
            }
        }

        else if (requestCode == PIC_CROP) {
            // user cancelled Image capture
            Bundle extras = data.getExtras();
//get the cropped bitmap
            Bitmap thePic = extras.getParcelable("data");
            //uploadphoto.setImageBitmap(thePic);
        }
    }


    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage()
    {
        try
        {
            // hide video preview

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(fileUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");

        } else {
            return null;
        }

        return mediaFile;
    }



    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

    private void registerUser() {
        String firstname = editTextfirstname.getText().toString().trim().toLowerCase();
        String lastname = editTextlastname.getText().toString().trim().toLowerCase();
        String preferredname = editTextpreferredname.getText().toString().trim().toLowerCase();
        String mobilenumber = editTextmobilenumber.getText().toString().trim().toLowerCase();
        String emailid = editTextemailid.getText().toString().trim().toLowerCase();
        String password = editTextpassword.getText().toString().trim().toLowerCase();
        String drivinglicenseid = editTextdrivinglicense.getText().toString().trim().toLowerCase();
        String vehicleregistrationnumber = editTextvehicleregistration.getText().toString().trim().toLowerCase();
        String drivinglicensevalidity = editTextdrivinglicensevalidity.getText().toString().trim().toLowerCase();
        String vehiclemodel = editTextvehiclemodel.getText().toString().trim().toLowerCase();
        String vehiclebrand = spinnervehiclebrand.getSelectedItem().toString().trim().toLowerCase();

        register(firstname,lastname,preferredname,mobilenumber,emailid,password,drivinglicenseid,vehicleregistrationnumber,userGender,drivinglicensevalidity,vehiclemodel,vehiclebrand);
    }

    private void register(String firstname, String lastname, String preferredname, String mobilenumber, String emailid, String password, String drivinglicenseid, String vehicleregistrationnumber, String gender, String drivinglicensevalidity, String vehiclemodel, String vehiclebrand) {
        String urlSuffix = "?firstname="+firstname+"&lastname="+lastname+"&preferredname="+preferredname+"&mobilenumber="+mobilenumber+"&emailid="+emailid+"&password="+password+"&drivinglicenseid="+drivinglicenseid+"&vehicleregistrationnumber="+vehicleregistrationnumber+"&gender="+gender+"&drivinglicensevalidity="+drivinglicensevalidity+"&vehiclemodel="+vehiclemodel+"&vehiclebrand="+vehiclebrand;
        class RegisterUser extends AsyncTask<String, Void, String>{

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(OwnerRegistrationActivity.this, "Please Wait",null, true, true);
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