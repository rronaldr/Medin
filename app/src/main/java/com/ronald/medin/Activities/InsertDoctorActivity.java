package com.ronald.medin.Activities;

import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.ronald.medin.Classes.Address;
import com.ronald.medin.Classes.Contact;
import com.ronald.medin.Classes.Doctor;
import com.ronald.medin.Classes.Working_hours;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

import java.util.Calendar;
import java.util.Locale;

public class InsertDoctorActivity extends AppCompatActivity implements View.OnClickListener {

    //Inicializace proměnných
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

        //Přiřazení view
        doctorName = findViewById(R.id.edit_insertDoctor_name);
        doctorSurname = findViewById(R.id.edit_insertDoctor_surname);
        doctorSpecializationSpinner = findViewById(R.id.spinner_insertDoctor_specialization);
        doctorSpecializationEditText = findViewById(R.id.edit_insertDoctor_specialization);

        contactEmail = findViewById(R.id.edit_insertDoctor_email);
        contactPhone = findViewById(R.id.edit_insertDoctor_phone);

        addressCity = findViewById(R.id.edit_insertDoctor_city);
        addressStreet = findViewById(R.id.edit_insertDoctor_street);
        addressStreetNumber = findViewById(R.id.edit_insertDoctor_street_number);

        //Přirazení view a onClickListeneru(TimePicker Dialog)
        whMondayFrom = findViewById(R.id.edit_insertDoctor_monday_from);
        whMondayFrom.setOnClickListener(this);
        whMondayTo = findViewById(R.id.edit_insertDoctor_monday_to);
        whMondayTo.setOnClickListener(this);

        whTuesdayFrom = findViewById(R.id.edit_insertDoctor_tuesday_from);
        whTuesdayFrom.setOnClickListener(this);
        whTuesdayTo = findViewById(R.id.edit_insertDoctor_tuesday_to);
        whTuesdayTo.setOnClickListener(this);

        whWednesdayFrom = findViewById(R.id.edit_insertDoctor_wednesday_from);
        whWednesdayFrom.setOnClickListener(this);
        whWednesdayTo = findViewById(R.id.edit_insertDoctor_wednesday_to);
        whWednesdayTo.setOnClickListener(this);

        whThursdayFrom = findViewById(R.id.edit_insertDoctor_thursday_from);
        whThursdayFrom.setOnClickListener(this);
        whThursdayTo = findViewById(R.id.edit_insertDoctor_thursday_to);
        whThursdayTo.setOnClickListener(this);

        whFridayFrom = findViewById(R.id.edit_insertDoctor_friday_from);
        whFridayFrom.setOnClickListener(this);
        whFridayTo = findViewById(R.id.edit_insertDoctor_friday_to);
        whFridayTo.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Na základé vybraného itemu spinneru ukáže edittext nebo ho schová
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

    /**
     * onClick event
     * @param view tlačítko vložit
     */
    public void InsertDoctor(View view) {
        //Nové připojení do db
        SQLite db = new SQLite(this);

        //Přiřazení hodnoty na základe pozice spinneru
        if(specializationSource){
            doctorSpecializationText = doctorSpecializationEditText.getText().toString();
        } else {
            doctorSpecializationText = doctorSpecializationSpinner.getSelectedItem().toString();
        }

        //Ověření vstupů
        if (doctorName.getText().toString().matches("") || doctorSurname.getText().toString().matches("") || doctorSpecializationText.matches("")) {

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.doctorInsertInputError), Toast.LENGTH_SHORT).show();

        } else {

            //Přiřazení hodnoty, aby nebyly null v databázi
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

            //Vytvoření instancí objektů pro vložení do db
            Doctor doctorToInsert = new Doctor(doctorName.getText().toString(), doctorSurname.getText().toString(), doctorSpecializationText);
            Contact contactToInsert = new Contact(contactEmail.getText().toString(), contactPhoneInt);
            Address addressToInsert = new Address(addressCity.getText().toString(), addressStreet.getText().toString(), addressStreetNumberInt);

            Working_hours mondayToInsert = new Working_hours(1, whMondayFrom.getText().toString(), whMondayTo.getText().toString());
            Working_hours tuesdayToInsert = new Working_hours(2, whTuesdayFrom.getText().toString(), whTuesdayTo.getText().toString());
            Working_hours wednesdayToInsert = new Working_hours(3, whWednesdayFrom.getText().toString(), whWednesdayTo.getText().toString());
            Working_hours thursdayToInsert = new Working_hours(4, whThursdayFrom.getText().toString(), whThursdayTo.getText().toString());
            Working_hours fridayToInsert = new Working_hours(5, whFridayFrom.getText().toString(), whFridayTo.getText().toString());

            //Vložení instancí do db
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


            Toast.makeText(getApplicationContext(), getResources().getString(R.string.insertedSuccessfully), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InsertDoctorActivity.this.finish();
                }
            }, 2000);
        }
    }


    /**
     * onClick
     * @param view určuje na jaké view bylo kliknuto
     */
    @Override
    public void onClick(View view) {
        showTimePicker(view.getId());
    }

    /**
     * Zobrazí TimePickerDialog
     * @param viewId id viewu do kterého se uloží hodnota TimePickeru
     */
    public void showTimePicker(final int viewId){
        final Calendar c = Calendar.getInstance();
        int Hour = c.get(Calendar.HOUR_OF_DAY);
        int Minute = c.get(Calendar.MINUTE);
        final EditText editText = findViewById(viewId);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String hourString;
                        if (hourOfDay < 10)
                            hourString = "0" + hourOfDay;
                        else
                            hourString = "" +hourOfDay;

                        String minuteSting;
                        if (minute < 10)
                            minuteSting = "0" + minute;
                        else
                            minuteSting = "" +minute;
                        editText.setText(hourString + ":" + minuteSting);
                    }
                }, Hour, Minute, true);
        timePickerDialog.show();
    }
}