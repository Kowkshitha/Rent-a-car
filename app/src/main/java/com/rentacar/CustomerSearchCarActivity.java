package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class CustomerSearchCarActivity extends AppCompatActivity {

    Spinner spcarsearchtype,spyeartype;
    Button btnsearchcar;
    String word[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_car);
        spcarsearchtype=(Spinner)findViewById(R.id.spcarsearchtype);
        spyeartype=(Spinner)findViewById(R.id.spyeartype);
        btnsearchcar=(Button)findViewById(R.id.btnsearchcar);
        programs();
        btnsearchcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CustomerSearchCarActivity.this,CusearchCarsListActivity.class);

                i.putExtra("type", spcarsearchtype.getSelectedItem().toString());
                i.putExtra("year", spyeartype.getSelectedItem().toString());

                startActivity(i);
            }
        });

    }

    public  void programs() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Rentacar/getyear.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(ProgramsActivity.this,response,Toast.LENGTH_LONG).show();
                        getDataFromServer(response);
//
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CustomerSearchCarActivity.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    ArrayList<HashMap<String, String>> student_info;

    private void getDataFromServer(String response) {//Step -2
        try {
            JSONObject json = new JSONObject(response);
            JSONArray jArray = json.getJSONArray("data");
            // data=new String[jArray.length()];
            JSONObject jObj;

            word=new String[jArray.length()];
            for(int i=0;i<jArray.length();i++) {
                jObj = jArray.getJSONObject(i);
                word[i]=jObj.getString("year");

            }


        } catch (JSONException e) {}
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, word);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spyeartype.setAdapter(spinnerArrayAdapter);

    }

}
