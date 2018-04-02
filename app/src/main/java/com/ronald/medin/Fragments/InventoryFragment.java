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

import com.ronald.medin.Activities.DoctorInfoActivity;
import com.ronald.medin.Activities.InventoryInfoActivity;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment {
    public InventoryFragment() {
        // Required empty public constructor
    }

    ListView inventoryListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_inventory, container, false);
        setRetainInstance(true);

        //Přiřazení view
        inventoryListView = v.findViewById(R.id.list_inventoryFragment_inventories);

        setListViewItemsSource();

        //Po kliknutí na položku v listview, přesměruje uživatele na activitu s informacema o dané položce
        inventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor itemCursor = (Cursor) inventoryListView.getItemAtPosition(position);
                //Id podle, které se na detaily vyberou správné údaje
                int inventoryID = itemCursor.getInt(itemCursor.getColumnIndex(SQLite.INVENTORY_COLUMN_ID));
                //Intent s navigací na activitu detailu
                Intent navigate = new Intent(getActivity(), InventoryInfoActivity.class);
                navigate.putExtra("ItemID", inventoryID);
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

        //Nové spojení s db
        SQLite db = new SQLite(context);
        final Cursor measurements = db.getAllInventory();

        //Jaké hodnoty se budou zobrazovat
        String[] columns = new String[] {
                SQLite.MEDICINE_COLUMN_NAME,
                SQLite.INVENTORY_COLUMN_QUANTITY,
                SQLite.MEDICINE_COLUMN_PACKAGE_UNIT
        };

        //View do kterých se vloží hodnoty
        int[] holders = new int[]{
                R.id.item_inventoryMedName,
                R.id.item_inventoryQuantity,
                R.id.item_inventoryMedUnit
        };

        //Nový adapter pro listview, s definovaným layoutem
        SimpleCursorAdapter inventoryListViewAdapter = new SimpleCursorAdapter(context, R.layout.item_inventory, measurements, columns, holders, 0);
        inventoryListViewAdapter.notifyDataSetChanged();
        //Settnutí adapteru k listview
        inventoryListView.setAdapter(inventoryListViewAdapter);

        //Ukončení spojení s db
        db.close();
    }

}
