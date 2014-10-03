package de.peter.fbdj_songs;

import java.util.Arrays;
import java.util.List;

import android.media.MediaPlayer;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;



public class Startpage extends ListActivity implements OnItemClickListener {
	
	public CommentsDataSource datasource;
	public static String neuerEintrag_titel;
	public static String neuerEintrag_interpret;
	public static String neuerEintrag_tonart;
	public static String neuerEintrag_liedtext;
	public  String search;
	public EditText et_search;
//	public MediaPlayer mp;
	
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_startpage);
	    
	    et_search = (EditText)findViewById(R.id.et_search);
	    search = et_search.getText().toString();
	    
	    datasource = new CommentsDataSource(this);
	    datasource.open();

	    List<Comment> values = datasource.getAllComments();
	    ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	    getListView().setOnItemClickListener(this);
	    
	  }
	    
	    

	  	/** Will be called via the onClick attribute
	   of the buttons in main.xml
	  @SuppressWarnings("null")*/
	  	public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
	   
	    switch (view.getId()) {
	   
	    case R.id.add:		// hier ist die fkt, um neue einträge zu schreiben
		    Intent intent = new Intent(Startpage.this, Neues_Lied_eingeben.class);
			startActivity(intent);
	    	break;		    
	    	
	    case R.id.search: 
		    List<Comment> values = datasource.getComment(et_search.getText().toString());
		    ArrayAdapter<Comment> adapter_search = new ArrayAdapter<Comment>(this,
		        android.R.layout.simple_list_item_1, values);
		    setListAdapter(adapter_search);
		    getListView().setOnItemClickListener(this);
		    
	    	break;
	   
	   
	  }
	  	}

	@Override
	  	protected void onResume() {
	    datasource.open();
	    super.onResume();
	    this.onCreate(null);
	  }

	  @Override
	  	protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }
	
	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {

			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.startpage, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			
			switch(item.getItemId()){
			
		
				
			case R.id.menu_sort_favorit:
					List<Comment> values = datasource.getAllComments_Favorit();
					ArrayAdapter<Comment> adapter_favorit = new ArrayAdapter<Comment>(this,
					        android.R.layout.simple_list_item_1, values);
					setListAdapter(adapter_favorit);
					getListView().setOnItemClickListener(this);
					return true;//Titel der Datenbank mit int Favorit=1;
				
				
				case R.id.menu_sort_interpret:
					List<Comment> values_interpret = datasource.getAllComments_Interpret();
					ArrayAdapter<Comment> adapter_interpret = new ArrayAdapter<Comment>(this,
							android.R.layout.simple_list_item_1, values_interpret);
					setListAdapter(adapter_interpret);
					getListView().setOnItemClickListener(this);
					return true;//Titel der Datenbank nach Interpreten sortieren
				case R.id.menu_sort_alle:
					List<Comment> values_alle = datasource.getAllComments();
					ArrayAdapter<Comment> adapter_alle = new ArrayAdapter<Comment>(this,
							android.R.layout.simple_list_item_1, values_alle);
					setListAdapter(adapter_alle);
					getListView().setOnItemClickListener(this);
					return true;//Titel der Datenbank mit eingegebenen Interpreten suchen
				default: //Titel der Datenbank nach Namen sortieren
			}
			return super.onOptionsItemSelected(item);
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			List<Comment> values;
			search = et_search.getText().toString();
			if(search.equals("")){
				values = datasource.getAllComments();
			}
			else {values = datasource.getComment(et_search.getText().toString());}
			
			Comment comment = values.get((int) id);
					//new Comment();
			    	Intent lied_anzeigen = new Intent(Startpage.this, Lied_anzeigen.class);
			    	lied_anzeigen.putExtra("Liedtitel", comment.getTitel().toString());
			    	lied_anzeigen.putExtra("Interpret", comment.getInterpret().toString());
			    	lied_anzeigen.putExtra("Tonart", comment.getTonart().toString());
			    	lied_anzeigen.putExtra("Liedtext", comment.getLiedtext().toString());
			    	lied_anzeigen.putExtra("Id", comment.getId());
			    	lied_anzeigen.putExtra("Favorit", comment.getFavorit());
			    	//lied_anzeigen.putExtra("Update", "nein");
					startActivity(lied_anzeigen);
		}
		
	} 

	

