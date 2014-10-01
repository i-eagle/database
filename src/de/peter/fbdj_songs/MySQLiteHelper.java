package de.peter.fbdj_songs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	

	public static final String TABLE_COMMENTS = "songs";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_COMMENT1 = "titel";
	  public static final String COLUMN_COMMENT2 = "interpret";
	  public static final String COLUMN_COMMENT3 = "tonart";
	  public static final String COLUMN_COMMENT4 = "liedtext";
	  public static final String COLUMN_COMMENT5 = "favorit";
	  public static final String COLUMN_COMMENT6 = "eingelesen";
	  public static final String COLUMN_COMMENT7 = "haeufig_benutzt";
	  
	  private static final String DATABASE_NAME = "songs.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = 
			  "create table if not exists" + TABLE_COMMENTS 
			  + "(" 
			  + COLUMN_ID + " integer primary key autoincrement, " 
			  + COLUMN_COMMENT1 + " VARCHAR(50) , " //NOT NULL
			  + COLUMN_COMMENT2 + " VARCHAR(50), "//NOT NULL
			  + COLUMN_COMMENT3 + " VARCHAR(10), "
			  + COLUMN_COMMENT4 + " VARCHAR(1000) , "//NOT NULL
			  + COLUMN_COMMENT5 + " INTEGER, "
			  + COLUMN_COMMENT6 + " INTEGER, "
			  + COLUMN_COMMENT7 + " INTEGER "
			  + ")";

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  
	  
	  @Override
	  public void onCreate(SQLiteDatabase database) {
		  database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
	    onCreate(db);
	  }
	 
	

	

	} 