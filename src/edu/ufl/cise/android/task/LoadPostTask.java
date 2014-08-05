package edu.ufl.cise.android.task;

import org.htmlparser.util.ParserException;

import edu.ufl.cise.android.data.Page;
import edu.ufl.cise.android.dataModel.PostDataModel;
import edu.ufl.cise.android.util.WebCrawler;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoadPostTask extends AsyncTask<String, Integer, Page> {

	PostDataModel model;
	ProgressDialog dialog;
	
	public LoadPostTask(Context ctx, PostDataModel postDataModel){
		this.model = postDataModel;
		dialog = new ProgressDialog(ctx);
	}
	
	@Override
	protected void onPreExecute(){
		dialog.setMessage("Loading Post...");
		dialog.show();
	}
	
	@Override
	protected Page doInBackground(String... params) {
		// TODO Auto-generated method stub
		Page page = null;
		try {
			page = WebCrawler.getInstance().parsePage(model);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialog.cancel();
			
		return page;
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress){
		;//
	}
	
	@Override
	protected void onPostExecute(Page result){
		model.refresh(result);
	}
}
