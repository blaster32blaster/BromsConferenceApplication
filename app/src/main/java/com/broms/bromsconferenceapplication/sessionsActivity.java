package com.broms.bromsconferenceapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class sessionsActivity extends AppCompatActivity
        {

    private static final Object TAG = "BromsConference";
            private Context mContext;
            private CoordinatorLayout mCLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);




//        printOutSessionList();
jsonrequester();



    }




    public void jsonrequester()
    {
        String url ="https://api-conferences.onlinelearningconsortium.org:6984/olc/_design/schedule/_view/schedule?reduce=false&keys=[%223193%22]&include_docs=true";
        final TextView responseView;
        Toast.makeText(this, " Fetching Data ... " , Toast.LENGTH_LONG).show();
        responseView = (TextView) findViewById(R.id.textViewSessions);
        // Empty the TextView


        // Initialize a new RequestQueue instance

        mContext = getApplicationContext();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        // Initialize a new JsonArrayRequest instance
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            JSONArray array = response.getJSONArray("rows");
//                             Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject session = array.getJSONObject(i);
                                JSONObject doc = session.getJSONObject("doc");
                                JSONObject node = doc.getJSONObject("node");

                                // Get the current student (json object) data
                                String title = node.getString("title");


                                // Display the formatted json data in text view
                                responseView.append(title +" ");
                                responseView.append("\n\n");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
//                        responseView.setText(response.toString());
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.i((String) TAG, "Json Error : "+ error);
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonObjRequest);
    }



    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }





}
