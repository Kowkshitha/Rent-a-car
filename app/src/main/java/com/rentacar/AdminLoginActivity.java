package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    EditText etadminun,etadminpass;
    Button btnadminlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etadminun=(EditText)findViewById(R.id.etadminun);
        etadminpass=(EditText)findViewById(R.id.etadminpass);
        btnadminlogin=(Button)findViewById(R.id.btnadminlogin);

        btnadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etadminun.length()==0)
                {
                    Toast.makeText(AdminLoginActivity.this,"Enter Username", Toast.LENGTH_LONG).show();}

                else if(etadminpass.length()==0)
                {
                    Toast.makeText(AdminLoginActivity.this,"Enter Password", Toast.LENGTH_LONG).show();}
                else
                {
                    if (etadminun.getText().toString().equals("admin") && etadminpass.getText().toString().equals("admin"))
                    {
                        Intent i=new Intent(AdminLoginActivity.this,AdminHomeActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(AdminLoginActivity.this,"Invalid Details", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
