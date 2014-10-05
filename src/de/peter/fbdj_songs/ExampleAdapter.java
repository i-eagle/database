package de.peter.fbdj_songs;

import java.awt.Color;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.CursorAdapter;
import android.widget.TextView;

public class ExampleAdapter extends CursorAdapter {
	

    private List<String> items;
    

    private TextView text;

    public ExampleAdapter(Context context, Cursor cursor, List<String> items) {

        super(context, cursor, false);

        this.items = items;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

      
        text.setText(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_COMMENT1)));
        //text.setBackgroundColor(color.white);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item, parent, false);

        text = (TextView) view.findViewById(R.id.text);

        return view;

    }

} 


