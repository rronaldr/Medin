package com.ronald.medin.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ronald.medin.Classes.Address;
import com.ronald.medin.Classes.Contact;
import com.ronald.medin.Classes.Doctor;
import com.ronald.medin.Classes.Working_hours;
import com.ronald.medin.Fragments.DoctorFragment;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

import java.util.Calendar;

public class DoctorInfoActivity extends AppCompatActivity implements View.OnClickListener {

    //Inicializace proměnných
    Doctor doctorToUpdate;
    Contact contactToUpdate;
    Address addressToUpdate;

    Working_hours mondayToUpdate;
    Working_hours tuesdayToUpdate;
    Working_hours wednesdayToUpdate;
    Working_hours thursdayToUpdate;
    Working_hours fridayToUpdate;

    private SQLite db;
    int doctorId;
    int contactId;
    int addressId;
    int doctor_contactId;
    int doctor_addressId;
    int doctor_working_hoursId1;
    int doctor_working_hoursId2;
    int doctor_working_hoursId3;
    int doctor_working_hoursId4;
    int doctor_working_hoursId5;
    int mondayId;
    int tuesdayId;
    int wednesdayId;
    int thursdayId;
    int fridayId;

    String doctorSpecialization;
    boolean specializationSource;

    EditText editTextBackgroundStyle;
    EditText doctorNameEdit;
    EditText doctorSurnameEdit;
    EditText doctorSpecializationEdit;
    EditText contactEmailEdit;
    EditText contactPhoneEdit;
    EditText addressCityEdit;
    EditText addressStreetEdit;
    EditText addressStreetNumberEdit;
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

    Spinner doctorSpecializationSpinner;
    Drawable drawableEditTextStyle;

    Button doctorInfoEditBtn;
    Button doctorInfoDeleteBtn;
    Button doctorInfoSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Přiřazení view k proměnným
        editTextBackgroundStyle = findViewById(R.id.edit_doctorInfo_style);
        drawableEditTextStyle = editTextBackgroundStyle.getBackground();

        doctorNameEdit = findViewById(R.id.edit_doctorInfo_name);
        doctorSurnameEdit = findViewById(R.id.edit_doctorInfo_surname);
        doctorSpecializationEdit = findViewById(R.id.edit_doctorInfo_specialization);

        doctorSpecializationSpinner = findViewById(R.id.spinner_doctorInfo_specialization);
        doctorSpecializationSpinner.setEnabled(false);

        contactEmailEdit = findViewById(R.id.edit_doctorInfo_email);
        contactPhoneEdit = findViewById(R.id.edit_doctorInfo_phone);

        addressCityEdit = findViewById(R.id.edit_doctorInfo_city);
        addressStreetEdit = findViewById(R.id.edit_doctorInfo_street);
        addressStreetNumberEdit = findViewById(R.id.edit_doctorInfo_streetNumber);

        whMondayFrom = findViewById(R.id.edit_doctorInfo_mondayFrom);
        whMondayTo = findViewById(R.id.edit_doctorInfo_mondayTo);
        whTuesdayFrom = findViewById(R.id.edit_doctorInfo_tuesdayFrom);
        whTuesdayTo = findViewById(R.id.edit_doctorInfo_tuesdayTo);
        whWednesdayFrom = findViewById(R.id.edit_doctorInfo_wednesdayFrom);
        whWednesdayTo = findViewById(R.id.edit_doctorInfo_wednesdayTo);
        whThursdayFrom = findViewById(R.id.edit_doctorInfo_thursdayFrom);
        whThursdayTo = findViewById(R.id.edit_doctorInfo_thursdayTo);
        whFridayFrom = findViewById(R.id.edit_doctorInfo_fridayFrom);
        whFridayTo = findViewById(R.id.edit_doctorInfo_fridayTo);

        doctorInfoEditBtn = findViewById(R.id.btn_doctorInfo_edit);
        doctorInfoDeleteBtn = findViewById(R.id.btn_doctorInfo_delete);
        doctorInfoSaveBtn = findViewById(R.id.btn_doctorInfo_save);

        //Přidání onClickListeneru k jednotlivým widgetům (vyvolání timePickeru)
        whMondayFrom.setOnClickListener(this);
        whMondayTo.setOnClickListener(this);
        whTuesdayFrom.setOnClickListener(this);
        whTuesdayTo.setOnClickListener(this);
        whWednesdayFrom.setOnClickListener(this);
        whWednesdayTo.setOnClickListener(this);
        whThursdayFrom.setOnClickListener(this);
        whThursdayTo.setOnClickListener(this);
        whFridayFrom.setOnClickListener(this);
        whFridayTo.setOnClickListener(this);

        //Získání id Doctora pro vypsání dat o něm
        doctorId = getIntent().getIntExtra("ItemID",0);

        //Nové spojení s DB
        db = new SQLite(this);
        //Získání dat o konkrétním doktorovi
        Cursor doctorInfoQuery = db.getDoctorInfo(doctorId);
        doctorInfoQuery.moveToFirst();

        contactId = doctorInfoQuery.getInt(doctorInfoQuery.getColumnIndex(SQLite.CONTACT_COLUMN_ID));
        addressId = doctorInfoQuery.getInt(doctorInfoQuery.getColumnIndex(SQLite.ADDRESS_COLUMN_ID));

        //Přiřazení hodnot k view
        doctorNameEdit.setText(doctorInfoQuery.getString(doctorInfoQuery.getColumnIndex(SQLite.DOCTOR_COLUMN_NAME)));
        doctorSurnameEdit.setText(doctorInfoQuery.getString(doctorInfoQuery.getColumnIndex(SQLite.DOCTOR_COLUMN_SURNAME)));
        doctorSpecialization = doctorInfoQuery.getString(doctorInfoQuery.getColumnIndex(SQLite.DOCTOR_COLUMN_SPECIALIZATION));

        contactEmailEdit.setText(doctorInfoQuery.getString(doctorInfoQuery.getColumnIndex(SQLite.CONTACT_COLUMN_EMAIL)));
        contactPhoneEdit.setText(doctorInfoQuery.getString(doctorInfoQuery.getColumnIndex(SQLite.CONTACT_COLUMN_TELEPHONE)));

        addressCityEdit.setText(doctorInfoQuery.getString(doctorInfoQuery.getColumnIndex(SQLite.ADDRESS_COLUMN_CITY)));
        addressStreetEdit.setText(doctorInfoQuery.getString(doctorInfoQuery.getColumnIndex(SQLite.ADDRESS_COLUMN_STREET)));
        addressStreetNumberEdit.setText(doctorInfoQuery.getString(doctorInfoQuery.getColumnIndex(SQLite.ADDRESS_COLUMN_STREET_NUMBER)));


        //ukončení spojení s DB
        doctorInfoQuery.close();
        db.close();

        Cursor doctorWorking_hoursQuery = db.getDoctor_Working_hours(doctorId);
        Log.e("Data",DatabaseUtils.dumpCursorToString(doctorWorking_hoursQuery));
        //Získání dat o pracovních hodnotách a jejich přiřazení
        if (doctorWorking_hoursQuery.moveToFirst()){
            do{
                switch (doctorWorking_hoursQuery.getPosition()){
                    case 0:
                        doctor_working_hoursId1 = doctorWorking_hoursQuery.getInt(0);
                        mondayId = doctorWorking_hoursQuery.getInt(1);
                        whMondayFrom.setText(doctorWorking_hoursQuery.getString(3));
                        whMondayTo.setText(doctorWorking_hoursQuery.getString(4));
                        break;
                    case 1:
                        doctor_working_hoursId2 = doctorWorking_hoursQuery.getInt(0);
                        tuesdayId = doctorWorking_hoursQuery.getInt(1);
                        whTuesdayFrom.setText(doctorWorking_hoursQuery.getString(3));
                        whTuesdayTo.setText(doctorWorking_hoursQuery.getString(4));
                        break;
                    case 2:
                        doctor_working_hoursId3 = doctorWorking_hoursQuery.getInt(0);
                        wednesdayId = doctorWorking_hoursQuery.getInt(1);
                        whWednesdayFrom.setText(doctorWorking_hoursQuery.getString(3));
                        whWednesdayTo.setText(doctorWorking_hoursQuery.getString(4));
                        break;
                    case 3:
                        doctor_working_hoursId4 = doctorWorking_hoursQuery.getInt(0);
                        thursdayId = doctorWorking_hoursQuery.getInt(1);
                        whThursdayFrom.setText(doctorWorking_hoursQuery.getString(3));
                        whThursdayTo.setText(doctorWorking_hoursQuery.getString(4));
                        break;
                    case 4:
                        doctor_working_hoursId5 = doctorWorking_hoursQuery.getInt(0);
                        fridayId = doctorWorking_hoursQuery.getInt(1);
                        whFridayFrom.setText(doctorWorking_hoursQuery.getString(3));
                        whFridayTo.setText(doctorWorking_hoursQuery.getString(4));
                        break;
                }
            }while(doctorWorking_hoursQuery.moveToNext());
        }
        doctorWorking_hoursQuery.close();
        db.close();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Naplnění spinneru hodnotami
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.insertDoctorSpecializationSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorSpecializationSpinner.setAdapter(adapter);
        //Určení defaultní pozice spinneru na základé hodnoty o doktorovi
        if (doctorSpecialization != null) {
            if(adapter.getPosition(doctorSpecialization) == -1){
                int spinnerPosition = adapter.getPosition(getString(R.string.custom));
                doctorSpecializationSpinner.setSelection(spinnerPosition);
            } else {
                int spinnerPosition = adapter.getPosition(doctorSpecialization);
                doctorSpecializationSpinner.setSelection(spinnerPosition);
            }
        }
        //Pokud se spinner rovná Jiné, tak zobraz edittext
        if(doctorSpecializationSpinner.getSelectedItem().toString().equals(getString(R.string.custom))){
            doctorSpecializationEdit.setVisibility(View.VISIBLE);
            doctorSpecializationEdit.setText(doctorSpecialization);
            specializationSource = true;
        }

        //Vytvoření setOnItemSelectedListeneru
        doctorSpecializationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(doctorSpecializationSpinner.getSelectedItem().toString().equals(getString(R.string.custom))){
                    doctorSpecializationEdit.setVisibility(View.VISIBLE);
                    doctorSpecializationEdit.setText(doctorSpecialization);
                    specializationSource = true;

                } else {
                    doctorSpecializationEdit.setVisibility(View.GONE);
                    specializationSource = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //onClick procedura po stisknutí tlačítka Smazat
    public void deleteDoctorInfo(View view) {

//        Vymazání všech údajů o doktorovi
        db.deleteDoctor(doctorId);
        db.deleteContact(contactId);
        db.deleteAddress(addressId);

        db.deleteDoctor_Contact(doctor_contactId);
        db.deleteDoctor_Address(doctor_addressId);

        db.deleteWorking_hours(mondayId);
        db.deleteWorking_hours(tuesdayId);
        db.deleteWorking_hours(wednesdayId);
        db.deleteWorking_hours(thursdayId);
        db.deleteWorking_hours(fridayId);

        db.deleteDoctor_Working_hours(doctor_working_hoursId1);
        db.deleteDoctor_Working_hours(doctor_working_hoursId2);
        db.deleteDoctor_Working_hours(doctor_working_hoursId3);
        db.deleteDoctor_Working_hours(doctor_working_hoursId4);
        db.deleteDoctor_Working_hours(doctor_working_hoursId5);


        //Zobrazení toastu
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.doctorDeleted), Toast.LENGTH_SHORT).show();

        //Ukončení activity po 2s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DoctorInfoActivity.this.finish();
            }
        }, 2000);
    }

    //onClick po stisknutí tlačítka Změnit
    public void editDoctor(View view) {

        makeViewEditable();
    }

    //onClick po stisknutí tlačítka Uložit
    public void updateDoctor(View view) {

        //Přiřazení hodnoty k doctorSpecialization
        if(specializationSource){
            doctorSpecialization = doctorSpecializationEdit.getText().toString();
        } else {
            doctorSpecialization = doctorSpecializationSpinner.getSelectedItem().toString();
        }

        createUpdateObjects();

        //Updatnutí všech informací o doktorovi
        db.updateDoctor(doctorToUpdate);
        db.updateContact(contactToUpdate);
        db.updateAddress(addressToUpdate);

        db.updateWorking_hours(mondayToUpdate);
        db.updateWorking_hours(tuesdayToUpdate);
        db.updateWorking_hours(wednesdayToUpdate);
        db.updateWorking_hours(thursdayToUpdate);
        db.updateWorking_hours(fridayToUpdate);

        //Uzavření spojení s db
        db.close();

        //Vypsání zprávy uživateli
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.doctorUpdated), Toast.LENGTH_SHORT).show();

        //Ukončení activity po 2s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DoctorInfoActivity.this.finish();
            }
        }, 2000);
    }

    //Procedura vytvoří instance tříd k updatnutí
    public void createUpdateObjects(){
        doctorToUpdate = new Doctor(doctorId, doctorNameEdit.getText().toString(), doctorSurnameEdit.getText().toString(), doctorSpecialization);
        contactToUpdate = new Contact(contactId, contactEmailEdit.getText().toString(), Integer.parseInt(contactPhoneEdit.getText().toString()));
        addressToUpdate = new Address(addressId, addressCityEdit.getText().toString(), addressStreetEdit.getText().toString(), Integer.parseInt(addressStreetNumberEdit.getText().toString()));

        mondayToUpdate = new Working_hours(mondayId, 1, whMondayFrom.getText().toString(), whMondayTo.getText().toString());
        tuesdayToUpdate = new Working_hours(tuesdayId, 2, whTuesdayFrom.getText().toString(), whTuesdayTo.getText().toString());
        wednesdayToUpdate = new Working_hours(wednesdayId, 3, whWednesdayFrom.getText().toString(), whWednesdayTo.getText().toString());
        thursdayToUpdate = new Working_hours(thursdayId, 4, whThursdayFrom.getText().toString(), whThursdayTo.getText().toString());
        fridayToUpdate = new Working_hours(fridayId, 5, whFridayFrom.getText().toString(), whFridayTo.getText().toString());
    }

    //Procedura změní vlastností view, tak aby se dali editovat
    public void makeViewEditable(){
        doctorInfoEditBtn.setVisibility(View.GONE);
        doctorInfoDeleteBtn.setVisibility(View.GONE);
        doctorInfoSaveBtn.setVisibility(View.VISIBLE);

        doctorSpecializationSpinner.setEnabled(true);

        doctorNameEdit.setBackground(drawableEditTextStyle);
        doctorNameEdit.setFocusableInTouchMode(true);
        doctorSurnameEdit.setBackground(drawableEditTextStyle);
        doctorSurnameEdit.setFocusableInTouchMode(true);
        doctorSpecializationEdit.setBackground(drawableEditTextStyle);
        doctorSpecializationEdit.setFocusableInTouchMode(true);

        contactEmailEdit.setBackground(drawableEditTextStyle);
        contactEmailEdit.setFocusableInTouchMode(true);
        contactPhoneEdit.setBackground(drawableEditTextStyle);
        contactPhoneEdit.setFocusableInTouchMode(true);

        addressCityEdit.setBackground(drawableEditTextStyle);
        addressCityEdit.setFocusableInTouchMode(true);
        addressStreetEdit.setBackground(drawableEditTextStyle);
        addressStreetEdit.setFocusableInTouchMode(true);
        addressStreetNumberEdit.setBackground(drawableEditTextStyle);
        addressStreetNumberEdit.setFocusableInTouchMode(true);

        whMondayFrom.setBackground(drawableEditTextStyle);
        whMondayFrom.setEnabled(true);
        whMondayTo.setBackground(drawableEditTextStyle);
        whMondayTo.setEnabled(true);

        whTuesdayFrom.setBackground(drawableEditTextStyle);
        whTuesdayFrom.setEnabled(true);
        whTuesdayTo.setBackground(drawableEditTextStyle);
        whTuesdayTo.setEnabled(true);

        whWednesdayFrom.setBackground(drawableEditTextStyle);
        whWednesdayFrom.setEnabled(true);
        whWednesdayTo.setBackground(drawableEditTextStyle);
        whWednesdayTo.setEnabled(true);

        whThursdayFrom.setBackground(drawableEditTextStyle);
        whThursdayFrom.setEnabled(true);
        whThursdayTo.setBackground(drawableEditTextStyle);
        whThursdayTo.setEnabled(true);

        whFridayFrom.setBackground(drawableEditTextStyle);
        whFridayFrom.setEnabled(true);
        whFridayTo.setBackground(drawableEditTextStyle);
        whFridayTo.setEnabled(true);
    }


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
                        //Formátování času
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

    //Otevře výchozí aplikaci na volání s číslem doktora
    public void callDoctor(View view) {
        if(contactPhoneEdit.getText().toString().equals("0")){
            Toast.makeText(getApplicationContext(), "Není údaj o telefonu doktora", Toast.LENGTH_SHORT).show();
        } else {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + contactPhoneEdit.getText().toString()));
            startActivity(callIntent);
        }
    }

    //Otevře výchozí emailovou aplikaci s emailem doktora
    public void emailDoctor(View view) {
        if(contactEmailEdit.getText().toString().matches("")){
            Toast.makeText(getApplicationContext(), "Není údaj o emailu doktora", Toast.LENGTH_SHORT).show();
        } else {
            Intent mailtoIntent = new Intent(Intent.ACTION_SENDTO);
            mailtoIntent.setData(Uri.parse("mailto:" + contactEmailEdit.getText().toString()));
            startActivity(mailtoIntent);
        }

    }
}
