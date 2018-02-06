package com.ronald.medin.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ronald.medin.Classes.Doctor;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class InsertDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_doctor);

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
            errorLabel.setText("Vyplňte všechna pole");
        } else {
            Doctor newDoctor = new Doctor(nameInput.getText().toString() ,surnameInput.getText().toString(), emailInput.getText().toString(),Integer.parseInt(phoneInput.getText().toString()));
            db.insertDoctor(newDoctor);
            errorLabel.setText("Lékař vložen úspěšně");
        }
    }
}
