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
        Cursor medicineCursor = db.getMedicine_info(medicineID);
        medicineCursor.moveToFirst();
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(medicineCursor));

        medName.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_NAME)));
        medType.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_TYPE)));
        medPackageQuantity.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_PACKAGE_QUANTITY)));
        medDescription.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_DESCRIPTION)));
        medWarning.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_WARNING)));
        medUsageInsctructions.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_USAGE_INSTRUCTIONS)));
        medSideEffects.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_SIDE_EFFECTS)));
        medStorageInstructions.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_STORAGE_INSTRUCTIONS)));
        medOtherInformation.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_INFO_COLUMN_OTHER_INFORMATION)));

    }

}
