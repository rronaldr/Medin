package com.ronald.medin.Activities;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ronald.medin.Fragments.TreatmentFragment;
import com.ronald.medin.R;

public class TreatmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_result);


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
