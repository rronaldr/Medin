package com.ronald.medin.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.ronald.medin.Activities.DoctorInfoActivity;
import com.ronald.medin.Activities.InsertDoctorActivity;
import com.ronald.medin.Activities.InsertTreatmentActivity;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;


public class DoctorFragment extends Fragment {


    public DoctorFragment() {
        // Required empty public constructor
    }

    ListView doctorListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor, container, false);
        setRetainInstance(true);

        doctorListView = v.findViewById(R.id.list_doctorFragment_doctors);

        setListViewItemsSource();

        FloatingActionButton fab =  v.findViewById(R.id.fab_doctorFragment);

        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor itemCursor = (Cursor) doctorListView.getItemAtPosition(position);
                int doctorID = itemCursor.getInt(itemCursor.getColumnIndex(SQLite.DOCTOR_COLUMN_ID));
                Intent navigate = new Intent(getActivity(), DoctorInfoActivity.class);
                navigate.putExtra("ItemID", doctorID);
                getActivity().startActivity(navigate);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigate = new Intent(getActivity(), InsertDoctorActivity.class);
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

    @Override
    public void onResume() {
        super.onResume();

        setListViewItemsSource();
    }

    public void setListViewItemsSource(){

        final Context context = getActivity().getApplicationContext();
        SQLite db = new SQLite(context);
        final Cursor doctors = db.getAllDoctors();
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(doctors));

        String[] columns = new String[] {
                SQLite.DOCTOR_COLUMN_SPECIALIZATION,
                SQLite.DOCTOR_COLUMN_NAME,
                SQLite.DOCTOR_COLUMN_SURNAME
        };

        int[] holders = new int[]{
                R.id.item_doctorSpecialization,
                R.id.item_doctorName,
                R.id.item_doctorSurname
        };

        SimpleCursorAdapter doctorListViewAdapter = new SimpleCursorAdapter(context, R.layout.item_doctor, doctors, columns, holders, 0);
        doctorListViewAdapter.notifyDataSetChanged();
        doctorListView.setAdapter(doctorListViewAdapter);

        db.close();
    }



}
