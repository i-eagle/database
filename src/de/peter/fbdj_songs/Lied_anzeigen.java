package de.peter.fbdj_songs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Lied_anzeigen extends ActionBarActivity {

	public TextView Liedtitel, Interpret, Liedtext; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lied_anzeigen);

		Liedtitel = (TextView)findViewById(R.id.tv_liedtitel);
		Interpret = (TextView)findViewById(R.id.tv_interpret);
		Liedtext = (TextView)findViewById(R.id.tv_liedtext);
		
		Intent intent = getIntent();
		Liedtitel.setText(intent.getStringExtra("Liedtitel"));
		Interpret.setText(intent.getStringExtra("Interpret"));
		Liedtext.setText(intent.getStringExtra("Liedtext"));
	}
}