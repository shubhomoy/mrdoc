package com.bitslate.mrdoc.MrDocAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitslate.mrdoc.DoctorDetailsActivity;
import com.bitslate.mrdoc.MrDocObjects.Doctor;
import com.bitslate.mrdoc.R;

import java.util.ArrayList;

/**
 * Created by vellapanti on 5/12/15.
 */
public class SearchAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Doctor> doctors_name;
    public  SearchAdapters(Context context,ArrayList<Doctor> doctors_name){
        this.context=context;
        this.doctors_name=doctors_name;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_doctor_search,parent,false);
        DoctorViewHolder doctorViewHolder=new DoctorViewHolder(view);
        return doctorViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DoctorViewHolder doctorViewHolder = (DoctorViewHolder)holder;
        doctorViewHolder.docName.setText(doctors_name.get(position).name);
        doctorViewHolder.docClinic.setText(doctors_name.get(position).address);

        doctorViewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, DoctorDetailsActivity.class)
                        .putExtra("docId", doctors_name.get(position).id);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors_name.size();
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {

        private TextView docName;
        private TextView docClinic;
        private CardView root;

        public DoctorViewHolder(View itemView) {
            super(itemView);
            docName = (TextView) itemView.findViewById(R.id.doc_name);
            docClinic = (TextView) itemView.findViewById(R.id.doc_clinic);
            root= (CardView) itemView.findViewById(R.id.row);

        }
    }
}
