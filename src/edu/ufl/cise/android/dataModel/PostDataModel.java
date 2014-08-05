package edu.ufl.cise.android.dataModel;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import edu.ufl.cise.android.data.Page;
import edu.ufl.cise.android.data.Post;
import edu.ufl.cise.android.data.Topic;
import edu.ufl.cise.android.task.LoadPostTask;

public class PostDataModel extends BaseDataModel{
	
	private Page page;
	private String Board = "";
	private int pageInBoard = 1;
	
	public PostDataModel(String topic, String link){
		page = new Page<Post>(topic, link);
	}

	public void refresh(Page newPage){
		page.update(newPage);
		notifyObservers();
	}
	
	public boolean empty(){
		return page.getLinks().size() == 0;
	}
	
	public void update(Context ctx){       
        new LoadPostTask(ctx, this).execute("");
	}
	
	public Page getPage(){
		return page;
	}
}
