package com.bitslate.mrdoc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitslate.mrdoc.MrDocAdapters.ClinicAdapter;
import com.bitslate.mrdoc.MrDocObjects.Clinic;
import com.bitslate.mrdoc.MrDocObjects.Doctor;
import com.bitslate.mrdoc.MrDocUtilities.Config;
import com.bitslate.mrdoc.MrDocUtilities.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ImageView doctorImage;
    private TextView docName, docAddress, docPhone, docDesc, regId, docEmail;
    private Button showCinics;
    private static int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        instantiate();

        Intent intent = getIntent();
        doctorId = intent.getIntExtra("docId", 0);
        setDocdetails(doctorId);

    }


    private void instantiate() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Doctor");
        doctorImage = (ImageView) findViewById(R.id.doctor_photo);
        docName = (TextView) findViewById(R.id.doc_name);
        docAddress = (TextView) findViewById(R.id.doctor_address);
        docPhone = (TextView) findViewById(R.id.doctor_phone);
        docDesc = (TextView) findViewById(R.id.doctor_details);
        docEmail = (TextView) findViewById(R.id.doc_email);
        regId = (TextView) findViewById(R.id.doc_reg_id);
        showCinics = (Button) findViewById(R.id.show_clinics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setShowCinics(final ArrayList<Clinic> clinics) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View row = inflater.inflate(R.layout.custom_show_clinics, null);
        builder.setView(row);

        ArrayList<String> slist = new ArrayList<String>();
        for (Clinic c : clinics)
            slist.add(c != null ? c.name : null);

        ListView clinicList = (ListView) row.findViewById(R.id.clinic_list);
        clinicList.setAdapter(new ClinicAdapter(this, clinics, slist));

        clinicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(DoctorDetailsActivity.this, ClinicDetailsActivity.class)
                        .putExtra("docId", doctorId)
                        .putExtra("clinicId", clinics.get(position).id);
                startActivity(intent);
            }
        });

        builder.create().show();

    }

    private void setDocdetails(int id) {

        String url = Config.apiUrl + "/doctor/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("data", response.toString());
                try {
                    Gson gson = new Gson();
                    final Doctor doctor = gson.fromJson(response.getString("doctor"), Doctor.class);

                    docName.setText(doctor.name);
                    docAddress.setText("" + doctor.address);
                    regId.setText("Registrtion ID: " + doctor.reg_id);
                    docEmail.setText(doctor.email);

                    if (doctor.contacts.size() > 0)
                        docPhone.setText(doctor.contacts.get(0).contact_no);
                    else
                        docPhone.setVisibility(View.GONE);
                    docDesc.setText("" + doctor.description);

                    showCinics.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setShowCinics(doctor.clinics);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
