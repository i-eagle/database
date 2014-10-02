package de.peter.fbdj_songs;

import java.util.List;

import android.media.MediaPlayer;
import android.app.ListActivity;
import android.content.Intent;
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
import android.widget.ListView;



public class Startpage extends ListActivity implements OnItemClickListener {
	
	public CommentsDataSource datasource;
	public static String neuerEintrag_titel;
	public static String neuerEintrag_interpret;
	public static String neuerEintrag_tonart;
	public static String neuerEintrag_liedtext;
	public static String et_search;
	private ListView lv;
	public MediaPlayer mp;
	
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_startpage);
	    
	    //lv = (ListView) findViewById(R.id.my_list);
	    
	    
	    datasource = new CommentsDataSource(this);
	    datasource.open();

	    List<Comment> values = datasource.getAllComments();
	    
	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	    getListView().setOnItemClickListener(this);
	    getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
	    	  public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
				/////
	    		  List<Comment> values = datasource.getAllComments();
	  			Comment comment = values.get(position);
	  			datasource.deleteComment(comment);
	    		  
	    		    //do your stuff here
	    		  return false;	}
	    		});
	  
	    
	    
	  
	  }
	    
	    

	  // Will be called via the onClick attribute
	  // of the buttons in main.xml
	//  @SuppressWarnings("null")
	public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
	    Comment comment = null;
	    switch (view.getId()) {
	    
	    case R.id.add:		// hier ist die fkt, um neue einträge zu schreiben
	    	
		    Intent intent = new Intent(Startpage.this, Neues_Lied_eingeben.class);
			startActivity(intent);
	    	break;		    
	    	
	    	
	    case R.id.delete:
	      if (getListAdapter().getCount() > 0) {
	       comment = (Comment) getListAdapter().getItem(0);
	       datasource.deleteComment(comment);
	       //adapter.remove(comment);			// hier geschieht ein fehler
	      
	      }
	      break;
	      
	    case R.id.search: break;
	   
	    }
	    List<Comment> values = datasource.getAllComments();
	    
	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    adapter = new ArrayAdapter<Comment>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
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
				case R.id.menu_add:				return true;
				case R.id.menu_search:			return true;//Titel der Datenbank nach eingegebenen Titel durchsuchen
				case R.id.menu_settings:		return true;
				case R.id.menu_sort_favorit:	return true;//Titel der Datenbank mit int Favorit=1;
				case R.id.menu_sort_interpret:	return true;//Titel der Datenbank mit eingegebenen Interpreten suchen
				case R.id.menu_sort_name:		return true;//Titel der Datenbank nach Namen sortieren
				default: //Titel der Datenbank nach Namen sortieren
			}
			return super.onOptionsItemSelected(item);
		}



		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			List<Comment> values = datasource.getAllComments();
			Comment comment = values.get(position);
					//new Comment();
			    	Intent lied_anzeigen = new Intent(Startpage.this, Lied_anzeigen.class);
			    	lied_anzeigen.putExtra("Liedtitel", comment.getTitel().toString());
			    	lied_anzeigen.putExtra("Interpret", comment.getInterpret().toString());
			    	lied_anzeigen.putExtra("Tonart", comment.getTonart().toString());
			    	lied_anzeigen.putExtra("Liedtext", comment.getLiedtext().toString());
					startActivity(lied_anzeigen);
		}
		
	} 

	

