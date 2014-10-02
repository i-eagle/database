package de.peter.fbdj_songs;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyList extends ListActivity {
	
	public CommentsDataSource datasource;
	public ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_list);
		
		lv = (ListView)findViewById(R.id.my_list);
		//lv.setOnItemSelectedListener(listener);
		
		 datasource = new CommentsDataSource(this);
		 datasource.open();

	

	    List<Comment> values = datasource.getAllComments();
	    
	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
	        android.R.layout.simple_list_item_1, values);
	    lv.setAdapter(adapter);
	    //setListAdapter(adapter);
	}
	
	
	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    String item = (String) getListAdapter().getItem(position);
	    /////////////// wenn auf Item klicken, was zu tun ist
	    
	    
	    
	    
	    /////////////
	    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	  }
}
