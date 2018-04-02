package com.ronald.medin.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.ronald.medin.Activities.AlarmActivity;
import com.ronald.medin.Classes.Treatment_time;
import com.ronald.medin.SQLite;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int treatment_infoId = 0;
        //Vybrání hodnoty z intentu
        if(intent.hasExtra("treatment_infoId")){
            treatment_infoId = intent.getIntExtra("treatment_infoId", 0);
        }

        //Pokud se intent akce rovná newAlarm
        if(intent.getAction().equals("newAlarm")){
            //Nové spojení s db
            SQLite db = new SQLite(context);
            Cursor dump = db.getTreatmentInfo(treatment_infoId);
            int treatment_timeId = 0;
            if (dump.moveToFirst()) {
                do {
                    int hour = dump.getInt(dump.getColumnIndex(SQLite.TREATMENT_TIME_COLUMN_HOUR));
                    int minute = dump.getInt(dump.getColumnIndex(SQLite.TREATMENT_TIME_COLUMN_MINUTE));

                    treatment_timeId = dump.getInt(dump.getColumnIndex(SQLite.TREATMENT_TIME_COLUMN_ID));

                    Calendar cur_cal = new GregorianCalendar();
                    cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

                    //Setne kalendář podlé údajů z databáze
                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
                    cal.set(Calendar.HOUR_OF_DAY, hour);
                    cal.set(Calendar.MINUTE, minute);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
                    cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
                    cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

                    //Navigace na AlarmActivity
                    Intent goToAlarm = new Intent(context, AlarmActivity.class);
                    goToAlarm.putExtra("Treatment_infoId", treatment_infoId);

                    //Nová PendingIntent s id treatment_timeId, aby byl každý unikátní
                    PendingIntent pendingIntentAlarm = PendingIntent.getActivity(context, treatment_timeId, goToAlarm, 0);
                    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    //Vytvoří alarm v uřivateli nastaveném čase a nastaví opakování na každý den
                    am.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentAlarm);

                    cal.clear();
                } while (dump.moveToNext());
            }
            //Pokud je akce intentu cancelAlarm
        } else if (intent.getAction().equals("cancelAlarm")){
            int treatment_timeId = 0;
            if(intent.hasExtra("treatment_timeId")){
                treatment_timeId = intent.getIntExtra("treatment_timeId", 0);
            }

            //Vytvoří stejné intenty jako, když se alarm setoval
            Intent goToAlarm = new Intent(context, AlarmActivity.class);
            goToAlarm.putExtra("Treatment_infoId", treatment_infoId);

            //Vytvoří PendingIntent se stejným id jako, když byl alarm vkládán
            PendingIntent pendingIntentAlarm = PendingIntent.getActivity(context, treatment_timeId, goToAlarm, 0);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            //Zruší alarm pro daný PendingIntent
            am.cancel(pendingIntentAlarm);
        }
    }
}
