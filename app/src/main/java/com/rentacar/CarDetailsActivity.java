package com.rentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CarDetailsActivity extends AppCompatActivity {


    TextView tt,tvgtype,tvgmake,tvgmodel,tvgyear,tvgcolor,tvgplate,etgetcarprice;
    String item, imgUrl,status;
    ImageView imggetphoto;

    Button btncheckstatus,btnbooknow;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Car Details");

        btnbooknow=(Button)findViewById(R.id.btnbooknow);
        btncheckstatus=(Button)findViewById(R.id.btncheckstatus);
        tt=(TextView)findViewById(R.id.tt);
        tvgtype=(TextView)findViewById(R.id.etgetcartype);
        tvgmake=(TextView)findViewById(R.id.etgetcarmake);
        tvgmodel=(TextView)findViewById(R.id.etgetcarmodel);
        tvgyear=(TextView)findViewById(R.id.etgetcaryear);
        tvgcolor=(TextView)findViewById(R.id.etgercarcolor);
        tvgplate=(TextView)findViewById(R.id.etgetcarplate);

        etgetcarprice=(TextView)findViewById(R.id.etgetcarprice);

        imggetphoto=(ImageView)findViewById(R.id.imggetphoto);
        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {



            id=extras1.getString("Id");
            tvgtype.setTag(extras1.getString("Id"));
            getDueDate(tvgtype.getTag().toString());
          //  Toast.makeText(CarDetailsActivity.this,id,Toast.LENGTH_LONG).show();

            tvgtype.setText(extras1.getString("type"));
            tvgmake.setText(extras1.getString("make"));
            tvgmodel.setText(extras1.getString("model"));
            tvgyear.setText(extras1.getString("year"));
            tvgcolor.setText(extras1.getString("color"));
            tvgplate.setText(extras1.getString("licenceplate"));
            imgUrl = extras1.getString("photo");
            etgetcarprice.setText(extras1.getString("price"));
            status=extras1.getString("status");

            if(status.equals("Available"))
            {

                        btncheckstatus.setVisibility(View.GONE);
            }
            else
            {
                btnbooknow.setVisibility(View.GONE);
            }

            Glide.with(CarDetailsActivity.this).load(imgUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .into(imggetphoto);

           // getDetails(fname);
        }


        btnbooknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CarDetailsActivity.this,BookNowActivity.class);


                i.putExtra("Id", id);
                i.putExtra("price", etgetcarprice.getText().toString());
                i.putExtra("licenceplate", tvgplate.getText().toString());


                startActivity(i);
            }
        });
    }

    public void getDueDate(final String id) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Rentacar/getreturndate.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressDoalog.dismiss();
                        // Toast.makeText(CarDetailsActivity.this, response, Toast.LENGTH_LONG).show();
                        parseServerJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CarDetailsActivity.this, "No Internet", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
//                sharedPreferences = CarDetailsActivity.this.getSharedPreferences("staff", Context.MODE_PRIVATE);
//                String value = sharedPreferences.getString("login", null);
                params.put("cid", id);//
                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    SharedPreferences sharedPreferences;

    ArrayList<HashMap<String, String>> student_info = new ArrayList<HashMap<String, String>>();

    private void parseServerJsonData(String response) {//Step -2
        HashMap<String, String> hm;
        try {
            JSONObject json = new JSONObject(response);
            JSONArray jArray = json.getJSONArray("data");
            // data=new String[jArray.length()];
            JSONObject jObj;
            for (int i = 0; i < jArray.length(); i++) {
                jObj = jArray.getJSONObject(i);
                hm = new HashMap<String, String>();
                hm.put("Id", jObj.getString("Id"));
                tt.setText("Available on"+jObj.getString("tdate"));

                student_info.add(hm);
            }
            // lv.setAdapter(new ChkStock.CustomAdapter(this, student_info));//Step -4
        } catch (JSONException e) {


        }
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
                Intent i=new Intent(CarDetailsActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
