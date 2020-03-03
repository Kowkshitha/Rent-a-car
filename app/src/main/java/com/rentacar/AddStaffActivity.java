package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddStaffActivity extends AppCompatActivity {

    EditText etsname,etsemail,etsphone,etspass,etaddsconfirmpass;

    Button btnaddstaff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        setTitle("Add Staff");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etsname=(EditText)findViewById(R.id.etaddsname);

        etsemail=(EditText)findViewById(R.id.etaddsemail);
        etsphone=(EditText)findViewById(R.id.etaddsphone);
        etspass=(EditText)findViewById(R.id.etaddspass);
        etaddsconfirmpass=(EditText)findViewById(R.id.etaddsconfirmpass);
        btnaddstaff=(Button)findViewById(R.id.btnaddstaff);






        btnaddstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(etsname.length()==0)
                {
                    Toast.makeText(AddStaffActivity.this,"Enter Name", Toast.LENGTH_LONG).show();}

                else if(etsphone.length()==0 || etsphone.length()>10 ||etsphone.length()<10)
                {
                    Toast.makeText(AddStaffActivity.this,"Enter Valid Phone Number", Toast.LENGTH_LONG).show();}

                else if(etsemail.length()==0)
                {
                    Toast.makeText(AddStaffActivity.this,"Enter Email", Toast.LENGTH_LONG).show();}

                else if(etaddsconfirmpass.length()==0)
                {
                    Toast.makeText(AddStaffActivity.this,"Enter Confirm Password", Toast.LENGTH_LONG).show();}



                else if(etspass.length()==0|| etspass.length()<6)
                {
                    Toast.makeText(AddStaffActivity.this,"Password must contain min 6 characters", Toast.LENGTH_LONG).show();}

                else
                {
                    submitDataToserver();
                }
            }
        });
    }


    public void submitDataToserver()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/addstaff.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Register.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){

                            Intent intent = new Intent(getApplicationContext(),AdminHomeActivity.class);
                            //intent.putExtra("uname",etuser.getText().toString());
                            startActivity(intent);

                            Toast.makeText(AddStaffActivity.this,"Successfully Registered", Toast.LENGTH_LONG).show();


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddStaffActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("name",etsname.getText().toString());//
                params.put("phone",etsphone.getText().toString());//
                params.put("email",etsemail.getText().toString());//
                params.put("pass",etspass.getText().toString());//

                return params;
            }
        }

                ;

        //3. add request to Queue
        queue.add(stringRequest);
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
                Intent i=new Intent(AddStaffActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
