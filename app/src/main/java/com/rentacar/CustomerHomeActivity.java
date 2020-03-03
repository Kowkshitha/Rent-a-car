package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class CustomerHomeActivity extends AppCompatActivity {

    ImageView imgprofile,imgviewcars,imgsearchcars,imgfeedback,imghelps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Home");

        imghelps=(ImageView)findViewById(R.id.imghelps);

        imghelps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CustomerHomeActivity.this,HelpActivity.class);
                startActivity(i);
            }
        });
        imgprofile=(ImageView)findViewById(R.id.imgprofile);

        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CustomerHomeActivity.this,CustomerprofileActivity.class);
                startActivity(i);
            }
        });

        imgviewcars=(ImageView)findViewById(R.id.imgviewcars);

        imgviewcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CustomerHomeActivity.this,CustomerViewCarsActivity.class);
                startActivity(i);
            }
        });
        imgsearchcars=(ImageView)findViewById(R.id.imgsearchcars);
        imgsearchcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CustomerHomeActivity.this,CustomerSearchCarActivity.class);
                startActivity(i);
            }
        });
        imgfeedback=(ImageView)findViewById(R.id.imgfeedback);
        imgfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CustomerHomeActivity.this,MyOrdersActivity.class);
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
                Intent i=new Intent(CustomerHomeActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
