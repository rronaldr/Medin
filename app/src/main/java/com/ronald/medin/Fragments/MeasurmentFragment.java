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

import com.ronald.medin.Activities.InsertDoctorActivity;
import com.ronald.medin.Activities.InsertMeasurementActivity;
import com.ronald.medin.Activities.MeasurementInfoActivity;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeasurmentFragment extends Fragment {


    public MeasurmentFragment() {
        // Required empty public constructor
    }

    ListView measurementListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_measurment, container, false);
        setRetainInstance(true);

        //Přiřazení k view
        measurementListView = v.findViewById(R.id.list_measurementFragment_measurements);

        FloatingActionButton fab =  v.findViewById(R.id.fab_measurementFragment);

        setListViewItemsSource();

        //Po kliknutí na položku v listview, přesměruje uživatele na activitu s informacema o dané položce
        measurementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor itemCursor = (Cursor) measurementListView.getItemAtPosition(position);
                //Id podle, které se na detaily vyberou správné údaje
                int measurementID = itemCursor.getInt(itemCursor.getColumnIndex(SQLite.MEASUREMENT_COLUMN_ID));
                //Intent s navigací na activitu detailu
                Intent navigate = new Intent(getActivity(), MeasurementInfoActivity.class);
                navigate.putExtra("ItemID", measurementID);
                getActivity().startActivity(navigate);
            }
        });

        //onClickListener na floating tlačítko
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigate = new Intent(getActivity(), InsertMeasurementActivity.class);
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

        //Vybere všechny záznamy o doktorovi z databáze
        SQLite db = new SQLite(context);
        final Cursor measurements = db.getAllMeasurements();
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(measurements));

        //Jaké hodnoty se budou zobrazovat
        String[] columns = new String[] {
                SQLite.MEASUREMENT_COLUMN_CREATED,
                SQLite.MEASUREMENT_COLUMN_NAME
        };

        //View do kterých se vloží hodnoty
        int[] holders = new int[]{
                R.id.item_measurementDatetime,
                R.id.item_measurementName
        };

        //Nový adapter pro listview, s definovaným layoutem
        SimpleCursorAdapter measurementListViewAdapter = new SimpleCursorAdapter(context, R.layout.item_measurement, measurements, columns, holders, 0);
        measurementListViewAdapter.notifyDataSetChanged();
        //Settnutí adapteru k listview
        measurementListView.setAdapter(measurementListViewAdapter);
        db.close();
    }

}
