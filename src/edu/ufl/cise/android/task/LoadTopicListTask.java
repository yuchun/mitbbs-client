package edu.ufl.cise.android.task;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.util.ParserException;

import edu.ufl.cise.android.data.Link;
import edu.ufl.cise.android.data.Topic;
import edu.ufl.cise.android.data.TopicList;
import edu.ufl.cise.android.dataModel.GuidanceDataModel;
import edu.ufl.cise.android.dataModel.TopicListDataModel;
import edu.ufl.cise.android.util.WebCrawler;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoadTopicListTask extends AsyncTask<String, Integer, TopicList<Link>> {

	TopicListDataModel model;
	ProgressDialog dialog;
	
	public LoadTopicListTask(Context ctx, TopicListDataModel model){
		this.model = model;
		dialog = new ProgressDialog(ctx);
	}
	
	@Override
	protected void onPreExecute(){
		dialog.setMessage("Loading Guidance...");
		dialog.show();
	}
	
	@Override
	protected TopicList<Link> doInBackground(String... params) {
		// TODO Auto-generated method stub
		TopicList<Link> lists = null;
		try {
			lists = WebCrawler.getInstance().parseTopicList(model);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialog.cancel();
			
		return lists;
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress){
		;//
	}
	
	@Override
	protected void onPostExecute(TopicList<Link> result){
		model.refresh(result);
	}
}
