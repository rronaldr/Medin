package com.ronald.medin.Activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ronald.medin.Alarms.AlarmReceiver;
import com.ronald.medin.Classes.Inventory;
import com.ronald.medin.Classes.Treatment_info;
import com.ronald.medin.Classes.Treatment_info_Treatment;
import com.ronald.medin.Classes.Treatment_info_Treatment_time;
import com.ronald.medin.Classes.Treatment_time;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InsertTreatmentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //Inicializace proměnných
    Cursor medicineCursor;

    List<String> medNames;
    List<EditText> editTextList = new ArrayList<EditText>();

    int medId;
    int dayCount;
    int inventoryId;

    EditText dayCountEdit;
    EditText medInventoryAmountEdit;
    EditText repeatAlarmValueEdit;
    EditText startTimeEdit;
    EditText endTimeEdit;
    EditText usageAmountEdit;
    EditText remindValueEdit;
    EditText noteEdit;

    Spinner medNameSpinner;
    Spinner modeSpinner;
    Spinner frequencySpinner;

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_treatment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Přiřazení view
        dayCountEdit = findViewById(R.id.edit_treatmentInsert_dayCount);
        medInventoryAmountEdit = findViewById(R.id.edit_treatmentInsert_inventoryAmount);
        repeatAlarmValueEdit = findViewById(R.id.edit_treatmentInsert_repeatAlarmValue);
        noteEdit = findViewById(R.id.edit_treatmentInsert_note);
        usageAmountEdit = findViewById(R.id.edit_treatmentInsert_quantity);
        remindValueEdit = findViewById(R.id.edit_treatmentInsert_reminderValue);

        //Přiřazení view a onClickListeneru
        repeatAlarmValueEdit.setOnClickListener(this);
        startTimeEdit = findViewById(R.id.edit_treatmentInsert_startTime);
        startTimeEdit.setOnClickListener(this);
        endTimeEdit = findViewById(R.id.edit_treatmentInsert_endTime);
        endTimeEdit.setOnClickListener(this);


        medNameSpinner = findViewById(R.id.spinner_treatmentInsert_medicine);
        medNameSpinner.setOnItemSelectedListener(this);
        modeSpinner = findViewById(R.id.spinner_treatmentInsert_mode);
        modeSpinner.setOnItemSelectedListener(this);
        frequencySpinner = findViewById(R.id.spinner_treatmentInsert_frequency);
        frequencySpinner.setOnItemSelectedListener(this);

        //Disablenutí spinneru
        frequencySpinner.setEnabled(false);

        saveButton = findViewById(R.id.btn_treatmentInsert_save);

        //Nové spojení s DB
        SQLite db = new SQLite(this);
        medicineCursor = db.getAllMedicine();
        populateSpinner(medicineCursor);

    }


    /**
     * onClick na tlačítko uložit
     * @param view
     */
    public void saveTreatment(View view) {
        SQLite db = new SQLite(this);

        boolean dayCountSet = false;
        String usageType;
        //Přiřazení hodnoty na základě pozice spinneru
        if(modeSpinner.getSelectedItemPosition() == 0){
            usageType = modeSpinner.getSelectedItem().toString();
            if (!dayCountEdit.getText().toString().matches("")){
                dayCount = Integer.parseInt(dayCountEdit.getText().toString());
                dayCountSet = true;
            } else {
                Toast.makeText(this,getResources().getString(R.string.fillTreatmentTime), Toast.LENGTH_SHORT).show();
            }
        } else {
            usageType = modeSpinner.getSelectedItem().toString();
            dayCount = -1;
            dayCountSet = true;
        }

        boolean usageAmountSet = false;
        boolean amountToNotify = false;

        //Ověření vstupů
        if(!usageAmountEdit.getText().toString().matches("")){
            usageAmountSet = true;
        } else {
            Toast.makeText(this,getResources().getString(R.string.fillTreatmentAmount), Toast.LENGTH_SHORT).show();
        }
        if(!remindValueEdit.getText().toString().matches("")){
            amountToNotify = true;
        } else {
            Toast.makeText(this,getResources().getString(R.string.fillTreatmentNotify), Toast.LENGTH_SHORT).show();
        }

        //Podmínka pokud vstupy nejsou prázné
        if(usageAmountSet && amountToNotify && dayCountSet){

            //Instance objektu Inventory
            Inventory i = new Inventory();
            i.setQuantity(Integer.parseInt(medInventoryAmountEdit.getText().toString()));

            //Vložení inventáře do db
            inventoryId = (int) db.insertInventory(i);

            //Instance objektu Treatment_info
            Treatment_info ti = new Treatment_info();
            if (!noteEdit.getText().toString().matches("")){
                ti.setNote(noteEdit.getText().toString());
            }
            //Setnutí dat instanci
            ti.setDays_to_use(dayCount);
            ti.setMedicineId(medId);
            ti.setInventoryId(inventoryId);
            ti.setUsage_type(usageType);
            ti.setUsage_amount(Integer.parseInt(usageAmountEdit.getText().toString()));
            ti.setAmount_to_notify(Integer.parseInt(remindValueEdit.getText().toString()));


            long treatment_infoId = db.insertTreatment_info(ti);

            //Pro všechny vygenerované EditTexty
            for (EditText editText : editTextList) {
                if(!editText.getText().toString().matches("")){
                    //Rozdělení hodnoty
                    String[] time = editText.getText().toString().split(":");

                    int hour = Integer.parseInt(time[0]);
                    int minute = Integer.parseInt(time[1]);

                    //Instance objektu Treatment_time
                    Treatment_time tt = new Treatment_time();
                    //Settnutí hodnot
                    tt.setHour(hour);
                    tt.setMinute(minute);

                    //Vložení Treatment_time do db
                    long treatment_timeId = db.insertTreatment_time(tt);

                    //Instance objektu Treatment_info_Treatment_time
                    Treatment_info_Treatment_time titt = new Treatment_info_Treatment_time();
                    //Settnutí hodnot
                    titt.setTreatment_infoId((int) treatment_infoId);
                    titt.setTreatment_timeId((int) treatment_timeId);
                    //Vložení do db
                    db.insertTreatment_info_Treatment_time(titt);
                }
            }

            //Vytvoří nový intent s akcí newAlarm a pošle broadcast s tímto intentem
            Intent intent= new Intent(this,AlarmReceiver.class);
            intent.setAction("newAlarm");
            intent.putExtra("treatment_infoId",(int) treatment_infoId);
            this.sendBroadcast(intent);

            //Zobrazení zprávy uživateli
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.treatmentSaved), Toast.LENGTH_SHORT).show();

            //Ukončení activity po 2 s
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InsertTreatmentActivity.this.finish();
                }
            }, 2000);
        }
    }

    /**
     * onClick pro view
     * @param view view na které bylo kliknuto
     */
    @Override
    public void onClick(View view) {
        //Podle toho o jaké view se jedná ukáze NumberPicker nebo TimePicker
        if(view == repeatAlarmValueEdit){
            showNumPicker(view.getId());
        } else {
            showTimePicker(view.getId());
        }
    }

    /**
     * OnItemSelectedListener pro spinnery
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Switch podle toho o jaký spinner jde
        switch (adapterView.getId()){
            case R.id.spinner_treatmentInsert_mode:
                if(modeSpinner.getSelectedItemPosition() == 0){
                    dayCountEdit.setVisibility(View.VISIBLE);
                } else {
                    dayCountEdit.setVisibility(View.GONE);
                }
                break;
            case R.id.spinner_treatmentInsert_medicine:
                if (medicineCursor.moveToFirst()){
                    do{
                        if(medNameSpinner.getSelectedItem().toString().equals(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_NAME)))){
                            medInventoryAmountEdit.setText(medicineCursor.getString(medicineCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_PACKAGE_QUANTITY)));
                            medId = medicineCursor.getInt(medicineCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_ID));
                        }
                    }while(medicineCursor.moveToNext());
                }
                break;
            case R.id.spinner_treatmentInsert_frequency:
                if (frequencySpinner.getSelectedItemPosition() == 0){
                    startTimeEdit.setVisibility(View.GONE);
                    endTimeEdit.setVisibility(View.GONE);

                } else {
                    startTimeEdit.setVisibility(View.VISIBLE);
                    endTimeEdit.setVisibility(View.VISIBLE);

                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Ukáže TimePicker dialog
     * @param viewId na jaké view ho má uložit
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
                        if (hourOfDay < 10){
                            hourString = "0" + hourOfDay;
                        }else{
                            hourString = "" +hourOfDay;
                        }

                        String minuteSting;
                        if (minute < 10){
                            minuteSting = "0" + minute;
                        } else {
                            minuteSting = "" + minute;
                        }
                        //Ověření vstupů
                        if(editText.getId() == endTimeEdit.getId()){
                            if(!startTimeEdit.getText().toString().matches("")){
                                String[] time = startTimeEdit.getText().toString().split(":");

                                Calendar checkCalendar = Calendar.getInstance();
                                checkCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
                                int startHour = checkCalendar.get(Calendar.HOUR_OF_DAY);
                                if(startHour >= Integer.parseInt(hourString)){
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.treatmentInputErrorStartTime), Toast.LENGTH_SHORT).show();
                                } else {
                                    editText.setText(hourString + ":" + minuteSting);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.treatmentInputErrorStartTime2), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            editText.setText(hourString + ":" + minuteSting);
                        }


                    }
                }, Hour, Minute, true);
        timePickerDialog.show();
    }

    /**
     * Zobrazení NumberPicker dialogu
     * @param viewId view do kterého se má hodnota uložit
     */
    public void showNumPicker(final int viewId){
        final NumberPicker picker = new NumberPicker(this);
        picker.setMinValue(1);
        picker.setMaxValue(12);

        final EditText editText = findViewById(viewId);

        final FrameLayout layout = new FrameLayout(this);
        layout.addView(picker, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        new AlertDialog.Builder(this)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(frequencySpinner.getSelectedItemPosition() == 1){
                            if(startTimeEdit.getText().toString().matches("") || endTimeEdit.getText().toString().matches("")){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.treatmentNumberPickerError), Toast.LENGTH_SHORT).show();
                            } else {
                                editText.setText(String.valueOf(picker.getValue()));
                            }
                        } else {
                            editText.setText(String.valueOf(picker.getValue()));
                        }
                        generateHourEditText(picker.getValue());
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    /**
     * Vloží do spinneru názvy jednotlivých prášků
     * @param medicine cursor, do kterého jsou uloženu názvy
     */
    public void populateSpinner(Cursor medicine){
        medNames = new ArrayList<>();
        if (medicine.moveToFirst()){
            do{
                medNames.add(medicine.getString(medicine.getColumnIndex(SQLite.MEDICINE_COLUMN_NAME)));
            }while(medicine.moveToNext());
        }

        //Vytvoření adapteru pro spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, medNames);

        //Vybrání layoutu pro zobrazení dat
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Bložení dat do spinneru
        medNameSpinner.setAdapter(dataAdapter);
    }

    /**
     * Vygeneruje edittexty
     * @param numberOfEdit počet, kolik edittextů má být vygenerováno
     */
    public void generateHourEditText(int numberOfEdit){
        RelativeLayout relativeLayout = findViewById(R.id.layout_insertTreatment_relative);

        if(editTextList.size() > 0){
            editTextList.clear();
            relativeLayout.removeAllViews();
        }
        for(int i = 1; i <= numberOfEdit; i++){

            //Vytvoření nového EditText
            EditText newEditText = new EditText(this);
            //Nastavení atrivutů
            newEditText.setId(i);
            newEditText.setHint(getResources().getString(R.string.time));
            newEditText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
            newEditText.setOnClickListener(this);
            newEditText.setFocusableInTouchMode(false);

            //Vybrání hodnot z layoutu
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            //Vložení view do layoutu
            if(i == 1){
                relativeLayout.addView(newEditText, params);
            } else {
                int prevId = (newEditText.getId()) - 1;
                params.addRule(RelativeLayout.BELOW, prevId);
                relativeLayout.addView(newEditText, params);
            }
            //Vložení do kolekce
            editTextList.add(newEditText);
        }
    }
}
