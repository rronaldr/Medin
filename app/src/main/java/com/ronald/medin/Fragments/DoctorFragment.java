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

        //Přiřazení view
        doctorListView = v.findViewById(R.id.list_doctorFragment_doctors);

        setListViewItemsSource();

        FloatingActionButton fab =  v.findViewById(R.id.fab_doctorFragment);

        //Po kliknutí na položku v listview, přesměruje uživatele na activitu s informacema o dané položce
        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor itemCursor = (Cursor) doctorListView.getItemAtPosition(position);
                //Id podle, které se na detaily vyberou správné údaje
                int doctorID = itemCursor.getInt(itemCursor.getColumnIndex(SQLite.DOCTOR_COLUMN_ID));
                //Intent s navigací na activitu detailu
                Intent navigate = new Intent(getActivity(), DoctorInfoActivity.class);
                navigate.putExtra("ItemID", doctorID);
                getActivity().startActivity(navigate);
            }
        });

        //onClickListener na floating tlačítko
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigate = new Intent(getActivity(), InsertDoctorActivity.class);
                navigate.putExtra("ItemID", 0);
                getActivity().startActivity(navigate);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        setListViewItemsSource();
    }

    //Naplní listview daty z db
    public void setListViewItemsSource(){
        final Context context = getActivity().getApplicationContext();
        SQLite db = new SQLite(context);
        //Vybere všechny záznamy o doktorovi z databáze
        final Cursor doctors = db.getAllDoctors();

        //Jaké hodnoty se budou zobrazovat
        String[] columns = new String[] {
                SQLite.DOCTOR_COLUMN_SPECIALIZATION,
                SQLite.DOCTOR_COLUMN_NAME,
                SQLite.DOCTOR_COLUMN_SURNAME
        };

        //View do kterých se vloží hodnoty
        int[] holders = new int[]{
                R.id.item_doctorSpecialization,
                R.id.item_doctorName,
                R.id.item_doctorSurname
        };

        //Nový adapter pro listview, s definovaným layoutem
        SimpleCursorAdapter doctorListViewAdapter = new SimpleCursorAdapter(context, R.layout.item_doctor, doctors, columns, holders, 0);
        doctorListViewAdapter.notifyDataSetChanged();
        //Settnutí adapteru k listview
        doctorListView.setAdapter(doctorListViewAdapter);

        //Ukončení spojení s db
        db.close();
    }



}
