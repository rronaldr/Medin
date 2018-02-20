package com.ronald.medin.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class MeasurementInfo extends AppCompatActivity {

    int measurmentID;
    private SQLite db;
    TextView measurmentDate;
    TextView measurmentName;
    TextView measurmentValue;
    TextView measurmentUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        measurmentID = getIntent().getIntExtra("ItemID",0);

        measurmentDate = findViewById(R.id.measurementInfo_Datetime);
        measurmentName = findViewById(R.id.measurementInfo_Name);
        measurmentValue = findViewById(R.id.measurementInfo_Value);
        measurmentUnit = findViewById(R.id.measurementInfo_Unit);

        db = new SQLite(this);
        Cursor rs = db.getMeasurement(measurmentID);
        rs.moveToFirst();

        measurmentDate.setText(rs.getString(rs.getColumnIndex(SQLite.MEASUREMENT_COLUMN_CREATED)));
        measurmentName.setText(rs.getString(rs.getColumnIndex(SQLite.MEASUREMENT_COLUMN_NAME)));
        measurmentValue.setText(rs.getString(rs.getColumnIndex(SQLite.MEASUREMENT_COLUMN_VALUE)));
        measurmentUnit.setText(rs.getString(rs.getColumnIndex(SQLite.MEASUREMENT_COLUMN_UNIT)));
    }

    public void DeleteMeasurment(View view) {
        db.deleteMeasurement(measurmentID);

        Toast.makeText(getApplicationContext(), "Měření vloženo vymazáno", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MeasurementInfo.this.finish();
            }
        }, 2000);
    }
}
