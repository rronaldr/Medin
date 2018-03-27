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

/**
 * Created by Ronald on 27.03.2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.ronald.medin/databases/";
    private static String DB_NAME = "Medin.sql";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

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

    public SQLiteHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

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
