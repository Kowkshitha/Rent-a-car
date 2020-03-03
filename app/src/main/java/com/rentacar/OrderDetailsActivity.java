package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView tvoid,tvcid,tvplate,tvuser,tvdate,tvfdate,tvtdate,tvamount;
   Button btnreturn,btnfeedback;
   String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Order Details");

        btnreturn=(Button)findViewById(R.id.btnoreturn);
        btnfeedback=(Button)findViewById(R.id.btnfeedback);

        tvoid=(TextView)findViewById(R.id.tvoid);
        tvcid=(TextView)findViewById(R.id.tvocid);
        tvplate=(TextView)findViewById(R.id.tvoplate);
        tvuser=(TextView)findViewById(R.id.tvouser);
       // tvdate=(TextView)findViewById(R.id.tvo);
        tvfdate=(TextView)findViewById(R.id.tvofrom);

        tvtdate=(TextView)findViewById(R.id.tvoto);


        tvamount=(TextView)findViewById(R.id.tvoamount);
        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {

//            Toast.makeText(CarDetailsActivity.this, id, Toast.LENGTH_LONG).show();

            tvoid.setText(extras1.getString("Id"));
            tvoid.setTag(extras1.getString("Id"));
            tvcid.setText(extras1.getString("cid"));
            tvcid.setTag(extras1.getString("cid"));
            tvplate.setText(extras1.getString("plate"));
            tvuser.setText(extras1.getString("user"));
            tvfdate.setText(extras1.getString("fdate"));
            tvtdate.setText(extras1.getString("tdate"));
            tvamount.setText(extras1.getString("amount"));
            status=extras1.getString("status");

            if(status.equals("Booked"))
            {

                btnreturn.setVisibility(View.VISIBLE);
                btnreturn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitDataToserver(tvoid.getTag().toString(),tvcid.getTag().toString());
                       // Toast.makeText(OrderDetailsActivity.this, tvcid.getTag().toString(), Toast.LENGTH_LONG).show();


                    }
                });
            }
            else
            {
                btnreturn.setVisibility(View.GONE);
            }
        }

        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FeedbackActivity.class);
                intent.putExtra("oid",tvoid.getTag().toString());
                startActivity(intent);

            }
        });
    }


    public void submitDataToserver(final String id, final String cid)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/returncar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(OrderDetailsActivity.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){

                            Intent intent = new Intent(getApplicationContext(),MyOrdersActivity.class);
                            //intent.putExtra("uname",etuser.getText().toString());
                            startActivity(intent);

                            Toast.makeText(OrderDetailsActivity.this,"Returned Successfully", Toast.LENGTH_LONG).show();


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Updation Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderDetailsActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("id",id);//
                params.put("cid",cid);//
                return params;
            }
        }

                ;

        //3. add request to Queue
        queue.add(stringRequest);
    }
}
