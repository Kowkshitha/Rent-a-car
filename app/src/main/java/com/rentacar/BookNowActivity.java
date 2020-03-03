package com.rentacar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookNowActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvamount;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private ImageButton ib_from_date,ib_to_date;
    private SimpleDateFormat dateFormatter;
    Button btnpay;
    String id,p,plate,from_date,to_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        setTitle("Check Out");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        findViewsById();
        setDateTimeField();

        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {


            id=(extras1.getString("Id"));
             p=(extras1.getString("price"));

             plate=(extras1.getString("licenceplate"));

            // Toast.makeText(CarDetailsActivity.this,extras1.getString("photo"),Toast.LENGTH_LONG).show();

          //  tvamount.setText(id+p+plate);

        }
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.et_from_date);
        btnpay = (Button) findViewById(R.id.btnpay);
        toDateEtxt = (EditText) findViewById(R.id.toDate);
        ib_from_date = (ImageButton) findViewById(R.id.ib_from_date);
        ib_to_date = (ImageButton) findViewById(R.id.ib_to_date);
        tvamount=(TextView)findViewById(R.id.tvamount);

    }

    private void setDateTimeField() {
        ib_from_date.setOnClickListener(this);
        ib_to_date.setOnClickListener(this);
        btnpay.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }



    public void onClick(View view) {
        if (view == ib_from_date) {
            fromDatePickerDialog.show();
        } else if (view == ib_to_date) {
            toDatePickerDialog.show();
        }
        if (view == btnpay) {
            if (fromDateEtxt.getText().toString().trim().length() == 0 && toDateEtxt.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter date");
                return;
            }
             from_date=fromDateEtxt.getText().toString().trim();
             to_date=toDateEtxt.getText().toString().trim();


            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date fdate = format.parse(from_date);
                Date tdate = format.parse(to_date);

            //  int n=  getDaysDifference(fdate,tdate);
                long diff = tdate.getTime() - fdate.getTime();
                int daysDifference = (int) (diff / (1000 * 60 * 60 * 24));

               int price=Integer.parseInt(p);

                tvamount.setText("Total amount is : "+String.valueOf(price*daysDifference)+" $" );

                btnpay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(BookNowActivity.this,PaymentDetailsActivity.class);


                        i.putExtra("Id", id);
                        i.putExtra("price", tvamount.getText().toString());
                        i.putExtra("licenceplate", plate);
                        i.putExtra("fdate", from_date);
                        i.putExtra("tdate", to_date);

                        startActivity(i);
                    }
                });


            } catch (ParseException e) {
                //Handle exception here, most of the time you will just log it.
                e.printStackTrace();
            }


           // tvamount.setText(to_date-from_date);


           // getDaysDifference(from_date,to_date);
//            Intent intent=new Intent(getApplicationContext(),TestMap.class);
//            intent.putExtra("category",category.getSelectedItem().toString());
//            intent.putExtra("s_date",from_date);
//            intent.putExtra("e_date",to_date);
//            startActivity(intent);

           // clearText();
        }


    }


    public static int getDaysDifference(Date fromDate, Date toDate)
    {
        if(fromDate==null||toDate==null)
            return 0;

        return (int)( (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        fromDateEtxt.setText("");
        toDateEtxt.setText("");
        fromDateEtxt.requestFocus();
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
                Intent i=new Intent(BookNowActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
