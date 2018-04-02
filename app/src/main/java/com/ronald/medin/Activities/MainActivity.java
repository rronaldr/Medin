package com.ronald.medin.Activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import com.ronald.medin.Fragments.MeasurmentFragment;
import com.ronald.medin.Fragments.MedsFragment;
import com.ronald.medin.Fragments.TreatmentFragment;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    //Inicializace proměnných
    NavigationView navigationView = null;
    Toolbar toolbar = null;
    private SQLite mSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vytvoření fragmentu
        if(savedInstanceState == null){
            TreatmentFragment fragment = new TreatmentFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        }

        //Přiřazení view
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Nové spojení s db
        mSQLite = new SQLite(this);

        File databaseFile = getApplicationContext().getDatabasePath(SQLite.DB_NAME);
        //Ověření jestli je vytvořena databáze
        if(false == databaseFile.exists()){
            mSQLite.getReadableDatabase();
            copyDatabase(this);
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

        //Změna fragmentu
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
        }  else if (id == R.id.nav_doctors) {
            DoctorFragment fragment = new DoctorFragment();
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

    /**
     * Vytvoří v prádné databázi tabulky a záznamy z databázového souboru ve složce assets
     * @param ctx
     * @return
     */
    private boolean copyDatabase(Context ctx){
        try {
            InputStream inputStream = ctx.getAssets().open(SQLite.DB_NAME);
            String outFileName = ctx.getDatabasePath(SQLite.DB_NAME).toString();
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int lenght = 0;
            while ((lenght = inputStream.read(buff)) > 0){
                outputStream.write(buff, 0, lenght);
            }
            outputStream.flush();
            outputStream.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
