package com.dynamic_host.petscontentprovider;
import com.dynamic_host.petscontentprovider.database.PetContract.PetEntry;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.dynamic_host.petscontentprovider.database.PetDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        String[] projection = {PetEntry._ID, PetEntry.COLUMN_NAME, PetEntry.COLUMN_BREED, PetEntry.COLUMN_GENDER, PetEntry.COLUMN_WEIGHT};
        Cursor cursor = getContentResolver().query(PetEntry.CONTENT_URI, projection, null, null, null);
        try {
            TextView displayView = findViewById(R.id.pet_text_box);
            displayView.setText("Numbers of rows in pets database table: "+ cursor.getCount()+"\n\n");
            displayView.append(PetEntry._ID+"    "+
                    PetEntry.COLUMN_NAME+"    "+
                    PetEntry.COLUMN_BREED+"    "+
                    PetEntry.COLUMN_GENDER+"    "+
                    PetEntry.COLUMN_WEIGHT+"\n");

            //Finding index of each column
            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_WEIGHT);

            while (cursor.moveToNext()){
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                String currentGender = cursor.getString(genderColumnIndex);
                String currentWeight = cursor.getString(weightColumnIndex);
                displayView.append("\n  " + currentId + "      " +
                        currentName+"      "+
                        currentBreed+"        "+
                        currentGender+"             "+
                        currentWeight);
            }
        }finally {
            cursor.close();
        }

    }



    public void insertToDatabase(){
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_NAME, "Cat");
        values.put(PetEntry.COLUMN_BREED, "Milk");
        values.put(PetEntry.COLUMN_GENDER, "Male");
        values.put(PetEntry.COLUMN_WEIGHT, 5);
        //Insert a new row for Toto into the provider using the ContentResolver.
        getContentResolver().insert(PetEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_dummy_data:
                insertToDatabase();
                displayDatabaseInfo();
                return true;
            case R.id.delete_all_entries:
                Toast.makeText(CatalogActivity.this, "All Entries Deleeted!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}