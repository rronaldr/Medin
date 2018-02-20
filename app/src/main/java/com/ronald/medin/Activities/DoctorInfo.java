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

import com.ronald.medin.Classes.Doctor;
import com.ronald.medin.Fragments.DoctorFragment;
import com.ronald.medin.MainActivity;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class DoctorInfo extends AppCompatActivity {

    EditText doctorSpecialization;
    EditText doctorName;
    EditText doctorSurname;
    Button deleteBtn;
    Button editBtn;
    Button saveBtn;
    private SQLite db;
    int doctorID;

    Doctor updatedDoctor = new Doctor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        doctorSpecialization = findViewById(R.id.doctorinfo_specialization);
        doctorName = findViewById(R.id.doctorinfo_name);
        doctorSurname = findViewById(R.id.doctorinfo_surname);

        doctorID = getIntent().getIntExtra("ItemID",0);

        db = new SQLite(this);
        Cursor rs = db.getDoctor(doctorID);
        rs.moveToFirst();
        doctorSpecialization.setText(rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_SPECIALIZATION)));
        doctorName.setText(rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_NAME)));
        doctorSurname.setText(rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_SURNAME)));

        updatedDoctor.setId(rs.getInt(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_ID)));
        updatedDoctor.setSpecialization(rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_SPECIALIZATION)));
        updatedDoctor.setName(rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_NAME)));
        updatedDoctor.setSurname((rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_SURNAME))));
        updatedDoctor.setEmail((rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_EMAIL))));
        updatedDoctor.setTelephone(rs.getInt(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_TELEPHONE)));
        updatedDoctor.setWorkingHours(rs.getString(rs.getColumnIndex(SQLite.DOCTOR_COLUMN_WORKING_HOURS)));

        rs.close();
        db.close();


    }

    public void DeleteDoctor(View view) {
        db.deleteDoctor(doctorID);
        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
        Intent navigate = new Intent(getApplicationContext(), DoctorFragment.class);
        navigate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(navigate);
    }

    public void EditDoctor(View view) {
        doctorSpecialization.setFocusableInTouchMode(true);
        doctorName.setFocusableInTouchMode(true);
        doctorSurname.setFocusableInTouchMode(true);

        deleteBtn = findViewById(R.id.doctorinfo_deleteBtn);
        deleteBtn.setVisibility(View.GONE);

        editBtn = findViewById(R.id.doctorinfo_editBtn);
        editBtn.setVisibility(View.GONE);

        saveBtn = findViewById(R.id.doctorinfo_saveBtn);
        saveBtn.setVisibility(View.VISIBLE);
    }

    public void UpdateDoctor(View view) {

        updatedDoctor.setSpecialization(doctorSpecialization.getText().toString());
        updatedDoctor.setName(doctorName.getText().toString());
        updatedDoctor.setSurname(doctorSurname.getText().toString());
        db.updateDoctor(updatedDoctor);

        doctorSpecialization.setFocusableInTouchMode(false);
        doctorName.setFocusableInTouchMode(false);
        doctorSurname.setFocusableInTouchMode(false);

        deleteBtn = findViewById(R.id.doctorinfo_deleteBtn);
        deleteBtn.setVisibility(View.VISIBLE);

        editBtn = findViewById(R.id.doctorinfo_editBtn);
        editBtn.setVisibility(View.VISIBLE);

        saveBtn = findViewById(R.id.doctorinfo_saveBtn);
        saveBtn.setVisibility(View.GONE);

        Toast.makeText(getApplicationContext(), "Informace o doktorovi byli úspěšně změněny", Toast.LENGTH_SHORT).show();
    }
}
