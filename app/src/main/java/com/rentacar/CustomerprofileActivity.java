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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerprofileActivity extends AppCompatActivity {

    EditText etfn,etln,etdl,etexp,etpass,etphone;
    Button btnupdatemyprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerprofile);
        setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etfn = (EditText) findViewById(R.id.etgetcfn);
        etln = (EditText) findViewById(R.id.etgetcsn);
        etdl = (EditText) findViewById(R.id.etgetcdl);
        etexp = (EditText) findViewById(R.id.etgetcexpdate);
        etpass = (EditText) findViewById(R.id.etgetcpassword);
        etphone = (EditText) findViewById(R.id.etgetcphone);
        btnupdatemyprofile=(Button)findViewById(R.id.btnupdatemyprofile);
        btnupdatemyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etfn.length()==0)
                {
                    Toast.makeText(CustomerprofileActivity.this,"Enter First Name", Toast.LENGTH_LONG).show();}

                else if(etln.length()==0)
                {
                    Toast.makeText(CustomerprofileActivity.this,"Enter Lastname", Toast.LENGTH_LONG).show();}

                else if(etpass.length()==0)
                {
                    Toast.makeText(CustomerprofileActivity.this,"Enter Password", Toast.LENGTH_LONG).show();}





                else if(etphone.length()==0)
                {
                    Toast.makeText(CustomerprofileActivity.this,"Enter Phone", Toast.LENGTH_LONG).show();}

                else if(etdl.length()==0)
                {
                    Toast.makeText(CustomerprofileActivity.this,"Enter License", Toast.LENGTH_LONG).show();}

                else if(etexp.length()==0)
                {
                    Toast.makeText(CustomerprofileActivity.this,"Enter Expiry Date", Toast.LENGTH_LONG).show();}

                else
                {
                    submitDataToserver();
                }
            }
        });
        getprofile();
    }


    public void submitDataToserver()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/updatemyprofile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CustomerprofileActivity.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){

                            Intent intent = new Intent(getApplicationContext(),CustomerHomeActivity.class);
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
                Toast.makeText(CustomerprofileActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
//                params.put("id",id);//
                params.put("fn",etfn.getText().toString());//
                params.put("ln",etln.getText().toString());//
                params.put("phone",etphone.getText().toString());//
                params.put("pass",etpass.getText().toString());//
                params.put("dl",etdl.getText().toString());//
                params.put("exp",etexp.getText().toString());//
                return params;
            }
        }

                ;

        //3. add request to Queue
        queue.add(stringRequest);
    }

    public void getprofile() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Rentacar/customerprofile.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressDoalog.dismiss();
                        //  Toast.makeText(CustomerprofileActivity.this, response, Toast.LENGTH_LONG).show();
                          parseServerJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CustomerprofileActivity.this, "No Internet", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = CustomerprofileActivity.this.getSharedPreferences("customer", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                params.put("un",value);//
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
                etfn.setText(jObj.getString("first_name"));
                etln.setText(jObj.getString("last_name"));

                etdl.setText(jObj.getString("driver_licence"));

                etexp.setText(jObj.getString("expire_date"));

                etphone.setText(jObj.getString("phone"));

                etpass.setText(jObj.getString("pass"));

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
                Intent i=new Intent(CustomerprofileActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}