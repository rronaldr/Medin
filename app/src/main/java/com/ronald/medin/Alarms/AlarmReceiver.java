package com.ronald.medin.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ronald.medin.Activities.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void SetAlarm(Context context,long alarmTime){
        Intent goToAlarm = new Intent(context, AlarmActivity.class);
        goToAlarm.putExtra("Msg", "Zprava 1");
        PendingIntent pendingIntentAlarm = PendingIntent.getActivity(context, 3, goToAlarm, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntentAlarm);
    }
}
