package com.ronald.medin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ronald.medin.Classes.Doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronald on 06.02.2018.
 */

public class SQLite extends SQLiteOpenHelper {

    private static final String DATABSE_NAME = "SQLiteDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public SQLite(Context context){
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    //Doctor table names
    public static final String DOCTOR_TABLE_NAME = "Doctor";
    public static final String DOCTOR_COLUMN_ID = "id";
    public static final String DOCTOR_COLUMN_NAME = "name";
    public static final String DOCTOR_COLUMN_SURNAME = "surname";
    public static final String DOCTOR_COLUMN_EMAIL = "email";
    public static final String DOCTOR_COLUMN_TELEPHONE = "telephone";

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DOCTOR_TABLE = "CREATE TABLE " + DOCTOR_TABLE_NAME + "(" + DOCTOR_COLUMN_ID + " INTEGER PRIMARY KEY, "
                + DOCTOR_COLUMN_NAME + " TEXT, " + DOCTOR_COLUMN_SURNAME + " TEXT, " + DOCTOR_COLUMN_EMAIL + " TEXT, "
                + DOCTOR_COLUMN_TELEPHONE + " INTEGER" + ")";
        db.execSQL(CREATE_DOCTOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DOCTOR_TABLE_NAME);

        onCreate(db);
    }

    public void insertDoctor(Doctor d){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DOCTOR_COLUMN_NAME, d.getName());
        values.put(DOCTOR_COLUMN_SURNAME, d.getSurname());
        values.put(DOCTOR_COLUMN_EMAIL, d.getEmail());
        values.put(DOCTOR_COLUMN_TELEPHONE, d.getTelephone());

        db.insert(DOCTOR_TABLE_NAME,null, values);
        db.close();
    }

    public int updateDoctor(Doctor d){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DOCTOR_COLUMN_NAME, d.getName());
        values.put(DOCTOR_COLUMN_SURNAME, d.getSurname());
        values.put(DOCTOR_COLUMN_EMAIL, d.getEmail());
        values.put(DOCTOR_COLUMN_TELEPHONE, d.getTelephone());

        return db.update(DOCTOR_TABLE_NAME, values, DOCTOR_COLUMN_ID + "= ?", new String[]{ String.valueOf(d.getId()) });

    }

    public void deleteDoctor(Doctor d){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DOCTOR_TABLE_NAME, DOCTOR_COLUMN_ID + "= ?", new String[] { String.valueOf(d.getId()) });
    }

    public Doctor getDoctor(Doctor d){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DOCTOR_TABLE_NAME, new String[] {DOCTOR_COLUMN_ID, DOCTOR_COLUMN_NAME, DOCTOR_COLUMN_SURNAME, DOCTOR_COLUMN_EMAIL,
                DOCTOR_COLUMN_TELEPHONE}, DOCTOR_COLUMN_ID + "= ?", new String[] {String.valueOf(d.getId())},
                null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        Doctor doctor = new Doctor(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));
        return doctor;
    }

    public List<Doctor> getAllDoctors(){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + DOCTOR_TABLE_NAME;

        List<Doctor> doctorList = new ArrayList<Doctor>();

        Cursor cursor = db.rawQuery(select, null);
        if(cursor.moveToFirst()){
            do{
                Doctor doctor = new Doctor(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));
                doctorList.add(doctor);
            } while (cursor.moveToNext());
        }
        return doctorList;
    }
}
