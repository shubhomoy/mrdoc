package com.bitslate.mrdoc;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DoctorDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ImageView doctorImage;
    private TextView docName, docAddress, docPhone, docDesc;
    private Button showCinics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        instantiate();


        showCinics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShowCinics();
            }
        });
    }



    private void instantiate(){

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        doctorImage= (ImageView) findViewById(R.id.doctor_photo);
        docName= (TextView) findViewById(R.id.doc_name);
        docAddress= (TextView) findViewById(R.id.doctor_address);
        docPhone= (TextView) findViewById(R.id.doctor_phone);
        docDesc= (TextView) findViewById(R.id.doctor_details);

        showCinics= (Button) findViewById(R.id.show_clinics);
    }


    private void setShowCinics(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        LayoutInflater inflater=LayoutInflater.from(this);
        View row=inflater.inflate(R.layout.custom_show_clinics,null);
        builder.setView(row);

        ListView clinicList= (ListView) row.findViewById(R.id.clinic_list);
        //clinicList.setAdapter();

        builder.create().show();

    }
}
