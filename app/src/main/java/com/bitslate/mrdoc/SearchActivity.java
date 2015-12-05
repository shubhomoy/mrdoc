package com.bitslate.mrdoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitslate.mrdoc.MrDocAdapters.SearchAdapters;
import com.bitslate.mrdoc.MrDocObjects.Doctor;
import com.bitslate.mrdoc.MrDocUtilities.Config;
import com.bitslate.mrdoc.MrDocUtilities.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView searchView;
    private Toolbar toolbar;
    private EditText search;
    SearchAdapters searchAdapters;
    ArrayList<Doctor> doctors_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        intantiate();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                fetchDoctorNames(s);
            }
        });


    }
    private void intantiate(){
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView= (RecyclerView) findViewById(R.id.search_result);
        searchView.setHasFixedSize(true);
        searchView.setLayoutManager(new LinearLayoutManager(this));
        search= (EditText) toolbar.findViewById(R.id.search);
        doctors_name = new ArrayList<>();
        searchAdapters = new SearchAdapters(this,doctors_name);
        searchView.setAdapter(searchAdapters);
    }
    public void fetchDoctorNames(CharSequence sequence){
        doctors_name.removeAll(doctors_name);
        doctors_name.clear();
        Log.d("option_se",sequence.toString());
        String url = Config.apiUrl+"/search/doctors/?q="+sequence.toString();
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = new JSONArray(response.getString("data"));
                    Gson gson =new Gson();
                    for(int i=0;i<array.length();i++){
                        final Doctor doctor =  gson.fromJson(array.getJSONObject(i).toString(),Doctor.class);
                        doctors_name.add(doctor);
                        Log.d("option_name",doctors_name.get(0).name);
                    }
                    searchAdapters.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().getRequestQueue().add(request);
    }
}
