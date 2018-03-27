package com.ronald.medin.Activities;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ronald.medin.Classes.Measurement;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class MeasurementInfoActivity extends AppCompatActivity {

    int measurementId;
    private SQLite db;
    Measurement measurementToUpdate;

    TextView measurementNameText;

    EditText editTextBackgroundStyle;
    EditText measurementDateEdit;
    EditText measurementValueEdit;
    EditText measurementUnitEdit;
    Button measurementEditBtn;
    Button measurementDeleteBtn;
    Button measurementSaveBtn;

    Drawable drawableEditTextStyle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        measurementId = getIntent().getIntExtra("ItemID",0);

        measurementNameText = findViewById(R.id.text_measurementInfo_name);

        editTextBackgroundStyle = findViewById(R.id.edit_measurementInfo_style);
        measurementDateEdit = findViewById(R.id.edit_measurementInfo_date);
        measurementValueEdit = findViewById(R.id.edit_measurementInfo_value);
        measurementUnitEdit = findViewById(R.id.edit_measurementInfo_unit);

        measurementEditBtn = findViewById(R.id.btn_measurementInfo_edit);
        measurementDeleteBtn = findViewById(R.id.btn_measurementInfo_delete);
        measurementSaveBtn = findViewById(R.id.btn_measurementInfo_save);

        drawableEditTextStyle = editTextBackgroundStyle.getBackground();

        measurementToUpdate = new Measurement();

        db = new SQLite(this);
        Cursor medicineInfoQuery = db.getMeasurement(measurementId);
        medicineInfoQuery.moveToFirst();

        measurementNameText.setText(medicineInfoQuery.getString(medicineInfoQuery.getColumnIndex(SQLite.MEASUREMENT_COLUMN_NAME)));
        measurementDateEdit.setText(medicineInfoQuery.getString(medicineInfoQuery.getColumnIndex(SQLite.MEASUREMENT_COLUMN_CREATED)));
        measurementValueEdit.setText(medicineInfoQuery.getString(medicineInfoQuery.getColumnIndex(SQLite.MEASUREMENT_COLUMN_VALUE)));
        measurementUnitEdit.setText(medicineInfoQuery.getString(medicineInfoQuery.getColumnIndex(SQLite.MEASUREMENT_COLUMN_UNIT)));

        measurementToUpdate.setId(medicineInfoQuery.getInt(medicineInfoQuery.getColumnIndex(SQLite.MEASUREMENT_COLUMN_ID)));
        measurementToUpdate.setMeasurement_name(medicineInfoQuery.getString(medicineInfoQuery.getColumnIndex(SQLite.MEASUREMENT_COLUMN_NAME)));
        measurementToUpdate.setMeasurement_unit(medicineInfoQuery.getString(medicineInfoQuery.getColumnIndex(SQLite.MEASUREMENT_COLUMN_UNIT)));
        measurementToUpdate.setMeasurement_created(medicineInfoQuery.getString(medicineInfoQuery.getColumnIndex(SQLite.MEASUREMENT_COLUMN_CREATED)));

        medicineInfoQuery.close();
        db.close();
    }

    public void editMeasurement(View view) {

        measurementEditBtn.setVisibility(View.GONE);
        measurementDeleteBtn.setVisibility(View.GONE);
        measurementSaveBtn.setVisibility(View.VISIBLE);

        measurementValueEdit.setBackground(drawableEditTextStyle);
        measurementValueEdit.setFocusableInTouchMode(true);

    }

    public void deleteMeasurement(View view) {
        db.deleteMeasurement(measurementId);
        db.close();

        Toast.makeText(getApplicationContext(), "Měření vymazáno", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MeasurementInfoActivity.this.finish();
            }
        }, 2000);
    }


    public void saveMeasurement(View view) {
        if(!measurementValueEdit.getText().toString().matches("")){

            measurementToUpdate.setMeasurement_value(Integer.parseInt(measurementValueEdit.getText().toString()));

            db.updateMeasurement(measurementToUpdate);
            db.close();
            Toast.makeText(getApplicationContext(), "Měření bylo úspěšně pozměněno", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MeasurementInfoActivity.this.finish();
                }
            }, 2000);
        } else {
            Toast.makeText(getApplicationContext(), "Pole s hodnotou je prázdné", Toast.LENGTH_SHORT).show();
        }
    }
}
