package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class UpdateCarsActivity extends AppCompatActivity {

    TextView tvgtype;
    EditText tvgmake,tvgmodel,tvgyear,tvgcolor,tvgplate;
    String item, imgUrl,id;
    ImageView imggetphoto;
    Button btnuapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cars);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Car Details");
        btnuapp=(Button)findViewById(R.id.btnuapp);

        tvgtype=(TextView)findViewById(R.id.etgetcarutype);
        tvgmake=(EditText)findViewById(R.id.etgetcarumake);
        tvgmodel=(EditText)findViewById(R.id.etgetcarumodel);
        tvgyear=(EditText)findViewById(R.id.etgetcaruyear);
        tvgcolor=(EditText)findViewById(R.id.etgercarucolor);
        tvgplate=(EditText)findViewById(R.id.etgetcaruplate);

        imggetphoto=(ImageView)findViewById(R.id.imggetuphoto);
        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {

            id=extras1.getString("Id");
           // Toast.makeText(UpdateCarsActivity.this,extras1.getString("photo"),Toast.LENGTH_LONG).show();


            tvgtype.setText(extras1.getString("type"));
            tvgmake.setText(extras1.getString("make"));
            tvgmodel.setText(extras1.getString("model"));
            tvgyear.setText(extras1.getString("year"));
            tvgcolor.setText(extras1.getString("color"));
            tvgplate.setText(extras1.getString("licenceplate"));
            imgUrl = extras1.getString("photo");

            Glide.with(UpdateCarsActivity.this).load(imgUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .into(imggetphoto);

            // getDetails(fname);
        }

        btnuapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDataToserver();
            }
        });
    }


    public void submitDataToserver()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/updatecar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UpdateCarsActivity.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){

                            Intent intent = new Intent(getApplicationContext(),StaffViewCarsActivity.class);
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
                Toast.makeText(UpdateCarsActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("id",id);//
                params.put("make",tvgmake.getText().toString());//
                params.put("model",tvgmodel.getText().toString());//
                params.put("year",tvgyear.getText().toString());//
                params.put("color",tvgcolor.getText().toString());//
                params.put("plate",tvgplate.getText().toString());//

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
                Intent i=new Intent(UpdateCarsActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
