package com.ronald.medin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ronald.medin.Classes.Doctor;
import com.ronald.medin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronald on 06.02.2018.
 */

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    public DoctorAdapter(Context context, List<Doctor> doctors){
        super(context, 0, doctors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Doctor doctor = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_doctor, parent, false);
        }

        TextView doctorName = convertView.findViewById(R.id.item_doctorName);
        TextView doctorSurname = convertView.findViewById(R.id.item_doctorSurname);
        TextView doctorEmail = convertView.findViewById(R.id.item_doctorEmail);
        TextView doctorPhone = convertView.findViewById(R.id.item_doctorPhone);

        doctorName.setText(doctor.getName());//Comment
        doctorSurname.setText(doctor.getSurname());
        doctorEmail.setText(doctor.getEmail());
        doctorPhone.setText(String.valueOf(doctor.getTelephone()));
        return convertView;
    }
}
