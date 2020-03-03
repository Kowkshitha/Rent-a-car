package com.rentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class StaffViewBookingsActivity extends AppCompatActivity {

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_bookings);
        lv=(ListView)findViewById(R.id.listgetsbookings);
        // Toast.makeText(myservices.this,"hello world",Toast.LENGTH_LONG).show();
        setTitle("Customer Bookings");
        getrecords();
    }

    public  void getrecords() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Rentacar/getbookings.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressDoalog.dismiss();
                        // Toast.makeText(StaffViewBookingsActivity.this,response,Toast.LENGTH_LONG).show();

                        // Toast.makeText(CustomerViewCarsActivity.this,"Hello world",Toast.LENGTH_LONG).show();

                        parseServerJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(StaffViewBookingsActivity.this,"No Internet",Toast.LENGTH_LONG).show();
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
                hm.put("cid", jObj.getString("cid"));
                hm.put("user", jObj.getString("user"));
                hm.put("plate", jObj.getString("plate"));
                hm.put("amount", jObj.getString("amount"));
                hm.put("fdate", jObj.getString("fdate"));
                hm.put("tdate", jObj.getString("tdate"));
                hm.put("dat", jObj.getString("dat"));
                hm.put("status", jObj.getString("status"));

                student_info.add(hm);
            }
            lv.setAdapter(new StaffViewBookingsActivity.CustomAdapter(this, student_info));//Step -4
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
                convertView = inflater.inflate(R.layout.childbooking, null);
            }
            HashMap<String, String> hm=alData1.get(position);


            // String imgUrl = "http://vaweinstitutes.com/Androidsampledbphp/Ravisampleapp/Images/furniture.png";

           // final String imgUrl=hm.get("photo");
           // final ImageView img=(ImageView) convertView.findViewById(R.id.itemimage);
            // ImageView.setTag(hm.get("photo"));


//
//            Glide.with(context).load(imgUrl)
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .into(img);

            final TextView tv1=(TextView) convertView.findViewById(R.id.tvgoid);
            tv1.setText("Order Id : "+hm.get("Id"));
            tv1.setTag(hm.get("Id"));

            final TextView tv2=(TextView) convertView.findViewById(R.id.tvgcid);
            tv2.setText("Car Id : "+hm.get("cid"));
            tv2.setTag(hm.get("cid"));

            final TextView tv3=(TextView) convertView.findViewById(R.id.tvgplate);
            tv3.setText("Number : "+hm.get("plate"));
            tv3.setTag(hm.get("plate"));

            final TextView tv4=(TextView) convertView.findViewById(R.id.tvguser);
            tv4.setText("User : "+hm.get("user"));
            tv4.setTag(hm.get("user"));


            final TextView tv5 = (TextView) convertView.findViewById(R.id.tvgdate);
            tv5.setText(hm.get("dat"));
            tv5.setTag(hm.get("dat"));
//            tv5.setVisibility(View.GONE);
            //  tv5.setText("Reply: " + hm.get("farmsg"));

            final TextView tv6=(TextView) convertView.findViewById(R.id.tvgfdate);
            tv6.setText("From : "+hm.get("fdate"));
            tv6.setTag(hm.get("fdate"));

            final TextView tv7=(TextView) convertView.findViewById(R.id.tvgtdate);
            tv7.setText("To : "+hm.get("tdate"));
            tv7.setTag(hm.get("tdate"));


            final TextView tv8=(TextView) convertView.findViewById(R.id.tvgamount);
            tv8.setText(hm.get("amount"));
            tv8.setTag(hm.get("amount"));
//            final TextView tv9=(TextView) convertView.findViewById(R.id.tvprice);
//            tv9.setText("$"+hm.get("price")+"/Day");
//            tv9.setTag(hm.get("price"));


            final Button btn=(Button) convertView.findViewById(R.id.btnorderstatus);
            btn.setText(hm.get("status"));
            btn.setTag(hm.get("status"));

//            final ImageView Imgedit=(ImageView)convertView.findViewById(R.id.imgedit);
//            Imgedit.setVisibility(View.GONE);
//            final ImageView imgdelete=(ImageView)convertView.findViewById(R.id.imgdelete);
//            imgdelete.setVisibility(View.GONE);



//            final Button bt=(Button) convertView.findViewById(R.id.btningetstatus);
//            bt.setText(hm.get("RequestId"));
//
//            bt.setTag(hm.get("RequestId").toString());

            final ImageView imgnext=(ImageView)convertView.findViewById(R.id.imgnext);
            imgnext.setTag(hm.get("Id"));
            imgnext.setVisibility(View.GONE);

//            imgnext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //  Toast.makeText(MyRequests.this,"hello",Toast.LENGTH_SHORT).show();
//
//
//                    Intent i = new Intent(StaffViewBookingsActivity.this,CarDetailsActivity.class);
//
//                    i.putExtra("photo", imgUrl);
//                    i.putExtra("Id", imgnext.getTag().toString());
//                    i.putExtra("make", tv1.getTag().toString());
//                    i.putExtra("model", tv2.getTag().toString());
//                    i.putExtra("type", tv3.getTag().toString());
//                    i.putExtra("color", tv4.getTag().toString());
//                    i.putExtra("year", tv5.getTag().toString());
//                    i.putExtra("licenceplate", tv6.getTag().toString());
//                    i.putExtra("status", tv8.getTag().toString());
//                    i.putExtra("price", tv9.getTag().toString());
//
//                    startActivity(i);
//
//
//                }
//            });







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
                Intent i=new Intent(StaffViewBookingsActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
