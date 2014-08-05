package edu.ufl.cise.android.adapter;

import java.util.List;

import edu.ufl.cise.android.PostActivity;
import edu.ufl.cise.android.R;
import edu.ufl.cise.android.data.Post;
import edu.ufl.cise.android.data.Topic;
import edu.ufl.cise.android.dataModel.GuidanceDataModel;

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

public class PostAdapter extends ArrayAdapter
								implements OnItemClickListener, BaseDataModel.OnDataModelChangeObserver{
	private PostDataModel model;
	private Context context; 
	//private List<>
	
	public PostAdapter(Context context, int resource, PostDataModel model) {
		super(context, resource, model.getPage().getLinks());
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
			v = inflater.inflate(R.layout.listitem_post, null);
		}
		
		Post i = (Post) model.getPage().getLinks().get(position);
		if(i != null){
			TextView tv = (TextView)v.findViewById(R.id.post_topic);
			if(tv != null)
				tv.setText(i.topic);
			tv = (TextView)v.findViewById(R.id.post_content);
			if(tv != null)
				tv.setText(i.contents);
		}
		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		//may add reply action
	}

	@Override
	public void onDataModelChanged() {
		// TODO Auto-generated method stub
		notifyDataSetChanged();
	}

}
