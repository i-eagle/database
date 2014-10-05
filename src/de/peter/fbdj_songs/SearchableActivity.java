package de.peter.fbdj_songs;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.os.Build;

public class SearchableActivity extends ListActivity implements OnItemClickListener {
	public CommentsDataSource datasource;
	public String query;
	public static String JARGON = "KEY";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);
		
		datasource = new CommentsDataSource(this);
		datasource.open();
		List<Comment> values = datasource.getAllComments();
		ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
			        android.R.layout.simple_list_item_1, values);
		
		Intent intent=getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        query = intent.getStringExtra(SearchManager.QUERY);
	       
	        
	        List<Comment> values_ = datasource.getComment(query);
		    ArrayAdapter<Comment> adapter_search = new ArrayAdapter<Comment>(this,
		        android.R.layout.simple_list_item_1, values_);
		    setListAdapter(adapter_search);
		    getListView().setOnItemClickListener(this);
	      }

}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		List<Comment> values = null;
		values = datasource.getComment(query);
		Comment comment = values.get((int) id);
		//new Comment();
    	Intent lied_anzeigen = new Intent(SearchableActivity.this, Lied_anzeigen.class);
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
