package de.peter.fbdj_songs;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
	private EditText  et_titel, et_interpret, et_tonart, et_liedtext;
	private Button btn_speichern, btn_abbrechen;
	private CheckBox cb_dur, cb_mol;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_neues__lied_eingeben);

		
		
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
		String titel, interpret, tonart, liedtext;
		
		
		switch(v.getId()){
		case R.id.btn_speichern:	
			if (et_titel.getText().toString()!= ""
			&& et_interpret.getText().toString()!= ""
			&& et_tonart.getText().toString() != ""
			&& (cb_dur.isChecked() || cb_mol.isChecked())
			&& et_liedtext.getText().toString()!= ""){
				//neuerEintrag.setTitel(et_titel.getText().toString());
				//neuerEintrag.setInterpret(et_interpret.getText().toString());
				if(cb_dur.isChecked()){
				et_tonart.setText(et_tonart.getText().toString() + 
						" Dur");}
				else{
					et_tonart.setText(et_tonart.getText().toString() + 
							" Mol");
				}
				//neuerEintrag.setLiedtext(et_liedtext.getText().toString());

				titel = et_titel.getText().toString();
				interpret = et_interpret.getText().toString();
				tonart = et_tonart.getText().toString();
				liedtext = et_liedtext.getText().toString();
			
				CommentsDataSource.createComment(titel, interpret, tonart, liedtext);
				
				
				
				////////////////////////////////////////////
				
				
			 ///////////////////////////////////////////
			toast = Toast.makeText(v.getContext(),
					"Gespeichert: " + et_titel.getText().toString(),
					Toast.LENGTH_LONG);
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
