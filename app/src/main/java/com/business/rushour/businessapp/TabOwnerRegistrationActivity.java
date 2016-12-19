package com.business.rushour.businessapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by deepak on 05/12/16.
 */

public class TabOwnerRegistrationActivity extends Fragment{

    private EditText input_firstname;
    private EditText input_lastname;
    private EditText input_preferredname;
    private RadioGroup radio_gender;
    private EditText input_mobilenumber;
    private EditText input_emailid;
    private EditText input_password;
    private Button btn_drivinglicensefront;
    private Button btn_drivinglicenseback;
    private Button btn_vehiclerc;
    private EditText input_drivinglicenseid;
    private EditText license_validity;
    private Spinner dropdown_vehicletype;
    private EditText input_vehiclemodel;
    private EditText input_vehicleregistrationnumber;
    private ImageView img_datepicker;
    EditText datepicker;
    private int year, month, day, hour, minute;



    private static final String REGISTER_URL = "http://sampletestingservice.3eeweb.com/Rushour/register.php";

    final int PIC_CROP = 2;

    private Bitmap bitmap;
    //Button btn_driving = (Button) getView().findViewById(R.id.btn_drivinglicense);
    //ImageView driving = (ImageView) getView().findViewById(R.id.img_datepicker);

    private int PICK_IMAGE_REQUEST = 1;


    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static int RESULT_LOAD_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Download";

    private Uri fileUri; // file url to store image/video


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_ownerregistration, container, false);
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                String firstname = input_firstname.getText().toString().trim().toLowerCase();
                String lastname = input_lastname.getText().toString().trim().toLowerCase();
                String preferredname = input_preferredname.getText().toString().trim().toLowerCase();
                String gender = radio_gender.toString().trim().toLowerCase();
                String mobilenumber = input_mobilenumber.getText().toString().trim().toLowerCase();
                String emailid = input_emailid.getText().toString().trim().toLowerCase();
                String password = input_password.getText().toString().trim().toLowerCase();
                String drivinglicensefrontimage = btn_drivinglicensefront.getText().toString().trim().toLowerCase();
                String drivinglicensebackimage = btn_drivinglicenseback.getText().toString().trim().toLowerCase();
                String vehiclercimage = btn_vehiclerc.getText().toString().trim().toLowerCase();
                String drivinglicenseid = input_drivinglicenseid.getText().toString().trim().toLowerCase();
                String drivinglicensevalidity = license_validity.getText().toString().trim().toLowerCase();
                String vehiclebrand = dropdown_vehicletype.toString().trim().toLowerCase();
                String vehiclename = input_vehiclemodel.getText().toString().trim().toLowerCase();
                String vehicleregistrationnumber = input_vehicleregistrationnumber.getText().toString().trim().toLowerCase();


                //if (editgender.getCheckedRadioButtonId() == editmale.getId()) {
                //  selectgender = "Male";
                //} else if (editgender.getCheckedRadioButtonId() == editfemale.getId()) {
                //  selectgender = "Female";
                //}

                register(firstname, lastname, preferredname, gender, mobilenumber, emailid, password, drivinglicensefrontimage, drivinglicensebackimage, vehiclercimage, drivinglicenseid, drivinglicensevalidity, vehiclebrand, vehiclename, vehicleregistrationnumber);
            }

        });

        view.findViewById(R.id.btn_drivinglicensefront).setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(final View view)
        {
            captureImage(view);
        }
    });

        view.findViewById(R.id.btn_drivinglicenseback).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                captureImage(view);
            }
        });

        view.findViewById(R.id.btn_vehiclerc).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                captureImage(view);
            }
        });

    }

    public void captureImage(View view)
    {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
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

    private void register(String firstname, String lastname, String preferredname, String gender, String mobilenumber, String emailid, String password, String drivinglicensefrontimage, String drivinglicensebackimage, String vehiclercimage, String drivinglicenseid, String drivinglicensevalidity, String vehiclebrand, String vehiclename, String vehicleregistrationnumber)
    {
        String urlSuffix = "?firstname="+firstname+"&lastname="+lastname+"&preferredname="+preferredname+"gender="+gender+"&mobilenumber="+mobilenumber+"&emailid="+emailid+"&password="+password+"&drivinglicensefrontimage="+drivinglicensefrontimage+"&drivinglicensebackimage="+drivinglicensebackimage+"&vehiclercimage="+vehiclercimage+"&drivinglicenseid="+drivinglicenseid+"&drivinglicensevalidity="+drivinglicensevalidity+"&vehiclebrand="+vehiclebrand+"&vehiclename="+vehiclename+"&vehicleregistrationnumber="+vehicleregistrationnumber;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(TabOwnerRegistrationActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
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
