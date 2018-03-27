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

        measurementListView = v.findViewById(R.id.list_measurementFragment_measurements);

        setListViewItemsSource();

        measurementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor itemCursor = (Cursor) measurementListView.getItemAtPosition(position);
                int measurementID = itemCursor.getInt(itemCursor.getColumnIndex(SQLite.MEASUREMENT_COLUMN_ID));
                Intent navigate = new Intent(getActivity(), MeasurementInfoActivity.class);
                navigate.putExtra("ItemID", measurementID);
                getActivity().startActivity(navigate);
            }
        });

        v.findViewById(R.id.btn_measurementFragment_new).setOnClickListener(new View.OnClickListener() {
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

    public void setListViewItemsSource(){

        final Context context = getActivity().getApplicationContext();

        SQLite db = new SQLite(context);
        final Cursor measurements = db.getAllMeasurements();
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(measurements));

        String[] columns = new String[] {
                SQLite.MEASUREMENT_COLUMN_CREATED,
                SQLite.MEASUREMENT_COLUMN_NAME
        };

        int[] holders = new int[]{
                R.id.item_measurementDatetime,
                R.id.item_measurementName
        };

        SimpleCursorAdapter measurementListViewAdapter = new SimpleCursorAdapter(context, R.layout.item_measurement, measurements, columns, holders, 0);
        measurementListViewAdapter.notifyDataSetChanged();
        measurementListView.setAdapter(measurementListViewAdapter);
    }

}
