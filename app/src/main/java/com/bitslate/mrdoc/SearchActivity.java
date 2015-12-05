package com.bitslate.mrdoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView emptyText;
    private ImageView emptyImage;
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

                if (s.toString().isEmpty()) {
                    emptyImage.setVisibility(View.VISIBLE);
                    emptyText.setVisibility(View.VISIBLE);
                    emptyText.setText("Search any Doctor,\nClinics or Specialization");
                    doctors_name.removeAll(doctors_name);
                    doctors_name.clear();

                } else {
                    emptyImage.setVisibility(View.GONE);
                    emptyText.setVisibility(View.GONE);
                    fetchDoctorNames(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private void intantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        emptyText = (TextView) findViewById(R.id.empty_text);
        emptyImage = (ImageView) findViewById(R.id.empty);
        searchView = (RecyclerView) findViewById(R.id.search_result);
        searchView.setHasFixedSize(true);
        searchView.setLayoutManager(new LinearLayoutManager(this));
        search = (EditText) toolbar.findViewById(R.id.search);
        doctors_name = new ArrayList<>();
        searchAdapters = new SearchAdapters(this, doctors_name);
        searchView.setAdapter(searchAdapters);
    }

    public void fetchDoctorNames(CharSequence sequence) {
        doctors_name.removeAll(doctors_name);
        doctors_name.clear();
        Log.d("option_se", sequence.toString());
        String url = Config.apiUrl + "/search/doctors/?q=" + sequence.toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = new JSONArray(response.getString("data"));
                    Gson gson = new Gson();
                    for (int i = 0; i < array.length(); i++) {
                        final Doctor doctor = gson.fromJson(array.getJSONObject(i).toString(), Doctor.class);
                        doctors_name.add(doctor);
                        Log.d("option_name", doctors_name.get(0).name);
                    }
                    searchAdapters.notifyDataSetChanged();

                    if (array.length() == 0) {
                        emptyImage.setVisibility(View.GONE);
                        emptyText.setVisibility(View.VISIBLE);
                        emptyText.setText("No results found!");
                    }
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
