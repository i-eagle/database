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
	  
	  static String[] allColumns = { 
			  MySQLiteHelper.COLUMN_ID,			//ID
			  MySQLiteHelper.COLUMN_COMMENT1,	//titel
			  MySQLiteHelper.COLUMN_COMMENT2,	//interpret
			  MySQLiteHelper.COLUMN_COMMENT3,	//tonart
			  MySQLiteHelper.COLUMN_COMMENT4,	//liedtext
			  MySQLiteHelper.COLUMN_COMMENT5,	//favorit
			  MySQLiteHelper.COLUMN_COMMENT6,	//eingelesen
			  MySQLiteHelper.COLUMN_COMMENT7,	//hauefig_benutzt
			  };
	  static String[] items={MySQLiteHelper.COLUMN_COMMENT1};

	  static String[] haeufig_benutzt={MySQLiteHelper.COLUMN_COMMENT7};
	  
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
	   int defaultwert = 1000;
		  ContentValues values = new ContentValues();
		values.clear();
	    values.put(MySQLiteHelper.COLUMN_COMMENT1, neuerEintrag_titel);
	    values.put(MySQLiteHelper.COLUMN_COMMENT2, neuerEintrag_interpret);
	    values.put(MySQLiteHelper.COLUMN_COMMENT3, neuerEintrag_tonart);
	    values.put(MySQLiteHelper.COLUMN_COMMENT4, neuerEintrag_liedtext);
	    values.put(MySQLiteHelper.COLUMN_COMMENT7, defaultwert);
	   
	    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,values);
	   	  
	  }
	  
	 

	  public void deleteComment(Comment comment) {
	    long id = comment.getId();	//Hier wird die Id bestimmt, 
	    							//welche gelöscht werden Soll
	    							// ItemClicklistener wird später verwendet
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public static void updateComment(Comment comment){
		  long id = comment.getId();
		  ContentValues cv = new ContentValues();
		  cv.put(MySQLiteHelper.COLUMN_COMMENT1, comment.getTitel());
		  cv.put(MySQLiteHelper.COLUMN_COMMENT2, comment.getInterpret());
		  cv.put(MySQLiteHelper.COLUMN_COMMENT3, comment.getTonart());
		  cv.put(MySQLiteHelper.COLUMN_COMMENT4, comment.getLiedtext());
		  cv.put(MySQLiteHelper.COLUMN_COMMENT5, comment.getFavorit());
		  cv.put(MySQLiteHelper.COLUMN_COMMENT6, comment.getEingelesen());
		  cv.put(MySQLiteHelper.COLUMN_COMMENT7, comment.getHaeufig_benutzt());
		  
		  database.update(MySQLiteHelper.TABLE_COMMENTS,
				  cv, MySQLiteHelper.COLUMN_ID+" ="+id,null);
				  
		
	  }
	  //hauefig_benutzt zurücksetzen
	  public static void updateComment_haeufig_reset(){
		  int i = 0;
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        haeufig_benutzt, null, null, null, null, 
		        null);
		 
		  ContentValues cv = new ContentValues();
		  
		  cv.put(MySQLiteHelper.COLUMN_COMMENT7, 1000);
		  
		  database.update(MySQLiteHelper.TABLE_COMMENTS,
				  cv, null,null);
				  
		
	  }
	  
	  public List<Comment> getAllComments() {
		    int i = 0;
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        allColumns, null, null, null, null, 
		        MySQLiteHelper.COLUMN_COMMENT1); //nach titel sortieren
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
	  
	  
	  public List <Comment> getComment(String search){
		  int i = 0;
		  Cursor cursor = CommentsDataSource.database.query(
				  MySQLiteHelper.TABLE_COMMENTS,
				  CommentsDataSource.allColumns, 
				  MySQLiteHelper.COLUMN_COMMENT1+" = ? ",
				  new String[]{String.valueOf(search)}, 
				  null, null, null);
		  if(cursor != null){
			  cursor.moveToFirst();
		  }

		  Comment[] comments = new Comment[cursor.getCount()];
		  while (!cursor.isAfterLast()) {
			  Comment comment = new Comment();
			  comment.setId				(cursor.getLong(0));
			  comment.setTitel			(cursor.getString(1));
			  comment.setInterpret		(cursor.getString(2));
			  comment.setTonart			(cursor.getString(3));
			  comment.setLiedtext			(cursor.getString(4));
			  comment.setFavorit			(1);
			  comment.setEingelesen		(cursor.getInt(6));
			  comment.setHaeufig_benutzt	(cursor.getInt(7));
			  comments[i] = comment;
			  i++;
			  comment = null;
			  cursor.moveToNext();
		  }

		  return Arrays.asList(comments);}
	 
	  public List <Comment> getAllComments_Favorit(){
			int i = 0;
		Cursor cursor = CommentsDataSource.database.query(
				MySQLiteHelper.TABLE_COMMENTS,
				CommentsDataSource.allColumns, 
				MySQLiteHelper.COLUMN_COMMENT5+" = ? ",
				new String[]{String.valueOf(1)}, 
				null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}

		Comment[] comments = new Comment[cursor.getCount()];
		while (!cursor.isAfterLast()) {
			Comment comment = new Comment();
		comment.setId				(cursor.getLong(0));
		comment.setTitel			(cursor.getString(1));
		comment.setInterpret		(cursor.getString(2));
		comment.setTonart			(cursor.getString(3));
		comment.setLiedtext			(cursor.getString(4));
		comment.setFavorit			(1);
		comment.setEingelesen		(cursor.getInt(6));
		comment.setHaeufig_benutzt	(cursor.getInt(7));
		comments[i] = comment;
		  i++;
		  comment = null;
		  cursor.moveToNext();
		  }

		return Arrays.asList(comments);
		}
			 
	  public List<Comment> getAllComments_Interpret() {

		    int i = 0;
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        allColumns, null, null, null, null, 
		        MySQLiteHelper.COLUMN_COMMENT2); //nach interpret sortieren
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
	  
	  public List<Comment> getAllComments_Haeufig_benutzt() {

		    int i = 0;
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        allColumns, null, null, null, null, 
		        MySQLiteHelper.COLUMN_COMMENT7); //nach haeufig_benutzt sortieren
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


	  public List<String> getAllTitel() {
		    int i = 0;
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        items, null, null, null, null, 
		        null); //nach titel sortieren
		    String[] strings = new String[cursor.getCount()];
		    
		    while (cursor.moveToNext()) {
		    	String comment ;//= new String();
			    comment=	(cursor.getString(0)); //Titel in List<String> schreiben
			    strings[i] = comment;
			    i++;
			    comment = null;
		    }
		    
		    cursor.close();
		    return Arrays.asList(strings);
		  }
		}
	
