package com.ronald.medin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ronald.medin.Classes.Doctor;
import com.ronald.medin.Fragments.DoctorFragment;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class InsertEditDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView errorLabel = this.findViewById(R.id.errorLabelInsertDoctor);
    }

    public void InsertDoctor(View view) {
        SQLite db = new SQLite(this);

        EditText nameInput = this.findViewById(R.id.textName);
        EditText surnameInput = this.findViewById(R.id.textSurname);
        EditText emailInput = this.findViewById(R.id.textEmail);
        EditText phoneInput = this.findViewById(R.id.textPhone);
        TextView errorLabel = this.findViewById(R.id.errorLabelInsertDoctor);

        if(nameInput.getText().toString().matches("") || surnameInput.getText().toString().matches("") ||
                emailInput.getText().toString().matches("") || phoneInput.getText().toString().matches("")){
            Toast.makeText(getApplicationContext(), "Vyplňte všechna pole", Toast.LENGTH_SHORT).show();
        } else {
            Doctor newDoctor = new Doctor(nameInput.getText().toString() ,surnameInput.getText().toString(), emailInput.getText().toString(),Integer.parseInt(phoneInput.getText().toString()));
            if(db.insertDoctor(newDoctor)) {
                Toast.makeText(getApplicationContext(), "Vložen úspěšně", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InsertEditDoctorActivity.this.finish();
                    }
                }, 2000);
            }

//                final Intent navigate = new Intent(this, DoctorFragment.class);
//                navigate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(navigate);

        }
    }
}
