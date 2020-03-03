package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

public class UpdateStaffActivity extends AppCompatActivity {

    EditText etsname,etsemail,etsphone,etspass;
    Button btnupdatestaff;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Staff Details");

        etsname=(EditText)findViewById(R.id.etusname);
        etsemail=(EditText)findViewById(R.id.etusemail);
        etsphone=(EditText)findViewById(R.id.etusphone);
        etspass=(EditText)findViewById(R.id.etuspass);
        btnupdatestaff=(Button)findViewById(R.id.btnustaff) ;

        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {

            id=extras1.getString("Id");
            //Toast.makeText(UpdateStaffActivity.this,id,Toast.LENGTH_LONG).show();

            etsname.setText(extras1.getString("name"));
            etsemail.setText(extras1.getString("email"));
            etsphone.setText(extras1.getString("phone"));
            etspass.setText(extras1.getString("pass"));



        }

        btnupdatestaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsname.length()==0)
                {
                    Toast.makeText(UpdateStaffActivity.this,"Enter Name", Toast.LENGTH_LONG).show();}

                else if(etsphone.length()==0)
                {
                    Toast.makeText(UpdateStaffActivity.this,"Enter Phone", Toast.LENGTH_LONG).show();}

                else if(etsemail.length()==0)
                {
                    Toast.makeText(UpdateStaffActivity.this,"Enter Email", Toast.LENGTH_LONG).show();}





                else if(etspass.length()==0)
                {
                    Toast.makeText(UpdateStaffActivity.this,"Enter Password", Toast.LENGTH_LONG).show();}

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/updatestaff.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UpdateStaffActivity.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){

                            Intent intent = new Intent(getApplicationContext(),AdminViewStaffActivity.class);
                            //intent.putExtra("uname",etuser.getText().toString());
                            startActivity(intent);

                            // Toast.makeText(Register.this,response, Toast.LENGTH_LONG).show();


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Updation Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateStaffActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("id",id);//
                params.put("name",etsname.getText().toString());//
                params.put("email",etsemail.getText().toString());//
                params.put("phone",etsphone.getText().toString());//
                params.put("pass",etspass.getText().toString());//

                return params;
            }
        }

                ;

        //3. add request to Queue
        queue.add(stringRequest);
    }
}
