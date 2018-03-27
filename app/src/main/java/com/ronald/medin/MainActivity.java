package com.ronald.medin;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ronald.medin.Fragments.DoctorFragment;
import com.ronald.medin.Fragments.HelpFragment;
import com.ronald.medin.Fragments.InventoryFragment;
import com.ronald.medin.Fragments.JournalFragment;
import com.ronald.medin.Fragments.MeasurmentFragment;
import com.ronald.medin.Fragments.MedsFragment;
import com.ronald.medin.Fragments.ReportFragment;
import com.ronald.medin.Fragments.SettingsFragment;
import com.ronald.medin.Fragments.TreatmentFragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.ronald.medin.SQLite.MEDICINE_COLUMN_ID;
import static com.ronald.medin.SQLite.MEDICINE_COLUMN_NAME;
import static com.ronald.medin.SQLite.MEDICINE_COLUMN_PACKAGE_QUANTITY;
import static com.ronald.medin.SQLite.MEDICINE_COLUMN_TYPE;
import static com.ronald.medin.SQLite.MEDICINE_INFO_COLUMN_DESCRIPTION;
import static com.ronald.medin.SQLite.MEDICINE_INFO_COLUMN_ID;
import static com.ronald.medin.SQLite.MEDICINE_INFO_COLUMN_OTHER_INFORMATION;
import static com.ronald.medin.SQLite.MEDICINE_INFO_COLUMN_SIDE_EFFECTS;
import static com.ronald.medin.SQLite.MEDICINE_INFO_COLUMN_STORAGE_INSTRUCTIONS;
import static com.ronald.medin.SQLite.MEDICINE_INFO_COLUMN_USAGE_INSTRUCTIONS;
import static com.ronald.medin.SQLite.MEDICINE_INFO_COLUMN_WARNING;
import static com.ronald.medin.SQLite.MEDICINE_INFO_TABLE_NAME;
import static com.ronald.medin.SQLite.MEDICINE_MEDICINE_INFO_COLUMN_ID;
import static com.ronald.medin.SQLite.MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID;
import static com.ronald.medin.SQLite.MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID;
import static com.ronald.medin.SQLite.MEDICINE_MEDICINE_INFO_TABLE_NAME;
import static com.ronald.medin.SQLite.MEDICINE_TABLE_NAME;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            TreatmentFragment fragment = new TreatmentFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SQLite db = new SQLite(getApplicationContext());
        if(db.checkIfMedicineEmpty() && db.checkIfMedicine_infoEmpty() && db.checkIfMedicine_Medicine_infoEmpty()){
//            insertMedicineAssetIntoDb(db);
//            insertMedicine_infoAssetIntoDb(db);
//            insertMedicine_Medicine_infoAssetIntoDb(db);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_treatment) {
            //Set Fragments
            TreatmentFragment fragment = new TreatmentFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_meds) {
            //Set Fragments
            MedsFragment fragment = new MedsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_inventory) {
            InventoryFragment fragment = new InventoryFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_measurment) {
            MeasurmentFragment fragment = new MeasurmentFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_journal) {
            JournalFragment fragment = new JournalFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_doctors) {
            DoctorFragment fragment = new DoctorFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_report){
            ReportFragment fragment = new ReportFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_settings){
            SettingsFragment fragment = new SettingsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_help){
            HelpFragment fragment = new HelpFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void insertMedicineAssetIntoDb(SQLite db) {
        db = new SQLite(getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();

        AssetManager manager = getApplicationContext().getAssets();
        InputStream inputStreamMedicine = null;
        try {
            inputStreamMedicine = manager.open("Medicine.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader bufferMedicine = new BufferedReader(new InputStreamReader(inputStreamMedicine));
        String line = "";
        String tableName = MEDICINE_TABLE_NAME;
        String columns = "_id, name, type, package_quantity";
        String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
        String str2 = ");";

        database.beginTransaction();
        try {
            while ((line = bufferMedicine.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(";");
                sb.append("'" + str[0] + "',");
                sb.append(str[1] + "',");
                sb.append(str[2] + "',");
                sb.append(str[3] + "'");
                sb.append(str2);
                Log.e("SQL", sb.toString());
                //database.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }
    public void insertMedicine_infoAssetIntoDb(SQLite db){
        db = new SQLite(getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();

        AssetManager manager = getApplicationContext().getAssets();
        InputStream inputStreamMedicine_info = null;
        try {
            inputStreamMedicine_info = manager.open("Medicine_info.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader bufferMedicine_info = new BufferedReader(new InputStreamReader(inputStreamMedicine_info));
        String line = "";
        String tableName = MEDICINE_INFO_TABLE_NAME;
        String columns = "_id, description, warning, usage_instructions, side_effects, storage_instructions, other_information";
        String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
        String str2 = ");";

        database.beginTransaction();
        try {
            while ((line = bufferMedicine_info.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(";");
                sb.append("'" + str[0] + "',");
                sb.append(str[1] + "',");
                sb.append(str[2] + "',");
                sb.append(str[3] + "',");
                sb.append(str[4] + "',");
                sb.append(str[5] + "',");
                sb.append(str[6] + "'");
                sb.append(str2);
                Log.e("SQL", sb.toString());
                //database.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }
    public void insertMedicine_Medicine_infoAssetIntoDb(SQLite db){
        db = new SQLite(getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();

        AssetManager manager = getApplicationContext().getAssets();

        InputStream inputStreamMedicine_Medicine_info = null;
        try {
            inputStreamMedicine_Medicine_info = manager.open("Medicine_Medicine_info.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader bufferMedicine_Medicine_info = new BufferedReader(new InputStreamReader(inputStreamMedicine_Medicine_info));
        String line = "";
        database.beginTransaction();

        try {
            while ((line = bufferMedicine_Medicine_info.readLine()) != null) {
                String[] columsMedicine_Medicine_info = line.split(";");

                ContentValues valuesMedicine_Medicine_info = new ContentValues();
                valuesMedicine_Medicine_info.put(MEDICINE_MEDICINE_INFO_COLUMN_ID, columsMedicine_Medicine_info[0].trim());
                valuesMedicine_Medicine_info.put(MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_ID, columsMedicine_Medicine_info[1].trim());
                valuesMedicine_Medicine_info.put(MEDICINE_MEDICINE_INFO_COLUMN_MEDICINE_INFO_ID, columsMedicine_Medicine_info[2].trim());
                database.insert(MEDICINE_MEDICINE_INFO_TABLE_NAME, null, valuesMedicine_Medicine_info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }
}
