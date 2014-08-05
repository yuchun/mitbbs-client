package edu.ufl.cise.android.dataModel;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import edu.ufl.cise.android.data.Link;
import edu.ufl.cise.android.data.TopicList;
import edu.ufl.cise.android.task.LoadTopicListTask;

public class TopicListDataModel extends BaseDataModel{

	private TopicList<Link> list;
	public TopicListDataModel(Context ctx,String link){
		list = new TopicList<Link>(link);
	}
	
	public void refresh(TopicList<Link> result){
		list.update(result);
		notifyObservers();
	}
	
	public boolean empty(){
		return true;
	}
	
	public void update(Context ctx){       
        new LoadTopicListTask(ctx, this).execute("");
	}
	
	public TopicList<Link> getList(){
		return list;
	}

	public List<Link> getTopicLists() {
		// TODO Auto-generated method stub
		return list.getLinks();
	}
	
}
