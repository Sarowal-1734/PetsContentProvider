package com.dynamic_host.petscontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.dynamic_host.petscontentprovider.database.PetContract;
import com.dynamic_host.petscontentprovider.database.PetDbHelper;

public class EditorActivity extends AppCompatActivity {

    Spinner spGender;
    EditText etName, etBreed, etWeight;
    String selectedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        etName = findViewById(R.id.etName);
        etBreed = findViewById(R.id.etBreed);
        etWeight = findViewById(R.id.etWeight);
        spGender = findViewById(R.id.spGender);
        setupSpinner();

    }


    public void insertToDatabase(){

        String name  = etName.getText().toString().trim();
        String breed  = etBreed.getText().toString().trim();
        Integer weight  = Integer.parseInt(etWeight.getText().toString().trim());

        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_NAME, name);
        values.put(PetContract.PetEntry.COLUMN_BREED, breed);
        values.put(PetContract.PetEntry.COLUMN_GENDER, selectedGender);
        values.put(PetContract.PetEntry.COLUMN_WEIGHT, weight);
        Uri newUri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null)
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Pet saved", Toast.LENGTH_SHORT).show();
    }


    void setupSpinner() {
        ArrayAdapter genderSpinner = ArrayAdapter.createFromResource(this, R.array.array_gender_options, android.R.layout.simple_spinner_item);
        genderSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderSpinner);

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(gender)){
                    if(gender.equals("Male"))
                        selectedGender = "Male";
                    else if(gender.equals("Female"))
                        selectedGender = "Female";
                    else
                        selectedGender = "Unknown";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {selectedGender = "Unknown"; }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                insertToDatabase();
                finish();
                return true;
            case R.id.action_delete:
                Toast.makeText(EditorActivity.this, "Item Deleted!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}