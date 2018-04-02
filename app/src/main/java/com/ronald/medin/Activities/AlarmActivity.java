package com.ronald.medin.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ronald.medin.Alarms.AlarmReceiver;
import com.ronald.medin.Classes.Inventory;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class AlarmActivity extends AppCompatActivity {

    //Deklarování proměnných, používaných v metodách
    MediaPlayer player;
    TextView medNameText;
    String medPackageUnit;

    int treatment_infoId;
    int treatmentId;
    int inventoryId;
    int inventoryQuantity;
    int usageAmount;
    int notifyAmount;
    String dateStarted;
    int daysToUse;

    @Override
    //
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Přiřazení id layoutu k widgetům
        medNameText = findViewById(R.id.text_alarmActivity_medName);
        TextView usageAmountText = findViewById(R.id.text_alarmActivity_usageAmount);
        TextView noteText = findViewById(R.id.text_alarmActivity_note);

        treatment_infoId = 0;
        //Ověření zda intent obsahuje data
        if(getIntent().hasExtra("Treatment_infoId")){
            treatment_infoId = getIntent().getIntExtra("Treatment_infoId", 0);
        }

        //Nové připojení k databázi
        SQLite db = new SQLite(this);
        //Vybrání dat o léčbě z databáze
        Cursor treatmentInfoCursor = db.getTreatmentInfo(treatment_infoId);

        treatmentInfoCursor.moveToFirst();

        //Přiřazení hodnot k proměnným
        dateStarted = treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_DATE_STARTED));
        treatmentId = treatmentInfoCursor.getInt(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_TIME_COLUMN_ID));
        inventoryId = treatmentInfoCursor.getInt(treatmentInfoCursor.getColumnIndex(SQLite.INVENTORY_COLUMN_ID));
        inventoryQuantity = treatmentInfoCursor.getInt(treatmentInfoCursor.getColumnIndex(SQLite.INVENTORY_COLUMN_QUANTITY));
        usageAmount = treatmentInfoCursor.getInt(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_USAGE_AMOUNT));
        notifyAmount = treatmentInfoCursor.getInt(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_AMOUNT_TO_NOTIFY));
        daysToUse = treatmentInfoCursor.getInt(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_DAYS_TO_USE));

        medPackageUnit = treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_PACKAGE_UNIT));

        medNameText.setText(treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_NAME)));
        usageAmountText.setText(treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_USAGE_AMOUNT)) + " " + treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_PACKAGE_UNIT)));
        noteText.setText(treatmentInfoCursor.getString(treatmentInfoCursor.getColumnIndex(SQLite.TREATMENT_INFO_COLUMN_NOTE)));

        //Uzavření cursoru a spojení databáze
        treatmentInfoCursor.close();
        db.close();
    }

    //
    @Override
    protected void onStart() {
        super.onStart();

        playRingtone();
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopRingtone();
    }

    //Nastaví vlastnosti activity, aby se zobrazila i když je obrazovka vypnutá
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        Window w = getWindow();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }


    //onClick akce po stisknutí buttonu zamítnutí léčby
    public void DismissAlarm(View view) {

        this.finish();
    }

    //onClick akce po stisknutí buttonu přijmutí léčby
    public void AcceptAlarm(View view) {
        //Pokud je v počet kusů v inventáři 0, tak neodečítej
        if(inventoryQuantity < usageAmount){
            inventoryQuantity = 0;
        } else {
            inventoryQuantity = inventoryQuantity - usageAmount;
        }

        cancelAlarmIfDateExpired();

        if(inventoryQuantity <= notifyAmount){
            final Intent notIntent = new Intent();
            final PendingIntent pendingIntent = PendingIntent.getActivity(this,1999,notIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notification2 = new NotificationCompat.Builder(this);
            notification2.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getResources().getString(R.string.pillsOutOfStock))
                    .setContentText(getResources().getString(R.string.lastRemaining) +" "+ String.valueOf(inventoryQuantity)+" "+ medPackageUnit +" " + medNameText.getText())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

            NotificationManager ntfManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            ntfManager.notify(treatment_infoId,notification2.build());
        }

        Inventory i = new Inventory();
        i.setId(inventoryId);
        i.setQuantity(inventoryQuantity);
        SQLite db = new SQLite(this);
        db.updateInventory(i);

        finish();
    }


    //Spustí MediaPlayer s vyzváněcí znělkou ze složky assets
    public void playRingtone(){
        AssetFileDescriptor afd = null;
        try {
            player = new MediaPlayer();
            if (!player.isPlaying()){

                afd = getAssets().openFd("ringtone.mp3");
                player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                player.prepare();
                player.setLooping(true);
                player.start();
            } else {
                player.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    public void stopRingtone(){
        if(player.isPlaying()){
            player.pause();
            player.release();
        }
    }

    //Procedura pro zrušení všech AlarmManageru, pokud skončila délka léčby
    public void cancelAlarmIfDateExpired(){
        Log.e("DAYS", String.valueOf(daysToUse));
        if(daysToUse > 0) {
            try {
                //
                String datetimeStarted = dateStarted;
                String date[] = datetimeStarted.split(" ");

                //Uložení aktuáního času
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                String curDate = formatter.format(currentTime);

                Date date1 = formatter.parse(date[0]);

                //Přidání délky léčby k datu započatí léčby
                Calendar startedCal = Calendar.getInstance();
                startedCal.setTime(date1);
                startedCal.add(Calendar.DATE, daysToUse);
                String dateStartedplus = formatter.format(startedCal.getTime());

                //Pokud je aktuální datum větší něž datum konce léčby, tak zruší Alarmy a odešle notifikaci
                if (curDate.compareTo(dateStartedplus) > 0) {
                    //Vybere z databáze všechny léčby a jejich časy
                    SQLite db = new SQLite(this);
                    Cursor cancelAlarmCursor = db.getTreatmentInfo(treatmentId);
                    if (cancelAlarmCursor.moveToFirst()) {
                        do {
                            //Pro každý Treatment_time provede proceduru cancelAlarm s id Treatment_time
                            cancelAlarm(cancelAlarmCursor.getInt(cancelAlarmCursor.getColumnIndex(SQLite.TREATMENT_TIME_COLUMN_ID)));
                        } while (cancelAlarmCursor.moveToNext());
                    }

                    final Intent notIntent = new Intent();
                    final PendingIntent pendingIntent = PendingIntent.getActivity(this, 2000, notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    //Vytvoření notifikace se zprávou o konci léčby
                    NotificationCompat.Builder notification2 = new NotificationCompat.Builder(this);
                    notification2.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getResources().getString(R.string.endOfTreatment))
                            .setContentText(getResources().getString(R.string.treatment) + " " + medNameText.getText() + " " + getResources().getString(R.string.ended))
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setContentIntent(pendingIntent);
                    NotificationManager ntfManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                    ntfManager.notify(treatment_infoId, notification2.build());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
