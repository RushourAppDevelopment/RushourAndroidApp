package com.business.rushour.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by deepak on 05/12/16.
 */

public class ChangePasswordActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button changepasswordbtn = (Button) findViewById(R.id.btn_changepassword);

        changepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmpassword();
            }
        });
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

    public void confirmpassword()
    {
        Intent login = new Intent(this, OwnerMainActivity.class);
        startActivity(login);
    }
}
