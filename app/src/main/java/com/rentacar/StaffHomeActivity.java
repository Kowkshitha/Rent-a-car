package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StaffHomeActivity extends AppCompatActivity {

    Button btnsaddcars,btnviewcars, btnsprofile,brnsfeedbacks,btnstaffbookings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);
        setTitle("Home Screen");
        btnsprofile=(Button)findViewById(R.id.btnstaffprofile);
        btnsaddcars=(Button)findViewById(R.id.btnsaddcars);
        btnviewcars=(Button)findViewById(R.id.btnsviewcars);
        brnsfeedbacks=(Button)findViewById(R.id.btnstafffeed);
        btnstaffbookings=(Button)findViewById(R.id.btnstaffbookings);


        btnstaffbookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StaffHomeActivity.this,StaffViewBookingsActivity.class);
                startActivity(i);
            }
        });

        btnsprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StaffHomeActivity.this,StaffProfileActivity.class);
                startActivity(i);
            }
        });

        btnsaddcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StaffHomeActivity.this,AddCarsActivity.class);
                startActivity(i);
            }
        });
        btnviewcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StaffHomeActivity.this,StaffViewCarsActivity.class);
                startActivity(i);
            }
        });
        brnsfeedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StaffHomeActivity.this,StaffViewFeedbacksActivity.class);
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
                Intent i=new Intent(StaffHomeActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
