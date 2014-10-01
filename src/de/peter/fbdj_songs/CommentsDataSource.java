package de.peter.fbdj_songs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Hier werden alle Daten von der Datenbank verarbeitet.
 * Eintrag einfügen,
 * Eintrag löschen,
 * 
 * @author petermartens
 *
 */
public class CommentsDataSource {

	  // Database felder
	 public static SQLiteDatabase database;
	  private static MySQLiteHelper dbHelper;
	  
	  private static String[] allColumns = { 
			  MySQLiteHelper.COLUMN_ID,			//ID
			  MySQLiteHelper.COLUMN_COMMENT1,	//titel
			  MySQLiteHelper.COLUMN_COMMENT2,	//interpret
			  MySQLiteHelper.COLUMN_COMMENT3,	//tonart
			  MySQLiteHelper.COLUMN_COMMENT4,	//liedtext
			  MySQLiteHelper.COLUMN_COMMENT5,	//favorit
			  MySQLiteHelper.COLUMN_COMMENT6,	//eingelesen
			  MySQLiteHelper.COLUMN_COMMENT7,	//hauefig_benutzt
			  };

	  public CommentsDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }
	  public void close() {
		//  dbHelper.close();		//gibt iwelche Probleme
		  	  }
	 

	  public static void createComment(
			 
			 String neuerEintrag_titel, 
			 String neuerEintrag_interpret,
			 String neuerEintrag_tonart,
			 String neuerEintrag_liedtext) {
	   
		  ContentValues values = new ContentValues();
		values.clear();
	    values.put(MySQLiteHelper.COLUMN_COMMENT1, neuerEintrag_titel);
	    values.put(MySQLiteHelper.COLUMN_COMMENT2, neuerEintrag_interpret);
	    values.put(MySQLiteHelper.COLUMN_COMMENT3, neuerEintrag_tonart);
	    values.put(MySQLiteHelper.COLUMN_COMMENT4, neuerEintrag_liedtext);
	   
	    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,values);
	   	  
	  }

	  public void deleteComment(Comment comment) {
	    long id = comment.getId();	//Hier wird die Id bestimmt, 
	    //welche gelöscht werden Soll// ItemClicklistener wird später verwendet
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	
	  public List<Comment> getAllComments() {
		    int i = 0;

		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        allColumns, null, null, null, null, MySQLiteHelper.COLUMN_COMMENT1); //nach titel sortieren
		    Comment[] comments = new Comment[cursor.getCount()];
		    
		    while (cursor.moveToNext()) {
		    	Comment comment = new Comment();
		    	comment.setId				(cursor.getLong(0));
			    comment.setTitel			(cursor.getString(1));
			    comment.setInterpret		(cursor.getString(2));
			    comment.setTonart			(cursor.getString(3));
			    comment.setLiedtext			(cursor.getString(4));
			    comment.setFavorit			(cursor.getInt(5));
			    comment.setEingelesen		(cursor.getInt(6));
			    comment.setHaeufig_benutzt	(cursor.getInt(7));
		      comments[i] = comment;
		      i++;
		      comment = null;
		    }
		    
		    cursor.close();
		    return Arrays.asList(comments);
		  }

}