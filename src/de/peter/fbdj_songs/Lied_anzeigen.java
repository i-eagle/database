package de.peter.fbdj_songs;

import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class Lied_anzeigen extends ActionBarActivity{

	public TextView Liedtitel, Interpret, Tonart, Liedtext; 
	public ImageButton	Ton;
	public int Tonspur;
	public CheckBox cb_favorit;
	public MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lied_anzeigen);

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
		
	}
	

		public void onClick(View view) {
		   
		    switch (view.getId()) {
		    
		    case R.id.btn_ton:	// hier wird zuerst die tonart ausgelesen, dann der
		    					// dazugehörige Akkord festgelegt, anschließend wird der
		    					// Akkord abgespielt
		    	
		    	if(Tonart.toString() == "c-mol" || Tonart.toString()=="C-mol"){
		    		Tonspur = R.raw.cmol;}
		    	else if(Tonart.toString() == "d-mol" || Tonart.toString()=="D-mol"){
		    		Tonspur = R.raw.dmol;}
		    	else if(Tonart.toString() == "e-mol" || Tonart.toString()=="E-mol"){
		    		Tonspur = R.raw.emol;}
		    	else if(Tonart.toString() == "f-mol" || Tonart.toString()=="F-mol"){
		    		Tonspur = R.raw.fmol;}
		    	else if(Tonart.toString() == "g-mol" || Tonart.toString()=="G-mol"){
		    		Tonspur = R.raw.gmol;}
		    	else if(Tonart.toString() == "a-mol" || Tonart.toString()=="A-mol"){
		    		Tonspur = R.raw.amol;}
		    	else if(Tonart.toString() == "h-mol" || Tonart.toString()=="H-mol"){
		    		Tonspur = R.raw.hmol;}
		    	else if(Tonart.toString() == "des-mol" || Tonart.toString()=="Des-mol"||
		    			Tonart.toString() == "cis-mol" || Tonart.toString()=="Cis-mol"){
		    		Tonspur = R.raw.desmol;}
		    	else if(Tonart.toString() == "es-mol" || Tonart.toString()=="Es-mol"||
		    			Tonart.toString() == "dis-mol" || Tonart.toString()=="Dis-mol"){
		    		Tonspur = R.raw.esmol;}
		    	else if(Tonart.toString() == "ges-mol" || Tonart.toString()=="Ges-mol"||
		    			Tonart.toString() == "fis-mol" || Tonart.toString()=="Fis-mol"){
		    		Tonspur = R.raw.gesmol;}
		    	else if(Tonart.toString() == "as-mol" || Tonart.toString()=="As-mol"||
		    			Tonart.toString() == "gis-mol" || Tonart.toString()=="Gis-mol"){
		    		Tonspur = R.raw.asmol;}
		    	else if(Tonart.toString() == "b-mol" || Tonart.toString()=="B-mol"||
		    			Tonart.toString() == "ais-mol" || Tonart.toString()=="Ais-mol"){
		    		Tonspur = R.raw.bmol;}
		    	else if(Tonart.toString() == "c-dur" || Tonart.toString()=="C-dur"){
		    		Tonspur = R.raw.cdur;}
		    	else if(Tonart.toString() == "d-dur" || Tonart.toString()=="D-dur"){
		    		Tonspur = R.raw.ddur;}
		    	else if(Tonart.toString() == "e-dur" || Tonart.toString()=="E-dur"){
		    		Tonspur = R.raw.edur;}
		    	else if(Tonart.toString() == "f-dur" || Tonart.toString()=="F-dur"){
		    		Tonspur = R.raw.fdur;}
		    	else if(Tonart.toString() == "g-dur" || Tonart.toString()=="G-dur"){
		    		Tonspur = R.raw.gdur;}
		    	else if(Tonart.toString() == "a-dur" || Tonart.toString()=="A-dur"){
		    		Tonspur = R.raw.adur;}
		    	else if(Tonart.toString() == "h-dur" || Tonart.toString()=="H-dur"){
		    		Tonspur = R.raw.hdur;}
		    	else if(Tonart.toString() == "cis-dur" || Tonart.toString()=="Cis-dur"||
		    			Tonart.toString() == "des-dur" || Tonart.toString()=="Des-dur"){
		    		Tonspur = R.raw.cisdur;}
		    	else if(Tonart.toString() == "dis-dur" || Tonart.toString()=="Dis-dur"||
		    			Tonart.toString() == "es-dur" || Tonart.toString()=="Es-dur"){
		    		Tonspur = R.raw.disdur;}
		    	else if(Tonart.toString() == "fis-dur" || Tonart.toString()=="Fis-dur"||
		    			Tonart.toString() == "ges-dur" || Tonart.toString()=="Ges-dur"){
		    		Tonspur = R.raw.fisdur;}
		    	else if(Tonart.toString() == "gis-dur" || Tonart.toString()=="Gis-dur" ||
		    			Tonart.toString() == "as-dur" || Tonart.toString()=="As-dur"){
		    		Tonspur = R.raw.gisdur;}
		    	else if(Tonart.toString() == "ais-dur" || Tonart.toString()=="ais-dur"||
		    			Tonart.toString() == "b-dur" || Tonart.toString()=="B-dur"){
		    		Tonspur = R.raw.aisdur;}
		    	
		    	mp= MediaPlayer.create(getApplicationContext(), Tonspur);
		    	mp.start();
		    	break;
		    
		    case R.id.cb_favorit:
		    	if(cb_favorit.isChecked()){
		    		
		    	}
		    	break;
		    default: 
		    	
		    	break;
		    
		    
		    }
		    	
	}

		
}