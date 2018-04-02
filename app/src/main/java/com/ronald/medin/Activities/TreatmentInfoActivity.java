package com.ronald.medin.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
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

import com.ronald.medin.Alarms.AlarmReceiver;
import com.ronald.medin.Classes.Treatment_info;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TreatmentInfoActivity extends AppCompatActivity {

    //Inicializace proměnných
    int treatmentId;
    EditText usageAmountEdit;
    EditText notifyAmountEdit;

    Button editTreatmentBtn;
    Button saveTreatmentBtn;
    Button deleteTreatmentBtn;

    Drawable drawableEditStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Přiřazení view
        EditText editTextStyle = findViewById(R.id.edit_treatmentInfo_style);
        drawableEditStyle = editTextStyle.getBackground();

        usageAmountEdit = findViewById(R.id.edit_treatmentInfo_usageAmount);
        notifyAmountEdit = findViewById(R.id.edit_treatmentInfo_notifyAmount);

        TextView medNameText = findViewById(R.id.text_treatmentInfo_medName);
        TextView typeText = findViewById(R.id.text_treatmentInfo_type);
        TextView dateStartedText = findViewById(R.id.text_treatmentInfo_dateStarted);

        editTreatmentBtn = findViewById(R.id.btn_treatmentInfo_edit);
        saveTreatmentBtn = findViewById(R.id.btn_treatmentInfo_save);
        deleteTreatmentBtn = findViewById(R.id.btn_treatmentInfo_delete);

        //Vybrání dat z intentu
        treatmentId = getIntent().getIntExtra("ItemID",0);

        //Nové spojení s db
        SQLite db = new SQLite(this);
        Cursor treatmentInfoCursor = db.getTreatmentInfoSelected(treatmentId);
        treatmentInfoCursor.moveToFirst();

        //Nastavení textu view
        usageAmountEdit.setText(treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_USAGE_AMOUNT)));
        notifyAmountEdit.setText(treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_AMOUNT_TO_NOTIFY)));
        typeText.setText(treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_USAGE_TYPE)));
        medNameText.setText(treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_NAME)));
        dateStartedText.setText(treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_DATE_STARTED)));

        treatmentInfoCursor.close();
    }

    /**
     * onClick tlačítka editace
     * @param view
     */
    public void editTreatment(View view) {
        makeViewEditable();
    }

    /**
     * onClick tlačítka uložení
     * @param view
     */
    public void saveTreatmentInfo(View view) {
        //Ověření vstupů
        if(!usageAmountEdit.getText().toString().matches("") && !notifyAmountEdit.getText().toString().matches("")){

            updateTreatment_info();

            //Zobrazení zprávy uživateli
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.treatmentUpdated), Toast.LENGTH_SHORT).show();

            //Ukončení activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    TreatmentInfoActivity.this.finish();
                }
            }, 2000);
        } else {
            //Zobrazení zprávy
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.fillAllFields), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * onClick tlačítka smazání
     * @param view
     */
    public void deleteTreatmentInfo(View view) {
        deleteTreatment();
    }

    //Udělá view editovatelnými
    public void makeViewEditable(){

        notifyAmountEdit.setBackground(drawableEditStyle);
        notifyAmountEdit.setFocusableInTouchMode(true);

        usageAmountEdit.setBackground(drawableEditStyle);
        usageAmountEdit.setFocusableInTouchMode(true);

        editTreatmentBtn.setVisibility(View.GONE);
        deleteTreatmentBtn.setVisibility(View.VISIBLE);
        saveTreatmentBtn.setVisibility(View.VISIBLE);
    }

    //Updatne léčbu v db
    public void updateTreatment_info(){
        SQLite db = new SQLite(this);

        Treatment_info ti = new Treatment_info();
        ti.setId(treatmentId);
        ti.setUsage_amount(Integer.parseInt(usageAmountEdit.getText().toString()));
        ti.setAmount_to_notify(Integer.parseInt(notifyAmountEdit.getText().toString()));

        db.updateTreatment_info(ti);
        db.close();
    }

    //Smaže léčbu a všechny údaje k ní
    public void deleteTreatment(){
        //Spojení s db
        SQLite db = new SQLite(this);
        Cursor deleteCursor = db.getTreatmentInfo(treatmentId);

        //Vymaže Treatment_info
        db.deleteTreatment_info(treatmentId);
        if (deleteCursor.moveToFirst()) {
            do {

                //Vymaže zbylé záznamy na základe treatmentId
                db.deleteTreatment_time(deleteCursor.getInt(deleteCursor.getColumnIndex(SQLite.TREATMENT_TIME_COLUMN_ID)));
                db.deleteTreatment_info_Treatment_time(deleteCursor.getInt(deleteCursor.getColumnIndex(SQLite.TREATMENT_INFO_TREATMENT_TIME_COLUMN_ID)));
                cancelAlarm(deleteCursor.getInt(deleteCursor.getColumnIndex(SQLite.TREATMENT_TIME_COLUMN_ID)));

            } while (deleteCursor.moveToNext());
        }

        //Zobrazení zprávy uživateli
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.treatmentDeleted), Toast.LENGTH_SHORT).show();

        //Ukončení activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TreatmentInfoActivity.this.finish();
            }
        }, 2000);
    }

    /**
     * Pošle na broadcast signál o provedení akce cancelAlarm
     * @param treatment_timeId id treatment_time podle, kterého se zruší alarm s tímto Id
     */
    public void cancelAlarm(int treatment_timeId){
        Intent intent= new Intent(this,AlarmReceiver.class);
        intent.setAction("cancelAlarm");
        intent.putExtra("treatment_timeId", treatment_timeId);
        this.sendBroadcast(intent);
    }


}
