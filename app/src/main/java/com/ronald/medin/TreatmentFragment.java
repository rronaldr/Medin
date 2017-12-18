package com.ronald.medin;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
                notification.setSmallIcon(R.mipmap.ic_launcher_round)
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

//        Context context = getActivity().getApplicationContext();
//        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, Alar);

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 16);
//        calendar.set(Calendar.MINUTE, 6);
//
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 20, alarmIntent);

//        Button alarmBtn = (Button) view.findViewById(R.id.treatmentButton);
//        alarmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createAlarm("Nastaven budík", 16,37);
//            }
//        });

        return v;
    }

//    public void createAlarm(String message, int hour, int minutes) {
//        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
//                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
//                .putExtra(AlarmClock.EXTRA_HOUR, hour)
//                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
//        startActivity(intent);
//    }
}
