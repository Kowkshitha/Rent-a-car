package com.rentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    TextView tvregister,tvlogsstatus;
    EditText  etcusphone, etcuspass;
    Button btncuslogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvregister=(TextView)findViewById(R.id.tvregister);
        tvlogsstatus=(TextView)findViewById(R.id.tvlogcstatus);
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        etcusphone=(EditText)findViewById(R.id.etcusphone);

        etcuspass=(EditText)findViewById(R.id.etcuspassd);

        btncuslogin=(Button)findViewById(R.id.btncuslogin);

        btncuslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etcusphone.length()==0)
                {
                    Toast.makeText(LoginActivity.this,"Enter Username", Toast.LENGTH_LONG).show();}

                else if(etcuspass.length()==0)
                {
                    Toast.makeText(LoginActivity.this,"Enter Password", Toast.LENGTH_LONG).show();}

                else
                {
                    submitDataToserver();
                }
            }
        });
    }


    SharedPreferences sharedPreferences;
    public void submitDataToserver()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Login.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){
                            sharedPreferences = LoginActivity.this.getSharedPreferences("customer", Context.MODE_PRIVATE);//step 1
                            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();//step 2
                            prefsEditor.clear().commit();
                            prefsEditor.putString("login",etcusphone.getText().toString());
                            // prefsEditor.putString("login_type","ShelterProvider");
                            prefsEditor.commit();
                            Intent intent = new Intent(getApplicationContext(),CustomerHomeActivity.class);
                            //intent.putExtra("uname",etuser.getText().toString());
                            startActivity(intent);

                            // Toast.makeText(Register.this,response, Toast.LENGTH_LONG).show();


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid Details", Toast.LENGTH_SHORT).show();
                            tvlogsstatus.setText("Invalid Details");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("un",etcusphone.getText().toString());//
                params.put("pass",etcuspass.getText().toString());//


                return params;
            }
        }

                ;

        //3. add request to Queue
        queue.add(stringRequest);
    }

}
