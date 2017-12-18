package com.ronald.medin;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TreatmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_result);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String x;
        TextView displMsg = (TextView) this.findViewById(R.id.msgTextView);
        Bundle sentBundle = getIntent().getExtras();
        if(sentBundle != null){
            x = sentBundle.getString("TAKEN");
            displMsg.setText(x);

        }


        NotificationManager ntfManager = (NotificationManager) getApplication().getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        ntfManager.cancel(TreatmentFragment.notificationId);
    }

}
