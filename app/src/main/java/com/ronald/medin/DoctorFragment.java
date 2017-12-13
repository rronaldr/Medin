package com.ronald.medin;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class DoctorFragment extends Fragment {


    public DoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor, container, false);

        final EditText doctorNumber = (EditText)v.findViewById(R.id.doctorNumber);
        final EditText doctorEmail = (EditText) v.findViewById(R.id.doctorEmail);

        v.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + doctorNumber.getText().toString()));
                startActivity(callIntent);

            }
        });

        v.findViewById(R.id.emailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mailtoIntent = new Intent(Intent.ACTION_SENDTO);
                mailtoIntent.setData(Uri.parse("mailto:" + doctorEmail.getText().toString()));
                startActivity(mailtoIntent);

            }
        });


        return v;
    }


}
