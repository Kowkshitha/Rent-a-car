package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class AdminHomeActivity extends AppCompatActivity {

    ImageView imgaddstaff,imgviewadmincars,imgadminviewstaff,imgadminreports,imgadminfeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
       // setContentView(R.layout.activity_staff_profile);
        setTitle("Admin Home");
        imgaddstaff=(ImageView)findViewById(R.id.imgaddstaff);
        imgaddstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(AdminHomeActivity.this,AddStaffActivity.class);
                startActivity(i);
            }
        });

        imgviewadmincars=(ImageView)findViewById(R.id.imgviewadmincars);
        imgviewadmincars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminHomeActivity.this,AdminViewCarsActivity.class);
                startActivity(i);
            }
        });

        imgadminreports=(ImageView)findViewById(R.id.imgadminreports);
        imgadminreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminHomeActivity.this,AdminReportsActivity.class);
                startActivity(i);
            }
        });

        imgadminfeedback=(ImageView)findViewById(R.id.imgadminfeedback);
        imgadminfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminHomeActivity.this,AdminFeedbackActivity.class);
                startActivity(i);
            }
        });


        imgadminviewstaff=(ImageView)findViewById(R.id.imgadminviewstaff);
        imgadminviewstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminHomeActivity.this,AdminViewStaffActivity.class);
                startActivity(i);
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idlogout:
                // Toast.makeText(getApplicationContext(),"logout Selected",Toast.LENGTH_LONG).show();
                Intent i=new Intent(AdminHomeActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
