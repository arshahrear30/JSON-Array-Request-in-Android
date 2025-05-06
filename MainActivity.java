package com.example.myyoutube;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);


        String url ="https://nubsoft.xyz/Video.json";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            //response এটা একটা Array হিসেবে এখানে কাজ করছে
            @Override
            public void onResponse(JSONArray response) {
                //JSONObject jsonObject=response.getJSONObject(0); এতটুক লেখার পর
                //getJSONObject এ red bulb এ click করে try catch নিবো ।
                try {

                    for(int x=0; x<response.length();x++) {

                        JSONObject jsonObject = response.getJSONObject(x);
                        //JsonObject থেকে Title & Video id use করবো
                        String title = jsonObject.getString("title");
                        String video_id = jsonObject.getString("video_id");
                        textView.append(x+". "+title + "\n" + video_id+ "\n"+ "\n");
                        //textView.setText(title + এই রকম ব্যবহার করলে এক data থাকব আগের data মুচে যাবে । আর append
                        //use করলে সবগুলো data serially show করবে ।
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            //error এর জন্য এখানে new (space) suggetion ধরবো ।
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(arrayRequest);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




    }

}
