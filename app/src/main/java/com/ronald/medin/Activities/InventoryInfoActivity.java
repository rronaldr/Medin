package com.ronald.medin.Activities;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ronald.medin.Classes.Inventory;
import com.ronald.medin.R;
import com.ronald.medin.SQLite;

public class InventoryInfoActivity extends AppCompatActivity {

    //Inicializace proměnných
    int inventoryId;

    EditText quantityValueEdit;
    Drawable drawableEditTextStyle;

    Button editBtn;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Přiřazení view
        EditText styleEdit = findViewById(R.id.edit_inventoryInfo_style);
        drawableEditTextStyle = styleEdit.getBackground();
        TextView medNameText = findViewById(R.id.text_inventoryInfo_medName);
        TextView packageUnitText = findViewById(R.id.text_inventoryInfo_packageUnit);

        editBtn = findViewById(R.id.btn_inventoryInfo_edit);
        saveBtn = findViewById(R.id.btn_inventoryInfo_save);

        quantityValueEdit = findViewById(R.id.edit_inventoryInfo_quantity);

        //Vybraní dat z intentu
        inventoryId = getIntent().getIntExtra("ItemID",0);

        //Nové spojení s db
        SQLite db = new SQLite(this);
        Cursor inventoryInfoCursor = db.getInventory(inventoryId);
        inventoryInfoCursor.moveToFirst();

        //Přiřazení view
        medNameText.setText(inventoryInfoCursor.getString(inventoryInfoCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_NAME)));
        packageUnitText.setText(inventoryInfoCursor.getString(inventoryInfoCursor.getColumnIndex(SQLite.MEDICINE_COLUMN_PACKAGE_UNIT)));
        quantityValueEdit.setText(inventoryInfoCursor.getString(inventoryInfoCursor.getColumnIndex(SQLite.INVENTORY_COLUMN_QUANTITY)));
    }

    /**
     * onClick na tlačítko editace
     * @param view
     */
    public void editInventory(View view) {
        makeViewEditable();
    }

    /**
     * onClick na tlačítko uložení
     * @param view
     */
    public void saveInventory(View view) {
        //Ověření vstupů
        if(!quantityValueEdit.getText().toString().matches("")){
            //Nová instance objektu
            Inventory i = new Inventory();
            i.setId(inventoryId);
            i.setQuantity(Integer.parseInt(quantityValueEdit.getText().toString()));

            SQLite db = new SQLite(this);
            //Updatnutí záznamu
            db.updateInventory(i);

            //Zobrazení zprávy uživateli
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.inventorySaved), Toast.LENGTH_SHORT).show();

            //Ukončení aktivity po 2s
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InventoryInfoActivity.this.finish();
                }
            }, 2000);
        } else {
            Toast.makeText(this, "Hodnota nesmí být prázdná", Toast.LENGTH_SHORT).show();
        }

    }

    //Procedura, změní view na editovatelné
    public void makeViewEditable(){
        editBtn.setVisibility(View.GONE);
        saveBtn.setVisibility(View.VISIBLE);
        quantityValueEdit.setBackground(drawableEditTextStyle);
        quantityValueEdit.setFocusableInTouchMode(true);
    }
}
