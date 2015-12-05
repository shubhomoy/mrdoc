package com.bitslate.mrdoc;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitslate.mrdoc.MrDocObjects.Clinic;
import com.bitslate.mrdoc.MrDocUtilities.Config;
import com.bitslate.mrdoc.MrDocUtilities.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClinicDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button bookTicket,facilities;
    private TextView clinicName, clinicAdd, clinicDetails,clinicEmail;
    ArrayList<String> facilitiesProvided;
    Clinic clinic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_details);

        instantiate();

        setClinicDetails(1);

    }

    private void instantiate() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        facilities= (Button) findViewById(R.id.facilities);
        bookTicket= (Button) findViewById(R.id.book_ticket);
        clinicName= (TextView) findViewById(R.id.clinic_name);
        clinicAdd= (TextView) findViewById(R.id.clinic_address);
        clinicDetails= (TextView) findViewById(R.id.clinic_details);
        facilitiesProvided=new ArrayList<String>();
        clinicEmail= (TextView) findViewById(R.id.email);
    }
    public void setClinicDetails(int id){
        String url = Config.apiUrl+"/clinic/"+id;
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("option",response.toString());
                try {
                    Gson gson = new Gson();
                        final Clinic clinic= gson.fromJson(response.getString("clinic"),Clinic.class);
                    clinicName.setText(clinic.name);
                    clinicAdd.setText(clinic.address);
                    clinicDetails.setText(clinic.description);
                    clinicEmail.setText(clinic.email);
                    facilities.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ClinicDetailsActivity.this);
                            LayoutInflater inflater = LayoutInflater.from(ClinicDetailsActivity.this);
                            View row = inflater.inflate(R.layout.custom_show_clinics, null);

                            ListView facilities = (ListView) row.findViewById(R.id.clinic_list);
                            for (int i = 0; i < clinic.facilities.size(); i++) {
                                facilitiesProvided.add(clinic.facilities.get(i).facility);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClinicDetailsActivity.this, android.R.layout.simple_list_item_1, facilitiesProvided);


                            facilities.setAdapter(adapter);
                            builder.setView(row);
                            builder.create().show();
                        }
                    });
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