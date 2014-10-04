package de.peter.fbdj_songs;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;




public class Neues_Lied_eingeben extends ActionBarActivity implements OnClickListener, DialogInterface.OnClickListener {
	private EditText  et_titel, et_interpret, et_tonart, et_durmol, et_liedtext;
	private Button btn_speichern, btn_abbrechen;
	private CheckBox cb_dur, cb_mol;
	public long id;
	public String updaten, tonart, cb_durmol, vonStartpage, string_durmol;
	public char[] durmol;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_neues__lied_eingeben);

		int i =0;
		
		
		et_titel=(EditText) findViewById(R.id.et_titel);
		et_interpret=(EditText) findViewById(R.id.et_interpret);
		et_tonart=(EditText) findViewById(R.id.et_tonart);
		et_liedtext=(EditText) findViewById(R.id.et_liedtext);
		cb_dur=(CheckBox) findViewById(R.id.cb_Dur);
		cb_mol=(CheckBox) findViewById(R.id.cb_mol);
		
		btn_speichern = (Button)findViewById(R.id.btn_speichern);
		btn_speichern.setOnClickListener(this);
		btn_abbrechen = (Button)findViewById(R.id.btn_abbrechen);
		btn_abbrechen.setOnClickListener(this);
		
		
		Intent intent = getIntent();
		id = intent.getLongExtra("Id_update", -1);
		vonStartpage=intent.getStringExtra("vonStartpage");
		if(vonStartpage.equals("ja")){
			
		}
		else{
		updaten = intent.getStringExtra("Update");
		//update_testvariable = "update";
		et_titel.setText(intent.getStringExtra("Liedtitel"));
		et_interpret.setText(intent.getStringExtra("Interpret"));
		et_tonart.setText(intent.getCharArrayExtra("Tonart"), 0, 1);
		durmol = (intent.getCharArrayExtra("durmol"));
		
		if(durmol.length != 0){
			while(durmol[i] != '-'){
				i++;
			}
			if(durmol[i+=1]=='d'){
				cb_dur.setChecked(true);
			}
			else if(durmol[i]=='m'){
				cb_mol.setChecked(true);
			}
		}
		
		et_liedtext.setText(intent.getStringExtra("Liedtext"));
		}
		
	
			
		
		
	}
	
	/** Damit die Seite nicht ohne Nachfrage über Zurück-Button beendet werden kann
	*/
	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) { 
		if (keycode == KeyEvent.KEYCODE_BACK){
			String message ="Möchtest du die Eingaben verwerfen und zur Titelliste zurückkehren?";
			AlertDialog ad = new AlertDialog.Builder(this)
		   .setMessage(message)
		   .setIcon(R.drawable.ic_launcher)
		   .setTitle("Sicher zurückkehren?")
		   .setPositiveButton("Zurückkehren", this)
		   .setNegativeButton("Abbrechen", this)
		   .create();
			ad.show();
		}
		return false;
	    
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast toast;
		String titel, interpret, tonart, liedtext, uebergabe_durmol;
		
		
		switch(v.getId()){
		case R.id.btn_speichern:	
			if (et_titel.getText().toString()!= ""
			&& et_interpret.getText().toString()!= ""
			&& et_tonart.getText().toString() != ""
			&& (cb_dur.isChecked() || cb_mol.isChecked())
			&& et_liedtext.getText().toString()!= ""){
				if(cb_dur.isChecked()){
				string_durmol="-dur";}
				else{string_durmol="-mol";}

				titel = et_titel.getText().toString();
				interpret = et_interpret.getText().toString();
				tonart = et_tonart.getText().toString();
				uebergabe_durmol = string_durmol;
				liedtext = et_liedtext.getText().toString();
			
				if(String.valueOf(id).equals(String.valueOf(-1))){
					CommentsDataSource.createComment(titel, interpret, tonart+string_durmol, liedtext);
				}
				else{	Cursor cursor = CommentsDataSource.database.query(
							MySQLiteHelper.TABLE_COMMENTS,
							CommentsDataSource.allColumns, 
							MySQLiteHelper.COLUMN_ID+" = ? ",
							new String[]{String.valueOf(id)}, 
							null, null, null); //nach titel sortiert
					if(cursor != null){
						cursor.moveToFirst();
					}
					Comment comment = new Comment();
					comment.setId				(cursor.getLong(0));
				    comment.setTitel			(titel);
				    comment.setInterpret		(interpret);
				    comment.setTonart			(tonart+uebergabe_durmol);
				    comment.setLiedtext			(liedtext);
				    comment.setFavorit			(cursor.getInt(5));
				    comment.setEingelesen		(cursor.getInt(6));
				    comment.setHaeufig_benutzt	(cursor.getInt(7));
				    ContentValues cv = new ContentValues();
					  cv.put(MySQLiteHelper.COLUMN_COMMENT1, comment.getTitel());
					  cv.put(MySQLiteHelper.COLUMN_COMMENT2, comment.getInterpret());
					  cv.put(MySQLiteHelper.COLUMN_COMMENT3, comment.getTonart());
					  cv.put(MySQLiteHelper.COLUMN_COMMENT4, comment.getLiedtext());
					  cv.put(MySQLiteHelper.COLUMN_COMMENT5, comment.getFavorit());
					  cv.put(MySQLiteHelper.COLUMN_COMMENT6, comment.getEingelesen());
					  cv.put(MySQLiteHelper.COLUMN_COMMENT7, comment.getHaeufig_benutzt());
					  
					  CommentsDataSource.database.update(MySQLiteHelper.TABLE_COMMENTS,
							  cv, MySQLiteHelper.COLUMN_ID+"="+id,null);
					  
				   
				}
				
				
			toast = Toast.makeText(v.getContext(),
					"Gespeichert: " + et_titel.getText().toString(),
					Toast.LENGTH_SHORT);
			toast.show();
			finish();
			
			}
			else{
			toast = Toast.makeText(v.getContext(),
					"Fülle bitte zuerst alle Felder aus", 
					Toast.LENGTH_LONG);
					toast.show();}
			
			break;
		
		case R.id.btn_abbrechen:
			String message ="Möchtest du die Eingaben verwerfen und zur Titelliste zurückkehren?";
			AlertDialog ad = new AlertDialog.Builder(this)
		   .setMessage(message)
		   .setIcon(R.drawable.ic_launcher)
		   .setTitle("Sicher zurückkehren?")
		   .setPositiveButton("Zurückkehren", this)
		   .setNegativeButton("Abbrechen", this)
		   .create();
			ad.show();
		}
	}



	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch(which){
		case DialogInterface.BUTTON_POSITIVE: //Zurückkehren
			finish();
			break;
		case DialogInterface.BUTTON_NEGATIVE: //Abbrechen
			break;
		}
		
	}

	
}
