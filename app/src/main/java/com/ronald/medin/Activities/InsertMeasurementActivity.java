package com.ronald.medin.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ronald.medin.Classes.Measurement;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class InsertMeasurementActivity extends AppCompatActivity {

    //Inicializace proměnných
    String measurementName;
    String measurementUnit;

    boolean nameSource;
    boolean unitSource;

    Spinner spinnerName;
    Spinner spinnerUnit;

    EditText editName;
    EditText editUnit;
    EditText editValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_measurement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Přiřazení k view
        spinnerName = findViewById(R.id.spinner_insertMeasurement_name);
        spinnerUnit = findViewById(R.id.spinner_insertMeasurement_unit);

        editName = findViewById(R.id.edit_insertMeasurement_name);
        editUnit = findViewById(R.id.edit_insertMeasurement_unit);
        editValue = findViewById(R.id.edit_insertMeasurement_value);

    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * OnItemSelectedListener pro spinner
         */
        spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Na základě hodnoty spinneru měření, nastaví spinneru s jednotkami, jedntku vztahující se k měření
                switch (i){
                    case 0: spinnerUnit.setSelection(i);
                        break;
                    case 1: spinnerUnit.setSelection(i);
                        break;
                    case 2: spinnerUnit.setSelection(i);
                        break;
                    case 3: spinnerUnit.setSelection(i);
                        break;
                    case 4: spinnerUnit.setSelection(i);
                        break;
                    case 5: spinnerUnit.setSelection(i);
                        break;

                }

                //Zobrazení a schování editextu na základě hodnoty spinneru
                if(spinnerName.getSelectedItem().toString().equals(getString(R.string.custom))){
                    editName.setVisibility(View.VISIBLE);
                    nameSource = true;
                } else {
                    editName.setVisibility(View.GONE);
                    nameSource = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /**
         * OnItemSelectedListener pro spinner
         */
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinnerUnit.getSelectedItem().toString().equals(getString(R.string.custom))){
                    editUnit.setVisibility(View.VISIBLE);
                    unitSource = true;
                } else {
                    editUnit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * onClick event
     * @param view tlačítko vložit
     */
    public void InsertMeasurement(View view) {
        SQLite db = new SQLite(this);

        //Přiřazení hodnot
        if(nameSource){
            measurementName = editName.getText().toString();
        } else {
            measurementName = spinnerName.getSelectedItem().toString();
        }
        if (unitSource){
            measurementUnit = editUnit.getText().toString();
        } else {
            measurementUnit = spinnerUnit.getSelectedItem().toString();
        }

        //Ověření vstupů
        if(measurementName.matches("") || measurementUnit.matches("") || editValue.getText().toString().matches("")){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.fillAllFields), Toast.LENGTH_SHORT).show();
        } else {
            //Instance objektu
            Measurement measurementToInsert = new Measurement(measurementName, Integer.valueOf(editValue.getText().toString()), measurementUnit);
            //Zobrazení zprávy uživateli
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.measurmentInserted), Toast.LENGTH_SHORT).show();

            //Vložení do databáze
            db.insertMeasurement(measurementToInsert);
            //Ukončení spojení s db
            db.close();

            //Ukonření aktivity po 2s
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InsertMeasurementActivity.this.finish();
                }
            }, 2000);
        }
    }

}
