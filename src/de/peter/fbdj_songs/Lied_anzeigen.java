package de.peter.fbdj_songs;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Lied_anzeigen extends ActionBarActivity implements OnClickListener, DialogInterface.OnClickListener{

	public CommentsDataSource datasource;
	public TextView Liedtitel, Interpret, Tonart, Liedtext; 
	public ImageButton	Ton;
	public int Tonspur, favorit;
	public long id;
	public CheckBox cb_favorit;
	public MediaPlayer mp;
	public int counter=1000;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lied_anzeigen);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		Liedtitel = (TextView)findViewById(R.id.tv_liedtitel);
		Interpret = (TextView)findViewById(R.id.tv_interpret);
		Tonart = (TextView)findViewById(R.id.tv_tonart);
		Liedtext = (TextView)findViewById(R.id.tv_liedtext);
		Ton = (ImageButton)findViewById(R.id.btn_ton);
		cb_favorit = (CheckBox)findViewById(R.id.cb_favorit);
		

		Intent intent = getIntent();
		Liedtitel.setText(intent.getStringExtra("Liedtitel"));
		Interpret.setText(intent.getStringExtra("Interpret"));
		Tonart.setText(intent.getStringExtra("Tonart"));
		Liedtext.setText(intent.getStringExtra("Liedtext"));
		counter = intent.getIntExtra("Haeufig_benutzt", -1);
		id = intent.getLongExtra("Id", -1);
		favorit = intent.getIntExtra("Favorit", 0);
		if (favorit == 1){
		cb_favorit.setChecked(true);
		}
		
		 datasource = new CommentsDataSource(this);
		 datasource.open();

		 if(counter!=-1){
		 if(counter>0){
			 counter--;
		 Cursor cursor = CommentsDataSource.database.query(
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
		    comment.setTitel			(cursor.getString(1));
		    comment.setInterpret		(cursor.getString(2));
		    comment.setTonart			(cursor.getString(3));
		    comment.setLiedtext			(cursor.getString(4));
		    comment.setFavorit			(cursor.getInt(5));
		    comment.setEingelesen		(cursor.getInt(6));
		    comment.setHaeufig_benutzt	(counter);
 		CommentsDataSource.updateComment(comment);
		    }
		 }
	}
	
	

	
		public void onClick(View view) {
		   
		    switch (view.getId()) {
		    
		    case R.id.btn_ton:	// hier wird zuerst die tonart ausgelesen, dann der
		    					// dazugehörige Akkord festgelegt, anschließend wird der
		    					// Akkord abgespielt
		    	String akkord = Tonart.getText().toString();
		    	
		    	if(akkord.equals("c-mol") || akkord.equals("C-mol")){
		    		Tonspur = R.raw.cmol;}
		    	else if(akkord.equals("d-mol") || akkord.equals("D-mol")){
		    		Tonspur = R.raw.dmol;}
		    	else if(akkord.equals("e-mol") || akkord.equals("E-mol")){
		    		Tonspur = R.raw.emol;}
		    	else if(akkord.equals("f-mol") || akkord.equals("F-mol")){
		    		Tonspur = R.raw.fmol;}
		    	else if(akkord.equals("g-mol") || akkord.equals("G-mol")){
		    		Tonspur = R.raw.gmol;}
		    	else if(akkord.equals("a-mol") || akkord.equals("A-mol")){
		    		Tonspur = R.raw.amol;}
		    	else if(akkord.equals("h-mol") || akkord.equals("H-mol")){
		    		Tonspur = R.raw.hmol;}
		    	else if(akkord.equals("des-mol") ||akkord.equals("Des-mol")||
		    			akkord.equals("cis-mol")|| akkord.equals("Cis-mol")){
		    		Tonspur = R.raw.desmol;}
		    	else if(akkord.equals("es-mol") ||akkord.equals("Es-mol")||
		    			akkord.equals("dis-mol")|| akkord.equals("Dis-mol")){
		    		Tonspur = R.raw.esmol;}
		    	else if(akkord.equals("ges-mol") ||akkord.equals("Ges-mol")||
		    			akkord.equals("fis-mol")|| akkord.equals("Fis-mol")){
		    		Tonspur = R.raw.gesmol;}
		    	else if(akkord.equals("as-mol") ||akkord.equals("As-mol")||
		    			akkord.equals("gis-mol")|| akkord.equals("Gis-mol")){
		    		Tonspur = R.raw.asmol;}
		    	else if(akkord.equals("b-mol") ||akkord.equals("B-mol")||
		    			akkord.equals("ais-mol")|| akkord.equals("Ais-mol")){
		    		Tonspur = R.raw.bmol;}
		    	else if(akkord.equals("c-dur") || akkord.equals("C-dur")){
		    		Tonspur = R.raw.cdur;}
		    	else if(akkord.equals("d-dur") || akkord.equals("D-dur")){
		    		Tonspur = R.raw.ddur;}
		    	else if(akkord.equals("e-dur") || akkord.equals("E-dur")){
		    		Tonspur = R.raw.edur;}
		    	else if(akkord.equals("f-dur") || akkord.equals("F-dur")){
		    		Tonspur = R.raw.fdur;}
		    	else if(akkord.equals("g-dur") || akkord.equals("G-dur")){
		    		Tonspur = R.raw.gdur;}
		    	else if(akkord.equals("a-dur") || akkord.equals("A-dur")){
		    		Tonspur = R.raw.adur;}
		    	else if(akkord.equals("h-dur") || akkord.equals("H-dur")){
		    		Tonspur = R.raw.hdur;}
		    	else if(akkord.equals("cis-dur") ||akkord.equals("Cis-dur")||
		    			akkord.equals("des-dur")|| akkord.equals("Des-dur")){
		    		Tonspur = R.raw.cisdur;}
		    	else if(akkord.equals("dis-dur") ||akkord.equals("Dis-dur")||
		    			akkord.equals("es-dur")|| akkord.equals("Es-dur")){
		    		Tonspur = R.raw.disdur;}
		    	else if(akkord.equals("fis-dur") ||akkord.equals("Fis-dur")||
		    			akkord.equals("ges-dur")|| akkord.equals("Ges-dur")){
		    		Tonspur = R.raw.fisdur;}
		    	else if(akkord.equals("gis-dur") ||akkord.equals("Gis-dur")||
		    			akkord.equals("as-dur")|| akkord.equals("As-dur")){
		    		Tonspur = R.raw.gisdur;}
		    	else if(akkord.equals("ais-dur") ||akkord.equals("Ais-dur")||
		    			akkord.equals("b-dur")|| akkord.equals("B-dur")){
		    		Tonspur = R.raw.aisdur;}
		    	else {Toast toast = Toast.makeText(view.getContext(),
							"Tonart konnte nicht ermittelt werden", 
							Toast.LENGTH_SHORT);
							toast.show();
		    		Log.v("peter","Tonart konnte nicht ermittelt werden");
		    		break;
		    	}
		    	mp= MediaPlayer.create(getApplicationContext(), Tonspur);
		    	mp.start();
		    	break;
		    
		    case R.id.cb_favorit:
		    	if(cb_favorit.isChecked()){
		    		Cursor cursor = CommentsDataSource.database.query(
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
				    comment.setTitel			(cursor.getString(1));
				    comment.setInterpret		(cursor.getString(2));
				    comment.setTonart			(cursor.getString(3));
				    comment.setLiedtext			(cursor.getString(4));
				    comment.setFavorit			(1);
				    comment.setEingelesen		(cursor.getInt(6));
				    comment.setHaeufig_benutzt	(cursor.getInt(7));
		    		CommentsDataSource.updateComment(comment);
		    	}
		    	else{
		    		Cursor cursor = CommentsDataSource.database.query(
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
				    comment.setTitel			(cursor.getString(1));
				    comment.setInterpret		(cursor.getString(2));
				    comment.setTonart			(cursor.getString(3));
				    comment.setLiedtext			(cursor.getString(4));
				    comment.setFavorit			(0);
				    comment.setEingelesen		(cursor.getInt(6));
				    comment.setHaeufig_benutzt	(cursor.getInt(7));
		    		CommentsDataSource.updateComment(comment);
		    	}
		    	break;
		    default: 
		    	Log.v("peter","Fehler in der OnClick_Lied_anzeigen");
		    	break;
		    
		    
		    }
		    	
	}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.lied_anzeigen, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			
			switch(item.getItemId()){
			
			case R.id.delete: //Liedeintrag aus Datenbank löschen
				
				String message ="Möchtest du das Lied wirklich löschen?";
				AlertDialog ad = new AlertDialog.Builder(this)
			   .setMessage(message)
			   .setIcon(R.drawable.ic_launcher)
			   .setTitle("Sicher löschen?")
			   .setPositiveButton("Ja, Lied löschen", this)
			   .setNegativeButton("Abbrechen", this)
			   .create();
				ad.show();
				
				 return true;
				
					
			case R.id.bearbeiten:	
				
				//List<Comment> values = datasource.getAllComments();
				//CommentsDataSource.database = this.getReadableDatabase();
				Cursor cursor = CommentsDataSource.database.query(
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
			    comment.setTitel			(cursor.getString(1));
			    comment.setInterpret		(cursor.getString(2));
			    comment.setTonart			(cursor.getString(3));
			    comment.setLiedtext			(cursor.getString(4));
			    comment.setFavorit			(cursor.getInt(5));
			    comment.setEingelesen		(cursor.getInt(6));
			    comment.setHaeufig_benutzt	(cursor.getInt(7));
			   
				
				Intent intent = new Intent(Lied_anzeigen.this, Neues_Lied_eingeben.class);
				intent.putExtra("Liedtitel", comment.getTitel().toString());
		    	intent.putExtra("Interpret", comment.getInterpret().toString());
		    	intent.putExtra("Tonart", comment.getTonart().toCharArray());
		    	intent.putExtra("durmol", comment.getTonart().toCharArray());
		    	intent.putExtra("Liedtext", comment.getLiedtext().toString());
		    	intent.putExtra("Id_update", comment.getId());
		    	intent.putExtra("Favorit", comment.getFavorit());
		    	intent.putExtra("Update", "update");
		    	intent.putExtra("vonStartpage", "nein");
				startActivity(intent);
				onResume();
				
		    	break;	
				// Hier muss die Datenbank geupdatet werden
				
				
				
			default: break;
			}
			return super.onOptionsItemSelected(item);
		}




		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			switch(which){
			case DialogInterface.BUTTON_POSITIVE: //löschen
				CommentsDataSource.database.delete(
						 MySQLiteHelper.TABLE_COMMENTS, 
						 MySQLiteHelper.COLUMN_ID + " = " + id, null);
				 
				 	Toast toast = Toast.makeText(
				 			getApplicationContext(),
							"Erfolgreich Gelöscht", 
							Toast.LENGTH_SHORT);
				 	toast.show();
				 Log.v("peter", "Lied " +Liedtitel+ " wurde gelöscht");
				finish();
				break;
			case DialogInterface.BUTTON_NEGATIVE: //Abbrechen
				break;
		}
		}
		
}