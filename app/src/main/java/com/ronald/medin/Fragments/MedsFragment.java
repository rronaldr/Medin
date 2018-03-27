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

import com.ronald.medin.Activities.MedicineInfoActivity;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;


public class MedsFragment extends Fragment {


    public MedsFragment() {
        // Required empty public constructor
    }

    ListView medsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meds, container, false);
        setRetainInstance(true);

        medsListView = v.findViewById(R.id.medsListView);

        setListViewItemsSource();

        medsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor itemCursor = (Cursor) medsListView.getItemAtPosition(position);
                int medID = itemCursor.getInt(itemCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_ID));
                Intent navigate = new Intent(getActivity(), MedicineInfoActivity.class);
                navigate.putExtra("ItemID", medID);
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
        final Cursor meds = db.getAllMedicine();
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(meds));

        String[] columns = new String[] {
                SQLite.MEDICINE_COLUMN_NAME,
                SQLite.MEDICINE_COLUMN_TYPE
        };

        int[] holders = new int[]{
                R.id.item_medName,
                R.id.item_medType,
        };

        SimpleCursorAdapter medListViewAdapter = new SimpleCursorAdapter(context, R.layout.item_med, meds, columns, holders, 0);
        medListViewAdapter.notifyDataSetChanged();
        medsListView.setAdapter(medListViewAdapter);
    }

}
