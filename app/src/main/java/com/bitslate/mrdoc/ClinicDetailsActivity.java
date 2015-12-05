package com.bitslate.mrdoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

public class ClinicDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button bookTicket;
    private TextView clinicName, clinicAdd, clinicDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_details);

        instantiate();
    }

    private void instantiate() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bookTicket= (Button) findViewById(R.id.book_ticket);

        clinicName= (TextView) findViewById(R.id.clinic_name);
        clinicAdd= (TextView) findViewById(R.id.clinic_address);
        clinicDetails= (TextView) findViewById(R.id.clinic_details);
    }
}