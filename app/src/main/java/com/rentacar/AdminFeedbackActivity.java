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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminFeedbackActivity extends AppCompatActivity {

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedback);
        lv=(ListView)findViewById(R.id.listgetadminfeedbacks);
        // Toast.makeText(myservices.this,"hello world",Toast.LENGTH_LONG).show();
        setTitle("Feedbacks");
        getrecords();
    }

    public  void getrecords() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Rentacar/getfeed.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressDoalog.dismiss();
                        // Toast.makeText(StaffViewFeedbacksActivity.this,response,Toast.LENGTH_LONG).show();

                        // Toast.makeText(StaffViewFeedbacksActivity.this,"Hello world",Toast.LENGTH_LONG).show();

                        parseServerJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AdminFeedbackActivity.this,"No Internet",Toast.LENGTH_LONG).show();
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
                hm.put("oid", jObj.getString("oid"));
                hm.put("msg", jObj.getString("msg"));
                hm.put("dat", jObj.getString("dat"));
                hm.put("rating", jObj.getString("rating"));

                student_info.add(hm);
            }
            lv.setAdapter(new AdminFeedbackActivity.CustomAdapter(this, student_info));//Step -4
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
                convertView = inflater.inflate(R.layout.childfeedback, null);
            }
            HashMap<String, String> hm=alData1.get(position);



            final TextView tv1=(TextView) convertView.findViewById(R.id.tvgfid);
            tv1.setText("Order Id : "+hm.get("oid"));
            tv1.setTag(hm.get("oid"));

            final TextView tv2=(TextView) convertView.findViewById(R.id.tvfmessage);
            tv2.setText(hm.get("msg"));
            tv2.setTag(hm.get("msg"));

            final TextView tv3=(TextView) convertView.findViewById(R.id.tvfeeddate);
            tv3.setText(hm.get("dat"));
            tv3.setTag(hm.get("dat"));
//
            final RatingBar rate=(RatingBar) convertView.findViewById(R.id.getfrate);
            rate.setRating(Float.parseFloat(hm.get("rating")));



            //   final ImageView imgnext=(ImageView)convertView.findViewById(R.id.imgnext);
            //imgnext.setVisibility(View.GONE);
//            imgnext.setTag(hm.get("Id"));
//
//            imgnext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //  Toast.makeText(MyRequests.this,"hello",Toast.LENGTH_SHORT).show();
//
//
//                    Intent i = new Intent(StaffViewFeedbacksActivity.this,CarDetailsActivity.class);
//
//                    i.putExtra("photo", tv7.getTag().toString());
//                    i.putExtra("Id", imgnext.getTag().toString());
//                    i.putExtra("make", tv1.getTag().toString());
//                    i.putExtra("model", tv2.getTag().toString());
//                    i.putExtra("type", tv3.getTag().toString());
//                    i.putExtra("color", tv4.getTag().toString());
//                    i.putExtra("year", tv5.getTag().toString());
//                    i.putExtra("licenceplate", tv6.getTag().toString());
//                    i.putExtra("status", tv8.getTag().toString());
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
                Intent i=new Intent(AdminFeedbackActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
