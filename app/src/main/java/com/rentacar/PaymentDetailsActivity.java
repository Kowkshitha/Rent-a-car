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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PaymentDetailsActivity extends AppCompatActivity {

    String id,p,plate,fdate,tdate;
    TextView test;
    TextView tvgetmoney;
    Button btnmakepayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        setTitle("Payment Screen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvgetmoney=(TextView)findViewById(R.id.tvgetmoney);
        btnmakepayment=(Button)findViewById(R.id.btnmakepayment);

        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {


            id=(extras1.getString("Id"));
            p=(extras1.getString("price"));

            plate=(extras1.getString("licenceplate"));

            fdate=(extras1.getString("fdate"));

            tdate=(extras1.getString("tdate"));
            // Toast.makeText(CarDetailsActivity.this,extras1.getString("photo"),Toast.LENGTH_LONG).show();

           // test.setText(id+p+plate+fdate+tdate);
            tvgetmoney.setText(extras1.getString("price"));
        }

        btnmakepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDataToserver();
               // Toast.makeText(PaymentDetailsActivity.this,id+p+plate+fdate+tdate,Toast.LENGTH_LONG).show();

            }
        });
    }

    SharedPreferences sharedPreferences;
    public void submitDataToserver()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/payment.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Register.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){

                            Intent intent = new Intent(getApplicationContext(),SuccessActivity.class);
                            //intent.putExtra("uname",etuser.getText().toString());
                            startActivity(intent);

                            Toast.makeText(PaymentDetailsActivity.this,"Successfully Done", Toast.LENGTH_LONG).show();


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentDetailsActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                sharedPreferences = PaymentDetailsActivity.this.getSharedPreferences("customer", Context.MODE_PRIVATE);
                String user=sharedPreferences.getString("login",null);

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("user",user);//
                params.put("cid",id);//
                params.put("plate",plate);//
                params.put("amount",p);//
                params.put("fdate",fdate);//
                params.put("tdate",tdate);//

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
                Intent i=new Intent(PaymentDetailsActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
