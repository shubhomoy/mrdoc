package com.bitslate.mrdoc;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bitslate.mrdoc.MrDocUtilities.Config;
import com.bitslate.mrdoc.MrDocUtilities.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class MakeAppointment extends AppCompatActivity {

    EditText nameEt;
    EditText phoneEt;
    EditText emailEt;
    EditText dateEt;
    EditText descriptionEt;
    Toolbar toolbar;

    void instantiate() {
        nameEt = (EditText)findViewById(R.id.name);
        phoneEt = (EditText)findViewById(R.id.phone);
        dateEt = (EditText)findViewById(R.id.date);
        emailEt = (EditText)findViewById(R.id.email_et);
        descriptionEt = (EditText)findViewById(R.id.problem);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Make Appointment");
    }

    boolean checkInput() {
        if(nameEt.getText().toString().trim().length() > 0 &&
                phoneEt.getText().toString().trim().length() > 0 &&
                emailEt.getText().toString().trim().length() > 0 &&
                dateEt.getText().toString().trim().length() > 0 &&
                descriptionEt.getText().toString().trim().length() > 0)
            return true;
        else
            return false;
    }

    void verifyAppointment(String otp) {
        String url = Config.apiUrl + "/appointment/verify";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MakeAppointment.this);
                builder.setTitle("Success");
                builder.setMessage("Your appointment is fixed!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MakeAppointment.this, "Invalid OTP", Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        instantiate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.make_appointment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if(checkInput()) {
                    String url = Config.apiUrl+"/appointment";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.d("option", jsonObject.getString("data"));
                                AlertDialog.Builder builder = new AlertDialog.Builder(MakeAppointment.this);
                                builder.setTitle("Enter OTP");
                                View v = LayoutInflater.from(MakeAppointment.this).inflate(R.layout.otp, null);
                                final EditText otpEt = (EditText)v.findViewById(R.id.otpEt);
                                builder.setView(v);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(otpEt.getText().toString().trim().length() > 0) {
                                            verifyAppointment(otpEt.getText().toString());
                                        }
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.create().show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("option", error.toString());
                        }
                    });
                    VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
