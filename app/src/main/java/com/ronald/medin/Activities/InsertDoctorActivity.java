package com.ronald.medin.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ronald.medin.Classes.Address;
import com.ronald.medin.Classes.Contact;
import com.ronald.medin.Classes.Doctor;
import com.ronald.medin.Classes.Working_hours;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class InsertDoctorActivity extends AppCompatActivity {

    String doctorSpecializationText;
    boolean specializationSource;
    int contactPhoneInt;
    int addressStreetNumberInt;

    EditText doctorName;
    EditText doctorSurname;
    Spinner doctorSpecializationSpinner;
    EditText doctorSpecializationEditText;

    EditText contactEmail;
    EditText contactPhone;

    EditText addressCity;
    EditText addressStreet;
    EditText addressStreetNumber;

    EditText whMondayFrom;
    EditText whMondayTo;

    EditText whTuesdayFrom;
    EditText whTuesdayTo;

    EditText whWednesdayFrom;
    EditText whWednesdayTo;

    EditText whThursdayFrom;
    EditText whThursdayTo;

    EditText whFridayFrom;
    EditText whFridayTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_insert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        doctorName = findViewById(R.id.edit_insertDoctor_name);
        doctorSurname = findViewById(R.id.edit_insertDoctor_surname);
        doctorSpecializationSpinner = findViewById(R.id.spinner_insertDoctor_specialization);
        doctorSpecializationEditText = findViewById(R.id.edit_insertDoctor_specialization);

        contactEmail = findViewById(R.id.edit_insertDoctor_email);
        contactPhone = findViewById(R.id.edit_insertDoctor_phone);

        addressCity = findViewById(R.id.edit_insertDoctor_city);
        addressStreet = findViewById(R.id.edit_insertDoctor_street);
        addressStreetNumber = findViewById(R.id.edit_insertDoctor_street_number);

        whMondayFrom = findViewById(R.id.edit_insertDoctor_monday_from);
        whMondayTo = findViewById(R.id.edit_insertDoctor_monday_to);

        whTuesdayFrom = findViewById(R.id.edit_insertDoctor_tuesday_from);
        whTuesdayTo = findViewById(R.id.edit_insertDoctor_tuesday_to);

        whWednesdayFrom = findViewById(R.id.edit_insertDoctor_wednesday_from);
        whWednesdayTo = findViewById(R.id.edit_insertDoctor_wednesday_to);

        whThursdayFrom = findViewById(R.id.edit_insertDoctor_thursday_from);
        whThursdayTo = findViewById(R.id.edit_insertDoctor_thursday_to);

        whFridayFrom = findViewById(R.id.edit_insertDoctor_friday_from);
        whFridayTo = findViewById(R.id.edit_insertDoctor_friday_to);
    }

    @Override
    protected void onStart() {
        super.onStart();

        doctorSpecializationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(doctorSpecializationSpinner.getSelectedItem().toString().equals(getString(R.string.custom))){
                    doctorSpecializationEditText.setVisibility(View.VISIBLE);
                    specializationSource = true;

                } else {
                    doctorSpecializationEditText.setVisibility(View.GONE);
                    specializationSource = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void InsertDoctor(View view) {
        SQLite db = new SQLite(this);

        if(specializationSource){
            doctorSpecializationText = doctorSpecializationEditText.getText().toString();
        } else {
            doctorSpecializationText = doctorSpecializationSpinner.getSelectedItem().toString();
        }

        if (doctorName.getText().toString().matches("") || doctorSurname.getText().toString().matches("") || doctorSpecializationText.matches("")) {

            Toast.makeText(getApplicationContext(), "Jméno, Přijmění a Specializace musí být vyplněna", Toast.LENGTH_SHORT).show();

        } else {

            if(contactPhone.getText().toString().matches("")){
                contactPhoneInt = 0;
            } else {
                contactPhoneInt = Integer.parseInt(contactPhone.getText().toString());
            }
            if(addressStreetNumber.getText().toString().matches("")){
                addressStreetNumberInt = 0;
            } else {
                addressStreetNumberInt = Integer.parseInt(addressStreetNumber.getText().toString());
            }


            Doctor doctorToInsert = new Doctor(doctorName.getText().toString(), doctorSurname.getText().toString(), doctorSpecializationText);
            Contact contactToInsert = new Contact(contactEmail.getText().toString(), contactPhoneInt);
            Address addressToInsert = new Address(addressCity.getText().toString(), addressStreet.getText().toString(), addressStreetNumberInt);

            Working_hours mondayToInsert = new Working_hours(1, whMondayFrom.getText().toString(), whMondayTo.getText().toString());
            Working_hours tuesdayToInsert = new Working_hours(2, whTuesdayFrom.getText().toString(), whTuesdayTo.getText().toString());
            Working_hours wednesdayToInsert = new Working_hours(3, whWednesdayFrom.getText().toString(), whWednesdayTo.getText().toString());
            Working_hours thursdayToInsert = new Working_hours(4, whThursdayFrom.getText().toString(), whThursdayTo.getText().toString());
            Working_hours fridayToInsert = new Working_hours(5, whFridayFrom.getText().toString(), whFridayTo.getText().toString());

            long insertedDoctorId = db.insertDoctor(doctorToInsert);
            long insertedContactId = db.insertContact(contactToInsert);
            long insertedAddressId = db.insertAddress(addressToInsert);

            long insertedMondayId = db.insertWorking_hours(mondayToInsert);
            long insertedTuesdayId = db.insertWorking_hours(tuesdayToInsert);
            long insertedWednesdayId = db.insertWorking_hours(wednesdayToInsert);
            long insertedThursdayId = db.insertWorking_hours(thursdayToInsert);
            long insertedFridayId = db.insertWorking_hours(fridayToInsert);

            db.insertDoctorContact((int) insertedDoctorId, (int) insertedContactId);
            db.insertDoctorAddress((int) insertedDoctorId, (int) insertedAddressId);

            db.insertDoctorWorking_hours((int) insertedDoctorId, (int) insertedMondayId);
            db.insertDoctorWorking_hours((int) insertedDoctorId, (int) insertedTuesdayId);
            db.insertDoctorWorking_hours((int) insertedDoctorId, (int) insertedWednesdayId);
            db.insertDoctorWorking_hours((int) insertedDoctorId, (int) insertedThursdayId);
            db.insertDoctorWorking_hours((int) insertedDoctorId, (int) insertedFridayId);

            db.close();


            Toast.makeText(getApplicationContext(), "Vložen úspěšně", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InsertDoctorActivity.this.finish();
                }
            }, 2000);
        }
    }
}