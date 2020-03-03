package com.rentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddCarsActivity extends AppCompatActivity {



    Spinner sptype,spitemname;
    EditText etmake,etmodel,etyear,etcolor,etplate,etcarprice;
    Button btnaddcar,btn_gallery,btn_camera;
    ImageView iv_img;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int PICK_IMAGE_REQUEST=3;
    byte img_store[]=null;
    String encodedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cars);
        setTitle("Add New Car");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sptype=(Spinner)findViewById(R.id.spcartype);
        etcarprice=(EditText)findViewById(R.id.etcarprice);
        etmake=(EditText)findViewById(R.id.etcarmake);
        etmodel=(EditText)findViewById(R.id.etcarmodel);
        etyear=(EditText)findViewById(R.id.etcaryear);
        etcolor=(EditText)findViewById(R.id.etcarcolor);
        etplate=(EditText)findViewById(R.id.etcarnumber);
        iv_img=(ImageView)findViewById(R.id.iv_img);
        btn_gallery=(Button)findViewById(R.id.btn_gallery);
        btn_camera=(Button)findViewById(R.id.btn_camera);

        btnaddcar=(Button) findViewById(R.id.btnsaddcar);
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_store=null;
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
            }
        });
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                img_store=null;
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                img_store=null;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        btnaddcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(img_store==null) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_no_image);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                    img_store = stream.toByteArray();
                    Toast.makeText(getApplicationContext(),"No Image seleted.",Toast.LENGTH_SHORT).show();
                }
                else if(etmodel.length()==0)
                {
                    Toast.makeText(AddCarsActivity.this,"Enter Model", Toast.LENGTH_LONG).show();}
                else if(etmake.length()==0)
                {
                    Toast.makeText(AddCarsActivity.this,"Enter Make", Toast.LENGTH_LONG).show();}
                else if(etyear.length()==0)
                {
                    Toast.makeText(AddCarsActivity.this,"Enter Year", Toast.LENGTH_LONG).show();}

                else
                {

                    submitDataToserver();
                    // submitDataToserver();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE) {
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                img_store = stream.toByteArray();
                iv_img.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST) {
            try {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                    img_store = stream.toByteArray();
                    iv_img.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    SharedPreferences sharedPreferences;
    public void submitDataToserver()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.  Prepare  Your Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://possakrishna.com/Rentacar/addcar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Toast.makeText(AddFarmActivity.this,response, Toast.LENGTH_LONG).show();
                        if(response.equalsIgnoreCase("success")){

                            Intent intent = new Intent(getApplicationContext(),StaffHomeActivity.class);
                            //intent.putExtra("uname",etuser.getText().toString());
                            startActivity(intent);

                            Toast.makeText(AddCarsActivity.this,"Car Added Successfully", Toast.LENGTH_LONG).show();


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Licence Plate Must be unique", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddCarsActivity.this,"No Internet", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                sharedPreferences = AddCarsActivity.this.getSharedPreferences("staff", Context.MODE_PRIVATE);
                String value=sharedPreferences.getString("login",null);
                encodedImage = Base64.encodeToString(img_store, Base64.DEFAULT);
                Map<String, String> params = new HashMap<String, String>();
                params.put("photo",encodedImage);
                params.put("type",sptype.getSelectedItem().toString());//
                params.put("make",etmake.getText().toString());//
                params.put("model",etmodel.getText().toString());//
                params.put("year",etyear.getText().toString());//
                params.put("color",etcolor.getText().toString());//
                params.put("price",etcarprice.getText().toString());//


              //  params.put("funame",value);//

                params.put("plate",etplate.getText().toString());//


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
                Intent i=new Intent(AddCarsActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
