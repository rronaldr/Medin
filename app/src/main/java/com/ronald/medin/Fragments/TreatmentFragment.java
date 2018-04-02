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

import com.ronald.medin.Activities.InsertTreatmentActivity;
import com.ronald.medin.Activities.MedicineInfoActivity;
import com.ronald.medin.Activities.TreatmentInfoActivity;
import com.ronald.medin.Classes.Treatment_info;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;


public class TreatmentFragment extends Fragment {

    ListView treatmentList;

    public TreatmentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_treatment, container, false);


        //Přiřazení view
        treatmentList = v.findViewById(R.id.list_treatmentFragment_treatments);
        FloatingActionButton fab =  v.findViewById(R.id.fab_treatmentFragment);

        setListViewItemsSource();

        //Po kliknutí na položku v listview, přesměruje uživatele na activitu s informacema o dané položce
        treatmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor itemCursor = (Cursor) treatmentList.getItemAtPosition(position);
                //Id podle, které se na detaily vyberou správné údaje
                int treatmentInfoID = itemCursor.getInt(itemCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_ID));
                //Intent s navigací na activitu detailu
                Intent navigate = new Intent(getActivity(), TreatmentInfoActivity.class);
                navigate.putExtra("ItemID", treatmentInfoID);
                Log.e("TreattoShow",String.valueOf(treatmentInfoID));
                getActivity().startActivity(navigate);
            }
        });

        //onClickListener na floating tlačítko
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h5 = new Intent(getActivity(), InsertTreatmentActivity.class);
                startActivity(h5);
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
        final Cursor treatmentCursor = db.getAllTreatmentInfo();

        //Jaké hodnoty se budou zobrazovat
        String[] columns = new String[] {
                SQLite.MEDICINE_COLUMN_NAME,
                SQLite.TREATMENT_INFO_COLUMN_USAGE_TYPE
        };

        //View do kterých se vloží hodnoty
        int[] holders = new int[]{
                R.id.item_treatmentMedName,
                R.id.item_treatmentType
        };

        //Nový adapter pro listview, s definovaným layoutem
        SimpleCursorAdapter treatmentListViewAdapter = new SimpleCursorAdapter(context, R.layout.item_treatment, treatmentCursor, columns, holders, 0);
        treatmentListViewAdapter.notifyDataSetChanged();
        //Settnutí adapteru k listview
        treatmentList.setAdapter(treatmentListViewAdapter);

        //Ukončení spojení s db
        db.close();
    }
}
