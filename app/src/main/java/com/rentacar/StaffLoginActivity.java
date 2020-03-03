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

public class StaffLoginActivity extends AppCompatActivity {


    EditText etstaffun,etstaffpass ;
    Button btnslogin;
    TextView tvlogsstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        etstaffun=(EditText)findViewById(R.id.etstaffun);

        etstaffpass=(EditText)findViewById(R.id.etstaffpass);

        btnslogin=(Button)findViewById(R.id.btnstfflogin);
        tvlogsstatus=(TextView)findViewById(R.id.tvlogsstatus);

        btnslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etstaffun.length()==0)
                {
                    Toast.makeText(StaffLoginActivity.this,"Enter Username", Toast.LENGTH_LONG).show();}

                else if(etstaffpass.length()==0)
                {
                    Toast.makeText(StaffLoginActivity.this,"Enter Password", Toast.LENGTH_LONG).show();}

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/stafflogin.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Login.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){
                            sharedPreferences = StaffLoginActivity.this.getSharedPreferences("staff", Context.MODE_PRIVATE);//step 1
                            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();//step 2
                            prefsEditor.clear().commit();
                            prefsEditor.putString("login",etstaffun.getText().toString());
                            // prefsEditor.putString("login_type","ShelterProvider");
                            prefsEditor.commit();
                            Intent intent = new Intent(getApplicationContext(),StaffHomeActivity.class);
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
                Toast.makeText(StaffLoginActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("un",etstaffun.getText().toString());//
                params.put("pass",etstaffpass.getText().toString());//


                return params;
            }
        }

                ;

        //3. add request to Queue
        queue.add(stringRequest);
    }

}
