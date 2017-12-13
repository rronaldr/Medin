package com.ronald.medin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;


public class TreatmentFragment extends Fragment {

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    Context context;

    public TreatmentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_treatment, container, false);

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

        return view;
    }

//    public void createAlarm(String message, int hour, int minutes) {
//        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
//                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
//                .putExtra(AlarmClock.EXTRA_HOUR, hour)
//                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
//        startActivity(intent);
//    }
}
