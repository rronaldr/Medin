package com.ronald.medin.Fragments;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ronald.medin.Activities.Alarm;
import com.ronald.medin.Alarms.AlarmReceiver;
import com.ronald.medin.R;
import com.ronald.medin.Activities.TreatmentActivity;

import java.util.Calendar;


public class TreatmentFragment extends Fragment {

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    public static int notificationId = 1;

    public TreatmentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_treatment, container, false);

        final Context context = getActivity().getApplicationContext();
        final Intent notIntent = new Intent();
        final PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        final Intent intentTaken = new Intent(context,TreatmentActivity.class);
        intentTaken.putExtra("TAKEN","-(x) z inventáře");
        final PendingIntent takenPending = PendingIntent.getActivity(context,1,intentTaken,PendingIntent.FLAG_UPDATE_CURRENT);

        final Intent intentNotTaken = new Intent(context,TreatmentActivity.class);
        intentNotTaken.putExtra("TAKEN","Léčba odložena");
        final PendingIntent notTakenPending = PendingIntent.getActivity(context,2,intentNotTaken,PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Action take = new NotificationCompat.Action(0, "Vzít", takenPending);
        final NotificationCompat.Action delay = new NotificationCompat.Action(0, "Odložit", notTakenPending);

        v.findViewById(R.id.treatmentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
                notification.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Vzít si léky")
                        .setContentText("Paralen 400mg")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Vzít si Paralen 400mg (2 tablety)"))
                        .setContentIntent(pendingIntent)
                        .addAction(take)
                        .addAction(delay)
                        .setAutoCancel(true);

                NotificationManager ntfManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                ntfManager.notify(notificationId,notification.build());
            }
        });

        v.findViewById(R.id.treatmentAlarmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar offsetTime = Calendar.getInstance();
                offsetTime.add(Calendar.SECOND, 5);

                Log.e("Now", Calendar.getInstance().getTime().toString());
                Log.e("Next", offsetTime.getTime().toString());

                Intent goToAlarm = new Intent(context, Alarm.class);
                goToAlarm.putExtra("Msg", "Zprava 1");
                PendingIntent pendingIntentAlarm = PendingIntent.getActivity(context, 3, goToAlarm, 0);
                AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                am.setExact(AlarmManager.RTC_WAKEUP, offsetTime.getTimeInMillis(), pendingIntentAlarm);

                Toast.makeText(context, "Alarm set", Toast.LENGTH_SHORT);

            }
        });

        return v;
    }

}
