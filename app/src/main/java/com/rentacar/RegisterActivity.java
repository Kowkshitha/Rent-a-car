package com.rentacar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity  {


    EditText etfn,etln,etphone,etpass,etlicence,edittext;

    Button btnregister;
    DatePickerDialog.OnDateSetListener date;
    private EditText etexpiry;
    Calendar myCalendar;
    private DatePickerDialog fromDatePickerDialog;
    DatePicker picker;

    private ImageButton ib_from_date;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Customer Registration");

        etfn=(EditText)findViewById(R.id.etfname);

        etln=(EditText)findViewById(R.id.etlname);
        etphone=(EditText)findViewById(R.id.etphone);
        etpass=(EditText)findViewById(R.id.etpass);
        etlicence=(EditText)findViewById(R.id.etlicence);
        etexpiry=(EditText)findViewById(R.id.etexpirydate);
        ib_from_date=(ImageButton)findViewById(R.id.ib_to_date);
        btnregister=(Button)findViewById(R.id.btnregister);

        myCalendar = Calendar.getInstance();

         edittext= (EditText) findViewById(R.id.Birthday);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etexpiry.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(etfn.length()==0)
                {
                    Toast.makeText(RegisterActivity.this,"Enter First Name", Toast.LENGTH_LONG).show();}

                else if(etln.length()==0)
                {
                    Toast.makeText(RegisterActivity.this,"Enter Lastname", Toast.LENGTH_LONG).show();}

                else if(etpass.length()==0 || etpass.length()<6)
                {
                    Toast.makeText(RegisterActivity.this,"Password must contain min 6 characters", Toast.LENGTH_LONG).show();}





                else if(etphone.length()==0 || etphone.length()>10 ||etphone.length()<10)
                {
                    Toast.makeText(RegisterActivity.this,"Enter Valid Phone Number", Toast.LENGTH_LONG).show();}

                else if(etlicence.length()==0)
                {
                    Toast.makeText(RegisterActivity.this,"Enter License", Toast.LENGTH_LONG).show();}

                else if(etexpiry.length()==0)
                {
                    Toast.makeText(RegisterActivity.this,"Enter Expiry Date", Toast.LENGTH_LONG).show();}

                else
                {
                    submitDataToserver();
                }
            }
        });
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etexpiry.setText(sdf.format(myCalendar.getTime()));
    }


    public void submitDataToserver()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Register.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){

                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            //intent.putExtra("uname",etuser.getText().toString());
                            startActivity(intent);

                            Toast.makeText(RegisterActivity.this,"Successfully Registered", Toast.LENGTH_LONG).show();


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("fn",etfn.getText().toString());//
                params.put("ln",etln.getText().toString());//
                params.put("pass",etpass.getText().toString());//
                params.put("phone",etphone.getText().toString());//
                params.put("dl",etlicence.getText().toString());//
                params.put("edate",etexpiry.getText().toString());//
                return params;
            }
        }

                ;

        //3. add request to Queue
        queue.add(stringRequest);
    }
}
