package com.example.umang.sentiment_analysis_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText input = findViewById(R.id.input);
        final Button postButton =  findViewById(R.id.post);
        final TextView output =  findViewById(R.id.output);
        final TextView text = findViewById(R.id.text);

        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://sentiment-analysis-api.herokuapp.com/sentiment";

                StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        text.setText(String.format("%s : ", input.getText().toString().toUpperCase()));
                        output.setText(response.toUpperCase());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        output.setText(error.getMessage());
                    }
                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        HashMap<String, String> params2 = new HashMap<String, String>();
                        params2.put("text", input.getText().toString() );
                        return new JSONObject(params2).toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };

                queue.add(sr);
            }

        });




    }
}
