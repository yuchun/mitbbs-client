package edu.ufl.cise.android.adapter;

import edu.ufl.cise.android.PostActivity;
import edu.ufl.cise.android.R;
import edu.ufl.cise.android.data.Link;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.ufl.cise.android.dataModel.*;

public class BoardListAdapter extends ArrayAdapter
								implements OnItemClickListener, BaseDataModel.OnDataModelChangeObserver{
	private TopicListDataModel model;
	private Context context; 
	//private List<>
	
	public BoardListAdapter(Context context, int resource, TopicListDataModel model) {
		super(context, resource, model.getTopicLists());
		// TODO Auto-generated constructor stub
		this.model = model;
		this.context = context;
		model.registerOnDataModelChangeObserver(this);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		if(v == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.listitem_link, null);
		}
		
		Link i = model.getTopicLists().get(position);
		if(i != null){
			TextView tv = (TextView)v.findViewById(R.id.list_link);
			if(tv != null)
				tv.setText(position+1 + ". " + i.text);
		}
		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Log.i("onItemClick", model.getTopicLists().get(position).link);
		Intent intent = new Intent(context, PostActivity.class);
		intent.putExtra("topic", model.getTopicLists().get(position).text);
		intent.putExtra("link", model.getTopicLists().get(position).link);
		context.startActivity(intent);
	}

	@Override
	public void onDataModelChanged() {
		// TODO Auto-generated method stub
		
		notifyDataSetChanged();
	}

}
