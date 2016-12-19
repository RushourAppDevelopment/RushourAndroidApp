package com.business.rushour.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by deepak on 27/11/16.
 */

public class PostRideSuccessScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride_success);

        Button okbtn = (Button) findViewById(R.id.btn_ok);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok();
            }
        });
    }

    public void ok()
    {
        Intent login = new Intent(this, OwnerMainActivity.class);
        startActivity(login);
    }
}
