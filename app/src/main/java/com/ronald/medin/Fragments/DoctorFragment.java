package com.ronald.medin.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ronald.medin.Activities.InsertDoctorActivity;
import com.ronald.medin.Adapters.DoctorAdapter;
import com.ronald.medin.Classes.Doctor;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

import java.util.List;


public class DoctorFragment extends Fragment {


    public DoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor, container, false);

        final Context context = getActivity().getApplicationContext();
        final ListView doctorListView = v.findViewById(R.id.doctorListView);
        final TextView errLabel = v.findViewById(R.id.errorLabel);
        SQLite db = new SQLite(context);

        List<Doctor> doctors = db.getAllDoctors();
        DoctorAdapter doctorArrayAdapter = new DoctorAdapter(context, doctors);


        if(doctors.isEmpty()){
            errLabel.setText("Nemáte uložené žádné lékaře.");
        } else {

            doctorListView.setAdapter(doctorArrayAdapter);
        }

        v.findViewById(R.id.btnNewDoctor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigate = new Intent(getActivity(), InsertDoctorActivity.class);
                getActivity().startActivity(navigate);
            }
        });
//        final EditText doctorNumber = (EditText)v.findViewById(R.id.doctorNumber);
//        final EditText doctorEmail = (EditText) v.findViewById(R.id.doctorEmail);
//
//        v.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("tel:" + doctorNumber.getText().toString()));
//                startActivity(callIntent);
//
//            }
//        });
//
//        v.findViewById(R.id.emailButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent mailtoIntent = new Intent(Intent.ACTION_SENDTO);
//                mailtoIntent.setData(Uri.parse("mailto:" + doctorEmail.getText().toString()));
//                startActivity(mailtoIntent);
//
//            }
//        });


        return v;
    }


}
