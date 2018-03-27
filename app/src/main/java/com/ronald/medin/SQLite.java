package com.ronald.medin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.ronald.medin.Classes.Address;
import com.ronald.medin.Classes.Contact;
import com.ronald.medin.Classes.Doctor;
import com.ronald.medin.Classes.Measurement;
import com.ronald.medin.Classes.Working_hours;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Ronald on 27.03.2018.
 */

public class SQLite extends SQLiteOpenHelper {
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    //The Android's default system path of your application database.
    public static String DB_NAME = "medin.db";

    //Doctor table names
    public static final String DOCTOR_TABLE_NAME = "Doctor";
    public static final String DOCTOR_COLUMN_ID = "_id";
    public static final String DOCTOR_COLUMN_NAME = "name";
    public static final String DOCTOR_COLUMN_SURNAME = "surname";
    public static final String DOCTOR_COLUMN_SPECIALIZATION = "specialization";

    //Contact table names
    public static final String CONTACT_TABLE_NAME = "Contact";
    public static final String CONTACT_COLUMN_ID = "_id";
    public static final String CONTACT_COLUMN_EMAIL = "email";
    public static final String CONTACT_COLUMN_TELEPHONE = "telephone";

    //Address table names
    public static final String ADDRESS_TABLE_NAME = "Address";
    public static final String ADDRESS_COLUMN_ID = "_id";
    public static final String ADDRESS_COLUMN_CITY = "city";
    public static final String ADDRESS_COLUMN_STREET = "street";
    public static final String ADDRESS_COLUMN_STREET_NUMBER = "street_number";

    //Working hours table names
    public static final String WORKING_HOURS_TABLE_NAME = "Working_hours";
    public static final String WORKING_HOURS_COLUMN_ID = "_id";
    public static final String WORKING_HOURS_COLUMN_DAY = "day";
    public static final String WORKING_HOURS_COLUMN_FROM = "fromwh";
    public static final String WORKING_HOURS_COLUMN_TO = "towh";

    //Doctor_Contact
    public static final String DOCTOR_CONTACT_TABLE_NAME = "Doctor_Contact";
    public static final String DOCTOR_CONTACT_COLUMN_ID = "_id";
    public static final String DOCTOR_CONTACT_COLUMN_DOCTOR_ID = "doctor_id";
    public static final String DOCTOR_CONTACT_COLUMN_CONTACT_ID = "contact_id";

    //Doctor_Address
    public static final String DOCTOR_ADDRESS_TABLE_NAME = "Doctor_Address";
    public static final String DOCTOR_ADDRESS_COLUMN_ID = "_id";
    public static final String DOCTOR_ADDRESS_COLUMN_DOCTOR_ID = "doctor_id";
    public static final String DOCTOR_ADDRESS_COLUMN_ADDRESS_ID = "address_id";

    //Doctor_Working_hours
    public static final String DOCTOR_WORKING_HOURS_TABLE_NAME = "Doctor_Working_hours";
    public static final String DOCTOR_WORKING_HOURS_COLUMN_ID = "_id";
    public static final String DOCTOR_WORKING_HOURS_COLUMN_DOCTOR_ID = "doctor_id";
    public static final String DOCTOR_WORKING_HOURS_COLUMN_WORKING_HOURS_ID = "working_hours_id";


    //Measurement table names
    public static final String MEASUREMENT_TABLE_NAME = "Measurement";
    public static final String MEASUREMENT_COLUMN_ID = "_id";
    public static final String MEASUREMENT_COLUMN_NAME = "name";
    public static final String MEASUREMENT_COLUMN_VALUE = "value";
    public static final String MEASUREMENT_COLUMN_UNIT = "unit";
    public static final String MEASUREMENT_COLUMN_CREATED = "dateCreated";

    //Medicine table names
    public static final String MEDICINE_TABLE_NAME = "Medicine";
    public static final String MEDICINE_COLUMN_ID = "_id";
    public static final String MEDICINE_COLUMN_NAME = "name";
    public static final String MEDICINE_COLUMN_TYPE = "type";
    public static final String MEDICINE_COLUMN_PACKAGE_QUANTITY = "package_quantity";

    //Medicine_info table names
    public static final String MEDICINE_INFO_TABLE_NAME = "Medicine_info";
    public static final String MEDICINE_INFO_COLUMN_ID = "_id";
    public static final String MEDICINE_INFO_COLUMN_DESCRIPTION = "description";
    public static final String MEDICINE_INFO_COLUMN_WARNING = "warning";
    public static final String MEDICINE_INFO_COLUMN_USAGE_INSTRUCTIONS = "usage_instructions";
    public static final String MEDICINE_INFO_COLUMN_SIDE_EFFECTS = "side_effects";
    public static final String MEDICINE_INFO_COLUMN_STORAGE_INSTRUCTIONS = "storage_instructions";
    public static final String MEDICINE_INFO_COLUMN_OTHER_INFORMATION = "other_information";

    //Medicine_Medicine_info table names
    public static final String MEDICINE_MEDICINE_INFO_TABLE_NAME = "Medicine_Medicine_info";
    public static final String MEDICINE_MEDICINE_INFO_COLUMN_ID = "_id";
    public static final String MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID = "medicine_id";
    public static final String MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID = "medicine_info_id";

    public SQLite(Context context) {

        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void openDatabase(){
        String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
        if(mDataBase != null && mDataBase.isOpen()){
            return;
        }
        mDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public void closeDatabase(){
        if(mDataBase != null){
            mDataBase.close();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Doctor CRUD operations
    public long insertDoctor(Doctor d){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DOCTOR_COLUMN_SPECIALIZATION, d.getSpecialization());
        values.put(DOCTOR_COLUMN_NAME, d.getName());
        values.put(DOCTOR_COLUMN_SURNAME, d.getSurname());

        return db.insert(DOCTOR_TABLE_NAME,null, values);
    }

    public Cursor getDoctorInfo(int doctorId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " + DOCTOR_TABLE_NAME + " d " +
                " INNER JOIN " + DOCTOR_CONTACT_TABLE_NAME + " dc ON dc."+ DOCTOR_CONTACT_COLUMN_DOCTOR_ID + " = d."+ DOCTOR_COLUMN_ID +
                " INNER JOIN " + CONTACT_TABLE_NAME + " c ON c." + CONTACT_COLUMN_ID +" = dc." + DOCTOR_CONTACT_COLUMN_CONTACT_ID +
                " INNER JOIN " + DOCTOR_ADDRESS_TABLE_NAME +" da ON da." + DOCTOR_ADDRESS_COLUMN_DOCTOR_ID +" = d." + DOCTOR_COLUMN_ID +
                " INNER JOIN " + ADDRESS_TABLE_NAME + " a ON a." + ADDRESS_COLUMN_ID + " = da." + DOCTOR_ADDRESS_COLUMN_ADDRESS_ID +
                " WHERE d."+ DOCTOR_COLUMN_ID + " = ?", new String [] {Integer.toString(doctorId)});
        return query;
    }
    public Cursor getDoctor_Working_hours(int doctorId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.rawQuery("SELECT dw."+ DOCTOR_WORKING_HOURS_COLUMN_ID +" ,wh."+ WORKING_HOURS_COLUMN_ID +" ,wh."+ WORKING_HOURS_COLUMN_DAY +" ,wh."+ WORKING_HOURS_COLUMN_FROM +" ,wh."+ WORKING_HOURS_COLUMN_TO +" FROM " + DOCTOR_TABLE_NAME + " d " +
                " INNER JOIN " + DOCTOR_WORKING_HOURS_TABLE_NAME + " dw ON dw." + DOCTOR_WORKING_HOURS_COLUMN_DOCTOR_ID + " = d."+ DOCTOR_COLUMN_ID +
                " INNER JOIN " + WORKING_HOURS_TABLE_NAME + " wh ON wh." + WORKING_HOURS_COLUMN_ID + " = dw." + DOCTOR_WORKING_HOURS_COLUMN_WORKING_HOURS_ID +
                " WHERE d." + DOCTOR_COLUMN_ID + " = ?", new String[] {Integer.toString(doctorId)});
        return query;
    }

    public Cursor getAllDoctors(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " + DOCTOR_TABLE_NAME, null);

        return query;
    }

    public int updateDoctor(Doctor d){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DOCTOR_COLUMN_SPECIALIZATION, d.getSpecialization());
        values.put(DOCTOR_COLUMN_NAME, d.getName());
        values.put(DOCTOR_COLUMN_SURNAME, d.getSurname());

        return db.update(DOCTOR_TABLE_NAME, values, DOCTOR_COLUMN_ID + "= ?", new String[]{ String.valueOf(d.getId()) });
    }

    public int deleteDoctor(int doctorId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DOCTOR_TABLE_NAME, DOCTOR_COLUMN_ID + " = ? ", new String[] {Integer.toString(doctorId)});
    }

    public long insertContact(Contact c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CONTACT_COLUMN_EMAIL, c.getEmail());
        values.put(CONTACT_COLUMN_TELEPHONE, c.getTelephone());

        return db.insert(CONTACT_TABLE_NAME, null, values);
    }

    public int updateContact(Contact c){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACT_COLUMN_EMAIL, c.getEmail());
        values.put(CONTACT_COLUMN_TELEPHONE, c.getTelephone());

        return db.update(CONTACT_TABLE_NAME, values, CONTACT_COLUMN_ID + "= ?", new String[]{ String.valueOf(c.getId()) });
    }

    public int deleteContact(int contactId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CONTACT_TABLE_NAME, CONTACT_COLUMN_ID + " = ? ", new String[] {Integer.toString(contactId)});
    }

    public long insertAddress (Address a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ADDRESS_COLUMN_CITY, a.getCity());
        values.put(ADDRESS_COLUMN_STREET, a.getStreet());
        values.put(ADDRESS_COLUMN_STREET_NUMBER, a.getStreet_number());

        return db.insert(ADDRESS_TABLE_NAME, null, values);
    }

    public int updateAddress(Address a){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ADDRESS_COLUMN_CITY, a.getCity());
        values.put(ADDRESS_COLUMN_STREET, a.getStreet());
        values.put(ADDRESS_COLUMN_STREET_NUMBER, a.getStreet_number());

        return db.update(ADDRESS_TABLE_NAME, values, ADDRESS_COLUMN_ID + "= ?", new String[]{ String.valueOf(a.getId()) });
    }

    public int deleteAddress(int addressId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ADDRESS_TABLE_NAME, ADDRESS_COLUMN_ID + " = ? ", new String[] {Integer.toString(addressId)});
    }

    public long insertWorking_hours(Working_hours wh){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(WORKING_HOURS_COLUMN_DAY, wh.getDay());
        values.put(WORKING_HOURS_COLUMN_FROM, wh.getFromwh());
        values.put(WORKING_HOURS_COLUMN_TO, wh.getTowh());

        return db.insert(WORKING_HOURS_TABLE_NAME, null, values);
    }

    public int updateWorking_hours(Working_hours wh){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WORKING_HOURS_COLUMN_DAY, wh.getDay());
        values.put(WORKING_HOURS_COLUMN_FROM, wh.getFromwh());
        values.put(WORKING_HOURS_COLUMN_TO, wh.getTowh());

        return db.update(WORKING_HOURS_TABLE_NAME, values, WORKING_HOURS_COLUMN_ID + "= ?", new String[]{ String.valueOf(wh.getId()) });
    }

    public int deleteWorking_hours(int working_hoursId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(WORKING_HOURS_TABLE_NAME, WORKING_HOURS_COLUMN_ID + " = ? ", new String[] {Integer.toString(working_hoursId)});
    }

    public long insertDoctorContact(int doctorId, int contactId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DOCTOR_CONTACT_COLUMN_DOCTOR_ID, doctorId);
        values.put(DOCTOR_CONTACT_COLUMN_CONTACT_ID, contactId);

        return db.insert(DOCTOR_CONTACT_TABLE_NAME, null, values);
    }

    public int deleteDoctor_Contact(int doctor_contactId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DOCTOR_CONTACT_TABLE_NAME, DOCTOR_CONTACT_COLUMN_ID + " = ? ", new String[] {Integer.toString(doctor_contactId)});
    }

    public long insertDoctorAddress(int doctorId, int addressId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DOCTOR_ADDRESS_COLUMN_DOCTOR_ID, doctorId);
        values.put(DOCTOR_ADDRESS_COLUMN_ADDRESS_ID, addressId);

        return db.insert(DOCTOR_ADDRESS_TABLE_NAME, null, values);
    }

    public int deleteDoctor_Address(int doctor_addressId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DOCTOR_ADDRESS_TABLE_NAME, DOCTOR_ADDRESS_COLUMN_ID + " = ? ", new String[] {Integer.toString(doctor_addressId)});
    }

    public long insertDoctorWorking_hours (int doctorId, int working_hoursId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DOCTOR_WORKING_HOURS_COLUMN_DOCTOR_ID, doctorId);
        values.put(DOCTOR_WORKING_HOURS_COLUMN_WORKING_HOURS_ID, working_hoursId);

        return db.insert(DOCTOR_WORKING_HOURS_TABLE_NAME, null, values);
    }

    public int deleteDoctor_Workinh_hours(int doctor_working_hoursId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DOCTOR_WORKING_HOURS_TABLE_NAME, DOCTOR_WORKING_HOURS_COLUMN_ID + " = ? ", new String[] {Integer.toString(doctor_working_hoursId)});
    }

    //Measurement CRUD operations
    public long insertMeasurement(Measurement m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEASUREMENT_COLUMN_NAME, m.getMeasurement_name());
        values.put(MEASUREMENT_COLUMN_VALUE, m.getMeasurement_value());
        values.put(MEASUREMENT_COLUMN_UNIT, m.getMeasurement_unit());

        return db.insert(MEASUREMENT_TABLE_NAME,null, values);
    }

    public Cursor getMeasurement(int measurmentId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " + MEASUREMENT_TABLE_NAME + " WHERE " +
                MEASUREMENT_COLUMN_ID + " =?", new String[] { Integer.toString(measurmentId) });
        return query;
    }

    public Cursor getAllMeasurements(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " + MEASUREMENT_TABLE_NAME, null);

        return query;
    }

    public int updateMeasurement(Measurement m){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEASUREMENT_COLUMN_VALUE, m.getMeasurement_value());

        return db.update(MEASUREMENT_TABLE_NAME, values, MEASUREMENT_COLUMN_ID + "= ?", new String[]{ String.valueOf( m.getId()) });
    }

    public int deleteMeasurement(int measurmentId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(MEASUREMENT_TABLE_NAME, MEASUREMENT_COLUMN_ID + " = ? ", new String[] {Integer.toString(measurmentId)});
    }

    //Medicine CRUD
    public Cursor getMedicine(int measurmentId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " + MEDICINE_TABLE_NAME + " m " +
                "INNER JOIN " + MEDICINE_MEDICINE_INFO_TABLE_NAME + " mni ON mni." + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID + " = m."+ MEDICINE_COLUMN_ID
                + " INNER JOIN " + MEDICINE_INFO_TABLE_NAME + " mi ON mi."+MEDICINE_INFO_COLUMN_ID + " = mni." + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID
                + " WHERE m." + MEDICINE_COLUMN_ID + " = ?", new String[]{ Integer.toString(measurmentId) });
        return query;
    }
    public Cursor getAllMedicine(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " + MEDICINE_TABLE_NAME, null);
        return query;
    }
    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}


//package com.ronald.medin;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.SearchRecentSuggestionsProvider;
//import android.content.res.AssetManager;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import com.ronald.medin.Classes.Address;
//import com.ronald.medin.Classes.Contact;
//import com.ronald.medin.Classes.Doctor;
//import com.ronald.medin.Classes.Measurement;
//import com.ronald.medin.Classes.Working_hours;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//public class SQLite extends SQLiteOpenHelper {
//    private static final String DATABSE_NAME = "SQLiteDatabase.db";
//    private static final int DATABASE_VERSION = 1;
//
//    public SQLite(Context context){
//        super(context, DATABSE_NAME, null, DATABASE_VERSION);
//    }
//
//    //Doctor table names
//    public static final String DOCTOR_TABLE_NAME = "Doctor";
//    public static final String DOCTOR_COLUMN_ID = "_id";
//    public static final String DOCTOR_COLUMN_NAME = "name";
//    public static final String DOCTOR_COLUMN_SURNAME = "surname";
//    public static final String DOCTOR_COLUMN_SPECIALIZATION = "specialization";
//
//    //Contact table names
//    public static final String CONTACT_TABLE_NAME = "Contact";
//    public static final String CONTACT_COLUMN_ID = "_id";
//    public static final String CONTACT_COLUMN_EMAIL = "email";
//    public static final String CONTACT_COLUMN_TELEPHONE = "telephone";
//
//    //Address table names
//    public static final String ADDRESS_TABLE_NAME = "Address";
//    public static final String ADDRESS_COLUMN_ID = "_id";
//    public static final String ADDRESS_COLUMN_CITY = "city";
//    public static final String ADDRESS_COLUMN_STREET = "street";
//    public static final String ADDRESS_COLUMN_STREET_NUMBER = "street_number";
//
//    //Working hours table names
//    public static final String WORKING_HOURS_TABLE_NAME = "Working_hours";
//    public static final String WORKING_HOURS_COLUMN_ID = "_id";
//    public static final String WORKING_HOURS_COLUMN_DAY = "day";
//    public static final String WORKING_HOURS_COLUMN_FROM = "fromwh";
//    public static final String WORKING_HOURS_COLUMN_TO = "towh";
//
//    //Doctor_Contact
//    public static final String DOCTOR_CONTACT_TABLE_NAME = "Doctor_Contact";
//    public static final String DOCTOR_CONTACT_COLUMN_ID = "_id";
//    public static final String DOCTOR_CONTACT_COLUMN_DOCTOR_ID = "doctor_id";
//    public static final String DOCTOR_CONTACT_COLUMN_CONTACT_ID = "contact_id";
//
//    //Doctor_Address
//    public static final String DOCTOR_ADDRESS_TABLE_NAME = "Doctor_Address";
//    public static final String DOCTOR_ADDRESS_COLUMN_ID = "_id";
//    public static final String DOCTOR_ADDRESS_COLUMN_DOCTOR_ID = "doctor_id";
//    public static final String DOCTOR_ADDRESS_COLUMN_ADDRESS_ID = "address_id";
//
//    //Doctor_Working_hours
//    public static final String DOCTOR_WORKING_HOURS_TABLE_NAME = "Doctor_Working_hours";
//    public static final String DOCTOR_WORKING_HOURS_COLUMN_ID = "_id";
//    public static final String DOCTOR_WORKING_HOURS_COLUMN_DOCTOR_ID = "doctor_id";
//    public static final String DOCTOR_WORKING_HOURS_COLUMN_WORKING_HOURS_ID = "working_hours_id";
//
//
//    //Measurement table names
//    public static final String MEASUREMENT_TABLE_NAME = "Measurement";
//    public static final String MEASUREMENT_COLUMN_ID = "_id";
//    public static final String MEASUREMENT_COLUMN_NAME = "name";
//    public static final String MEASUREMENT_COLUMN_VALUE = "value";
//    public static final String MEASUREMENT_COLUMN_UNIT = "unit";
//    public static final String MEASUREMENT_COLUMN_CREATED = "dateCreated";
//
//    //Medicine table names
//    public static final String MEDICINE_TABLE_NAME = "Medicine";
//    public static final String MEDICINE_COLUMN_ID = "_id";
//    public static final String MEDICINE_COLUMN_NAME = "name";
//    public static final String MEDICINE_COLUMN_TYPE = "type";
//    public static final String MEDICINE_COLUMN_PACKAGE_QUANTITY = "package_quantity";
//
//    //Medicine_info table names
//    public static final String MEDICINE_INFO_TABLE_NAME = "Medicine_info";
//    public static final String MEDICINE_INFO_COLUMN_ID = "_id";
//    public static final String MEDICINE_INFO_COLUMN_DESCRIPTION = "description";
//    public static final String MEDICINE_INFO_COLUMN_WARNING = "warning";
//    public static final String MEDICINE_INFO_COLUMN_USAGE_INSTRUCTIONS = "usage_instructions";
//    public static final String MEDICINE_INFO_COLUMN_SIDE_EFFECTS = "side_effects";
//    public static final String MEDICINE_INFO_COLUMN_STORAGE_INSTRUCTIONS = "storage_instructions";
//    public static final String MEDICINE_INFO_COLUMN_OTHER_INFORMATION = "other_information";
//
//    //Medicine_Medicine_info table names
//    public static final String MEDICINE_MEDICINE_INFO_TABLE_NAME = "Medicine_Medicine_info";
//    public static final String MEDICINE_MEDICINE_INFO_COLUMN_ID = "_id";
//    public static final String MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID = "medicine_id";
//    public static final String MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID = "medicine_info_id";
//
//    //Create Doctor table SQL
//    private static final String CREATE_DOCTOR_TABLE = "CREATE TABLE " + DOCTOR_TABLE_NAME + "("
//            + DOCTOR_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + DOCTOR_COLUMN_NAME + " TEXT, "
//            + DOCTOR_COLUMN_SURNAME + " TEXT, "
//            + DOCTOR_COLUMN_SPECIALIZATION + " TEXT "
//            + ")";
//
//    //Create Contact table SQL
//    private static final String CREATE_CONTACT_TABLE = "CREATE TABLE " + CONTACT_TABLE_NAME + "("
//            + CONTACT_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + CONTACT_COLUMN_EMAIL + " TEXT, "
//            + CONTACT_COLUMN_TELEPHONE + " INTEGER "
//            + ")";
//
//    //Create Address table SQL
//    private static final String CREATE_ADDRESS_TABLE = "CREATE TABLE " + ADDRESS_TABLE_NAME + "("
//            + ADDRESS_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + ADDRESS_COLUMN_CITY + " TEXT, "
//            + ADDRESS_COLUMN_STREET + " TEXT, "
//            + ADDRESS_COLUMN_STREET_NUMBER + " INTEGER "
//            + ")";
//
//    //Create Working hours table SQL
//    private static final String CREATE_WORKING_HOURS_TABLE = "CREATE TABLE " + WORKING_HOURS_TABLE_NAME + "("
//            + WORKING_HOURS_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + WORKING_HOURS_COLUMN_DAY + " INTEGER, "
//            + WORKING_HOURS_COLUMN_FROM + " TEXT, "
//            + WORKING_HOURS_COLUMN_TO + " TEXT "
//            + ")";
//
//    //Create Doctor_Contact table SQL
//    private static final String CREATE_DOCTOR_CONTACT = "CREATE TABLE " + DOCTOR_CONTACT_TABLE_NAME + "("
//            + DOCTOR_CONTACT_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + DOCTOR_CONTACT_COLUMN_DOCTOR_ID + " INTEGER, "
//            + DOCTOR_CONTACT_COLUMN_CONTACT_ID + " INTEGER "
//            + ")";
//
//    //Create Doctor_Address table SQL
//    private static final String CREATE_DOCTOR_ADDRESS = "CREATE TABLE " + DOCTOR_ADDRESS_TABLE_NAME+ "("
//            + DOCTOR_ADDRESS_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + DOCTOR_ADDRESS_COLUMN_DOCTOR_ID + " INTEGER, "
//            + DOCTOR_ADDRESS_COLUMN_ADDRESS_ID + " INTEGER "
//            + ")";
//
//    //Create Doctor_Contact table SQL
//    private static final String CREATE_DOCTOR_WORKING_HOURS = "CREATE TABLE " + DOCTOR_WORKING_HOURS_TABLE_NAME + "("
//            + DOCTOR_WORKING_HOURS_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + DOCTOR_WORKING_HOURS_COLUMN_DOCTOR_ID + " INTEGER, "
//            + DOCTOR_WORKING_HOURS_COLUMN_WORKING_HOURS_ID + " INTEGER "
//            + ")";
//
//    //Create Measurement table SQL
//    private static final String CREATE_MEASUREMENT_TABLE = "CREATE TABLE " + MEASUREMENT_TABLE_NAME + "("
//            + MEASUREMENT_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + MEASUREMENT_COLUMN_NAME + " TEXT, "
//            + MEASUREMENT_COLUMN_VALUE + " INTEGER, "
//            + MEASUREMENT_COLUMN_UNIT + " TEXT, "
//            + MEASUREMENT_COLUMN_CREATED + " DATETIME DEFAULT (datetime('now','localtime')) "
//            + ")";
//
//    //Create Medicine table SQL
//    private static final String CREATE_MEDICINE_TABLE = "CREATE TABLE " + MEDICINE_TABLE_NAME + "("
//            + MEDICINE_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + MEDICINE_COLUMN_NAME + " TEXT, "
//            + MEDICINE_COLUMN_TYPE + " TEXT, "
//            + MEDICINE_COLUMN_PACKAGE_QUANTITY + " INTEGER "
//            + ")";
//
//    //Create Medicine_info table SQL
//    private static final String CREATE_MEDICINE_INFO_TABLE = "CREATE TABLE " + MEDICINE_INFO_TABLE_NAME + "("
//            + MEDICINE_INFO_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + MEDICINE_INFO_COLUMN_DESCRIPTION + " TEXT, "
//            + MEDICINE_INFO_COLUMN_WARNING + " TEXT, "
//            + MEDICINE_INFO_COLUMN_USAGE_INSTRUCTIONS + " TEXT, "
//            + MEDICINE_INFO_COLUMN_SIDE_EFFECTS + " TEXT, "
//            + MEDICINE_INFO_COLUMN_STORAGE_INSTRUCTIONS + " TEXT, "
//            + MEDICINE_INFO_COLUMN_OTHER_INFORMATION + " TEXT"
//            + ")";
//
//    //Create Medicine_Medicine_info table SQL
//    private static final String CREATE_MEDICINE_MEDICINE_INFO_TABLE = "CREATE TABLE " + MEDICINE_MEDICINE_INFO_TABLE_NAME + "("
//            + MEDICINE_MEDICINE_INFO_COLUMN_ID + " INTEGER PRIMARY KEY, "
//            + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID + " INTEGER, "
//            + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID + " INTEGER"
//            + ")";
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        db.execSQL(CREATE_DOCTOR_TABLE);
//        db.execSQL(CREATE_CONTACT_TABLE);
//        db.execSQL(CREATE_ADDRESS_TABLE);
//        db.execSQL(CREATE_WORKING_HOURS_TABLE);
//        db.execSQL(CREATE_DOCTOR_CONTACT);
//        db.execSQL(CREATE_DOCTOR_ADDRESS);
//        db.execSQL(CREATE_DOCTOR_WORKING_HOURS);
//
//        db.execSQL(CREATE_MEASUREMENT_TABLE);
//        db.execSQL(CREATE_MEDICINE_TABLE);
//        db.execSQL(CREATE_MEDICINE_INFO_TABLE);
//        db.execSQL(CREATE_MEDICINE_MEDICINE_INFO_TABLE);
//
////        db.execSQL(Paralen);
////        db.execSQL(ParalenInfo);
////
////        db.execSQL(Olynth);
////        db.execSQL(OlynthInfo);
////
////        db.execSQL(Ibalgin);
////        db.execSQL(IbalginInfo);
////
////        db.execSQL(Sinecod);
////        db.execSQL(SinecodInfo);
////
////        db.execSQL(Coldrex);
////        db.execSQL(ColdrexInfo);
//
////        db.execSQL("INSERT INTO " + MEDICINE_MEDICINE_INFO_TABLE_NAME + "(" + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID + "," + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID + ") VALUES (1,1)");
////        db.execSQL("INSERT INTO " + MEDICINE_MEDICINE_INFO_TABLE_NAME + "(" + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID + "," + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID + ") VALUES (2,2)");
////        db.execSQL("INSERT INTO " + MEDICINE_MEDICINE_INFO_TABLE_NAME + "(" + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID + "," + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID + ") VALUES (3,3)");
////        db.execSQL("INSERT INTO " + MEDICINE_MEDICINE_INFO_TABLE_NAME + "(" + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID + "," + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID + ") VALUES (4,4)");
////        db.execSQL("INSERT INTO " + MEDICINE_MEDICINE_INFO_TABLE_NAME + "(" + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID + "," + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID + ") VALUES (5,5)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + DOCTOR_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + ADDRESS_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + WORKING_HOURS_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + DOCTOR_CONTACT_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + DOCTOR_ADDRESS_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + DOCTOR_WORKING_HOURS_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + MEASUREMENT_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_INFO_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_MEDICINE_INFO_TABLE_NAME);
//
//        onCreate(db);
//    }
//
//    //Doctor CRUD operations
//    public long insertDoctor(Doctor d){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(DOCTOR_COLUMN_SPECIALIZATION, d.getSpecialization());
//        values.put(DOCTOR_COLUMN_NAME, d.getName());
//        values.put(DOCTOR_COLUMN_SURNAME, d.getSurname());
//
//        return db.insert(DOCTOR_TABLE_NAME,null, values);
//    }
//
//    public Cursor getDoctorInfo(int doctorId){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor query = db.rawQuery("SELECT * FROM " + DOCTOR_TABLE_NAME + " d " +
//                " INNER JOIN " + DOCTOR_CONTACT_TABLE_NAME + " dc ON dc."+ DOCTOR_CONTACT_COLUMN_DOCTOR_ID + " = d."+ DOCTOR_COLUMN_ID +
//                " INNER JOIN " + CONTACT_TABLE_NAME + " c ON c." + CONTACT_COLUMN_ID +" = dc." + DOCTOR_CONTACT_COLUMN_CONTACT_ID +
//                " INNER JOIN " + DOCTOR_ADDRESS_TABLE_NAME +" da ON da." + DOCTOR_ADDRESS_COLUMN_DOCTOR_ID +" = d." + DOCTOR_COLUMN_ID +
//                " INNER JOIN " + ADDRESS_TABLE_NAME + " a ON a." + ADDRESS_COLUMN_ID + " = da." + DOCTOR_ADDRESS_COLUMN_ADDRESS_ID +
//                " WHERE d."+ DOCTOR_COLUMN_ID + " = ?", new String [] {Integer.toString(doctorId)});
//        return query;
//    }
//    public Cursor getDoctor_Working_hours(int doctorId){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor query = db.rawQuery("SELECT dw."+ DOCTOR_WORKING_HOURS_COLUMN_ID +" ,wh."+ WORKING_HOURS_COLUMN_ID +" ,wh."+ WORKING_HOURS_COLUMN_DAY +" ,wh."+ WORKING_HOURS_COLUMN_FROM +" ,wh."+ WORKING_HOURS_COLUMN_TO +" FROM " + DOCTOR_TABLE_NAME + " d " +
//                " INNER JOIN " + DOCTOR_WORKING_HOURS_TABLE_NAME + " dw ON dw." + DOCTOR_WORKING_HOURS_COLUMN_DOCTOR_ID + " = d."+ DOCTOR_COLUMN_ID +
//                " INNER JOIN " + WORKING_HOURS_TABLE_NAME + " wh ON wh." + WORKING_HOURS_COLUMN_ID + " = dw." + DOCTOR_WORKING_HOURS_COLUMN_WORKING_HOURS_ID +
//                " WHERE d." + DOCTOR_COLUMN_ID + " = ?", new String[] {Integer.toString(doctorId)});
//        return query;
//    }
//
//    public Cursor getAllDoctors(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor query = db.rawQuery("SELECT * FROM " + DOCTOR_TABLE_NAME, null);
//
//        return query;
//    }
//
//    public int updateDoctor(Doctor d){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(DOCTOR_COLUMN_SPECIALIZATION, d.getSpecialization());
//        values.put(DOCTOR_COLUMN_NAME, d.getName());
//        values.put(DOCTOR_COLUMN_SURNAME, d.getSurname());
//
//        return db.update(DOCTOR_TABLE_NAME, values, DOCTOR_COLUMN_ID + "= ?", new String[]{ String.valueOf(d.getId()) });
//    }
//
//    public int deleteDoctor(int doctorId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(DOCTOR_TABLE_NAME, DOCTOR_COLUMN_ID + " = ? ", new String[] {Integer.toString(doctorId)});
//    }
//
//    public long insertContact(Contact c){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(CONTACT_COLUMN_EMAIL, c.getEmail());
//        values.put(CONTACT_COLUMN_TELEPHONE, c.getTelephone());
//
//        return db.insert(CONTACT_TABLE_NAME, null, values);
//    }
//
//    public int updateContact(Contact c){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(CONTACT_COLUMN_EMAIL, c.getEmail());
//        values.put(CONTACT_COLUMN_TELEPHONE, c.getTelephone());
//
//        return db.update(CONTACT_TABLE_NAME, values, CONTACT_COLUMN_ID + "= ?", new String[]{ String.valueOf(c.getId()) });
//    }
//
//    public int deleteContact(int contactId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(CONTACT_TABLE_NAME, CONTACT_COLUMN_ID + " = ? ", new String[] {Integer.toString(contactId)});
//    }
//
//    public long insertAddress (Address a){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(ADDRESS_COLUMN_CITY, a.getCity());
//        values.put(ADDRESS_COLUMN_STREET, a.getStreet());
//        values.put(ADDRESS_COLUMN_STREET_NUMBER, a.getStreet_number());
//
//        return db.insert(ADDRESS_TABLE_NAME, null, values);
//    }
//
//    public int updateAddress(Address a){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(ADDRESS_COLUMN_CITY, a.getCity());
//        values.put(ADDRESS_COLUMN_STREET, a.getStreet());
//        values.put(ADDRESS_COLUMN_STREET_NUMBER, a.getStreet_number());
//
//        return db.update(ADDRESS_TABLE_NAME, values, ADDRESS_COLUMN_ID + "= ?", new String[]{ String.valueOf(a.getId()) });
//    }
//
//    public int deleteAddress(int addressId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(ADDRESS_TABLE_NAME, ADDRESS_COLUMN_ID + " = ? ", new String[] {Integer.toString(addressId)});
//    }
//
//    public long insertWorking_hours(Working_hours wh){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(WORKING_HOURS_COLUMN_DAY, wh.getDay());
//        values.put(WORKING_HOURS_COLUMN_FROM, wh.getFromwh());
//        values.put(WORKING_HOURS_COLUMN_TO, wh.getTowh());
//
//        return db.insert(WORKING_HOURS_TABLE_NAME, null, values);
//    }
//
//    public int updateWorking_hours(Working_hours wh){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(WORKING_HOURS_COLUMN_DAY, wh.getDay());
//        values.put(WORKING_HOURS_COLUMN_FROM, wh.getFromwh());
//        values.put(WORKING_HOURS_COLUMN_TO, wh.getTowh());
//
//        return db.update(WORKING_HOURS_TABLE_NAME, values, WORKING_HOURS_COLUMN_ID + "= ?", new String[]{ String.valueOf(wh.getId()) });
//    }
//
//    public int deleteWorking_hours(int working_hoursId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(WORKING_HOURS_TABLE_NAME, WORKING_HOURS_COLUMN_ID + " = ? ", new String[] {Integer.toString(working_hoursId)});
//    }
//
//    public long insertDoctorContact(int doctorId, int contactId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(DOCTOR_CONTACT_COLUMN_DOCTOR_ID, doctorId);
//        values.put(DOCTOR_CONTACT_COLUMN_CONTACT_ID, contactId);
//
//        return db.insert(DOCTOR_CONTACT_TABLE_NAME, null, values);
//    }
//
//    public int deleteDoctor_Contact(int doctor_contactId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(DOCTOR_CONTACT_TABLE_NAME, DOCTOR_CONTACT_COLUMN_ID + " = ? ", new String[] {Integer.toString(doctor_contactId)});
//    }
//
//    public long insertDoctorAddress(int doctorId, int addressId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(DOCTOR_ADDRESS_COLUMN_DOCTOR_ID, doctorId);
//        values.put(DOCTOR_ADDRESS_COLUMN_ADDRESS_ID, addressId);
//
//        return db.insert(DOCTOR_ADDRESS_TABLE_NAME, null, values);
//    }
//
//    public int deleteDoctor_Address(int doctor_addressId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(DOCTOR_ADDRESS_TABLE_NAME, DOCTOR_ADDRESS_COLUMN_ID + " = ? ", new String[] {Integer.toString(doctor_addressId)});
//    }
//
//    public long insertDoctorWorking_hours (int doctorId, int working_hoursId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(DOCTOR_WORKING_HOURS_COLUMN_DOCTOR_ID, doctorId);
//        values.put(DOCTOR_WORKING_HOURS_COLUMN_WORKING_HOURS_ID, working_hoursId);
//
//        return db.insert(DOCTOR_WORKING_HOURS_TABLE_NAME, null, values);
//    }
//
//    public int deleteDoctor_Workinh_hours(int doctor_working_hoursId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(DOCTOR_WORKING_HOURS_TABLE_NAME, DOCTOR_WORKING_HOURS_COLUMN_ID + " = ? ", new String[] {Integer.toString(doctor_working_hoursId)});
//    }
//
//    //Measurement CRUD operations
//    public long insertMeasurement(Measurement m){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(MEASUREMENT_COLUMN_NAME, m.getMeasurement_name());
//        values.put(MEASUREMENT_COLUMN_VALUE, m.getMeasurement_value());
//        values.put(MEASUREMENT_COLUMN_UNIT, m.getMeasurement_unit());
//
//        return db.insert(MEASUREMENT_TABLE_NAME,null, values);
//    }
//
//    public Cursor getMeasurement(int measurmentId){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor query = db.rawQuery("SELECT * FROM " + MEASUREMENT_TABLE_NAME + " WHERE " +
//                MEASUREMENT_COLUMN_ID + " =?", new String[] { Integer.toString(measurmentId) });
//        return query;
//    }
//
//    public Cursor getAllMeasurements(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor query = db.rawQuery("SELECT * FROM " + MEASUREMENT_TABLE_NAME, null);
//
//        return query;
//    }
//
//    public int updateMeasurement(Measurement m){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(MEASUREMENT_COLUMN_VALUE, m.getMeasurement_value());
//
//        return db.update(MEASUREMENT_TABLE_NAME, values, MEASUREMENT_COLUMN_ID + "= ?", new String[]{ String.valueOf( m.getId()) });
//    }
//
//    public int deleteMeasurement(int measurmentId){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(MEASUREMENT_TABLE_NAME, MEASUREMENT_COLUMN_ID + " = ? ", new String[] {Integer.toString(measurmentId)});
//    }
//
//    //Medicine CRUD
//    public Cursor getMedicine(int measurmentId){
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor query = db.rawQuery("SELECT * FROM " + MEDICINE_TABLE_NAME + " m " +
//                "INNER JOIN " + MEDICINE_MEDICINE_INFO_TABLE_NAME + " mni ON mni." + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID + " = m."+ MEDICINE_COLUMN_ID
//        + " INNER JOIN " + MEDICINE_INFO_TABLE_NAME + " mi ON mi."+MEDICINE_INFO_COLUMN_ID + " = mni." + MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID
//        + " WHERE m." + MEDICINE_COLUMN_ID + " = ?", new String[]{ Integer.toString(measurmentId) });
//        return query;
//    }
//    public Cursor getAllMedicine(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor query = db.rawQuery("SELECT * FROM " + MEDICINE_TABLE_NAME, null);
//        return query;
//    }
//}
//
//
//
//
//
////    private static final String Paralen =
////            "INSERT INTO " + MEDICINE_TABLE_NAME + "(name, type, package_quantity) VALUES ('PARALEN 500','Tablety','24')";
////    private static final String ParalenInfo =
////            "INSERT INTO " + MEDICINE_INFO_TABLE_NAME + "(description, warning, usage_instructions, side_effects, storage_instructions, other_information) VALUES " +
////                    "('Paracetamol, léčivá látka přípravku Paralen 500 tablety, působí proti bolesti a snižuje zvýšenou tělesnou teplotu. Tablety Paralen 500 nezhoršují žaludeční potíže a nevyvolávají zvracení, mohou je užít i nemocní se žaludečními a dvanáctníkovými vředy a nemocní, kteří nesnášejí kyselinu acetylsalicylovou.Tablety Paralen 500 jsou určeny ke snížení horečky a bolesti při chřipce, nachlazení a jiných infekčních onemocněních.Tablety Paralen 500 jsou také vhodné při bolestech různého původu, např. při bolestech hlavy, zubů, bolestivé menstruaci, bolesti v krku a bolesti pohybového ústrojí provázející chřipku a nachlazení..Paralen 500 je určen pro dospělé, mladistvé a děti od 6 let věku.','Neužívejte přípravek PARALEN 500 pokud- jste alergický/á (přecitlivělý/á) na účinnou látku nebo na některou pomocnou látku přípravku, - máte vážné onemocnění jater nebo akutní zánět jater - máte typ chudokrevnosti zvaný hemolytická anémiePokud si nejste jisti, zda se vás uvedené týká, poraďte se se svým lékařem.Vzhledem k vysokému obsahu účinné látky nejsou tablety Paralen 500 vhodné pro děti mladší než 6 let (nebo pro děti s tělesnou hmotností menší než 20 kg).\n" +
////                    "\n" +
////                    "Zvláštní opatrnosti při použití přípravku PARALEN 500 je zapotřebí Před podáním přípravku konzultujte lékaře, pokud - máte nedostatek enzymu glukóza-6-fosfát dehydrogenáza- současně užíváte léky ovlivňující funkci jater - máte vážné onemocnění ledvinPokud si nejste jisti, zda se vás uvedené týká, poraďte se se svým lékařem.\n" +
////                    "\n" +
////                    "O vhodnosti současného užívání tablet Paralen 500 s jinými léky proti bolesti a nachlazení se poraďte s lékařem.Užívání vyšších než doporučených dávek může vést k riziku závažného poškození jater. Neužívejte tento přípravek bez doporučení lékaře, pokud trpíte jaterním onemocněním a/nebo užívate jakákoliv jiná léčiva obsahující paracetamol.Při onemocnění ledvin je možno užívat Paralen 500 tablety pouze po poradě s lékařem.\n" +
////                    "\n" +
////                    "Vzájemné působení s dalšími léčivými přípravkyÚčinky tablet Paralen 500 a jiných léků současně užívaných se mohou navzájem ovlivňovat. Současné užívání tablet Paralen 500 a některých léků na spaní, léků proti epilepsii, některých antibiotik nebo současné pití alkoholu může způsobit poškození jater. V případě, že užíváte jiné léky, a to na lékařský předpis i bez něj, poraďte se o vhodnosti současného užívání tablet Paralen 500 s lékařem a bez porady s ním je neužívejte. V případě předepisování jiných léků upozorněte lékaře, že užíváte Paralen 500 tablety.\n" +
////                    "\n" +
////                    "Užívání přípravku PARALEN 500 s jídlem a pitímJestliže se během léčby objeví zažívací obtíže, podávejte lék během jídla.Během léčby nesmíte pít alkoholické nápoje.\n" +
////                    "\n" +
////                    "Těhotenství a kojeníPoraďte se se svým lékařem nebo lékárníkem dříve, než začnete užívat jakýkoliv lék.Těhotné ženy mohou užívat tablety Paralen 500 jen po poradě s lékařem. Kojící ženy mohou přípravek užívat 1 den, déle než 1 den mohou přípravek užívat pouze po poradě s lékařem.\n" +
////                    "\n" +
////                    "Řízení dopravních prostředků a obsluha strojůPřípravek neovlivňuje činnosti vyžadující zvýšenou pozornost (řízení motorových vozidel, obsluha strojů, práce ve výškách).','Jednotlivé a maximální denní dávky dle věku a hmotnosti jsou vyznačeny v tabulce. Nepřekračujte doporučené dávkování.\n" +
////                    "\n" +
////                    "Dospělí a mladiství (starší 15 let ) užívají 1-2 tablety dle potřeby několikrát denně v časovém odstupu nejméně 4 hodin. Maximální denní dávka je 8 tablet, nejvyšší jednotlivá dávka jsou 2 tablety.Při dlouhodobé terapii (nad 10 dnů) nemá denní dávka překročit 5 tablet.Dětem a mladistvým ve věku od 6 do 15 let se podává 1/2 - 1 tableta dle potřeby několikrát denně v 6-hodinových intervalech. Interval lze zkrátit v případě potřeby na 4 hod, přičemž nesmí být překročena celková denní dávka. Maximální denní dávky dle věku a hmotnosti jsou vyznačeny v tabulce.Děti do 6 let věkuVzhledem k množství léčivé látky v tabletě není přípravek určen pro děti do 6 let věku. Dětem od 3 let je možno podat Paralen 125 tablety, pro mladší děti je určen Paralen ve formě suspenze nebo čípků.Nemocní se sníženou funkcí ledvin užívají po poradě s lékařem nižší dávky.\n" +
////                    "\n" +
////                    "Léčení můžete ukončit, jakmile horečka nebo bolest ustoupí.Tablety Paralen 500 se užívají při jídle, užití před jídlem zvýší rychlost nástupu účinku, tablety je možné půlit nebo drtit, zapijí se douškem tekutiny.\n" +
////                    "\n" +
////                    "Pokud nedojde do 3 dnů k ústupu obtíží (horečka, bolest) nebo naopak dojde ke zhoršení obtíží či se vyskytnou neobvyklé reakce, poraďte se o dalším užívání přípravku s lékařem.Bez porady s lékařem neužívejte Paralen 500 tablety nepřetržitě déle než 1 týden. Dítěti nepodávejte Paralen 500 bez porady s lékařem déle než 3 dny.Při dlouhodobém užívání tablet Paralen 500 (několik týdnů) je třeba průběžná kontrola u lékaře.\n" +
////                    "\n" +
////                    "Věk\n" +
////                    "\n" +
////                    "Hmotnost\n" +
////                    "\n" +
////                    "Jednotlivá dávka\n" +
////                    "\n" +
////                    "Max. denní dávka\n" +
////                    "\n" +
////                    "Děti 6-12 let\n" +
////                    "\n" +
////                    "21 - 25 kg\n" +
////                    "\n" +
////                    "1/2 - 1 tableta PARALEN 500\n" +
////                    "\n" +
////                    "(250-500 mg paracetamolu)\n" +
////                    "\n" +
////                    "3 tablety PARALEN 500\n" +
////                    "\n" +
////                    "(1,5 g paracetamolu)\n" +
////                    "\n" +
////                    "26 - 40 kg\n" +
////                    "\n" +
////                    "4 tablety PARALEN 500\n" +
////                    "\n" +
////                    "(2 g paracetamolu)\n" +
////                    "\n" +
////                    "Mladiství\n" +
////                    "\n" +
////                    "12-15 let\n" +
////                    "\n" +
////                    "40 - 50 kg\n" +
////                    "\n" +
////                    "1 tableta PARALEN 500\n" +
////                    "\n" +
////                    "(500mg paracetamolu)\n" +
////                    "\n" +
////                    "6 tablet PARALEN 500\n" +
////                    "\n" +
////                    "(3 g paracetamolu)\n" +
////                    "\n" +
////                    "Mladiství\n" +
////                    "\n" +
////                    "nad 15 let a\n" +
////                    "\n" +
////                    "dospělí\n" +
////                    "\n" +
////                    "≤ 50 kg\n" +
////                    "\n" +
////                    "1 tableta PARALEN 500\n" +
////                    "\n" +
////                    "(500mg paracetamolu)\n" +
////                    "\n" +
////                    "8 tablet PARALEN 500\n" +
////                    "\n" +
////                    "(4g paracetamolu)\n" +
////                    "\n" +
////                    "> 50 kg\n" +
////                    "\n" +
////                    "1-2 tablety PARALEN 500\n" +
////                    "\n" +
////                    "(500 - 1000 mg paracetamolu)\n" +
////                    "\n" +
////                    "Jestliže jste užil(a) více přípravku PARALEN 500 než jste měl(a)Při předávkování nebo náhodném požití tablet dítětem ihned vyhledejte lékaře, i když nejsou přítomny příznaky předávkování!\n" +
////                    "\n" +
////                    "Jestliže jste zapomněla užít přípravek PARALEN 500Pokud je třeba, podejte další dávku přípravku, jakmile si vzpomenete, dodržte však odstup mezi jednotlivými dávkami minimálně 4 hodiny a neužívejte více než maximální denní dávku.Nezdvojujte následující dávku, abyste nahradil(a) vynechanou tabletu.\n" +
////                    "\n" +
////                    "Máte-li jakékoli další otázky, týkající se užívání tohoto přípravku, zeptejte se svého lékaře nebo lékárníka.','Podobně jako všechny léky, může i PARALEN 500 způsobit nežádoucí účinky, které se ale nemusí vyskytnout u každého.Tablety Paralen 500 v doporučeném dávkování vyvolávají nežádoucí účinky jen zřídka. U citlivějších jedinců se mohou vyskytnout alergické reakce, např. kožní vyrážky.Pokud se kterýkoli z nežádoucích účinků vyskytne v závažné míře, nebo pokud si všimnetejakýchkoli nežádoucích účinků, které nejsou uvedeny v této příbalové informaci, prosím, sdělte to svému lékaři nebo lékárníkovi.', 'Přípravek nesmí být používán po uplynutí doby použitelnosti vyznačené na obalu.Přípravek musí být uchováván mimo dosah a dohled dětí.Uchovávejte při teplotě do 25\n" +
////                    "\n" +
////                    "o\n" +
////                    "\n" +
////                    "C, v původním vnitřním obalu, vnitřní obal v krabičce, aby byl\n" +
////                    "\n" +
////                    "přípravek chráněn před světlem.Léčivé přípravky se nesmí vyhazovat do odpadních vod nebo domácího odpadu. Zeptejte se svého lékárníka, jak máte likvidovat přípravky, které již nepotřebujete. Tato opatření pomáhají chránit životní prostředí.', 'Co přípravek PARALEN 500 obsahujeLéčivá látka: Paracetamol 500 mg v 1 tabletěPomocné látky: Předbobtnalý kukuřičný škrob, povidon 30, sodná sůl kroskarmelosy, kyselina stearová.\n" +
////                    "\n" +
////                    "Jak přípravek PARALEN 500 vypadá a co obsahuje toto baleníPopis: Bílé, až téměř bílé tablety s půlicí rýhou, velikosti 18 x 8 mm, s půlící rýhou na jedné straně a vyraženým nápisem PARALEN na druhé straně.\n" +
////                    "\n" +
////                    "Velikost balení: 10, 12, 20 a 24 tablet\n" +
////                    "\n" +
////                    "Držitel rozhodnutí o registraci a výrobce:Zentiva, k.s., Praha, Česká republika\n" +
////                    "\n" +
////                    "Tato příbalová informace byla naposledy schválena 16.4.2010')";
////
////    private static final String Olynth =
////            "INSERT INTO " + MEDICINE_TABLE_NAME + "(name, type, package_quantity) VALUES ('OLYNTH® 0,1 %','Nosní sprej','10 ml')";
////    private static final String OlynthInfo =
////            "INSERT INTO " + MEDICINE_INFO_TABLE_NAME + "(description, warning, usage_instructions, side_effects, storage_instructions, other_information) VALUES " +
////                    "('Léčivá látka přípravku Olynth HA 0,1%, xylometazolin-hydrochlorid, způsobuje v místě svého účinku stažení cév, tím snižuje otok sliznic a následně usnadňuje dýchání nosem a zlepšuje uvolnění hlenů. K nástupu účinku obvykle dochází za 5–10 minut. Pomocná látka natrium-hyaluronát napomáhá zvlhčení nosní sliznice.\n" +
////                    "\n" +
////                    "Přípravek Olynth HA 0,1% je určen ke snížení otoku nosní sliznice při akutní rýmě, při nadměrné tvorbě hlenu způsobené vazomotorickou rýmou a při alergické rýmě.\n" +
////                    "\n" +
////                    "Přípravek Olynth HA 0,1% je rovněž určen k urychlení uvolnění hlenů při zánětu vedlejších dutin nosních a při zánětu Eustachovy trubice provázeného rýmou.\n" +
////                    "\n" +
////                    "Olynth HA 0,1% je určen k léčbě dospělých a dětí od 7 let.','Nepoužívejte přípravek Olynth HA 0,1%\n" +
////                    "\n" +
////                    "jestliže jste alergický/á (přecitlivělý/á) na xylometazolin-hydrochlorid nebo na kteroukoli další složku přípravku\n" +
////                    "v případě suchého zánětu nosní sliznice (rhinitis sicca), který se projevuje pocitem sucha a šimrání v nose často provázeným drobným krvácením po odloučení zaschlých strupů\n" +
////                    "u dětí do 7 let.\n" +
////                    "Zvláštní opatrnosti při použití přípravku Olynth HA 0,1% je zapotřebí\n" +
////                    "\n" +
////                    "jestliže jste léčen/a některými léky proti depresi (inhibitory monoaminooxidázy) nebo jinými léky s možným účinkem na zvýšení krevního tlaku\n" +
////                    "jestliže trpíte zvýšeným nitroočním tlakem, zejména máte-li zelený zákal (glaukom) s uzavřeným úhlem\n" +
////                    "jestliže trpíte závažným onemocněním oběhového systému (např. ischemickou chorobou srdeční, vysokým krevním tlakem)\n" +
////                    "jestliže trpíte feochromocytomem (nádorem dřeně nadledvin, který způsobuje zvýšení krevního tlaku)\n" +
////                    "jestliže trpíte některými poruchami látkové výměny (zvýšenou činností štítné žlázy, cukrovkou)\n" +
////                    "V těchto případech lze přípravek Olynth HA 0,1% používat pouze po poradě s lékařem, který posoudí prospěšnost léčby u jmenovaných onemocnění.\n" +
////                    "\n" +
////                    "Vzájemné působení s dalšími léčivými přípravky\n" +
////                    "Prosím, informujte svého lékaře nebo lékárníka o všech lécích, které užíváte nebo jste užíval/a v nedávné době, a to i o lécích, které jsou dostupné bez lékařského předpisu.\n" +
////                    "\n" +
////                    "Účinky přípravku Olynth HA 0,1% a jiných současně užívaných léků se mohou navzájem ovlivňovat. Týká se to některých léků užívaných na zlepšení nálady (inhibitory monoaminooxidázy tranylcyprominového typu nebo tricyklická antidepresiva), které mohou působit na oběhový systém zvýšením krevního tlaku.\n" +
////                    "\n" +
////                    "Používání přípravku Olynth HA 0,1% s jídlem a pitím\n" +
////                    "Přípravek Olynth HA 0,1% lze používat nezávisle na stravovacích zvyklostech.\n" +
////                    "\n" +
////                    "Těhotenství a kojení\n" +
////                    "Poraďte se se svým lékařem nebo lékárníkem dříve, než začnete užívat jakýkoli lék. Použití přípravku Olynth HA 0,1% není v průběhu těhotenství a kojení doporučeno.\n" +
////                    "\n" +
////                    "Řízení dopravních prostředků a obsluha strojů\n" +
////                    "Při dlouhodobém podávání nebo při podání vyšší dávky přípravku Olynth HA 0,1% nelze vyloučit celkové účinky na kardiovaskulární nebo nervový systém. V takovém případě může být snížena schopnost řídit motorové vozidlo nebo obsluhovat stroje.','Přípravek Olynth HA 0,1% používejte vždy přesně podle pokynů lékaře. Pokud si nejste jistý/á, poraďte se se svým lékařem nebo lékárníkem.\n" +
////                    "\n" +
////                    "Pokud lékař nedoporučí jinak, dospělým a dětem od 7 let se přípravek podává podle potřeby, nejvýše však 3× denně 1 dávka přípravku Olynth HA 0,1% nosního spreje do každé nosní dírky. Dávkování závisí na citlivosti nemocného a na účinku přípravku. Bez porady s lékařem nepoužívejte přípravek Olynth HA 0,1% déle než 5 dní. Jestliže se do této doby příznaky onemocnění nezlepší, nebo se naopak zhoršují, nebo se vyskytnou nežádoucí účinky nebo nějaké neobvyklé reakce, poraďte se o dalším používání přípravku s lékařem. Delší používání než 5 dní je možné pouze po poradě s lékařem.\n" +
////                    "\n" +
////                    "Přípravek můžete opět používat pouze po několikadenní přestávce.\n" +
////                    "\n" +
////                    "Používání přípravku Olynth HA 0,1% u chronické rýmy je povoleno pouze pod dohledem lékaře vzhledem k nebezpečí ztenčení nosní sliznice.\n" +
////                    "\n" +
////                    "Návod k použití\n" +
////                    "Odstraňte ochranný kryt. Před prvním použitím několikrát stiskněte pumpičku, dokud se poprvé neobjeví souvislý obláček aerosolu. Nosní sprej je tímto připraven k dalšímu použití. Při aplikaci stiskněte pouze jednou pumpičku a lehce vdechněte nosem. Lahvičku s přípravkem držte svisle. Nevstřikujte si dávku přípravku držením nosního spreje ve vodorovné poloze nebo v poloze dnem lahvičky vzhůru. Po použití nasaďte opět na pumpičku ochranný kryt.\n" +
////                    "\n" +
////                    "Jestliže jste použil/a více přípravku Olynth HA 0,1%, než jste měl/a\n" +
////                    "Při předávkování nebo neúmyslném požití přípravku může dojít k těmto příznakům: rozšíření zornic (mydriáze), nucení na zvracení, zvracení, modravému zabarvení rtů (cyanóze), horečce, křečím, oběhovým poruchám (zrychlení tepu, poruchám srdečního rytmu, oběhovému selhání, srdeční zástavě, zvýšení krevního tlaku), plicním poruchám (otoku plic, poruchám dýchání) a poruchám nálady.\n" +
////                    "\n" +
////                    "Může dojít též k ospalosti, snížení tělesné teploty, zpomalení srdečního tepu, poklesu krevního tlaku až napodobující šok, přerušení dýchání a bezvědomí.\n" +
////                    "\n" +
////                    "Při těchto obtížích je nutné ihned vyhledat lékaře! Jako první pomoc je možné podat živočišné uhlí.\n" +
////                    "\n" +
////                    "Máte-li jakékoli další otázky, týkající se používání tohoto přípravku, zeptejte se svého lékaře nebo lékárníka.','Podobně jako všechny léky, může mít i přípravek Olynth HA 0,1% nežádoucí účinky, které se však nemusí vyskytnout u každého.\n" +
////                    "\n" +
////                    "Dýchací systém\n" +
////                    "U citlivých nemocných může přípravek Olynth HA 0,1% způsobit příznaky dráždění sliznice přechodného a mírného charakteru, které se projeví jako pálení v nose nebo suchost nosní sliznice. V ojedinělých případech může dojít ke zvýšenému otoku nosní sliznice po odeznění účinku léku.\n" +
////                    "\n" +
////                    "Nervový systém\n" +
////                    "Ve velmi vzácných případech se mohou vyskytnout bolesti hlavy, nespavost nebo únava.\n" +
////                    "\n" +
////                    "Oběhový systém\n" +
////                    "Ojediněle až vzácně se mohou dostavit účinky přípravku na srdce a krevní oběh, jako např. bušení srdce, zrychlený tep a zvýšení krevního tlaku.\n" +
////                    "\n" +
////                    "Dlouhodobé nebo časté používání vyšších dávek přípravku Olynth HA 0,1% může vést k pocitům pálení v nose nebo suchosti sliznic, ale i k překrvení sliznic, které se zhoršuje s nadměrným používáním léku (tzv. rhinitis medicamentosa). K tomuto účinku může dojít i 5 dnů po ukončení léčby – dlouhodobé podávání přípravku může vést k trvalému poškození sliznice, pocitu sucha a šimrání v nose s tvorbou strupů a drobným krvácením po jejich odloučení (rhinitis sicca).\n" +
////                    "\n" +
////                    "Pokud se kterýkoli z nežádoucích účinků vyskytne v závažné míře, nebo pokud si všimnete jakýchkoli nežádoucích účinků, které nejsou uvedeny v této příbalové informaci, prosím, sdělte to svému lékaři nebo lékárníkovi.', 'Uchovávejte mimo dosah a dohled dětí.\n" +
////                    "\n" +
////                    "Uchovávejte při teplotě do 25 °C.\n" +
////                    "\n" +
////                    "Nepoužívejte po uplynutí doby použitelnosti, uvedené na obalu. Doba použitelnosti se vztahuje k poslednímu dni uvedeného měsíce.\n" +
////                    "\n" +
////                    "Z hygienických důvodů se má přípravek zlikvidovat po 12 měsících od prvního použití.\n" +
////                    "\n" +
////                    "Léčivé přípravky se nesmí vyhazovat do odpadních vod nebo domácího odpadu. Zeptejte se svého lékárníka, jak máte likvidovat přípravky, které již nepotřebujete. Tato opatření pomáhají chránit životní prostředí.', 'Co přípravek Olynth HA 0,1% obsahuje\n" +
////                    "\n" +
////                    "Léčivou látkou je xylometazolini hydrochloridum (xylometazolin-hydrochlorid) 1 mg (0,1%) v 1 ml roztoku.\n" +
////                    "Pomocnými látkami jsou: natrium-hyaluronát, glycerol, dihydrát hydrogenfosfo\u00ADrečnanu sodného, dihydrát dihydrogenfos\u00ADforečnanu sodného, chlorid sodný, sorbitol, voda na injekci.\n" +
////                    "Jak přípravek Olynth HA 0,1% vypadá a co obsahuje toto balení\n" +
////                    "Přípravek je vyráběn v lékové formě nosního spreje, je balen ve skleněné lahvičce s dávkovacím zařízením.\n" +
////                    "\n" +
////                    "Velikost balení: 10 ml\n" +
////                    "\n" +
////                    "Držitel rozhodnutí o registraci\n" +
////                    "McNeil Products Limited\n" +
////                    "c/o Johnson & Johnson\n" +
////                    "Foundation Park, Roxborough Way\n" +
////                    "Maidenhead, Berkshire\n" +
////                    "SL6 3UG, Velká Británie\n" +
////                    "\n" +
////                    "Výrobce\n" +
////                    "URSAPHARM Arzneimittel GmbH\n" +
////                    "Industriestrasse 35\n" +
////                    "D-66129 Saarbrücken\n" +
////                    "Německo\n" +
////                    "\n" +
////                    "Další informace o tomto přípravku získáte u místního zástupce držitele rozhodnutí o registraci: Johnson & Johnson s.r.o., Karla Engliše 3201/6, 150 00 Praha 5, tel.: 227 012 111, www.jnjcz.cz\n" +
////                    "\n" +
////                    "Tento příbalový leták byl naposledy schválen 24.2.2010.')";
////
////    private static final String Ibalgin =
////            "INSERT INTO " + MEDICINE_TABLE_NAME + "(name, type, package_quantity) VALUES ('Ibalgin 400mg','Tablety','48')";
////    private static final String IbalginInfo =
////            "INSERT INTO " + MEDICINE_INFO_TABLE_NAME + "(description, warning, usage_instructions, side_effects, storage_instructions, other_information) VALUES " +
////                    "('Přípravek Ibalgin 400 obsahuje léčivou látku ibuprofen, která patří do skupiny tzv. nesteroidních protizánětlivých léčiv. Zabraňuje tvorbě tkáňových působků, tzv. prostaglandinů, které jsou odpovědné za vznik bolesti a zánětu a uvolňují se v místě poškození tkáně.\n" +
////                    "Ibuprofen zmírňuje bolest a zánět různého původu. Ibuprofen rovněž tlumí horečku, která provází např. nemoci z nachlazení.\n" +
////                    "Přípravek Ibalgin 400 se užívá např. při bolesti hlavy, zubů, zad, při bolestivé menstruaci, při bolesti svalů a kloubů provázející chřipková onemocnění, při poranění měkkých tkání, jako je pohmoždění a podvrtnutí.\n" +
////                    "Dále se Ibalgin 400 užívá při horečnatých stavech při chřipkových onemocněních a jako doplňková léčba horečnatých stavů při jiných onemocněních.\n" +
////                    "V případě zánětlivých a degenerativních onemocnění kloubů a páteře (artritidě a artróze) nebo měkkých tkání pohybového ústrojí provázených bolestivostí, zarudnutím, otokem a kloubní ztuhlostí se přípravek používá pouze na doporučení lékaře.\n" +
////                    "Přípravek je vzhledem k množství léčivé látky v jedné tabletě určen pro dospělé a dospívající od 12 let. Pro děti ve věku 6–12 let je vhodný přípravek s obsahem 200 mg ibuprofenu (Ibalgin 200), dětem do 6 let věku je určen ibuprofen v suspenzi (Ibalgin Baby).','Neužívejte přípravek Ibalgin 400\n" +
////                    "\n" +
////                    "jestliže jste alergický(á) na ibuprofen (léčivá látka přípravku Ibalgin 400) nebo na kteroukoli další složku tohoto přípravku (uvedenou v bodě 6),\n" +
////                    "jestliže jste alergický(á) na kyselinu acetylsalicylovou nebo některé jiné nesteroidní protizánětlivé léky, projevující se jako průduškové astma nebo kopřivka,\n" +
////                    "jestliže máte aktivní nebo opakující se vřed nebo krvácení do žaludku nebo dvanáctníku,\n" +
////                    "při krvácení nebo proděravení v zažívacím traktu způsobených nesteroidními protizánětlivými léky v minulosti,\n" +
////                    "jestliže trpíte poruchou krvetvorby nebo poruchou krevní srážlivosti,\n" +
////                    "jestliže máte závažné srdeční selhání.\n" +
////                    "Přípravek nesmí užívat ženy v třetím trimestru těhotenství.\n" +
////                    "\n" +
////                    "Upozornění a opatření\n" +
////                    "Před užitím přípravku Ibalgin 400 se poraďte se svým lékařem nebo lékárníkem, pokud:\n" +
////                    "\n" +
////                    "trpíte těžší poruchou ledvin nebo jater,\n" +
////                    "trpíte průduškovým astmatem i v klidovém stavu,\n" +
////                    "trpíte některým onemocněním pojivové tkáně (tzv. kolagenózy),\n" +
////                    "jste prodělal(a) vředovou chorobu žaludku nebo dvanáctníku,\n" +
////                    "trpíte zánětlivým vředovým onemocněním trávicího ústrojí jako např. Crohnova choroba nebo ulcerózní kolitida,\n" +
////                    "současně užíváte léky snižující srážení krve,\n" +
////                    "máte srdeční obtíže včetně srdečního selhání, anginy pectoris (bolest na hrudi), nebo pokud jste prodělali srdeční infarkt, jste po operaci srdce pomocí bypassu, trpíte onemocněním periferních tepen (špatný krevní oběh v nohou kvůli zúženým nebo ucpaným tepnám) nebo jste prodělali jakýkoli druh cévní mozkové příhody (včetně mozkové „mini“ mrtvice neboli tranzitorní ischemické ataky „TIA“),\n" +
////                    "máte vysoký krevní tlak, cukrovku, vysoký cholesterol, máte v rodinné anamnéze srdeční onemocnění nebo cévní mozkovou příhodu, nebo pokud jste kuřák/kuřačka,\n" +
////                    "Pokud se Vás týká některý z výše uvedených bodů, užívejte přípravek pouze na doporučení lékaře.\n" +
////                    "\n" +
////                    "Protizánětlivá/analgetická léčiva, jako je ibuprofen, mohou působit mírné zvýšení rizika srdečních nebo cévních mozkových příhod, především pokud jsou užívána ve vysokých dávkách. Proto nepřekračujte doporučenou dávku ani délku léčby.\n" +
////                    "Přípravek Ibalgin 400 by se neměl užívat současně s jinými nesteroidními protizánětlivými léky.\n" +
////                    "U pacientů, u kterých se v minulosti vyskytl vřed žaludku nebo dvanáctníku, především pokud byla vředová choroba komplikována krvácením nebo perforací zažívacího traktu, dále u starších pacientů a také u pacientů vyžadujících současnou léčbu nízkými dávkami kyseliny acetylsalicylové, by mělo být zváženo podání léků chránících sliznici žaludku a dvanáctníku.\n" +
////                    "V průběhu léčení se ojediněle může vyskytnout krvácení ze zažívacího traktu, vřed nebo perforace. Vznik těchto stavů může, ale nemusí být provázen varovnými příznaky. Riziko vzniku krvácení, vředu nebo perforace v zažívacím traktu se zvyšuje se stoupající dávkou léku. Závažnost těchto poruch je obecně vyšší u starších pacientů.\n" +
////                    "Pokud se u pacienta léčeného přípravkem Ibalgin 400 objeví krvácení ze zažívacího traktu, musí být přípravek Ibalgin 400 vysazen.\n" +
////                    "Během léčby není vhodné kouřit.\n" +
////                    "\n" +
////                    "Přípravek Ibalgin 400 není vzhledem k množství léčivé látky v jedné tabletě určen dětem do 12 let.\n" +
////                    "\n" +
////                    "Dospívající (12–18 let)\n" +
////                    "U dehydrovaných dospívajících existuje riziko poruchy funkce ledvin.\n" +
////                    "\n" +
////                    "Další léčivé přípravky a přípravek Ibalgin 400\n" +
////                    "Informujte svého lékaře nebo lékárníka o všech lécích, které užíváte, které jste v nedávné době užíval(a) nebo které možná budete užívat.\n" +
////                    "Zejména se poraďte před podáním přípravku s lékařem, pokud užíváte:\n" +
////                    "\n" +
////                    "Antikoagulancia/antiagregancia (tj. přípravky ředící krev/bránící jejímu srážení, např. kyselina acetylsalicylová, warfarin, ticlopidin). Léky snižující srážlivost krve zvyšují riziko krvácení. Kyselina acetylsalicylová a jiné protizánětlivé léky, kortikoidy a léky proti depresi ze skupiny SSRI zvyšují riziko vzniku nežádoucích účinků v oblasti zažívacího ústrojí včetně krvácení a vzniku žaludečního vředu.\n" +
////                    "Léčiva snižující vysoký krevní tlak (ACE-inhibitory, jako je captopril, beta-blokátory jako atenolol, antagonisté receptoru angiotensinu-II, jako je losartan).\n" +
////                    "Ibuprofen může zvýšit hladinu lithia, digoxinu a fenytoinu (léku užívaného k léčbě epilepsie) v krvi, a tím zvýšit jejich nežádoucí účinky.\n" +
////                    "Ibuprofen může snižovat účinek léků používaných k léčení vysokého krevního tlaku a močopudných léků.\n" +
////                    "Ibuprofen snižuje účinek léků snižujících hladinu kyseliny močové v krvi (např. probenecid, sulfinpyrazon).\n" +
////                    "Léčivá látka ibuprofen může zvýšit toxicitu metotrexátu (léku proti rakovině a některým kloubním onemocněním) a baklofenu (léku snižujícího svalové napětí).\n" +
////                    "Ibuprofen může zvýšit poškození ledvin způsobené podáváním cyklosporinu (léku užívaného po transplantaci).\n" +
////                    "Při současném podávání s kalium šetřícími močopudnými léky může dojít ke zvýšení hladiny draslíku v krvi.\n" +
////                    "Při současném podávání chinolonových antibiotik a nesteroidních antirevmatik se může zvýšit riziko vzniku křečí.\n" +
////                    "Některá další léčiva mohou rovněž ovlivňovat nebo být ovlivňována léčbou přípravkem Ibalgin 400. Proto byste se vždy měli poradit se svým lékařem nebo lékárníkem, než začnete přípravek Ibalgin 400 užívat s jinými léčivy.\n" +
////                    "\n" +
////                    "Přípravek Ibalgin 400 s jídlem, pitím a alkoholem\n" +
////                    "Potahované tablety se polykají celé, zapijí se dostatečným množstvím tekutiny. Jestliže se během léčby objeví zažívací obtíže, lék užívejte během jídla nebo jej zapijte mlékem. Během léčby není vhodné pít alkoholické nápoje.\n" +
////                    "\n" +
////                    "Těhotenství, kojení a plodnost\n" +
////                    "Pokud jste těhotná nebo kojíte, domníváte se, že můžete být těhotná, nebo plánujete otěhotnět, poraďte se se svým lékařem nebo lékárníkem dříve, než začnete tento přípravek užívat.\n" +
////                    "V prvním a druhém trimestru těhotenství a při kojení užívejte přípravek pouze na výslovné doporučení lékaře. Ženy, které chtějí otěhotnět, se musí o užívání přípravku poradit s lékařem.\n" +
////                    "Přípravek nesmí užívat ženy v třetím trimestru těhotenství.\n" +
////                    "\n" +
////                    "Řízení dopravních prostředků a obsluha strojů\n" +
////                    "Přípravek neovlivňuje pozornost.','Vždy užívejte tento přípravek přesně v souladu s příbalovou informací nebo podle pokynů svého lékaře nebo lékárníka. Pokud si nejste jistý(á), poraďte se se svým lékařem nebo lékárníkem.\n" +
////                    "\n" +
////                    "I. Doporučené dávkování při bolestech, při horečnatých onemocněních:\n" +
////                    "Dospělí a dospívající nad 12 let užívají obvykle 1 potahovanou tabletu Ibalgin 400 3krát denně, a to 1 potahovanou tabletu jednorázově nebo podle potřeby, nejvýše 3 potahované tablety denně.\n" +
////                    "Odstup mezi jednotlivými dávkami je nejméně 4 hodiny.\n" +
////                    "\n" +
////                    "Dospělí\n" +
////                    "Pokud se Vaše příznaky při léčbě přípravkem Ibalgin 400 zhorší nebo se nezlepší do 3 dnů v případě horečky a do 5 dnů v případě bolesti, poraďte se o dalším postupu s lékařem. Bez porady s lékařem neužívejte přípravek déle než 7 dní.\n" +
////                    "\n" +
////                    "Dospívající\n" +
////                    "Pokud je u dospívajících nutné podávat tento léčivý přípravek déle než 3 dny nebo pokud se zhorší příznaky onemocnění, je třeba vyhledat lékaře.\n" +
////                    "\n" +
////                    "II. Doporučené dávkování při léčbě zánětlivých a degenerativních onemocnění kloubů a páteře nebo mimokloubního revmatismu\n" +
////                    "Přípravek se u těchto onemocnění užívá na doporučení lékaře, který stanoví i dávkování. Dospělí a dospívající od 12 let obvykle užívají 1–2 potahované tablety Ibalgin 400 3krát denně. Odstup mezi jednotlivými dávkami je nejméně 4 hodiny. Účinek tablet můžete podpořit nanesením krému nebo gelu s obsahem ibuprofenu na postižené místo (např. klouby, svaly, šlachy, záda). Pokud se při léčení revmatických onemocnění, nedostaví v průběhu jednoho až dvou týdnů zlepšení, poraďte se s lékařem o dalším postupu.\n" +
////                    "\n" +
////                    "Potahované tablety se polykají celé, zapijí se dostatečným množstvím tekutiny. Jestliže se během léčby objeví zažívací obtíže, lék užívejte během jídla nebo jej zapijte mlékem.\n" +
////                    "\n" +
////                    "Jestliže jste užil(a) více přípravku Ibalgin 400, než jste měl(a)\n" +
////                    "Při předávkování nebo náhodném požití potahovaných tablet dítětem vyhledejte lékaře.\n" +
////                    "\n" +
////                    "Jestliže jste zapomněl(a) užít přípravek Ibalgin 400\n" +
////                    "Pokud zapomenete vzít si tabletu přípravku Ibalgin 400, užijte ji, jakmile si vzpomenete. Mezi jednotlivými dávkami musí být minimální časový odstup 4 hodin. Nezdvojnásobujte následující dávku, abyste nahradil(a) vynechanou dávku.\n" +
////                    "\n" +
////                    "Máte-li jakékoli další otázky týkající se užívání tohoto přípravku, zeptejte se svého lékaře nebo lékárníka.','Podobně jako všechny léky může mít i tento přípravek nežádoucí účinky, které se ale nemusí vyskytnout u každého.\n" +
////                    "Při užívání ibuprofenu (léčivé látky přípravku Ibalgin 400) se mohou vyskytnout následující nežádoucí účinky, které jsou seřazeny dle četnosti výskytu:\n" +
////                    "\n" +
////                    "Velmi časté (mohou postihnout více než 1 pacienta z 10): pocit na zvracení, zvracení, pálení žáhy, průjem, zácpa, nadýmání.\n" +
////                    "\n" +
////                    "Časté (mohou postihnout až 1 pacienta z 10): bolest v nadbřišku.\n" +
////                    "\n" +
////                    "Méně časté (mohou postihnout až 1 pacienta ze 100): bolest hlavy, závrať.\n" +
////                    "\n" +
////                    "Vzácné (mohou postihnout až 1 pacienta z 1 000): zánět sliznice žaludku, žaludeční nebo dvanáctníkový vřed, krvácení z trávicího traktu (projevuje se jako černá stolice v důsledku natrávené krve nebo krev ve stolici)1, proděravění sliznice trávicího traktu, alergické reakce jako horečka, vyrážka, poškození jater, srdeční selhávání, otoky, sterilní zánět mozkových plen2, zúžení průdušek3, zánět slinivky břišní, poruchy vidění a vnímaní barev, tupozrakost, poškození jaterních funkcí4.\n" +
////                    "\n" +
////                    "Velmi vzácné (mohou postihnout až 1 pacienta z 10 000): zánět sliznice ústní dutiny provázený vznikem vředů (ulcerózní stomatitida), nové vzplanutí zánětlivých onemocnění s tvorbou vředů na sliznici trávicího traktu (Crohnova choroba, ulcerózní kolitida), pokles počtu krvinek nebo krevních destiček, zadržení vody a/nebo soli s otoky, nespavost, deprese, emoční labilita, palpitace (bušení srdce vnímané pacientem), snížení krevního tlaku, vysoký krevní tlak, zánět močového měchýře, přítomnost krve v moči, porucha funkce ledvin, zánět ledvin, nefrotický syndrom (soubor příznaků při onemocnění ledvin), puchýřovité kožní reakce.\n" +
////                    "\n" +
////                    "Není známo (z dostupných údajů nelze určit): porucha sluchu.\n" +
////                    "\n" +
////                    "1 Tyto nežádoucí účinky mohou, ale nemusí být provázeny varovnými příznaky. Riziko vzniku těchto nežádoucích účinků stoupá se zvyšující se dávkou, je vyšší u starších pacientů, u osob, u kterých se v minulosti vyskytl žaludeční či dvanáctníkový vřed (zejména spojený s krvácením nebo perforací sliznice žaludku či dvanáctníku), dále u pacientů léčených dlouhodobě kyselinou acetylsalicylovou pro snížení srážlivosti krve. U těchto pacientů může lékař navrhnout současné podávání léčiv, které chrání sliznici trávicího traktu.\n" +
////                    "2 Zejména u pacientů s onemocněním pojiva (systémový lupus erythematodes a některé typy kolagenóz).\n" +
////                    "3 U pacientů s průduškovým astmatem.\n" +
////                    "4 Poškození jaterních funkcí je obvykle přechodné.\n" +
////                    "\n" +
////                    "Léky ze skupiny nesteroidních protizánětlivých léčiv (mezi která Ibalgin 400 patří) mohou být spojeny s mírným zvýšením rizika srdečních nebo mozkových cévních příhod.\n" +
////                    "\n" +
////                    "Jestliže se objeví kopřivka, náhle vzniklý otok kolem očí, pocit tísně na hrudníku nebo obtíže s dechem, dále bolesti v nadbřišku nebo poruchy vidění, případně při objevení se krvácení z trávicího traktu (zvracení krve nebo dočerna zbarvená stolice), přerušte užívání přípravku a okamžitě vyhledejte lékaře.\n" +
////                    "\n" +
////                    "Hlášení nežádoucích účinků\n" +
////                    "Pokud se u Vás vyskytne kterýkoli z nežádoucích účinků, sdělte to svému lékaři nebo lékárníkovi.\n" +
////                    "Stejně postupujte v případě jakýchkoli nežádoucích účinků, které nejsou uvedeny v této příbalové informaci. Nežádoucí účinky můžete hlásit také přímo na adresu:\n" +
////                    "Státní ústav pro kontrolu léčiv\n" +
////                    "Šrobárova 48\n" +
////                    "100 41 Praha 10\n" +
////                    "webové stránky: http://www.sukl.cz/nahlasit-nezadouci-ucinek\n" +
////                    "Nahlášením nežádoucích účinků můžete přispět k získání více informací o bezpečnosti tohoto přípravku.', 'Uchovávejte tento přípravek mimo dohled a dosah dětí.\n" +
////                    "Nepoužívejte tento přípravek po uplynutí doby použitelnosti uvedené na obalu. Doba použitelnosti se vztahuje k poslednímu dni uvedeného měsíce.\n" +
////                    "Blistr: Uchovávejte při teplotě do 25 °C v původním obalu, aby byl přípravek chráněn před světlem a vlhkostí.\n" +
////                    "Lahvička: Uchovávejte při teplotě do 25 °C v dobře uzavřené lahvičce, lahvičku uchovávejte v krabičce, aby byl přípravek chráněn před světlem a vlhkostí.\n" +
////                    "\n" +
////                    "Nevyhazujte žádné léčivé přípravky do odpadních vod nebo domácího odpadu. Zeptejte se svého lékárníka, jak naložit s přípravky, které již nepoužíváte. Tato opatření pomáhají chránit životní prostředí.', 'Co přípravek Ibalgin 400 obsahuje\n" +
////                    "Léčivou látkou je ibuprofenum 400 mg v 1 potahované tabletě.\n" +
////                    "\n" +
////                    "Pomocnými látkami jsou kukuřičný škrob, předbobtnalý kukuřičný škrob, sodná sůl karboxymetylškrobu (typ C), kyselina stearová, mastek, koloidní bezvodý oxid křemičitý, hypromelóza 2910/3, makrogol 6000, oxid titaničitý (E171), sodná sůl erythrosinu (E127), simetikonová emulze SE 4.\n" +
////                    "\n" +
////                    "Jak přípravek Ibalgin 400 vypadá a co obsahuje toto balení\n" +
////                    "Ibalgin 400 jsou růžové potahované tablety.\n" +
////                    "Velikost balení: 10, 12, 24, 30, 36, 48 a 100 potahovaných tablet.\n" +
////                    "Na trhu nemusí být všechny velikosti balení.\n" +
////                    "\n" +
////                    "Držitel rozhodnutí o registraci a výrobce:\n" +
////                    "Zentiva, k. s., Praha, Česká republika\n" +
////                    "\n" +
////                    "Tato příbalová informace byla naposledy revidována:\n" +
////                    "9.9.2016')";
////
////    private static final String Sinecod =
////            "INSERT INTO " + MEDICINE_TABLE_NAME + "(name, type, package_quantity) VALUES ('Sinecod','Sirup','400ml')";
////    private static final String SinecodInfo =
////            "INSERT INTO " + MEDICINE_INFO_TABLE_NAME + "(description, warning, usage_instructions, side_effects, storage_instructions, other_information) VALUES " +
////                    "('Sinecod obsahuje léčivou látku butamirát citrát, která patří do skupiny léků nazývaných antitusika – léky tlumící\n" +
////                    "kašel.\n" +
////                    "Sinecod se používá k léčbě suchého, dráždivého kašle různého původu.\n" +
////                    "Bez porady slékařem se přípravek užívá ke krátkodobé léčbě akutního kašle, např. při akutních infekcích horních\n" +
////                    "a dolních cest dýchacích, průdušnice a průdušek.\n" +
////                    "U chronických onemocnění dýchacích cest provázených kašlem se přípravek užívá pouze na doporučení lékaře.\n" +
////                    "Sinecod 0,5 % perorální kapky, roztok mohou užívat děti od 2 měsíců.\n" +
////                    "Sinecod 0,15 % sirup mohou užívat dospělí, dospívající a děti od 3 let.\n" +
////                    "Sinecod 50 mg tablety s prodlouženým uvolňováním mohou užívat dospělí a dospívající od 12 let.','Neužívejte Sinecod:\n" +
////                    "\uF0A7 jestliže jste alergický(á) na léčivou látku nebo na kteroukoli další složku tohoto přípravku (uvedenou\n" +
////                    "v bodě 6).\n" +
////                    "Děti\n" +
////                    "Bez porady s lékařem nepodávejte Sinecod:\n" +
////                    "\uF0A7 Sinecod 0,5 % perorální kapky, roztok mohou užívat děti od 2 měsíců;\n" +
////                    "\uF0A7 Sinecod 0,15 % sirup mohou užívat dospělí, dospívající a děti od 3 let;\n" +
////                    "\uF0A7 Sinecod 50 mg tablety s prodlouženým uvolňováním mohou užívat dospělí a dospívající od 12 let.\n" +
////                    "Další léčivé přípravky a Sinecod\n" +
////                    "Neužívejte současně jiné léky na léčbu kašle (především léky podporující vykašlávání hlenu, tzv. expektorancia),\n" +
////                    "neboť kombinace těchto léků může vést k hromadění hlenu v dýchacích cestách.\n" +
////                    "Informujte svého lékaře nebo lékárníka o všech lécích, které užíváte, nebo které jste v nedávné době užíval(a)\n" +
////                    "včetně léků a doplňků stravy, které jsou k dostání bez lékařského předpisu.\n" +
////                    "Těhotenství a kojení:\n" +
////                    "Pokud jste těhotná nebo kojíte, domníváte se, že můžete být těhotná, nebo plánujete otěhotnět, poraďte se se\n" +
////                    "svým lékařem nebo lékárníkem dříve, než začnete tento přípravek používat.\n" +
////                    "Jestliže jste těhotná nebo kojíte, neužívejte Sinecod, pokud Vám to nedoporučil Váš lékař.\n" +
////                    "Řízení dopravních prostředků a obsluha strojů\n" +
////                    "Sinecod může ve vzácných případech způsobit u některých lidí, že se stanou méně pozorní než obvykle. Pokud\n" +
////                    "se Vám toto stane, měli byste být opatrní, pokud řídíte nebo obsluhujete stroje.\n" +
////                    "Vaše dítě by se mělo vyvarovat jízdy na kole nebo skútru, pokud používá Sinecod.\n" +
////                    "Přípravky Sinecod 0,5 % perorální kapky, roztok a Sinecod 0,15 % sirup obsahují sorbitol a ethanol:\n" +
////                    "\uF0A7 sorbitol (E 420): pokud Vám Váš lékař řekl, že nesnášíte některé cukry, poraďte se se svým lékařem,\n" +
////                    "než začnete tento přípravek užívat;\n" +
////                    "\uF0A7 ethanol: tento léčivý přípravek obsahuje malé množství ethanolu (alkoholu), méně než 100 mg v jedné\n" +
////                    "dávce.\n" +
////                    "Přípravek Sinecod 50 mg tablety s prodlouženým uvolňováním obsahuje monohydrát laktosy:\n" +
////                    "\uF0A7 pokud Vám Váš lékař řekl, že nesnášíte některé cukry, poraďte se se svým lékařem, než začnete tento\n" +
////                    "přípravek užívat','Vždy užívejte přípravek Sinecod přesně v souladu s příbalovou informací nebo podle pokynů svého lékaře nebo\n" +
////                    "lékárníka. Pokud si nejste jistý(á), poraďte se se svým lékařem nebo lékárníkem.\n" +
////                    "Bez porady s lékařem se přípravek užívá ke krátkodobé léčbě akutního kašle (viz bod 1).\n" +
////                    "Doporučené dávkování je následující:\n" +
////                    "Použití u dětí\n" +
////                    "Sinecod 0,5 % perorální kapky, roztok\n" +
////                    "Bez porady slékařem nepodávejte Sinecod dětem mladším 2 let.\n" +
////                    "Děti od 2 měsíců do jednoho roku: 10 kapek 4 krát denně.\n" +
////                    "Děti 1 – 3 roky: 15 kapek 4 krát denně.\n" +
////                    "Děti starší než 3 roky: 25 kapek 4 krát denně.\n" +
////                    "Sinecod 0,15 % sirup (s odměrkou)\n" +
////                    "Děti 3 - 6 let: 5 ml 3 krát denně.\n" +
////                    "Děti 6 – 12 let: 10 ml 3 krát denně.\n" +
////                    "K odměření dávky použijte přiloženou odměrku. Po každém použití umyjte a vysušte odměrku.\n" +
////                    "Nikdy nevracejte sirup z odměrky zpět do lahvičky.\n" +
////                    "Dávkování pro dospělé a dospívající od 12 let\n" +
////                    "Sinecod 0,15 % sirup (s odměrkou)\n" +
////                    "Dospívající starší 12 let: 15 ml 3 krát denně.\n" +
////                    "Dospělí: 15 ml 4 krát denně.\n" +
////                    "K odměření dávky použijte přiloženou odměrku. Po každém použití umyjte a vysušte odměrku.\n" +
////                    "Nikdy nevracejte sirup z odměrky zpět do lahvičky.\n" +
////                    "Sinecod 50 mg tablety s prodlouženým uvolňováním\n" +
////                    "Dospívající od 12 let: 1 tableta 1 – 2 krát denně.\n" +
////                    "Dospělí: 1 tableta 2 – 3 krát denně. Jednotlivé dávky se užívají v intervalu 8 – 12 hodin.\n" +
////                    "Tablety se polykají celé.\n" +
////                    "Pokud se u Vás příznaky zhorší či se nezlepší do 5 dnů (u dětí mladších 12 let do 3 dnů), poraďte se s lékařem.\n" +
////                    "Bez doporučení lékaře neužívejte Sinecod po dobu delší než 7 dní. Pokud během této doby nedojde k úplnému\n" +
////                    "vymizení příznaků nebo pokud je onemocnění doprovázeno horečkou či se objeví jakékoliv jiné obtíže, poraďte\n" +
////                    "se slékařem.\n" +
////                    "U chronických onemocnění provázených kašlem se přípravek užívá pouze na doporučení lékaře, který Vám sdělí,\n" +
////                    "jakou dávku a jak dlouho máte přípravek užívat.\n" +
////                    "Jestliže jste užil(a) více přípravku Sinecod, než jste měl(a):\n" +
////                    "Jestliže jste užil(a) příliš mnoho přípravku, oznamte to okamžitě svému lékaři.\n" +
////                    "Jestliže jste zapomněl(a) užít přípravek Sinecod\n" +
////                    "Jestliže jste zapomněl(a) užít pravidelnou dávku přípravku, vezměte si ji, jakmile si vzpomenete. Nezdvojujte\n" +
////                    "následující dávku, abyste nahradil(a) vynechanou dávku','Podobně jako všechny léky, může mít i tento přípravek nežádoucí účinky, které se ale nemusí vyskytnout\n" +
////                    "u každého.\n" +
////                    "Ve vzácných případech (mohou postihnout 1 až 10 pacientů z 10 000) se mohou vyskytnout:\n" +
////                    "\uF0A7 ospalost, kožní vyrážka, nevolnost a průjem.\n" +
////                    "Tyto nežádoucí účinky by měly odeznít po snížení dávky nebo ukončení léčby. Pokud tyto účinky přetrvávají,\n" +
////                    "přestaňte užívat Sinecod a poraďte se s lékařem nebo lékárníkem.\n" +
////                    "Hlášení nežádoucích účinků\n" +
////                    "Pokud se u Vás vyskytne kterýkoli z nežádoucích účinků, sdělte to svému lékaři nebo lékárníkovi. Stejně\n" +
////                    "postupujte v případě jakýchkoli nežádoucích účinků, které nejsou uvedeny v této příbalové informaci. Nežádoucí\n" +
////                    "účinky můžete hlásit také přímo na adresu: Státní ústav pro kontrolu léčiv, Šrobárova 48, 100 41, Praha 10,\n" +
////                    "webové stránky: www.sukl.cz/nahlasit-nezadouci-ucinek. Nahlášením nežádoucího účinku můžete přispět\n" +
////                    "k získání více informací o bezpečnosti tohoto přípravku.', 'Uchovávejte tento přípravek mimo dohled a dosah dětí.\n" +
////                    "Sinecod 0,5 % perorální kapky, roztok a Sinecod 0,15 % sirup\n" +
////                    "Uchovávejte při teplotě do 30 °C v původním obalu.\n" +
////                    "Sinecod 50 mg tablety s prodlouženým uvolňováním\n" +
////                    "Uchovávejte při teplotě do 25 °C v původním obalu.\n" +
////                    "Nepoužívejte tento přípravek po uplynutí doby použitelnosti uvedené na obalu za EXP/Použitelné do:. Doba\n" +
////                    "použitelnosti se vztahuje k poslednímu dni uvedeného měsíce.\n" +
////                    "Nevyhazujte žádné léčivé přípravky do odpadních vod nebo domácího odpadu. Zeptejte se svého lékárníka, jak\n" +
////                    "naložit s přípravky, které již nepoužíváte. Tato opatření pomáhají chránit životní prostředí.', 'Co Sinecod obsahuje\n" +
////                    "Sinecod 0,5 % perorální kapky, roztok\n" +
////                    "\uF0A7 Léčivou látkou je butamirati citras 5 mg v 1ml.\n" +
////                    "\uF0A7 Pomocnými látkami jsou: krystalizující sorbitol 70 %, glycerol 85 %, sodná sůl sacharinu, vanilin, kyselina\n" +
////                    "benzoová, ethanol 96 % (v/v), roztok hydroxidu sodného 300 g/l, čištěná voda.\n" +
////                    "Sinecod 0,15 % sirup\n" +
////                    "\uF0A7 Léčivou látkou je butamirati citras 15 mg v 10 ml.\n" +
////                    "\uF0A7 Pomocnými látkami jsou: krystalizující sorbitol 70 %, glycerol 85 %, sodná sůl sacharinu, vanilin,\n" +
////                    "kyselina benzoová, ethanol 96 % (v/v), roztok hydroxidu sodného 300 g/l, čištěná voda.\n" +
////                    "Sinecod 50 mg tablety s prodlouženým uvolňováním\n" +
////                    "\uF0A7 Léčivou látkou je butamirati citras: 1 tableta s prodlouženým uvolňováním obsahuje butamirati citras\n" +
////                    "50 mg.\n" +
////                    "\uF0A7 Pomocnými látkami jsou monohydrát laktosy, kyselina vinná, povidon 30,\n" +
////                    "hydroxypropylmethylcelulosa, kopolymer MA/MAA 1:1, koloidní bezvodý oxid křemičitý, magnesiumstearát,\n" +
////                    "hydrogenovaný ricinový olej, metakrylátový kopolymer typ E, monohydrát laktosy,\n" +
////                    "polysorbát 80, mastek, červený oxid železitý, oxid titaničitý, hlinitý lak erythrosinu.\n" +
////                    "Jak Sinecod vypadá a co obsahuje toto balení\n" +
////                    "Sinecod 0,5 % perorální kapky, roztok\n" +
////                    "Čirý, bezbarvý roztok s vůní po vanilce, sladké a lehce nahořklé chuti.\n" +
////                    "Hnědá lékovka s plastickým šroubovacím uzávěrem s kapací vložkou, krabička.\n" +
////                    "Velikost balení: 20 ml, 50 ml\n" +
////                    "Sinecod 0,15 % sirup\n" +
////                    "Čirý, bezbarvý roztok.\n" +
////                    "Lékovka z hnědého skla s HDPE vložkou a pojistným uzávěrem, odměrka, krabička.\n" +
////                    "Velikost balení: 100 ml, 200 ml\n" +
////                    "Sinecod 50 mg tablety s prodlouženým uvolňováním\n" +
////                    "Sinecod 50 mg tablety s prodlouženým uvolňováním jsou kulaté, bikonvexní, lesklé, rezavě hnědé potahované\n" +
////                    "tablety, na jedné straně označené znakem firmy Zyma, na druhé straně označené PT. Al/PVC/PE/PVDC blistr,\n" +
////                    "krabička.\n" +
////                    "Velikost balení: 10 tablet s prodlouženým uvolňováním.\n" +
////                    "Na trhu nemusí být všechny velikosti balení.\n" +
////                    "Držitel rozhodnutí o registraci\n" +
////                    "GlaxoSmithKline Consumer Healthcare Cz Republic s.r.o.\n" +
////                    "Hvězdova 1734/2c, 140 00 Praha 4 - Nusle, Česká republika\n" +
////                    "Výrobce:\n" +
////                    "GSK Consumer Healthcare GmbH & Co. KG, Barthstrasse 4, 80339 Mnichov, Německo\n" +
////                    "Tato příbalová informace byla naposledy revidována: 7. 3. 2017')";
////
////    private static final String Coldrex =
////            "INSERT INTO " + MEDICINE_TABLE_NAME + "(name, type, package_quantity) VALUES ('Coldrex Horký nápoj','prášek pro perorální roztok v sáčku','14 sáčků')";
////
////    private static final String ColdrexInfo =
////            "INSERT INTO " + MEDICINE_INFO_TABLE_NAME + "(description, warning, usage_instructions, side_effects, storage_instructions, other_information) VALUES " +
////                    "('Coldrex horký nápoj citron je přípravek proti chřipce a nachlazení, který obsahuje tři léčivé látky:\n" +
////                    "\n" +
////                    "paracetamol, léčivou látku s účinkem proti bolesti a horečce, která odstraňuje bolesti v krku, bolesti hlavy, svalů i kloubů a snižuje horečku\n" +
////                    "fenylefrin, dekongestant (látka snižující zduření nosní sliznice) ze skupiny sympatomimetik, který uvolňuje ucpaný nos a vedlejší nosní dutiny a tím pomáhá ulehčit dýchání,\n" +
////                    "kyselinu askorbovou (vitamin C), obvyklou složku léků proti chřipce a nachlazení, která je obsažená v léku kvůli tomu, aby pomohla nahradit vitamín C, jehož množství může být v počátečních stádiích chřipky a nachlazení nedostatečné.\n" +
////                    " \n" +
////                    "\n" +
////                    "Coldrex horký nápoj citron je vhodný pro dospělé a dospívající od 15 let (s hmotností nad 50 kg) k úlevě od nepříjemných příznaků chřipky a nachlazení. Coldrex horký nápoj citron odstraňuje bolesti v krku, uvolňuje nos i vedlejší nosní dutiny, odstraňuje bolesti hlavy, svalů i kloubů, snižuje horečku a doplňuje vitamin C.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Tento přípravek je určen pouze pro pacienty, kteří mají příznaky chřipky nebo nachlazení doprovázené ucpaným nosem. Pokud pacienti nemají ucpaný nos, doporučuje se použití jiných léčivých přípravků.\n" +
////                    "\n" +
////                    "Léčivé látky v přípravku Coldrex horký nápoj citron nezpůsobují ospalost.\n" +
////                    "\n" +
////                    "Pokud se do 3 dnů nebudete cítit lépe, nebo pokud se Vám přitíží, musíte se poradit s lékařem.','Neužívejte Coldrex horký nápoj citron, jestliže\n" +
////                    "\n" +
////                    "jste alergický(á) na paracetamol, fenylefrin-hydrochlorid, vitamin C nebo kteroukoli další složku tohoto přípravku (uvedenou v bodě 6)\n" +
////                    "máte některý z následujících zdravotních problémů: závažné onemocnění jater včetně akutní žloutenky, závažné zvýšení krevního tlaku, zvýšený nitrooční tlak (zelený zákal), užíváte nebo jste užíval(a) během posledních dvou týdnů léky zvané inhibitory monoaminooxidázy (léky k léčbě deprese)\n" +
////                    " \n" +
////                    "\n" +
////                    "Vzhledem k obsahu léčivých látek se tento přípravek nesmí podávat dětem a dospívajícím do 15 let.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Upozornění a opatření\n" +
////                    "\n" +
////                    "Coldrex horký nápoj citron obsahuje paracetamol.\n" +
////                    "Neužívejte tento lék s žádným dalším lékem obsahujícím paracetamol.\n" +
////                    "\n" +
////                    "Neužívejte tento přípravek bez doporučení lékaře, pokud máte problémy s konzumací alkoholu a/nebo trpíte jaterním onemocněním.\n" +
////                    "Coldrex horký nápoj citron obsahuje i dekongestant (látka snižující zduření nosní sliznice). Neužívejte tento lék s dalšími léky, které se používají na zmírnění chřipky, nachlazení nebo otoku nosní sliznice (ucpaného nosu).\n" +
////                    "Nesmíte překročit uvedenou dávku. Užívání vyšších než doporučených dávek může vést k riziku závažného poškození jater.\n" +
////                    "Coldrex horký nápoj citron není určený k dlouhodobé léčbě.\n" +
////                    " \n" +
////                    "\n" +
////                    "Při užívání přípravku Coldrex horký nápoj citron je třeba zvláštní opatrnosti jestliže:\n" +
////                    "\n" +
////                    "Máte některý z následujících zdravotních problémů:\n" +
////                    "onemocnění jater nebo ledvin, vysoký krevní tlak, zvýšenou činnost štítné žlázy, zvětšenou prostatu nebo potíže s močením, průduškové astma, cukrovku, onemocnění srdce, onemocnění postihující mozkové cévy, nedostatek enzymu zvaného glukóza-6-fosfát dehydrogenáza nebo hemolytickou anemii (chudokrevnost z rozpadu červených krvinek), feochromocytom (nádor dřeně nadledvin), některá cévní onemocnění (např. Raynaudův fenomén - špatné prokrvení určitých oblastí těla např. rukou), užíváte tzv. tricyklická antidepresiva (léky k léčbě deprese), betablokátory (léky ke snížení krevního tlaku a zpomalení srdeční činnosti), nebo jiné léky k léčbě vysokého krevního tlaku, srdeční glykosidy (digoxin), warfarin (ke snížení krevní srážlivosti), psychostimulancia (látky stimulující centrální nervový systém) nebo léky snižující chuť k jídlu.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Pokud trpíte některým z výše uvedených onemocnění, poraďte se před užíváním přípravku Coldrex horký nápoj citron s lékařem.\n" +
////                    "\n" +
////                    "máte závažnou infekci, protože ta může vést ke zvýšení rizika metabolické acidózy.\n" +
////                    "Příznaky metabolické acidózy jsou:\n" +
////                    "\n" +
////                    "- hluboké, zrychlené, obtížné dýchání\n" +
////                    "\n" +
////                    "- nevolnost (pocit na zvracení), zvracení\n" +
////                    "\n" +
////                    "- ztráta chuti k jídlu\n" +
////                    "\n" +
////                    "Pokud se u Vás objeví kombinace těchto příznaků, okamžitě kontaktujte lékaře.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Další léčivé přípravky a přípravek Coldrex horký nápoj citron\n" +
////                    "\n" +
////                    "Účinky přípravku Coldrex horký nápoj citron a účinky jiných současně užívaných léků se mohou vzájemně ovlivňovat.\n" +
////                    "\n" +
////                    "Pokud užíváte Coldrex horký nápoj citron, neužívejte současně jiné přípravky k léčbě chřipky a nachlazení nebo dekongestanty, zejména jiné přípravky obsahující paracetamol.\n" +
////                    "\n" +
////                    "Užívání přípravku s jakýmikoli souběžně užívanými léky je možné pouze po konzultaci s lékařem, neboť riziko postižení jater působením paracetamolu je zvýšené při současném užívání jiných léků, které mohou způsobovat poškození jater, nebo léků indukujících jaterní mikrosomální enzymy, např. některé léky proti epilepsii (fenobarbital) či antibiotika (rifampicin), a dále je nutná konzultace lékaře při současném užívání warfarinu vzhledem k možnému riziku zvýšení krvácení.\n" +
////                    "\n" +
////                    "Současné dlouhodobé užívání paracetamolu a kyseliny acetylsalicylové nebo dalších nesteroidních protizánětlivých přípravků (NSA) může vést k poškození ledvin.\n" +
////                    "\n" +
////                    "Poraďte se s lékařem nebo lékárníkem, jestliže užíváte metoklopramid nebo domperidon při nevolnosti a zvracení, cholestyramin ke snížení hladiny cholesterolu, zidovudin (k léčbě infekce HIV), isoniazid (k léčbě tuberkulózy), chloramfenikol (antibiotikum) nebo lamotrigin (k léčbě epilepsie).\n" +
////                    "\n" +
////                    "Fenylefrin obsažený v přípravku může při kombinaci s některými léky proti depresi způsobit zvýšení krevního tlaku nebo výskyt srdečních nežádoucích účinků. Při společném užívání se srdečními glykosidy (např. digoxinem) může dojít ke zvýšení rizika nepravidelného srdečního rytmu nebo srdeční příhody.\n" +
////                    "\n" +
////                    "Rovněž současné podávání s betablokátory (léky na snížení krevního tlaku a zpomalení srdeční činnosti) může snížením účinku těchto léčivých přípravků vést ke zvýšení krevního tlaku.\n" +
////                    "\n" +
////                    "Informujte svého lékaře nebo lékárníka o všech lécích, které užíváte, které jste v nedávné době užíval(a) nebo které možná budete užívat. To se týká i léků, které jsou dostupné bez lékařského předpisu.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Užívání přípravku Coldrex horký nápoj citron s jídlem, pitím a alkoholem\n" +
////                    "\n" +
////                    "Během léčby se nesmí konzumovat alkohol. Dlouhodobá konzumace alkoholu významně zvyšuje riziko poškození jater. Máte-li problémy s požíváním alkoholu, poraďte se před zahájením léčby s lékařem.\n" +
////                    "\n" +
////                    "Přípravek se může podávat nezávisle na jídle. Užívání po jídle může vést ke sníženému účinku paracetamolu.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Těhotenství a kojení\n" +
////                    "\n" +
////                    "Pokud jste těhotná nebo kojíte, domníváte se, že můžete být těhotná, nebo těhotenství plánujete, poraďte se se svým lékařem nebo lékárníkem dříve, než začnete tento přípravek užívat.\n" +
////                    "\n" +
////                    "Přípravek není vhodné užívat během těhotenství. Kojící ženy mohou přípravek užívat pouze na výslovné doporučení lékaře.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Řízení dopravních prostředků a obsluha strojů:\n" +
////                    "\n" +
////                    "Coldrex horký nápoj citron může způsobit závratě. Pokud vám přípravek způsobuje závratě, neměl(a) byste řídit dopravní prostředky ani obsluhovat stroje.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Přípravek Coldrex horký citron obsahuje sodík a sacharózu\n" +
////                    "\n" +
////                    "Přípravek obsahuje 135 mg sodíku v jednom sáčku. To je nutné vzít v úvahu u pacientů na dietě s nízkým obsahem sodíku.\n" +
////                    "\n" +
////                    "Přípravek obsahuje 2900 mg sacharózy v jednom sáčku. Pokud Vám Váš lékař řekl, že nesnášíte některé cukry, poraďte se se svým lékařem, než začnete tento přípravek užívat. ¨\n" +
////                    "\n" +
////                    "Je nutno vzít v úvahu u pacientů s cukrovkou.','Vždy užívejte tento přípravek přesně v souladu s příbalovou informací nebo podle pokynů svého lékaře nebo lékárníka. Pokud si nejste jistý(á), poraďte se se svým lékařem nebo lékárníkem.\n" +
////                    "\n" +
////                    "Vysypte obsah sáčku do sklenice a přelijte velmi horkou vodou. Míchejte, dokud se prášek nerozpustí. Podle chuti můžete přisladit medem nebo cukrem, případně dolít studenou vodou. Teplý nápoj vypijte.\n" +
////                    "\n" +
////                    "Dospělí (včetně starších osob) a dospívající od 15 let (s tělesnou hmotností od 50 kg): 1 sáček až 4x denně. Jednotlivé dávky užívejte vždy nejdříve po 4 hodinách. Neužívejte více než 4 sáčky za 24 hodin. Nepřekračujte doporučené dávkování. Užívání vyšších než doporučených dávek může vést k riziku závažného poškození jater.\n" +
////                    "\n" +
////                    "Pokud nedojde do 3 dnů ke zlepšení příznaků onemocnění (horečka, bolest) nebo se naopak obtíže zhorší, vyskytnou se nové příznaky nebo neobvyklé reakce, poraďte se o další léčbě s lékařem. Tento lék neužívejte déle než 7 dnů, neurčí-li lékař jinak.\n" +
////                    "\n" +
////                    "Děti a dospívající mladší 15 let: Tento přípravek není určen pro děti a dospívající mladší 15 let.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Jestliže jste užil(a) více přípravku Coldrex horký nápoj citron, než jste měl(a):\n" +
////                    "\n" +
////                    "V případě předávkování nebo náhodného požití přípravku dítětem vyhledejte ihned svého lékaře nebo nejbližší zdravotnické zařízení a řekněte přesně, jaké množství přípravku jste užil(a). Ukažte jim použité balení a tuto příbalovou informaci.\n" +
////                    "\n" +
////                    "Předávkování paracetamolem může způsobit vážné poškození až selhání funkce jater. V případě předávkování je nezbytná okamžitá lékařská pomoc, i když nejsou přítomny žádné příznaky předávkování.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Jestliže jste zapomněl(a) užít Coldrex horký nápoj citron:\n" +
////                    "\n" +
////                    "Vezměte si jednu dávku, když si vzpomenete. Nezdvojnásobujte následující dávku, abyste nahradil(a) vynechanou dávku. Neužívejte více než 1 dávku po 4 hodinách.','Podobně jako všechny léky, může mít i tento přípravek nežádoucí účinky, které se ale nemusí projevit u každého.\n" +
////                    "\n" +
////                    "Přestaňte užívat tento lék a okamžitě kontaktujte lékaře:\n" +
////                    "\n" +
////                    "jestliže se u Vás objeví alergická reakce, jako je kožní vyrážka nebo svědění, někdy doprovázená obtížemi s dýcháním nebo otokem rtů, jazyka, hrdla nebo obličeje.\n" +
////                    "jestliže se u Vás objeví kožní vyrážka, olupování kůže nebo vředy v ústech.\n" +
////                    "jestliže se u Vás po užití kyseliny acetylsalicylové nebo jiných nesteroidních protizánětlivých léků objevily potíže s dýcháním a podobná reakce se objevila i po podání tohoto přípravku.\n" +
////                    "jestliže u Vás došlo k nevysvětlené tvorbě modřin.\n" +
////                    "Tyto nežádoucí účinky jsou vzácné (objevují se u 1 až 10 osob z 10 000).\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Stejně postupujte i v případě:\n" +
////                    "\n" +
////                    "jestliže máte neobvykle rychlou tepovou frekvenci nebo pocit neobvykle rychlé nebo nepravidelné srdeční činnosti.\n" +
////                    "jestliže máte potíže s močení. Ty se s větší pravděpodobností objeví u mužů s hypertrofií (zbytněním) prostaty.\n" +
////                    "Četnost těchto nežádoucích účinků není známa.\n" +
////                    "\n" +
////                    "Velmi vzácně byly hlášeny případy závažných kožních reakcí (objevují se u méně než 1 osoby z 10 000).\n" +
////                    "\n" +
////                    "Další nežádoucí účinky mohou zahrnovat:\n" +
////                    "\n" +
////                    "Pocit na zvracení a zvracení, průjem, bolest břicha, pocení a abnormální výsledky jaterních testů, selhání jater nebo žloutenka, změny krevního obrazu (vzácné, objevují se u 1 až 10 osob z 10 000).\n" +
////                    "\n" +
////                    "S neznámou četností se může objevit nervozita, bolest hlavy, závratě, potíže se spánkem, zvýšení krevního tlaku a rozšíření zorniček.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Hlášení nežádoucích účinků\n" +
////                    "\n" +
////                    "Pokud se u Vás vyskytne kterýkoli z nežádoucích účinků, sdělte to svému lékaři nebo lékárníkovi. Stejně postupujte v případě jakýchkoli nežádoucích účinků, které nejsou uvedeny v této příbalové informaci. Nežádoucí účinky můžete hlásit také přímo na adresu:\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Státní ústav pro kontrolu léčiv Šrobárova 48\n" +
////                    "\n" +
////                    "100 41 Praha 10\n" +
////                    "\n" +
////                    "webové stránky: www.sukl.cz/nahlasit-nezadouci-ucinek.\n" +
////                    "\n" +
////                    "Nahlášením nežádoucích účinků můžete přispět k získání více informací o bezpečnosti tohoto přípravku.', 'Uchovávejte mimo dohled a dosah dětí.\n" +
////                    "\n" +
////                    "Přípravek nepoužívejte po uplynutí doby použitelnosti vyznačené na obalu. Doba použitelnosti se vztahuje k poslednímu dni uvedeného měsíce.\n" +
////                    "\n" +
////                    "Uchovávejte při teplotě do 25oC.\n" +
////                    "\n" +
////                    "Nevyhazujte žádné léčivé přípravky do odpadních vod nebo domácího odpadu. Zeptejte se svého lékárníka, jak naložit s přípravky, které již nepoužíváte. Tato opatření pomáhají chránit životní prostředí.', 'Co Coldrex horký nápoj citron obsahuje\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Léčivými látkami jsou:\n" +
////                    "\n" +
////                    "Paracetamolum 750 mg, phenylephrini hydrochloridum 10 mg a acidum ascorbicum 60 mg\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Pomocnými látkami jsou:\n" +
////                    "\n" +
////                    "Sacharóza, kyselina citronová, sodná sůl sacharinu, dihydrát natrium-citrátu, chinolinová žluť, citronové aroma v prášku.\n" +
////                    "\n" +
////                    "Sáček obsahuje 135 mg sodíku a 2 900 mg sacharózy.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Jak vypadá Coldrex horký nápoj citron a co obsahuje toto balení\n" +
////                    "\n" +
////                    "Coldrex horký nápoj citron je krystalický, světle žlutý prášek chuti a vůně po citronu.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Dodává se v jednodávkových sáčcích, každý obsahuje 5 g prášku.\n" +
////                    "\n" +
////                    "Sáčky jsou baleny po 1, 3, 5, 6, 10,12, 14, 20 nebo 30 kusech v krabičce\n" +
////                    "\n" +
////                    "Velikost balení 1, 3, 5, 6, 10, 12 a 14 sáčků je vydávána bez lékařského předpisu.\n" +
////                    "\n" +
////                    "Velikost balení 20 a 30 sáčků je vydávána bez lékařského předpisu s omezením.\n" +
////                    "\n" +
////                    " \n" +
////                    "\n" +
////                    "Držitel rozhodnutí o registraci a výrobce\n" +
////                    "\n" +
////                    "Držitel rozhodnutí o registraci\n" +
////                    "\n" +
////                    "OMEGA PHARMA a.s.\n" +
////                    "\n" +
////                    "Drážní 253/7\n" +
////                    "\n" +
////                    "Slatina\n" +
////                    "\n" +
////                    "627 00 Brno\n" +
////                    "\n" +
////                    "Česká republika\n" +
////                    "\n" +
////                    "e-mail: safety@omega-pharma.cz\n" +
////                    "\n" +
////                    "Výrobce\n" +
////                    "\n" +
////                    "SmithKline Beecham, S.A.\n" +
////                    "\n" +
////                    "Alcala de Henares\n" +
////                    "\n" +
////                    "Madrid\n" +
////                    "\n" +
////                    "Španělsko ')";
////
////    private static final String OlynthInfo =
////            "INSERT INTO " + MEDICINE_INFO_TABLE_NAME + "(description, warning, usage_instructions, side_effects, storage_instructions, other_information) VALUES " +
////                    "('','','','', '', '')";
