package com.ronald.medin.Activities;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class MedicineInfo extends AppCompatActivity {

    int medicineID;
    int medicine_infoID;
    private SQLite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView medName = findViewById(R.id.medinfo_name);
        TextView medType = findViewById(R.id.medinfo_type);
        TextView medPackageQuantity = findViewById(R.id.medinfo_packageQuantity);
        TextView medDescription = findViewById(R.id.medinfo_description);
        TextView medWarning = findViewById(R.id.medinfo_warning);
        TextView medUsageInsctructions = findViewById(R.id.medinfo_usageInstructions);
        TextView medSideEffects = findViewById(R.id.medinfo_sideEffects);
        TextView medStorageInstructions = findViewById(R.id.medinfo_storageInstructions);
        TextView medOtherInformation = findViewById(R.id.medinfo_otherInformation);

        medicineID = getIntent().getIntExtra("ItemID",0);

        db = new SQLite(this);
        Cursor rsmed = db.getMedicine(medicineID);
        //Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(rsmed));
        rsmed.moveToFirst();

        medName.setText(rsmed.getString(rsmed.getColumnIndex(SQLite.MEDICINE_COLUMN_NAME)));
        medType.setText(rsmed.getString(rsmed.getColumnIndex(SQLite.MEDICINE_COLUMN_TYPE)));
        medPackageQuantity.setText(rsmed.getString(rsmed.getColumnIndex(SQLite.MEDICINE_COLUMN_PACKAGE_QUANTITY)));


        medicine_infoID = rsmed.getInt(rsmed.getColumnIndex(SQLite.MEDICINE_COLUMN_MEDICINE_INFO_ID));

        Cursor rsmed_info = db.getMedicine_info(medicine_infoID);
        //Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(rsmed_info));
        rsmed_info.moveToFirst();

        medDescription.setText(rsmed_info.getString(rsmed_info.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_DESCRIPTION)));
        medWarning.setText(rsmed_info.getString(rsmed_info.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_WARNING)));
        medUsageInsctructions.setText(rsmed_info.getString(rsmed_info.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_USAGE_INSTRUCTIONS)));
        medSideEffects.setText(rsmed_info.getString(rsmed_info.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_SIDE_EFFECTS)));
        medStorageInstructions.setText(rsmed_info.getString(rsmed_info.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_STORAGE_INSTRUCTIONS)));
        medOtherInformation.setText(rsmed_info.getString(rsmed_info.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_OTHER_INFORMATION)));

    }

}
