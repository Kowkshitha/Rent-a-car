package com.rentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerViewCarsActivity extends AppCompatActivity {

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_cars);
        lv=(ListView)findViewById(R.id.listgetcars);
        // Toast.makeText(myservices.this,"hello world",Toast.LENGTH_LONG).show();
        setTitle("Cars List");
        getrecords();
    }

    public  void getrecords() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Rentacar/getcars.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressDoalog.dismiss();
                       // Toast.makeText(CustomerViewCarsActivity.this,response,Toast.LENGTH_LONG).show();

                       // Toast.makeText(CustomerViewCarsActivity.this,"Hello world",Toast.LENGTH_LONG).show();

                        parseServerJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CustomerViewCarsActivity.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        });
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
                hm=new HashMap<String, String>();
                hm.put("Id", jObj.getString("Id"));
                hm.put("type", jObj.getString("type"));
                hm.put("make", jObj.getString("make"));
                hm.put("model", jObj.getString("model"));
                hm.put("year", jObj.getString("year"));
                hm.put("photo", "http://possakrishna.com/Rentacar/"+jObj.getString("photo"));
                hm.put("color", jObj.getString("color"));
                hm.put("status", jObj.getString("status"));
                hm.put("licence_plate", jObj.getString("licence_plate"));
                hm.put("price", jObj.getString("price"));

                student_info.add(hm);
            }
            lv.setAdapter(new CustomerViewCarsActivity.CustomAdapter(this, student_info));//Step -4
        } catch (JSONException e) {}

    }


    private static LayoutInflater inflater=null;
    public class CustomAdapter extends BaseAdapter {   //Step -3

        Context context;
        ArrayList<HashMap<String, String>> alData1;
        public CustomAdapter(Context mainActivity, ArrayList<HashMap<String, String>> al) {
            alData1=al;
            context=mainActivity;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return alData1.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = inflater.inflate(R.layout.childviewcars, null);
            }
            HashMap<String, String> hm=alData1.get(position);


             // String imgUrl = "http://vaweinstitutes.com/Androidsampledbphp/Ravisampleapp/Images/furniture.png";

           final String imgUrl=hm.get("photo");
            final ImageView img=(ImageView) convertView.findViewById(R.id.itemimage);
           // ImageView.setTag(hm.get("photo"));

            final TextView tv7=(TextView) convertView.findViewById(R.id.viewcarslplate);

            tv7.setTag(imgUrl);



            Glide.with(context).load(imgUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .into(img);

           final TextView tv1=(TextView) convertView.findViewById(R.id.viewcarsmake);
            tv1.setText("Make : "+hm.get("make"));
            tv1.setTag(hm.get("make"));

            final TextView tv2=(TextView) convertView.findViewById(R.id.viewcarsmodel);
            tv2.setText("Model : "+hm.get("model"));
            tv2.setTag(hm.get("model"));

           final TextView tv3=(TextView) convertView.findViewById(R.id.viewcarstype);
            tv3.setText("Type : "+hm.get("type"));
            tv3.setTag(hm.get("type"));

            final TextView tv4=(TextView) convertView.findViewById(R.id.viewcarscolor);
            tv4.setText("Color : "+hm.get("color"));
            tv4.setTag(hm.get("color"));


            final TextView tv5 = (TextView) convertView.findViewById(R.id.viewcarsyear);
            tv5.setText("Year : "+hm.get("year"));
            tv5.setTag(hm.get("year"));
//            tv5.setVisibility(View.GONE);
            //  tv5.setText("Reply: " + hm.get("farmsg"));

            final TextView tv6=(TextView) convertView.findViewById(R.id.viewcarslplate);
            tv6.setText("Licence : "+hm.get("licence_plate"));
            tv6.setTag(hm.get("licence_plate"));

            final TextView tv8=(TextView) convertView.findViewById(R.id.viewcarstatus);
            tv8.setText(hm.get("status"));
            tv8.setTag(hm.get("status"));
            final TextView tv9=(TextView) convertView.findViewById(R.id.tvprice);
            tv9.setText("$"+hm.get("price")+"/Day");
            tv9.setTag(hm.get("price"));

            final ImageView Imgedit=(ImageView)convertView.findViewById(R.id.imgedit);
            Imgedit.setVisibility(View.GONE);
            final ImageView imgdelete=(ImageView)convertView.findViewById(R.id.imgdelete);
            imgdelete.setVisibility(View.GONE);



//            final Button bt=(Button) convertView.findViewById(R.id.btningetstatus);
//            bt.setText(hm.get("RequestId"));
//
//            bt.setTag(hm.get("RequestId").toString());

            final ImageView imgnext=(ImageView)convertView.findViewById(R.id.imgnext);
            imgnext.setTag(hm.get("Id"));

            imgnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  Toast.makeText(MyRequests.this,"hello",Toast.LENGTH_SHORT).show();


                    Intent i = new Intent(CustomerViewCarsActivity.this,CarDetailsActivity.class);

                    i.putExtra("photo", imgUrl);
                    i.putExtra("Id", imgnext.getTag().toString());
                    i.putExtra("make", tv1.getTag().toString());
                    i.putExtra("model", tv2.getTag().toString());
                    i.putExtra("type", tv3.getTag().toString());
                    i.putExtra("color", tv4.getTag().toString());
                    i.putExtra("year", tv5.getTag().toString());
                    i.putExtra("licenceplate", tv6.getTag().toString());
                    i.putExtra("status", tv8.getTag().toString());
                    i.putExtra("price", tv9.getTag().toString());

                    startActivity(i);


                }
            });







            return convertView;
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
                Intent i=new Intent(CustomerViewCarsActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
