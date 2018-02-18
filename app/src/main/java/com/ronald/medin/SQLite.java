package com.ronald.medin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ronald.medin.Classes.Doctor;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {

    private static final String DATABSE_NAME = "SQLiteDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public SQLite(Context context){
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    //Doctor table names
    public static final String DOCTOR_TABLE_NAME = "Doctor";
    public static final String DOCTOR_COLUMN_ID = "_id";
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

    public boolean insertDoctor(Doctor d){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DOCTOR_COLUMN_NAME, d.getName());
        values.put(DOCTOR_COLUMN_SURNAME, d.getSurname());
        values.put(DOCTOR_COLUMN_EMAIL, d.getEmail());
        values.put(DOCTOR_COLUMN_TELEPHONE, d.getTelephone());

        db.insert(DOCTOR_TABLE_NAME,null, values);

        return true;
    }

    public boolean updateDoctor(Doctor d){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DOCTOR_COLUMN_NAME, d.getName());
        values.put(DOCTOR_COLUMN_SURNAME, d.getSurname());
        values.put(DOCTOR_COLUMN_EMAIL, d.getEmail());
        values.put(DOCTOR_COLUMN_TELEPHONE, d.getTelephone());

        db.update(DOCTOR_TABLE_NAME, values, DOCTOR_COLUMN_ID + "= ?", new String[]{ String.valueOf(d.getId()) });

        return true;

    }

    public Cursor getDoctor(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " + DOCTOR_TABLE_NAME + " WHERE " +
                DOCTOR_COLUMN_ID + " =?", new String[] { Integer.toString(id) });
        return query;
    }

    public Cursor getAllDoctors(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " + DOCTOR_TABLE_NAME, null);

        return query;
    }

    public int deleteDoctor(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DOCTOR_TABLE_NAME, DOCTOR_COLUMN_ID + " = ? ", new String[] {Integer.toString(id)});
    }
}
