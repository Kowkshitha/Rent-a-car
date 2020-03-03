package com.rentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GetCarsActivity extends AppCompatActivity {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_cars);
        lv=(ListView)findViewById(R.id.listcars);
        // Toast.makeText(myservices.this,"hello world",Toast.LENGTH_LONG).show();

       // getrecords();
    }

//    public  void getrecords() {
//
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://possakrishna.com/Rentacar/getcars.php";
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        //  progressDoalog.dismiss();
//                        Toast.makeText(GetCarsActivity.this,response,Toast.LENGTH_LONG).show();
//                        parseServerJsonData(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(GetCarsActivity.this,"No Internet",Toast.LENGTH_LONG).show();
//            }
//        });
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
//    }
//
//
//    SharedPreferences sharedPreferences;
//
//    ArrayList<HashMap<String, String>> student_info = new ArrayList<HashMap<String, String>>();
//
//    private void parseServerJsonData(String response) {//Step -2
//        HashMap<String, String> hm;
//        try {
//            JSONObject json = new JSONObject(response);
//            JSONArray jArray = json.getJSONArray("data");
//            // data=new String[jArray.length()];
//            JSONObject jObj;
//            for (int i = 0; i < jArray.length(); i++) {
//                jObj = jArray.getJSONObject(i);
//                hm=new HashMap<String, String>();
//                hm.put("Id", jObj.getString("Id"));
//                hm.put("itemname", jObj.getString("itemname"));
//                hm.put("price", jObj.getString("price"));
//                hm.put("lbs", jObj.getString("lbs"));
//                hm.put("duname", jObj.getString("duname"));
//                hm.put("photo", jObj.getString("photo"));
//                hm.put("phone", jObj.getString("phone"));
//
//                student_info.add(hm);
//            }
//            lv.setAdapter(new productsActivity.CustomAdapter(this, student_info));//Step -4
//        } catch (JSONException e) {}
//
//    }
//
//
//    private static LayoutInflater inflater=null;
//    public class CustomAdapter extends BaseAdapter {   //Step -3
//
//        Context context;
//        ArrayList<HashMap<String, String>> alData1;
//        public CustomAdapter(Context mainActivity, ArrayList<HashMap<String, String>> al) {
//            alData1=al;
//            context=mainActivity;
//            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//        @Override
//        public int getCount() {
//            return alData1.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            if(convertView==null){
//                convertView = inflater.inflate(R.layout.childproduct, null);
//            }
//            HashMap<String, String> hm=alData1.get(position);
//
//
//            //  String imgUrl = "http://vaweinstitutes.com/Androidsampledbphp/Ravisampleapp/Images/furniture.png";
//
////            String imgUrl =hm.get("photo");
////            final ImageView img=(ImageView) convertView.findViewById(R.id.itemimage);
////
////
////            Glide.with(context).load(imgUrl)
////                    .thumbnail(0.5f)
////                    .crossFade()
////                    .into(img);
//
//            TextView tv1=(TextView) convertView.findViewById(R.id.petgetitem);
//            tv1.setText(hm.get("itemname"));
//
//            final TextView tv2=(TextView) convertView.findViewById(R.id.petcusnumber);
//            tv2.setText(hm.get("price"));
//            tv2.setTag(hm.get("phone"));
//
////            TextView tv3=(TextView) convertView.findViewById(R.id.pgetdate);
////            tv3.setText(hm.get("dat"));
//
////            TextView tv4=(TextView) convertView.findViewById(R.id.petcusmsg);
////           // tv4.setText("msg: " + hm.get("cusmsg")+ " ?");
////            tv4.setVisibility(View.GONE);
////
////            TextView tv5 = (TextView) convertView.findViewById(R.id.petfarmsg);
////            tv5.setVisibility(View.GONE);
//            //  tv5.setText("Reply: " + hm.get("farmsg"));
//
////            TextView tv4=(TextView) convertView.findViewById(R.id.etgetnumber);
////            tv4.setText(hm.get("Number"));
//
////            final Button bt=(Button) convertView.findViewById(R.id.btningetstatus);
////            bt.setText(hm.get("RequestId"));
////
////            bt.setTag(hm.get("RequestId").toString());
//
//            ImageView img=(ImageView)convertView.findViewById(R.id.pimggetcall);
//
//
//            img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //  Toast.makeText(MyRequests.this,"hello",Toast.LENGTH_SHORT).show();
//
//
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tv2.getTag().toString(), null));
//                    startActivity(intent);
//
//
//                }
//            });
//
//
//
//
//
//
//
//            return convertView;
//        }
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.idlogout:
//                // Toast.makeText(getApplicationContext(),"logout Selected",Toast.LENGTH_LONG).show();
//                Intent i=new Intent(GetCarsActivity.this,HomeActivity.class);
//                startActivity(i);
//                finish();
//
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
