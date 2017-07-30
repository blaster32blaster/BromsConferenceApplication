package com.broms.bromsconferenceapplication;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpeakerActivity extends AppCompatActivity {

    private static final Object TAG = "Speaker Activity";
    private Context mContext;
    private CoordinatorLayout mCLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        SpeakersJsonRequest();
    }



    public void SpeakersJsonRequest()
    {
        String url ="https://conference.onlinelearningconsortium.org/conferences/3193/presenters.json";
        Toast.makeText(this, " Fetching Speakers ... " , Toast.LENGTH_SHORT).show();

        final TextView responseView;
        responseView = (TextView) findViewById(R.id.textViewSpeakers);

//        final ScrollView responseView;
//        responseView = (ScrollView) findViewById(R.id.scrollViewSpeakers);


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
                            JSONArray array = response.getJSONArray("nodes");
//                             Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject speakers = array.getJSONObject(i);
                                JSONObject node = speakers.getJSONObject("node");

                                // Get the current student (json object) data
                                String firstname    = node.getString("first_name");
                                String lastname     = node.getString("last_name");
                                String organization = node.getString("organization");
                                String title        = node.getString("job_title");


                                // Display the formatted json data in text view
                                responseView.append(firstname +" "+ lastname + " \n"+ title +" - "+ organization );
                                responseView.append("\n\n");

//                                LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                                View v = vi.inflate(R.layout.activity_speaker, null);
//
//                                // fill in any details dynamically here
//                                TextView textView = (TextView) v.findViewById(R.id.textViewSpeakers);
//                                textView.setText("your text"+ firstname);
//
//                                // insert into main view
//                                ViewGroup insertPoint = (ViewGroup) findViewById(R.id.tableViewSpeakers);
//                                insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

//                                responseView.addView();


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
                        Log.i((String) TAG, "Speaker Json Error : "+ error);
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonObjRequest);
    }
}
