package edu.ufl.cise.android;

import edu.ufl.cise.android.fragment.PostFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PostActivity extends FragmentActivity{
	PostFragment fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		fragment = (PostFragment) getSupportFragmentManager().findFragmentById(R.id.frag_post);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.post_options, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.menu_post_refresh:
			fragment.onRefresh();
			break;
		case R.id.menu_post_return:
			fragment.returnBoard();
			break;
			
		}
		return true;
	}
}
