package de.peter.fbdj_songs;

import java.util.Arrays;
import java.util.List;

import android.R.color;
import android.R.menu;
import android.media.MediaPlayer;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
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
import android.widget.TextView;



public class Startpage extends ListActivity implements OnItemClickListener {
	
	public CommentsDataSource datasource;
	public static String neuerEintrag_titel;
	public static String neuerEintrag_interpret;
	public static String neuerEintrag_tonart;
	public static String neuerEintrag_liedtext;
	public  String search, von_wo;
	public EditText et_search;
	public String[] letzte_eingaben;
	private Menu menu;
	private List<String> items;
	private TextView history;

	
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_startpage);
	   // handleIntent(getIntent());
	    
	    int i =0;
	    et_search = (EditText)findViewById(R.id.et_search);
	    search = et_search.getText().toString();
	    datasource = new CommentsDataSource(this);
	    datasource.open();
	    
	    items = datasource.getAllTitel();
	    ArrayAdapter<String> adapter_items = new ArrayAdapter<String>(this, 
	    		android.R.layout.simple_list_item_1, items);
	    List<Comment> values = datasource.getAllComments();
	    ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	    getListView().setOnItemClickListener(this);
	    von_wo="alle";
	   
	    
	  }
	  /*//Versuch eine Search-view-widget mit history-einträgen.
	   * jedoch ohne vollständigem Erfolg, deshalb auskommentiert   
	  @Override
	  public boolean onSearchRequested() {
	       Bundle appData = new Bundle();
	       appData.putBoolean(SearchableActivity.JARGON, true);
	       startSearch(null, false, appData, false);
	       return true;
	   }
	    
	  @Override
	  protected void onNewIntent(Intent intent) {
	      setIntent(intent);
	      handleIntent(intent);
	  }

	  public void handleIntent(Intent intent) {
	      if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	  String query = intent.getStringExtra(SearchManager.QUERY);
		       
		        List<Comment> values_ = datasource.getComment(query);
			    ArrayAdapter<Comment> adapter_search = new ArrayAdapter<Comment>(this,
			        android.R.layout.simple_list_item_1, values_);
			    setListAdapter(adapter_search);
			    getListView().setOnItemClickListener(this);
	      }
	  }
	 */

	  	/** Will be called via the onClick attribute
	   of the buttons in main.xml
	  @SuppressWarnings("null")*/
	  	public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
	   
	    switch (view.getId()) {
	   
	    case R.id.search: 
	    	/*//um eine anzeigen-history zu erstellen, jedoch ohne fkt, deshalb auskommentiert
	    	 int i=0;
	    	 
	    	letzte_eingaben= new String[4];
	    	if(i<=4){
	    		letzte_eingaben[i] = search;
	    		i++;
	    		if(i==5){
	    			i=0;
	    		}*/
	    	//Alle Datenbankeinträge nach eingegebenem Titel durchsuchen
		    List<Comment> values = datasource.getComment(et_search.getText().toString());
		    ArrayAdapter<Comment> adapter_search = new ArrayAdapter<Comment>(this,
		        android.R.layout.simple_list_item_1, values);
		    setListAdapter(adapter_search);
		    getListView().setOnItemClickListener(this);
		    
	    	break;
	   
	    case R.id.et_search: break;
	  
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
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public boolean onCreateOptionsMenu(Menu menu) {

			getMenuInflater().inflate(R.menu.startpage, menu);
			this.menu = menu;

			/* //Versuch ein Search-View- widget einzufügen, hat jedoch nicht ganz geklappt
			    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			      /*  SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
			     //   search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
			        search.setOnQueryTextListener(new OnQueryTextListener() { 

			            @Override 
			            public boolean onQueryTextChange(String query) {

			                loadHistory(query);

			                return true; 

			            }

						@Override
						public boolean onQueryTextSubmit(String letzte_eingaben) {
							// TODO Auto-generated method stub
							loadHistory(letzte_eingaben);
							
							return false;
						} 

			        });*/

			    
			
			
			
		
	     	  return true;
	  			}
		    
/*
	 // History //Versuch ein Search-View- widget einzufügen, hat jedoch nicht ganz geklappt
	 
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void loadHistory(String query) {

	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

	        // Cursor
	        String[] columns = new String[]{"_id", MySQLiteHelper.COLUMN_COMMENT1};
	        Object[] temp = new Object[]{0, "default"} ;

	       MatrixCursor cursor = new MatrixCursor(columns);
	        
	        for(int i = 0; i < 5 ; i++) { //history aus 5 Einträgen bestehend

	        	temp[0] = i;
	            temp[1] = items.get(i);

	            cursor.addRow(temp);
	        }
	     // SearchView
	        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

	        final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
	        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
	        search.setSuggestionsAdapter(new ExampleAdapter(this, cursor, items));

	    }
	    }*/

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
			switch(item.getItemId()){
				
			case R.id.menu_sort_favorit:
					// Liste nach Favorit sortieren und anzeigen
					List<Comment> values = datasource.getAllComments_Favorit();
					ArrayAdapter<Comment> adapter_favorit = new ArrayAdapter<Comment>(this,
					        android.R.layout.simple_list_item_1, values);
					setListAdapter(adapter_favorit);
					getListView().setOnItemClickListener(this);
					von_wo ="favorit";
					return true;//Titel der Datenbank mit int Favorit=1;
				
				
				case R.id.menu_sort_interpret:
					//Liste nach Interpret sortieren und anzeigen
					List<Comment> values_interpret = datasource.getAllComments_Interpret();
					ArrayAdapter<Comment> adapter_interpret = new ArrayAdapter<Comment>(this,
							android.R.layout.simple_list_item_1, values_interpret);
					setListAdapter(adapter_interpret);
					getListView().setOnItemClickListener(this);
					von_wo="interpret";
					return true;//Titel der Datenbank nach Interpreten sortieren
				
				
				case R.id.menu_sort_alle:
					// Liste nach Alphabet sortieren und anzeigen
					List<Comment> values_alle = datasource.getAllComments();
					ArrayAdapter<Comment> adapter_alle = new ArrayAdapter<Comment>(this,
							android.R.layout.simple_list_item_1, values_alle);
					setListAdapter(adapter_alle);
					getListView().setOnItemClickListener(this);
					von_wo = "alle";
					return true;//Titel der Datenbank mit eingegebenen Interpreten suchen
				
				case R.id.sort_haeufig_benutzt:
					// Liste nach Countervariable sortieren und anzeigen
					List<Comment> values_haeufig_benutzt = datasource.getAllComments_Haeufig_benutzt();
					ArrayAdapter<Comment> adapter_haeufig_benutzt = new ArrayAdapter<Comment>(this,
							android.R.layout.simple_list_item_1, values_haeufig_benutzt);
					setListAdapter(adapter_haeufig_benutzt);
					getListView().setOnItemClickListener(this);
					von_wo = "haeufig_benutzt";
					return true;
					
				case R.id.reset_haeufig_benutzt:
					// die Countervariable wird in allen Einträgen wieder auf
					// 1000 gesetzt
					// beim sortieren danach wird vom kleinsten bis größten sortiert,
					// und damit der meistgeklickteste Eintrag oben in der Liste steht,
					// muss dekrementiert werden
					CommentsDataSource.updateComment_haeufig_reset();
					return true;
				case R.id.add:
					// neuen Liedeintrag in die Datenbank speichern
				    Intent intent = new Intent(Startpage.this, Neues_Lied_eingeben.class);
				    intent.putExtra("vonStartpage", "ja");
					startActivity(intent);
			    	break;		   
				
					
				default: break;
			}
			return super.onOptionsItemSelected(item);
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			// Wenn die Lieder sortiert werden, damit beim Click, die richtige
			// Id weitergegeben wird für die Lied_anzeigen-Activity
			List<Comment> values = null;
			search = et_search.getText().toString();
			if(von_wo.equals("haeufig_benutzt")){
				values = datasource.getAllComments_Haeufig_benutzt();
			}
			else if(von_wo.equals("interpret")){
				values = datasource.getAllComments_Interpret();
			}
			else if(von_wo.equals("favorit")){
				values = datasource.getAllComments_Favorit();
			}
			else if(search.equals("")){
				values = datasource.getAllComments();
			}
			else if(search.length()>0) {
				values = datasource.getComment(et_search.getText().toString());
			}
			
			Comment comment = values.get((int) id);
					//new Comment();
			    	Intent lied_anzeigen = new Intent(Startpage.this, Lied_anzeigen.class);
			    	lied_anzeigen.putExtra("Liedtitel", comment.getTitel().toString());
			    	lied_anzeigen.putExtra("Interpret", comment.getInterpret().toString());
			    	lied_anzeigen.putExtra("Tonart", comment.getTonart().toString());
			    	lied_anzeigen.putExtra("Liedtext", comment.getLiedtext().toString());
			    	lied_anzeigen.putExtra("Id", comment.getId());
			    	lied_anzeigen.putExtra("Favorit", comment.getFavorit());
			    	int hb=comment.getHaeufig_benutzt();
			    	lied_anzeigen.putExtra("Haeufig_benutzt", hb);
					startActivity(lied_anzeigen);
		}
		
	} 

	

