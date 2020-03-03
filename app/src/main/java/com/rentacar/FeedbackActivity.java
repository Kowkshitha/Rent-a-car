package com.rentacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class FeedbackActivity extends AppCompatActivity {

    EditText tvoid,etfeedmsg;
    RatingBar rate;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Feedback");
        etfeedmsg = (EditText) findViewById(R.id.etfeedmsg);
        tvoid = (EditText) findViewById(R.id.etfeedoid);
        rate=(RatingBar)findViewById(R.id.rate);

        submit=(Button)findViewById(R.id.btnratenow);

        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {

//            Toast.makeText(CarDetailsActivity.this, id, Toast.LENGTH_LONG).show();

            tvoid.setText(extras1.getString("oid"));

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(etfeedmsg.length()==0)
                {
                    Toast.makeText(FeedbackActivity.this,"Enter Meassage", Toast.LENGTH_LONG).show();}

                else if(tvoid.length()==0)
                {
                    Toast.makeText(FeedbackActivity.this,"Enter Order Id", Toast.LENGTH_LONG).show();}

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/feedback.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Register.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){

                            Intent intent = new Intent(getApplicationContext(),CustomerHomeActivity.class);
                            //intent.putExtra("uname",etuser.getText().toString());
                            startActivity(intent);

                            Toast.makeText(FeedbackActivity.this,"Successfully Submitted", Toast.LENGTH_LONG).show();


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FeedbackActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("oid",tvoid.getText().toString());//
                params.put("rate",String.valueOf(rate.getRating()));//
                params.put("msg",etfeedmsg.getText().toString());//

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
                Intent i=new Intent(FeedbackActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
