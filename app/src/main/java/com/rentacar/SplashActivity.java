package com.rentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        final int ScreenDisplay = 2000;
        Thread t1=new Thread(){
            int wait1=0;
            public void run(){
                try{
                    while(wait1<=ScreenDisplay )
                    {
                        sleep(100);
                        wait1+=100;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    sharedPreferences = SplashActivity.this.getSharedPreferences("bus", Context.MODE_PRIVATE);
                    String value=sharedPreferences.getString("login", "defalt");
                    if(!value.equalsIgnoreCase("yes")) {
                        Intent intentg = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intentg);
                        finish();
                    }
                    else{
                        Intent intentg = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intentg);
                        finish();
                    }
                }
            }
        };
        t1.start();


    }

    SharedPreferences sharedPreferences;


}
