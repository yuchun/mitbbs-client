package edu.ufl.cise.android.adapter;

import java.util.List;

import edu.ufl.cise.android.BoardActivity;
import edu.ufl.cise.android.PostActivity;
import edu.ufl.cise.android.R;
import edu.ufl.cise.android.data.Link;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.ufl.cise.android.dataModel.*;

public class TopicListAdapter extends ArrayAdapter<Link>
								implements OnItemClickListener, BaseDataModel.OnDataModelChangeObserver{
	private List<Link> links;
	private BaseDataModel model;
	private Context context; 
	private int linkType;
	
	public static final int LINKTYPE_POST = 0; 
	public static final int LINKTYPE_BOARD = 1;
	//private List<>
	
	public TopicListAdapter(Context context, int resource, BaseDataModel model, List<Link> links, int linkType) {
		super(context, resource, links);
		// TODO Auto-generated constructor stub
		this.model = model;
		this.links = links;
		this.context = context;
		this.linkType = linkType;
		model.registerOnDataModelChangeObserver(this);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		if(v == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.listitem_link, null);
		}
		
		Link i = links.get(position);
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
		Intent intent = null;
		switch(linkType){
		case LINKTYPE_POST:
			intent = new Intent(context, PostActivity.class);
			break;
		case LINKTYPE_BOARD:
			intent = new Intent(context, BoardActivity.class);
			break;
		}
		if(intent != null){
			intent.putExtra("topic", links.get(position).text);
			intent.putExtra("link", links.get(position).link);
			context.startActivity(intent);
		}
	}

	@Override
	public void onDataModelChanged() {
		// TODO Auto-generated method stub
		notifyDataSetChanged();
	}

}
