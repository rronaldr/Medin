package com.ronald.medin.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ronald.medin.Activities.DoctorInfo;
import com.ronald.medin.Activities.InsertEditDoctorActivity;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;


public class DoctorFragment extends Fragment {


    public DoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor, container, false);
        setRetainInstance(true);

        final Context context = getActivity().getApplicationContext();
        final ListView doctorListView = v.findViewById(R.id.doctorListView);
        final TextView errLabel = v.findViewById(R.id.errorLabel);
        SQLite db = new SQLite(context);
        final Cursor doctors = db.getAllDoctors();


        String[] columns = new String[] {
                SQLite.DOCTOR_COLUMN_NAME,
                SQLite.DOCTOR_COLUMN_SURNAME,
                SQLite.DOCTOR_COLUMN_EMAIL,
                SQLite.DOCTOR_COLUMN_TELEPHONE
        };

        int[] holders = new int[]{
                R.id.item_doctorName,
                R.id.item_doctorSurname,
                R.id.item_doctorEmail,
                R.id.item_doctorPhone
        };

        SimpleCursorAdapter doctorListViewAdapter = new SimpleCursorAdapter(context, R.layout.item_doctor, doctors, columns, holders, 0);
        doctorListViewAdapter.notifyDataSetChanged();
        doctorListView.setAdapter(doctorListViewAdapter);

        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor itemCursor = (Cursor) doctorListView.getItemAtPosition(position);
                int doctorID = itemCursor.getInt(itemCursor.getColumnIndex(SQLite.DOCTOR_COLUMN_ID));
                Intent navigate = new Intent(getActivity(), DoctorInfo.class);
                navigate.putExtra("ItemID", doctorID);
                getActivity().startActivity(navigate);
            }
        });

        v.findViewById(R.id.btnNewDoctor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigate = new Intent(getActivity(), InsertEditDoctorActivity.class);
                navigate.putExtra("ItemID", 0);
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
