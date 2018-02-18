package com.ronald.medin.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ronald.medin.Fragments.DoctorFragment;
import com.ronald.medin.MainActivity;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class DoctorInfo extends AppCompatActivity {

    private SQLite db;
    int doctorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText doctorName = findViewById(R.id.doctorinfo_name);
        EditText doctorSurname = findViewById(R.id.doctorinfo_surname);

        doctorID = getIntent().getIntExtra("ItemID",0);

        db = new SQLite(this);
        Cursor rs = db.getDoctor(doctorID);
        rs.moveToFirst();
        doctorName.setText(rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_NAME)));
        doctorSurname.setText(rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_SURNAME)));



    }

    public void DeleteDoctor(View view) {
        db.deleteDoctor(doctorID);
        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
        Intent navigate = new Intent(getApplicationContext(), DoctorFragment.class);
        navigate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(navigate);
    }
}
