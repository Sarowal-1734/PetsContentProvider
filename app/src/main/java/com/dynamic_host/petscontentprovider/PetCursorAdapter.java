package com.dynamic_host.petscontentprovider;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dynamic_host.petscontentprovider.database.PetContract;

public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvBreed = (TextView) view.findViewById(R.id.tvBreed);
        int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_BREED);
        String name = cursor.getString(nameColumnIndex);
        String breed = cursor.getString(breedColumnIndex);
        tvName.setText(name);
        tvBreed.setText(breed);
    }
}
