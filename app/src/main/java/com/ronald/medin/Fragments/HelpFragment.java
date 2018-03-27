package com.ronald.medin.Fragments;


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

import com.ronald.medin.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);

        final Context context = getActivity().getApplicationContext();
        final Intent notIntent = new Intent();
        final PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        v.findViewById(R.id.notificationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder notification2 = new NotificationCompat.Builder(context);
                notification2.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Upozornění")
                        .setContentText("Zbývá posledních (5 tablet) Paralen 400")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent);


                NotificationManager ntfManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                ntfManager.notify(2,notification2.build());


            }
        });

        return v;
    }

}
