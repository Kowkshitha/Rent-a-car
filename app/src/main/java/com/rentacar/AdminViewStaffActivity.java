package com.rentacar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.Map;

public class AdminViewStaffActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_staff);
        lv=(ListView)findViewById(R.id.listgetstaff);


        // Toast.makeText(myservices.this,"hello world",Toast.LENGTH_LONG).show();
        setTitle("Staff List");
        getrecords();
    }

    public  void getrecords() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Rentacar/getstaff.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  progressDoalog.dismiss();
                       //  Toast.makeText(AdminViewStaffActivity.this,response,Toast.LENGTH_LONG).show();

                       //  Toast.makeText(AdminViewStaffActivity.this,"Hello world",Toast.LENGTH_LONG).show();

                        parseServerJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AdminViewStaffActivity.this,"No Internet",Toast.LENGTH_LONG).show();
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
                hm.put("name", jObj.getString("name"));
                hm.put("email", jObj.getString("email"));
                hm.put("phone", jObj.getString("phone"));
                hm.put("pass", jObj.getString("pass"));

                student_info.add(hm);
            }
            lv.setAdapter(new AdminViewStaffActivity.CustomAdapter(this, student_info));//Step -4
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
                convertView = inflater.inflate(R.layout.childstaff, null);
            }
            HashMap<String, String> hm=alData1.get(position);




            final TextView tv1=(TextView) convertView.findViewById(R.id.getsname);
            tv1.setText(hm.get("name"));
            tv1.setTag(hm.get("name"));

            final TextView tv2=(TextView) convertView.findViewById(R.id.getsemail);
            tv2.setText(hm.get("email"));
            tv2.setTag(hm.get("email"));

            final TextView tv3=(TextView) convertView.findViewById(R.id.getsphone);
            tv3.setText(hm.get("phone"));
            tv3.setTag(hm.get("phone"));

            final TextView tv4=(TextView) convertView.findViewById(R.id.getspass);
            tv4.setText(hm.get("pass"));
            tv4.setTag(hm.get("pass"));


            final ImageView Imgedit=(ImageView)convertView.findViewById(R.id.imgsedit);
            Imgedit.setTag(hm.get("Id"));
            final ImageView imgdelete=(ImageView)convertView.findViewById(R.id.imgsdelete);
            imgdelete.setTag(hm.get("Id"));
            Imgedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i = new Intent(AdminViewStaffActivity.this,UpdateStaffActivity.class);


                    i.putExtra("Id", Imgedit.getTag().toString());
                    i.putExtra("name", tv1.getTag().toString());
                    i.putExtra("email", tv2.getTag().toString());
                    i.putExtra("phone", tv3.getTag().toString());
                    i.putExtra("pass", tv4.getTag().toString());

                    startActivity(i);
                }
            });

            imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  deleteStaff(imgdelete.getTag().toString());

                    builder.show();
                }
            });



            builder = new AlertDialog.Builder(AdminViewStaffActivity.this);
            builder.setTitle("Want to Delete?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Toast.makeText(StaffViewCarsActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                    deleteStaff(imgdelete.getTag().toString());
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(AdminViewStaffActivity.this,"Deletion Cancelled",Toast.LENGTH_LONG).show();
                }
            });
//            final Button bt=(Button) convertView.findViewById(R.id.btningetstatus);
//            bt.setText(hm.get("RequestId"));
//
//            bt.setTag(hm.get("RequestId").toString());




            return convertView;
        }
    }

    public  void deleteStaff(final String id) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://possakrishna.com/Rentacar/deletestaff.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("success")) {

                            Toast.makeText(AdminViewStaffActivity.this,"Deleted Successffuly",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(AdminViewStaffActivity.this,AdminHomeActivity.class);

                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(AdminViewStaffActivity.this,"Deletion Failed",Toast.LENGTH_LONG).show();

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AdminViewStaffActivity.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("id",id);//

                return params1;

            }
        }

                ;
// Add the request to the RequestQueue.
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
                Intent i=new Intent(AdminViewStaffActivity.this,HomeActivity.class);
                startActivity(i);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
