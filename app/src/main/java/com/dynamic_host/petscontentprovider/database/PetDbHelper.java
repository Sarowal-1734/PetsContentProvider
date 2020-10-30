package com.dynamic_host.petscontentprovider.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dynamic_host.petscontentprovider.database.PetContract.PetEntry;

public class PetDbHelper extends SQLiteOpenHelper {

    public static final int DATADASE_VERSION = 1;
    public static final String DATADASE_NAME = "petDB";

    public PetDbHelper(Context context) {
        super(context, DATADASE_NAME, null, DATADASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE "+ PetEntry.TABLE_NAME+
                " ("+PetEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                PetEntry.COLUMN_NAME+" TEXT NOT NULL, "+
                PetEntry.COLUMN_BREED+" TEXT, "+
                PetEntry.COLUMN_GENDER+" TEXT NOT NULL, "+
                PetEntry.COLUMN_WEIGHT+" TEXT NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
