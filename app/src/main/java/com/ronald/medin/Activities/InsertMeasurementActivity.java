package com.ronald.medin.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ronald.medin.Classes.Measurement;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class InsertMeasurementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_measurement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    public void InsertMeasurement(View view) {
        SQLite db = new SQLite(this);

        Spinner spinnerName = findViewById(R.id.insertMeasurement_NameSpinner);
        Spinner spinnerUnit = findViewById(R.id.insertMeasurement_UnitSpinner);
        EditText editName = findViewById(R.id.insertMeasurement_EditName);
        EditText editValue = findViewById(R.id.insertMeasurement_EditValue);
        EditText editUnit = findViewById(R.id.insertMeasurement_EditUnit);

        if(editValue.getText().toString().equals(null)){
            Toast.makeText(getApplicationContext(), "Vyplňte všechna pole", Toast.LENGTH_SHORT).show();
        } else {
            Measurement newMeasurment = new Measurement(spinnerName.getSelectedItem().toString(), Integer.valueOf(editValue.getText().toString()), spinnerUnit.getSelectedItem().toString());
            if(db.insertMeasurement(newMeasurment)) {
                Toast.makeText(getApplicationContext(), "Měření vloženo úspěšně", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InsertMeasurementActivity.this.finish();
                    }
                }, 2000);
            } else {
                Toast.makeText(getApplicationContext(), "Chyba", Toast.LENGTH_SHORT).show();
            }

//                final Intent navigate = new Intent(this, DoctorFragment.class);
//                navigate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(navigate);

        }
    }

}
